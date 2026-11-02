package org.example.AlterarAcessoLogin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Menu;
import org.example.Dominios.Acesso;
import org.example.Dominios.Login;
import org.example.Servicos.CRUD.Crud;
import org.example.Tema.Tema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class AlterarAcessoLogin extends JFrame{
    public JPanel panel;
    private JButton btn_alterarAcesso;
    private JComboBox cb_usuarios;
    private JComboBox cb_acesso;
    private JButton btn_voltar;
    private Login login;

    public AlterarAcessoLogin(Login login) throws IOException, InterruptedException {
        this.login = login;
        carregarAcessos();
        carregarUsuarios();

        btn_alterarAcesso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login loginSelecionado = (Login) cb_usuarios.getSelectedItem();
                Acesso acessoSelecionado = (Acesso) cb_acesso.getSelectedItem();
                if (loginSelecionado == null || acessoSelecionado == null) {
                    JOptionPane.showMessageDialog(null, "Selecione o usuário e o nível de acesso.");
                    return;
                }
                int reply = JOptionPane.showConfirmDialog(null, "Deseja salvar as alterações?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (reply != JOptionPane.YES_OPTION) return;
                try {
                    Crud crudLogin = new Crud("login/updateAcesso/" + loginSelecionado.getId() + "/acesso=" + acessoSelecionado.getId());
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
            }
        });
        btn_voltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Menu menu = new Menu(login);
                    Tema.aplicar(menu.panel);
                    menu.setContentPane(menu.panel);
                    menu.setTitle("Menu");
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
    private void carregarAcessos() {
        try {
            Crud crud = new Crud();
            crud.setEndpoint("acesso");
            List<Map> lista = crud.getAll();

            ObjectMapper mapper = new ObjectMapper();
            DefaultComboBoxModel<Acesso> model = new DefaultComboBoxModel<>();
            for (Map<String, Object> item : lista) {
                model.addElement(mapper.convertValue(item, Acesso.class));
            }
            cb_acesso.setModel(model);
            cb_acesso.setSelectedIndex(-1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar os acessos: " + ex.getMessage());
        }
    }
    private void carregarUsuarios() {
        try {
            Crud crud = new Crud();
            crud.setEndpoint("login");
            List<Map> lista = crud.getAll();

            ObjectMapper mapper = new ObjectMapper();
            DefaultComboBoxModel<Login> model = new DefaultComboBoxModel<>();
            for (Map<String, Object> item : lista) {
                model.addElement(mapper.convertValue(item, Login.class));
            }
            cb_usuarios.setModel(model);
            cb_usuarios.setSelectedIndex(-1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar os usuários: " + ex.getMessage());
        }
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
