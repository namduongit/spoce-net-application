package GUI.panels;

import javax.swing.*;

import BLL.CategoryBLL;
import BLL.FoodBLL;
import DTO.Categories;
import DTO.Foods;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import GUI.Card.FoodCard;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomPanel;
import GUI.Components.CustomScrollPane;
import GUI.Components.CustomTextField;

import java.util.ArrayList;

public class FoodPanel extends JPanel {
    private FoodBLL foodBLL;
    private CategoryBLL categoryBLL;
    private ArrayList<Foods> foodList;
    private ArrayList<Categories> categoryList;

    private Foods currentFoods;

    private JPanel foodPanel;
    private JPanel titlePanel;
    private CustomPanel filterPanel;
    private CustomPanel buttonFilterPanel;
    private CustomTextField searchField;
    private CustomCombobox<String> statusComboBoxProduct;
    private CustomCombobox<String> statusComboBoxCategory;
    private JLabel nameCurrentFood;

    public FoodPanel() {
        this.foodBLL = new FoodBLL();
        this.categoryBLL = new CategoryBLL();
        this.foodList = this.foodBLL.getAllFoods();
        this.categoryList = this.categoryBLL.getAllCategories();

        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(null);

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

    // ------------------------------------------------------- PHẦN TIÊU ĐỀ ----------------------------------------------------------
    private JPanel createControlButtonPanel() {
        JPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel titlePanel = new JLabel("QUẢN LÝ ĐỒ ĂN");
        titlePanel.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        titlePanel.setBounds(420, 20, 400, 50);

        panel.add(titlePanel);
        return panel;
    }

    // ------------------------------------------------------- PHẦN BỘ LỌC ----------------------------------------------------------
    private CustomPanel createFilterPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(20, 20, 80, 35);
        panel.add(searchLabel);

        this.searchField = new CustomTextField("Nhập thông tin sản phẩm");
        this.searchField.setBounds(80, 20, 200, 35);
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

        ArrayList<String> statusesProduct = new ArrayList<>();
        statusesProduct.add("Tất cả");
        statusesProduct.add("Còn hàng");
        statusesProduct.add("Hết hàng");
        this.statusComboBoxProduct = new CustomCombobox<>(statusesProduct);
        this.statusComboBoxProduct.setBounds(385, 20, 150, 35);
        this.statusComboBoxProduct.addActionListener(e -> {
            updateListFood(null, (String) this.statusComboBoxProduct.getSelectedItem(), null, 2);
        });
        panel.add(this.statusComboBoxProduct);

        JLabel statusCategoryLabel = new JLabel("Loại sản phẩm:");
        statusCategoryLabel.setBounds(570, 20, 100, 35);
        panel.add(statusCategoryLabel);

        ArrayList<String> statusesCategory = new ArrayList<>();
        statusesCategory.add("Tất cả");
        for (Categories cate : this.categoryList) {
            statusesCategory.add(cate.getName());
        }
        this.statusComboBoxCategory = new CustomCombobox<>(statusesCategory);
        this.statusComboBoxCategory.setBounds(660, 20, 150, 35);
        this.statusComboBoxCategory.addActionListener(e -> {
            updateListFood((String) this.statusComboBoxCategory.getSelectedItem(), null, null, 1);
        });
        panel.add(this.statusComboBoxCategory);

        this.nameCurrentFood = new JLabel("Đang chọn: NULL");
        this.nameCurrentFood.setBounds(850, 20, 200, 35);
        panel.add(nameCurrentFood);

        return panel;
    }

    // ------------------------------------------------------- PHẦN NÚT BẢNG HIỂN THỊ THỨC ĂN ----------------------------------------------------------
    private CustomPanel createFoodPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(1080, 480));

        // Panel chứa danh sách sản phẩm
        JPanel panelProduct = new JPanel();
        panelProduct.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelProduct.setBackground(Color.WHITE);

        for (Foods fd : this.foodList) {
            FoodCard foodCard = new FoodCard(
                    fd.getImage(),
                    fd.getFoodId(),
                    fd.getName(),
                    fd.getPrice(),
                    fd.getCategoryId(),
                    fd.getStatus());

            foodCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    currentFoods = foodBLL.getFoodByID(foodCard.getIdProduct());
                    if (currentFoods != null) {
                        nameCurrentFood.setText("Đang chọn: "+ foodCard.getIdProduct() +", "+ foodCard.getNameProduct());
                    } else {
                        nameCurrentFood.setText("Đang chọn: NULL");
                    }
                }
            });

            panelProduct.add(foodCard);
        }

        panelProduct.setPreferredSize(new Dimension(1060, (int) Math.ceil(this.foodList.size() / 4.0) * 250));

        JScrollPane scrollPane = new CustomScrollPane(panelProduct);
        scrollPane.setBounds(0, 0, 1080, 480);
        scrollPane.setBorder(null);
        panel.add(scrollPane);

        return panel;
    }
    private void updateListFood(String categoryType, String status, String search, int type) {
        switch (type) {
            case 1:
                this.foodList = categoryType.equalsIgnoreCase("Tất cả")
                                ? this.foodBLL.getAllFoods()
                                : this.foodBLL.getFoodByCategory(categoryType);
                break;

            case 2:
                this.foodList = status.equalsIgnoreCase("Tất cả")
                                ? this.foodBLL.getAllFoods()
                                :  this.foodBLL.getFoodByStatus(status);
                break;

            case 3:
                this.foodList = this.foodBLL.searchFoodByName(search);
                break;

            case 4:
                this.foodList = this.foodBLL.advancedSearch(categoryType, status, search);
                break;

            default:
                System.out.println("Loại tìm kiếm không hợp lệ!");
                return;
        }
        this.reloadFoodPanel();
    }

    private void reloadFoodPanel() {
        this.foodPanel.removeAll();
        this.foodPanel.revalidate();
        this.foodPanel.repaint();

        JPanel panelProduct = new JPanel();
        panelProduct.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelProduct.setBackground(Color.WHITE);

        for (Foods fd : this.foodList) {
            FoodCard foodCard = new FoodCard(
                    fd.getImage(),
                    fd.getFoodId(),
                    fd.getName(),
                    fd.getPrice(),
                    fd.getCategoryId(),
                    fd.getStatus());

            foodCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    currentFoods = foodBLL.getFoodByID(foodCard.getIdProduct());
                    if (currentFoods != null) {
                        nameCurrentFood.setText("Đang chọn: " + foodCard.getIdProduct() + ", " + foodCard.getNameProduct());
                    } else {
                        nameCurrentFood.setText("Đang chọn: NULL");
                    }
                }
            });

            panelProduct.add(foodCard);
        }

        panelProduct.setPreferredSize(new Dimension(1060, (int) Math.ceil(this.foodList.size() / 4.0) * 250));

        JScrollPane scrollPane = new CustomScrollPane(panelProduct);
        scrollPane.setBounds(0, 0, 1080, 480);
        scrollPane.setBorder(null);
        this.foodPanel.add(scrollPane);

        this.foodPanel.revalidate();
        this.foodPanel.repaint();
    }

    // ------------------------------------------------------- PHẦN NÚT HÀNH ĐỘNG ----------------------------------------------------------
    private CustomPanel createFilterButtonPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        CustomButton addButton = new CustomButton("Thêm");
        addButton.setBounds(200, 10, 100, 30);
        panel.add(addButton);

        CustomButton editButton = new CustomButton("Sửa");
        editButton.setBounds(320, 10, 100, 30);
        editButton.addActionListener(e -> {
            if (this.currentFoods == null) {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn sản phẩm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
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
