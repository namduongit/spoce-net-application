package GUI.Components;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class CustomPieChart extends JPanel {
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultPieDataset dataset;

    public CustomPieChart(String[] labels, Number[] values) {
        if (labels.length != values.length) {
            throw new IllegalArgumentException("Số lượng label và value không khớp.");
        }

        dataset = new DefaultPieDataset();
        for (int i = 0; i < labels.length; i++) {
            if (labels[i] != null && !labels[i].trim().isEmpty() && values[i] != null && values[i].doubleValue() > 0) {
                dataset.setValue(labels[i], values[i]);
            } else {
                System.out.println("Skipped invalid pie chart data: Label=" + labels[i] + ", Value=" + values[i]);
            }
        }

        // Nếu dataset rỗng, thêm dữ liệu mặc định
        if (dataset.getItemCount() == 0) {
            dataset.setValue("Không có dữ liệu", 1);
        }

        chart = ChartFactory.createPieChart(
                "",         // Không đặt tiêu đề
                dataset,
                true,       // Hiển thị legend
                true,
                false
        );

        // Cấu hình hiển thị nhãn giá trị trên biểu đồ tròn
        org.jfree.chart.plot.PiePlot plot = (org.jfree.chart.plot.PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {1} VND ({2})", // Hiển thị tên, giá trị, và phần trăm
                new DecimalFormat("#,##0"), // Định dạng giá trị số
                new DecimalFormat("0.0%") // Định dạng phần trăm
        ));
        plot.setLabelBackgroundPaint(Color.WHITE); // Màu nền nhãn
        plot.setLabelOutlinePaint(Color.BLACK); // Viền nhãn
        plot.setLabelShadowPaint(null); // Tắt bóng
        plot.setSimpleLabels(true); // Nhãn đơn giản để tránh lộn xộn

        chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setLayout(null);
        setLayout(null);

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
        chartPanel.setBounds(0, 0, width, height);
    }

    public JFreeChart getChart() {
        return chart;
    }
}