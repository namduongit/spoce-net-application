package GUI.Form;

import BLL.*;
import DTO.Categories;
import DTO.Foods;
import DTO.PurchaseReceipt;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomScrollPane;
import GUI.Components.CustomTextField;
import Utils.Helper.Comon;
import Utils.Helper.CreateComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailsInbound extends JFrame {
    private CategoryBLL categoryBLL;
    private FoodBLL foodBLL;
    private StaffBLL staffBLL;
    private AccountBLL accountBLL;
    private PurchaseReceiptBLL purchaseReceiptBLL;
    private PurchaseReceiptDetailBLL purchaseReceiptDetailBLL;
    private ArrayList<String> currentFoodList;
    private ArrayList<CustomTextField> foodDataList;
    private ArrayList<CustomTextField> foodQuantityList;
    private ArrayList<CustomTextField> foodPriceList;
    private ArrayList<JButton> deleteButtonList;

    // Trạng thái phiếu nhập
    private CustomTextField statusTextField;

    private CustomCombobox<String> foodNameCombo;
    private JPanel content;
    private CustomTextField totalTextField;
    private JTextArea noteTextArea;

    private PurchaseReceipt purchaseReceipt;
    private ArrayList<ArrayList<Object>> details;

    public DetailsInbound(int id) {
        this.categoryBLL = new CategoryBLL();
        this.foodBLL = new FoodBLL();
        this.purchaseReceiptBLL = new PurchaseReceiptBLL();
        this.staffBLL = new StaffBLL();
        this.accountBLL = new AccountBLL();
        this.purchaseReceiptDetailBLL = new PurchaseReceiptDetailBLL();
        this.currentFoodList = new ArrayList<>();
        this.foodDataList = new ArrayList<>();
        this.foodPriceList = new ArrayList<>();
        this.foodQuantityList = new ArrayList<>();
        this.deleteButtonList = new ArrayList<>();
        this.purchaseReceipt = this.purchaseReceiptBLL.getPurchaseReceiptById(id);
        this.details = this.purchaseReceiptDetailBLL.getDetailById(id);

        this.initComponents();
    }

    private void initComponents() {
        this.setTitle("Chi tiết phiếu nhập");
        this.setSize(700, 650);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        JLabel idLabel = new JLabel("Mã phiếu nhập");
        idLabel.setBounds(10, 10, 100, 20);

        CustomTextField idTextField = new CustomTextField(this.purchaseReceipt.getReceiptId() + "");
        idTextField.setBounds(10, 35, 100, 30);
        idTextField.setEditable(false);
        idTextField.setFocusable(false);

        JLabel employeeNameLabel = new JLabel("Tên nhân viên lập phiếu");
        employeeNameLabel.setBounds(120, 10, 200, 20);

        CustomTextField employeeNameTextField = new CustomTextField(this.accountBLL
                .getAccountById(this.staffBLL.getStaffById(this.purchaseReceipt.getStaffId()).getAccountId())
                .getUsername());
        employeeNameTextField.setBounds(120, 35, 300, 30);
        employeeNameTextField.setEditable(false);
        employeeNameTextField.setFocusable(false);

        JLabel createdDateLabel = new JLabel("Ngày lập phiếu");
        createdDateLabel.setBounds(10, 75, 100, 20);

        CustomTextField createdDateTextField = new CustomTextField(this.purchaseReceipt.getCreateAt() + "");
        createdDateTextField.setBounds(10, 100, 150, 30);
        createdDateTextField.setEditable(false);
        createdDateTextField.setFocusable(false);

        JLabel statusLabel = new JLabel("Trạng thái phiếu nhập");
        statusLabel.setBounds(170, 75, 200, 20);

        CustomTextField statusTextField = new CustomTextField(this.purchaseReceipt.getStatus());
        statusTextField.setBounds(170, 100, 150, 30);
        statusTextField.setEditable(false);
        statusTextField.setFocusable(false);

        JLabel totalLabel = new JLabel("Tổng tiền");
        totalLabel.setBounds(330, 75, 200, 20);

        totalTextField = new CustomTextField(Comon.formatMoney(this.purchaseReceipt.getTotal() + ""));
        totalTextField.setBounds(330, 100, 200, 30);
        totalTextField.setEditable(false);
        totalTextField.setFocusable(false);

        JLabel noteLabel = new JLabel("Ghi chú");
        noteLabel.setBounds(10, 140, 200, 20);

        noteTextArea = new JTextArea(this.purchaseReceipt.getNote());
        noteTextArea.setLineWrap(true);
        noteTextArea.setWrapStyleWord(true);
        JScrollPane noteScrollPane = new JScrollPane(noteTextArea);
        noteScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        noteScrollPane.setBorder(BorderFactory.createEtchedBorder());
        noteScrollPane.setBounds(10, 165, 665, 60);

        JLabel foodTypeLabel = new JLabel("Loại món");
        foodTypeLabel.setBounds(10, 235, 60, 20);

        ArrayList<String> foodTypeList = new ArrayList<>();
        foodTypeList.add("Tất cả");
        for (Categories x : this.categoryBLL.getAllCategories()) {
            foodTypeList.add(x.getName());
        }
        CustomCombobox<String> foodTypeCombo = new CustomCombobox<>(foodTypeList);
        foodTypeCombo.setBounds(75, 235, 120, 30);
        foodTypeCombo.addActionListener(e -> {
            String foodType = foodTypeCombo.getSelectedItem() != null ? foodTypeCombo.getSelectedItem().toString()
                    : null;
            if (foodType == null)
                return;

            ArrayList<String> foodNameList = new ArrayList<>();
            if (foodType.equals("Tất cả")) {
                for (Foods x : this.foodBLL.getAllFoods()) {
                    foodNameList.add(x.getFoodId() + " - " + x.getName());

                }
            } else {
                for (Foods x : this.foodBLL.getFoodByCategory(foodType)) {
                    foodNameList.add(x.getFoodId() + " - " + x.getName());
                }
            }
            this.foodNameCombo.setModel(new DefaultComboBoxModel<>(foodNameList.toArray(new String[0])));
        });

        JLabel foodNameLabel = new JLabel("Tên món");
        foodNameLabel.setBounds(205, 235, 60, 20);

        ArrayList<String> foodNameList = new ArrayList<>();
        for (Foods x : this.foodBLL.getAllFoods()) {
            foodNameList.add(x.getFoodId() + " - " + x.getName());
        }

        foodNameCombo = new CustomCombobox<>(foodNameList);
        foodNameCombo.setBounds(270, 235, 250, 30);

        CustomButton addFoodButton = CreateComponent.createButtonNoIcon("Thêm món");
        addFoodButton.setBounds(530, 235, 120, 30);
        addFoodButton.addActionListener(this::addItemRow);

        content = new JPanel(null);
        CustomScrollPane scrollPane = new CustomScrollPane(content);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        scrollPane.setBounds(10, 275, 665, 280);

        JLabel foodNameOfTableLabel = new JLabel("Tên món");
        foodNameOfTableLabel.setBounds(10, 15, 100, 20);

        JLabel foodQuantityLabel = new JLabel("Số lượng");
        foodQuantityLabel.setBounds(330, 15, 100, 20);

        JLabel foodPriceLabel = new JLabel("Giá nhập");
        foodPriceLabel.setBounds(450, 15, 100, 20);

        this.content.add(foodNameOfTableLabel);
        this.content.add(foodPriceLabel);
        this.content.add(foodQuantityLabel);
        this.addItemByDetails();

        CustomButton calculateButton = CreateComponent.createGrayButton("Tính tiền");
        calculateButton.setBounds(10, 565, 100, 30);
        calculateButton.addActionListener(e -> this.calculateTotalMoney());

        CustomButton saveButton = CreateComponent.createGreenButton("Lưu");
        saveButton.setBounds(465, 565, 100, 30);
        saveButton.addActionListener(e -> this.saveChanges());

        CustomButton exitButton = CreateComponent.createOrangeButton("Thoát");
        exitButton.setBounds(575, 565, 100, 30);
        exitButton.addActionListener(e -> this.dispose());

        this.add(idLabel);
        this.add(idTextField);
        this.add(employeeNameLabel);
        this.add(employeeNameTextField);
        this.add(createdDateLabel);
        this.add(createdDateTextField);
        this.add(statusLabel);
        this.add(statusTextField);
        this.add(totalLabel);
        this.add(totalTextField);
        this.add(noteLabel);
        this.add(noteScrollPane);
        this.add(foodTypeLabel);
        this.add(foodTypeCombo);
        this.add(foodNameLabel);
        this.add(foodNameCombo);
        this.add(addFoodButton);
        this.add(scrollPane);
        this.add(calculateButton);
        this.add(saveButton);
        this.add(exitButton);
        this.setLocationRelativeTo(null);
    }

    private void addItemRow(ActionEvent e) {
        String selectedFood = this.foodNameCombo.getSelectedItem() != null
                ? this.foodNameCombo.getSelectedItem().toString()
                : null;
        if (selectedFood == null) {
            JOptionPane.showMessageDialog(this, "Hết món để tạo phiếu nhập", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Nếu đã tồn tại thì cảnh báo
        if (this.currentFoodList.contains(selectedFood)) {
            JOptionPane.showMessageDialog(this, "Món này đã được thêm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Nếu đã xác nhận thì không cho thêm món
        if (this.purchaseReceipt.getStatus().equals("Đã xác nhận")) {
            JOptionPane.showMessageDialog(
                    null,
                    "Phiếu đã xác nhận thì không thể thêm món",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        currentFoodList.add(selectedFood); // Thêm vào danh sách đã chọn

        int rowIndex = this.foodDataList.size();

        int yOffset = (rowIndex + 1) * 40;

        CustomTextField tfFood = new CustomTextField(selectedFood);
        tfFood.setBounds(10, yOffset, 300, 30);
        tfFood.setFocusable(false);

        CustomTextField tfQuantity = new CustomTextField("0");
        tfQuantity.setBounds(330, yOffset, 100, 30);

        CustomTextField tfPrice = new CustomTextField("0");
        tfPrice.setBounds(450, yOffset, 100, 30);

        CustomButton deleteButton = new CustomButton("Xóa");
        deleteButton.setBounds(570, yOffset, 80, 30);

        this.content.add(tfFood);
        this.content.add(tfQuantity);
        this.content.add(tfPrice);
        this.content.add(deleteButton);

        this.foodDataList.add(tfFood);
        this.foodQuantityList.add(tfQuantity);
        this.foodPriceList.add(tfPrice);
        this.deleteButtonList.add(deleteButton);

        deleteButton.addActionListener(ev -> {
            if (this.purchaseReceipt.getStatus().equals("Đã xác nhận")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Phiếu đã xác nhận thì không thể sửa",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.content.remove(tfFood);
            this.content.remove(tfQuantity);
            this.content.remove(tfPrice);
            this.content.remove(deleteButton);

            this.foodDataList.remove(tfFood);
            this.foodQuantityList.remove(tfQuantity);
            this.foodPriceList.remove(tfPrice);
            this.deleteButtonList.remove(deleteButton);
            this.currentFoodList.remove(selectedFood); // Bỏ khỏi danh sách đã thêm

            refreshRows();
        });

        this.content.setPreferredSize(new Dimension(950, (rowIndex + 2) * 40));
        this.content.revalidate();
        this.content.repaint();
    }

    private void refreshRows() {
        for (int i = 0; i < this.foodDataList.size(); i++) {
            this.foodDataList.get(i).setBounds(10, (i + 1) * 40, 300, 30);
            this.foodQuantityList.get(i).setBounds(330, (i + 1) * 40, 100, 30);
            this.foodPriceList.get(i).setBounds(450, (i + 1) * 40, 100, 30);
            this.deleteButtonList.get(i).setBounds(570, (i + 1) * 40, 80, 30);
        }

        this.content.setPreferredSize(new Dimension(950, this.foodDataList.size() * 40 + 10));
        this.content.revalidate();
        this.content.repaint();
    }

    private void calculateTotalMoney() {
        long total = 0;

        for (int i = 0; i < this.foodDataList.size(); i++) {
            try {
                int quantity = Integer.parseInt(this.foodQuantityList.get(i).getText());
                long price = Long.parseLong(this.foodPriceList.get(i).getText());
                total += quantity * price;
            } catch (NumberFormatException ignored) {
            }
        }

        this.totalTextField.setText(Utils.Helper.Comon.formatMoney(total + ""));
    }

    private boolean trueData() {
        for (int i = 0; i < this.foodDataList.size(); i++) {
            try {
                int quantity = Integer.parseInt(this.foodQuantityList.get(i).getText());
                long price = Long.parseLong(this.foodPriceList.get(i).getText());

                if (quantity == 0 || price == 0)
                    return false;
            } catch (NumberFormatException ignored) {
            }
        }

        return true;
    }

    private void addItemByDetails() {
        for (ArrayList<Object> detail : this.details) {
            currentFoodList.add(detail.get(0) + " - " + detail.get(1));

            int rowIndex = this.foodDataList.size();

            int yOffset = (rowIndex + 1) * 40;

            CustomTextField tfFood = new CustomTextField(detail.get(0) + " - " + detail.get(1));
            tfFood.setBounds(10, yOffset, 300, 30);
            tfFood.setFocusable(false);

            CustomTextField tfQuantity = new CustomTextField(detail.get(2) + "");
            tfQuantity.setBounds(330, yOffset, 100, 30);

            CustomTextField tfPrice = new CustomTextField(detail.get(3) + "");
            tfPrice.setBounds(450, yOffset, 100, 30);

            CustomButton deleteButton = new CustomButton("Xóa");
            deleteButton.setBounds(570, yOffset, 80, 30);

            this.content.add(tfFood);
            this.content.add(tfQuantity);
            this.content.add(tfPrice);
            this.content.add(deleteButton);

            this.foodDataList.add(tfFood);
            this.foodQuantityList.add(tfQuantity);
            this.foodPriceList.add(tfPrice);
            this.deleteButtonList.add(deleteButton);

            deleteButton.addActionListener(ev -> {
                if (this.purchaseReceipt.getStatus().equals("Đã xác nhận")) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Phiếu đã xác nhận thì không thể sửa",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                this.content.remove(tfFood);
                this.content.remove(tfQuantity);
                this.content.remove(tfPrice);
                this.content.remove(deleteButton);

                this.foodDataList.remove(tfFood);
                this.foodQuantityList.remove(tfQuantity);
                this.foodPriceList.remove(tfPrice);
                this.deleteButtonList.remove(deleteButton);
                this.currentFoodList.remove(detail.get(0) + " - " + detail.get(1)); // Bỏ khỏi danh sách đã thêm

                refreshRows();
            });

            this.content.setPreferredSize(new Dimension(950, (rowIndex + 2) * 40));
            this.content.revalidate();
            this.content.repaint();
        }
    }

    private void saveChanges() {
        if (!this.trueData()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Either the quantity or price is empty",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (this.purchaseReceipt.getStatus().equals("Đã xác nhận")) {
            JOptionPane.showMessageDialog(
                    null,
                    "Phiếu đã xác nhận thì không thể sửa",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.calculateTotalMoney();

        this.purchaseReceiptDetailBLL.deleteAllRowsOfSpecificId(this.purchaseReceipt.getReceiptId());

        for (int i = 0; i < this.foodDataList.size(); i++) {
            try {
                int quantity = Integer.parseInt(this.foodQuantityList.get(i).getText());
                int price = Integer.parseInt(this.foodPriceList.get(i).getText());

                this.purchaseReceiptBLL.updateNote(this.purchaseReceipt.getReceiptId(), this.noteTextArea.getText());
                this.purchaseReceiptDetailBLL.insertDataBill(this.purchaseReceipt.getReceiptId(),
                        this.getFoodIdFromFullName(this.foodDataList.get(i).getText()), quantity, price);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Exception was thrown by saveChanges method of DetailsInbound",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        JOptionPane.showMessageDialog(
                null,
                "Successfully Saved",
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private int getFoodIdFromFullName(String str) {
        String[] arr = str.split(" - ");
        return Integer.parseInt(arr[0]);
    }

    public static void main(String[] args) {
        new DetailsInbound(1).setVisible(true);
    }
}
