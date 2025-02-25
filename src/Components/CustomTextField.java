package Components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JTextField;

public class CustomTextField extends JTextField {
    private int border = 1;
    private int borderRadius = 10;
    private Color borderColor = Color.BLACK;
    private int paddingX = 10;

    public CustomTextField() {
        setOpaque(false);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        updateMargin();
    }

    public CustomTextField(String textContent) {
        this();
        this.setText(textContent);
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

    public void setPaddingX(int paddingX) { // Chỉ cập nhật padding ngang
        this.paddingX = paddingX;
        updateMargin();
    }

    private void updateMargin() {
        setMargin(new Insets(0, paddingX, 0, paddingX)); // Chỉ cập nhật padding ngang
        revalidate();
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

        g2D.setStroke(new BasicStroke(border));
        g2D.setColor(borderColor);
        g2D.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
    }
}
