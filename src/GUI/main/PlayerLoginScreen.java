package GUI.main;

import BLL.AccountBLL;
import BLL.ComputerBLL;
import BLL.ComputerSessionBLL;
import BLL.PlayerBLL;
import DTO.Accounts;
import DTO.Computers;
import DTO.Players;
import GUI.Components.CustomButton;
import GUI.Components.CustomPasswordField;
import GUI.Components.CustomTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.HashMap;

public class PlayerLoginScreen extends JFrame {
    private int computerId;
    private ComputerBLL computerBLL;
    private Computers computers;

    private PlayerBLL playerBLL;
    private AccountBLL accountBLL;
    private ComputerSessionBLL computerSessionBLL;

    private Timer timer;
    public PlayerLoginScreen(int computerId) {
        this.computerId = computerId;
        this.computerBLL = new ComputerBLL();
        this.computers = this.computerBLL.getComputerById(this.computerId);

        this.playerBLL = new PlayerBLL();
        this.accountBLL = new AccountBLL();
        this.computerSessionBLL = new ComputerSessionBLL();

        this.initComponents();
    }


    private void initComponents() {
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") +"/src/Assets/Icon/icons8-login-100.png").getImage());
        this.setTitle("Đăng nhập vào máy: "+ this.computers.getComputerId() +" - "+ this.computers.getName());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        JLabel label = new JLabel("Đăng nhập vào máy: "+ this.computers.getComputerId() +" - "+ this.computers.getName());
        label.setFont(new Font("Sans-serif", Font.BOLD, 15));
        label.setBounds(30, 20, 330, 30);


        JLabel usernameLabel = new JLabel("Tên đăng nhập");
        usernameLabel.setBounds(30, 70, 330, 25);

        CustomTextField usernameTextField = new CustomTextField("Nhập tên đăng nhập");
        usernameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameTextField.getText().equals("Nhập tên đăng nhập")) {
                    usernameTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameTextField.getText().isEmpty()) {
                    usernameTextField.setText("Nhập tên đăng nhập");
                }
            }
        });
        usernameTextField.setBounds(30, 95, 330, 35);

        JLabel passwordLabel = new JLabel("Mật khẩu");
        passwordLabel.setBounds(30, 135, 330, 25);

        CustomPasswordField passwordTextField = new CustomPasswordField("Nhập mật khẩu");
        passwordTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordTextField.getPassword()).equals("Nhập mật khẩu")) {
                    passwordTextField.setText("");
                    passwordTextField.setEchoChar('\u2022');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordTextField.getPassword()).isEmpty()) {
                    passwordTextField.setText("Nhập mật khẩu");
                    passwordTextField.setEchoChar((char) 0);
                }
            }
        });
        passwordTextField.setBounds(30, 160, 330, 35);

        CustomButton returnChooseComputer = Utils.Helper.CreateComponent.createOrangeButton("Quay lại chọn máy");
        returnChooseComputer.setBounds(30, 210, 170, 25);
        returnChooseComputer.addActionListener(e -> {
            new ChooseComputer().setVisible(true);
            this.dispose();
        });




        CustomButton loginButton = Utils.Helper.CreateComponent.createBlueButton("Đăng nhập");
        loginButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = new String(passwordTextField.getPassword());

            if (username.isEmpty() || username.equalsIgnoreCase("Nhập tên đăng nhập") || password.isEmpty() || password.equalsIgnoreCase("Nhập mật khẩu")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Accounts accounts = this.accountBLL.playerLoginAccount(username, Utils.Helper.EncriptString.MD5String(password));
            if (accounts == null) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thất bại", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Players players = this.playerBLL.getPlayerByAccountId(accounts.getAccountId());

            if (players == null) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thất bại", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (accounts.getStatus().equalsIgnoreCase("Online")) {
                JOptionPane.showMessageDialog(this, "Tài khoản này đang hoạt động không sài được", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (accounts.getStatus().equalsIgnoreCase("Locked")) {
                JOptionPane.showMessageDialog(this, "Tài khoản này đang bị khóa", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (players.getBalance() <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nạp thêm tiền để sử dụng dịch vụ", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Đăng nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            this.accountBLL.updateAccountStatus(accounts.getAccountId(), "Online");

            // Tạo phiên mới
            int sessionId = this.computerSessionBLL.insertValueComputerSession(computers.getComputerId(), players.getPlayerId());
            // Update status computer
            HashMap<String, Object> updateValues = new HashMap<>();
            updateValues.put("status", "Đang sử dụng");
            this.computerBLL.updateComputerById(this.computers.getComputerId(), updateValues);

            new ComputerChooseDashboard(computers, players, sessionId).setVisible(true);
            this.dispose();

        });
        loginButton.setBounds(30, 300, 330, 35);

        this.add(label);
        this.add(usernameLabel);
        this.add(usernameTextField);
        this.add(passwordLabel);
        this.add(usernameLabel);
        this.add(passwordTextField);
        this.add(returnChooseComputer);
        this.add(loginButton);

        this.listenToOpenTheMachine();
    }

    private void listenToOpenTheMachine() {
        // 100 millisecond sẽ chạy lại coi chú ý nếu máy ae yếu quá thì set lên cao hơn
        this.timer = new Timer(100, e -> {
            this.computers = this.computerBLL.getComputerById(this.computerId);
            if (this.computers.getStatus().equalsIgnoreCase("Đang sử dụng")) {
                timer.stop();
                // Lấy cái vừa mở được
                int sessionId = this.computerSessionBLL.getInsertLastestSession(this.computers.getComputerId());
                new ComputerChooseDashboard(this.computers, null, sessionId).setVisible(true);
                this.dispose();
            }
        });

        timer.start();
    }

}
