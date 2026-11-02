package org.example.ConsultaFornecedores;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Cadastros.CadastroFornecedor.CadastroFornecedor;
import org.example.MenuConsultas;
import org.example.Dominios.Fornecedor;
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

public class ListaFornecedores extends JFrame {
    String[] columns = {"ID", "CNPJ", "Razão Social", "Nome Fantasia", "E-mail", "Telefone", "Editar"};
    DefaultTableModel tbModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    public JTable tb_fornecedores;
    public JPanel painelFornecedores;
    private JButton btn_voltar;
    private List<Map> fornecedores;
    private Login login;

    public ListaFornecedores(Login login) throws IOException, InterruptedException {
        this.login = login;
        tb_fornecedores.setModel(tbModel);
        carregar();
        tb_fornecedores.removeColumn(tb_fornecedores.getColumnModel().getColumn(0));

        tb_fornecedores.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tb_fornecedores.rowAtPoint(e.getPoint());
                int col = tb_fornecedores.columnAtPoint(e.getPoint());
                if (row >= 0 && col == tb_fornecedores.getColumnCount() - 1) {
                    abrirEdicao(tb_fornecedores.convertRowIndexToModel(row));
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
        Crud crud = new Crud("fornecedor");
        fornecedores = crud.getAll();
        for (Map i : fornecedores) {
            Object[] data = {i.get("id"), i.get("cnpj"), i.get("razaoSocial"),
                    i.get("nomeFantasia"), i.get("email"), i.get("telefone"), "✏"};
            tbModel.addRow(data);
        }
    }
    private void abrirEdicao(int linhaModelo) {
        try {
            Fornecedor fornecedor = new ObjectMapper().convertValue(fornecedores.get(linhaModelo), Fornecedor.class);
            CadastroFornecedor tela = new CadastroFornecedor(fornecedor);
            Tema.aplicar(tela.painelFornecedor);
            tela.setContentPane(tela.painelFornecedor);
            tela.setTitle("Editar fornecedor");
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