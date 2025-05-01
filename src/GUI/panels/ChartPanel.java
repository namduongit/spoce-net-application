package GUI.panels;

import BLL.ComputerSessionBLL;
import BLL.FoodRevenueBLL;
import DTO.FoodRevenue;
import GUI.Components.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class ChartPanel extends JPanel {
    private final FoodRevenueBLL foodRevenueBLL;
    private final ComputerSessionBLL sessionBLL;

    private CustomPanel headerPanel;
    private CustomPanel filterPanel;
    private CustomPanel dataPanel;
    private CustomPanel chartDisplayPanel;
    private CustomPanel infoPanel;
    private CustomPanel sessionPanel;
    private CustomPanel billPanel;
    private CustomPanel filterSwitchPanel;
    private CardLayout cardLayout;
    private CardLayout filterCardLayout;

    private CustomCombobox<String> roomComboBox;
    private CustomCombobox<String> categoryComboBox;
    private CustomTextField startDateField;
    private CustomTextField endDateField;
    private CustomButton applyFilterButton;
    private CustomButton printBillButton;

    private final ArrayList<String> roomNames;
    private String currentPanel = "BillPanel";
    private JLabel infoLabel;
    private JPanel infoContentPanel;
    private CustomScrollPane infoScrollPane;

    // Định dạng tiền tệ
    private final DecimalFormat currencyFormat = new DecimalFormat("#,###,### VND");

    public ChartPanel() {
        this.foodRevenueBLL = new FoodRevenueBLL();
        this.sessionBLL = new ComputerSessionBLL();

        this.roomNames = sessionBLL.getRoomNames();
        this.roomNames.add(0, "Tất cả");

        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        headerPanel = createHeaderPanel();
        filterPanel = createFilterPanel();
        dataPanel = createDataPanel();

        headerPanel.setBounds(10, 0, 1080, 140);
        filterPanel.setBounds(10, 150, 1080, 80);
        dataPanel.setBounds(10, 240, 1080, 420);

        add(headerPanel);
        add(filterPanel);
        add(dataPanel);
    }

    private CustomPanel createHeaderPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("THỐNG KÊ DOANH THU");
        title.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        title.setBounds(450, 25, 500, 50);
        panel.add(title);

        CustomButton sessionButton = new CustomButton("Phiên chơi");
        sessionButton.addActionListener(e -> switchToPanel("SessionPanel", "RoomFilter"));
        sessionButton.setBounds(20, 100, 175, 35);
        panel.add(sessionButton);

        CustomButton billButton = new CustomButton("Hóa đơn");
        billButton.addActionListener(e -> switchToPanel("BillPanel", "CategoryFilter"));
        billButton.setBounds(205, 100, 165, 35);
        panel.add(billButton);

        return panel;
    }

    private void switchToPanel(String panelName, String filterName) {
        cardLayout.show(chartDisplayPanel, panelName);
        filterCardLayout.show(filterSwitchPanel, filterName);
        currentPanel = panelName;
        updateChart();
    }

    private CustomPanel createFilterPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        filterSwitchPanel = new CustomPanel();
        filterCardLayout = new CardLayout();
        filterSwitchPanel.setLayout(filterCardLayout);
        filterSwitchPanel.setBounds(20, 20, 300, 35);

        CustomPanel roomFilterPanel = new CustomPanel();
        roomFilterPanel.setLayout(null);
        roomFilterPanel.setBackground(Color.WHITE);
        JLabel roomLabel = new JLabel("Phòng máy:");
        roomLabel.setBounds(0, 0, 100, 35);
        roomFilterPanel.add(roomLabel);
        roomComboBox = new CustomCombobox<>(roomNames);
        roomComboBox.setBounds(100, 0, 200, 35);
        roomFilterPanel.add(roomComboBox);

        CustomPanel categoryFilterPanel = new CustomPanel();
        categoryFilterPanel.setLayout(null);
        categoryFilterPanel.setBackground(Color.WHITE);
        JLabel categoryLabel = new JLabel("Loại sản phẩm:");
        categoryLabel.setBounds(0, 0, 100, 35);
        categoryFilterPanel.add(categoryLabel);
        ArrayList<String> categories = new ArrayList<>(foodRevenueBLL.getCategory());
        categories.add(0, "Tất cả");
        categoryComboBox = new CustomCombobox<>(categories);
        categoryComboBox.setBounds(100, 0, 200, 35);
        categoryFilterPanel.add(categoryComboBox);

        filterSwitchPanel.add(roomFilterPanel, "RoomFilter");
        filterSwitchPanel.add(categoryFilterPanel, "CategoryFilter");
        filterCardLayout.show(filterSwitchPanel, "CategoryFilter");
        panel.add(filterSwitchPanel);

        JLabel startDateLabel = new JLabel("Từ ngày:");
        startDateLabel.setBounds(350, 20, 80, 35);
        panel.add(startDateLabel);

        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        startDateField = new CustomTextField(today);
        startDateField.setBounds(420, 20, 150, 35);
        panel.add(startDateField);

        JLabel endDateLabel = new JLabel("Đến ngày:");
        endDateLabel.setBounds(600, 20, 80, 35);
        panel.add(endDateLabel);

        endDateField = new CustomTextField(today);
        endDateField.setBounds(670, 20, 150, 35);
        panel.add(endDateField);

        applyFilterButton = new CustomButton("Áp dụng");
        applyFilterButton.setBounds(850, 20, 100, 35);
        applyFilterButton.addActionListener(e -> updateChart());
        panel.add(applyFilterButton);

        printBillButton = new CustomButton("In hóa đơn");
        printBillButton.setBounds(960, 20, 100, 35);
        printBillButton.addActionListener(e ->
            JOptionPane.showMessageDialog(null, "Chức năng in hóa đơn chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE));
        panel.add(printBillButton);

        return panel;
    }

    private CustomPanel createDataPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBackground(Color.WHITE);

        chartDisplayPanel = new CustomPanel();
        cardLayout = new CardLayout();
        chartDisplayPanel.setLayout(cardLayout);
        chartDisplayPanel.setBackground(Color.WHITE);

        sessionPanel = new CustomPanel();
        sessionPanel.setLayout(new BorderLayout());
        sessionPanel.setBackground(Color.WHITE);
        sessionPanel.add(createPlaceholderLabel(), BorderLayout.CENTER);

        billPanel = new CustomPanel();
        billPanel.setLayout(new BorderLayout());
        billPanel.setBackground(Color.WHITE);
        billPanel.add(createPlaceholderLabel(), BorderLayout.CENTER);

        chartDisplayPanel.add(sessionPanel, "SessionPanel");
        chartDisplayPanel.add(billPanel, "BillPanel");
        cardLayout.show(chartDisplayPanel, "BillPanel");

        infoPanel = new CustomPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);

        infoLabel = new JLabel("Tổng Tiền Thống Kê Doanh Thu : 0 VND", SwingConstants.CENTER);
        infoLabel.setForeground(Color.RED);
        infoLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        infoPanel.add(infoLabel, BorderLayout.SOUTH);

        infoContentPanel = new JPanel();
        infoContentPanel.setLayout(new BoxLayout(infoContentPanel, BoxLayout.Y_AXIS));
        infoContentPanel.setBackground(Color.WHITE);

        infoScrollPane = new CustomScrollPane(infoContentPanel);
        infoScrollPane.setBackground(Color.WHITE);
        infoPanel.add(infoScrollPane, BorderLayout.CENTER);

        panel.add(chartDisplayPanel);
        panel.add(infoPanel);
        return panel;
    }

    private JLabel createPlaceholderLabel() {
        JLabel placeholder = new JLabel("Vui lòng bấm Áp dụng để xem dữ liệu", SwingConstants.CENTER);
        placeholder.setFont(new Font("Sans-serif", Font.PLAIN, 16));
        return placeholder;
    }

    // Phương thức tiện ích để định dạng giá tiền
    private String formatCurrency(double amount) {
        return currencyFormat.format(amount);
    }

    private void updateChart() {
        String startDate = startDateField.getText().trim();
        String endDate = endDateField.getText().trim();

        if (startDate.isEmpty() || endDate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ ngày!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDateTime start, end;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            start = LocalDateTime.parse(startDate + " 00:00:00", formatter);
            end = LocalDateTime.parse(endDate + " 23:59:59", formatter);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Định dạng ngày không hợp lệ (YYYY-MM-DD)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tính tổng doanh thu
        ArrayList<Object[]> computerData = sessionBLL.getComputerRevenue(start, end);
        double computerRevenueTotal = computerData.stream()
            .mapToDouble(row -> (Double) row[2])
            .sum();

        FoodRevenue pendingInfo = foodRevenueBLL.getPendingBillsInfo(start, end);
        double foodRevenueTotal = pendingInfo.getTotalRevenue();
        double totalRevenue = computerRevenueTotal + foodRevenueTotal;
        infoLabel.setText("Tổng Tiền Thống Kê Doanh Thu : " + formatCurrency(totalRevenue));

        updateInfoPanel(start, end, computerData);

        if (currentPanel.equals("SessionPanel")) {
            String selectedRoom = roomComboBox.getSelectedItem().toString();
            System.out.println("Current Panel: " + currentPanel + ", Room: " + selectedRoom + ", Start: " + start + ", End: " + end);
            updatePanelWithChart(sessionPanel, createComputerRevenueChart(start, end, selectedRoom, computerData));
        } else {
            String selectedCategory = categoryComboBox.getSelectedItem().toString();
            System.out.println("Current Panel: " + currentPanel + ", Category: " + selectedCategory + ", Start: " + start + ", End: " + end);
            updatePanelWithChart(billPanel, createFoodRevenueChart(start, end, selectedCategory));
        }
    }

    private void updatePanelWithChart(CustomPanel panel, CustomPieChart pieChart) {
        panel.removeAll();
        pieChart.setBounds(0, 0, 540, 400);
        panel.add(pieChart, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    private CustomPieChart createFoodRevenueChart(LocalDateTime start, LocalDateTime end, String category) {
        ArrayList<Object[]> data = foodRevenueBLL.getFoodRevenueByCategory(start, end, category);
        System.out.println("FoodRevenue data size: " + data.size());

        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu doanh thu món ăn trong khoảng thời gian này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return createEmptyPieChart("Không có dữ liệu");
        }

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Number> values = new ArrayList<>();
        for (Object[] row : data) {
            labels.add((String) row[1]);
            values.add((Double) row[4]);
        }

        CustomPieChart pieChart = new CustomPieChart(labels.toArray(new String[0]), values.toArray(new Number[0]));
        pieChart.getChart().setTitle("Doanh thu món ăn");
        return pieChart;
    }

    private CustomPieChart createComputerRevenueChart(LocalDateTime start, LocalDateTime end, String selectedRoom, ArrayList<Object[]> data) {
        System.out.println("ComputerRevenue data size before filtering: " + data.size());

        if (!selectedRoom.equals("Tất cả")) {
            ArrayList<Object[]> filteredData = new ArrayList<>();
            for (Object[] row : data) {
                String roomName = (String) row[3];
                if (roomName != null && roomName.equals(selectedRoom)) {
                    filteredData.add(row);
                }
            }
            data = filteredData;
        }

        System.out.println("ComputerRevenue data size after filtering: " + data.size());
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu doanh thu máy trong khoảng thời gian này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return createEmptyPieChart("Không có dữ liệu");
        }

        ArrayList<String> validLabels = new ArrayList<>();
        ArrayList<Number> validValues = new ArrayList<>();
        HashSet<String> roomsInvolved = new HashSet<>();
        for (Object[] row : data) {
            try {
                String name = (String) row[0];
                int duration = (Integer) row[1];
                Double revenue = (Double) row[2];
                String roomName = (String) row[3];
                if (name != null && !name.trim().isEmpty() && revenue != null && revenue > 0) {
                    validLabels.add(name);
                    validValues.add(revenue);
                    if (roomName != null) {
                        roomsInvolved.add(roomName);
                    }
                    System.out.println("Computer: " + name + ", Duration: " + duration + ", Revenue: " + revenue + ", Room: " + roomName);
                } else {
                    System.out.println("Skipped invalid data: Name=" + name + ", Revenue=" + revenue);
                }
            } catch (Exception e) {
                System.err.println("Error processing computer revenue data: " + e.getMessage());
            }
        }

        if (validLabels.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu doanh thu máy hợp lệ trong khoảng thời gian này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return createEmptyPieChart("Không có dữ liệu");
        }

        String chartTitle = selectedRoom.equals("Tất cả")
            ? "Doanh thu máy: " + (roomsInvolved.isEmpty() ? "Không có phòng" : String.join(", ", roomsInvolved))
            : "Doanh thu máy: " + selectedRoom;
        CustomPieChart pieChart = new CustomPieChart(validLabels.toArray(new String[0]), validValues.toArray(new Number[0]));
        pieChart.getChart().setTitle(chartTitle);
        return pieChart;
    }

    private CustomPieChart createEmptyPieChart(String title) {
        CustomPieChart pieChart = new CustomPieChart(new String[]{"Không có dữ liệu"}, new Number[]{1});
        pieChart.getChart().setTitle(title);
        return pieChart;
    }

    private void updateInfoPanel(LocalDateTime start, LocalDateTime end, ArrayList<Object[]> computerData) {
        infoContentPanel.removeAll();

        if (currentPanel.equals("SessionPanel")) {
            updateSessionInfoPanel(start, end, computerData);
        } else {
            updateBillInfoPanel(start, end);
        }

        infoContentPanel.revalidate();
        infoContentPanel.repaint();
    }

    private void updateSessionInfoPanel(LocalDateTime start, LocalDateTime end, ArrayList<Object[]> computerData) {
        String selectedRoom = roomComboBox.getSelectedItem().toString();
        HashMap<String, Double> roomRevenueMap = calculateRoomRevenue(computerData);

        if (selectedRoom.equals("Tất cả")) {
            ArrayList<String> allRooms = sessionBLL.getAllRooms();
            for (String room : allRooms) {
                displayRoomInfo(room, computerData, roomRevenueMap);
            }
            double totalAllRooms = roomRevenueMap.values().stream().mapToDouble(Double::doubleValue).sum();
            addLabel("Tổng tất cả phòng: " + formatCurrency(totalAllRooms), Font.BOLD, 14, Color.RED, 10);
        } else {
            displayRoomInfo(selectedRoom, computerData, roomRevenueMap);
        }
    }

    private HashMap<String, Double> calculateRoomRevenue(ArrayList<Object[]> computerData) {
        HashMap<String, Double> roomRevenueMap = new HashMap<>();
        for (Object[] row : computerData) {
            String roomName = (String) row[3];
            double revenue = (Double) row[2];
            if (roomName != null) {
                roomRevenueMap.put(roomName, roomRevenueMap.getOrDefault(roomName, 0.0) + revenue);
            }
        }
        return roomRevenueMap;
    }

    private void displayRoomInfo(String room, ArrayList<Object[]> computerData, HashMap<String, Double> roomRevenueMap) {
        addLabel("Phòng: " + room, Font.BOLD, 14, Color.BLACK, 10);

        boolean hasComputers = false;
        for (Object[] row : computerData) {
            String roomName = (String) row[3];
            if (roomName != null && roomName.equals(room)) {
                hasComputers = true;
                String computerName = (String) row[0];
                int duration = (Integer) row[1];
                double totalCost = (Double) row[2];
                addLabel(String.format("  Máy: %s - Thời Gian: %d phút - Giá: %s", computerName, duration, formatCurrency(totalCost)),
                    Font.PLAIN, 12, Color.BLACK, 20);
            }
        }

        if (!hasComputers) {
            addLabel("  Không có", Font.PLAIN, 12, Color.BLACK, 20);
        }

        double roomRevenue = roomRevenueMap.getOrDefault(room, 0.0);
        addLabel("  Tổng tiền: " + formatCurrency(roomRevenue), Font.PLAIN, 12, Color.BLUE, 20);
        infoContentPanel.add(Box.createVerticalStrut(10));
    }

    private void updateBillInfoPanel(LocalDateTime start, LocalDateTime end) {
        String selectedCategory = categoryComboBox.getSelectedItem().toString();
        ArrayList<Object[]> categoryData = foodRevenueBLL.getFoodRevenueByCategory(start, end, selectedCategory);

        if (categoryData.isEmpty()) {
            addLabel("Không có dữ liệu doanh thu trong khoảng thời gian này!", Font.PLAIN, 14, Color.BLACK, 10);
        } else {
            displayCategoryData(categoryData);
        }

        updateBillStatusInfo(start, end);
    }

    private void displayCategoryData(ArrayList<Object[]> categoryData) {
        String currentCategory = "";
        double categoryTotal = 0;
        double overallTotal = 0;

        for (Object[] row : categoryData) {
            String categoryName = (String) row[0];
            String foodName = (String) row[1];
            int price = (Integer) row[2];
            int quantity = (Integer) row[3];
            double total = (Double) row[4];

            if (!categoryName.equals(currentCategory)) {
                if (!currentCategory.isEmpty()) {
                    addLabel("  Tổng tiền: " + formatCurrency(categoryTotal), Font.PLAIN, 12, Color.BLUE, 20);
                    infoContentPanel.add(Box.createVerticalStrut(10));
                }
                currentCategory = categoryName;
                categoryTotal = 0;
                addLabel(currentCategory + ":", Font.BOLD, 14, Color.BLACK, 10);
            }

            addLabel(String.format("  %s - Giá: %s - SL: %d", foodName, formatCurrency(price), quantity), Font.PLAIN, 12, Color.BLACK, 20);
            categoryTotal += total;
            overallTotal += total;
        }

        if (!currentCategory.isEmpty()) {
            addLabel("  Tổng tiền: " + formatCurrency(categoryTotal), Font.PLAIN, 12, Color.BLUE, 20);
        }

        addLabel("Tổng tiền: " + formatCurrency(overallTotal), Font.BOLD, 14, Color.RED, 10);
    }

    private void updateBillStatusInfo(LocalDateTime start, LocalDateTime end) {
        if (currentPanel.equals("BillPanel")) {
            HashMap<String, Integer> statusCounts = foodRevenueBLL.getBillStatusCounts(start, end);
            int processedCount = statusCounts.getOrDefault("Đã xử lý", 0);
            int pendingCount = statusCounts.getOrDefault("Chưa xử lý", 0);
            int canceledCount = statusCounts.getOrDefault("Đã hủy", 0);

            addLabel(String.format("Hóa đơn: Đã xử lý: %d, Chưa xử lý: %d, Đã hủy: %d", processedCount, pendingCount, canceledCount),
                Font.PLAIN, 12, Color.BLACK, 10);
        }
    }

    private void addLabel(String text, int style, int size, Color color, int leftPadding) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Sans-serif", style, size));
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(0, leftPadding, 0, 0)); // Thêm thụt lề bên trái
        infoContentPanel.add(label);
    }
}