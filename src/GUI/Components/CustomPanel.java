package GUI.Components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class CustomPanel extends JPanel {

    public CustomPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        int borderRadius = 10;

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setColor(getBackground());
        g2D.fillRoundRect(0, 0, width, height, borderRadius, borderRadius);
    }
}
