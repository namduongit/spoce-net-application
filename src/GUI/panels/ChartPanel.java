package GUI.panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import BLL.FoodRevenueBLL;
import BLL.ComputerSessionBLL;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomPanel;
import GUI.Components.CustomPieChart;
import GUI.Components.CustomTextField;

public class ChartPanel extends JPanel {
    private FoodRevenueBLL foodRevenueBLL;
    private ComputerSessionBLL sessionBLL;

    private CustomPanel titlePanel;
    private CustomPanel filterPanel;
    private CustomPanel chartPanel;
    private CustomCombobox<String> chartTypeComboBox;
    private CustomTextField startDateField;
    private CustomTextField endDateField;
    private CustomButton applyFilterButton;

    public ChartPanel() {
        this.foodRevenueBLL = new FoodRevenueBLL();
        this.sessionBLL = new ComputerSessionBLL();

        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(null);

        // Tạo các panel chính
        this.titlePanel = createTitlePanel();
        this.filterPanel = createFilterPanel();
        this.chartPanel = createChartPanel();

        // Đặt vị trí và kích thước
        this.titlePanel.setBounds(10, 0, 1080, 80);
        this.filterPanel.setBounds(10, 90, 1080, 80);
        this.chartPanel.setBounds(10, 180, 1080, 480);

        this.add(this.titlePanel);
        this.add(this.filterPanel);
        this.add(this.chartPanel);
    }

    // Tạo tiêu đề
    private CustomPanel createTitlePanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("THỐNG KÊ DOANH THU");
        titleLabel.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        titleLabel.setBounds(350, 20, 500, 50);

        panel.add(titleLabel);
        return panel;
    }

    // Tạo panel bộ lọc
    private CustomPanel createFilterPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        // Loại biểu đồ
        JLabel chartTypeLabel = new JLabel("Loại biểu đồ:");
        chartTypeLabel.setBounds(20, 20, 100, 35);
        panel.add(chartTypeLabel);

        ArrayList<String> chartTypes = new ArrayList<>();
        chartTypes.add("Doanh thu món ăn");
        chartTypes.add("Doanh thu máy");
        this.chartTypeComboBox = new CustomCombobox<>(chartTypes);
        this.chartTypeComboBox.setBounds(120, 20, 200, 35);
        panel.add(this.chartTypeComboBox);

        // Khoảng thời gian
        JLabel startDateLabel = new JLabel("Từ ngày:");
        startDateLabel.setBounds(350, 20, 80, 35);
        panel.add(startDateLabel);

        // Đặt ngày hôm nay làm giá trị mặc định
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.startDateField = new CustomTextField(today);
        this.startDateField.setBounds(420, 20, 150, 35);
        panel.add(this.startDateField);

        JLabel endDateLabel = new JLabel("Đến ngày:");
        endDateLabel.setBounds(600, 20, 80, 35);
        panel.add(endDateLabel);

        this.endDateField = new CustomTextField(today);
        this.endDateField.setBounds(670, 20, 150, 35);
        panel.add(this.endDateField);

        // Nút áp dụng
        this.applyFilterButton = new CustomButton("Áp dụng");
        this.applyFilterButton.setBounds(850, 20, 100, 35);
        this.applyFilterButton.addActionListener(e -> updateChart());
        panel.add(this.applyFilterButton);

        // Nút in hóa đơn
        CustomButton printBillButton = new CustomButton("In hóa đơn");
        printBillButton.setBounds(960, 20, 100, 35);
        printBillButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Chức năng in hóa đơn chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(printBillButton);

        return panel;
    }

    // Tạo panel chứa biểu đồ
    private CustomPanel createChartPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Biểu đồ mặc định
        CustomPieChart defaultChart = createDefaultChart();
        defaultChart.setBounds(0, 0, 1080, 480);
        panel.add(defaultChart, BorderLayout.CENTER);

        return panel;
    }

    // Tạo biểu đồ mặc định
    private CustomPieChart createDefaultChart() {
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime todayEnd = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        ArrayList<Object[]> data = foodRevenueBLL.getFoodRevenue(todayStart, todayEnd);
        System.out.println("DefaultChart FoodRevenue data size: " + data.size());

        // Nếu không có dữ liệu, trả về biểu đồ "Không có dữ liệu"
        if (data.isEmpty()) {
            String[] labels = {"Không có dữ liệu"};
            Number[] values = {1};
            return new CustomPieChart(labels, values);
        }

        // Chuyển dữ liệu thành labels và values, bỏ qua các bản ghi không hợp lệ
        ArrayList<String> validLabels = new ArrayList<>();
        ArrayList<Number> validValues = new ArrayList<>();
        for (Object[] row : data) {
            try {
                String name = (String) row[0];
                Double revenue = (Double) row[1];
                if (name != null && !name.trim().isEmpty() && revenue != null && revenue > 0) {
                    validLabels.add(name);
                    validValues.add(revenue);
                    System.out.println("DefaultChart Food: " + name + ", Revenue: " + revenue);
                } else {
                    System.out.println("Skipped invalid data: Name=" + name + ", Revenue=" + revenue);
                }
            } catch (Exception e) {
                System.err.println("Error processing default chart data: " + e.getMessage());
            }
        }

        // Nếu không có dữ liệu hợp lệ, trả về biểu đồ "Không có dữ liệu"
        if (validLabels.isEmpty()) {
            String[] labels = {"Không có dữ liệu"};
            Number[] values = {1};
            return new CustomPieChart(labels, values);
        }

        return new CustomPieChart(validLabels.toArray(new String[0]), validValues.toArray(new Number[0]));
    }

    // Cập nhật biểu đồ dựa trên bộ lọc
    private void updateChart() {
        String chartType = chartTypeComboBox.getSelectedItem().toString();
        String startDate = startDateField.getText().trim();
        String endDate = endDateField.getText().trim();

        // Kiểm tra ngày nhập vào
        if (startDate.isEmpty() || endDate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ ngày!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Xác thực định dạng ngày
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = null, end = null;
        try {
            start = LocalDateTime.parse(startDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            end = LocalDateTime.parse(endDate + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Định dạng ngày không hợp lệ (YYYY-MM-DD)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Gỡ lỗi
        System.out.println("ChartType: " + chartType + ", Start: " + start + ", End: " + end);

        // Xóa nội dung cũ
        chartPanel.removeAll();
        chartPanel.revalidate();
        chartPanel.repaint();

        CustomPieChart pieChart = null;
        if (chartType.equals("Doanh thu món ăn")) {
            pieChart = createFoodRevenueChart(start, end);
        } else if (chartType.equals("Doanh thu máy")) {
            pieChart = createComputerRevenueChart(start, end);
        }

        if (pieChart == null) {
            pieChart = createDefaultChart();
        }

        pieChart.setBounds(0, 0, 1080, 480);
        chartPanel.add(pieChart, BorderLayout.CENTER);

        chartPanel.revalidate();
        chartPanel.repaint();
    }

    // Biểu đồ doanh thu món ăn
    private CustomPieChart createFoodRevenueChart(LocalDateTime start, LocalDateTime end) {
        ArrayList<Object[]> data = foodRevenueBLL.getFoodRevenue(start, end);
        System.out.println("FoodRevenue data size: " + data.size());

        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu doanh thu món ăn trong khoảng thời gian này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return createDefaultChart();
        }

        ArrayList<String> validLabels = new ArrayList<>();
        ArrayList<Number> validValues = new ArrayList<>();
        for (Object[] row : data) {
            try {
                String name = (String) row[0];
                Double revenue = (Double) row[1];
                if (name != null && !name.trim().isEmpty() && revenue != null && revenue > 0) {
                    validLabels.add(name);
                    validValues.add(revenue);
                    System.out.println("Food: " + name + ", Revenue: " + revenue);
                } else {
                    System.out.println("Skipped invalid data: Name=" + name + ", Revenue=" + revenue);
                }
            } catch (Exception e) {
                System.err.println("Error processing food revenue data: " + e.getMessage());
            }
        }

        if (validLabels.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu doanh thu món ăn hợp lệ trong khoảng thời gian này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return createDefaultChart();
        }

        return new CustomPieChart(validLabels.toArray(new String[0]), validValues.toArray(new Number[0]));
    }

    // Biểu đồ doanh thu máy
    private CustomPieChart createComputerRevenueChart(LocalDateTime start, LocalDateTime end) {
        ArrayList<Object[]> data = sessionBLL.getComputerRevenue(start, end);
        System.out.println("ComputerRevenue data size: " + data.size());

        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu doanh thu máy trong khoảng thời gian này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return createDefaultChart();
        }

        ArrayList<String> validLabels = new ArrayList<>();
        ArrayList<Number> validValues = new ArrayList<>();
        for (Object[] row : data) {
            try {
                String name = (String) row[0];
                Double revenue = (Double) row[1];
                if (name != null && !name.trim().isEmpty() && revenue != null && revenue > 0) {
                    validLabels.add(name);
                    validValues.add(revenue);
                    System.out.println("Computer: " + name + ", Revenue: " + revenue);
                } else {
                    System.out.println("Skipped invalid data: Name=" + name + ", Revenue=" + revenue);
                }
            } catch (Exception e) {
                System.err.println("Error processing computer revenue data: " + e.getMessage());
            }
        }

        if (validLabels.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu doanh thu máy hợp lệ trong khoảng thời gian này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return createDefaultChart();
        }

        return new CustomPieChart(validLabels.toArray(new String[0]), validValues.toArray(new Number[0]));
    }
}