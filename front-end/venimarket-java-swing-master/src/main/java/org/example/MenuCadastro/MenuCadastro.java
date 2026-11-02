package org.example.MenuCadastro;

import org.example.Cadastros.CadastroCliente.CadastroCliente;
import org.example.Cadastros.CadastroFornecedor.CadastroFornecedor;
import org.example.Cadastros.CadastroProduto.CadastroProduto;
import org.example.Menu;
import org.example.Dominios.Login;
import org.example.Tema.Tema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuCadastro extends JFrame{
    public JPanel panel;
    private JButton btn_cadastroProduto;
    private JButton btn_cadastroFornecedor;
    private JButton btn_voltar;
    private JButton btn_cadastroClientes;

    private final Login login;
    public MenuCadastro(Login login) throws IOException, InterruptedException {
        this.login = login;
        btn_cadastroProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CadastroProduto cadastroProduto = new CadastroProduto(login);
                    Tema.aplicar(cadastroProduto.painelProdutos);
                    cadastroProduto.setContentPane(cadastroProduto.painelProdutos);
                    cadastroProduto.setTitle("Cadastro de Produtos");
                    cadastroProduto.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    cadastroProduto.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        btn_cadastroClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CadastroCliente cadastroCliente = new CadastroCliente(login);
                    Tema.aplicar(cadastroCliente.painelClientes);
                    cadastroCliente.setContentPane(cadastroCliente.painelClientes);
                    cadastroCliente.setTitle("Cadastro de Clientes");
                    cadastroCliente.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    cadastroCliente.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        btn_cadastroFornecedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CadastroFornecedor cadastroFornecedor = new CadastroFornecedor(login);
                    Tema.aplicar(cadastroFornecedor.painelFornecedor);
                    cadastroFornecedor.setContentPane(cadastroFornecedor.painelFornecedor);
                    cadastroFornecedor.setTitle("Cadastro de Fornecedores");
                    cadastroFornecedor.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    cadastroFornecedor.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
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
}
