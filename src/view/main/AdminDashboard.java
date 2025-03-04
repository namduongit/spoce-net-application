package view.main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import model.Pojo.Accounts;
import model.Pojo.Staffs;
import Utils.Helper.CreateComponent;
import view.Components.CustomButton;
import view.panels.AccountPanel;
import view.panels.BillPanel;
import view.panels.ChartPanel;
import view.panels.ComputerPanel;
import view.panels.DashBoardPanel;
import view.panels.FoodPanel;
import view.panels.HardwarePanel;
import view.panels.HistoryPanel;
import view.panels.RoomPanel;


@SuppressWarnings("unused")
public class AdminDashboard extends JFrame {
    private Staffs currentStaff;
    private Accounts currentAccount;

    private JLabel iconLogo;
    private JLabel nameStaffs;
    private JLabel roleAccount;

    private JPanel actionPanel;

    private CardLayout cardLayout;
    private JPanel infoPanel;

    public AdminDashboard(Staffs staffLogined, Accounts accountLogined) {
        this.currentStaff = staffLogined;
        this.currentAccount = accountLogined;

        this.initComponents();
    }

    private void createLayoutAdmin(JPanel buttonActionPanel) {
        buttonActionPanel.setLayout(null);

        CustomButton dashBoardButton = CreateComponent.createButton("icons8-dashboard-100.png", "Trang chủ");
        dashBoardButton.setBounds(10, 5, 230, 50);
        dashBoardButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "DashBoardPanel"));

        CustomButton accountButton = CreateComponent.createButton("icons8-account-100.png", "Tài khoản");
        accountButton.setBounds(10, 60, 230, 50);
        accountButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "AccountPanel"));

        CustomButton historyButton = CreateComponent.createButton("icons8-payment-history-100.png", "Lịch sử");
        historyButton.setBounds(10, 115, 230, 50);
        historyButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "HistoryPanel"));

        CustomButton computerButton = CreateComponent.createButton("icons8-computer-100.png", "Máy tính");
        computerButton.setBounds(10, 170, 230, 50);
        computerButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "ComputerPanel"));

        CustomButton hardwareButton = CreateComponent.createButton("icons8-ethernet-on-100.png", "Linh kiện");
        hardwareButton.setBounds(10, 225, 230, 50);
        hardwareButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "HardwarePanel"));

        CustomButton foodButton = CreateComponent.createButton("icons8-ingredients-100.png", "Thức ăn");
        foodButton.setBounds(10, 280, 230, 50);
        foodButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "FoodPanel"));

        CustomButton billButton = CreateComponent.createButton("icons8-bill-100.png", "Hóa đơn");
        billButton.setBounds(10, 335, 230, 50);
        billButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "BillPanel"));

        CustomButton chartButton = CreateComponent.createButton("icons8-chart-100.png", "Thống kê");
        chartButton.setBounds(10, 390, 230, 50);
        chartButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "ChartPanel"));

        CustomButton roomButton = CreateComponent.createButton("icons8-hotel-room-key-100.png", "Phòng chơi");
        roomButton.setBounds(10, 445, 230, 50);
        roomButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "RoomPanel"));

        CustomButton logoutButton = CreateComponent.createButton("icons8-logout-100.png", "Đăng xuất");
        logoutButton.setBounds(10, 555, 230, 50);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonActionPanel.add(dashBoardButton);
        buttonActionPanel.add(accountButton);
        buttonActionPanel.add(historyButton);
        buttonActionPanel.add(computerButton);
        buttonActionPanel.add(hardwareButton);
        buttonActionPanel.add(foodButton);
        buttonActionPanel.add(billButton);
        buttonActionPanel.add(chartButton);
        buttonActionPanel.add(roomButton);
        buttonActionPanel.add(logoutButton);

        buttonActionPanel.setBounds(0, 80, 250, Utils.Config.ConfigFrame.HEIGHT_FRAME - 80);
        buttonActionPanel.setBackground(Color.WHITE);
    }

    private void createLayoutEmployee(JPanel buttonActionPanel) {
        buttonActionPanel.setLayout(null);

        CustomButton dashBoardButton = CreateComponent.createButton("icons8-dashboard-100.png", "Trang chủ");
        dashBoardButton.setBounds(10, 5, 230, 50);
        dashBoardButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "DashBoardPanel"));

        CustomButton acccountButton = CreateComponent.createButton("icons8-account-100.png", "Tài khoản");
        acccountButton.setBounds(10, 60, 230, 50);
        acccountButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "AccountPanel"));

        CustomButton foodButton = CreateComponent.createButton("icons8-ingredients-100.png", "Thức ăn");
        foodButton.setBounds(10, 115, 230, 50);
        foodButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "FoodPanel"));

        CustomButton billButton = CreateComponent.createButton("icons8-bill-100.png", "Hóa đơn");
        billButton.setBounds(10, 170, 230, 50);
        billButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "BillPanel"));

        CustomButton roomButton = CreateComponent.createButton("icons8-hotel-room-key-100.png", "Phòng chơi");
        roomButton.setBounds(10, 225, 230, 50);
        roomButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "RoomPanel"));

        CustomButton logoutButton = CreateComponent.createButton("icons8-logout-100.png", "Đăng xuất");
        logoutButton.setBounds(10, 555, 230, 50);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonActionPanel.add(dashBoardButton);
        buttonActionPanel.add(acccountButton);
        buttonActionPanel.add(foodButton);
        buttonActionPanel.add(billButton);
        buttonActionPanel.add(roomButton);
        buttonActionPanel.add(logoutButton);

        buttonActionPanel.setBounds(0, 80, 250, Utils.Config.ConfigFrame.HEIGHT_FRAME - 80);
        buttonActionPanel.setBackground(Color.WHITE);
    }

    private JPanel actionPanelDesign() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        // Ở trên đây có Hình logo là con người, Tên, Chức Vụ
        JPanel headActionPanel = new JPanel();
        headActionPanel.setLayout(null);

        JLabel iconLogo = new JLabel();
        iconLogo.setIcon(
                new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/src/view/Assets/Icon/icons8-employee-100.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        iconLogo.setBounds(10, 10, 50, 50);
        JLabel nameStaffs = new JLabel(this.currentStaff.getFullName().isEmpty() ? "Chưa có tên" : this.currentStaff.getFullName());
        nameStaffs.setFont(new Font("Sans-serif", Font.BOLD, 15));
        nameStaffs.setForeground(Color.BLACK);
        nameStaffs.setBounds(80, 10, 150, 30);
        JLabel roleAccount = new JLabel(this.currentAccount.getRole());
        roleAccount.setFont(new Font("Sans-serif", Font.BOLD, 12));
        roleAccount.setForeground(Color.GRAY);
        roleAccount.setBounds(80, 30, 150, 30);

        headActionPanel.add(iconLogo);
        headActionPanel.add(nameStaffs);
        headActionPanel.add(roleAccount);
        headActionPanel.setBounds(0, 0, 250, 80);
        headActionPanel.setBackground(Color.WHITE);

        // Ở dưới đây là bảng nút
        JPanel buttonActionPanel = new JPanel();
        if (this.currentAccount.getRole().equals("Quản trị viên")) {
            this.createLayoutAdmin(buttonActionPanel);
        } else if (this.currentAccount.getRole().equals("Nhân viên")) {
            this.createLayoutEmployee(buttonActionPanel);
        }

        // Thêm đường viền cho 2 Panel phụ trong Panel chính
        headActionPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.decode("#9E9E9E")));

        // Thêm trước khi trả về
        panel.add(headActionPanel);
        panel.add(buttonActionPanel);

        // Tạo đường viền ở phía bên phải
        panel.setBorder(new MatteBorder(0, 0, 0, 1, Color.GRAY));
        return panel;
    }

    private JPanel infoPanelDesign() {
        this.cardLayout = new CardLayout();
        JPanel panel = new JPanel(this.cardLayout);

        DashBoardPanel dashBoardPanel = new DashBoardPanel();

        AccountPanel accountPanel = new AccountPanel(this.currentAccount, this.currentStaff);

        HistoryPanel historyPanel = new HistoryPanel();

        ComputerPanel computerPanel = new ComputerPanel();

        HardwarePanel hardwarePanel = new HardwarePanel();

        FoodPanel foodPanel = new FoodPanel();

        BillPanel billPanel = new BillPanel();

        ChartPanel chartPanel = new ChartPanel();

        RoomPanel roomPanel = new RoomPanel();

        // Thêm trước khi trả về
        panel.add(dashBoardPanel, "DashBoardPanel");
        panel.add(accountPanel, "AccountPanel");
        panel.add(historyPanel, "HistoryPanel");
        panel.add(computerPanel, "ComputerPanel");
        panel.add(hardwarePanel, "HardwarePanel");
        panel.add(foodPanel, "FoodPanel");
        panel.add(billPanel, "BillPanel");
        panel.add(chartPanel, "ChartPanel");
        panel.add(roomPanel, "RoomPanel");

        // Tạo đường viền ở bên trái
        panel.setBorder(new MatteBorder(0, 1, 0, 0, Color.decode("#9E9E9E")));
        return panel;
    }

    private void initComponents() {
        this.setSize(Utils.Config.ConfigFrame.WIDTH_FRAME, Utils.Config.ConfigFrame.HEIGHT_FRAME);
        this.setTitle("SPOCE Cyber System");
        this.setIconImage(
                new ImageIcon(System.getProperty("user.dir") + "/src/view/Assets/Image/spoce_net_gaming.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.iconLogo = new JLabel();
        this.iconLogo.setIcon(
                new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/src/view/Assets/Icon/icons8-employee-100.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        this.iconLogo.setBounds(10, 10, 50, 50);

        this.nameStaffs = new JLabel(this.currentStaff.getFullName());
        nameStaffs.setFont(new Font("Sans-serif", Font.BOLD, 15));
        nameStaffs.setForeground(Color.BLACK);
        nameStaffs.setBounds(80, 10, 150, 30);

        roleAccount = new JLabel(this.currentAccount.getRole());
        roleAccount.setFont(new Font("Sans-serif", Font.BOLD, 12));
        roleAccount.setForeground(Color.GRAY);
        roleAccount.setBounds(80, 30, 150, 30);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        this.actionPanel = this.actionPanelDesign();
        this.infoPanel = this.infoPanelDesign();

        this.actionPanel.setBounds(0, 0, 250, Utils.Config.ConfigFrame.HEIGHT_FRAME);
        this.infoPanel.setBounds(250, 0, Utils.Config.ConfigFrame.WIDTH_FRAME - 250, Utils.Config.ConfigFrame.HEIGHT_FRAME);

        panel.add(actionPanel);
        panel.add(infoPanel);

        panel.setBackground(Color.WHITE);

        this.getContentPane().add(panel);
    }

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2005, 2, 14);
        LocalDateTime timeslap = LocalDateTime.of(2025, 2, 14, 10, 20, 22);

        Date birthDate = Date.valueOf(date);
        Timestamp createAt = Timestamp.valueOf(timeslap);


        Staffs employeeStaff = new Staffs(1, 1, "Nguyễn Nam Dương", birthDate, "Nam", "0388853835", "nguyennamduong205@gmail.com", "Long Khánh, Đồng Nai", "075205020410", "1.png");

        Accounts adminAccount = new Accounts(1, "namduongit", "NDuong205", "Quản trị viên", "Đang hoạt động", createAt);
        Accounts employeeAccount = new Accounts(1, "namduongit", "NDuong205", "Nhân viên", "Đang hoạt động", createAt);

        new AdminDashboard(employeeStaff, employeeAccount).setVisible(true);
    }
}
