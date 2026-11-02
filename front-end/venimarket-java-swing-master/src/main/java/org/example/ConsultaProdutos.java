package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Cadastros.CadastroProduto.CadastroProduto;
import org.example.Dominios.Login;
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

public class ConsultaProdutos extends JFrame{
    String[] columns = {"ID", "Ativo","Descrição", "Quantidade Disponível", "Valor Unitário", "Valor de Custo","Código de Barras","Fornecedor", "Marca", "Categoria","Editar"};
    DefaultTableModel tbModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    public JPanel painelFornecedores;
    private JTable tb_produtos;
    private JButton btn_voltar;
    private List<Map> produtos;
    private Login login;

    public ConsultaProdutos(Login login) throws IOException, InterruptedException {
        this.login = login;
        tb_produtos.setModel(tbModel);
        carregar();

        tb_produtos.removeColumn(tb_produtos.getColumnModel().getColumn(0));

        tb_produtos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tb_produtos.rowAtPoint(e.getPoint());
                int col = tb_produtos.columnAtPoint(e.getPoint());
                if (row >= 0 && col == tb_produtos.getColumnCount() - 1) {
                    abrirEdicao(tb_produtos.convertRowIndexToModel(row));
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
                    menu.setTitle("Menu Consulta");
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
        Crud crud = new Crud("produto");
        produtos = crud.getAll();
        for (Map i : produtos) {
            Map fornecedor = (Map) i.get("fornecedor");
            Map categoria = (Map) i.get("categoriaProduto");
            String produtoAtivo;
            if((boolean) i.get("ativo")){
                produtoAtivo = "Sim";
            } else{
                produtoAtivo = "Não";
            }
            String nomeFornecedor = fornecedor != null && fornecedor.get("nomeFantasia") != null
                    ? fornecedor.get("nomeFantasia").toString() : "";
            Object[] data = {i.get("id"), produtoAtivo,i.get("descricao"), i.get("quantidade"),
                    i.get("precoVenda"), i.get("precoCusto"), i.get("codigoBarras"), nomeFornecedor,i.get("marca"), categoria.get("categoriaProduto"), "✏"};
            tbModel.addRow(data);
        }
    }

    private void abrirEdicao(int linhaModelo) {
        try {
            Produto produto = new ObjectMapper().convertValue(produtos.get(linhaModelo), Produto.class);
            CadastroProduto tela = new CadastroProduto(produto);
            Tema.aplicar(tela.painelProdutos);
            tela.setContentPane(tela.painelProdutos);
            tela.setTitle("Editar produto");
            tela.setSize(600, 500);
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