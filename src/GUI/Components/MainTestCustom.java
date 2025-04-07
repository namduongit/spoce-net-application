package GUI.Components;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainTestCustom {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test CustomDesignButton");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new FlowLayout());

            // Load icon
            ImageIcon icon = new ImageIcon(System.getProperty("user.dir") +"/src/Assets/Icon/icons8-address-100.png"); // Thay bằng đường dẫn thực tế

            // Tạo button
            CustomDesignButton button = new CustomDesignButton("Click Me", icon);
            button.setBackground(Color.WHITE);
            button.setBorderColor(Color.BLACK);
            button.setBorderRadius(15);
            button.setPadding(15);

            frame.add(button);

            frame.setLocationRelativeTo(null); // Center the frame
            frame.setVisible(true);
        });
    }
}
