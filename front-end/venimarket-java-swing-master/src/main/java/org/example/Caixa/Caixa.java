package org.example.Caixa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Menu;
import org.example.Dominios.Login;
import org.example.Servicos.CRUD.Crud;
import org.example.Tema.Tema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class Caixa extends JFrame {
    public JPanel panel;
    private JLabel lbl_status;
    private JLabel lbl_valor;
    private JTextField txt_valor;
    private JButton btn_acaoCaixa;
    private JButton btn_voltar;

    private Long caixaId;
    private Login login;

    public Caixa(Login login) throws IOException, InterruptedException {
        this.login = login;

        atualizarEstado();

        btn_acaoCaixa.addActionListener(e -> {
            try {
                if (caixaId == null) abrirCaixa(); else fecharCaixa();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
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

    private void atualizarEstado() {
        Map<String, Object> map = null;
        try {
            Crud crud = new Crud();
            crud.setEndpoint("caixa/aberto");
            map = crud.getBy();
        } catch (Exception ex) {
            map = null;
        }

        if (map != null && map.get("id") != null) {
            caixaId = ((Number) map.get("id")).longValue();
            lbl_status.setText("Caixa ABERTO");
            lbl_valor.setText("Valor contado no caixa:");
            btn_acaoCaixa.setText("Fechar caixa");
        } else {
            caixaId = null;
            lbl_status.setText("Caixa FECHADO");
            lbl_valor.setText("Valor inicial (troco):");
            btn_acaoCaixa.setText("Abrir caixa");
        }
        txt_valor.setText("");
    }

    private BigDecimal lerValor() {
        try {
            return new BigDecimal(txt_valor.getText().trim().replace(",", "."));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Digite um valor válido.");
            return null;
        }
    }

    private void abrirCaixa() throws IOException, InterruptedException {
        BigDecimal valor = lerValor();
        if (valor == null) return;

        Crud crud = new Crud();
        crud.setEndpoint("caixa/abrir");
        String json = "{\"loginId\":" + login.getId() + ",\"valorInicial\":" + valor + "}";
        var response = crud.post(json);

        if (response.statusCode() == 201) {
            JOptionPane.showMessageDialog(null, "Caixa aberto com sucesso!");
            atualizarEstado();
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao abrir o caixa.");
        }
    }

    private void fecharCaixa() throws IOException, InterruptedException {
        BigDecimal valor = lerValor();
        if (valor == null) return;

        int r = JOptionPane.showConfirmDialog(null, "Deseja fechar o caixa?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (r != JOptionPane.YES_OPTION) return;

        Crud crud = new Crud();
        crud.setEndpoint("caixa/" + caixaId + "/fechar");
        var response = crud.post("{\"valorContado\":" + valor + "}");

        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            Map<?, ?> caixa = mapper.readValue(response.body().toString(), Map.class);
            JOptionPane.showMessageDialog(null,
                    "Caixa fechado!\nEsperado: " + caixa.get("valorEsperado")
                            + "\nContado: " + caixa.get("valorContado")
                            + "\nDiferença: " + caixa.get("diferenca"));
            atualizarEstado();
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao fechar o caixa.");
        }
    }
}
