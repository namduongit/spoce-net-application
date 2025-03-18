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

        this.titleForm.setBounds(20, 80, 270, 30);
        this.titleForm.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));

        this.emailField = new CustomTextField();
        this.emailField.setBounds(20, 110, 270, 35);

        this.sendNewPasswordButton = new CustomButton("Send new password");
        this.sendNewPasswordButton.setBounds(20, 160, 270, 40);
        this.sendNewPasswordButton.setBackground(Color.decode("#2962FF"));
        this.sendNewPasswordButton.setBorderColor(Color.decode("#2962FF"));
        this.sendNewPasswordButton.setForeground(Color.WHITE);
        this.sendNewPasswordButton.addActionListener(this::handleSendOTP);


        panel.add(this.titleForm);
        panel.add(this.emailField);
        panel.add(this.sendNewPasswordButton);

        return panel;
    }

    private void handleSendOTP(ActionEvent e) {
        if (this.emailField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Không được để Email trống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            String textEmail = this.emailField.getText();
            Staffs staffs = this.staffBLL.getStaffByEmail(textEmail);
            if (staffs == null) {
                JOptionPane.showMessageDialog(null, "Tài khoản không tồn tại hoặc chưa cập nhật\n Vui lòng liên hệ quản trị viên để được đổi mật khẩu", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                Accounts accounts = this.accountBLL.getAccountById(staffs.getAccountId());
                SendEmail sendEmail = new SendEmail();
                String newPassword = sendEmail.CreateNewPassword();
                HashMap<String, Object> updateValues = new HashMap<>();
                updateValues.put("password", Utils.Helper.EncriptString.MD5String(newPassword));
                boolean updateResult = this.accountBLL.updateAccountDetailsById(accounts.getAccountId(), updateValues);
                if (updateResult) {
                    JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu mới thành công\n Vui lòng kiểm tra Email của bạn \n Nếu không thấy vui lòng kiểm tra thư mục rác", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    sendEmail.sendEmail(textEmail, newPassword, accounts.getUsername());
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu không thành công\n Vui lòng thử lại sau 60 giây", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }
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
