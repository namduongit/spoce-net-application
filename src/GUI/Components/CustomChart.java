package GUI.Components;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class CustomChart extends JPanel {

    private JFreeChart chart;
    private CategoryPlot plot;
    private BarRenderer renderer;

    public CustomChart(String[] categories, double[] values, String seriesName) {
        if (categories.length != values.length) {
            throw new IllegalArgumentException("Số lượng category và value không khớp.");
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < categories.length; i++) {
            dataset.addValue(values[i], seriesName, categories[i]);
        }

        chart = ChartFactory.createBarChart(
                "",       
                "",       
                "",    
                dataset,
                PlotOrientation.VERTICAL,
                false,   
                true,
                false
        );

        plot = chart.getCategoryPlot();
        renderer = (BarRenderer) plot.getRenderer();

        // Giao diện mặc định
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
    }

    public void setBackgroundColor(Color color) {
        plot.setBackgroundPaint(color);
    }


    public void setBarColor(Color color) {
        renderer.setSeriesPaint(0, color);
    }

    public void setTitle(String title) {
        chart.setTitle(title);
    }
}
