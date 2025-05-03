package GUI.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import GUI.Components.CustomButton;
import GUI.Components.CustomTextField;
import Utils.Helper.SendEmail;
import BLL.AccountBLL;
import BLL.StaffBLL;
import DTO.Accounts;
import DTO.Staffs;
public class ForgetPass extends JFrame {

    private StaffBLL staffBLL;
    private AccountBLL accountBLL;

    private JPanel leftPanel;
    private JPanel rightPanel;

    private JLabel titleForm = new JLabel("Enter your email:");

    private CustomTextField emailField;
    private CustomTextField usernameField;
    private JLabel usernameLabel;

    private CustomButton sendNewPasswordButton;

    public ForgetPass() {
        this.staffBLL = new StaffBLL();
        this.accountBLL = new AccountBLL();
        this.initComponents();
    }


    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel titlePanel = new JLabel("Reset your password");
        titlePanel.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
        titlePanel.setBounds(50, 40, 250, 50);

        JLabel logoPanel = new JLabel();
        Image scaledImage = new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Image/undraw_forgot-password_odai.png").getImage().getScaledInstance(331, 220, Image.SCALE_SMOOTH);
        logoPanel.setIcon(new ImageIcon(scaledImage));
        logoPanel.setBounds(5, 100, 331, 220);

        panel.add(titlePanel);
        panel.add(logoPanel);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel titleForm = new JLabel("Reset your password");
        titleForm.setBounds(20, 30, 270, 30);
        titleForm.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 16));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 70, 270, 30);
        usernameLabel.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 14));
        this.usernameLabel = usernameLabel;

        this.usernameField = new CustomTextField();
        this.usernameField.setBounds(20, 100, 270, 35);

        this.titleForm.setText("Email:");
        this.titleForm.setBounds(20, 140, 270, 30);
        this.titleForm.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 14));

        this.emailField = new CustomTextField();
        this.emailField.setBounds(20, 170, 270, 35);

        this.sendNewPasswordButton = new CustomButton("Send new password");
        this.sendNewPasswordButton.setBounds(20, 220, 270, 40);
        this.sendNewPasswordButton.setBackground(Color.decode("#2962FF"));
        this.sendNewPasswordButton.setBorderColor(Color.decode("#2962FF"));
        this.sendNewPasswordButton.setForeground(Color.WHITE);
        this.sendNewPasswordButton.addActionListener(this::handleSendOTP);

        panel.add(titleForm);
        panel.add(usernameLabel);
        panel.add(this.usernameField);
        panel.add(this.titleForm);
        panel.add(this.emailField);
        panel.add(this.sendNewPasswordButton);

        return panel;
    }


    private void handleSendOTP(ActionEvent e) {
        String textEmail = this.emailField.getText().trim();
        String textUsername = this.usernameField.getText().trim();

        if (textEmail.equals("") || textUsername.equals("")) {
            JOptionPane.showMessageDialog(null, "Không được để trống Email hoặc Tên đăng nhập", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Accounts accountsUser = this.accountBLL.getAccountByUsername(textUsername);
        Staffs staffs = this.staffBLL.getStaffByEmail(textEmail);

        if (staffs == null || accountsUser == null) {
            JOptionPane.showMessageDialog(null, "Thông tin không có trên hệ thống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (accountsUser.getAccountId() != staffs.getAccountId()) {
            JOptionPane.showMessageDialog(null, "Thông tin không khớp so với thông tin trên hệ thống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Accounts accounts = this.accountBLL.getAccountById(staffs.getAccountId());
        if (!accounts.getUsername().equals(textUsername)) {
            JOptionPane.showMessageDialog(null, "Tên đăng nhập không trùng khớp với Email đã nhập", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SendEmail sendEmail = new SendEmail();
        String newPassword = sendEmail.CreateNewPassword();
        HashMap<String, Object> updateValues = new HashMap<>();
        updateValues.put("password", Utils.Helper.EncriptString.MD5String(newPassword));
        boolean updateResult = this.accountBLL.updateAccountDetailsById(accounts.getAccountId(), updateValues);

        if (updateResult) {
            JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu mới thành công\nVui lòng kiểm tra Email\nNếu không thấy, hãy kiểm tra thư mục Rác", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            sendEmail.sendEmail(textEmail, newPassword, accounts.getUsername());
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu không thành công\nVui lòng thử lại sau", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(680, 420);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Reset your password");
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Icon/icons8-password-50.png").getImage());
        this.setLayout(null);

        this.leftPanel = this.createLeftPanel();
        this.rightPanel = this.createRightPanel();

        this.rightPanel.setBounds(335, 0, 340, 420);
        this.leftPanel.setBounds(0, 0, 340, 420);

        this.add(rightPanel);
        this.add(leftPanel);
    }

    public static void main(String[] args) {
        new ForgetPass().setVisible(true);
    }
}
