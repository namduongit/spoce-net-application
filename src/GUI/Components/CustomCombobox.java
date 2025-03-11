package GUI.Components;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class CustomCombobox<E> extends JComboBox<E> {
    private int borderRadius = 10;
    private Color borderColor = Color.BLACK;
    private int borderSize = 1;
    private Color backgroundColor = Color.WHITE;
    private Color foregroundColor = Color.BLACK;

    public CustomCombobox(E[] items) {
        super(items);
        initComponents();
    }

    public CustomCombobox(ArrayList<E> items) {
        super();
        for (E item : items) {
            this.addItem(item);
        }
        initComponents();
    }

    private void initComponents() {
        setOpaque(false);
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setUI(new CustomComboBoxUI());
    }

    public void setBorderRadius(int radius) {
        this.borderRadius = radius;
        repaint();
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public void setBorderSize(int size) {
        this.borderSize = size;
        repaint();
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public void setForegroundColor(Color color) {
        this.foregroundColor = color;
        setForeground(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setColor(backgroundColor);
        g2D.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setColor(borderColor);
        g2D.setStroke(new BasicStroke(borderSize));
        g2D.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, borderRadius, borderRadius);
    }
}
