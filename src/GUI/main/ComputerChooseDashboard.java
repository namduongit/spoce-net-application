package GUI.main;

import BLL.AccountBLL;
import BLL.ComputerBLL;
import BLL.ComputerSessionBLL;
import BLL.PlayerBLL;
import DTO.Computers;
import DTO.Players;
import GUI.Components.CustomButton;
import GUI.Components.CustomPanel;
import GUI.Components.CustomTextField;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class ComputerChooseDashboard extends JFrame {

    private AccountBLL accountBLL;
    private PlayerBLL playerBLL;
    private ComputerSessionBLL computerSessionBLL;
    private ComputerBLL computerBLL;
    private Computers currentComputer;
    private Players currentPlayer;

    private JPanel panelData;
    private CustomPanel clinePanel;

    private JLabel detailComputer;
    private CustomTextField detailComputerTextField;
    private JLabel pricePerHours;
    private CustomTextField detailPricePerHoursTextField;
    private JLabel detailPlayer;
    private CustomTextField detailPlayerTextField;
    private JLabel balancePlayer;
    private CustomTextField balancePlayerTextField;
    private JLabel timeStart;
    private CustomTextField detailTimeStart;
    private JLabel totalMoney;
    private CustomTextField totalMoneyTextField;

    private CustomButton logOutButton;

    private LocalDateTime timeNow;
    private Timer timer;

    private int sessionId;


    public ComputerChooseDashboard(Computers currentComputer, Players currentPlayer, int sessionId) {
        this.accountBLL = new AccountBLL();
        this.playerBLL = new PlayerBLL();
        this.sessionId = sessionId;

        this.computerSessionBLL = new ComputerSessionBLL();
        this.computerBLL = new ComputerBLL();

        this.currentComputer = currentComputer;
        this.currentPlayer = currentPlayer;
        this.timeNow = LocalDateTime.now();

        System.out.println("Đang nhận phiên chơi: "+ this.sessionId);


        initComponents();
    }

    private void initComponents() {
        // Cài đặt cửa sổ chính
        setSize(Utils.Config.ConfigFrame.WIDTH_FRAME, Utils.Config.ConfigFrame.HEIGHT_FRAME);
        setTitle("Máy tính: " + currentComputer.getComputerId() + " - " + currentComputer.getName());
        setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Icon/icons8-game-100.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        panelData = new JPanel(null);
        panelData.setBackground(Color.decode("#D3D3D3")); // Màu xám nhạt

        initClientPanel();

        clinePanel.setBounds(this.getWidth() - 300, 0, 300, this.getHeight());
        panelData.add(clinePanel);

        setLayout(new BorderLayout());
        add(panelData, BorderLayout.CENTER);
    }

    private void initClientPanel() {
        clinePanel = new CustomPanel();
        clinePanel.setLayout(null);
        clinePanel.setBackground(Color.WHITE);
        clinePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font textFont = new Font("SansSerif", Font.PLAIN, 13);

        int x = 20, y = 20, width = 240, height = 30, gap = 50;

        // Máy tính
        detailComputer = new JLabel("Máy: " + currentComputer.getComputerId() + " - " + currentComputer.getName());
        detailComputer.setBounds(x, y, width, 25);
        detailComputer.setFont(labelFont);
        clinePanel.add(detailComputer);

        // Giá giờ
        y += gap;
        pricePerHours = new JLabel("Giá giờ:");
        pricePerHours.setBounds(x, y, width, 25);
        pricePerHours.setFont(labelFont);
        clinePanel.add(pricePerHours);

        y += 25;
        detailPricePerHoursTextField = new CustomTextField(Utils.Helper.Comon.formatMoney(currentComputer.getPricePerHour() + ""));
        detailPricePerHoursTextField.setBounds(x, y, width, height);
        detailPricePerHoursTextField.setEditable(false);
        detailPricePerHoursTextField.setFont(textFont);
        clinePanel.add(detailPricePerHoursTextField);

        // Người chơi
        y += gap;
        detailPlayer = new JLabel("Người chơi:");
        detailPlayer.setBounds(x, y, width, 25);
        detailPlayer.setFont(labelFont);
        clinePanel.add(detailPlayer);

        y += 25;
        String username = (currentPlayer != null)
                ? accountBLL.getAccountById(currentPlayer.getAccountId()).getUsername()
                : "Khách";
        detailPlayerTextField = new CustomTextField(username);
        detailPlayerTextField.setBounds(x, y, width, height);
        detailPlayerTextField.setEditable(false);
        detailPlayerTextField.setFont(textFont);
        clinePanel.add(detailPlayerTextField);

        // Số dư
        y += gap;
        balancePlayer = new JLabel("Số dư:");
        balancePlayer.setBounds(x, y, width, 25);
        balancePlayer.setFont(labelFont);
        clinePanel.add(balancePlayer);

        y += 25;
        String balance = (currentPlayer != null)
                ? Utils.Helper.Comon.formatMoney(currentPlayer.getBalance() + "")
                : "N/A";
        balancePlayerTextField = new CustomTextField(balance);
        balancePlayerTextField.setBounds(x, y, width, height);
        balancePlayerTextField.setEditable(false);
        balancePlayerTextField.setFont(textFont);
        clinePanel.add(balancePlayerTextField);

        // Thời gian bắt đầu
        y += gap;
        timeStart = new JLabel("Bắt đầu lúc:");
        timeStart.setBounds(x, y, width, 25);
        timeStart.setFont(labelFont);
        clinePanel.add(timeStart);

        y += 25;
        detailTimeStart = new CustomTextField(timeNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        detailTimeStart.setBounds(x, y, width, height);
        detailTimeStart.setEditable(false);
        detailTimeStart.setFont(textFont);
        clinePanel.add(detailTimeStart);

        // Tổng tiền
        y += gap;
        totalMoney = new JLabel("Tổng tiền:");
        totalMoney.setBounds(x, y, width, 25);
        totalMoney.setFont(labelFont);
        clinePanel.add(totalMoney);

        y += 25;
        totalMoneyTextField = new CustomTextField("0 VND");
        totalMoneyTextField.setBounds(x, y, width, height);
        totalMoneyTextField.setEditable(false);
        totalMoneyTextField.setFont(textFont);
        clinePanel.add(totalMoneyTextField);

        // Nút đăng xuất
        y += gap;
        logOutButton = Utils.Helper.CreateComponent.createBrownButton("Đăng xuất");
        logOutButton.setBounds(x, y, width, 30);
        logOutButton.setFont(labelFont);
        logOutButton.setForeground(Color.WHITE);
        logOutButton.addActionListener(e -> {
            if (currentPlayer == null) {
                JOptionPane.showMessageDialog(this, "Bạn đang mở máy nên không thể đăng xuất", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            timer.stop();
            HashMap<String, Object> values = new HashMap<>();
            values.put("status", "Đang chờ sử dụng");

            this.accountBLL.updateAccountStatus(this.currentPlayer.getAccountId(), "Offline");

            this.computerSessionBLL.updateEndTimeOfComputerSession(this.currentComputer.getComputerId());
            this.computerBLL.updateComputerById(this.currentComputer.getComputerId(), values);
            this.computerSessionBLL.updateTotalCostOfComputerSession(this.sessionId, this.currentComputer.getPricePerHour());

            new ChooseComputer().setVisible(true);
            dispose();
        });
        clinePanel.add(logOutButton);

        startBalanceMonitor();
    }

    private void startBalanceMonitor() {
        timer = new Timer(1000, e -> {
            long secondsPlayed = java.time.Duration.between(timeNow, LocalDateTime.now()).getSeconds();

            double pricePerSecond = currentComputer.getPricePerHour() / 3600.0;
            int moneyToSubtract = (int) (secondsPlayed * pricePerSecond);

            // Cập nhật tổng tiền của phiên chơi
            this.computerSessionBLL.updateSessionCost(this.sessionId, moneyToSubtract);

            // Trường hợp đăng nhập để chơi game
            if (currentPlayer != null) {
                if (moneyToSubtract >= currentPlayer.getBalance()) {
                    totalMoneyTextField.setText(Utils.Helper.Comon.formatMoney(currentPlayer.getBalance() + ""));
                    balancePlayerTextField.setText("0 VND");
                    this.accountBLL.updateAccountStatus(this.currentPlayer.getAccountId(), "Offline");

                    timer.stop();

                    JOptionPane.showMessageDialog(this, "Tài khoản đã hết tiền. Hệ thống sẽ tự động đăng xuất.", "Thông báo", JOptionPane.WARNING_MESSAGE);

                    new ChooseComputer().setVisible(true);
                    dispose();
                    return;
                }

                // Cập nhật tổng tiên nếu có người dùng
                int remainingBalance = currentPlayer.getBalance() - (int) pricePerSecond;
                currentPlayer.setBalance(remainingBalance);
                balancePlayerTextField.setText(Utils.Helper.Comon.formatMoney(remainingBalance + ""));

                // Cập nhật số dư vào database
                this.accountBLL.updateBalancePlayerAccount(this.accountBLL.getAccountById(this.currentPlayer.getAccountId()).getUsername(), remainingBalance);
            }

            // Cập nhật tổng tiền lên giao diện
            totalMoneyTextField.setText(Utils.Helper.Comon.formatMoney(moneyToSubtract + ""));

            // Get computerData
            Computers computersTemp = this.computerBLL.getComputerById(this.currentComputer.getComputerId());
            if (!computersTemp.getStatus().equalsIgnoreCase("Đang sử dụng")) {
                JOptionPane.showMessageDialog(this, "Bạn đã bị đuổi khỏi phiên chơi", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                timer.stop();
                dispose();
                new ChooseComputer().setVisible(true);
            }
        });


        timer.start();
    }
}
