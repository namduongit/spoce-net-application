package GUI.Components;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class CustomPieChart extends JPanel {
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultPieDataset<String> dataset;

    public CustomPieChart(String[] labels, Number[] values) {
        if (labels.length != values.length) {
            throw new IllegalArgumentException("Số lượng label và value không khớp.");
        }

        dataset = new DefaultPieDataset<>();
        for (int i = 0; i < labels.length; i++) {
            dataset.setValue(labels[i], values[i]);
        }

        chart = ChartFactory.createPieChart(
                "",         // Không đặt tiêu đề
                dataset,
                true,       // Hiển thị legend
                true,
                false
        );

        chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setLayout(null);
        setLayout(null); // Để dùng setBounds nếu muốn thêm nhiều thành phần khác sau này

        add(chartPanel);
    }

    public void setChartBounds(int x, int y, int width, int height) {
        chartPanel.setBounds(x, y, width, height);
    }

    public void setBackgroundColor(Color color) {
        chartPanel.setBackground(color);
        chart.setBackgroundPaint(color);
        chart.getPlot().setBackgroundPaint(color);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        chartPanel.setBounds(0, 0, width, height); // Auto resize theo panel chứa nó
    }
}
