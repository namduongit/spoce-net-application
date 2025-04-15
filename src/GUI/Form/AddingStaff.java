package GUI.Form;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import BLL.AccountBLL;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomPasswordField;
import GUI.Components.CustomTextField;

public class AddingStaff extends JFrame {
    private AccountBLL accountBLL;

    private JLabel titleForm;
    private JLabel inputUsernameLabel;
    private JLabel inputPasswordLabel;
    private JLabel confirmPasswordLabel;
    private JLabel inputRoleLabel;

    private CustomTextField inputUsernameField;
    private CustomPasswordField inputPasswordField;
    private CustomPasswordField confirmPasswordField;
    private CustomCombobox<String> inputRoleField;

    private CustomButton confirmButton;
    private CustomButton cancelButton;

    public AddingStaff() {
        this.accountBLL = new AccountBLL();
        this.initComponents();
    }

    private void initComponents() {
        this.setSize(400, 550);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Thêm nhân viên");
        this.setIconImage(
                new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Icon/icons8-create-100.png").getImage());

        this.titleForm = new JLabel("Thêm nhân viên");
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

        this.inputRoleLabel = new JLabel("Quyền hạn:");
        this.inputRoleLabel.setBounds(25, 310, 335, 30);

        String[] roles = { "Nhân viên", "Quản trị viên" };
        this.inputRoleField = new CustomCombobox<>(roles);
        this.inputRoleField.setBounds(25, 340, 335, 40);

        this.confirmButton = Utils.Helper.CreateComponent.createGreenButton("Xác nhận");
        this.confirmButton.setBounds(25, 420, 100, 30);
        this.confirmButton.addActionListener(e -> {
            this.confirmAddingStaff();
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
        this.add(this.inputRoleLabel);
        this.add(this.inputRoleField);
        this.add(this.confirmButton);
        this.add(this.cancelButton);
    }

    private void confirmAddingStaff() {
        String username = this.inputUsernameField.getText();
        String password = new String(this.inputPasswordField.getPassword());
        String confirmPassword = new String(this.confirmPasswordField.getPassword());
        String role = this.inputRoleField.getSelectedItem().toString();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Mật khẩu có ít nhất 8 kí tự", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (role.equals("Quản trị viên")) {
            int resultAnswer = JOptionPane.showConfirmDialog(this,
                "Bạn đang thêm tài khoản Quản trị viên\nBạn có chắc chắn không ?", 
                "Thông báo",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            if (resultAnswer == JOptionPane.OK_OPTION) {
                if (this.accountBLL != null) {
                    boolean createStaff = this.accountBLL.createStaffAccount(username, confirmPassword, role);
                    if (createStaff) {
                        JOptionPane.showMessageDialog(this, "Tài khoản đã được tạo thành công!", "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Tạo tài khoản thất bại!", "Thông báo",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                return;
            }
        } else {
            boolean createStaff = this.accountBLL.createStaffAccount(username, confirmPassword, role);
            if (createStaff) {
                JOptionPane.showMessageDialog(this, "Tài khoản đã được tạo thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản thất bại!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }
        }

    }

}
