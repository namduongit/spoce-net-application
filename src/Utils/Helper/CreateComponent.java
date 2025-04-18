package Utils.Helper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;

import GUI.Components.CustomButton;

public class CreateComponent {
    public static CustomButton createButton(String imageIconName, String textIcon) {
        int target = 15;
        int lengthText = textIcon.length();
        for (int i = 0; i < target - lengthText; i++) {
            textIcon = " "+ textIcon;
        }
        CustomButton button = new CustomButton(textIcon);
        button.setIcon(new ImageIcon(
                new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Icon/"+ imageIconName).getImage()
                .getScaledInstance(50, 50, Image.SCALE_SMOOTH)),
                0);
        button.setBorderSize(0);
        button.setBorderColor(Color.WHITE);
        button.setFont(new Font("Sans-serif", Font.BOLD, 15));

        // Thêm sự kiện ấn vào đổi màu nền background
        button.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                button.setBackground(Color.decode("#BBDEFB"));
            }
            @Override
            public void focusLost(FocusEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
        return button;
    }

    public static CustomButton createButtonNoIcon(String textContent) {
        CustomButton button = new CustomButton(textContent);
        button.setBorderColor(Color.decode("#1976D2"));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode("#1976D2"));
        button.setBorderSize(3);
        button.setBorderRadius(20);
        button.setFont(new Font("Sans-serif", Font.BOLD, 13));
        // Thêm sự kiện ấn vào đổi màu nền background
        button.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                button.setForeground(Color.decode("#1976D2"));
                button.setBackground(Color.WHITE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                button.setForeground(Color.WHITE);
                button.setBackground(Color.decode("#1976D2"));
            }
        });
        return button;
    }

    public static CustomButton createBlueButton(String textContent) {
        CustomButton button = new CustomButton(textContent);
        button.setBorderColor(Color.decode("#1E88E5"));
        button.setFont(new Font("Sans-serif", Font.BOLD, 13));
        button.setBackground(Color.decode("#1E88E5"));
        button.setForeground(Color.WHITE);

        return button;
    }

    public static CustomButton createOrangeButton(String textContent) {
        CustomButton button = new CustomButton(textContent);
        button.setBorderColor(Color.decode("#DD2C00"));
        button.setFont(new Font("Sans-serif", Font.BOLD, 13));
        button.setBackground(Color.decode("#DD2C00"));
        button.setForeground(Color.WHITE);

        return button;
    }

    public static CustomButton createGreenButton(String textContent) {
        CustomButton button = new CustomButton(textContent);
        button.setBorderColor(Color.decode("#2E7D32"));
        button.setFont(new Font("Sans-serif", Font.BOLD, 13));
        button.setBackground(Color.decode("#2E7D32"));
        button.setForeground(Color.WHITE);

        return button;
    }

    public static CustomButton createGrayButton(String textContent) {
        CustomButton button = new CustomButton(textContent);
        button.setBorderColor(Color.decode("#37474F"));
        button.setFont(new Font("Sans-serif", Font.BOLD, 13));
        button.setBackground(Color.decode("#37474F"));
        button.setForeground(Color.WHITE);
        
        return button;
    }

    public static CustomButton createBrownButton(String textContent) {
        CustomButton button = new CustomButton(textContent);
        button.setBorderColor(Color.decode("#5D4037"));
        button.setFont(new Font("Sans-serif", Font.BOLD, 13));
        button.setBackground(Color.decode("#5D4037"));
        button.setForeground(Color.WHITE);

        return button;
    }
}
