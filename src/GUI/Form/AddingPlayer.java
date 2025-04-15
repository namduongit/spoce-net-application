package GUI.Form;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import BLL.AccountBLL;
import GUI.Components.CustomButton;
import GUI.Components.CustomTextField;
import GUI.Components.CustomPasswordField;

public class AddingPlayer extends JFrame {
    private AccountBLL accountBLL;

    private JLabel titleForm;
    private JLabel inputUsernameLabel;
    private JLabel inputPasswordLabel;
    private JLabel confirmPasswordLabel;
    private JLabel inputBalanceLabel;

    private CustomTextField inputUsernameField;
    private CustomPasswordField inputPasswordField;
    private CustomPasswordField confirmPasswordField;
    private CustomTextField inputBalanceField;

    private CustomButton confirmButton;
    private CustomButton cancelButton;

    public AddingPlayer() {
        this.accountBLL = new AccountBLL();
        this.initComponents();
    }

    private void initComponents() {
        this.setSize(400, 550);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Thêm người chơi");
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Icon/icons8-create-100.png").getImage());

        this.titleForm = new JLabel("Thêm người chơi");
        this.titleForm.setFont(new Font("Sans", Font.BOLD, 18));
        this.titleForm.setBounds(120, 10, 200, 50);

        this.inputUsernameLabel = new JLabel("Tên đăng nhập:");
        this.inputUsernameLabel.setBounds(25, 70, 335, 30);

        this.inputUsernameField = new CustomTextField();
        this.inputUsernameField.setBounds(25, 100, 335, 40);

        this.inputPasswordLabel = new JLabel("Mật khẩu:");
        this.inputPasswordLabel.setBounds(25, 150, 335, 30);

        this.inputPasswordField = new CustomPasswordField();
        this.inputPasswordField.setBounds(25, 180, 335, 40);

        this.confirmPasswordLabel = new JLabel("Xác nhận mật khẩu:");
        this.confirmPasswordLabel.setBounds(25, 230, 335, 30);

        this.confirmPasswordField = new CustomPasswordField();
        this.confirmPasswordField.setBounds(25, 260, 335, 40);

        this.inputBalanceLabel = new JLabel("Số dư:");
        this.inputBalanceLabel.setBounds(25, 310, 335, 30);

        this.inputBalanceField = new CustomTextField();
        this.inputBalanceField.setBounds(25, 340, 335, 40);

        this.confirmButton = Utils.Helper.CreateComponent.createGreenButton("Xác nhận");
        this.confirmButton.setBounds(25, 420, 100, 30);
        this.confirmButton.addActionListener(e -> {
            this.confirmAddingPlayer();
        });

        this.cancelButton = Utils.Helper.CreateComponent.createOrangeButton("Hủy");
        this.cancelButton.setBounds(140, 420, 100, 30);
        this.cancelButton.addActionListener(e -> {
            this.dispose();
        });

        this.add(this.titleForm);
        this.add(this.inputUsernameLabel);
        this.add(this.inputUsernameField);
        this.add(this.inputPasswordLabel);
        this.add(this.inputPasswordField);
        this.add(this.confirmPasswordLabel);
        this.add(this.confirmPasswordField);
        this.add(this.inputBalanceLabel);
        this.add(this.inputBalanceField);
        this.add(this.confirmButton);
        this.add(this.cancelButton);
    }

    private void confirmAddingPlayer() {
        String username = this.inputUsernameField.getText();
        String password = new String(this.inputPasswordField.getPassword());
        String confirmPassword = new String(this.confirmPasswordField.getPassword());
        String balanceStr = this.inputBalanceField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || balanceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!Utils.Helper.Comon.isTrueNumber(balanceStr)) {
            JOptionPane.showMessageDialog(this, "Nhập đúng định dạng số dư VD: 10000", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Mật khẩu có ít nhất 8 kí tự", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean createPlayer = this.accountBLL.createPlayerAccount(username, confirmPassword);
        boolean updateBalance = this.accountBLL.updateBalancePlayerAccount(username, Integer.parseInt(balanceStr));

        if (createPlayer && updateBalance) {
            JOptionPane.showMessageDialog(this, "Tạo tài khoản và thêm số dư thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Tại khoản đã tồn tại hoặc có lỗi hệ thống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
