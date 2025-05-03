package GUI.Form;

import BLL.AccountBLL;
import BLL.StaffBLL;
import DTO.Accounts;
import DTO.Staffs;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomPasswordField;
import GUI.Components.CustomTextField;

import javax.swing.*;
import java.util.HashMap;

public class EditAccount extends JFrame {
    private final int accountId;
    private final AccountBLL accountBLL;
    private final StaffBLL staffBLL;

    public EditAccount(int accountId) {
        this.accountId = accountId;
        this.accountBLL = new AccountBLL();
        this.staffBLL = new StaffBLL();
        initComponents();
    }

    private void initComponents() {
        setTitle("Chỉnh sửa thông tin nhân viên");
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") +"/src/Assets/Icon/icons8-details-100.png").getImage());
        setSize(500, 370);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Accounts account = accountBLL.getAccountById(accountId);
        Staffs staff = staffBLL.getStaffByAccountId(accountId);

        // Họ và tên
        JLabel nameLabel = new JLabel("Họ và tên:");
        nameLabel.setBounds(30, 20, 100, 25);
        add(nameLabel);

        CustomTextField nameField = new CustomTextField(staff.getFullName());
        nameField.setBounds(150, 20, 300, 30);
        add(nameField);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 65, 100, 25);
        add(emailLabel);

        CustomTextField emailField = new CustomTextField(staff.getEmail());
        emailField.setBounds(150, 65, 300, 30);
        add(emailField);

        // Trạng thái
        JLabel statusLabel = new JLabel("Trạng thái:");
        statusLabel.setBounds(30, 110, 100, 25);
        add(statusLabel);

        String[] statuses = {"Online", "Offline", "Locked"};
        CustomCombobox<String> statusCombo = new CustomCombobox<>(statuses);
        statusCombo.setSelectedItem(account.getStatus());
        statusCombo.setBounds(150, 110, 300, 30);
        add(statusCombo);

        // Quyền hạn
        JLabel roleLabel = new JLabel("Quyền hạn:");
        roleLabel.setBounds(30, 155, 100, 25);
        add(roleLabel);

        String[] roles = {"Nhân viên", "Quản trị viên"};
        CustomCombobox<String> roleCombo = new CustomCombobox<>(roles);
        roleCombo.setSelectedItem(account.getRole());
        roleCombo.setBounds(150, 155, 300, 30);
        add(roleCombo);

        // Mật khẩu
        JLabel passwordLabel = new JLabel("Mật khẩu mới:");
        passwordLabel.setBounds(30, 200, 100, 25);
        add(passwordLabel);

        CustomPasswordField passwordField = new CustomPasswordField();
        passwordField.setBounds(150, 200, 300, 30);
        add(passwordField);

        // Nút Thay đổi
        CustomButton updateBtn = new CustomButton("Thay đổi");
        updateBtn.setBounds(150, 260, 90, 35);
        updateBtn.addActionListener(e -> {
            String fullName = nameField.getText();
            String email = emailField.getText();
            String status = statusCombo.getSelectedItem().toString();
            String role = roleCombo.getSelectedItem().toString();
            String password = new String(passwordField.getPassword());

            // Cập nhật thông tin dễ trước
            HashMap<String, Object> updateValues = new HashMap<>();
            updateValues.put("full_name", fullName);
            updateValues.put("email", email);
            this.staffBLL.updateDetailsInfoStaffById(staff.getStaffId(), updateValues);

            if (fullName.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Họ tên và mật khẩu không được để trống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (!password.isEmpty()) {
                if (password.length() < 8) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu ít nhất 8 kí tự", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int resultAnswer = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thay đổi ?", "Thông báo", JOptionPane.OK_CANCEL_OPTION);
                if (resultAnswer == JOptionPane.OK_OPTION) {
                    HashMap<String, Object> values = new HashMap<>();
                    values.put("role", role);
                    values.put("status", status);
                    values.put("password", Utils.Helper.EncriptString.MD5String(password));
                    boolean update = this.accountBLL.updateAccountDetailsById(account.getAccountId(), values);
                    if (update) {
                        JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật thông tin thất bại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }

                    return;
                }
            }

            int resultAnswer = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thay đổi ?", "Thông báo", JOptionPane.OK_CANCEL_OPTION);
            if (resultAnswer == JOptionPane.OK_OPTION) {
                HashMap<String, Object> values = new HashMap<>();
                values.put("role", role);
                values.put("status", status);
                boolean update =  this.accountBLL.updateAccountDetailsById(account.getAccountId(), values);
                if (update) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin thất bại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
                return;
            }


        });
        add(updateBtn);

        // Nút Hủy bỏ
        CustomButton cancelBtn = new CustomButton("Hủy bỏ");
        cancelBtn.setBounds(260, 260, 90, 35);
        add(cancelBtn);

        // Nút Đặt lại
        CustomButton resetBtn = new CustomButton("Đặt lại");
        resetBtn.setBounds(360, 260, 90, 35);
        add(resetBtn);

        // Hủy bỏ
        cancelBtn.addActionListener(e -> dispose());

        // Đặt lại
        resetBtn.addActionListener(e -> {
            nameField.setText(staff.getFullName());
            emailField.setText(staff.getEmail());
            statusCombo.setSelectedItem(account.getStatus());
            roleCombo.setSelectedItem(account.getRole());
            passwordField.setText("");
        });

        // "Thay đổi" chưa xử lý cập nhật DB

    }

}
