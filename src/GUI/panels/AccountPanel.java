package GUI.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import DTO.Accounts;
import DTO.Staffs;
import Utils.Helper.CreateComponent;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomPanel;
import GUI.Components.CustomScrollPane;
import GUI.Components.CustomTable;
import GUI.Components.CustomTextField;

public class AccountPanel extends JPanel {
private final Font fontSans15 = new Font("Sans", Font.BOLD, 15);
   private Accounts loginAccount;
   private Staffs loginStaff;

    private CardLayout cardLayout;

    private JPanel buttonControllPanel;
    private JPanel showInfoPanel;

    private JLabel avatarEmployee;

    public AccountPanel(Accounts loginAccount, Staffs loginStaff) {
        this.loginAccount = loginAccount;
        this.loginStaff = loginStaff;
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

        if (this.loginAccount.getRole().equalsIgnoreCase("Quản trị viên")) {
            CustomButton employeeButton = CreateComponent.createButtonNoIcon("Tài khoản nhân viên");
            employeeButton.setBounds(400, 100, 175, 35);
            employeeButton.addActionListener(e -> this.cardLayout.show(this.showInfoPanel, "EmployeeInfo"));
            panel.add(employeeButton);
        }

        panel.add(titlePanel);
        panel.add(selfInfoButton);
        panel.add(playerAccountButton);

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

        CustomTextField myUsernameInput = new CustomTextField(this.loginAccount.getUsername());
        myUsernameInput.setBounds(30, 300, 300, 35);

        // Email
        JLabel myEmail = new JLabel("Địa chỉ email:");
        myEmail.setFont(fontSans15);
        myEmail.setBounds(35, 350, 200, 30);

        CustomTextField myEmailInput = new CustomTextField(this.loginStaff.getEmail());
        myEmailInput.setBounds(30, 380, 300, 35);

        // Ngày tạo tài khoản
        JLabel createAtLabel = new JLabel("Ngày tạo tài khoản:");
        createAtLabel.setFont(fontSans15);
        createAtLabel.setBounds(35, 430, 200, 30);

        CustomTextField myCreateAtInput = new CustomTextField(this.loginAccount.getCreatedAt().toString());
        myCreateAtInput.setBounds(30, 460, 300, 35);
        myCreateAtInput.setEditable(false);

        // Đường kẻ thứ nhất
        // JLabel firstLine = new JLabel();
        // firstLine.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") +"/src/Assets/Image/card_black.png").getImage().getScaledInstance(10, 500, Image.SCALE_SMOOTH)));
        // firstLine.setBounds(380, 10, 1, 490);

        // Họ và tên
        JLabel myName = new JLabel("Họ và tên:");
        myName.setFont(fontSans15);
        myName.setBounds(430, 30, 200, 30);

        CustomTextField myNameInput = new CustomTextField(this.loginStaff.getFullName());
        myNameInput.setBounds(430, 60, 300, 35);

        // Ngày sinh
        JLabel myBirthdate = new JLabel("Ngày sinh:");
        myBirthdate.setFont(fontSans15);
        myBirthdate.setBounds(430, 110, 200, 30);

        CustomTextField myBirthdateInput = new CustomTextField(this.loginStaff.getBirthDate().toString());
        myBirthdateInput.setBounds(430, 140, 300, 35);

        // Số điện thoại
        JLabel myNumberPhone = new JLabel("Số điện thoại:");
        myNumberPhone.setFont(fontSans15);
        myNumberPhone.setBounds(430, 190, 200, 30);

        CustomTextField myNumberPhoneInput = new CustomTextField(this.loginStaff.getPhone());
        myNumberPhoneInput.setBounds(430, 220, 300, 35);

        // CCCD
        JLabel myCCCD = new JLabel("Số căn cước công dân:");
        myCCCD.setFont(fontSans15);
        myCCCD.setBounds(430, 270, 200, 30);

        CustomTextField myCCCDInput = new CustomTextField(this.loginStaff.getCccd());
        myCCCDInput.setBounds(430, 300, 300, 35);

        // Giới tính
        JLabel myGender = new JLabel("Giới tính:");
        myGender.setFont(fontSans15);
        myGender.setBounds(430, 350, 200, 30);

        String[] genders = {"Chưa đặt", "Nam", "Nữ"};
        CustomCombobox<String> myGenderInput = new CustomCombobox<>(genders);
        myGenderInput.setBounds(430, 380, 300, 35);
        String currentGender = loginStaff.getGender();
        int index = 0;
        for (int i = 0; i < genders.length; i++) {
            if (genders[i].equalsIgnoreCase(currentGender)) {
                index = i;
                break;
            }
        }
        myGenderInput.setSelectedIndex(index);

        // Địa chỉ
        JLabel myAddress = new JLabel("Địa chỉ:");
        myAddress.setFont(fontSans15);
        myAddress.setBounds(430, 430, 200, 30);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(430, 460, 300, 35);

        CustomTextField myAddressInput = new CustomTextField(this.loginStaff.getAddress());
        myAddressInput.setBounds(0, 0, 300, 35);

        CustomButton editAddress = new CustomButton("Chi tiết");
        editAddress.setBounds(200, 0, 100, 35);
        editAddress.setForeground(Color.WHITE);
        editAddress.setBorderColor(Color.WHITE);
        editAddress.setBackground(Color.decode("#1976D2"));

        layeredPane.add(myAddressInput, Integer.valueOf(1));
        layeredPane.add(editAddress, Integer.valueOf(2));

        // Nút đổi mật khẩu
        CustomButton changePassword = new CustomButton("Đổi mật khẩu");
        changePassword.setBounds(830, 60, 200, 30);

         // Nút lưu thông tin
        CustomButton changeMyInfo = new CustomButton("Cập nhật thông tin");
        changeMyInfo.setBounds(830, 110, 200, 30);

        // Thêm các thành phần vào panel
        panel.add(avatarEmployee);
        panel.add(btnChooseFile);
        panel.add(myUsername);
        panel.add(myUsernameInput);
        panel.add(myEmail);
        panel.add(myEmailInput);
        panel.add(createAtLabel);
        panel.add(myCreateAtInput);
        // panel.add(firstLine);
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
        // panel.add(secondLine);
        panel.add(changePassword);
        panel.add(changeMyInfo);

        return panel;
    }

    private CustomPanel createEmployeeInfoPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        CustomPanel findDataPanel = new CustomPanel();
        findDataPanel.setLayout(null);
        findDataPanel.setBackground(Color.WHITE);
        findDataPanel.setBounds(10, 0, 1080, 80);

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(20, 20, 80, 30);
        CustomTextField searchField = new CustomTextField();
        searchField.setBounds(100, 20, 200, 30);

        JLabel statusLabel = new JLabel("Trạng thái:");
        statusLabel.setBounds(320, 20, 80, 30);
        String[] statuses = {"Tất cả", "Hoạt động", "Bị khóa"};
        CustomCombobox<String> statusComboBox = new CustomCombobox<>(statuses);
        statusComboBox.setBounds(400, 20, 150, 30);

        JLabel roleLabel = new JLabel("Quyền:");
        roleLabel.setBounds(570, 20, 50, 30);
        String[] roles = {"Tất cả", "Quản trị viên", "Người dùng"};
        CustomCombobox<String> roleComboBox = new CustomCombobox<>(roles);
        roleComboBox.setBounds(620, 20, 150, 30);

        CustomButton filterButton = new CustomButton("Lọc");
        filterButton.setBounds(800, 15, 80, 30);
        filterButton.setBackground(new Color(70, 130, 180));
        filterButton.setForeground(Color.WHITE);
        filterButton.setBorderSize(0);
        filterButton.setBorderColor(new Color(70, 130, 180));

        CustomButton resetButton = new CustomButton("Đặt lại");
        resetButton.setBounds(890, 15, 80, 30);
        resetButton.setBackground(Color.RED);
        resetButton.setForeground(Color.WHITE);
        resetButton.setBorderSize(0);
        resetButton.setBorderColor(Color.RED);

        findDataPanel.add(searchLabel);
        findDataPanel.add(searchField);
        findDataPanel.add(statusLabel);
        findDataPanel.add(statusComboBox);
        findDataPanel.add(roleLabel);
        findDataPanel.add(roleComboBox);
        findDataPanel.add(filterButton);
        findDataPanel.add(resetButton);

        CustomPanel tableDataPanel = new CustomPanel();
        tableDataPanel.setLayout(null);
        tableDataPanel.setBackground(Color.WHITE);
        tableDataPanel.setBounds(10, 90, 1080, 400);

        String[] columnNames = {"Mã tài khoản", "Tên đăng nhập", "Họ và tên", "Chức vụ", "Trạng thái"};
        Object[][] data = {
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"},
            {"TK001", "namduongit", "Nguyễn Nam Dương", "Quản trị viên", "Đang hoạt động"}
        };

        CustomTable table = new CustomTable(new DefaultTableModel(data, columnNames));
        CustomScrollPane scrollPane = new CustomScrollPane(table);
        scrollPane.setBounds(0, 0, 1080, 400);
        tableDataPanel.add(scrollPane);

        CustomPanel buttonPanel = new CustomPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(10, 500, 1080, 65);
        buttonPanel.setBackground(Color.WHITE);

        CustomButton addButton = new CustomButton("Thêm");
        addButton.setBounds(10, 20, 100, 30);
        addButton.setBackground(new Color(34, 177, 76));
        addButton.setForeground(Color.WHITE);
        addButton.setBorderSize(0);
        addButton.setBorderColor(new Color(34, 177, 76));

        CustomButton editButton = new CustomButton("Thay đổi");
        editButton.setBounds(120, 20, 100, 30);
        editButton.setBackground(Color.decode("#795548"));
        editButton.setForeground(Color.WHITE);
        editButton.setBorderSize(0);
        editButton.setBorderColor(Color.decode("#795548"));

        CustomButton lockButton = new CustomButton("Khóa");
        lockButton.setBounds(230, 20, 100, 30);
        lockButton.setBackground(Color.decode("#E57373"));
        lockButton.setForeground(Color.WHITE);
        lockButton.setBorderSize(0);
        lockButton.setBorderColor(Color.decode("#E57373"));

        CustomButton detailButton = new CustomButton("Chi tiết");
        detailButton.setBounds(340, 20, 100, 30);
        detailButton.setBackground(Color.decode("#455A64"));
        detailButton.setForeground(Color.WHITE);
        detailButton.setBorderColor(Color.decode("#455A64"));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(lockButton);
        buttonPanel.add(detailButton);

        panel.add(findDataPanel);
        panel.add(tableDataPanel);
        panel.add(buttonPanel);

        return panel;
    }

    private CustomPanel createPlayerInfoPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        CustomPanel findDataPanel = new CustomPanel();
        findDataPanel.setLayout(null);
        findDataPanel.setBackground(Color.WHITE);
        findDataPanel.setBounds(10, 0, 1080, 80);

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(20, 20, 80, 30);
        CustomTextField searchField = new CustomTextField();
        searchField.setBounds(100, 20, 200, 30);

        JLabel statusLabel = new JLabel("Trạng thái:");
        statusLabel.setBounds(320, 20, 80, 30);
        String[] statuses = {"Tất cả", "Hoạt động", "Bị khóa"};
        CustomCombobox<String> statusComboBox = new CustomCombobox<>(statuses);
        statusComboBox.setBounds(400, 20, 150, 30);

        CustomButton filterButton = new CustomButton("Lọc");
        filterButton.setBounds(600, 15, 80, 30);
        filterButton.setBackground(new Color(70, 130, 180));
        filterButton.setForeground(Color.WHITE);
        filterButton.setBorderSize(0);
        filterButton.setBorderColor(new Color(70, 130, 180));

        CustomButton resetButton = new CustomButton("Đặt lại");
        resetButton.setBounds(690, 15, 80, 30);
        resetButton.setBackground(Color.RED);
        resetButton.setForeground(Color.WHITE);
        resetButton.setBorderSize(0);
        resetButton.setBorderColor(Color.RED);

        findDataPanel.add(searchLabel);
        findDataPanel.add(searchField);
        findDataPanel.add(statusLabel);
        findDataPanel.add(statusComboBox);
        findDataPanel.add(filterButton);
        findDataPanel.add(resetButton);

        CustomPanel tableDataPanel = new CustomPanel();
        tableDataPanel.setLayout(null);
        tableDataPanel.setBackground(Color.WHITE);
        tableDataPanel.setBounds(10, 90, 1080, 400);

        String[] columnNames = {"Mã tài khoản", "Tên đăng nhập", "Số dư", "Trạng thái"};
        Object[][] data = {
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"},
            {"TK001", "namduongit", "10000000", "Đang hoạt động"}
        };

        CustomTable table = new CustomTable(new DefaultTableModel(data, columnNames));
        CustomScrollPane scrollPane = new CustomScrollPane(table);
        scrollPane.setBounds(0, 0, 1080, 400);
        tableDataPanel.add(scrollPane);

        CustomPanel buttonPanel = new CustomPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(10, 500, 1080, 65);
        buttonPanel.setBackground(Color.WHITE);

        CustomButton addButton = new CustomButton("Thêm");
        addButton.setBounds(10, 20, 100, 30);
        addButton.setBackground(new Color(34, 177, 76));
        addButton.setForeground(Color.WHITE);
        addButton.setBorderSize(0);
        addButton.setBorderColor(new Color(34, 177, 76));

        CustomButton editButton = new CustomButton("Thay đổi");
        editButton.setBounds(120, 20, 100, 30);
        editButton.setBackground(Color.decode("#795548"));
        editButton.setForeground(Color.WHITE);
        editButton.setBorderSize(0);
        editButton.setBorderColor(Color.decode("#795548"));

        CustomButton lockButton = new CustomButton("Khóa");
        lockButton.setBounds(230, 20, 100, 30);
        lockButton.setBackground(Color.decode("#E57373"));
        lockButton.setForeground(Color.WHITE);
        lockButton.setBorderSize(0);
        lockButton.setBorderColor(Color.decode("#E57373"));

        CustomButton detailButton = new CustomButton("Chi tiết");
        detailButton.setBounds(340, 20, 100, 30);
        detailButton.setBackground(Color.decode("#455A64"));
        detailButton.setForeground(Color.WHITE);
        detailButton.setBorderSize(0);
        detailButton.setBorderColor(Color.decode("#455A64"));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(lockButton);
        buttonPanel.add(detailButton);

        panel.add(findDataPanel);
        panel.add(tableDataPanel);
        panel.add(buttonPanel);

        return panel;
    }

    private JPanel createShowInfo() {
        this.cardLayout = new CardLayout();
        JPanel panel = new JPanel(this.cardLayout);
        panel.setBackground(Color.WHITE);

        CustomPanel selfInfoPanel = this.createStaffInfoPanel();

        CustomPanel playerAccountPanel = this.createPlayerInfoPanel();

        CustomPanel employeePanel = this.createEmployeeInfoPanel();

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

        buttonControllPanel.setBounds(0, 0, 1116, 150);
        showInfoPanel.setBounds(0, 150, 1116, 618);

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
