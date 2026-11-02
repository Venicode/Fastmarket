package org.example.Cadastros.CadastroFornecedor;

import org.example.MenuCadastro.MenuCadastro;
import org.example.MenuConsultas;
import org.example.Dominios.Fornecedor;
import org.example.Dominios.Login;
import org.example.Servicos.CRUD.Crud;
import org.example.Tema.Tema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.http.HttpResponse;

public class CadastroFornecedor extends JFrame {
    public JPanel painelFornecedor;
    private JTextField txt_cnpj;
    private JButton btn_cadastrarFornecedor;
    private JTextField txt_razaoSocial;
    private JTextField txt_telefone;
    private JTextField txt_nomeFantasia;
    private JTextField txt_email;
    private JComboBox cb_ativo;
    private JButton btn_voltar;

    private Long fornecedorId;
    private boolean ativo = true;
    private Login login;
    public CadastroFornecedor(Login login) throws IOException, InterruptedException {
        this.login = login;
        cb_ativo.addItem("Sim");
        cb_ativo.addItem("Não");
        cb_ativo.setSelectedIndex(0);

        btn_cadastrarFornecedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Crud crudCliente = new Crud();
                crudCliente.setEndpoint("fornecedor");
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(fornecedorId);
                fornecedor.setAtivo(cb_ativo.getSelectedIndex() == 0);
                fornecedor.setCnpj(txt_cnpj.getText());
                fornecedor.setEmail(txt_email.getText());
                fornecedor.setTelefone(txt_telefone.getText());
                fornecedor.setRazaoSocial(txt_razaoSocial.getText());
                fornecedor.setNomeFantasia(txt_nomeFantasia.getText());
                if (fornecedor != null &&
                        !fornecedor.getCnpj().isBlank() &&
                        !fornecedor.getRazaoSocial().isBlank()  &&
                        !fornecedor.getNomeFantasia().isBlank() && (!fornecedor.getEmail().isBlank() || !fornecedor.getTelefone().isBlank())) {

                    String pergunta = fornecedorId == null ? "Deseja realizar o cadastro?" : "Deseja salvar as alterações?";
                    int reply = JOptionPane.showConfirmDialog(null, pergunta, "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        HttpResponse<String> responseLogin = null;
                        try {
                            if (fornecedorId == null) {
                                responseLogin = crudCliente.post(fornecedor.fromMap(fornecedor));
                            } else {
                                crudCliente.setEndpoint("fornecedor/" + fornecedorId);
                                responseLogin = crudCliente.put(fornecedor.fromMap(fornecedor));
                            }
                            if (responseLogin.statusCode() == 201 || responseLogin.statusCode() == 204|| responseLogin.statusCode() == 200) {
                                JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                                if (fornecedorId != null) dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro ao realizar o cadastro.");
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente.");

                }

            }
        });
        btn_voltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MenuCadastro menu = new MenuCadastro(login);
                    Tema.aplicar(menu.panel);
                    menu.setContentPane(menu.panel);
                    menu.setTitle("Menu Cadastro");
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

    public CadastroFornecedor(Fornecedor fornecedor) throws IOException, InterruptedException {
        this((Login) null);
        this.fornecedorId = fornecedor.getId();
        this.ativo = fornecedor.isAtivo();
        txt_cnpj.setText(fornecedor.getCnpj());
        txt_razaoSocial.setText(fornecedor.getRazaoSocial());
        txt_nomeFantasia.setText(fornecedor.getNomeFantasia());
        txt_email.setText(fornecedor.getEmail());
        txt_telefone.setText(fornecedor.getTelefone());
        cb_ativo.setSelectedIndex(fornecedor.isAtivo() == true? 0 : 1);
        btn_cadastrarFornecedor.setText("Salvar alterações");
        btn_voltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(fornecedorId == null){
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

}