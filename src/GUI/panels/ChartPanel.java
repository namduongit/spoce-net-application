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

    // Các panel chính của giao diện
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

    // Các thành phần giao diện cho bộ lọc
    private CustomCombobox<String> roomComboBox; // Chọn phòng máy
    private CustomCombobox<String> categoryComboBox; // Chọn loại sản phẩm
    private CustomTextField startDateField; 
    private CustomTextField endDateField;
    private CustomButton applyFilterButton; 

    private String currentPanel = "BillPanel"; // Trạng thái panel hiện tại (mặc định là hóa đơn)
    
    // Các thành phần hiển thị thông tin
    private JLabel infoLabel; 
    private JPanel infoContentPanel; 
    private CustomScrollPane infoScrollPane; 

    private final DecimalFormat currencyFormat = new DecimalFormat("#,###,### VND");

    // Khởi tạo ChartPanel, không tải danh sách phòng máy ngay
    public ChartPanel() {
        foodRevenueBLL = new FoodRevenueBLL();
        sessionBLL = new ComputerSessionBLL();
        initComponents();
    }

    // Khởi tạo bố cục giao diện, chia thành 3 phần: tiêu đề, bộ lọc, và dữ liệu
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

    // Tạo panel tiêu đề, chứa tiêu đề "THỐNG KÊ DOANH THU" và hai nút chuyển đổi
    private CustomPanel createHeaderPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        // Tiêu đề chính
        JLabel title = new JLabel("THỐNG KÊ DOANH THU");
        title.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        title.setBounds(450, 25, 500, 50);
        panel.add(title);

        // Nút "Phiên chơi" 
        CustomButton sessionButton = new CustomButton("Phiên chơi");
        sessionButton.addActionListener(e -> switchToPanel("SessionPanel", "RoomFilter"));
        sessionButton.setBounds(20, 100, 175, 35);
        panel.add(sessionButton);

        // Nút "Hóa đơn" 
        CustomButton billButton = new CustomButton("Hóa đơn");
        billButton.addActionListener(e -> switchToPanel("BillPanel", "CategoryFilter"));
        billButton.setBounds(205, 100, 165, 35);
        panel.add(billButton);

        return panel;
    }

    // Chuyển đổi giữa các panel biểu đồ và bộ lọc tương ứng
    private void switchToPanel(String panelName, String filterName) {
        cardLayout.show(chartDisplayPanel, panelName); 
        filterCardLayout.show(filterSwitchPanel, filterName); // Chuyển đổi bộ lọc tương ứng
        currentPanel = panelName;
        updateChart(); // Cập nhật biểu đồ
    }

    // Tạo panel bộ lọc, chứa các tùy chọn lọc dữ liệu (phòng máy, loại sản phẩm, ngày)
    private CustomPanel createFilterPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        // Panel chứa bộ lọc phòng máy hoặc loại sản phẩm
        filterSwitchPanel = new CustomPanel();
        filterCardLayout = new CardLayout();
        filterSwitchPanel.setLayout(filterCardLayout);
        filterSwitchPanel.setBounds(20, 20, 300, 35);

        // Bộ lọc phòng máy
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

        // Bộ lọc loại sản phẩm
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

        // Thêm các bộ lọc vào filterSwitchPanel
        filterSwitchPanel.add(roomFilterPanel, "RoomFilter");
        filterSwitchPanel.add(categoryFilterPanel, "CategoryFilter");
        filterCardLayout.show(filterSwitchPanel, "CategoryFilter"); // Mặc định hiển thị bộ lọc "loại sản phẩm"
        panel.add(filterSwitchPanel);

        // Nhãn và trường nhập ngày bắt đầu
        JLabel startDateLabel = new JLabel("Từ ngày:");
        startDateLabel.setBounds(350, 20, 80, 35);
        panel.add(startDateLabel);
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        startDateField = new CustomTextField(today);
        startDateField.setBounds(420, 20, 150, 35);
        panel.add(startDateField);

        // Nhãn và trường nhập ngày kết thúc
        JLabel endDateLabel = new JLabel("Đến ngày:");
        endDateLabel.setBounds(600, 20, 80, 35);
        panel.add(endDateLabel);
        endDateField = new CustomTextField(today);
        endDateField.setBounds(670, 20, 150, 35);
        panel.add(endDateField);

        // Nút áp dụng bộ lọc
        applyFilterButton = new CustomButton("Áp dụng");
        applyFilterButton.setBounds(850, 20, 100, 35);
        applyFilterButton.addActionListener(e -> updateChart());
        panel.add(applyFilterButton);

        return panel;
    }

    // Tạo panel dữ liệu, chứa biểu đồ và thông tin chi tiết
    private CustomPanel createDataPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(new GridLayout(1, 2)); // Chia thành 2 cột: biểu đồ và thông tin
        panel.setBackground(Color.WHITE);

        // Panel chứa biểu đồ
        chartDisplayPanel = new CustomPanel();
        cardLayout = new CardLayout();
        chartDisplayPanel.setLayout(cardLayout);
        chartDisplayPanel.setBackground(Color.WHITE);

        // Panel cho biểu đồ phiên chơi
        sessionPanel = new CustomPanel();
        sessionPanel.setLayout(new BorderLayout());
        sessionPanel.setBackground(Color.WHITE);
        sessionPanel.add(createPlaceholderLabel(), BorderLayout.CENTER);

        // Panel cho biểu đồ hóa đơn
        billPanel = new CustomPanel();
        billPanel.setLayout(new BorderLayout());
        billPanel.setBackground(Color.WHITE);
        billPanel.add(createPlaceholderLabel(), BorderLayout.CENTER);

        // Thêm các panel biểu đồ vào chartDisplayPanel
        chartDisplayPanel.add(sessionPanel, "SessionPanel");
        chartDisplayPanel.add(billPanel, "BillPanel");
        cardLayout.show(chartDisplayPanel, "BillPanel"); // Mặc định hiển thị panel hóa đơn

        // Panel thông tin chi tiết
        infoPanel = new CustomPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);

        // Nhãn tổng doanh thu
        infoLabel = new JLabel("Tổng Tiền Thống Kê Doanh Thu : 0 VND", SwingConstants.CENTER);
        infoLabel.setForeground(Color.RED);
        infoLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        infoPanel.add(infoLabel, BorderLayout.SOUTH);

        // Panel chứa nội dung thông tin chi tiết
        infoContentPanel = new JPanel();
        infoContentPanel.setLayout(new BoxLayout(infoContentPanel, BoxLayout.Y_AXIS));
        infoContentPanel.setBackground(Color.WHITE);

        // Thanh cuộn cho thông tin
        infoScrollPane = new CustomScrollPane(infoContentPanel);
        infoScrollPane.setBackground(Color.WHITE);
        infoPanel.add(infoScrollPane, BorderLayout.CENTER);

        // Thêm các panel vào panel chính
        panel.add(chartDisplayPanel);
        panel.add(infoPanel);
        return panel;
    }

    // Tạo nhãn placeholder khi chưa có dữ liệu
    private JLabel createPlaceholderLabel() {
        JLabel placeholder = new JLabel("Vui lòng bấm Áp dụng để xem dữ liệu", SwingConstants.CENTER);
        placeholder.setFont(new Font("Sans-serif", Font.PLAIN, 16));
        return placeholder;
    }

    // Định dạng số tiền thành chuỗi có đơn vị VND
    private String formatCurrency(double amount) {
        return currencyFormat.format(amount);
    }

    // Cập nhật biểu đồ và thông tin dựa trên bộ lọc
    private void updateChart() {
        String startDate = startDateField.getText().trim();
        String endDate = endDateField.getText().trim();

        // Kiểm tra dữ liệu đầu vào
        if (startDate.isEmpty() || endDate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ ngày!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chuyển đổi ngày tháng
        LocalDateTime start, end;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            start = LocalDateTime.parse(startDate + " 00:00:00", formatter);  
            end = LocalDateTime.parse(endDate + " 23:59:59", formatter);
            // nối để đúng định dạng trong csdl 
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
        double foodRevenueTotal = pendingInfo.getTotalRevenue(); // Tổng doanh thu món ăn , chỉ lấy món ăn đã xử lý
        double totalRevenue = computerRevenueTotal + foodRevenueTotal;
        infoLabel.setText("Tổng Tiền Thống Kê Doanh Thu : " + formatCurrency(totalRevenue));

        // Cập nhật panel thông tin
        updateInfoPanel(start, end, computerData);

        // Cập nhật biểu đồ
        if (currentPanel.equals("SessionPanel")) {
            String selectedRoom = roomComboBox.getSelectedItem() != null ? roomComboBox.getSelectedItem().toString() : "Tất cả";
            updatePanelWithChart(sessionPanel, createComputerRevenueChart(start, end, selectedRoom, computerData));
        } else {
            String selectedCategory = categoryComboBox.getSelectedItem().toString();
            updatePanelWithChart(billPanel, createFoodRevenueChart(start, end, selectedCategory));
        }
    }

    // Cập nhật panel biểu đồ với biểu đồ mới
    private void updatePanelWithChart(CustomPanel panel, CustomPieChart pieChart) {
        panel.removeAll();
        pieChart.setBounds(0, 0, 540, 400);
        panel.add(pieChart, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    // Tạo biểu đồ doanh thu món ăn
    private CustomPieChart createFoodRevenueChart(LocalDateTime start, LocalDateTime end, String category) {
        ArrayList<Object[]> data = foodRevenueBLL.getFoodRevenueByCategory(start, end, category);

        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu doanh thu món ăn trong khoảng thời gian này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return createEmptyPieChart("Không có dữ liệu");
        }

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Number> values = new ArrayList<>();
        for (Object[] row : data) {
            labels.add((String) row[1]); // Tên món ăn
            values.add((Double) row[4]); // Doanh thu
        }

        CustomPieChart pieChart = new CustomPieChart(labels.toArray(new String[0]), values.toArray(new Number[0]));
        pieChart.getChart().setTitle("Doanh thu món ăn");
        return pieChart;
    }

    // Tạo biểu đồ doanh thu máy tính
    private CustomPieChart createComputerRevenueChart(LocalDateTime start, LocalDateTime end, String selectedRoom, ArrayList<Object[]> data) {
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

        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu doanh thu máy trong khoảng thời gian này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return createEmptyPieChart("Không có dữ liệu");
        }

        ArrayList<String> validLabels = new ArrayList<>();
        ArrayList<Number> validValues = new ArrayList<>();
        HashSet<String> roomsInvolved = new HashSet<>();
        for (Object[] row : data) {
            try {
                String name = (String) row[0]; // Tên máy
                Double revenue = (Double) row[2]; // Doanh thu
                String roomName = (String) row[3]; // Tên phòng
                if (name != null && !name.trim().isEmpty() && revenue != null && revenue > 0) {
                    validLabels.add(name);
                    validValues.add(revenue);
                    if (roomName != null) {
                        roomsInvolved.add(roomName);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error processing computer revenue data: " + e.getMessage());
            }
        }

        if (validLabels.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu doanh thu máy hợp lệ trong khoảng thời gian này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return createEmptyPieChart("Không có dữ liệu");
        }

      
        CustomPieChart pieChart = new CustomPieChart(validLabels.toArray(new String[0]), validValues.toArray(new Number[0]));
        pieChart.getChart().setTitle("Doanh thu phòng máy");
        return pieChart;
    }

    // Tạo biểu đồ trống khi không có dữ liệu
    private CustomPieChart createEmptyPieChart(String title) {
        CustomPieChart pieChart = new CustomPieChart(new String[]{"Không có dữ liệu"}, new Number[]{1});
        pieChart.getChart().setTitle(title);
        return pieChart;
    }

    // Cập nhật panel thông tin chi tiết dựa trên loại panel hiện tại
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

    // Cập nhật thông tin chi tiết cho panel phiên chơi
    private void updateSessionInfoPanel(LocalDateTime start, LocalDateTime end, ArrayList<Object[]> computerData) {
        String selectedRoom = roomComboBox.getSelectedItem() != null ? roomComboBox.getSelectedItem().toString() : "Tất cả";
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

    // Tính tổng doanh thu của từng phòng máy
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

    // Hiển thị thông tin chi tiết của một phòng máy
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

    // Cập nhật thông tin chi tiết cho panel hóa đơn
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

    // Hiển thị dữ liệu doanh thu theo loại sản phẩm
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

    // Cập nhật thông tin trạng thái hóa đơn (đã xử lý, chưa xử lý, đã hủy)
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

    // Thêm nhãn văn bản vào panel thông tin
    private void addLabel(String text, int style, int size, Color color, int leftPadding) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Sans-serif", style, size));
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(0, leftPadding, 0, 0));
        infoContentPanel.add(label);
    }
}