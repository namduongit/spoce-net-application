package GUI.Form;

import BLL.*;
import DTO.Categories;
import DTO.Foods;
import DTO.Staffs;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomScrollPane;
import GUI.Components.CustomTextField;
import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddingInbound extends JFrame {
    private Staffs staffs;
    private AccountBLL accountBLL;
    private FoodBLL foodBLL;
    private CategoryBLL categoryBLL;
    private PurchaseReceiptBLL purchaseReceiptBLL;
    private PurchaseReceiptDetailBLL purchaseReceiptDetailBLL;

    // GUI
    private ArrayList<CustomTextField> listDataFood = new ArrayList<>();
    private ArrayList<CustomTextField> listDataQuantity = new ArrayList<>();
    private ArrayList<CustomTextField> listDataPrice = new ArrayList<>();
    private ArrayList<JButton> listDeleteButton = new ArrayList<>();

    private ArrayList<String> addedFood;

    private JPanel entryPanel;
    private CustomScrollPane scrollPane;
    private JLabel totalLabel;
    private CustomTextField staffTextField;
    private JTextArea noteTextArea;


    // Layer data
    private ArrayList<Foods> listFood;
    private ArrayList<Categories> listCategory;

    private CustomCombobox<String> comboboxFood;
    private CustomCombobox<String> comboboxCategory;

    public AddingInbound(Staffs staffs) {
        this.staffs = staffs;

        this.accountBLL = new AccountBLL();
        this.foodBLL = new FoodBLL();
        this.categoryBLL = new CategoryBLL();
        this.purchaseReceiptBLL = new PurchaseReceiptBLL();
        this.purchaseReceiptDetailBLL = new PurchaseReceiptDetailBLL();

        this.listFood = this.foodBLL.getAllFoods();
        this.listCategory = this.categoryBLL.getAllCategories();

        this.addedFood = new ArrayList<>();

        ArrayList<String> arrayListFood = new ArrayList<>();
        for (Foods foods : this.listFood) arrayListFood.add(foods.getFoodId() +" - "+ foods.getName());

        ArrayList<String> arrayListCategory = new ArrayList<>();
        arrayListCategory.add("Tất cả");
        for (Categories categories : this.listCategory)
            arrayListCategory.add(categories.getName());

        this.comboboxFood = new CustomCombobox<>(arrayListFood);
        this.comboboxCategory = new CustomCombobox<>(arrayListCategory);

        this.initComponent();
    }
    private void reloadCustomComboboxCategory() {
        String selectedCategory = comboboxCategory.getSelectedItem().toString();
        reloadCustomComboboxFood();
    }

    private void reloadCustomComboboxFood() {
        String selectedCategory = comboboxCategory.getSelectedItem().toString();

        ArrayList<Foods> list;
        if (selectedCategory.equals("Tất cả")) {
            list = this.listFood;
        } else {
            list = this.foodBLL.getFoodByCategory(selectedCategory);
        }

        ArrayList<String> filteredFoods = new ArrayList<>();
        for (Foods food : list) {
            if (!addedFood.contains(food.getFoodId() +" - "+ food.getName())) {
                filteredFoods.add(food.getFoodId() +" - "+ food.getName());
            }
        }

        comboboxFood.setModel(new DefaultComboBoxModel<>(filteredFoods.toArray(new String[0])));
    }

    private void addItemRow(ActionEvent e) {
        String selectedFood = comboboxFood.getSelectedItem() != null ? comboboxFood.getSelectedItem().toString() : null;
        if (selectedFood == null) {
            JOptionPane.showMessageDialog(this, "Hết món để tạo phiếu nhập", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Nếu đã tồn tại thì cảnh báo
        if (addedFood.contains(selectedFood)) {
            JOptionPane.showMessageDialog(this, "Món này đã được thêm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        addedFood.add(selectedFood); // Thêm vào danh sách đã chọn

        int rowIndex = listDataFood.size();

        int yOffset = (rowIndex + 1) * 40;

        CustomTextField tfFood = new CustomTextField(selectedFood);
        tfFood.setBounds(20, yOffset, 300, 30);
        tfFood.setEditable(false);

        CustomTextField tfQuantity = new CustomTextField("0");
        tfQuantity.setBounds(340, yOffset, 100, 30);

        CustomTextField tfPrice = new CustomTextField("0");
        tfPrice.setBounds(460, yOffset, 100, 30);

        CustomButton deleteButton = new CustomButton("Xóa");
        deleteButton.setBounds(580, yOffset, 80, 30);

        entryPanel.add(tfFood);
        entryPanel.add(tfQuantity);
        entryPanel.add(tfPrice);
        entryPanel.add(deleteButton);

        listDataFood.add(tfFood);
        listDataQuantity.add(tfQuantity);
        listDataPrice.add(tfPrice);
        listDeleteButton.add(deleteButton);

        deleteButton.addActionListener(ev -> {
            entryPanel.remove(tfFood);
            entryPanel.remove(tfQuantity);
            entryPanel.remove(tfPrice);
            entryPanel.remove(deleteButton);

            listDataFood.remove(tfFood);
            listDataQuantity.remove(tfQuantity);
            listDataPrice.remove(tfPrice);
            listDeleteButton.remove(deleteButton);
            addedFood.remove(selectedFood); // Bỏ khỏi danh sách đã thêm

            refreshRows();
            reloadCustomComboboxFood(); // Cập nhật combobox
        });

        entryPanel.setPreferredSize(new Dimension(950, (rowIndex + 2) * 40));
        entryPanel.revalidate();
        entryPanel.repaint();

        reloadCustomComboboxFood(); // Cập nhật combobox sau khi thêm
    }

    private void refreshRows() {
        for (int i = 0; i < listDataFood.size(); i++) {
            listDataFood.get(i).setBounds(20, i * 40, 300, 30);
            listDataQuantity.get(i).setBounds(340, i * 40, 100, 30);
            listDataPrice.get(i).setBounds(460, i * 40, 100, 30);
            listDeleteButton.get(i).setBounds(580, i * 40, 80, 30);
        }

        entryPanel.setPreferredSize(new Dimension(950, listDataFood.size() * 40 + 10));
        entryPanel.revalidate();
        entryPanel.repaint();
    }

    private void calculateTotalMoney() {
        long total = 0;

        for (int i = 0; i < listDataFood.size(); i++) {
            try {
                int quantity = Integer.parseInt(listDataQuantity.get(i).getText());
                long price = Long.parseLong(listDataPrice.get(i).getText());
                total += quantity * price;
            } catch (NumberFormatException ignored) {
            }
        }

        totalLabel.setText("Tổng tiền: " + Utils.Helper.Comon.formatMoney(total +""));
    }

    private boolean trueData() {
        for (int i = 0; i < listDataFood.size(); i++) {
            try {
                int quantity = Integer.parseInt(listDataQuantity.get(i).getText());
                long price = Long.parseLong(listDataPrice.get(i).getText());

                if (quantity == 0 || price == 0) return false;
            } catch (NumberFormatException ignored) {
            }
        }

        return true;
    }

    private void createInbound() {
        this.calculateTotalMoney();

        boolean isTrue = this.trueData();

        if (!isTrue) {
            JOptionPane.showMessageDialog(this, "Số lượng và giá nhập phải lớn hơn 0", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String resultMoney = totalLabel.getText().replaceAll("[^0-9]", "");
        String[] regexStringStaff = this.staffTextField.getText().split(" - ");

        int totalMoney = Integer.parseInt(resultMoney);
        String noteString = noteTextArea.getText();
        int staffId = Integer.parseInt(regexStringStaff[0]);

        if (totalMoney == 0) {
            JOptionPane.showMessageDialog(this, "Chưa có dữ liệu phiếu nhập", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int note = 1;

        if (noteString.equalsIgnoreCase("")) {
            int ask = JOptionPane.showConfirmDialog(this, "Chưa có ghi chú bạn có chắc chắn", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (ask == JOptionPane.NO_OPTION) note = 0;
        }

        if (note == 1) {
            int lastestInbound = this.purchaseReceiptBLL.createLastestPurchaseReceipt(staffId, noteString);
            if (lastestInbound > 0) {
                for (int i = 0; i < listDataFood.size(); i++) {
                    String[] regexFoodName = listDataFood.get(i).getText().split(" - ");

                    int foodId = Integer.parseInt(regexFoodName[0]);
                    int quantity = Integer.parseInt(listDataQuantity.get(i).getText());
                    int price = Integer.parseInt(listDataPrice.get(i).getText());
                    this.purchaseReceiptDetailBLL.insertDataBill(lastestInbound, foodId, quantity, price);
                }
            }

            JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }





    }

    private void initComponent() {
        this.setTitle("Thêm phiếu nhập");
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        JPanel contentPanel = new JPanel(null);
        contentPanel.setBounds(0, 0, 1000, 700);

        JLabel staffLabel = new JLabel("Nhân viên nhập hàng:");
        staffLabel.setBounds(30, 20, 160, 30);
        staffTextField = new CustomTextField(staffs.getStaffId() + " - " + accountBLL.getAccountById(staffs.getAccountId()).getUsername());
        staffTextField.setBounds(200, 20, 300, 30);
        staffTextField.setEditable(false);
        staffTextField.setFocusable(false);

        JLabel categoryLabel = new JLabel("Loại món:");
        categoryLabel.setBounds(30, 70, 100, 30);
        comboboxCategory.setBounds(120, 70, 250, 30);
        comboboxCategory.addActionListener(e -> {
            reloadCustomComboboxCategory();
        });

        JLabel foodLabel = new JLabel("Tên món:");
        foodLabel.setBounds(400, 70, 100, 30);
        comboboxFood.setBounds(500, 70, 300, 30);

        CustomButton btnAddFood = new CustomButton("Thêm món");
        btnAddFood.setBounds(820, 70, 120, 30);
        btnAddFood.addActionListener(this::addItemRow);

        JLabel listLabel = new JLabel("Danh sách món nhập:");
        listLabel.setBounds(30, 120, 200, 30);

        JLabel lblFood = new JLabel("Tên món");
        lblFood.setBounds(50, 160, 100, 30);
        contentPanel.add(lblFood);

        JLabel lblQuantity = new JLabel("Số lượng");
        lblQuantity.setBounds(370, 160, 100, 30);
        contentPanel.add(lblQuantity);

        JLabel lblPrice = new JLabel("Giá nhập");
        lblPrice.setBounds(490, 160, 100, 30);
        contentPanel.add(lblPrice);

        entryPanel = new JPanel(null);
        entryPanel.setPreferredSize(new Dimension(950, 400));

        scrollPane = new CustomScrollPane(entryPanel);
        scrollPane.setBounds(20, 200, 950, 330);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEtchedBorder());

        totalLabel = new JLabel("Tổng tiền: 0 VND");
        totalLabel.setBounds(30, 540, 300, 30);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        CustomButton btnCalcTotal = new CustomButton("Tính tiền");
        btnCalcTotal.setBounds(700, 540, 150, 30);
        btnCalcTotal.addActionListener(e -> calculateTotalMoney());

        CustomButton btnSave = new CustomButton("Lưu phiếu nhập");
        btnSave.setBounds(700, 590, 150, 40);
        btnSave.addActionListener(e -> {
            createInbound();
        });

        CustomButton btnCancel = new CustomButton("Thoát");
        btnCancel.setBounds(860, 590, 100, 40);
        btnCancel.addActionListener(e -> this.dispose());

        JLabel noteLabel = new JLabel("Ghi chú:");
        noteLabel.setBounds(30, 580, 100, 30);

        noteTextArea = new JTextArea();
        noteTextArea.setLineWrap(true);
        noteTextArea.setWrapStyleWord(true);

        JScrollPane noteScrollPane = new JScrollPane(noteTextArea);
        noteScrollPane.setBounds(120, 580, 550, 60);
        noteScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        noteScrollPane.setBorder(BorderFactory.createEtchedBorder());

        contentPanel.add(noteLabel);
        contentPanel.add(noteScrollPane);

        contentPanel.add(staffLabel);
        contentPanel.add(staffTextField);
        contentPanel.add(categoryLabel);
        contentPanel.add(comboboxCategory);
        contentPanel.add(foodLabel);
        contentPanel.add(comboboxFood);
        contentPanel.add(btnAddFood);
        contentPanel.add(listLabel);
        contentPanel.add(scrollPane);
        contentPanel.add(totalLabel);
        contentPanel.add(btnCalcTotal);
        contentPanel.add(btnSave);
        contentPanel.add(btnCancel);

        this.add(contentPanel);
    }
}
