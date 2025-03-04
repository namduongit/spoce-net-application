package view.main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.DAO.AccountDAO;
import model.DAO.StaffDAO;
import model.Pojo.Accounts;
import model.Pojo.Staffs;
import utils.Helper.EncriptString;
import view.Components.CustomButton;
import view.Components.CustomPasswordField;
import view.Components.CustomTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class AdminLoginScreen extends JFrame{

    public AdminLoginScreen() {
        this.initComponents();
    }

    private void initComponents() {
        this.setSize(utils.Config.ConfigFrame.WIDTH_FRAME, utils.Config.ConfigFrame.HEIGHT_FRAME);
        this.setTitle("Đăng nhập hệ thống quản lý NetGaming");
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/view/Assets/Icon/icons8-management-100.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

        JLabel leftIcon = new JLabel();
        Image iconImage = new ImageIcon(System.getProperty("user.dir") +"/src/Assets/Image/undraw_personal-goals_f9bb.png").getImage().getScaledInstance(612, 480, Image.SCALE_SMOOTH);
        leftIcon.setIcon(new ImageIcon(iconImage));
        leftIcon.setBounds(20, 170, 612, 480);
        JLabel titleApp = new JLabel("Net Management App");
        titleApp.setFont(new Font(Font.DIALOG_INPUT,  Font.BOLD, 30));
        titleApp.setBounds(110, 80, 612, 50);

        JLabel titleLabel = new JLabel("Login to admin Dashboard");
        titleLabel.setFont(new Font(Font.DIALOG_INPUT,  Font.BOLD, 25));
        titleLabel.setBounds(160, 140, 540, 50);

        JLabel textInputUserName = new JLabel("Username");
        textInputUserName.setBounds(90, 225, 115, 20);
        textInputUserName.setFont(new Font(Font.DIALOG_INPUT,  Font.BOLD, 15));

        CustomTextField userNameInput = new CustomTextField("Enter your username");
        userNameInput.setBounds(90, 250, 540, 50);
        userNameInput.setFont(new Font(Font.DIALOG_INPUT,  Font.BOLD, 15));
        userNameInput.setForeground(Color.decode("#424242"));
        userNameInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = userNameInput.getText();
                if (text.equals("Enter your username")) {
                    userNameInput.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                String text = userNameInput.getText();
                if (text.isEmpty()) {
                    userNameInput.setText("Enter your username");
                }
            }
        });

        JLabel textInputPassword = new JLabel("Password");
        textInputPassword.setBounds(90, 310, 115, 20);
        textInputPassword.setFont(new Font(Font.DIALOG_INPUT,  Font.BOLD, 15));

        CustomPasswordField passwordInput = new CustomPasswordField("Enter your password");
        passwordInput.setBounds(90, 340, 540, 50);
        passwordInput.setFont(new Font(Font.DIALOG_INPUT,  Font.BOLD, 15));
        passwordInput.setForeground(Color.decode("#424242"));
        passwordInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = new String(passwordInput.getPassword());
                if (text.equals("Enter your password")) {
                    passwordInput.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                String text = new String(passwordInput.getPassword());
                if (text.isEmpty()) {
                    passwordInput.setText("Enter your password");
                }
            }
        });

        CustomButton forgotPassword = new CustomButton("Forgot your password");
        forgotPassword.setBounds(390, 405, 240, 35);
        forgotPassword.setFont(new Font(Font.DIALOG_INPUT,  Font.BOLD, 15));
        forgotPassword.setBackground(Color.decode("#424242"));
        forgotPassword.setBorderColor(Color.decode("#424242"));
        forgotPassword.setForeground(Color.WHITE);
        forgotPassword.setIcon(new ImageIcon(System.getProperty("user.dir") +"/src/view/Assets/Icon/icons8-forget-96.png"), 1);
        forgotPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ForgetPass().setVisible(true);
            }
        });

        CustomButton loginButton = new CustomButton("Login Admin Dashboard");
        loginButton.setBounds(90, 452, 540, 50);
        loginButton.setFont(new Font(Font.DIALOG_INPUT,  Font.BOLD, 15));
        loginButton.setBackground(Color.WHITE);
        loginButton.setBorderColor(Color.decode("#303F9F"));
        loginButton.setForeground(Color.decode("#303F9F"));
        loginButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Ấn chuột một phát
                String textUserName = userNameInput.getText();
                String textPassword = new String(passwordInput.getPassword());

                if (textUserName.equals("Enter your username") || textUserName.isEmpty()
                    || textPassword.equals("Enter your password") || textPassword.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                else {
                    Accounts accounts = AccountDAO.loginAccount(userNameInput.getText(), EncriptString.MD5String(new String(passwordInput.getPassword())));
                    if (accounts != null && (accounts.getRole().equals("Quản trị viên") || accounts.getRole().equals("Nhân viên"))) {
                        Staffs staffs = StaffDAO.getStaffsByAccountID(accounts.getAccountId());
                        JOptionPane.showMessageDialog(null, "Đăng nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new AdminDashboard(staffs, accounts).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Tài khoản không tồn tại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                // Di chuột tới vùng này
                loginButton.setBackground(Color.decode("#303F9F"));
                loginButton.setBorderColor(Color.decode("#303F9F"));
                loginButton.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // Ra khỏi vùng của vật gọi sự kiện
                loginButton.setBackground(Color.WHITE);
                loginButton.setBorderColor(Color.decode("#303F9F"));
                loginButton.setForeground(Color.decode("#303F9F"));
            }
        });

        leftPanel.setLayout(null);
        rightPanel.setLayout(null);

        panel.setBackground(Color.WHITE);
        leftPanel.setBackground(Color.WHITE);
        rightPanel.setBackground(Color.WHITE);

        leftPanel.setBounds(0, 0, 640, 720);
        rightPanel.setBounds(640, 0, 720, 720);

        rightPanel.add(titleLabel);
        rightPanel.add(textInputUserName);
        rightPanel.add(userNameInput);
        rightPanel.add(textInputPassword);
        rightPanel.add(passwordInput);
        rightPanel.add(forgotPassword);
        rightPanel.add(loginButton);

        leftPanel.add(leftIcon);
        leftPanel.add(titleApp);

        panel.add(rightPanel);
        panel.add(leftPanel);
        this.getContentPane().add(panel);
    }

    public static void main(String[] args) {
        new AdminLoginScreen().setVisible(true);
    }
}
