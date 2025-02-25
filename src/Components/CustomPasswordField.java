package Components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JPasswordField;


public class CustomPasswordField extends JPasswordField {
    private int border = 1;
    private int borderRadius = 10;
    private Color borderColor = Color.BLACK;
    private int paddingX = 10;

    public CustomPasswordField() {
        setOpaque(false);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setMargin(new Insets(5, paddingX, 5, paddingX));
    }

    public CustomPasswordField(String text) {
        this();
        setText(text);
    }

    public void setBorderSize(int border) {
        this.border = border;
        repaint();
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        repaint();
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public void setPaddingX(int paddingX) {
        this.paddingX = paddingX;
        setMargin(new Insets(5, paddingX, 5, paddingX));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setColor(getBackground());
        g2D.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setColor(borderColor);
        g2D.setStroke(new BasicStroke(border));
        g2D.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
    }
}
