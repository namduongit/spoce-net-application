package Utils.Helper;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class AdjustTableWidth {
    public static void automaticallyAdjustTableWidths(JTable table) {
        for (int i=0; i<table.getColumnCount(); i++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(i);

            int width = 0, headerWidth;
            String header = table.getColumnName(i);
            FontMetrics metrics = table.getTableHeader().getFontMetrics(table.getFont());
            headerWidth = metrics.stringWidth(header) + 10;

            for (int j=0; j<table.getRowCount(); j++) {
                String value = table.getValueAt(j, i) == null ? "" : table.getValueAt(j, i).toString();
                int valueWidth = metrics.stringWidth(value) + 10;
                width = Math.max(width, valueWidth);
            }

            width = Math.max(width, headerWidth);
            width = Math.min(width, 300);
            width = Math.max(width, 250);
            tableColumn.setPreferredWidth(width);
        }
    }
}
