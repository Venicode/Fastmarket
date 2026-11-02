package org.example.Login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Cadastros.CadastroLogin.CadastroLoginTela;
import org.example.Menu;
import org.example.Tema.Tema;
import org.example.Servicos.CRUD.Crud;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

public class Login extends JFrame{
    public JPanel panel;
    private JButton btnLogin;
    private JTextField txt_matricula;
    private JTextField txt_senha;
    private JLabel lbl_matricula;
    private JLabel lbl_senha;
    private JButton btn_telaCadastro;
    private JLabel lbl_status;
    private JTextArea realizeOSeuCadastroTextArea;
    private JTextField txtName;
    org.example.Dominios.Login novoLogin = new org.example.Dominios.Login();



    public static void main(String[] args) throws IOException, InterruptedException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
       Login login = new Login();
        Tema.aplicar(login.panel);
        login.setContentPane(login.panel);
        login.setTitle("Login");
        login.setExtendedState(MAXIMIZED_BOTH);
        login.setVisible(true);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public Login() throws IOException, InterruptedException {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txt_matricula.getText().isBlank() && !txt_senha.getText().isBlank()) {
                    Crud crud = new Crud();

                    crud.setEndpoint("login/getByMatricula/" + txt_matricula.getText());
                    try {
                        Map<String, Object> map = crud.getBy();
                        ObjectMapper mapper = new ObjectMapper();
                        novoLogin = mapper.convertValue(map, org.example.Dominios.Login.class);
                        System.out.println(novoLogin.getSenha());
                        if (novoLogin.getSenha().equals(txt_senha.getText().toString())) {
                            Menu menu = new Menu(novoLogin);
                            Tema.aplicar(menu.panel);
                            menu.setContentPane(menu.panel);
                            menu.setTitle("Menu");
                            menu.setExtendedState(MAXIMIZED_BOTH);
                            dispose();
                            menu.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos.");
                        }

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Preencha os campos corretamente.");
                }
            }
        });
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
