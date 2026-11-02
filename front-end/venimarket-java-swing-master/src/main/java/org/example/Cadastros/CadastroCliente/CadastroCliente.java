package org.example.Cadastros.CadastroCliente;

import org.example.MenuCadastro.MenuCadastro;
import org.example.MenuConsultas;
import org.example.Dominios.*;
import org.example.Servicos.CRUD.Crud;
import org.example.Tema.Tema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.http.HttpResponse;

public class CadastroCliente extends JFrame {
    public JPanel painelClientes;
    private JTextField txt_cpf;
    private JButton btn_cadastrarCliente;
    private JTextField txt_nome;
    private JTextField txt_telefone;
    private JTextField txt_sobrenome;
    private JTextField txt_email;
    private JComboBox cb_ativo;
    private JButton btn_voltar;
    private Long clienteId;
    private Login login;

        public CadastroCliente(Login login) throws IOException, InterruptedException {
            this.login = login;
            cb_ativo.addItem("Sim");
            cb_ativo.addItem("Não");
            cb_ativo.setSelectedIndex(0);

            btn_cadastrarCliente.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Crud crudCliente = new Crud();
                    crudCliente.setEndpoint("cliente");
                    Cliente cliente = new Cliente();
                    cliente.setId(clienteId);
                    cliente.setCpf(txt_cpf.getText());
                    cliente.setEmail(txt_email.getText());
                    cliente.setTelefone(txt_telefone.getText());
                    cliente.setPrimeiroNome(txt_nome.getText());
                    cliente.setUltimoNome(txt_sobrenome.getText());
                    cliente.setAtivo(cb_ativo.getSelectedIndex() == 0);
                    if (cliente != null &&
                            !cliente.getCpf().isBlank() &&
                            !cliente.getPrimeiroNome().isBlank()  &&
                            !cliente.getUltimoNome().isBlank() && (!cliente.getEmail().isBlank() || !cliente.getTelefone().isBlank())) {


                        String pergunta = clienteId == null ? "Deseja realizar o cadastro?" : "Deseja salvar as alterações?";
                        int reply = JOptionPane.showConfirmDialog(null, pergunta, "Confirmação", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            HttpResponse<String> responseLogin = null;
                            try {
                                if (clienteId == null) {
                                    responseLogin = crudCliente.post(cliente.fromMap(cliente));
                                } else {
                                    crudCliente.setEndpoint("cliente/" + clienteId);
                                    responseLogin = crudCliente.put(cliente.fromMap(cliente));
                                }
                                if (responseLogin.statusCode() == 201 || responseLogin.statusCode() == 204 || responseLogin.statusCode() == 200) {
                                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                                    if (clienteId != null) dispose();
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
                        if(clienteId == null){
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
        public CadastroCliente(Cliente cliente) throws IOException, InterruptedException {
            this((Login) null);
            this.clienteId = cliente.getId();
            txt_cpf.setText(cliente.getCpf());
            txt_nome.setText(cliente.getPrimeiroNome());
            txt_sobrenome.setText(cliente.getUltimoNome());
            txt_email.setText(cliente.getEmail());
            txt_telefone.setText(cliente.getTelefone());
            cb_ativo.setSelectedIndex(cliente.isAtivo() == true? 0 : 1);
            btn_cadastrarCliente.setText("Salvar alterações");
        }

    }
