package GUI.Components;


import javax.swing.*;
import java.awt.*;

public class CustomScrollPane extends JScrollPane {
    public CustomScrollPane(Component view) {
        super(view);
        this.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        this.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        this.getVerticalScrollBar().setUnitIncrement(16);
    }
}
