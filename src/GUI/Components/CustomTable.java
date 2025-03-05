package GUI.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CustomTable extends JTable {
    public CustomTable(DefaultTableModel model) {
        super(model);
        initTable();
    }

    private void initTable() {
        setFont(new Font("Sans", Font.PLAIN, 13));
        setRowHeight(50);
        setGridColor(Color.LIGHT_GRAY);
        setShowGrid(true);
        setFillsViewportHeight(true);

        DefaultTableCellRenderer paddedRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value != null ? value.toString() : "");
                label.setFont(new Font("Arial", Font.PLAIN, 14));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

                // Màu xen kẽ
                if (!isSelected) {
                    label.setBackground(row % 2 == 0 ? Color.WHITE : new Color(230, 230, 230));
                } else {
                    label.setBackground(new Color(173, 216, 230));
                }

                return label;
            }
        };

        for (int i = 0; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setCellRenderer(paddedRenderer);
        }

        JTableHeader header = getTableHeader();
        header.setFont(new Font("Sans", Font.BOLD, 13));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(70, 130, 180));
        header.setOpaque(true);
        header.setPreferredSize(new Dimension(header.getWidth(), 50));
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Không cho chỉnh sửa ô nào hết
    }
}
