package GUI.Form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import BLL.FoodBLL;
import DTO.Foods;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomScrollPane;
import GUI.Components.CustomTextField;
import Utils.Helper.CreateComponent;

public class AddingOrder extends JFrame {
    private JPanel itemPanel;
    private CustomButton addItemButton;
    private CustomButton saveButton;
    private CustomButton billingButton;

    private JLabel totalBill;

    private ArrayList<FoodBLL> foodBLLList = new ArrayList<>();
    private ArrayList<CustomCombobox<String>> foodComboList = new ArrayList<>();
    private ArrayList<JTextField> quantityFieldList = new ArrayList<>();
    private ArrayList<Foods> foodList;

    public AddingOrder() {
        setTitle("Thêm hóa đơn");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        foodList = new FoodBLL().getAllFoods();

        itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding 10px
        wrapperPanel.add(itemPanel, BorderLayout.NORTH); // giữ các dòng căn lên trên
        
        CustomScrollPane scrollPane = new CustomScrollPane(wrapperPanel);
        scrollPane.setBounds(25, 25, 530, 320);
        add(scrollPane);

        addItemButton = CreateComponent.createBlueButton("Thêm món");
        addItemButton.setBounds(25, 400, 120, 30);
        addItemButton.addActionListener(this::addItemRow);
        add(addItemButton);

        saveButton = CreateComponent.createOrangeButton("Đặt món");
        saveButton.setBounds(150, 400, 120, 30);
        saveButton.addActionListener(e -> saveOrder());
        add(saveButton);

        billingButton = CreateComponent.createBrownButton("Tính tiền");
        billingButton.setBounds(275, 400, 120, 30);
        add(billingButton);

        totalBill = new JLabel("Tổng tiền:");
        totalBill.setBounds(25, 360, 500, 30);
        add(totalBill);

        // Thêm dòng đầu tiên
        addItemRow(null);
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
            foodCombo.addItem(food.getName());
        }
    
        JLabel labelQuantity = new JLabel("Số lượng:");
        labelQuantity.setPreferredSize(new Dimension(60, 30));
    
        CustomTextField quantityField = new CustomTextField();
        quantityField.setPreferredSize(new Dimension(60, 30));
    
        rowPanel.add(labelFood);
        rowPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        rowPanel.add(foodCombo);
        rowPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        rowPanel.add(labelQuantity);
        rowPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        rowPanel.add(quantityField);
    
        itemPanel.add(rowPanel);
        itemPanel.revalidate();
        itemPanel.repaint();
    
        foodComboList.add(foodCombo);
        quantityFieldList.add(quantityField);
    }
    

    private void saveOrder() {
        System.out.println("Danh sách món đã chọn:");
        for (int i = 0; i < foodComboList.size(); i++) {
            String foodName = (String) foodComboList.get(i).getSelectedItem();
            String quantity = quantityFieldList.get(i).getText().trim();

            System.out.println("- " + foodName + ": " + quantity);
        }
    }

    public static void main(String[] args) {
        new AddingOrder().setVisible(true);
    }
}
