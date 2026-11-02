package org.example.Tema;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Tema {
    public static final Color FUNDO = Color.decode("#FFFDFD");
    public static final Color AMARELO = Color.decode("#FFC931");
    public static final Color TEXTO_BOTAO = Color.decode("#FFFDFD");
    public static final Color TEXTO = Color.decode("#2B2B2B");

    public static final Color CAMPO = Color.WHITE;
    public static final Color BORDA = Color.decode("#DADADA");
    public static final Color BORDA_FOCO = AMARELO;
    public static final int RAIO = 14;

    private static final String APLICADO = "tema.aplicado";

    public static void aplicar(Component c) {
        if (c instanceof JButton b) {
            b.setBackground(AMARELO);
            b.setForeground(TEXTO_BOTAO);
            estilizarBotao(b);
        } else if (c instanceof JLabel l) {
            if ("FASTMARKET".equalsIgnoreCase(l.getText())) l.setForeground(AMARELO);
            else l.setForeground(TEXTO);
        } else if (c instanceof JPanel || c instanceof JViewport || c instanceof JScrollPane) {
            c.setBackground(FUNDO);
        } else if (c instanceof JTextField t) {
            t.setForeground(TEXTO);
            estilizarCampo(t);
        } else if (c instanceof JComboBox<?> cb) {
            cb.setForeground(TEXTO);
            estilizarCombo(cb);
            return;
        } else if (c instanceof JTextArea || c instanceof JTable || c instanceof JList) {
            c.setForeground(TEXTO);
        }
        if (c instanceof Container ct) {
            for (Component filho : ct.getComponents()) aplicar(filho);
        }
    }
    private static void estilizarBotao(JButton b) {
        b.setUI(new BotaoArredondadoUI());
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setRolloverEnabled(true);
        b.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private static class BotaoArredondadoUI extends BasicButtonUI {
        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            ButtonModel m = b.getModel();
            Color cor = b.getBackground();
            if (!m.isEnabled()) cor = misturar(cor, Color.WHITE, 0.5f);
            else if (m.isPressed()) cor = cor.darker();
            else if (m.isRollover()) cor = misturar(cor, Color.BLACK, 0.08f);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(cor);
            g2.fill(new RoundRectangle2D.Float(0, 0, c.getWidth(), c.getHeight(), RAIO, RAIO));
            g2.dispose();
            super.paint(g, c);
        }

        @Override
        protected void paintText(Graphics g, AbstractButton b, Rectangle r, String text) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Color original = b.getForeground();
            if (!b.isEnabled()) {
                g.setColor(misturar(original, Color.GRAY, 0.4f));
                FontMetrics fm = g.getFontMetrics();
                g.drawString(text, r.x, r.y + fm.getAscent());
            } else {
                super.paintText(g, b, r, text);
            }
        }
    }

    private static void estilizarCampo(JTextField t) {
        t.setBackground(CAMPO);
        t.setCaretColor(TEXTO);
        t.setBorder(new BordaArredondada(new Insets(6, 12, 6, 12)));
        repintarNoFoco(t);
    }

    private static void estilizarCombo(JComboBox<?> cb) {
        cb.setUI(new ComboArredondadoUI());
        cb.setBackground(CAMPO);
        cb.setBorder(new BordaArredondada(new Insets(4, 10, 4, 6)));
        cb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        repintarNoFoco(cb);
    }

    private static class ComboArredondadoUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            JButton seta = new BasicArrowButton(SwingConstants.SOUTH) {
                @Override
                public void paint(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    int w = getWidth(), h = getHeight();
                    int cx = w / 2, cy = h / 2;
                    Path2D p = new Path2D.Float();
                    p.moveTo(cx - 5, cy - 2);
                    p.lineTo(cx, cy + 3);
                    p.lineTo(cx + 5, cy - 2);
                    g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.setColor(isEnabled() ? TEXTO : Color.GRAY);
                    g2.draw(p);
                    g2.dispose();
                }

                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(24, 24);
                }
            };
            seta.setOpaque(false);
            seta.setContentAreaFilled(false);
            seta.setBorder(BorderFactory.createEmptyBorder());
            seta.setFocusable(false);
            return seta;
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            g.setColor(comboBox.getBackground());
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }

        @Override
        public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
            super.paintCurrentValue(g, bounds, false);
        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup popup = new BasicComboPopup(comboBox);
            popup.setBorder(BorderFactory.createLineBorder(BORDA));
            JList<?> lista = popup.getList();
            lista.setSelectionBackground(AMARELO);
            lista.setSelectionForeground(TEXTO);
            return popup;
        }
    }

    private static class BordaArredondada extends AbstractBorder {
        private final Insets espaco;

        BordaArredondada(Insets espaco) {
            this.espaco = espaco;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            RoundRectangle2D forma = new RoundRectangle2D.Float(x + 0.5f, y + 0.5f, w - 1f, h - 1f, RAIO, RAIO);

            Area cantos = new Area(new Rectangle2D.Float(x, y, w, h));
            cantos.subtract(new Area(forma));
            Container pai = c.getParent();
            g2.setColor(pai != null ? pai.getBackground() : FUNDO);
            g2.fill(cantos);

            boolean foco = c.isFocusOwner()
                    || (c instanceof JComboBox<?> cb && cb.isPopupVisible());
            g2.setColor(foco ? BORDA_FOCO : BORDA);
            g2.setStroke(new BasicStroke(foco ? 1.6f : 1f));
            g2.draw(forma);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(espaco.top, espaco.left, espaco.bottom, espaco.right);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets i) {
            i.set(espaco.top, espaco.left, espaco.bottom, espaco.right);
            return i;
        }
    }

    private static void repintarNoFoco(JComponent c) {
        if (Boolean.TRUE.equals(c.getClientProperty(APLICADO))) return;
        c.putClientProperty(APLICADO, Boolean.TRUE);
        c.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { c.repaint(); }

            @Override
            public void focusLost(FocusEvent e) { c.repaint(); }
        });
    }

    private static Color misturar(Color a, Color b, float t) {
        return new Color(
                Math.round(a.getRed() + (b.getRed() - a.getRed()) * t),
                Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t),
                Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t));
    }
}
