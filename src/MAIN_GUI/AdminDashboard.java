package MAIN_GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import CardPanel.AccountPanel;
import CardPanel.DashBoardPanel;
import CardPanel.HistoryPanel;
import Components.CustomButton;
import Pojo.Accounts;
import Pojo.Staffs;

@SuppressWarnings("unused")
public class AdminDashboard extends JFrame {
    private Staffs currentStaff;
    private Accounts currentAccount;

    private JPanel actionPanel;

    private CardLayout cardLayout;
    private JPanel infoPanel;

    public AdminDashboard(Staffs staffLogined, Accounts accountLogined) {
        this.currentStaff = staffLogined;
        this.currentAccount = accountLogined;
        this.initComponents();
    }

    public CustomButton createButton(String imageIconName, String textIcon) {
        int target = 15;
        int lengthText = textIcon.length();
        for (int i = 0; i < target - lengthText; i++) {
            textIcon = " "+ textIcon;
        }
        CustomButton button = new CustomButton(textIcon);
        button.setIcon(new ImageIcon(
                new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Icon/"+ imageIconName).getImage()
                .getScaledInstance(50, 50, Image.SCALE_SMOOTH)),
                0);
        button.setBorderSize(0);
        button.setBorderColor(Color.WHITE);
        button.setFont(new Font("Sans-serif", Font.BOLD, 15));
        // Sự kiện ấn vào nền có màu xanh nhạt còn không ấn thì có màu trắng (tức không có màu)
        button.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                button.setBackground(Color.decode("#B3E5FC"));
            }

            @Override
            public void focusLost(FocusEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
        return button;
    }

    public JPanel actionPanelDesign() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        // Ở trên đây có Hình logo là con người, Tên, Chức Vụ
        JPanel headActionPanel = new JPanel();
        headActionPanel.setLayout(null);

        JLabel iconLogo = new JLabel();
        iconLogo.setIcon(
                new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Icon/icons8-employee-100.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        iconLogo.setBounds(10, 10, 50, 50);
        JLabel nameStaffs = new JLabel("Nguyễn Nam Dương");
        nameStaffs.setFont(new Font("Sans-serif", Font.BOLD, 15));
        nameStaffs.setForeground(Color.BLACK);
        nameStaffs.setBounds(80, 10, 150, 30);
        JLabel roleAccount = new JLabel("Quản trị viên");
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
        buttonActionPanel.setLayout(null);

        CustomButton dashBoardButton = this.createButton("icons8-dashboard-100.png", "Trang chủ");
        dashBoardButton.setBounds(20, 5, 210, 50);
        dashBoardButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "DashBoardPanel"));

        CustomButton acccountButton = this.createButton("icons8-account-100.png", "Tài khoản");
        acccountButton.setBounds(20, 60, 210, 50);
        acccountButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "AccountPanel"));

        CustomButton historyButton = this.createButton("icons8-payment-history-100.png", "Lịch sử");
        historyButton.setBounds(20, 115, 210, 50);
        historyButton.addActionListener(e -> this.cardLayout.show(this.infoPanel, "HistoryPanel"));

        CustomButton computerButton = this.createButton("icons8-computer-100.png", "Máy tính");
        computerButton.setBounds(20, 170, 210, 50);

        CustomButton harwareButton = this.createButton("icons8-ethernet-on-100.png", "Linh kiện");
        harwareButton.setBounds(20, 225, 210, 50);

        CustomButton foodButton = this.createButton("icons8-ingredients-100.png", "Thức ăn");
        foodButton.setBounds(20, 280, 210, 50);

        CustomButton billButton = this.createButton("icons8-bill-100.png", "Hóa đơn");
        billButton.setBounds(20, 335, 210, 50);

        CustomButton chartButton = this.createButton("icons8-chart-100.png", "Thống kê");
        chartButton.setBounds(20, 390, 210, 50);

        CustomButton logoutButton = this.createButton("icons8-logout-100.png", "Đăng xuất");
        logoutButton.setBounds(20, 445, 210, 50);

        buttonActionPanel.add(dashBoardButton);
        buttonActionPanel.add(acccountButton);
        buttonActionPanel.add(historyButton);
        buttonActionPanel.add(computerButton);
        buttonActionPanel.add(harwareButton);
        buttonActionPanel.add(foodButton);
        buttonActionPanel.add(billButton);
        buttonActionPanel.add(chartButton);
        buttonActionPanel.add(logoutButton);

        buttonActionPanel.setBounds(0, 80, 250, Utils.Config.ConfigFrame.HEIGHT_FRAME - 80);
        buttonActionPanel.setBackground(Color.WHITE);

        // Thêm đường viền cho 2 Panel phụ trong Panel chính
        headActionPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        // Thêm trước khi trả về
        panel.add(headActionPanel);
        panel.add(buttonActionPanel);

        // Tạo đường viền ở phía bên phải
        panel.setBorder(new MatteBorder(0, 0, 0, 1, Color.GRAY));
        return panel;
    }

    public JPanel infoPanelDesign() {
        this.cardLayout = new CardLayout();
        JPanel panel = new JPanel(this.cardLayout);

        DashBoardPanel dashBoardPanel = new DashBoardPanel();
        dashBoardPanel.setBackground(Color.decode("#2196F3"));

        AccountPanel accountPanel = new AccountPanel();
        accountPanel.setBackground(Color.decode("#1E88E5"));

        HistoryPanel historyPanel = new HistoryPanel();
        historyPanel.setBackground(Color.decode("#1976D2"));


        // Thêm trước khi trả về
        panel.add(dashBoardPanel, "DashBoardPanel");
        panel.add(accountPanel, "AccountPanel");
        panel.add(historyPanel, "HistoryPanel");

        // Tạo đường viền ở bên trái
        panel.setBorder(new MatteBorder(0, 1, 0, 0, Color.GRAY));
        return panel;
    }

    public void initComponents() {
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

    public static void main(String[] args) {
        new AdminDashboard(new Staffs(), new Accounts()).setVisible(true);
    }
}
