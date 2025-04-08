package GUI.Components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class CustomDesignButton extends JButton {
    private int border = 1;
    private int borderRadius = 10;
    private Color borderColor = Color.BLACK;
    private int padding = 10;


    public CustomDesignButton(String text, Icon icon) {
        super(text);
        setIcon(icon);
        initComponents();
        updateButtonSize();
    }

    private void initComponents() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.TOP);
    }

    private void updateButtonSize() {
        FontMetrics fm = getFontMetrics(getFont());
        int textHeight = (getText() != null) ? fm.getHeight() : 0;
        int iconHeight = (getIcon() != null) ? getIcon().getIconHeight() : 0;
        int iconWidth = (getIcon() != null) ? getIcon().getIconWidth() : 0;

        int width = Math.max(fm.stringWidth(getText()), iconWidth) + padding * 2;
        int height = iconHeight + textHeight + padding * 2;

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

    public void setPadding(int padding) {
        this.padding = padding;
        updateButtonSize();
    }

    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
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
        int borderRadius = (int)(this.borderRadius * 1.3);
        g2D.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setColor(borderColor);
        g2D.setStroke(new BasicStroke(border));
        int borderRadius = (int)(this.borderRadius * 1.3);
        g2D.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, borderRadius, borderRadius);
    }
}
