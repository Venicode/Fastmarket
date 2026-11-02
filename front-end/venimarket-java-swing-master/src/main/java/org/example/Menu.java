package org.example;

import org.example.AlterarAcessoLogin.AlterarAcessoLogin;
import org.example.AlterarSenhaLogin.AlterarSenhaLogin;
import org.example.Cadastros.CadastroLogin.CadastroLoginTela;
import org.example.Caixa.Caixa;
import org.example.Login.Login;
import org.example.MenuCadastro.MenuCadastro;
import org.example.VendaDelivery.VendaDelivery;
import org.example.Tema.Tema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Menu extends JFrame{
    public JPanel panel;
    private JButton btnRealizarVendaPDV;
    private JButton btnRealizarVendaDelivery;
    private JButton btnConsultas;
    private JButton btnCadastros;
    private JButton btnCaixa;
    private JButton btnSair;
    private JButton btn_alterarAcesso;
    private JButton btn_alterarSenha;
    private JButton btn_cadastrarLogin;
    private JTextField txtName;
    private JButton btn_click;
    private final org.example.Dominios.Login login;

    public Menu(org.example.Dominios.Login login) throws IOException, InterruptedException {
        this.login = login;
        if(login.getAcesso().getId() == 1){
            btn_alterarAcesso.setVisible(true);
            btn_cadastrarLogin.setVisible(true);
        } else{
            btn_alterarAcesso.setVisible(false);
            btn_cadastrarLogin.setVisible(false);
        }
        btnConsultas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuConsultas menuConsultas = null;
                try {
                    menuConsultas = new MenuConsultas(login);
                    Tema.aplicar(menuConsultas.panel);
                    menuConsultas.setContentPane(menuConsultas.panel);
                    menuConsultas.setTitle("Consultas");
                    menuConsultas.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    menuConsultas.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        btnCadastros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MenuCadastro menuCadastro = new MenuCadastro(login);
                    Tema.aplicar(menuCadastro.panel);
                    menuCadastro.setContentPane(menuCadastro.panel);
                    menuCadastro.setTitle("Menu de Cadastros");
                    menuCadastro.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    menuCadastro.setVisible(true);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = null;
                try {
                    login = new Login();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                Tema.aplicar(login.panel);
                login.setContentPane(login.panel);
                login.setTitle("Consultas");
                login.setExtendedState(MAXIMIZED_BOTH);
                dispose();
                login.setVisible(true);

            }
        });
        btnRealizarVendaPDV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Venda venda = null;
                try {
                    venda = new Venda(login);
                    Tema.aplicar(venda.panel1);
                    venda.setContentPane(venda.panel1);
                    venda.setTitle("Realizar venda PDV");
                    venda.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    venda.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnRealizarVendaDelivery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VendaDelivery venda = null;
                try {
                    venda = new VendaDelivery(login);
                    Tema.aplicar(venda.panel1);
                    venda.setContentPane(venda.panel1);
                    venda.setTitle("Realizar venda Delivery");
                    venda.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    venda.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btn_alterarAcesso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AlterarAcessoLogin alterarAcesso = null;
                try {
                    alterarAcesso = new AlterarAcessoLogin(login);
                    Tema.aplicar(alterarAcesso.panel);
                    alterarAcesso.setContentPane(alterarAcesso.panel);
                    alterarAcesso.setTitle("Alterar Acesso");
                    alterarAcesso.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    alterarAcesso.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btn_alterarSenha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AlterarSenhaLogin alterarSenhaLogin = null;
                try {
                    alterarSenhaLogin = new AlterarSenhaLogin(login);
                    Tema.aplicar(alterarSenhaLogin.panel);
                    alterarSenhaLogin.setContentPane(alterarSenhaLogin.panel);
                    alterarSenhaLogin.setTitle("Alterar Acesso");
                    alterarSenhaLogin.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    alterarSenhaLogin.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btn_cadastrarLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CadastroLoginTela cadastroLoginTela = new CadastroLoginTela();
                    Tema.aplicar(cadastroLoginTela.panel);
                    cadastroLoginTela.setContentPane(cadastroLoginTela.panel);
                    cadastroLoginTela.setTitle("Cadastrar Login");
                    cadastroLoginTela.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    cadastroLoginTela.setVisible(true);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        btnCaixa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Caixa caixa = new Caixa(login);
                    Tema.aplicar(caixa.panel);
                    caixa.setContentPane(caixa.panel);
                    caixa.setTitle("Realizar venda PDV");
                    caixa.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    caixa.setVisible(true);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
