package GUI.Form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;

import BLL.FoodBLL;
import BLL.FoodBillBLL;
import BLL.FoodOrderBLL;
import DTO.Foods;
import DTO.Staffs;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomScrollPane;
import GUI.Components.CustomTextField;
import Utils.Helper.CreateComponent;

public class AddingOrder extends JFrame {
    private Staffs loginStaff;

    private FoodBillBLL foodBillBLL;
    private FoodOrderBLL foodOrderBLL;

    private JPanel itemPanel;
    private CustomButton addItemButton;
    private CustomButton saveButton;
    private CustomButton billingButton;

    private JLabel totalBill;

    private ArrayList<CustomCombobox<String>> foodComboList = new ArrayList<>();
    private ArrayList<JTextField> quantityFieldList = new ArrayList<>();
    private ArrayList<JPanel> rowPanelList = new ArrayList<>();
    private ArrayList<Foods> foodList;

    public AddingOrder(Staffs loginStaffs) {
        this.loginStaff = loginStaffs;

        this.foodBillBLL = new FoodBillBLL();
        this.foodOrderBLL = new FoodOrderBLL();

        this.setTitle("Thêm hóa đơn");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(
                new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Icon/icons8-cheque-100.png").getImage());
        this.setResizable(false);

        this.foodList = new FoodBLL().getAllFoods();

        this.itemPanel = new JPanel();
        this.itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wrapperPanel.add(itemPanel, BorderLayout.NORTH);

        CustomScrollPane scrollPane = new CustomScrollPane(wrapperPanel);
        scrollPane.setBounds(25, 25, 530, 320);
        add(scrollPane);

        this.addItemButton = CreateComponent.createBlueButton("Thêm món");
        this.addItemButton.setBounds(25, 400, 120, 30);
        this.addItemButton.addActionListener(this::addItemRow);
        this.add(addItemButton);

        this.saveButton = CreateComponent.createOrangeButton("Đặt món");
        this.saveButton.setBounds(150, 400, 120, 30);
        this.saveButton.addActionListener(e -> saveOrder());
        this.add(saveButton);

        this.billingButton = CreateComponent.createBrownButton("Tính tiền");
        this.billingButton.setBounds(275, 400, 120, 30);
        this.billingButton.addActionListener(e -> updateTotal());
        this.add(billingButton);

        this.totalBill = new JLabel("Tổng tiền:");
        this.totalBill.setBounds(25, 360, 500, 30);
        this.add(totalBill);

        this.addItemRow(null);
    }

    private void addItemRow(ActionEvent e) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setMaximumSize(new Dimension(600, 40));
        rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelFood = new JLabel("Món:");
        labelFood.setPreferredSize(new Dimension(40, 30));

        CustomCombobox<String> foodCombo = new CustomCombobox<>();
        foodCombo.setPreferredSize(new Dimension(200, 30));
        for (Foods food : foodList) {
            foodCombo.addItem(food.getFoodId() + " - " + food.getName());
        }

        JLabel labelQuantity = new JLabel("Số lượng:");
        labelQuantity.setPreferredSize(new Dimension(60, 30));

        CustomTextField quantityField = new CustomTextField();
        quantityField.setPreferredSize(new Dimension(60, 30));

        CustomButton btnRemove = new CustomButton("Xóa");
        btnRemove.setPreferredSize(new Dimension(60, 30));
        btnRemove.addActionListener(evt -> {
            int index = rowPanelList.indexOf(rowPanel);
            if (index >= 0) {
                itemPanel.remove(rowPanel.getParent());
                rowPanelList.remove(index);
                foodComboList.remove(index);
                quantityFieldList.remove(index);
                itemPanel.revalidate();
                itemPanel.repaint();
                updateTotal();
            }
        });

        rowPanel.add(labelFood);
        rowPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        rowPanel.add(foodCombo);
        rowPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        rowPanel.add(labelQuantity);
        rowPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        rowPanel.add(quantityField);
        rowPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        rowPanel.add(btnRemove);

        // Tạo khoảng cách 10px dưới
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        wrapperPanel.add(rowPanel, BorderLayout.CENTER);

        this.itemPanel.add(wrapperPanel);
        this.rowPanelList.add(rowPanel);
        this.foodComboList.add(foodCombo);
        this.quantityFieldList.add(quantityField);
        this.itemPanel.revalidate();
        this.itemPanel.repaint();
        updateTotal();
    }

    private void saveOrder() {
        HashSet<String> selectedFoods = new HashSet<>();
        for (int i = 0; i < foodComboList.size(); i++) {
            String selected = (String) foodComboList.get(i).getSelectedItem();
            if (selectedFoods.contains(selected)) {
                JOptionPane.showMessageDialog(this, "Món '" + selected + "' đã được chọn ở dòng khác!", "Lỗi",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            selectedFoods.add(selected);
        }

        for (int i = 0; i < foodComboList.size(); i++) {
            String quantityStr = quantityFieldList.get(i).getText().trim();

            if (quantityStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng cho dòng thứ " + (i + 1), "Lỗi",
                        JOptionPane.WARNING_MESSAGE);
                quantityFieldList.get(i).requestFocus();
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng ở dòng " + (i + 1) + " phải lớn hơn 0", "Lỗi",
                            JOptionPane.WARNING_MESSAGE);
                    quantityFieldList.get(i).requestFocus();
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng ở dòng " + (i + 1) + " không hợp lệ", "Lỗi",
                        JOptionPane.WARNING_MESSAGE);
                quantityFieldList.get(i).requestFocus();
                return;
            }
        }

        int resultOrder = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn đặt đơn này?",
                "Xác nhận đặt đơn",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (resultOrder == JOptionPane.CANCEL_OPTION) {
            return;
        }

        updateTotal();

        int lastBill = this.foodBillBLL.createLastestBill(this.loginStaff.getStaffId());

        for (int i = 0; i < foodComboList.size(); i++) {
            String foodName = (String) foodComboList.get(i).getSelectedItem();
            String quantity = quantityFieldList.get(i).getText().trim();

            String[] regexStrings = foodName.split(" - ");

            String idString = "";
            String nameString = "";

            if (regexStrings.length > 0) {
                idString = regexStrings[0];
                for (int j = 1; j < regexStrings.length; j++) {
                    nameString += regexStrings[j] + " ";
                }
                nameString = nameString.trim();
            }

            this.foodOrderBLL.insertDataBill(lastBill, Integer.parseInt(idString), Integer.parseInt(quantity));
        }

        int totalPrice = Integer.parseInt(this.totalBill.getText().replaceAll("[^\\d]", ""));

        boolean updatePriceBill = this.foodBillBLL.updateTotalPrice(lastBill, totalPrice);

        if (updatePriceBill) {
            JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công\nBạn có thể xác nhận trong mục Hóa đơn",
                    "Thông báo", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Thêm hóa đơn thất bại", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateTotal() {
        int total = calculateTotal();
        totalBill.setText("Tổng tiền: " + Utils.Helper.Comon.formatMoney(total + ""));
    }

    private int calculateTotal() {
        int total = 0;
        for (int i = 0; i < foodComboList.size(); i++) {
            String foodName = (String) foodComboList.get(i).getSelectedItem();
            String quantityText = quantityFieldList.get(i).getText().trim();
            int quantity = 0;

            try {
                quantity = Integer.parseInt(quantityText);
            } catch (NumberFormatException ignored) {
            }

            String[] regexStrings = foodName.split(" - ");

            String idString = "";
            String nameString = "";

            if (regexStrings.length > 0) {
                idString = regexStrings[0];
                for (int j = 1; j < regexStrings.length; j++) {
                    nameString += regexStrings[j] + " ";
                }
                nameString = nameString.trim();
            }

            for (Foods food : foodList) {
                if (food.getName().equals(nameString) && Integer.parseInt(idString) == food.getFoodId()) {
                    total += food.getPrice() * quantity;
                    break;
                }
            }
        }
        return total;
    }

    public static void main(String[] args) {
        new AddingOrder(null).setVisible(true);
    }
}
