package GUI.Form;

import BLL.AccountBLL;
import BLL.StaffBLL;
import DTO.Accounts;
import DTO.Staffs;
import GUI.Components.CustomTextField;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;

import static java.awt.Color.BLACK;

public class DetailsAccount extends JFrame {
    private int accountId;

    private AccountBLL accountBLL;
    private StaffBLL staffBLL;
    public DetailsAccount(int accountId) {
        this.accountId = accountId;

        this.accountBLL = new AccountBLL();
        this.staffBLL = new StaffBLL();
        this.initComponents();
    }

    private void initComponents() {
        this.setTitle("Thông tin chi tiết nhân viên");
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") +"/src/Assets/Icon/icons8-details-100.png").getImage());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 350);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        Accounts accounts = this.accountBLL.getAccountById(this.accountId);
        Staffs staffs = this.staffBLL.getStaffByAccountId(accounts.getAccountId());

        String avatar = staffs.getAvatar();
        File testFindFile = new File(System.getProperty("user.dir") +"/src/Assets/Avatar/"+ avatar);

        String avatarPath = testFindFile.exists() ? testFindFile.getPath() : System.getProperty("user.dir") +"/src/Assets/Avatar/non_avatar.png";

        JLabel avatarLabel = new JLabel();

        ImageIcon avatarIcon = new ImageIcon(
                new ImageIcon(avatarPath).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH)
        );
        avatarLabel.setIcon(avatarIcon);
        avatarLabel.setBounds(20, 20, 130, 130);
        avatarLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel fullNameLabel = new JLabel("Họ và tên");
        fullNameLabel.setBounds(190, 20, 270, 20);
        this.add(fullNameLabel);

        CustomTextField fullNameTextField = new CustomTextField(staffs.getFullName());
        fullNameTextField.setBounds(190, 45, 270, 30);
        fullNameTextField.setEditable(false);
        this.add(fullNameTextField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(190, 80, 270, 25);
        this.add(emailLabel);

        CustomTextField emailTextField = new CustomTextField(staffs.getEmail());
        emailTextField.setBounds(190, 105, 270, 30);
        emailTextField.setEditable(false);
        this.add(emailTextField);

        JLabel usernameLabel = new JLabel("Tên đăng nhập");
        usernameLabel.setBounds(20, 170, 200, 25);
        this.add(usernameLabel);

        CustomTextField usernameTextField = new CustomTextField(accounts.getUsername());
        usernameTextField.setBounds(20, 195, 200, 30);
        usernameTextField.setEditable(false);
        this.add(usernameTextField);

        JLabel roleLabel = new JLabel("Quyền hạn");
        roleLabel.setBounds(20, 230, 200, 25);
        this.add(roleLabel);

        CustomTextField roleTextField = new CustomTextField(accounts.getRole());
        roleTextField.setBounds(20, 255, 200, 30);
        roleTextField.setEditable(false);
        this.add(roleTextField);

        JLabel addressLabel = new JLabel("Địa chỉ");
        addressLabel.setBounds(240, 170, 200, 25);
        this.add(addressLabel);

        CustomTextField addressTextField = new CustomTextField(staffs.getAddress());
        addressTextField.setBounds(240, 195, 200, 30);
        addressTextField.setEditable(false);
        this.add(addressTextField);

        JLabel genderLabel = new JLabel("Giới tính");
        genderLabel.setBounds(240, 230, 200, 30);
        this.add(genderLabel);

        CustomTextField genderTextField = new CustomTextField(staffs.getGender());
        genderTextField.setBounds(240, 255, 200, 30);
        genderTextField.setEditable(false);
        this.add(genderTextField);

        this.add(avatarLabel);

    }
}
