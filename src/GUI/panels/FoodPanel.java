package GUI.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import GUI.Card.FoodCard;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomPanel;
import GUI.Components.CustomScrollPane;
import GUI.Components.CustomTextField;

import java.util.ArrayList;
import java.util.List;

public class FoodPanel extends JPanel {
    private JPanel foodPanel;
    private List<FoodCard> foodCards;
    private JPanel titlePanel;
    private CustomPanel filterPanel;
    private CustomPanel buttonFilterPanel;
    private CustomTextField searchField;
    private CustomCombobox<String> statusComboBoxProduct;
    private CustomCombobox<String> statusComboBoxCategory;

    public FoodPanel() {
        this.foodCards = new ArrayList<>();
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(null);
        // this.setBackground(Color.WHITE);

        this.titlePanel = this.createControlButtonPanel();
        this.filterPanel = this.createFilterPanel();
        this.foodPanel = this.createFoodPanel();
        this.buttonFilterPanel = this.createFilterButtonPanel();

        this.titlePanel.setBounds(10, 0, 1080, 80);
        this.filterPanel.setBounds(10, 90, 1080, 80);
        this.foodPanel.setBounds(10, 180, 1080, 480);
        this.buttonFilterPanel.setBounds(10, 670, 1080, 50);

        this.add(this.titlePanel);
        this.add(this.filterPanel);
        this.add(this.foodPanel);
        this.add(this.buttonFilterPanel);
    }

    private JPanel createControlButtonPanel() {
        JPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel titlePanel = new JLabel("QUẢN LÝ ĐỒ ĂN");
        titlePanel.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        titlePanel.setBounds(420, 20, 400, 50);

        panel.add(titlePanel);
        return panel;
    }

    private CustomPanel createFilterPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(20, 20, 80, 35);
        panel.add(searchLabel);

        this.searchField = new CustomTextField("Nhập thông tin sản phẩm");
        this.searchField.setBounds(100, 20, 200, 35);
        this.searchField.addFocusListener(new FocusListener() {
            public String text = searchField.getText();
            @Override
            public void focusGained(FocusEvent e) {
                if (this.text.equalsIgnoreCase("Nhập thông tin sản phẩm")) {
                    searchField.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (!this.text.equalsIgnoreCase("")) {
                    searchField.setText("Nhập thông tin sản phẩm");
                }
            }
        });
        panel.add(this.searchField);

        JLabel statusProductLabel = new JLabel("Trạng thái:");
        statusProductLabel.setBounds(320, 20, 80, 35);
        panel.add(statusProductLabel);

        String[] statusesProduct = {"Tất cả", "Còn hàng", "Hết hàng"};
        this.statusComboBoxProduct = new CustomCombobox<>(statusesProduct);
        this.statusComboBoxProduct.setBounds(400, 20, 150, 35);
        panel.add(this.statusComboBoxProduct);

        JLabel statusCategoryLabel = new JLabel("Loại sản phẩm:");
        statusCategoryLabel.setBounds(570, 20, 100, 35);
        panel.add(statusCategoryLabel);

        String[] statusesCategory = {"Tất cả"};
        this.statusComboBoxCategory = new CustomCombobox<>(statusesCategory);
        this.statusComboBoxCategory.setBounds(670, 20, 150, 35);
        panel.add(this.statusComboBoxCategory);

        return panel;
    }

    private JPanel createFoodPanel() {
        JPanel panel = new CustomPanel();
        panel.setLayout(new BorderLayout());

        JPanel tableDataPanel = new CustomPanel();
        tableDataPanel.setBackground(Color.WHITE);

        int columns = 6;
        tableDataPanel.setLayout(new GridLayout(0, columns, 10, 10));

        for (int i = 0; i < 12; i++) {

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

    private CustomPanel createFilterButtonPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        CustomButton addButton = new CustomButton("Thêm");
        addButton.setBounds(200, 10, 100, 30);
        panel.add(addButton);

        CustomButton editButton = new CustomButton("Sửa");
        editButton.setBounds(320, 10, 100, 30);
        panel.add(editButton);

        CustomButton deleteButton = new CustomButton("Xóa");
        deleteButton.setBounds(440, 10, 100, 30);
        panel.add(deleteButton);

        CustomButton detailButton = new CustomButton("Xem chi tiết");
        detailButton.setBounds(560, 10, 120, 30);
        panel.add(detailButton);

        return panel;
    }
}
