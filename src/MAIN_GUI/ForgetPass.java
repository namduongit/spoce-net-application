package MAIN_GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

import Components.CustomButton;
import Components.CustomTextField;
import SQLHelper.MySQLHelper;

public class ForgetPass extends JFrame {

    private JPanel leftPanel;
    private JPanel rightPanel;

    private JLabel titleForm = new JLabel("Enter your email:");

    private CustomTextField emailField;
    private CustomTextField otpField;
    private CustomTextField passwordField;

    private JLabel countdownLabel;

    private CustomButton sendOTPButton, verifyOTPButton, resendOTPButton, goBackMenuButton;
    private CustomButton changePasswordButton;

    private Timer timer;
    private int timeLeft = 5;
    private int currentTimeLeft = this.timeLeft;

    public ForgetPass() {
        this.initComponents();
    }

    private void returnHomePage() {
        this.titleForm.setText("Enter your email");

        this.countdownLabel.setText("Resend in: " + this.timeLeft + "s");
        this.countdownLabel.setVisible(false);

        this.resendOTPButton.setVisible(false);
        this.verifyOTPButton.setVisible(false);
        this.goBackMenuButton.setVisible(false);

        this.sendOTPButton.setVisible(true);

        this.emailField.setVisible(true);

        this.otpField.setVisible(false);
        this.passwordField.setVisible(false);
    }

    private void waitResendForm() {
        this.titleForm.setText("Enter your OTP");

        this.resendOTPButton.setEnabled(false);
        this.resendOTPButton.setBackground(Color.decode("#424242"));
        this.resendOTPButton.setBorderColor(Color.decode("#424242"));
        this.resendOTPButton.setForeground(Color.BLACK);

        this.countdownLabel.setVisible(true);
        this.goBackMenuButton.setVisible(true);
        this.verifyOTPButton.setVisible(true);

        this.verifyOTPButton.setBounds(20, 180, 130, 40);
        this.resendOTPButton.setBounds(160, 180, 130, 40);
        this.goBackMenuButton.setBounds(20, 230, 270, 40);

        this.otpField.setVisible(true);
        this.emailField.setVisible(false);
        this.passwordField.setVisible(false);
    }

    private void notWaitResendForm() {
        this.titleForm.setText("Enter your OTP");

        this.countdownLabel.setVisible(false);
        this.countdownLabel.setText("Resend in: " + this.timeLeft + "s");

        this.resendOTPButton.setEnabled(true);
        this.resendOTPButton.setBackground(Color.decode("#FF8F00"));
        this.resendOTPButton.setBorderColor(Color.decode("#FF8F00"));
        this.resendOTPButton.setForeground(Color.WHITE);

        this.verifyOTPButton.setBounds(20, 155, 130, 40);
        this.resendOTPButton.setBounds(160, 155, 130, 40);
        this.goBackMenuButton.setBounds(20, 205, 270, 40);

        this.otpField.setVisible(true);
        this.emailField.setVisible(false);
        this.passwordField.setVisible(false);
    }

    private void changePasswordForm() {
        this.titleForm.setText("Enter your new password");

        this.countdownLabel.setVisible(false);

        this.changePasswordButton.setVisible(true);
        this.sendOTPButton.setVisible(false);
        this.verifyOTPButton.setVisible(false);
        this.resendOTPButton.setVisible(false);
        this.goBackMenuButton.setVisible(false);

        this.passwordField.setVisible(true);
        this.emailField.setVisible(false);
        this.otpField.setVisible(false);
    }

    private void startCountdown() {
        this.waitResendForm();
        this.currentTimeLeft = this.timeLeft;
        if (this.timer != null) this.timer.stop();
        this.timer = new Timer(1000, e -> {
            this.currentTimeLeft--;
            this.countdownLabel.setText("Resend in: " + this.currentTimeLeft + "s");
            if ( this.currentTimeLeft <= 0) {
                this.timer.stop();
                this.notWaitResendForm();
            }
        });
        if (this.currentTimeLeft > 0) this.timer.start();
    }

    private CustomButton createButton(String textButton, String backgroundColorCode, Color colorCode) {
        CustomButton button = new CustomButton(textButton);
        button.setBackground(Color.decode(backgroundColorCode));
        button.setBorderColor(Color.decode(backgroundColorCode));
        button.setForeground(colorCode);
        button.setVisible(false);
        return button;
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

        this.sendOTPButton = new CustomButton("Send OTP");
        this.sendOTPButton.setBounds(20, 160, 270, 40);
        this.sendOTPButton.setBackground(Color.decode("#2962FF"));
        this.sendOTPButton.setBorderColor(Color.decode("#2962FF"));
        this.sendOTPButton.setForeground(Color.WHITE);
        this.sendOTPButton.addActionListener(this::handleSendOTP);

        this.otpField = new CustomTextField();
        this.otpField.setBounds(20, 110, 270, 35);
        this.otpField.setVisible(false);

        this.passwordField = new CustomTextField();
        this.passwordField.setBounds(20, 110, 270, 35);
        this.passwordField.setVisible(false);

        this.resendOTPButton = this.createButton("Resend OTP", "#FF8F00", Color.WHITE);
        this.resendOTPButton.setBounds(150, 180, 130, 40);
        this.resendOTPButton.addActionListener(e -> {
            this.handleSendOTP(e);
            this.startCountdown();
        });

        this.goBackMenuButton = this.createButton("Go Back", "#2962FF", Color.WHITE);
        this.goBackMenuButton.setBounds(20, 250, 250, 40);
        this.goBackMenuButton.addActionListener(e -> {
            this.returnHomePage();
        });

        this.verifyOTPButton = this.createButton("Verify OTP", "#4CAF50", Color.WHITE);
        this.verifyOTPButton.setBounds(20, 180, 130, 40);
        this.verifyOTPButton.addActionListener(this::verifyOTPValue);

        this.changePasswordButton = this.createButton("Change your password", "#2962FF", Color.WHITE);
        this.changePasswordButton.setBounds(20, 160, 270, 40);
        this.changePasswordButton.addActionListener(this::changePassword);

        this.countdownLabel = new JLabel("Resend in: "+ this.timeLeft +"s");
        this.countdownLabel.setBounds(20, 145, 250, 30);
        this.countdownLabel.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
        this.countdownLabel.setForeground(Color.BLACK);
        this.countdownLabel.setVisible(false);

        panel.add(this.titleForm);

        panel.add(this.otpField);
        panel.add(this.emailField);
        panel.add(this.passwordField);

        panel.add(this.verifyOTPButton);
        panel.add(this.resendOTPButton);
        panel.add(this.goBackMenuButton);
        panel.add(this.sendOTPButton);
        panel.add(this.changePasswordButton);

        panel.add(this.countdownLabel);

        return panel;
    }

    private void handleSendOTP(ActionEvent e) {
        String textEmail = this.emailField.getText();
        if (textEmail != "") {
            // Kiểm tra Email có tồn tại trên hệ thống không
            /** Nếu đúng thì thực hiện gửi mã và cho điền OTP
             * this.sendOTPButton.setVisible(false);

                this.emailField.setVisible(false);
                this.passwordField.setVisible(false);
                this.otpField.setVisible(true);

                this.goBackMenuButton.setVisible(true);
                this.verifyOTPButton.setVisible(true);
                this.resendOTPButton.setVisible(true);

                this.startCountdown();
             */
            MySQLHelper helper = new MySQLHelper();

            Map<String, String> params = new LinkedHashMap<>();
            params.put("TABLE", "");
            helper.buidlingQueryParam(params);

        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng điền Email", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void verifyOTPValue(ActionEvent e) {
        this.changePasswordForm();
    }

    private void changePassword(ActionEvent e) {

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
