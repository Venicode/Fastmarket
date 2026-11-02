package org.example.AlterarSenhaLogin;

import org.example.Menu;
import org.example.Dominios.Login;
import org.example.Servicos.CRUD.Crud;
import org.example.Tema.Tema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.http.HttpResponse;

public class AlterarSenhaLogin extends JFrame{
    public JPanel panel;
    private JButton btn_voltar;
    private JTextField txt_senhaNova;
    private JLabel lbl_senha;
    private JButton btn_realizarCadastro;
    private JTextArea txtArea_nivelmensagem;
    private JTextField txt_senhaAntiga;
    private Login login;
    public AlterarSenhaLogin(Login login) throws IOException, InterruptedException {
        this.login = login;
        btn_realizarCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Crud crudLogin = new Crud();
                    if(txt_senhaAntiga.getText().equals(login.getSenha())){
                        int reply = JOptionPane.showConfirmDialog(null, "Deseja salvar as alterações?", "Confirmação", JOptionPane.YES_NO_OPTION);
                        if (reply != JOptionPane.YES_OPTION) return;
                        try {
                            crudLogin.setEndpoint("login/updateSenha/" + login.getId() + "/senha=" + txt_senhaNova.getText());
                            HttpResponse<String> responseLogin = crudLogin.patch("");
                            if (responseLogin.statusCode() == 204) {
                                JOptionPane.showMessageDialog(null, "Acesso alterado com sucesso!");
                            } else {
                                System.out.println(responseLogin.statusCode() + " -> " + responseLogin.body());
                                JOptionPane.showMessageDialog(null, "Erro ao alterar o acesso.");
                            }
                        } catch (IOException | InterruptedException ex) {
                            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
                        }
                    } else{
                        JOptionPane.showMessageDialog(null, "Senha anterior diferente da cadastrada.");
                    }
            }
        });
        btn_voltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = null;
                try {
                    menu = new Menu(login);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                Tema.aplicar(menu.panel);
                menu.setContentPane(menu.panel);
                menu.setTitle("Login");
                menu.setExtendedState(MAXIMIZED_BOTH);
                dispose();
                menu.setVisible(true);

            }
        });


    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
