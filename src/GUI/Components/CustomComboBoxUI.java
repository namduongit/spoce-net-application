package GUI.Components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class CustomComboBoxUI extends BasicComboBoxUI {
    @Override
    protected JButton createArrowButton() {
        JButton button = new JButton("▼");
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setForeground(Color.BLACK);
        return button;
    }

    @Override
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
        if (hasFocus) {
            g.setColor(new Color(220, 220, 220));
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
protected ListCellRenderer<Object> createRenderer() {
    return new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                    cellHasFocus);

            label.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

            if (isSelected) {
                label.setBackground(new Color(220, 220, 220));
                label.setForeground(Color.BLACK);
                label.setFont(label.getFont().deriveFont(Font.BOLD));
            } else {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
                label.setFont(label.getFont().deriveFont(Font.PLAIN));
            }
            return label;
        }
    };
}


    @Override
    public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
        g.setColor(Color.WHITE);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g.setFont(new Font("Sans", Font.PLAIN, 15));
        // Vẽ giá trị được chọn
        g.setColor(comboBox.getForeground());
        g.setFont(comboBox.getFont());

        if (comboBox.getSelectedItem() != null) {
            String text = comboBox.getSelectedItem().toString();
            FontMetrics fm = g.getFontMetrics();
            int y = (bounds.height - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(text, bounds.x + 5, bounds.y + y);
        }
    }

}
