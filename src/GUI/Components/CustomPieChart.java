package GUI.Components;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;

public class CustomPieChart extends JPanel {
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultPieDataset dataset;
    private static final int MAX_DISPLAY_ITEMS = 10; // Giới hạn 10 mục hiển thị

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public CustomPieChart(String[] labels, Number[] values) {
        if (labels.length != values.length) {
            throw new IllegalArgumentException("Số lượng label và value không khớp.");
        }

        // Tạo danh sách các mục để sắp xếp
        ArrayList<Map.Entry<String, Number>> dataList = new ArrayList<>();
        for (int i = 0; i < labels.length; i++) {
            if (labels[i] != null && !labels[i].trim().isEmpty() && values[i] != null && values[i].doubleValue() > 0) {
                dataList.add(new AbstractMap.SimpleEntry<>(labels[i], values[i]));
            } else {
                System.out.println("Skipped invalid pie chart data: Label=" + labels[i] + ", Value=" + values[i]);
            }
        }

        // Sắp xếp theo giá trị giảm dần
        dataList.sort((a, b) -> Double.compare(b.getValue().doubleValue(), a.getValue().doubleValue()));

        // Tạo dataset, giới hạn số lượng hiển thị
        dataset = new DefaultPieDataset();
        double otherValue = 0;
        for (int i = 0; i < dataList.size(); i++) {
            if (i < MAX_DISPLAY_ITEMS) {
                dataset.setValue(dataList.get(i).getKey(), dataList.get(i).getValue());
            } else {
                otherValue += dataList.get(i).getValue().doubleValue();
            }
        }

        if (otherValue > 0) {
            dataset.setValue("Khác", otherValue);
        }

        if (dataset.getItemCount() == 0) {
            dataset.setValue("Không có dữ liệu", 1);
        }

        chart = ChartFactory.createPieChart(
                "",         // Tiêu đề sẽ được set sau
                dataset,
                true,       // Hiển thị legend
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {1} VND ({2})",
                new DecimalFormat("#,##0"),
                new DecimalFormat("0.0%")
        ));
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelOutlinePaint(Color.BLACK);
        plot.setLabelShadowPaint(null);
        plot.setSimpleLabels(true);
        plot.setLabelFont(new Font("Sans-serif", Font.PLAIN, 12)); // Tăng kích thước chữ nhãn
        chart.getTitle().setFont(new Font("Sans-serif", Font.BOLD, 16)); // Tăng kích thước chữ tiêu đề

        // Tạo màu sắc ngẫu nhiên cho từng phần
        Random rand = new Random();
        for (int i = 0; i < dataset.getItemCount(); i++) {
            plot.setSectionPaint(dataset.getKey(i), new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        }

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