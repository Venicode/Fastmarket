package org.example.Estoque;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.MenuConsultas;
import org.example.Dominios.Login;
import org.example.Dominios.Produto;
import org.example.Servicos.CRUD.Crud;
import org.example.Tema.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Estoque extends JFrame {
    String[] columns = {"Produto", "Quantidade",};
    DefaultTableModel tbModel = new DefaultTableModel(columns, 0); // Initialize with column names
    public JTable tb_estoque;
    public JPanel painelEstoque;
    private JButton btn_voltar;
    private Login login;

    public Estoque(Login login) throws IOException, InterruptedException {
        this.login = login;
        tb_estoque.setModel(tbModel);
        Crud crud = new Crud("estoque");
        List<Map> mapList = crud.getAll();
        ObjectMapper mapper = new ObjectMapper();
        for (Map i : mapList) {
            Map<String, Object> produtoMap = (Map<String, Object>) i.get("produto");
            Produto produto = mapper.convertValue(produtoMap, Produto.class);
            Object[] data = {produto.getDescricao(), i.get("quantidade").toString(), "Selecionar"};
            tbModel.addRow(data);
        }
        btn_voltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MenuConsultas menu = new MenuConsultas(login);
                    Tema.aplicar(menu.panel);
                    menu.setContentPane(menu.panel);
                    menu.setTitle("Menu Consulta");
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