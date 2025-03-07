package GUI.panels;

import javax.swing.*;
import java.awt.*;
import GUI.Card.FoodCard;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import Utils.Helper.CreateComponent;
import GUI.Components.CustomPanel;
import GUI.Components.CustomScrollPane;
import GUI.Components.CustomTextField;
import GUI.Components.CustomButton;

import java.util.ArrayList;
import java.util.List;

public class FoodPanel extends JPanel {
    private JPanel foodPanel;
    private List<FoodCard> foodCards;

    private CardLayout cardLayout;
    private JPanel buttonControllPanel;
    private CustomPanel filterPanel;
    private CustomPanel showInfoPanel;

    public FoodPanel() {
        this.foodCards = new ArrayList<>(); // Khởi tạo danh sách để tránh NullPointerException
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        buttonControllPanel = createControlButtonPanel();
        filterPanel = createFilterPanel();
        foodPanel = createFoodPanel();

        buttonControllPanel.setBounds(10, 0, 1080, 100);

        filterPanel.setBounds(10, 110, 1080, 80);

        foodPanel.setBounds(10, 200, 1080, 600);

        this.add(buttonControllPanel);
        this.add(filterPanel);
        this.add(foodPanel);
    }

    private JPanel createControlButtonPanel() {
        JPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel titlePanel = new JLabel("QUẢN LÝ ĐỒ ĂN");
        titlePanel.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        titlePanel.setBounds(420, 30, 400, 50);

        panel.add(titlePanel);

        return panel;
    }

    private CustomPanel createFilterPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(20, 20, 80, 35);
        panel.add(searchLabel);

        CustomTextField searchField = new CustomTextField();
        searchField.setBounds(100, 20, 200, 35);
        panel.add(searchField);

        JLabel statusLabel = new JLabel("Trạng thái:");
        statusLabel.setBounds(320, 20, 80, 35);
        panel.add(statusLabel);

        String[] statuses = {"Tất cả", "Còn hàng", "Hết hàng"};
        CustomCombobox<String> statusComboBox = new CustomCombobox<>(statuses);
        statusComboBox.setBounds(400, 20, 150, 35);
        panel.add(statusComboBox);

        CustomButton filterButton = new CustomButton("Lọc");
        filterButton.setBounds(600, 20, 100, 35);
        filterButton.setBackground(Color.decode("#03A9F4"));
        filterButton.setForeground(Color.WHITE);
        filterButton.setBorderColor(Color.decode("#03A9F4"));
        panel.add(filterButton);

        CustomButton resetButton = new CustomButton("Đặt lại");
        resetButton.setBounds(720, 20, 100, 35);
        resetButton.setBackground(Color.RED);
        resetButton.setForeground(Color.WHITE);
        resetButton.setBorderColor(Color.RED);
        panel.add(resetButton);

        CustomButton addNewButton = new CustomButton("Thêm");
        addNewButton.setBounds(840, 20, 100, 35);
        addNewButton.setBackground(Color.decode("#388E3C"));
        addNewButton.setForeground(Color.WHITE);
        addNewButton.setBorderColor(Color.decode("#388E3C"));
        panel.add(addNewButton);

        return panel;
    }

    private JPanel createFoodPanel() {
        JPanel panel = new CustomPanel();
        panel.setLayout(new BorderLayout());

        JPanel tableDataPanel = new CustomPanel();
        tableDataPanel.setBackground(Color.WHITE);

        int columns = 5;
        tableDataPanel.setLayout(new GridLayout(0, columns, 10, 10));

        for (int i = 0; i < 20; i++) {
            FoodCard foodCard = new FoodCard(
                    new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Food/none.png"),
                    "Thức ăn: " + i,
                    "200000",
                    new CustomButton("Sửa"),
                    new CustomButton("Xóa"));
            foodCards.add(foodCard);
            tableDataPanel.add(foodCard);
        }
        JPanel lastRow = new JPanel();
        lastRow.setBackground(Color.WHITE);
        tableDataPanel.add(lastRow);

        JScrollPane scrollPane = new CustomScrollPane(tableDataPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }


}