package CardPanel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.*;

import Components.CustomButton;
import Components.CustomPanel;
import Pojo.Accounts;
import Pojo.Staffs;

public class AccountPanel extends JPanel {
    private Accounts currentAccount;
    private Staffs currentStaff;

    private CardLayout cardLayout;
    private JPanel buttonPanel;
    private JPanel infomationPanel;
    private JLabel avatarEmployee;

    public AccountPanel(Accounts currentAccount, Staffs currentStaff) {
        this.currentAccount = currentAccount;
        this.currentStaff = currentStaff;
        this.initComponents();
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titlePanel = new JLabel("QUẢN LÝ TÀI KHOẢN");
        titlePanel.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        titlePanel.setBounds(420, 30, 400, 50);

        CustomButton selfInfoButton = Utils.Helper.CreateComponent.createButtonNoIcon("Thông tin tài khoản");
        selfInfoButton.setBounds(20, 100, 170, 40);
        selfInfoButton.addActionListener(e -> this.cardLayout.show(this.infomationPanel, "MyInfo"));

        CustomButton playerAccountButton = Utils.Helper.CreateComponent.createButtonNoIcon("Tài khoản người chơi");
        playerAccountButton.setBounds(200, 100, 170, 40);
        playerAccountButton.addActionListener(e -> this.cardLayout.show(this.infomationPanel, "PlayerInfo"));

        panel.add(titlePanel);
        panel.add(selfInfoButton);
        panel.add(playerAccountButton);

        if (this.currentAccount.getRole().equals("Quản trị viên")) {
            CustomButton employeeButton = Utils.Helper.CreateComponent.createButtonNoIcon("Tài khoản nhân viên");
            employeeButton.setBounds(380, 100, 170, 40);
            employeeButton.addActionListener(e -> this.cardLayout.show(this.infomationPanel, "EmployeeInfo"));
            panel.add(employeeButton);
        }

        return panel;
    }

    private CustomPanel createInfoPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        avatarEmployee = new JLabel();
        updateAvatar(getAvatarPath());
        avatarEmployee.setBounds(30, 30, 200, 200);
        panel.add(avatarEmployee);

        // Nút chọn ảnh
        JButton btnChooseFile = new JButton("Chọn Ảnh");
        btnChooseFile.setBounds(30, 250, 120, 30);
        btnChooseFile.addActionListener(e -> chooseImage());
        panel.add(btnChooseFile);

        return panel;
    }

    private JPanel createInfomationPanel() {
        this.cardLayout = new CardLayout();
        JPanel panel = new JPanel(this.cardLayout);
        panel.setBackground(Color.WHITE);

        CustomPanel selfInfoPanel = this.createInfoPanel();
        selfInfoPanel.setBackground(Color.BLACK);

        CustomPanel playerAccountPanel = new CustomPanel();
        playerAccountPanel.setBackground(Color.BLUE);

        CustomPanel employeePanel = new CustomPanel();
        employeePanel.setBackground(Color.RED);

        panel.add(selfInfoPanel, "MyInfo");
        panel.add(playerAccountPanel, "PlayerInfo");
        panel.add(employeePanel, "EmployeeInfo");
        return panel;
    }

    private void initComponents() {
        this.setLayout(null);
        this.setBackground(Color.decode("#9E9E9E"));
        this.buttonPanel = this.createButtonPanel();
        this.infomationPanel = this.createInfomationPanel();

        buttonPanel.setBounds(0, 0, 1116, 200);
        infomationPanel.setBounds(0, 200, 1116, 568);

        this.add(buttonPanel);
        this.add(infomationPanel);
    }

    private String getAvatarPath() {
        String avatarFile = this.currentStaff.getAvatar();
        String basePath = System.getProperty("user.dir") + "/src/Assets/Avatar/";

        if (avatarFile == null || avatarFile.isEmpty()) {
            return basePath + "non_avatar.png";
        }

        File file = new File(basePath + avatarFile); // Không nối thêm ".png"
        return file.exists() ? file.getAbsolutePath() : basePath + "non_avatar.png";
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh PNG");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Images", "png"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            saveNewAvatar(selectedFile);
        }
    }

    private void saveNewAvatar(File selectedFile) {
        try {
            String oldAvatarFile = this.currentStaff.getAvatar();
            String basePath = System.getProperty("user.dir") + "/src/Assets/Avatar/";

            // Xóa ảnh cũ (nếu không phải ảnh mặc định)
            if (oldAvatarFile != null && !oldAvatarFile.isEmpty() && !oldAvatarFile.equals("non_avatar.png")) {
                File oldFile = new File(basePath + oldAvatarFile); // Thêm ".png"
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            String newFileName = "avatar_" + this.currentStaff.getAccountId() + ".png";
            File destination = new File(basePath + newFileName);

            // Copy ảnh mới vào thư mục
            Files.copy(selectedFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Cập nhật thông tin avatar của nhân viên
            this.currentStaff.setAvatar(newFileName);
            updateAvatar(destination.getAbsolutePath());

            JOptionPane.showMessageDialog(null, "Ảnh đại diện đã được cập nhật!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu ảnh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateAvatar(String path) {
        ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        avatarEmployee.setIcon(icon);
    }
}
