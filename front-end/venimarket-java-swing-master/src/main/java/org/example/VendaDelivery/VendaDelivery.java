package org.example.VendaDelivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Menu;
import org.example.Dominios.*;
import org.example.Servicos.CRUD.Crud;
import org.example.Servicos.pdf.CreatePDF;
import org.example.Tema.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.print.PrinterAbortException;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VendaDelivery extends JFrame {
    String[] columns = {"Produto", "Quantidade", "Valor Unitário", "Desconto", "Codigo"};
    DefaultTableModel tbModel = new DefaultTableModel(columns, 0);
    public JPanel panel1;
    private JTextField txtfield_quantity;
    private JTextArea txtarea_amount;
    private JButton btn_addsell;
    private JComboBox cb_methodpayment;
    private JButton btn_addProduct;
    private JTable tb_products;
    private JPanel panel2;
    private JComboBox cb_produtos;
    private JButton btn_voltar;
    private JComboBox cb_clientes;
    private JComboBox cb_status;
    private JTextField txt_endereco;
    private JTextField txt_entregador;
    private Login login;

    private final List<MetodoPagamento> metodos = new ArrayList<>();
    private TipoPedido tipoPedidoDelivery;
    Pedido pedido = new Pedido();
    double amountTotal = 0;
    double amountNoDiscount = 0;
    private JList<PedidoResumo> lst_pedidos;
    private JButton btn_novo;
    private final DefaultListModel<PedidoResumo> pedidosModel = new DefaultListModel<>();
    private Long pedidoEmEdicaoId;
    private final List<String> list = new ArrayList<>();

    private static double parseValor(String texto) {
        String t = texto == null ? "" : texto.replace("R$", "").replace(",", ".").trim();
        return t.isEmpty() ? 0 : Double.parseDouble(t);
    }

    public VendaDelivery(Login login) throws IOException, InterruptedException {

        this.login = login;
        carregarProdutos();
        carregarClientes();
        carregarStatus();
        lst_pedidos.setModel(pedidosModel);
        carregarHistorico();

        lst_pedidos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && lst_pedidos.getSelectedValue() != null) {
                abrirPedido(lst_pedidos.getSelectedValue());
            }
        });

        btn_novo.addActionListener(e -> novoPedido());
        tb_products.setModel(tbModel);
        tbModel.addTableModelListener(ev -> {
            try { recalcularTotais(); } catch (NumberFormatException ignored) {}
        });

        Crud crudMethod = new Crud("metodoPagamento");
        ObjectMapper mapperMetodo = new ObjectMapper();
        for (Map map : crudMethod.getAll()) {
            MetodoPagamento metodoPagamento = mapperMetodo.convertValue(map, MetodoPagamento.class);
            metodos.add(metodoPagamento);
            cb_methodpayment.addItem(metodoPagamento.getMetodoPagamento());
        }

        Crud crudTipo = new Crud("tipoPedido");
        ObjectMapper mapperTipo = new ObjectMapper();
        for (Map map : crudTipo.getAll()) {
            TipoPedido t = mapperTipo.convertValue(map, TipoPedido.class);
            if ("Delivery".equalsIgnoreCase(t.getTipoPedido())) {
                tipoPedidoDelivery = t;
            }
        }

        btn_addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Produto produto = (Produto) cb_produtos.getSelectedItem();
                    if (produto == null) {
                        JOptionPane.showMessageDialog(null, "Selecione um produto.");
                        return;
                    }
                    int quantity = Integer.parseInt(txtfield_quantity.getText().trim());
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
            }
        });

        btn_addsell.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (pedidoEmEdicaoId != null) {
                    atualizarStatus();
                    return;
                }
                try {
                    if (tbModel.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "Adicione ao menos um produto.");
                        return;
                    }
                    if (tipoPedidoDelivery == null) {
                        JOptionPane.showMessageDialog(null, "Tipo de pedido Delivery não encontrado.");
                        return;
                    }
                    Cliente cliente = (Cliente) cb_clientes.getSelectedItem();
                    if (cliente == null) {
                        JOptionPane.showMessageDialog(null, "Selecione o cliente.");
                        return;
                    }
                    String endereco = txt_endereco.getText();
                    if (endereco.isBlank()) {
                        JOptionPane.showMessageDialog(null, "Informe o endereço de entrega.");
                        return;
                    }
                    StatusPedido statusPedido = (StatusPedido) cb_status.getSelectedItem();
                    if (statusPedido == null) {
                        JOptionPane.showMessageDialog(null, "Selecione o status do pedido.");
                        return;
                    }

                    recalcularTotais();
                    ObjectMapper mapper = new ObjectMapper();

                    String nome = cb_methodpayment.getSelectedItem().toString();
                    MetodoPagamento metodo = metodos.stream()
                            .filter(m -> nome.equals(m.getMetodoPagamento()))
                            .findFirst().orElse(null);
                    if (metodo == null) {
                        JOptionPane.showMessageDialog(null, "Método de pagamento inválido.");
                        return;
                    }

                    pedido.setMetodoPagamento(metodo);
                    pedido.setTipoPedido(tipoPedidoDelivery);
                    pedido.setCliente(cliente);
                    pedido.setEnderecoEntrega(endereco);
                    pedido.setStatusPedido(statusPedido);
                    pedido.setEntregador(txt_entregador.getText());
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

                    Crud crudItem = new Crud("itemPedido");
                    for (int row = 0; row < tbModel.getRowCount(); row++) {
                        double desconto = parseValor(tbModel.getValueAt(row, 3).toString());
                        double unitario = parseValor(tbModel.getValueAt(row, 2).toString());
                        int qtd = Integer.parseInt(tbModel.getValueAt(row, 1).toString());
                        Produto p = new Produto();
                        p.setId(Long.parseLong(tbModel.getValueAt(row, 4).toString()));

                        ItemPedido item = new ItemPedido();
                        item.setPedido(pedidoSalvo);
                        item.setProduto(p);
                        item.setQuantidade(qtd);
                        item.setValor(unitario * qtd - desconto);
                        item.setDesconto(desconto);

                        HttpResponse<String> resp = crudItem.post(item.fromMap(item));
                        System.out.println("ITEM " + row + ": " + resp.statusCode() + " -> " + resp.body());
                        if (resp.statusCode() != 200 && resp.statusCode() != 201) {
                            JOptionPane.showMessageDialog(null, "Erro no item " + (row + 1) + ": " + resp.body());
                            return;
                        }
                    }
                    carregarHistorico();
                    JOptionPane.showMessageDialog(null, "Pedido de delivery registrado com sucesso!");

                    list.add("Cliente: " + cliente);
                    list.add("Endereço de entrega: " + endereco);
                    list.add("Valor Pago: " + amountTotal);
                    list.add("Valor da Venda: " + amountNoDiscount);
                    list.add("Descontos: " + (amountNoDiscount - amountTotal));
                    list.add("Forma de Pagamento: " + nome);
                    list.add("\nData e hora: " + LocalDateTime.now());
                    try {
                        new CreatePDF("FASTMARKET", list, "comprovante").printPdf();
                    } catch (PrinterAbortException ex) {
                    }

                    tbModel.setRowCount(0);
                    list.clear();
                    amountTotal = 0;
                    amountNoDiscount = 0;
                    txtarea_amount.setText("R$ 0.0");
                    txt_endereco.setText("");
                    cb_clientes.setSelectedIndex(-1);
                    if (cb_status.getItemCount() > 0) {
                        cb_status.setSelectedIndex(0);
                    }
                    pedido = new Pedido();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao realizar a venda: " + ex.getMessage());
                }
            }
        });

        txtfield_quantity.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtfield_quantity.setText("");
            }
        });

        btn_voltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Menu menu = new Menu(login);
                    Tema.aplicar(menu.panel);
                    menu.setContentPane(menu.panel);
                    menu.setTitle("Menu");
                    menu.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    menu.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
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
        txtarea_amount.setText("R$ " + amountTotal);
    }

    private void carregarProdutos() {
        try {
            Crud crud = new Crud("produto");
            ObjectMapper mapper = new ObjectMapper();
            DefaultComboBoxModel<Produto> model = new DefaultComboBoxModel<>();
            for (Map<String, Object> item : (List<Map<String, Object>>) (List) crud.getAll()) {
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
            List<Map> lista = crud.getAll();

            ObjectMapper mapper = new ObjectMapper();
            DefaultComboBoxModel<Cliente> model = new DefaultComboBoxModel<>();
            for (Map<String, Object> item : lista) {
                model.addElement(mapper.convertValue(item, Cliente.class));
            }
            cb_clientes.setModel(model);
            cb_clientes.setSelectedIndex(-1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar clientes: " + ex.getMessage());
        }
    }

    private void carregarStatus() {
        try {
            Crud crud = new Crud();
            crud.setEndpoint("status");
            List<Map> lista = crud.getAll();

            ObjectMapper mapper = new ObjectMapper();
            DefaultComboBoxModel<StatusPedido> model = new DefaultComboBoxModel<>();
            for (Map<String, Object> item : lista) {
                model.addElement(mapper.convertValue(item, StatusPedido.class));
            }
            cb_status.setModel(model);
            cb_status.setSelectedIndex(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar status: " + ex.getMessage());
        }
    }

    private void carregarHistorico() {
        try {
            pedidosModel.clear();
            Crud crud = new Crud("pedido");
            for (Map m : crud.getAll()) {
                Map tipo = (Map) m.get("tipoPedido");
                if (tipo == null || !"delivery".equalsIgnoreCase(String.valueOf(tipo.get("tipoPedido")).trim())) {
                    continue;
                }
                PedidoResumo resumo = new PedidoResumo(m);
                String status = resumo.getStatus().trim();
                if (!"Entregue".equalsIgnoreCase(status) && !"Cancelado".equalsIgnoreCase(status)) {
                    pedidosModel.addElement(resumo);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar o histórico: " + ex.getMessage());
        }
    }

    private void abrirPedido(PedidoResumo resumo) {
        try {
            Map p = resumo.dados;
            pedidoEmEdicaoId = resumo.getId();

            Map cliente = (Map) p.get("cliente");
            cb_clientes.setSelectedIndex(-1);
            if (cliente != null) {
                for (int i = 0; i < cb_clientes.getItemCount(); i++) {
                    Cliente c = (Cliente) cb_clientes.getItemAt(i);
                    if (c.getId().equals(((Number) cliente.get("id")).longValue())) {
                        cb_clientes.setSelectedIndex(i);
                        break;
                    }
                }
            }

            txt_endereco.setText(p.get("enderecoEntrega") != null ? p.get("enderecoEntrega").toString() : "");

            Map metodo = (Map) p.get("metodoPagamento");
            if (metodo != null) {
                cb_methodpayment.setSelectedItem(metodo.get("metodoPagamento"));
            }

            Map status = (Map) p.get("statusPedido");
            if (status != null) {
                for (int i = 0; i < cb_status.getItemCount(); i++) {
                    StatusPedido s = (StatusPedido) cb_status.getItemAt(i);
                    if (s.getId().equals(((Number) status.get("id")).longValue())) {
                        cb_status.setSelectedIndex(i);
                        break;
                    }
                }
            }

            tbModel.setRowCount(0);
            Crud crudItens = new Crud("itemPedido/pedido/" + pedidoEmEdicaoId);
            for (Map item : crudItens.getAll()) {
                Map produto = (Map) item.get("produto");
                int qtd = ((Number) item.get("quantidade")).intValue();
                double valor = ((Number) item.get("valor")).doubleValue();
                double desconto = ((Number) item.get("desconto")).doubleValue();
                tbModel.addRow(new Object[]{produto.get("descricao"), String.valueOf(qtd),
                        String.valueOf((valor + desconto) / qtd), String.valueOf(desconto),
                        String.valueOf(produto.get("id"))});
            }

            btn_addsell.setText("Atualizar Status");
            btn_addProduct.setEnabled(false);
            tb_products.setEnabled(false);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao abrir o pedido: " + ex.getMessage());
        }
    }

    private void atualizarStatus() {
        try {
            StatusPedido novoStatus = (StatusPedido) cb_status.getSelectedItem();
            if (novoStatus == null) {
                JOptionPane.showMessageDialog(null, "Selecione o novo status do pedido.");
                return;
            }
            int r = JOptionPane.showConfirmDialog(null,
                    "Alterar o pedido #" + pedidoEmEdicaoId + " para \"" + novoStatus.getStatus() + "\"?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            if (r != JOptionPane.YES_OPTION) {
                return;
            }
            Crud crud = new Crud("pedido/" + pedidoEmEdicaoId + "/status/" + novoStatus.getId());
            HttpResponse<String> resp = crud.put("");
            if (resp.statusCode() == 200 || resp.statusCode() == 204) {
                JOptionPane.showMessageDialog(null, "Status do pedido atualizado para \"" + novoStatus.getStatus() + "\"!");
                novoPedido();
                carregarHistorico();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar o pedido: " + resp.body());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
        }
    }

    private void novoPedido() {
        pedidoEmEdicaoId = null;
        lst_pedidos.clearSelection();
        btn_addsell.setText("Salvar Pedido");
        btn_addProduct.setEnabled(true);
        tb_products.setEnabled(true);
        tbModel.setRowCount(0);
        amountTotal = 0;
        amountNoDiscount = 0;
        txtarea_amount.setText("R$ 0.0");
        txt_endereco.setText("");
        cb_clientes.setSelectedIndex(-1);
        if (cb_status.getItemCount() > 0) {
            cb_status.setSelectedIndex(0);
        }
        pedido = new Pedido();
    }

    private static class PedidoResumo {
        final Map dados;
        PedidoResumo(Map dados) { this.dados = dados; }
        Long getId() { return ((Number) dados.get("id")).longValue(); }

        String getStatus() {
            Map status = (Map) dados.get("statusPedido");
            return status != null && status.get("status") != null ? status.get("status").toString() : "";
        }

        @Override
        public String toString() {
            Map cliente = (Map) dados.get("cliente");
            String nome = cliente != null
                    ? cliente.get("primeiroNome") + " " + cliente.get("ultimoNome")
                    : "Sem cliente";
            return "#" + dados.get("id") + " - " + nome + " (" + getStatus() + ")"; }
    }

    private void createUIComponents() {
    }
}
