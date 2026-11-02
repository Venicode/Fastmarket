package org.example.ConsultaVendas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.MenuConsultas;
import org.example.Dominios.Login;
import org.example.Dominios.Pedido;
import org.example.Dominios.Produto;
import org.example.Servicos.CRUD.Crud;
import org.example.Tema.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ListaVendas extends JFrame {
    String[] columns = {"Data da Venda", "Tipo de Venda","Produto", "Quantidade", "Cliente", "Endereço de Entrega","Valor", "Status"};
    DefaultTableModel tbModel = new DefaultTableModel(columns, 0); // Initialize with column names
    public JTable tb_vendas;
    public JPanel painelVendas;
    private JButton btn_voltar;
    private Login login;

    public ListaVendas(Login login) throws IOException, InterruptedException {
        this.login = login;
        tb_vendas.setModel(tbModel);
        Crud crud = new Crud("itemPedido");
        List<Map> mapList = crud.getAll();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        for (Map i : mapList) {
            Map<String, Object> produtoMap = (Map<String, Object>) i.get("produto");
            Produto produto = mapper.convertValue(produtoMap, Produto.class);
            Map<String, Object> pedidoMap = (Map<String, Object>) i.get("pedido");
            Pedido pedido = mapper.convertValue(pedidoMap, Pedido.class);

            String nomeCliente = "";
            if (pedido.getCliente() != null) {
                nomeCliente = pedido.getCliente().getPrimeiroNome() + " " + pedido.getCliente().getUltimoNome();
            }
            String status = pedido.getStatusPedido() != null && pedido.getStatusPedido().getStatus() != null
                    ? pedido.getStatusPedido().getStatus() : "";
            String endereco = pedido.getEnderecoEntrega() != null ? pedido.getEnderecoEntrega() : "";
            String tipo = pedido.getTipoPedido() != null ? pedido.getTipoPedido().getTipoPedido() : "";

            Object[] data = {
                    pedido.getDataVenda() != null ? pedido.getDataVenda().toString() : "",
                    tipo,
                    produto != null ? produto.getDescricao() : "",
                    i.get("quantidade"),
                    nomeCliente,
                    endereco,
                    i.get("valor"),
                    status
            };
            tbModel.addRow(data);
        }
        btn_voltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MenuConsultas menu = new MenuConsultas(login);
                    Tema.aplicar(menu.panel);
                    menu.setContentPane(menu.panel);
                    menu.setTitle("Menu Consultas");
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
}