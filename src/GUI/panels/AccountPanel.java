package GUI.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.*;

import Utils.Helper.CreateComponent;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomPanel;
import GUI.Components.CustomTextField;

public class AccountPanel extends JPanel {
    private final Font fontSans15 = new Font("Sans", Font.BOLD, 15);
//    private Accounts currentAccount;
//    private Staffs currentStaff;

    private CardLayout cardLayout;

    private JPanel buttonControllPanel;
    private JPanel showInfoPanel;

    private JLabel avatarEmployee;

    public AccountPanel() {
        this.initComponents();
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titlePanel = new JLabel("QUẢN LÝ TÀI KHOẢN");
        titlePanel.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        titlePanel.setBounds(420, 30, 400, 50);

        CustomButton selfInfoButton = CreateComponent.createButtonNoIcon("Thông tin tài khoản");
        selfInfoButton.setBounds(20, 100, 175, 35);
        selfInfoButton.addActionListener(e -> this.cardLayout.show(this.showInfoPanel, "MyInfo"));

        CustomButton playerAccountButton = CreateComponent.createButtonNoIcon("Tài khoản người chơi");
        playerAccountButton.setBounds(210, 100, 175, 35);
        playerAccountButton.addActionListener(e -> this.cardLayout.show(this.showInfoPanel, "PlayerInfo"));

        CustomButton employeeButton = CreateComponent.createButtonNoIcon("Tài khoản nhân viên");
        employeeButton.setBounds(400, 100, 175, 35);
        employeeButton.addActionListener(e -> this.cardLayout.show(this.showInfoPanel, "EmployeeInfo"));

        panel.add(titlePanel);
        panel.add(selfInfoButton);
        panel.add(playerAccountButton);
        panel.add(employeeButton);

        return panel;
    }

    private CustomPanel createStaffInfoPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        // Ảnh đại diện
        this.avatarEmployee = new JLabel();
        this.updateAvatar(this.getAvatarPath());
        this.avatarEmployee.setBounds(80, 30, 200, 200);
        this.avatarEmployee.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Nút chọn ảnh
        CustomButton btnChooseFile = new CustomButton("Chọn Ảnh");
        btnChooseFile.setBounds(130, 240, 100, 25);
        btnChooseFile.addActionListener(e -> chooseImage());

        // Tên người dùng
        JLabel myUsername = new JLabel("Tên người dùng:");
        myUsername.setFont(fontSans15);
        myUsername.setBounds(35, 270, 200, 30);

        CustomTextField myUsernameInput = new CustomTextField("namduongit");
        myUsernameInput.setBounds(30, 300, 300, 35);

        // Email
        JLabel myEmail = new JLabel("Địa chỉ email:");
        myEmail.setFont(fontSans15);
        myEmail.setBounds(35, 350, 200, 30);

        CustomTextField myEmailInput = new CustomTextField("nguyennamduong205@gmail.com");
        myEmailInput.setBounds(30, 380, 300, 35);

        // Ngày tạo tài khoản
        JLabel createAtLabel = new JLabel("Ngày tạo tài khoản:");
        createAtLabel.setFont(fontSans15);
        createAtLabel.setBounds(35, 430, 200, 30);

        CustomTextField myCreateAtInput = new CustomTextField("2025-01-01");
        myCreateAtInput.setBounds(30, 460, 300, 35);
        myCreateAtInput.setEnabled(false);

        // Đường kẻ thứ nhất
        JLabel firstLine = new JLabel();
        firstLine.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") +"/src/Assets/Image/card_black.png").getImage().getScaledInstance(10, 500, Image.SCALE_SMOOTH)));
        firstLine.setBounds(380, 10, 1, 490);

        // Họ và tên
        JLabel myName = new JLabel("Họ và tên:");
        myName.setFont(fontSans15);
        myName.setBounds(430, 30, 200, 30);

        CustomTextField myNameInput = new CustomTextField("Nguyễn Nam Dương");
        myNameInput.setBounds(430, 60, 300, 35);

        // Ngày sinh
        JLabel myBirthdate = new JLabel("Ngày sinh:");
        myBirthdate.setFont(fontSans15);
        myBirthdate.setBounds(430, 110, 200, 30);

        CustomTextField myBirthdateInput = new CustomTextField("14-02-2005");
        myBirthdateInput.setBounds(430, 140, 300, 35);

        // Số điện thoại
        JLabel myNumberPhone = new JLabel("Số điện thoại:");
        myNumberPhone.setFont(fontSans15);
        myNumberPhone.setBounds(430, 190, 200, 30);

        CustomTextField myNumberPhoneInput = new CustomTextField("0388 853 835");
        myNumberPhoneInput.setBounds(430, 220, 300, 35);

        // CCCD
        JLabel myCCCD = new JLabel("Số căn cước công dân:");
        myCCCD.setFont(fontSans15);
        myCCCD.setBounds(430, 270, 200, 30);

        CustomTextField myCCCDInput = new CustomTextField("075 200 200 200");
        myCCCDInput.setBounds(430, 300, 300, 35);

        // Giới tính
        JLabel myGender = new JLabel("Giới tính:");
        myGender.setFont(fontSans15);
        myGender.setBounds(430, 350, 200, 30);

        String[] genders = {"Chưa có", "Nam", "Nữ"};
        CustomCombobox<String> myGenderInput = new CustomCombobox<>(genders);
        myGenderInput.setBounds(430, 380, 300, 35);
        myGenderInput.setSelectedIndex(0);

        // Địac chỉ
        JLabel myAddress = new JLabel("Địa chỉ:");
        myAddress.setFont(fontSans15);
        myAddress.setBounds(430, 430, 200, 30);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(430, 460, 300, 35);

        CustomTextField myAddressInput = new CustomTextField("Long Khánh, Đồng Nai");
        myAddressInput.setBounds(0, 0, 300, 35);

        CustomButton editAddress = new CustomButton("Chi tiết");
        editAddress.setBounds(200, 0, 100, 35);
        editAddress.setForeground(Color.WHITE);
        editAddress.setBorderColor(Color.WHITE);
        editAddress.setBackground(Color.decode("#1976D2"));

        layeredPane.add(myAddressInput, Integer.valueOf(1));
        layeredPane.add(editAddress, Integer.valueOf(2));

        // Đường kẻ thứ 2
        JLabel secondLine = new JLabel();
        secondLine.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") +"/src/Assets/Image/card_black.png").getImage().getScaledInstance(10, 500, Image.SCALE_SMOOTH)));
        secondLine.setBounds(780, 10, 1, 490);

        // Nút đổi mật khẩu
        CustomButton changePassword = new CustomButton("Đổi mật khẩu");
        changePassword.setBounds(830, 60, 200, 30);

        CustomButton changeMyInfo = new CustomButton("Cập nhật thông tin");
        changeMyInfo.setBounds(830, 110, 200, 30);
        // Nút lưu thông tin



        // Thêm các thành phần vào panel
        panel.add(avatarEmployee);
        panel.add(btnChooseFile);
        panel.add(myUsername);
        panel.add(myUsernameInput);
        panel.add(myEmail);
        panel.add(myEmailInput);
        panel.add(createAtLabel);
        panel.add(myCreateAtInput);
        panel.add(firstLine);
        panel.add(myName);
        panel.add(myNameInput);
        panel.add(myBirthdate);
        panel.add(myBirthdateInput);
        panel.add(myNumberPhone);
        panel.add(myNumberPhoneInput);
        panel.add(myCCCD);
        panel.add(myCCCDInput);
        panel.add(myGender);
        panel.add(myAddress);
        panel.add(myGenderInput);
        panel.add(layeredPane);
        panel.add(secondLine);
        panel.add(changePassword);
        panel.add(changeMyInfo);

        return panel;
    }


    private JPanel createShowInfo() {
        this.cardLayout = new CardLayout();
        JPanel panel = new JPanel(this.cardLayout);
        panel.setBackground(Color.WHITE);

        CustomPanel selfInfoPanel = this.createStaffInfoPanel();

        CustomPanel playerAccountPanel = new CustomPanel();

        CustomPanel employeePanel = new CustomPanel();

        panel.add(selfInfoPanel, "MyInfo");
        panel.add(playerAccountPanel, "PlayerInfo");
        panel.add(employeePanel, "EmployeeInfo");
        return panel;
    }

    private void initComponents() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.buttonControllPanel = this.createButtonPanel();
        this.showInfoPanel = this.createShowInfo();

        buttonControllPanel.setBounds(0, 0, 1116, 170);
        showInfoPanel.setBounds(0, 170, 1116, 598);

        this.add(buttonControllPanel);
        this.add(showInfoPanel);
    }

    private String getAvatarPath() {
        String avatarFile = "getAvatar";
        String basePath = System.getProperty("user.dir") + "/src/Assets/Avatar/";

        if (avatarFile == null || avatarFile.isEmpty()) {
            return basePath + "non_avatar.png";
        }

        File file = new File(basePath + avatarFile);
        return file.exists() ? file.getAbsolutePath() : basePath + "non_avatar.png";
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh PNG");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Images", "png"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            saveNewAvatar(selectedFile);
        }
    }

    private void saveNewAvatar(File selectedFile) {
        try {
            String oldAvatarFile = "getAvatar";
            String basePath = System.getProperty("user.dir") + "/src/Assets/Avatar/";

            if (oldAvatarFile != null && !oldAvatarFile.isEmpty() && !oldAvatarFile.equals("non_avatar.png")) {
                File oldFile = new File(basePath + oldAvatarFile);
                if (oldFile.exists()) {
                    boolean result = oldFile.delete();
                    if (result) System.out.println("Xóa thành công");
                    else System.out.println("Xóa thất bại");
                }
            }

            String newFileName = "avatar_" + "1" + ".png";
            File destination = new File(basePath + newFileName);

            Files.copy(selectedFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

            updateAvatar(destination.getAbsolutePath());

            JOptionPane.showMessageDialog(null, "Ảnh đại diện đã được cập nhật!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu ảnh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAvatar(String path) {
        ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        avatarEmployee.setIcon(icon);
    }
}
