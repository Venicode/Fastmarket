package org.example.Cadastros.CadastroProduto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.MenuCadastro.MenuCadastro;
import org.example.MenuConsultas;
import org.example.Dominios.CategoriaProduto;
import org.example.Dominios.Fornecedor;
import org.example.Dominios.Login;
import org.example.Dominios.Produto;
import org.example.Servicos.CRUD.Crud;
import org.example.Tema.Tema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class CadastroProduto extends JFrame {
    public JPanel painelProdutos;
    private JTextField txt_descricao;
    private JTextField txt_precoCusto;
    private JLabel lbl_matricula;
    private JComboBox cb_ativo;
    private JButton btn_cadastroProduto;
    private JComboBox cb_categoria;
    private JTextField txt_codBarras;
    private JComboBox cb_fornecedor;
    private JTextField txt_quantidade;
    private JTextField txt_precoVenda;
    private JTextField txt_marca;
    private JButton btn_voltar;

    private Long produtoId;
    private Login login;

    public CadastroProduto(Login login) throws IOException, InterruptedException {
        this.login = login;
        carregarFornecedores();
        carregarCategorias();
        cb_ativo.addItem("Sim");
        cb_ativo.addItem("Não");
        cb_ativo.setSelectedIndex(0);

        btn_cadastroProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Crud crudProduto = new Crud();
                crudProduto.setEndpoint("produto");
                Produto novoProduto = new Produto();
                novoProduto.setId(produtoId);
                novoProduto.setAtivo(cb_ativo.getSelectedIndex() == 0);
                novoProduto.setMarca(txt_marca.getText());
                novoProduto.setDescricao(txt_descricao.getText());
                Fornecedor fornecedor = (Fornecedor) cb_fornecedor.getSelectedItem();
                novoProduto.setFornecedor(fornecedor);
                if (!txt_codBarras.getText().isBlank()) {
                    novoProduto.setCodigoBarras(Long.parseLong(txt_codBarras.getText()));
                }
                if (!txt_precoCusto.getText().isBlank()) {
                    novoProduto.setPrecoCusto(Double.parseDouble(txt_precoCusto.getText()));
                }
                if (!txt_precoVenda.getText().isBlank()) {
                    novoProduto.setPrecoVenda(Double.parseDouble(txt_precoVenda.getText()));
                }
                if (!txt_quantidade.getText().isBlank()) {
                    novoProduto.setQuantidade(Integer.parseInt(txt_quantidade.getText()));
                }
                CategoriaProduto categoriaProduto = (CategoriaProduto) cb_categoria.getSelectedItem();
                novoProduto.setCategoriaProduto(categoriaProduto);
                if (novoProduto != null &&
                        novoProduto.getDescricao() != null && !novoProduto.getDescricao().isBlank() &&
                        novoProduto.getPrecoCusto() != 0 &&
                        novoProduto.getPrecoVenda() != 0) {


                    String pergunta = produtoId == null ? "Deseja realizar o cadastro?" : "Deseja salvar as alterações?";
                    int reply = JOptionPane.showConfirmDialog(null, pergunta, "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        HttpResponse<String> responseLogin = null;
                        try {

                            if (produtoId == null) {
                                responseLogin = crudProduto.post(novoProduto.fromMap(novoProduto));
                            } else {
                                crudProduto.setEndpoint("produto/" + produtoId);
                                responseLogin = crudProduto.put(novoProduto.fromMap(novoProduto));
                            }
                            System.out.println(responseLogin.statusCode() + " -> " + responseLogin.body());
                            System.out.println("JSON enviado: " + novoProduto.fromMap(novoProduto));
                            if (responseLogin.statusCode() == 201 || responseLogin.statusCode() == 200
                                    || responseLogin.statusCode() == 204) {
                                JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                                if (produtoId != null) dispose(); } else {
                                JOptionPane.showMessageDialog(null, "Erro ao realizar o cadastro.");
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios corretamente");

                }

            }
        });
        btn_voltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(produtoId == null){
                        MenuCadastro menu = new MenuCadastro(login);
                        Tema.aplicar(menu.panel);
                        menu.setContentPane(menu.panel);
                        menu.setTitle("Menu Cadastro");
                        menu.setExtendedState(MAXIMIZED_BOTH);
                        dispose();
                        menu.setVisible(true);}
                    else{
                        MenuConsultas menu = new MenuConsultas(login);
                        Tema.aplicar(menu.panel);
                        menu.setContentPane(menu.panel);
                        menu.setTitle("Menu Consulta");
                        menu.setExtendedState(MAXIMIZED_BOTH);
                        dispose();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public CadastroProduto(Produto produto) throws IOException, InterruptedException {
        this((Login) null);
        this.produtoId = produto.getId();
        txt_descricao.setText(produto.getDescricao());
        txt_marca.setText(produto.getMarca());
        txt_precoCusto.setText(String.valueOf(produto.getPrecoCusto()));
        txt_precoVenda.setText(String.valueOf(produto.getPrecoVenda()));
        txt_quantidade.setText(String.valueOf(produto.getQuantidade()));
        if (produto.getCodigoBarras() != null) {
            txt_codBarras.setText(String.valueOf(produto.getCodigoBarras()));
        }
        cb_ativo.setSelectedIndex(produto.isAtivo() ? 0 : 1);
        selecionarFornecedor(produto.getFornecedor());
        selecionarCategoria(produto.getCategoriaProduto());
        btn_cadastroProduto.setText("Salvar alterações");
    }

    private void selecionarFornecedor(Fornecedor alvo) {
        if (alvo == null) return;
        for (int i = 0; i < cb_fornecedor.getItemCount(); i++) {
            Fornecedor f = (Fornecedor) cb_fornecedor.getItemAt(i);
            if (f.getId().equals(alvo.getId())) {
                cb_fornecedor.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selecionarCategoria(CategoriaProduto alvo) {
        if (alvo == null) return;
        for (int i = 0; i < cb_categoria.getItemCount(); i++) {
            CategoriaProduto c = (CategoriaProduto) cb_categoria.getItemAt(i);
            if (c.getId().equals(alvo.getId())) {
                cb_categoria.setSelectedIndex(i);
                return;
            }
        }
    }

    private void carregarFornecedores() {
        try {
            Crud crud = new Crud();
            crud.setEndpoint("fornecedor");
            List<Map> lista = crud.getAll();

            ObjectMapper mapper = new ObjectMapper();
            DefaultComboBoxModel<Fornecedor> model = new DefaultComboBoxModel<>();
            for (Map<String, Object> item : lista) {
                model.addElement(mapper.convertValue(item, Fornecedor.class));
            }
            cb_fornecedor.setModel(model);
            cb_fornecedor.setSelectedIndex(-1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar fornecedores: " + ex.getMessage());
        }
    }
    private void carregarCategorias() {
        try {
            Crud crud = new Crud();
            crud.setEndpoint("categoriaProduto");
            List<Map> lista = crud.getAll();

            ObjectMapper mapper = new ObjectMapper();
            DefaultComboBoxModel<CategoriaProduto> model = new DefaultComboBoxModel<>();
            for (Map<String, Object> item : lista) {
                model.addElement(mapper.convertValue(item, CategoriaProduto.class));
            }
            cb_categoria.setModel(model);
            cb_categoria.setSelectedIndex(-1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar as categorias: " + ex.getMessage());
        }
    }
}