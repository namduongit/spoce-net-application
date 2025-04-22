
import GUI.Components.CustomChart;

import javax.swing.*;
import java.awt.*;

public class ChartExample {

    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Demo CustomChart");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            String[] categories = {"Tháng 1", "Tháng 2", "Tháng 3"};
            double[] values = {5.0, 7.0, 3.0};

            CustomChart chart = new CustomChart(categories, values, "Doanh thu");
            chart.setBackgroundColor(Color.WHITE);
            chart.setBarColor(new Color(0, 153, 255)); 
            chart.setTitle("Biểu đồ doanh thu theo tháng");

            frame.add(chart);
            frame.setVisible(true);
        });
    }
}
