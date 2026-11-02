package org.example.Cadastros.CadastroLogin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Login.Login;
import org.example.Dominios.Acesso;
import org.example.Servicos.CRUD.Crud;
import org.example.Tema.Tema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

public class CadastroLoginTela extends JFrame{
    public JPanel panel;
    private JButton btnLogin;
    private JTextField txt_matricula;
    private JTextField txt_senha;
    private JLabel lbl_matricula;
    private JLabel lbl_senha;
    private JButton btn_realizarCadastro;
    private JTextArea txtArea_nivelmensagem;
    private JTextField txt_nome;
    private JTextField txtName;
    org.example.Dominios.Login novoLogin = new org.example.Dominios.Login();
    Acesso acesso = new Acesso();
    Login login = new Login();

    public CadastroLoginTela() throws IOException, InterruptedException {
        btn_realizarCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Crud crudLogin = new Crud();
                crudLogin.setEndpoint("login");

                Crud crudAcesso = new Crud();
                crudAcesso.setEndpoint("acesso/2");
                try {
                    Map<String, Object> map = crudAcesso.getBy();
                    ObjectMapper mapper = new ObjectMapper();
                    acesso = mapper.convertValue(map, Acesso.class);
                    novoLogin.setAcesso(acesso);
                    novoLogin.setNome(txt_nome.getText());
                    if (!txt_matricula.getText().isBlank()) {
                        novoLogin.setMatricula(Integer.parseInt(txt_matricula.getText()));
                    } else{
                        JOptionPane.showMessageDialog(null, "Preencha a matrícula corretamente.");
                    }
                    novoLogin.setAtivo(true);
                    novoLogin.setSenha(txt_senha.getText());

                    if (novoLogin != null &&
                            novoLogin.getAcesso() != null &&
                            novoLogin.getNome() != null && !novoLogin.getNome().isBlank() &&
                            novoLogin.getMatricula() != 0 &&
                            novoLogin.isAtivo() == true &&
                            novoLogin.getSenha() != null && !novoLogin.getSenha().isBlank()) {

                         int reply = JOptionPane.showConfirmDialog(null, "Deseja realizar o cadastro?", "Confirmação", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            var responseLogin = crudLogin.post(novoLogin.fromMap(novoLogin));
                            if (responseLogin.statusCode() == 201) {
                                JOptionPane.showMessageDialog(null, "Login criado com sucesso!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro ao criar o login");
                            }
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "Preencha todos os dados corretamente.");

                    }


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tema.aplicar(login.panel);
                login.setContentPane(login.panel);
                login.setTitle("Login");
                login.setExtendedState(MAXIMIZED_BOTH);
                dispose();
                login.setVisible(true);

            }
        });


    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
