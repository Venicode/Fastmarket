package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Dominios.*;
import org.example.Servicos.CRUD.Crud;
import org.example.Servicos.pdf.CreatePDF;
import org.example.Tema.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class Venda extends JFrame {
    String[] columns = {"Produto", "Quantidade", "Valor Unitário", "Desconto", "Codigo"};
    DefaultTableModel tbModel = new DefaultTableModel(columns, 0);
    public JPanel panel1;
    private JTextField txt_quantidade;
    private JTextArea txtarea_troco;
    private JTextArea txtarea_valor;
    private JButton btn_addVenda;
    private JComboBox cb_metodoPagamento;
    private JButton btn_addProduto;
    private JTextField txt_amount;
    private JPanel img_pix;
    private JLabel lb_troco;
    private JTable tb_produtos;
    private JPanel panel2;
    private JLabel lb_valorpago;
    private JComboBox cb_produtos;
    private JButton btn_voltar;
    private JComboBox cb_clientes;
    private Login login;

    private final List<MetodoPagamento> metodos = new ArrayList<>();
    private TipoPedido tipoPedidoPdv;
    Pedido pedido = new Pedido();
    double amountTotal = 0;
    double amountNoDiscount = 0;
    private final List<String> list = new ArrayList<>();

    private static double parseValor(String texto) {
        String t = texto == null ? "" : texto.replace("R$", "").replace(",", ".").trim();
        return t.isEmpty() ? 0 : Double.parseDouble(t);
    }

    public Venda(Login login) throws IOException, InterruptedException {
        this.login = login;
        carregarProdutos();
        carregarClientes();
        lb_valorpago.setVisible(false);
        tb_produtos.setModel(tbModel);
        tbModel.addTableModelListener(ev -> {
            try { recalcularTotais(); } catch (NumberFormatException ignored) {}
        });

        img_pix.setVisible(false);
        txt_amount.setVisible(false);

        Crud crudMethod = new Crud();
        crudMethod.setEndpoint("metodoPagamento");
        ObjectMapper mapperMetodo = new ObjectMapper();
        for (Map map : crudMethod.getAll()) {
            MetodoPagamento metodoPagamento = mapperMetodo.convertValue(map, MetodoPagamento.class);
            metodos.add(metodoPagamento);
            cb_metodoPagamento.addItem(metodoPagamento.getMetodoPagamento());
        }

        Crud crudTipo = new Crud("tipoPedido");
        ObjectMapper mapperTipo = new ObjectMapper();
        for (Map map : crudTipo.getAll()) {
            TipoPedido t = mapperTipo.convertValue(map, TipoPedido.class);
            if ("PDV".equalsIgnoreCase(t.getTipoPedido())) tipoPedidoPdv = t;
        }

        btn_addProduto.addActionListener(e -> {
            try {
                Produto produto = (Produto) cb_produtos.getSelectedItem();
                if (produto == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um produto.");
                    return;
                }
                int quantity = Integer.parseInt(txt_quantidade.getText().trim());
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(null, "Informe uma quantidade válida.");
                    return;
                }
                double subtotal = produto.getPrecoVenda() * quantity;

                String entrada = JOptionPane.showInputDialog(null, "Desconto (opcional):", "0");
                if (entrada == null) return;
                double desconto = parseValor(entrada);

                if (desconto < 0 || desconto > subtotal) {
                    JOptionPane.showMessageDialog(null, "Desconto inválido para este item.");
                    return;
                }
                tbModel.addRow(new Object[]{produto.getDescricao(), String.valueOf(quantity),
                        String.valueOf(produto.getPrecoVenda()), String.valueOf(desconto),
                        produto.getId().toString()});
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Informe quantidade e desconto válidos.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        btn_addVenda.addActionListener(e -> {
            try {
                if (tbModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Adicione ao menos um produto.");
                    return;
                }
                if (tipoPedidoPdv == null) {
                    JOptionPane.showMessageDialog(null, "Tipo de pedido PDV não encontrado.");
                    return;
                }
                recalcularTotais();
                ObjectMapper mapper = new ObjectMapper();

                String nome = cb_metodoPagamento.getSelectedItem().toString();
                MetodoPagamento metodo = metodos.stream()
                        .filter(m -> nome.equals(m.getMetodoPagamento()))
                        .findFirst().orElse(null);
                if (metodo == null) {
                    JOptionPane.showMessageDialog(null, "Método de pagamento inválido.");
                    return;
                }
                pedido.setMetodoPagamento(metodo);
                pedido.setTipoPedido(tipoPedidoPdv);
                pedido.setCliente((Cliente) cb_clientes.getSelectedItem());

                Crud crudPedido = new Crud("pedido");
                HttpResponse<String> respPedido = crudPedido.post(pedido.fromMap(pedido));
                System.out.println("PEDIDO: " + respPedido.statusCode() + " -> " + respPedido.body());
                if (respPedido.statusCode() != 200 && respPedido.statusCode() != 201) {
                    JOptionPane.showMessageDialog(null, "Erro ao criar o pedido: " + respPedido.body());
                    return;
                }
                Long idPedido = ((Number) mapper.readValue(respPedido.body(), Map.class).get("id")).longValue();
                Pedido pedidoSalvo = new Pedido();
                pedidoSalvo.setId(idPedido);

                double troco = parseValor(txtarea_troco.getText());
                Crud crudItem = new Crud("itemPedido");
                for (int row = 0; row < tbModel.getRowCount(); row++) {
                    int qtd = Integer.parseInt(tbModel.getValueAt(row, 1).toString());
                    double unitario = parseValor(tbModel.getValueAt(row, 2).toString());
                    double desconto = parseValor(tbModel.getValueAt(row, 3).toString());
                    Produto p = new Produto();
                    p.setId(Long.parseLong(tbModel.getValueAt(row, 4).toString()));

                    ItemPedido item = new ItemPedido();
                    item.setPedido(pedidoSalvo);
                    item.setProduto(p);
                    item.setQuantidade(qtd);
                    item.setValor(unitario * qtd - desconto);
                    item.setDesconto(desconto);
                    item.setTroco(row == 0 ? troco : 0);
                    HttpResponse<String> resp = crudItem.post(item.fromMap(item));
                    System.out.println("ITEM " + row + ": " + resp.statusCode() + " -> " + resp.body());
                    if (resp.statusCode() != 200 && resp.statusCode() != 201) {
                        JOptionPane.showMessageDialog(null, "Erro no item " + (row + 1) + ": " + resp.body());
                        return;
                    }
                }

                JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!");

                list.add("Valor Pago: " + amountTotal);
                list.add("Valor da Venda: " + amountNoDiscount);
                list.add("Descontos: " + (amountNoDiscount - amountTotal));
                list.add("Forma de Pagamento: " + nome);
                list.add("\nData e hora: " + LocalDateTime.now());
                new CreatePDF("FASTMARKET", list, "comprovante").printPdf();

                tbModel.setRowCount(0);
                list.clear();
                amountTotal = 0;
                amountNoDiscount = 0;
                txtarea_valor.setText("R$ 0.0");
                txtarea_troco.setText("");
                txt_amount.setText("");
                pedido = new Pedido();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao realizar a venda: " + ex.getMessage());
            }
        });

        txt_amount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        double pago = parseValor(txt_amount.getText());
                        txtarea_troco.setText("R$ " + (pago - amountTotal));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Informe um valor válido.");
                    }
                }
            }
        });

        cb_metodoPagamento.addActionListener(e -> {
            if (cb_metodoPagamento.getSelectedItem() == null) return;
            String m = cb_metodoPagamento.getSelectedItem().toString();
            if (m.equals("Dinheiro")) {
                txt_amount.setVisible(true);
                lb_valorpago.setVisible(true);
                img_pix.setVisible(false);
            } else if (m.equals("PIX")) {
                img_pix.setVisible(true);
                txt_amount.setVisible(false);
                lb_valorpago.setVisible(false);
            }
            panel1.revalidate();
            panel1.repaint();
        });

        tb_produtos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        recalcularTotais();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Há valores inválidos na tabela.");
                    }
                }
            }
        });

        txt_quantidade.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txt_quantidade.setText("");
            }
        });

        btn_voltar.addActionListener(e -> {
            try {
                Menu menu = new Menu(login);
                Tema.aplicar(menu.panel);
                menu.setContentPane(menu.panel);
                menu.setTitle("Menu");
                menu.setExtendedState(MAXIMIZED_BOTH);
                dispose();
                menu.setVisible(true);
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void recalcularTotais() {
        amountTotal = 0;
        amountNoDiscount = 0;
        list.clear();
        for (int row = 0; row < tbModel.getRowCount(); row++) {
            String descricao = tbModel.getValueAt(row, 0).toString();
            int qtd = Integer.parseInt(tbModel.getValueAt(row, 1).toString());
            double unitario = parseValor(tbModel.getValueAt(row, 2).toString());
            double desconto = parseValor(tbModel.getValueAt(row, 3).toString());
            double subtotal = unitario * qtd;
            amountNoDiscount += subtotal;
            amountTotal += subtotal - desconto;
            list.add(descricao + " Quant. " + qtd + " Valor Unit. " + unitario
                    + (desconto > 0 ? " Desc. " + desconto : ""));
        }
        txtarea_valor.setText("R$ " + amountTotal);
    }

    private void carregarProdutos() {
        try {
            Crud crud = new Crud();
            crud.setEndpoint("produto");
            ObjectMapper mapper = new ObjectMapper();
            DefaultComboBoxModel<Produto> model = new DefaultComboBoxModel<>();
            for (Map<String, Object> item : (List<Map>) (List) crud.getAll()) {
                model.addElement(mapper.convertValue(item, Produto.class));
            }
            cb_produtos.setModel(model);
            cb_produtos.setSelectedIndex(-1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar produtos: " + ex.getMessage());
        }
    }

    private void carregarClientes() {
        try {
            Crud crud = new Crud();
            crud.setEndpoint("cliente");
            ObjectMapper mapper = new ObjectMapper();
            DefaultComboBoxModel<Cliente> model = new DefaultComboBoxModel<>();
            for (Map<String, Object> item : (List<Map>) (List) crud.getAll()) {
                model.addElement(mapper.convertValue(item, Cliente.class));
            }
            cb_clientes.setModel(model);
            cb_clientes.setSelectedIndex(-1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar clientes: " + ex.getMessage());
        }
    }

    private void createUIComponents() {
    }
}