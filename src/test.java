import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class test extends JFrame {
    public test() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("Pizza", 100000);
        dataset.setValue("Soda", 50000);
        dataset.setValue("Fries", 75000);

        JFreeChart chart = ChartFactory.createPieChart(
                "Doanh thu món ăn",
                dataset,
                true, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        add(chartPanel);

        setTitle("Biểu đồ tròn");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new test().setVisible(true));
    }
}