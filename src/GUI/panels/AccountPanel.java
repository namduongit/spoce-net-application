package GUI.panels;

import BLL.AccountBLL;
import BLL.StaffBLL;
import DTO.Accounts;
import DTO.Staffs;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomPanel;
import GUI.Components.CustomScrollPane;
import GUI.Components.CustomTable;
import GUI.Components.CustomTextField;
import GUI.Form.AddingPlayer;
import GUI.Form.AddingStaff;
import GUI.Form.DetailsAddress;
import GUI.Form.DetailsAuthenticator;
import GUI.Form.EditingPlayerForm;
import Utils.Helper.CreateComponent;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class AccountPanel extends JPanel {
    private final Font fontSans15 = new Font("Sans", Font.BOLD, 15);
    private AccountBLL accountBLL;
    private StaffBLL staffBLL;

    private Accounts loginAccount;
    private Staffs loginStaff;

    private CardLayout cardLayout;

    private JPanel buttonControllPanel;
    private JPanel showInfoPanel;

    private JLabel avatarEmployee;

    // Các trường dành cho tài khoản bản thân
    private CustomTextField myUsernameInput;
    private CustomTextField myEmailInput;
    private CustomTextField myCreateAtInput;
    private CustomTextField myNameInput;
    private CustomTextField myBirthdateInput;
    private CustomTextField myNumberPhoneInput;
    private CustomTextField myCCCDInput;
    private CustomCombobox<String> myGenderInput;
    private CustomTextField myAddressInput;

    // Các trường dành cho tài khoản nhân viên (Nhớ phân vùng)
    private JLabel staffClicked;

    private ArrayList<Object[]> staffAccountList;
    private CustomTable staffAccountTable;
    private CustomScrollPane scrollPanelStaffAccount;
    private CustomPanel panelDataStaffAccount;

    private CustomTextField searchEmployeeAccount;
    private CustomCombobox<String> statusEmployeeAccount;
    private CustomCombobox<String> roleEmmployeeAccount;
    private CustomCombobox<String> orderSortEmployeeByName;
    @SuppressWarnings("unused")
    private CustomButton filterStaffAccount;
    @SuppressWarnings("unused")
    private CustomButton resetFilterStaffAccount;


    // Các trường dành cho tài khoản người chơi
    private JLabel playerClicked;

    private ArrayList<Object[]> playerAccountList;
    private CustomTable playerAccountTable;
    private CustomScrollPane scrollPanelPlayerAccount;
    private CustomPanel panelDataPlayerAccount;

    private CustomTextField searchPlayerAccount;
    private CustomCombobox<String> statusPlayerAccount;
    @SuppressWarnings("unused")
    private CustomCombobox<String> orderSortPlayerByName;
    @SuppressWarnings("unused")
    private CustomCombobox<String> orderSortPlayerByBalance;
    private CustomButton filterPlayerAccount;
    private CustomButton resetFilterPlayerAccount;

    
    public AccountPanel(Accounts loginAccount, Staffs loginStaff) {
        this.accountBLL = new AccountBLL();
        this.staffBLL = new StaffBLL();

        this.playerAccountList = new ArrayList<>();

        this.loginAccount = loginAccount;
        this.loginStaff = loginStaff;
        this.initComponents();
    }


    private CustomButton createButton(String text, Color backgroundColor, Color foregroundColor) {
        CustomButton button = new CustomButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setBorderSize(0);
        button.setBorderColor(backgroundColor);
        return button;
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

        this.myUsernameInput = new CustomTextField(this.loginAccount.getUsername());
        this.myUsernameInput.setBounds(30, 300, 300, 35);
        this.myUsernameInput.setEditable(false);

        // Email
        JLabel myEmail = new JLabel("Địa chỉ email:");
        myEmail.setFont(fontSans15);
        myEmail.setBounds(35, 350, 200, 30);

        this.myEmailInput = new CustomTextField(this.loginStaff.getEmail());
        this.myEmailInput.setBounds(30, 380, 300, 35);

        // Ngày tạo tài khoản
        JLabel createAtLabel = new JLabel("Ngày tạo tài khoản:");
        createAtLabel.setFont(fontSans15);
        createAtLabel.setBounds(35, 430, 200, 30);

        this.myCreateAtInput = new CustomTextField(this.loginAccount.getCreatedAt().toString());
        this.myCreateAtInput.setBounds(30, 460, 300, 35);
        this.myCreateAtInput.setEditable(false);

        // Họ và tên
        JLabel myName = new JLabel("Họ và tên:");
        myName.setFont(fontSans15);
        myName.setBounds(430, 30, 200, 30);

        this.myNameInput = new CustomTextField(this.loginStaff.getFullName());
        this.myNameInput.setBounds(430, 60, 300, 35);

        // Ngày sinh
        JLabel myBirthdate = new JLabel("Ngày sinh:");
        myBirthdate.setFont(fontSans15);
        myBirthdate.setBounds(430, 110, 200, 30);

        this.myBirthdateInput = new CustomTextField(this.loginStaff.getBirthDate().toString());
        this.myBirthdateInput.setBounds(430, 140, 300, 35);

        // Số điện thoại
        JLabel myNumberPhone = new JLabel("Số điện thoại:");
        myNumberPhone.setFont(fontSans15);
        myNumberPhone.setBounds(430, 190, 200, 30);

        this.myNumberPhoneInput = new CustomTextField(this.loginStaff.getPhone());
        this.myNumberPhoneInput.setBounds(430, 220, 300, 35);

        // CCCD
        JLabel myCCCD = new JLabel("Số căn cước công dân:");
        myCCCD.setFont(fontSans15);
        myCCCD.setBounds(430, 270, 200, 30);

        this.myCCCDInput = new CustomTextField(this.loginStaff.getCccd());
        this.myCCCDInput.setBounds(430, 300, 300, 35);

        // Giới tính
        JLabel myGender = new JLabel("Giới tính:");
        myGender.setFont(fontSans15);
        myGender.setBounds(430, 350, 200, 30);

        String[] genders = { "Chưa đặt", "Nam", "Nữ" };
        this.myGenderInput = new CustomCombobox<>(genders);
        this.myGenderInput.setBounds(430, 380, 300, 35);
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

        this.myAddressInput = new CustomTextField(this.loginStaff.getAddress());
        this.myAddressInput.setBounds(430, 460, 300, 35);
        this.myAddressInput.setEditable(false);

        // Nút đổi mật khẩu
        CustomButton changePassword = new CustomButton("Đổi mật khẩu");
        changePassword.setBounds(830, 60, 200, 30);
        changePassword.addActionListener(e -> {
            this.showDialogChangePassword();
        });

        // Nút lưu thông tin
        CustomButton changeMyInfo = new CustomButton("Cập nhật thông tin");
        changeMyInfo.setBounds(830, 110, 200, 30);
        changeMyInfo.addActionListener(e -> {
            this.showDialogConfirmChangeInfo();
        });

        // Nút cập nhật địa chỉ
        CustomButton editAddress = new CustomButton("Sửa địa chỉ");
        editAddress.setBounds(830, 160, 200, 30);
        editAddress.addActionListener(e -> {
            this.showDialogDetailsAddress();
        });

        // Thêm các thành phần vào panel
        panel.add(avatarEmployee);
        panel.add(btnChooseFile);
        panel.add(myUsername);
        panel.add(this.myUsernameInput);
        panel.add(myEmail);
        panel.add(this.myEmailInput);
        panel.add(createAtLabel);
        panel.add(this.myCreateAtInput);
        panel.add(myName);
        panel.add(this.myNameInput);
        panel.add(myBirthdate);
        panel.add(this.myBirthdateInput);
        panel.add(myNumberPhone);
        panel.add(this.myNumberPhoneInput);
        panel.add(myCCCD);
        panel.add(this.myCCCDInput);
        panel.add(myGender);
        panel.add(this.myGenderInput);
        panel.add(myAddress);
        panel.add(this.myAddressInput);
        panel.add(changePassword);
        panel.add(changeMyInfo);
        panel.add(editAddress);

        return panel;
    }

    private void showDialogChangePassword() {
        new DetailsAuthenticator(this.loginAccount).setVisible(true);
    }

    private void showDialogDetailsAddress() {
        new DetailsAddress(this.loginStaff).setVisible(true);
    }

    private void showDialogConfirmChangeInfo() {
        int resultConfirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc với sự thay đổi này ?", "Hỏi cập nhật thông tin", JOptionPane.INFORMATION_MESSAGE);
        if (resultConfirm == JOptionPane.YES_OPTION) {
            String emailString = this.myEmailInput.getText();
            String fullNameString = this.myNameInput.getText();
            String birthDateString = this.myBirthdateInput.getText();
            String phoneString = this.myNumberPhoneInput.getText();
            String cccdString = this.myCCCDInput.getText();
            String genderString = this.myGenderInput.getSelectedItem().toString();

            if (!Utils.Helper.Comon.isTrueEmail(emailString)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng Email ! \nVí dụ: example@email.com", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!Utils.Helper.Comon.isTrueDate(birthDateString)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng ngày tháng sinh nhật ! \nVí dụ: 2005-02-14", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!Utils.Helper.Comon.isTruePhone(phoneString)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số điện thoại ! \nVí dụ: 0388853835", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!Utils.Helper.Comon.isTrueCCCD(cccdString)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng CCCD ! \nVí dụ: 075205000000", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (fullNameString.equals("") || fullNameString == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng không để trống tên ! \nVí dụ: Nguyễn Nam Dương", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            HashMap<String, Object> updateValues = new HashMap<>();
            updateValues.put("full_name", fullNameString);
            updateValues.put("birth_date", birthDateString);
            updateValues.put("gender", genderString);
            updateValues.put("phone", phoneString);
            updateValues.put("email", emailString);
            updateValues.put("cccd", cccdString);

            boolean resultUpdate =this.staffBLL.updateDetailsInfoStaffById(this.loginStaff.getStaffId(), updateValues);
            JOptionPane.showMessageDialog(this, resultUpdate ? "Đổi thông tin thành công" : "Đổi thông tin thất bại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ------------------------------------- NỘI DUNG BẢNG NHÂN VIÊN ------------------------------------- //

    private void initStaffAccount(ArrayList<Object[]> dataObjects) {
        this.staffAccountList = this.accountBLL.getInfoStaffAccountList();
        for (Object object : this.staffAccountList) {
            dataObjects.add((Object[]) object);
        }
    }

    private void reloadStaffAccountTable() {
        this.staffAccountList = this.accountBLL.getInfoStaffAccountList();

        ArrayList<Object[]> dataObjects = new ArrayList<>();
        for (Object object : this.staffAccountList) {
            dataObjects.add((Object[]) object);
        }

        Object[][] newData = dataObjects.toArray(new Object[0][]);
        String[] columnNames = { "Mã tài khoản", "Tên đăng nhập", "Họ và tên", "Chức vụ", "Trạng thái", "Ngày tạo tài khoản" };

        DefaultTableModel model = new DefaultTableModel(newData, columnNames);
        this.staffAccountTable.setModel(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < this.staffAccountTable.getColumnModel().getColumnCount(); i++) {
            this.staffAccountTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        this.staffClicked.setText("Đang chọn: NULL");
        this.statusEmployeeAccount.setSelectedItem("Tất cả");
        this.searchEmployeeAccount.setText("Nhập thông tin tìm kiếm");
        this.roleEmmployeeAccount.setSelectedItem("Tất cả");
        this.orderSortEmployeeByName.setSelectedItem("Mặc định");
    }

    private void filterDataStaffAccount() {
        String searchText = this.searchEmployeeAccount.getText().equals("Nhập thông tin tìm kiếm") ? "" : this.searchEmployeeAccount.getText();
        String status = this.statusEmployeeAccount.getSelectedItem().toString();
        String role = this.roleEmmployeeAccount.getSelectedItem().toString();
        String orderName = this.orderSortEmployeeByName.getSelectedItem().toString();

       this.staffAccountList = this.accountBLL.filterStaffAccountList(searchText, status, role, orderName);

        ArrayList<Object[]> dataObjects = new ArrayList<>();
        for (Object object : this.staffAccountList) {
            dataObjects.add((Object[]) object);
        }

        Object[][] newData = dataObjects.toArray(new Object[0][]);
        String[] columnNames = { "Mã tài khoản", "Tên đăng nhập", "Họ và tên", "Chức vụ", "Trạng thái", "Ngày tạo tài khoản" };

        DefaultTableModel model = new DefaultTableModel(newData, columnNames);
        this.staffAccountTable.setModel(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < this.staffAccountTable.getColumnModel().getColumnCount(); i++) {
            this.staffAccountTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    

    @SuppressWarnings("unused")
    private CustomPanel createEmployeeInfoPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        CustomPanel findDataPanel = new CustomPanel();
        findDataPanel.setLayout(null);
        findDataPanel.setBackground(Color.WHITE);
        findDataPanel.setBounds(10, 0, 1080, 100);

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(20, 20, 80, 30);
        this.searchEmployeeAccount = new CustomTextField("Nhập thông tin tìm kiếm");
        this.searchEmployeeAccount.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchEmployeeAccount.getText().equals("Nhập thông tin tìm kiếm")) {
                    searchEmployeeAccount.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchEmployeeAccount.getText().equals("")) {
                    searchEmployeeAccount.setText("Nhập thông tin tìm kiếm");
                }
                
            }
        });
        this.searchEmployeeAccount.setBounds(100, 20, 200, 30);

        JLabel statusLabel = new JLabel("Trạng thái:");
        statusLabel.setBounds(330, 20, 80, 30);
        String[] statuses = { "Tất cả", "Online", "Offline", "Locked" };
        this.statusEmployeeAccount = new CustomCombobox<>(statuses);
        this.statusEmployeeAccount.setBounds(420, 20, 160, 30);

        JLabel roleLabel = new JLabel("Quyền:");
        roleLabel.setBounds(610, 20, 50, 30);
        String[] roles = { "Tất cả", "Quản trị viên", "Nhân viên" };
        this.roleEmmployeeAccount = new CustomCombobox<>(roles);
        this.roleEmmployeeAccount.setBounds(670, 20, 160, 30);

        CustomButton filterButton = new CustomButton("Lọc");
        filterButton.setBounds(870, 20, 120, 30);
        filterButton.setBackground(new Color(70, 130, 180));
        filterButton.setForeground(Color.WHITE);
        filterButton.setBorderSize(0);
        filterButton.setBorderColor(new Color(70, 130, 180));
        filterButton.addActionListener(e -> {
            this.filterDataStaffAccount();
        });

        CustomButton resetButton = new CustomButton("Đặt lại");
        resetButton.setBounds(870, 58, 120, 30);
        resetButton.setBackground(Color.RED);
        resetButton.setForeground(Color.WHITE);
        resetButton.setBorderSize(0);
        resetButton.setBorderColor(Color.RED);
        resetButton.addActionListener(e -> {
            this.staffClicked.setText("Đang chọn: NULL");
            this.statusEmployeeAccount.setSelectedItem("Tất cả");
            this.searchEmployeeAccount.setText("Nhập thông tin tìm kiếm");
            this.roleEmmployeeAccount.setSelectedItem("Tất cả");
            this.orderSortEmployeeByName.setSelectedItem("Mặc định");
            this.reloadStaffAccountTable();
            this.filterDataStaffAccount();
        });

        JLabel orderName = new JLabel("Lọc tên:");
        orderName.setBounds(20, 58, 80, 30);
        String[] sortOrder = {"Mặc định", "Theo tên tăng dần", "Theo tên giảm dần"};
        this.orderSortEmployeeByName = new CustomCombobox<>(sortOrder);
        this.orderSortEmployeeByName.setBounds(100, 58, 200, 30);


        this.staffClicked = new JLabel("Đang chọn: NULL");
        this.staffClicked.setBounds(610, 58, 200, 30);

        findDataPanel.add(searchLabel);
        findDataPanel.add(this.searchEmployeeAccount);
        findDataPanel.add(statusLabel);
        findDataPanel.add(this.statusEmployeeAccount);
        findDataPanel.add(roleLabel);
        findDataPanel.add(this.roleEmmployeeAccount);
        findDataPanel.add(filterButton);
        findDataPanel.add(resetButton);
        findDataPanel.add(orderName);
        findDataPanel.add(this.orderSortEmployeeByName);
        findDataPanel.add(this.staffClicked);

        this.panelDataStaffAccount = new CustomPanel();
        this.panelDataStaffAccount.setLayout(null);
        this.panelDataStaffAccount.setBackground(Color.WHITE);
        this.panelDataStaffAccount.setBounds(10, 110, 1080, 380);

        String[] columnNames = { "Mã tài khoản", "Tên đăng nhập", "Họ và tên", "Chức vụ", "Trạng thái", "Ngày tạo tài khoản" };
        ArrayList<Object[]> dataObjects = new ArrayList<>();
        this.initStaffAccount(dataObjects);
        Object[][] data = dataObjects.toArray(new Object[0][]);

        this.staffAccountTable = new CustomTable(new DefaultTableModel(data, columnNames));
        this.staffAccountTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = this.staffAccountTable.getSelectedRow();
                if (selectedRow != -1) {
                    int columnCount = this.staffAccountTable.getColumnCount();
                    Object[] rowData = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = this.staffAccountTable.getValueAt(selectedRow, i);
                    }
    
                    this.staffClicked.setText("Đang chọn: "+ rowData[0] +" | "+ rowData[1]);
                }
            }
        });

        this.scrollPanelStaffAccount = new CustomScrollPane(this.staffAccountTable);
        this.scrollPanelStaffAccount.setBounds(0, 0, 1080, 400);
        this.panelDataStaffAccount.add(this.scrollPanelStaffAccount);

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
        addButton.addActionListener(e -> {
            new AddingStaff().setVisible(true);
        });

        CustomButton editButton = new CustomButton("Thay đổi");
        editButton.setBounds(120, 20, 100, 30);
        editButton.setBackground(Color.decode("#795548"));
        editButton.setForeground(Color.WHITE);
        editButton.setBorderSize(0);
        editButton.setBorderColor(Color.decode("#795548"));

        CustomButton deleteButton = new CustomButton("Xóa");
        deleteButton.setBounds(230, 20, 100, 30);
        deleteButton.setBackground(Color.decode("#E57373"));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorderSize(0);
        deleteButton.setBorderColor(Color.decode("#E57373"));
        deleteButton.addActionListener(e -> {
            String[] path = this.staffClicked.getText().split(" \\| ");
            if (path.length == 1) {
                JOptionPane.showMessageDialog(this, "Bạn chưa chọn tài khoản nào để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                int resultConfirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa tài khoản này không ?");
                if (resultConfirm == 0) {
                    String username = path[path.length - 1];
                    boolean resultDelete = this.accountBLL.deleteAccountByUsername(username);
                    if (resultDelete) {
                        JOptionPane.showMessageDialog(this, "Xóa tài khoản "+ username +" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        this.reloadStaffAccountTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Có lỗi trong khi xóa tài khoản", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        CustomButton detailButton = new CustomButton("Chi tiết");
        detailButton.setBounds(340, 20, 100, 30);
        detailButton.setBackground(Color.decode("#455A64"));
        detailButton.setForeground(Color.WHITE);
        detailButton.setBorderColor(Color.decode("#455A64"));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(detailButton);

        panel.add(findDataPanel);
        panel.add(this.panelDataStaffAccount);
        panel.add(buttonPanel);

        return panel;
    }

    // ------------------------------------- NỘI DUNG BẢNG NGƯỜI CHƠI ------------------------------------- //

    private void initPlayerAccount(ArrayList<Object[]> dataObjects) {
        this.playerAccountList = this.accountBLL.getInfoPLayerAccountList();
        for (Object object : this.playerAccountList) {
            dataObjects.add((Object[]) object);
        }
    }

    private void reloadPlayerAccountTable() {
        this.playerAccountList = this.accountBLL.getInfoPLayerAccountList();

        ArrayList<Object[]> dataObjects = new ArrayList<>();
        for (Object object : this.playerAccountList) {
            dataObjects.add((Object[]) object);
        }

        Object[][] newData = dataObjects.toArray(new Object[0][]);
        String[] columnNames = { "Mã tài khoản", "Tên đăng nhập", "Số dư", "Trạng thái", "Ngày tạo" };

        DefaultTableModel model = new DefaultTableModel(newData, columnNames);
        this.playerAccountTable.setModel(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < this.playerAccountTable.getColumnModel().getColumnCount(); i++) {
            this.playerAccountTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        this.playerClicked.setText("Đang chọn: NULL");
        this.statusPlayerAccount.setSelectedItem("Tất cả");
        this.searchPlayerAccount.setText("");
    }

    private void filterDataPlayerAccount() {
        String textField = this.searchPlayerAccount.getText().trim();
        String statusField = this.statusPlayerAccount.getSelectedItem().toString();


        this.playerAccountList = this.accountBLL.filterPlayerAccountList(textField, statusField);

        ArrayList<Object[]> dataObjects = new ArrayList<>();
        for (Object object : this.playerAccountList) {
            dataObjects.add((Object[]) object);
        }

        Object[][] newData = dataObjects.toArray(new Object[0][]);
        String[] columnNames = { "Mã tài khoản", "Tên đăng nhập", "Số dư", "Trạng thái", "Ngày tạo" };

        DefaultTableModel model = new DefaultTableModel(newData, columnNames);
        this.playerAccountTable.setModel(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < this.playerAccountTable.getColumnModel().getColumnCount(); i++) {
            this.playerAccountTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

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
        searchPlayerAccount = new CustomTextField();
        searchPlayerAccount.setBounds(100, 20, 200, 30);

        JLabel statusLabel = new JLabel("Trạng thái:");
        statusLabel.setBounds(320, 20, 80, 30);
        String[] statuses = { "Tất cả", "Online", "Offline", "Locked" };

        this.statusPlayerAccount = new CustomCombobox<>(statuses);
        this.statusPlayerAccount.setBounds(400, 20, 150, 30);

        this.filterPlayerAccount = new CustomButton("Lọc");
        this.filterPlayerAccount.setBounds(590, 20, 120, 30);
        this.filterPlayerAccount.setBackground(new Color(70, 130, 180));
        this.filterPlayerAccount.setForeground(Color.WHITE);
        this.filterPlayerAccount.setBorderSize(0);
        this.filterPlayerAccount.setBorderColor(new Color(70, 130, 180));
        this.filterPlayerAccount.addActionListener(e -> {
            this.filterDataPlayerAccount();
        });

        this.resetFilterPlayerAccount = new CustomButton("Đặt lại");
        this.resetFilterPlayerAccount.setBounds(730, 20, 120, 30);
        this.resetFilterPlayerAccount.setBackground(Color.RED);
        this.resetFilterPlayerAccount.setForeground(Color.WHITE);
        this.resetFilterPlayerAccount.setBorderSize(0);
        this.resetFilterPlayerAccount.setBorderColor(Color.RED);
        this.resetFilterPlayerAccount.addActionListener(e -> {
            this.searchPlayerAccount.setText("");
            this.statusPlayerAccount.setSelectedItem("Tất cả");
            this.filterDataPlayerAccount();
        });

        this.playerClicked = new JLabel("Đang chọn: NULL");
        this.playerClicked.setBounds(880, 20, 170, 30);

        findDataPanel.add(searchLabel);
        findDataPanel.add(this.searchPlayerAccount);
        findDataPanel.add(statusLabel);
        findDataPanel.add(this.statusPlayerAccount);
        findDataPanel.add(this.filterPlayerAccount);
        findDataPanel.add(this.resetFilterPlayerAccount);
        findDataPanel.add(this.playerClicked);

        this.panelDataPlayerAccount = new CustomPanel();
        this.panelDataPlayerAccount.setLayout(null);
        this.panelDataPlayerAccount.setBackground(Color.WHITE);
        this.panelDataPlayerAccount.setBounds(10, 90, 1080, 400);

        String[] columnNames = { "Mã tài khoản", "Tên đăng nhập", "Số dư", "Trạng thái", "Ngày tạo" };
        ArrayList<Object[]> dataObjects = new ArrayList<>();
        this.initPlayerAccount(dataObjects);
        Object[][] data = dataObjects.toArray(new Object[0][]);

        this.playerAccountTable = new CustomTable(new DefaultTableModel(data, columnNames));
        this.playerAccountTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = this.playerAccountTable.getSelectedRow();
                if (selectedRow != -1) {
                    int columnCount = this.playerAccountTable.getColumnCount();
                    Object[] rowData = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = this.playerAccountTable.getValueAt(selectedRow, i);
                    }
    
                    this.playerClicked.setText("Đang chọn: "+ rowData[0] +" | "+ rowData[1]);
                }
            }
        });
        this.scrollPanelPlayerAccount = new CustomScrollPane(this.playerAccountTable);
        this.scrollPanelPlayerAccount.setBounds(0, 0, 1080, 400);
        this.panelDataPlayerAccount.add(this.scrollPanelPlayerAccount);

        CustomPanel buttonPanel = new CustomPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(10, 500, 1080, 65);
        buttonPanel.setBackground(Color.WHITE);

        CustomButton addButton = this.createButton("Thêm", new Color(34, 177, 76), Color.WHITE);
        addButton.setBounds(10, 20, 100, 30);
        addButton.addActionListener(e -> {
            new AddingPlayer().setVisible(true);
        });

        CustomButton editButton = this.createButton("Thay đổi", Color.decode("#795548"), Color.WHITE);
        editButton.setBounds(120, 20, 100, 30);
        editButton.addActionListener(e -> {
            String[] path = this.playerClicked.getText().split(" \\| ");
            if (path.length == 1) {
                JOptionPane.showMessageDialog(this, "Bạn chưa chọn tài khoản nào để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                new EditingPlayerForm(this.playerClicked.getText()).setVisible(true);
            }
        });

        CustomButton deleteButton = this.createButton("Xóa", Color.decode("#E57373"), Color.WHITE);
        deleteButton.setBounds(230, 20, 100, 30);
        deleteButton.addActionListener(e -> {
            String[] path = this.playerClicked.getText().split(" \\| ");
            if (path.length == 1) {
                JOptionPane.showMessageDialog(this, "Bạn chưa chọn tài khoản nào để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                int resultConfirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa tài khoản này không ?");
                if (resultConfirm == 0) {
                    String username = path[path.length - 1];
                    boolean resultDelete = this.accountBLL.deleteAccountByUsername(username);
                    if (resultDelete) {
                        JOptionPane.showMessageDialog(this, "Xóa tài khoản "+ username +" thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        this.reloadPlayerAccountTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Có lỗi trong khi xóa tài khoản", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });


        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(findDataPanel);
        panel.add(this.panelDataPlayerAccount);
        panel.add(buttonPanel);

        return panel;
    }

    private JPanel createShowInfo() {
        this.cardLayout = new CardLayout();
        JPanel panel = new JPanel(this.cardLayout);
        panel.setBackground(Color.WHITE);

        CustomPanel selfInfoPanel = this.createStaffInfoPanel();

        CustomPanel playerAccountPanel = this.createPlayerInfoPanel();

        // CustomPanel employeePanel = this.createEmployeeInfoPanel();

        panel.add(selfInfoPanel, "MyInfo");
        panel.add(playerAccountPanel, "PlayerInfo");
        
        if (this.loginAccount.getRole().equals("Quản trị viên")) {
            CustomPanel employeePanel = this.createEmployeeInfoPanel();
            panel.add(employeePanel, "EmployeeInfo");
        }
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
        String avatarFile = this.loginStaff.getAvatar();
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
                    if (result)
                        System.out.println("Xóa ảnh cũ thành công");
                    else
                        System.out.println("Xóa ảnh cũ thất bại");
                }
            }

            String newFileName = "avatar_" + "1" + ".png";
            File destination = new File(basePath + newFileName);

            Files.copy(selectedFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            // Cập nhật lại JLabel
            updateAvatar(destination.getAbsolutePath());
            // Cập nhật trên database
            boolean updateResult = new StaffBLL().updateAvatarStaffById(this.loginStaff.getStaffId(), newFileName);
            if (updateResult) {
                JOptionPane.showMessageDialog(this, "Ảnh đại diện đã được cập nhật!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Ảnh đại diện có lỗi khi cập nhật!", "Có lỗi",
                    JOptionPane.WARNING_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu ảnh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAvatar(String path) {
        ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        avatarEmployee.setIcon(icon);
    }
}
