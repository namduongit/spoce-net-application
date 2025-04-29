package GUI.panels;

import BLL.CategoryBLL;
import BLL.FoodBLL;
import DTO.Accounts;
import DTO.Categories;
import DTO.Foods;
import DTO.Staffs;
import GUI.Card.FoodCard;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomPanel;
import GUI.Components.CustomScrollPane;
import GUI.Components.CustomTextField;
import GUI.Form.AddingFood;
import GUI.Form.AddingOrder;
import GUI.Form.DetailsFood;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

public class FoodPanel extends JPanel {
    @SuppressWarnings("unused")
    private Accounts loginAccount;
    private Staffs loginStaff;

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

    public FoodPanel(Accounts loginAccount, Staffs loginStaff) {
        this.loginAccount = loginAccount;
        this.loginStaff = loginStaff;

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
        this.searchField.addActionListener(e -> this.filterFoodList());
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
                if (this.text.equalsIgnoreCase("")) {
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
        this.statusComboBoxProduct.addActionListener(e -> filterFoodList());
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
        this.statusComboBoxCategory.addActionListener(e -> filterFoodList());
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
                        nameCurrentFood
                                .setText("Đang chọn: " + foodCard.getIdProduct() + ", " + foodCard.getNameProduct());
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

    private void filterFoodList() {
        String typeStatus = this.statusComboBoxProduct.getSelectedItem().toString();
        String typeCategory = this.statusComboBoxCategory.getSelectedItem().toString();
        String searchText = this.searchField.getText().trim().equals("Nhập thông tin sản phẩm") ? "" : this.searchField.getText().trim();

        if (typeStatus.equalsIgnoreCase("Tất cả") && typeCategory.equalsIgnoreCase("Tất cả") && searchText.isEmpty()) {
            this.foodList = this.foodBLL.getAllFoods();
        } else {
            this.updateFoodByCategoryAndType(typeStatus, typeCategory, searchText, "name");
        }

        this.reloadFoodPanel();
    }

    private void updateFoodByCategoryAndType(String status, String categoryType, String searchText, String sortBy) {
        List<Foods> filteredList = this.foodBLL.getFoodByCategory(categoryType)
                .stream()
                .filter(fd -> status.equalsIgnoreCase("Tất cả") || fd.getStatus().equalsIgnoreCase(status))
                .filter(fd -> searchText.isEmpty() || fd.getName().toLowerCase().contains(searchText.toLowerCase()))
                .sorted(Comparator.comparing(fd -> {
                    if (sortBy.equals("name")) {
                        return fd.getName().toLowerCase();
                    }
                    return "";
                }))
                .collect(Collectors.toList());

        this.foodList = new ArrayList<>(filteredList);
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
                        nameCurrentFood
                                .setText("Đang chọn: " + foodCard.getIdProduct() + ", " + foodCard.getNameProduct());
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

        CustomButton addButton = Utils.Helper.CreateComponent.createGreenButton("Thêm");
        addButton.setBounds(50, 10, 100, 30);
        addButton.addActionListener(e -> {
            AddingFood addingFoodForm = new AddingFood();
            addingFoodForm.setVisible(true);
        });
        
        CustomButton editButton = Utils.Helper.CreateComponent.createBlueButton("Chỉnh sửa");
        editButton.setBounds(170, 10, 100, 30);
        editButton.addActionListener(e -> {
            if (this.currentFoods == null) {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn sản phẩm", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                new DetailsFood(this.foodBLL.getFoodByID(this.currentFoods.getFoodId())).setVisible(true);
                ;
            }
            this.reloadFoodPanel();
        });

        CustomButton deleteButton = Utils.Helper.CreateComponent.createOrangeButton("Xóa");
        deleteButton.setBounds(290, 10, 100, 30);
        deleteButton.addActionListener(e -> {
            if (this.currentFoods != null) {
                int option = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc muốn xóa sản phẩm\nCó tên: " + this.currentFoods.getName(),
                        "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                    if (this.foodBLL.deleteFoodById(this.currentFoods.getFoodId())) {
                        JOptionPane.showMessageDialog(null, "Xóa thành công", "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);    
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Hủy xóa sản phẩm", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn sản phẩm", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
            this.reloadFoodPanel();
        });

        CustomButton addNewCategory = Utils.Helper.CreateComponent.createGrayButton("Thêm loại");
        addNewCategory.setBounds(410, 10, 100, 30);
        addNewCategory.addActionListener(e -> {
            String newCate = JOptionPane.showInputDialog("Nhập tên loại bạn muốn thêm:");
            
            if (newCate != null && !newCate.trim().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Bạn có chắc muốn thêm loại \"" + newCate + "\" không?",
                    "Xác nhận thêm loại",
                    JOptionPane.YES_NO_OPTION
                );

                if (newCate.isEmpty() || newCate == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin loại sản phẩm mới !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if (newCate.length() < 4) {
                    JOptionPane.showMessageDialog(this, "Tên của loại sản phẩm mới phải có ít nhất 4 kí tự !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                if (confirm == JOptionPane.YES_OPTION) {
                    System.out.println("Thêm loại: " + newCate);
                    boolean resultAdd = this.categoryBLL.createNewCategory(newCate);
                    if (resultAdd) {
                        JOptionPane.showMessageDialog(this, "Thêm loại mới thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm loại mới thất bại", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    }

                }
            }
        });
        
        CustomButton createBillOrder = Utils.Helper.CreateComponent.createBrownButton("Tạo hóa đơn");
        createBillOrder.setBounds(530, 10, 150, 30);
        createBillOrder.addActionListener(e -> {
            new AddingOrder(this.loginStaff).setVisible(true);
        });

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(addNewCategory);
        panel.add(createBillOrder);

        return panel;
    }
}
