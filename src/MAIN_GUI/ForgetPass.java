package MAIN_GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.*;

import Components.CustomButton;
import Components.CustomTextField;
import SQLHelper.MySQLHelper;
import Utils.SendEmail;

public class ForgetPass extends JFrame {

    public ForgetPass() {
        this.initComponents();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(680, 420);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Reset your password");
        this.setIconImage(
                new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Icon/icons8-password-50.png").getImage());
        this.setLayout(null);

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

        JLabel titlePanel = new JLabel("Reset your password");
        titlePanel.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
        titlePanel.setBounds(50, 40, 250, 50);

        JLabel logoPanel = new JLabel();
        Image scaledImage = new ImageIcon(
                System.getProperty("user.dir") + "/src/Assets/Image/undraw_forgot-password_odai.png")
                .getImage()
                .getScaledInstance(331, 220, Image.SCALE_SMOOTH);
        logoPanel.setIcon(new ImageIcon(scaledImage));
        logoPanel.setBounds(5, 100, 331, 220);

        JLabel emailLabel = new JLabel("Enter your email:");
        emailLabel.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        emailLabel.setBounds(40, 80, 300, 30);

        CustomTextField emailField = new CustomTextField();
        emailField.setBounds(40, 120, 250, 35);

        CustomButton sendOTPButton = new CustomButton("Send OTP");
        sendOTPButton.setBounds(40, 170, 250, 40);
        sendOTPButton.setBackground(Color.decode("#2962FF"));
        sendOTPButton.setBorderColor(Color.decode("#2962FF"));
        sendOTPButton.setForeground(Color.WHITE);
        sendOTPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MySQLHelper helper = new MySQLHelper();
                SendEmail sendEmail = new SendEmail();

                String emailInput = emailField.getText();

                Map<String, String> params = new LinkedHashMap<>();
                params.put("TABLE", "accounts");
                params.put("JOIN", "staffs ON accounts.account_id = staffs.staff_id");
                params.put("WHERE", "staffs.email = ?");

                List<Object> values = new ArrayList<>(List.of(emailInput));

                helper.buidlingQueryParam(params);

                ResultSet resultSet = helper.queryWithParam(values);

                if (resultSet != null) {
                    try {
                        if (resultSet.next()) {
                            String accountID = resultSet.getString("account_id");
                            String newOTPCode = sendEmail.createDigitToReset();
                            String username = resultSet.getString("username");

                            // Lấy thời gian hiện tại + 10 phút
                            LocalDateTime timeNow = LocalDateTime.now().plusMinutes(10);
                            Timestamp expires_at = Timestamp.valueOf(timeNow);

                            // Chuẩn bị thông tin INSERT
                            params.put("TABLE", "password_reset_otps");
                            params.put("FIELD", "account_id, otp_code, expires_at");

                            values.clear();
                            values = new ArrayList<>(List.of(username, newOTPCode, expires_at));

                            helper.buidlingQueryParam(params);
                            boolean success = helper.insertData(values);

                            if (success) {
                                boolean resultSend = sendEmail.sendNewOTP(emailInput, "Cập nhật lại mật khẩu!", newOTPCode, accountID);
                                if (resultSend) {
                                    JOptionPane.showMessageDialog(null, "Đã gửi mã OTP tới Email của bạn", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Có lỗi xảy ra vui lòng thử lại sau", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Email không tồn tại trên hệ thống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (SQLException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        leftPanel.setLayout(null);
        rightPanel.setLayout(null);

        rightPanel.setBounds(335, 0, 340, 420);
        leftPanel.setBounds(0, 0, 340, 420);

        rightPanel.setBackground(Color.WHITE);
        leftPanel.setBackground(Color.WHITE);

        leftPanel.add(titlePanel);
        leftPanel.add(logoPanel);

        rightPanel.add(emailLabel);
        rightPanel.add(emailField);
        rightPanel.add(sendOTPButton);
        rightPanel.remove(sendOTPButton);

        this.add(rightPanel);
        this.add(leftPanel);
    }

    public static void main(String[] args) {
        new ForgetPass().setVisible(true);
    }
}
