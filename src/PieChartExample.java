

import GUI.Components.CustomPieChart;

import java.awt.Color;


import javax.swing.*;

public class PieChartExample {
    
    public static void main(String[] args) {
           SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pie Chart Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);

            String[] labels = {"Apple", "Samsung", "Xiaomi", "Other"};
            Number[] values = {30, 25, 20, 25};

            CustomPieChart pieChart = new CustomPieChart(labels, values);
            pieChart.setBounds(50, 50, 600, 400); // Đặt vị trí và kích thước
            pieChart.setBackgroundColor(Color.WHITE); // Đặt màu nền

            frame.add(pieChart);
            frame.setSize(720, 520);
            frame.setVisible(true);
        });
    }
}
