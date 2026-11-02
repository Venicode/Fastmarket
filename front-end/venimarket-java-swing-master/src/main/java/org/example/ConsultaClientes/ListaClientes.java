package org.example.ConsultaClientes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Cadastros.CadastroCliente.CadastroCliente;
import org.example.MenuConsultas;
import org.example.Dominios.Cliente;
import org.example.Dominios.Login;
import org.example.Servicos.CRUD.Crud;
import org.example.Tema.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ListaClientes extends JFrame {
    String[] columns = {"ID", "CPF", "Nome", "Sobrenome", "E-mail", "Telefone", "Editar"};
    DefaultTableModel tbModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    public JTable tb_clientes;
    public JPanel painelClientes;
    private JButton btn_voltar;
    private List<Map> clientes;
    private Login login;

    public ListaClientes(Login login) throws IOException, InterruptedException {
        this.login = login;
        tb_clientes.setModel(tbModel);
        carregar();

        // Esconde a coluna do ID (continua no model)
        tb_clientes.removeColumn(tb_clientes.getColumnModel().getColumn(0));

        tb_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tb_clientes.rowAtPoint(e.getPoint());
                int col = tb_clientes.columnAtPoint(e.getPoint());
                // Após esconder o ID, "Editar" é a última coluna visível
                if (row >= 0 && col == tb_clientes.getColumnCount() - 1) {
                    abrirEdicao(tb_clientes.convertRowIndexToModel(row));
                }
            }
        });
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

    private void carregar() throws IOException, InterruptedException {
        tbModel.setRowCount(0);
        Crud crud = new Crud("cliente");
        clientes = crud.getAll();
        for (Map i : clientes) {
            tbModel.addRow(new Object[]{
                    i.get("id"), i.get("cpf"), i.get("primeiroNome"),
                    i.get("ultimoNome"), i.get("email"), i.get("telefone"), "✏"
            });
        }
    }

    private void abrirEdicao(int linhaModelo) {
        try {
            Cliente cliente = new ObjectMapper().convertValue(clientes.get(linhaModelo), Cliente.class);
            CadastroCliente tela = new CadastroCliente(cliente);
            Tema.aplicar(tela.painelClientes);
            tela.setContentPane(tela.painelClientes);
            tela.setTitle("Editar cliente");
            tela.setSize(500, 400);
            tela.setLocationRelativeTo(null);
            tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tela.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    try { carregar(); } catch (Exception ex) { /* ignora */ }
                }
            });
            tela.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir edição: " + ex.getMessage());
        }
    }
}