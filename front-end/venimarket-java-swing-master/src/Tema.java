package org.example;

import javax.swing.*;
import java.awt.*;

public class Tema {
    public static final Color FUNDO = Color.decode("#FFFDFD");
    public static final Color AMARELO = Color.decode("#FFC931");
    public static final Color TEXTO_BOTAO = Color.decode("#FFFDFD");
    public static final Color TEXTO = Color.decode("#2B2B2B");

    public static void aplicar(Component c) {
        if (c instanceof JButton b) {
            b.setBackground(AMARELO);
            b.setForeground(TEXTO_BOTAO);
            b.setOpaque(true);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
        } else if (c instanceof JLabel l) {
            if ("FASTMARKET".equalsIgnoreCase(l.getText())) l.setForeground(AMARELO);
            else l.setForeground(TEXTO);
        } else if (c instanceof JPanel || c instanceof JViewport || c instanceof JScrollPane) {
            c.setBackground(FUNDO);
        } else if (c instanceof JComboBox || c instanceof JTextField || c instanceof JTextArea
                || c instanceof JTable || c instanceof JList) {
            c.setForeground(TEXTO);
        }
        if (c instanceof Container ct) {
            for (Component filho : ct.getComponents()) aplicar(filho);
        }
    }
}