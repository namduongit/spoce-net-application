package Components;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.*;

public class CustomButton extends JButton {
    private int border = 1;
    private int borderRadius = 10;
    private Color borderColor = Color.BLACK;
    private int paddingX = 10;

    public CustomButton(String text) {
        super(text);
        initComponents();
        updateButtonSize();
    }

    private void initComponents() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
    }

    private void updateButtonSize() {
        FontMetrics fm = getFontMetrics(getFont());
        int textWidth = (getText() != null) ? fm.stringWidth(getText()) : 0;
        int textHeight = (getText() != null) ? fm.getHeight() : 0;

        int iconWidth = (getIcon() != null) ? getIcon().getIconWidth() : 0;
        int iconHeight = (getIcon() != null) ? getIcon().getIconHeight() : 0;

        int width = iconWidth + (textWidth > 0 ? paddingX + textWidth : 0) + paddingX * 2;
        int height = Math.max(textHeight, iconHeight) + paddingX;

        setPreferredSize(new Dimension(width, height));
        revalidate();
        repaint();
    }

    public void setBorderSize(int border) {
        this.border = border;
        repaint();
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        repaint();
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public void setPaddingX(int paddingX) {
        this.paddingX = paddingX;
        updateButtonSize();
    }

    public void setIcon(Icon icon, int typeScale) {
        if (icon instanceof ImageIcon) {
            ImageIcon imageIcon = (ImageIcon) icon;
            int textHeight = (getText() != null) ? getFontMetrics(getFont()).getHeight() : 16;
            int iconSize = typeScale == 0 ? imageIcon.getIconHeight() : textHeight;
            Image scaledImage = imageIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImage);
        }
        super.setIcon(icon);
        updateButtonSize();
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
