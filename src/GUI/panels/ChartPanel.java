package GUI.panels;

import BLL.ComputerSessionBLL;
import BLL.FoodRevenueBLL;
import BLL.PurchaseReceiptBLL;
import DTO.FoodRevenue;
import GUI.Components.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ChartPanel extends JPanel {

    private final FoodRevenueBLL foodRevenueBLL;
    private final ComputerSessionBLL sessionBLL;
    private final PurchaseReceiptBLL purchaseReceiptBLL;

    // Các panel chính của giao diện
    private CustomPanel headerPanel;
    private CustomPanel filterPanel;
    private CustomPanel dataPanel;

    private CustomPanel chartDisplayPanel;
    private CustomPanel infoPanel;

    private CustomPanel sessionPanel;
    private CustomPanel billPanel;
    private CustomPanel inboundPanel;

    private CustomPanel filterSwitchPanel;
    private CardLayout cardLayout;
    private CardLayout filterCardLayout;

    // Các thành phần giao diện cho bộ lọc
    private CustomCombobox<String> roomComboBox; // Chọn phòng máy
    private CustomCombobox<String> categoryComboBox; // Chọn loại sản phẩm
    private CustomCombobox<String> inboundCateCombobox; // Chọn coi nhập thức ăn nào
    private CustomTextField startDateField;
    private CustomTextField endDateField;
    private CustomButton applyFilterButton;

    private String currentPanel = "BillPanel"; // Trạng thái panel hiện tại (mặc định là hóa đơn)

    // Các thành phần hiển thị thông tin
    private JLabel infoLabel;
    private JPanel infoContentPanel;
    private CustomScrollPane infoScrollPane;

    private final DecimalFormat currencyFormat = new DecimalFormat("#,###,### VND");

    public ChartPanel() {
        foodRevenueBLL = new FoodRevenueBLL();
        sessionBLL = new ComputerSessionBLL();
        purchaseReceiptBLL = new PurchaseReceiptBLL();

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
        billButton.setBounds(205, 100, 175, 35);
        panel.add(billButton);

        CustomButton inboundButton = new CustomButton("Phiếu nhập");
        inboundButton.addActionListener(e -> switchToPanel("InBoundPanel", "InBoundFilter"));
        inboundButton.setBounds(390, 100, 175, 35);
        panel.add(inboundButton);

        return panel;
    }

    private void switchToPanel(String panelName, String filterName) {
        currentPanel = panelName;
        resetFilterValues(filterName);

        cardLayout.show(chartDisplayPanel, panelName);
        filterCardLayout.show(filterSwitchPanel, filterName);

        updateChart();
    }

    private void resetFilterValues(String filterName) {
        switch (filterName) {
            case "RoomFilter":
                roomComboBox.setSelectedItem("Tất cả");
                break;
            case "CategoryFilter":
                categoryComboBox.setSelectedItem("Tất cả");
                break;
            case "InBoundFilter":
                inboundCateCombobox.setSelectedItem("Tất cả");
                break;
        }
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
        ArrayList<String> rooms = new ArrayList<>(sessionBLL.getAllRooms());
        rooms.add(0, "Tất cả");
        roomComboBox = new CustomCombobox<>(rooms);
        roomComboBox.setBounds(100, 0, 200, 35);
        roomFilterPanel.add(roomComboBox);

        CustomPanel categoryFilterPanel = new CustomPanel();
        categoryFilterPanel.setLayout(null);
        categoryFilterPanel.setBackground(Color.WHITE);
        JLabel categoryLabel = new JLabel("Loại sản phẩm:");
        categoryLabel.setBounds(0, 0, 100, 35);
        categoryFilterPanel.add(categoryLabel);
        ArrayList<String> categories = new ArrayList<>(foodRevenueBLL.getAllCategory());
        categories.add(0, "Tất cả");
        categoryComboBox = new CustomCombobox<>(categories);
        categoryComboBox.setBounds(100, 0, 200, 35);
        categoryFilterPanel.add(categoryComboBox);

        CustomPanel inboundFilterPanel = new CustomPanel();
        inboundFilterPanel.setLayout(null);
        inboundFilterPanel.setBackground(Color.WHITE);
        JLabel categoriesLabel = new JLabel("Loại sản phẩm:");
        categoriesLabel.setBounds(0, 0, 100, 35);
        inboundFilterPanel.add(categoriesLabel);
        ArrayList<String> cates = new ArrayList<>(foodRevenueBLL.getAllCategory());
        cates.add(0, "Tất cả");
        inboundCateCombobox = new CustomCombobox<>(cates);
        inboundCateCombobox.setBounds(100, 0, 200, 35);
        inboundFilterPanel.add(inboundCateCombobox);

        filterSwitchPanel.add(roomFilterPanel, "RoomFilter");
        filterSwitchPanel.add(categoryFilterPanel, "CategoryFilter");
        filterSwitchPanel.add(inboundFilterPanel, "InBoundFilter");

        filterCardLayout.show(filterSwitchPanel, "CategoryFilter");
        panel.add(filterSwitchPanel);

        JLabel startDateLabel = new JLabel("Từ ngày:");
        startDateLabel.setBounds(350, 20, 80, 35);
        panel.add(startDateLabel);
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        startDateField = new CustomTextField("2000-01-01");
        startDateField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (startDateField.getText().equalsIgnoreCase("2000-01-01")) {
                    startDateField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (startDateField.getText().isEmpty()) {
                    startDateField.setText("2000-01-01");
                }
            }
        });
        startDateField.setBounds(420, 20, 150, 35);
        panel.add(startDateField);

        JLabel endDateLabel = new JLabel("Đến ngày:");
        endDateLabel.setBounds(600, 20, 80, 35);
        panel.add(endDateLabel);
        endDateField = new CustomTextField(today);
        endDateField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (endDateField.getText().equalsIgnoreCase(today)) {
                    endDateField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (endDateField.getText().isEmpty()) {
                    endDateField.setText(today);
                }
            }
        });
        endDateField.setBounds(670, 20, 150, 35);
        panel.add(endDateField);

        applyFilterButton = new CustomButton("Áp dụng");
        applyFilterButton.setBounds(850, 20, 100, 35);
        applyFilterButton.addActionListener(e -> updateChart());
        panel.add(applyFilterButton);

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

        inboundPanel = new CustomPanel();
        inboundPanel.setLayout(new BorderLayout());
        inboundPanel.setBackground(Color.WHITE);
        inboundPanel.add(createPlaceholderLabel(), BorderLayout.CENTER);

        chartDisplayPanel.add(sessionPanel, "SessionPanel");
        chartDisplayPanel.add(billPanel, "BillPanel");
        chartDisplayPanel.add(inboundPanel, "InBoundPanel");
        cardLayout.show(chartDisplayPanel, "BillPanel");

        infoPanel = new CustomPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);

        infoLabel = new JLabel("Tổng Tiền Thống Kê Doanh Thu: 0 VND", SwingConstants.CENTER);
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
            JOptionPane.showMessageDialog(null, "Định dạng ngày không hợp lệ (YYYY-MM-DD)", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Object[]> computerData = sessionBLL.getComputerRevenue(start, end);
        double computerRevenueTotal = computerData.stream()
                .mapToDouble(row -> (Double) row[2])
                .sum();

        FoodRevenue pendingInfo = foodRevenueBLL.getPendingBillsInfo(start, end);
        double foodRevenueTotal = pendingInfo.getTotalRevenue();
        double totalRevenue = computerRevenueTotal + foodRevenueTotal;

        infoLabel.setText("Tổng Tiền Thống Kê Doanh Thu: " + formatCurrency(totalRevenue));

        infoContentPanel.removeAll();

        switch (currentPanel) {
            case "SessionPanel":
                String selectedRoom = roomComboBox.getSelectedItem() != null ? roomComboBox.getSelectedItem().toString()
                        : "Tất cả";
                updatePanelWithChart(sessionPanel, createComputerRevenueChart(start, end, selectedRoom, computerData));
                updateSessionInfoPanel(start, end, computerData);
                break;
            case "BillPanel":
                String selectedCategory = categoryComboBox.getSelectedItem() != null
                        ? categoryComboBox.getSelectedItem().toString()
                        : "Tất cả";
                updatePanelWithChart(billPanel, createFoodRevenueChart(start, end, selectedCategory));
                updateBillInfoPanel(start, end);
                break;
            case "InBoundPanel":
                String selectedCate = inboundCateCombobox.getSelectedItem() != null
                        ? inboundCateCombobox.getSelectedItem().toString()
                        : "Tất cả";
                updatePanelWithChart(inboundPanel, createInboundRevenueChart(start, end, selectedCate));
                updateInboundInfoPanel(start, end);
                break;
        }

        infoContentPanel.revalidate();
        infoContentPanel.repaint();
    }

    private void updateInboundInfoPanel(LocalDateTime start, LocalDateTime end) {
        String selectedCate = inboundCateCombobox.getSelectedItem().toString();
        ArrayList<Object[]> inboundData = purchaseReceiptBLL.getInboundRevenueByCategory(start, end, selectedCate);

        if (inboundData.isEmpty()) {
            addLabel("Không có dữ liệu phiếu nhập trong khoảng thời gian này!", Font.PLAIN, 14, Color.BLACK, 10);
        } else {
            double totalInbound = 0;
            for (Object[] row : inboundData) {
                String foodName = (String) row[9];
                int quantity = (Integer) row[10];
                int price = (Integer) row[11];
                double total = quantity * price;
                addLabel(String.format("  %s - Giá: %s - SL: %d", foodName, formatCurrency(price), quantity),
                        Font.PLAIN, 12, Color.BLACK, 20);
                totalInbound += total;
            }
            addLabel("Tổng tiền: " + formatCurrency(totalInbound), Font.BOLD, 14, Color.RED, 10);
        }
    }

    private void updatePanelWithChart(CustomPanel panel, CustomPieChart pieChart) {
        panel.removeAll();
        pieChart.setBounds(0, 0, 540, 400);
        panel.add(pieChart, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    private CustomPieChart createInboundRevenueChart(LocalDateTime start, LocalDateTime end, String category) {
        ArrayList<Object[]> data = purchaseReceiptBLL.getInboundRevenueByCategory(start, end, category);
        if (data.isEmpty()) {
            return createEmptyPieChart("Không có dữ liệu");
        }

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Number> values = new ArrayList<>();
        for (Object[] row : data) {
            String foodName = (String) row[9];
            int quantity = (Integer) row[10];
            int price = (Integer) row[11];
            double total = quantity * price;
            if (foodName != null && total > 0) {
                labels.add(foodName);
                values.add(total);
            }
        }

        if (labels.isEmpty()) {
            return createEmptyPieChart("Không có dữ liệu");
        }

        CustomPieChart pieChart = new CustomPieChart(labels.toArray(new String[0]), values.toArray(new Number[0]));
        pieChart.getChart().setTitle("Thống kê phiếu nhập");
        return pieChart;
    }

    private CustomPieChart createFoodRevenueChart(LocalDateTime start, LocalDateTime end, String category) {
        ArrayList<Object[]> data = foodRevenueBLL.getFoodRevenueByCategory(start, end, category);
        if (data.isEmpty()) {
            return createEmptyPieChart("Không có dữ liệu");
        }

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Number> values = new ArrayList<>();
        for (Object[] row : data) {
            String foodName = (String) row[1];
            Double revenue = (Double) row[4];
            if (foodName != null && revenue != null && revenue > 0) {
                labels.add(foodName);
                values.add(revenue);
            }
        }

        if (labels.isEmpty()) {
            return createEmptyPieChart("Không có dữ liệu");
        }

        CustomPieChart pieChart = new CustomPieChart(labels.toArray(new String[0]), values.toArray(new Number[0]));
        pieChart.getChart().setTitle("Doanh thu món ăn");
        return pieChart;
    }

    private CustomPieChart createComputerRevenueChart(LocalDateTime start, LocalDateTime end, String selectedRoom,
            ArrayList<Object[]> data) {
        ArrayList<Object[]> filteredData = new ArrayList<>();
        if (!selectedRoom.equals("Tất cả")) {
            for (Object[] row : data) {
                String roomName = (String) row[3];
                if (roomName != null && roomName.equals(selectedRoom)) {
                    filteredData.add(row);
                }
            }
        } else {
            filteredData = data;
        }

        if (filteredData.isEmpty()) {
            return createEmptyPieChart("Không có dữ liệu");
        }

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Number> values = new ArrayList<>();
        for (Object[] row : filteredData) {
            String name = (String) row[0];
            Double revenue = (Double) row[2];
            if (name != null && revenue != null && revenue > 0) {
                labels.add(name);
                values.add(revenue);
            }
        }

        if (labels.isEmpty()) {
            return createEmptyPieChart("Không có dữ liệu");
        }

        CustomPieChart pieChart = new CustomPieChart(labels.toArray(new String[0]), values.toArray(new Number[0]));
        pieChart.getChart().setTitle("Doanh thu phòng máy");
        return pieChart;
    }

    private CustomPieChart createEmptyPieChart(String title) {
        CustomPieChart pieChart = new CustomPieChart(new String[] { "Không có dữ liệu" }, new Number[] { 1 });
        pieChart.getChart().setTitle(title);
        return pieChart;
    }

    private void updateSessionInfoPanel(LocalDateTime start, LocalDateTime end, ArrayList<Object[]> computerData) {
        String selectedRoom = roomComboBox.getSelectedItem() != null ? roomComboBox.getSelectedItem().toString()
                : "Tất cả";
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

    private void displayRoomInfo(String room, ArrayList<Object[]> computerData,
            HashMap<String, Double> roomRevenueMap) {
        addLabel("Phòng: " + room, Font.BOLD, 14, Color.BLACK, 10);

        boolean hasComputers = false;
        for (Object[] row : computerData) {
            String roomName = (String) row[3];
            if (roomName != null && roomName.equals(room)) {
                hasComputers = true;
                String computerName = (String) row[0];
                int duration = (Integer) row[1];
                double totalCost = (Double) row[2];
                addLabel(
                        String.format("  Máy: %s - Thời Gian: %d phút - Giá: %s", computerName, duration,
                                formatCurrency(totalCost)),
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

            addLabel(String.format("  %s - Giá: %s - SL: %d", foodName, formatCurrency(price), quantity), Font.PLAIN,
                    12, Color.BLACK, 20);
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

            addLabel(
                    String.format("Hóa đơn: Đã xử lý: %d, Chưa xử lý: %d, Đã hủy: %d", processedCount, pendingCount,
                            canceledCount),
                    Font.PLAIN, 12, Color.BLACK, 10);
        }
    }

    private void addLabel(String text, int style, int size, Color color, int leftPadding) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Sans-serif", style, size));
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(0, leftPadding, 0, 0));
        infoContentPanel.add(label);
    }
}