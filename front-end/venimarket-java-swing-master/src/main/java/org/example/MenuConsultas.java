package org.example;

import org.example.Estoque.Estoque;
import org.example.ConsultaClientes.ListaClientes;
import org.example.ConsultaFornecedores.ListaFornecedores;
import org.example.ConsultaVendas.ListaVendas;
import org.example.Dominios.Login;
import org.example.Tema.Tema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuConsultas extends JFrame{
    public JPanel panel;
    private JButton btn_produtos;
    private JButton btn_fornecedores;
    private JButton btn_vendas;
    private JButton btn_estoque;
    private JButton btn_voltar;
    private JButton btn_clientes;




    private final Login login;
    public MenuConsultas(Login login) throws IOException, InterruptedException {
        this.login = login;
        btn_produtos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConsultaProdutos consultaProdutos = null;
                try {
                    consultaProdutos = new ConsultaProdutos(login);
                    Tema.aplicar(consultaProdutos.painelFornecedores);
                    consultaProdutos.setContentPane(consultaProdutos.painelFornecedores);
                    consultaProdutos.setTitle("Lista de Produtos");
                    consultaProdutos.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    consultaProdutos.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        btn_fornecedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaFornecedores listaFornecedores = null;
                try {
                    listaFornecedores = new ListaFornecedores(login);
                    Tema.aplicar(listaFornecedores.painelFornecedores);
                    listaFornecedores.setContentPane(listaFornecedores.painelFornecedores);
                    listaFornecedores.setTitle("Lista de Fornecedores");
                    listaFornecedores.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    listaFornecedores.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        btn_clientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaClientes listaClientes = null;
                try {
                    listaClientes = new ListaClientes(login);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                Tema.aplicar(listaClientes.painelClientes);
                listaClientes.setContentPane(listaClientes.painelClientes);
                listaClientes.setTitle("Lista de Clientes");
                listaClientes.setExtendedState(MAXIMIZED_BOTH);
                dispose();
                listaClientes.setVisible(true);

            }
        });
        btn_estoque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Estoque estoque = null;
                try {
                    estoque = new Estoque(login);
                    Tema.aplicar(estoque.painelEstoque);
                    estoque.setContentPane(estoque.painelEstoque);
                    estoque.setTitle("Estoque");
                    estoque.setExtendedState(MAXIMIZED_BOTH);
                    dispose();
                    estoque.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        btn_vendas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final ListaVendas listaVendas;
                try {
                    listaVendas = new ListaVendas(login);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                Tema.aplicar(listaVendas.painelVendas);
                listaVendas.setContentPane(listaVendas.painelVendas);
                listaVendas.setTitle("Lista de Vendas");
                listaVendas.setExtendedState(MAXIMIZED_BOTH);
                dispose();
                listaVendas.setVisible(true);
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
