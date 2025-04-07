package GUI.Form;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import BLL.AccountBLL;
import DTO.Accounts;
import GUI.Components.CustomButton;
import GUI.Components.CustomPasswordField;

public class DetailsAuthenticator extends JFrame {
    private Accounts editAccounts;

    private JLabel titleForm;
    private JLabel inputOldPassword;
    private JLabel inputPassword;
    private JLabel confirmInputPassword;

    private CustomPasswordField inputOldPasswordField;
    private CustomPasswordField inputPasswordField;
    private CustomPasswordField confirmInputPasswordField;

    private CustomButton confirmButton;
    private CustomButton cancelButton;

    public DetailsAuthenticator(Accounts editAccount) {
        this.editAccounts = editAccount;
        this.initComponents();
    }

    private void initComponents() {
        this.setSize(400, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Thay đổi mật khẩu");
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Icon/icons8-password-100.png").getImage());

        this.titleForm = new JLabel("Đổi mật khẩu");
        this.titleForm.setFont(new Font("Sans", Font.BOLD, 18));
        this.titleForm.setBounds(130, 10, 140, 50);

        this.inputOldPassword = new JLabel("Mật khẩu cũ:");
        this.inputOldPassword.setBounds(25, 80, 335, 30);
        
        this.inputOldPasswordField = new CustomPasswordField();
        this.inputOldPasswordField.setBounds(25, 110, 335, 40);

        this.inputPassword = new JLabel("Mật khẩu mới:");
        this.inputPassword.setBounds(25, 160, 335, 30);

        this.inputPasswordField = new CustomPasswordField();
        this.inputPasswordField.setBounds(25, 190, 335, 40);

        this.confirmInputPassword = new JLabel("Xác nhận mật khẩu:");
        this.confirmInputPassword.setBounds(25, 240, 335, 30);

        this.confirmInputPasswordField = new CustomPasswordField();
        this.confirmInputPasswordField.setBounds(25, 270, 335, 40);

        this.add(this.titleForm);
        this.add(this.inputOldPassword);
        this.add(this.inputOldPasswordField);
        this.add(this.inputPassword);
        this.add(this.inputPasswordField);
        this.add(this.confirmInputPassword);
        this.add(this.confirmInputPasswordField);

        // Khởi tạo hai nút nhưng không thêm vào form để bạn tự set
        this.confirmButton = Utils.Helper.CreateComponent.createGreenButton("Xác nhận");
        this.confirmButton.addActionListener(e -> {
            this.changePassword();
        });
        this.confirmButton.setBounds(25, 380, 100, 30);

        this.cancelButton = Utils.Helper.CreateComponent.createOrangeButton("Hủy");
        this.cancelButton.addActionListener(e -> {
            this.dispose();
        });
        this.cancelButton.setBounds(140, 380, 100, 30);


        this.add(this.confirmButton);
        this.add(this.cancelButton);
    }

    private void changePassword() {
        int resultAnswer = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thay đổi mật khẩu ?", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        if (resultAnswer == JOptionPane.YES_OPTION) {
            String currnetPassword = new String(this.inputOldPasswordField.getPassword());
            String newPassword = new String(this.inputPasswordField.getPassword());
            String confirmPassword = new String(this.confirmInputPasswordField.getPassword());

            if (newPassword.length() < 8 || confirmPassword.length() < 8) {
                JOptionPane.showMessageDialog(this, "Mật khẩu mới phải có ít nhất 8 kí tự", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (newPassword.equals(confirmPassword) != true) {
                JOptionPane.showMessageDialog(this, "Mật khẩu mới và mật khẩu xác nhận phải giống nhau", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean resultUpdate = new AccountBLL().updatePasswordAccountById(this.editAccounts.getAccountId(), currnetPassword, newPassword);
            if (resultUpdate) {
                JOptionPane.showMessageDialog(this, "Cập nhật mật khẩu mới thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            else JOptionPane.showMessageDialog(this, "Vui lòng nhật đúng mật khẩu cũ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new DetailsAuthenticator(null).setVisible(true);
    }
}
