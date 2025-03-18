 package GUI.main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import DTO.Accounts;
import DTO.Staffs;
import Utils.Helper.CreateComponent;
import GUI.Components.CustomButton;
import GUI.panels.AccountPanel;
import GUI.panels.BillPanel;
import GUI.panels.ChartPanel;
import GUI.panels.ComputerPanel;
import GUI.panels.DashBoardPanel;
import GUI.panels.FoodPanel;
import GUI.panels.HardwarePanel;
import GUI.panels.HistoryPanel;
import GUI.panels.RoomPanel;



public class AdminDashboard extends JFrame {
   private Accounts loginAccount;
   private Staffs loginStaff;

    // Thông tin cơ bản nhân viên
    private JLabel iconLogo;
    private JLabel nameStaffs;
    private JLabel roleAccount;

    private JPanel actionPanel;

    private CardLayout cardLayout;
    private JPanel infoPanel;

    // Phần nút
    CustomButton dashBoardButton;
    CustomButton accountButton;
    CustomButton historyButton;
    CustomButton computerButton;
    CustomButton hardwareButton;
    CustomButton foodButton;
    CustomButton billButton;
    CustomButton chartButton;
    CustomButton roomButton;


    public AdminDashboard(Accounts loginAccount, Staffs loginStaff) {
        this.loginAccount = loginAccount;
        this.loginStaff = loginStaff;
        this.initComponents();
    }

    private void createLayoutAdmin(JPanel buttonActionPanel) {
        buttonActionPanel.setLayout(null);

        this.dashBoardButton = CreateComponent.createButton("icons8-dashboard-100.png", "Trang chủ");
        this.dashBoardButton.setBounds(10, 5, 230, 50);
        this.dashBoardButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "DashBoardPanel"));

        this.accountButton = CreateComponent.createButton("icons8-account-100.png", "Tài khoản");
        this.accountButton.setBounds(10, 60, 230, 50);
        this.accountButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "AccountPanel"));

        this.historyButton = CreateComponent.createButton("icons8-payment-history-100.png", "Lịch sử");
        this.historyButton.setBounds(10, 115, 230, 50);
        this.historyButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "HistoryPanel"));

        this.computerButton = CreateComponent.createButton("icons8-computer-100.png", "Máy tính");
        this.computerButton.setBounds(10, 170, 230, 50);
        this.computerButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "ComputerPanel"));

        this.hardwareButton = CreateComponent.createButton("icons8-ethernet-on-100.png", "Linh kiện");
        this.hardwareButton.setBounds(10, 225, 230, 50);
        this.hardwareButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "HardwarePanel"));

        this.foodButton = CreateComponent.createButton("icons8-ingredients-100.png", "Thức ăn");
        this.foodButton.setBounds(10, 280, 230, 50);
        this.foodButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "FoodPanel"));

        this.billButton = CreateComponent.createButton("icons8-bill-100.png", "Hóa đơn");
        this.billButton.setBounds(10, 335, 230, 50);
        this.billButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "BillPanel"));

        this.chartButton = CreateComponent.createButton("icons8-chart-100.png", "Thống kê");
        this.chartButton.setBounds(10, 390, 230, 50);
        this.chartButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "ChartPanel"));

        this.roomButton = CreateComponent.createButton("icons8-hotel-room-key-100.png", "Phòng chơi");
        this.roomButton.setBounds(10, 445, 230, 50);
        this.roomButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "RoomPanel"));

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

        CustomButton accountButton = CreateComponent.createButton("icons8-account-100.png", "Tài khoản");
        accountButton.setBounds(10, 60, 230, 50);
        accountButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "AccountPanel"));

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
        buttonActionPanel.add(accountButton);
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

        // Phần hiển thị thông tin cơ bản nhân viên
        JPanel headActionPanel = new JPanel();
        headActionPanel.setLayout(null);

        this.iconLogo = new JLabel();
        this.iconLogo.setIcon(
                new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Icon/icons8-employee-100.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                this.iconLogo.setBounds(10, 10, 50, 50);
        this.nameStaffs = new JLabel(this.loginStaff.getFullName());
        this.nameStaffs.setFont(new Font("Sans-serif", Font.BOLD, 15));
        this.nameStaffs.setForeground(Color.BLACK);
        this.nameStaffs.setBounds(80, 10, 150, 30);
        this.roleAccount = new JLabel(this.loginAccount.getRole());
        this.roleAccount.setFont(new Font("Sans-serif", Font.BOLD, 12));
        this.roleAccount.setForeground(Color.GRAY);
        this.roleAccount.setBounds(80, 30, 150, 30);

        headActionPanel.add(iconLogo);
        headActionPanel.add(nameStaffs);
        headActionPanel.add(roleAccount);
        headActionPanel.setBounds(0, 0, 250, 80);
        headActionPanel.setBackground(Color.WHITE);

        // Phần nút chuyển các CardLayout
        JPanel buttonActionPanel = new JPanel();
        if (this.loginAccount.getRole().equalsIgnoreCase("Quản trị viên")) {
            this.createLayoutAdmin(buttonActionPanel);
        } else this.createLayoutEmployee(buttonActionPanel);

        headActionPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.decode("#9E9E9E")));

        panel.add(headActionPanel);
        panel.add(buttonActionPanel);

        panel.setBorder(new MatteBorder(0, 0, 0, 1, Color.GRAY));
        return panel;
    }

    private JPanel infoPanelDesign() {
        this.cardLayout = new CardLayout();
        JPanel panel = new JPanel(this.cardLayout);

        DashBoardPanel dashBoardPanel = new DashBoardPanel();

        AccountPanel accountPanel = new AccountPanel();

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
                new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Image/spoce_net_gaming.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

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
}
