package GUI.main;

import DTO.Accounts;
import DTO.Staffs;
import GUI.Components.CustomButton;
import GUI.panels.*;
import Utils.Helper.CreateComponent;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class AdminDashboard extends JFrame {
    private Accounts loginAccount;
    private Staffs loginStaff;

    private JLabel iconLogo;
    private JLabel nameStaffs;
    private JLabel roleAccount;

    private JPanel actionPanel;
    private JPanel infoPanel;

    private CustomButton dashBoardButton;
    private CustomButton accountButton;
    private CustomButton computerButton;
    private CustomButton hardwareButton;
    private CustomButton foodButton;
    private CustomButton billButton;
    private CustomButton chartButton;
    private CustomButton roomButton;
    private CustomButton computerManagingButton;

    private JPanel currentShowingPanel;

    public AdminDashboard(Accounts loginAccount, Staffs loginStaff) {
        this.loginAccount = loginAccount;
        this.loginStaff = loginStaff;

        this.initComponents();
    }

    private void createButtonLayout(JPanel buttonPanel) {
        if (this.loginAccount == null || this.loginStaff == null)
            return;


        buttonPanel.setLayout(null);
        buttonPanel.setBounds(0, 80, 250, Utils.Config.ConfigFrame.HEIGHT_FRAME - 80);
        buttonPanel.setBackground(Color.WHITE);

        List<CustomButton> buttons = new ArrayList<>();

        this.dashBoardButton = CreateComponent.createButton("icons8-dashboard-100.png", "Trang chủ");
        this.dashBoardButton.addActionListener(e -> {
            this.switchPanel(new DashBoardPanel());
        });
        buttons.add(this.dashBoardButton);

        this.accountButton = CreateComponent.createButton("icons8-account-100.png", "Tài khoản");
        this.accountButton.addActionListener(e -> {
            this.switchPanel(new AccountPanel(this.loginAccount, this.loginStaff));
        });
        buttons.add(this.accountButton);


        if (this.loginAccount.getRole().equalsIgnoreCase("Quản trị viên")) {
            this.computerButton = CreateComponent.createButton("icons8-computer-100.png", "Máy tính");
            this.computerButton.addActionListener(e -> {
                this.switchPanel(new ComputerPanel(this.loginAccount, this.loginStaff));
            });
            buttons.add(this.computerButton);

            this.hardwareButton = CreateComponent.createButton("icons8-ethernet-on-100.png", "Linh kiện");
            this.hardwareButton.addActionListener(e -> {
                this.switchPanel(new HardwarePanel());
            });
            buttons.add(this.hardwareButton);

            this.foodButton = CreateComponent.createButton("icons8-ingredients-100.png", "Thức ăn");
            this.foodButton.addActionListener(e -> {
                this.switchPanel(new FoodPanel(this.loginAccount, this.loginStaff));
            });
            buttons.add(this.foodButton);

            this.billButton = CreateComponent.createButton("icons8-bill-100.png", "Hóa đơn");
            this.billButton.addActionListener(e -> {
                this.switchPanel(new BillPanel(this.loginAccount, this.loginStaff));
            });
            buttons.add(this.billButton);

            this.chartButton = CreateComponent.createButton("icons8-chart-100.png", "Thống kê");
            this.chartButton.addActionListener(e -> {
                this.switchPanel(new ChartPanel());
            });
            buttons.add(this.chartButton);

            this.roomButton = CreateComponent.createButton("icons8-hotel-room-key-100.png", "Phòng chơi");
            this.roomButton.addActionListener(e -> {
                this.switchPanel(new RoomPanel());
            });
            buttons.add(this.roomButton);

            this.computerManagingButton = CreateComponent.createButton("icons8-computer-100.png", "Bật tắt máy");
            this.computerManagingButton.addActionListener(e -> {
                this.switchPanel(new ComputerManagingPanel(this.loginAccount, this.loginStaff));
            });
            buttons.add(this.computerManagingButton);

        } else if (this.loginAccount.getRole().equalsIgnoreCase("Nhân viên")) {
            this.foodButton = CreateComponent.createButton("icons8-ingredients-100.png", "Thức ăn");
            this.foodButton.addActionListener(e -> {
                this.switchPanel(new FoodPanel(this.loginAccount, this.loginStaff));
            });
            buttons.add(this.foodButton);

            this.billButton = CreateComponent.createButton("icons8-bill-100.png", "Hóa đơn");
            this.billButton.addActionListener(e -> {
                this.switchPanel(new BillPanel(this.loginAccount, this.loginStaff));
            });
            buttons.add(this.billButton);

            this.computerManagingButton = CreateComponent.createButton("icons8-computer-100.png", "Bật tắt máy");
            this.computerManagingButton.addActionListener(e -> {
                this.switchPanel(new ComputerManagingPanel(this.loginAccount, this.loginStaff));
            });
            buttons.add(this.computerManagingButton);
        }

        CustomButton logoutButton = CreateComponent.createButton("icons8-logout-100.png", "Đăng xuất");
        logoutButton.addActionListener(e -> {
            int resultLogout = JOptionPane.showConfirmDialog(
                    AdminDashboard.this,
                    "Bạn có chắc chắn đăng xuất hay không ?",
                    "Thông báo",
                    JOptionPane.OK_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (resultLogout == JOptionPane.OK_OPTION) {
                dispose();
            }
        });
        buttons.add(logoutButton);

        int y = 5;
        for (CustomButton btn : buttons) {
            btn.setBounds(10, y, 230, 50);
            buttonPanel.add(btn);
            y += 55;
        }
    }

    private JPanel actionPanelDesign() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JPanel headActionPanel = new JPanel();
        headActionPanel.setLayout(null);

        this.iconLogo = new JLabel();
        this.iconLogo.setIcon(
                new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Icon/icons8-employee-100.png")
                        .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        this.iconLogo.setBounds(10, 10, 50, 50);
        this.nameStaffs = new JLabel(this.loginStaff.getFullName() != null ? this.loginStaff.getFullName() : "Chưa đặt tên");
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

        JPanel buttonActionPanel = new JPanel();
        this.createButtonLayout(buttonActionPanel);

        headActionPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.decode("#9E9E9E")));

        panel.add(headActionPanel);
        panel.add(buttonActionPanel);

        panel.setBorder(new MatteBorder(0, 0, 0, 1, Color.GRAY));
        return panel;
    }

    private JPanel infoPanelDesign() {
        JPanel panel = new JPanel(null);
        panel.setBorder(new MatteBorder(0, 1, 0, 0, Color.decode("#9E9E9E")));
        return panel;
    }

    private void switchPanel(JPanel newPanel) {
        if (currentShowingPanel != null) {
            this.infoPanel.remove(currentShowingPanel);
        }
        this.currentShowingPanel = newPanel;
        newPanel.setBounds(0, 0, this.infoPanel.getWidth(), this.infoPanel.getHeight());
        this.infoPanel.add(newPanel);
        this.infoPanel.revalidate();
        this.infoPanel.repaint();
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
        this.infoPanel.setBounds(250, 0, Utils.Config.ConfigFrame.WIDTH_FRAME - 250,
                Utils.Config.ConfigFrame.HEIGHT_FRAME);

        panel.add(actionPanel);
        panel.add(infoPanel);

        panel.setBackground(Color.WHITE);

        this.getContentPane().add(panel);
        this.switchPanel(new DashBoardPanel());
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        Accounts accounts = new Accounts(1, "namduongit", "namduongit", "Quản trị viên", "Đang hoạt động",
                new Timestamp(1, 1, 1, 1, 1, 1, 1));
        Staffs staffs = new Staffs(1, 1, "Nguyễn Nam Dương", new Date(1, 1, 1), "Nam", "0388853835", "null", "null",
                "null", "null");
        new AdminDashboard(accounts, staffs).setVisible(true);
    }
}
