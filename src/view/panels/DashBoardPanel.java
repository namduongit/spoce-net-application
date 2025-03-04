package view.panels;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.Components.CustomButton;

public class DashBoardPanel extends JPanel {
    public DashBoardPanel() {
        this.setLayout(null);
        this.setSize(Utils.Config.ConfigFrame.WIDTH_FRAME - 250, Utils.Config.ConfigFrame.HEIGHT_FRAME);
        this.initComponents();
    }

    private CustomButton createButton(String imageIconName) {
        CustomButton button = new CustomButton("");
        button.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") +"/src/Assets/Icon/"+ imageIconName).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)), 0);
        button.setBorderSize(0);
        button.setBorderColor(Color.WHITE);
        return button;
    }

    private JPanel createDashBoardTitle() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") +"/src/Assets/Image/MENU.png").getImage().getScaledInstance(1116, 256, Image.SCALE_SMOOTH)));
        label.setBounds(0, 0, 1116, 256);

        panel.add(label);
        return panel;
    }

    private JPanel createIntromation() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") +"/src/view/Assets/Image/infomation-dashboard.png").getImage().getScaledInstance(1116, 512, Image.SCALE_SMOOTH)));
        label.setBounds(0, 0, 1116, 512);
        label.setOpaque(false);

        CustomButton targetButton = this.createButton("icons8-target-100.png");
        targetButton.setBounds(105, 100, 200, 100);

        CustomButton securityButton = this.createButton("icons8-cyber-security-100.png");
        securityButton.setBounds(460, 100, 200, 100);

        CustomButton growthButton = this.createButton("icons8-growth-100.png");
        growthButton.setBounds(815, 100, 200, 100);

        panel.add(label);
        panel.add(targetButton);
        panel.add(securityButton);
        panel.add(growthButton);
        panel.setComponentZOrder(targetButton, 0);
        panel.setComponentZOrder(securityButton, 0);
        panel.setComponentZOrder(growthButton, 0);
        return panel;
    }

    private void initComponents() {
        JPanel dashBoardTitlePanel = this.createDashBoardTitle();
        JPanel createIntromation = this.createIntromation();

        dashBoardTitlePanel.setBounds(0, 0, Utils.Config.ConfigFrame.WIDTH_FRAME - 250, 256);
        createIntromation.setBounds(0, 256, Utils.Config.ConfigFrame.WIDTH_FRAME - 250, Utils.Config.ConfigFrame.HEIGHT_FRAME - 256);

        this.add(dashBoardTitlePanel);
        this.add(createIntromation);

        this.revalidate();
        this.repaint();
    }
}
