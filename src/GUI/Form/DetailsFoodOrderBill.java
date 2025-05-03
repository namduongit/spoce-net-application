package GUI.Form;

import BLL.AccountBLL;
import BLL.FoodBLL;
import BLL.FoodBillBLL;
import GUI.Components.CustomScrollPane;
import GUI.Components.CustomTextField;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DetailsFoodOrderBill extends JFrame {
    private int foodOrderBillId;
    private FoodBillBLL foodBillBLL;

    private AccountBLL accountBLL;
    private FoodBLL foodBLL;

    private ArrayList<Object[]> detailsFoodOrderBill;

    public DetailsFoodOrderBill(int foodOrderBillId) {
        this.foodOrderBillId = foodOrderBillId;
        this.foodBillBLL = new FoodBillBLL();
        this.accountBLL = new AccountBLL();
        this.foodBLL = new FoodBLL();

        this.detailsFoodOrderBill = this.foodBillBLL.getDetailFoodBillById(this.foodOrderBillId);
        this.initComponents();
    }

    private void initComponents() {
        this.setTitle("Chi tiết hóa đơn gọi đồ ăn");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(550,700);
        this.setResizable(false);

        JPanel content = this.createContent();

        this.add(content);
        this.setLocationRelativeTo(null);
    }

    private JPanel createContent() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);

        JLabel billIdLabel = new JLabel("Mã hóa đơn");
        billIdLabel.setBounds(20, 10, 100, 20);

        JLabel timeCreateBillLabel = new JLabel("Ngày lập hóa đơn");
        timeCreateBillLabel.setBounds(275, 10, 150, 20);

        CustomTextField billIdTextField = new CustomTextField(this.detailsFoodOrderBill.get(0)[0].toString());
        billIdTextField.setBounds(20, 35, 245, 30);
        billIdTextField.setFocusable(false);
        billIdTextField.setEditable(false);

        CustomTextField timeCreateBillTextField = new CustomTextField(this.detailsFoodOrderBill.get(0)[this.detailsFoodOrderBill.get(0).length - 1].toString());
        timeCreateBillTextField.setBounds(275, 35, 245, 30);
        timeCreateBillTextField.setFocusable(false);
        timeCreateBillTextField.setEditable(false);

        headerPanel.add(billIdLabel);
        headerPanel.add(timeCreateBillLabel);
        headerPanel.add(billIdTextField);
        headerPanel.add(timeCreateBillTextField);
        headerPanel.setBounds(0, 0, 550, 70);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(null);

        JPanel headerBodyPanel = new JPanel();
        headerBodyPanel.setLayout(null);
        JLabel nameFoodLabel = new JLabel("Mã - Tên món");
        nameFoodLabel.setBounds(20, 0, 220, 20);
        JLabel quantityFoodLabel = new JLabel("Số lượng");
        quantityFoodLabel.setBounds(260, 0, 100, 20);
        JLabel totalPriceFoodLabel = new JLabel("Thành tiền");
        totalPriceFoodLabel.setBounds(380, 0, 100, 20);

        headerBodyPanel.add(nameFoodLabel);
        headerBodyPanel.add(quantityFoodLabel);
        headerBodyPanel.add(totalPriceFoodLabel);
        headerBodyPanel.setBounds(0, 0, 550, 30);

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(null);

        int positionY = 0;
        int totalMoney = 0;
        int totalQuantity = 0;

        for (Object[] dataBill : this.detailsFoodOrderBill) {
            String foodName = this.foodBLL.getFoodByID(Integer.parseInt(dataBill[5].toString())).getName();
            String quantity = dataBill[6].toString();
            String totalPrice = Utils.Helper.Comon.formatMoney(Integer.parseInt(dataBill[8].toString()) * Integer.parseInt(quantity) + "");

            CustomTextField foodNameTextField = new CustomTextField(dataBill[5].toString() + " - " + foodName);
            foodNameTextField.setBounds(20, positionY * 40, 220, 30);

            CustomTextField quantityTextField = new CustomTextField(quantity);
            quantityTextField.setBounds(260, positionY * 40, 110, 30);

            CustomTextField totalPriceTextField = new CustomTextField(totalPrice);
            totalPriceTextField.setBounds(390, positionY * 40, 110, 30);

            foodNameTextField.setFocusable(false);
            foodNameTextField.setEditable(false);
            quantityTextField.setFocusable(false);
            quantityTextField.setEditable(false);
            totalPriceTextField.setFocusable(false);
            totalPriceTextField.setEditable(false);

            totalQuantity += Integer.parseInt(quantity);
            totalMoney += Integer.parseInt(dataBill[8].toString()) * Integer.parseInt(quantity);

            positionY += 1;

            dataPanel.add(foodNameTextField);
            dataPanel.add(quantityTextField);
            dataPanel.add(totalPriceTextField);
        }

        dataPanel.setPreferredSize(new Dimension(550, positionY * 45));
        dataPanel.revalidate();
        dataPanel.repaint();

        CustomScrollPane dataScrollBodyPanel = new CustomScrollPane(dataPanel);
        dataScrollBodyPanel.setBorder(null);
        dataScrollBodyPanel.setBounds(0, 30, 550, 360);

        bodyPanel.add(headerBodyPanel);
        bodyPanel.add(dataScrollBodyPanel);

        bodyPanel.setBounds(0, 80, 550, 320);

        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(null);

        JLabel totalBillLabel = new JLabel("Tổng kết");
        totalBillLabel.setBounds(20, 0, 550, 20);

        CustomTextField totalBillTextField = new CustomTextField("Số lượng: "+ totalQuantity +", Thành tiền: "+ Utils.Helper.Comon.formatMoney(totalMoney +""));
        totalBillTextField.setBounds(20, 25, 500, 30);
        totalBillTextField.setFocusable(false);
        totalBillTextField.setEditable(false);

        JLabel staffUsernameLabel = new JLabel("Nhân viên lập hóa đơn");
        staffUsernameLabel.setBounds(20, 55, 550, 20);

        CustomTextField staffUsernameTextField = new CustomTextField(
                this.detailsFoodOrderBill.get(0)[2].toString() + " - " + this.accountBLL.getAccountById(Integer.parseInt(this.detailsFoodOrderBill.get(0)[2].toString())).getUsername()
        );
        staffUsernameTextField.setBounds(20, 80, 500, 30);
        staffUsernameTextField.setFocusable(false);
        staffUsernameTextField.setEditable(false);

        JLabel playerUsernameLabel = new JLabel("Hóa đơn của khách hàng");
        playerUsernameLabel.setBounds(20, 115, 550, 20);

        CustomTextField playerUsernameTextField = new CustomTextField(this.detailsFoodOrderBill.get(0)[1].toString().equals("0") ? "Tài khoản khách (Order riêng)" :
                this.detailsFoodOrderBill.get(0)[1].toString() + " - " + this.accountBLL.getAccountById(Integer.parseInt(this.detailsFoodOrderBill.get(0)[1].toString())).getUsername());
        playerUsernameTextField.setBounds(20, 135, 500, 30);
        playerUsernameTextField.setFocusable(false);
        playerUsernameTextField.setEditable(false);

        JLabel statusBillLabel = new JLabel("Trạng thái hóa đơn");
        statusBillLabel.setBounds(20, 175, 550, 20);

        CustomTextField statusBillTextField = new CustomTextField(this.detailsFoodOrderBill.get(0)[4].toString());
        statusBillTextField.setBounds(20, 200, 500, 30);
        statusBillTextField.setFocusable(false);
        statusBillTextField.setEditable(false);

        footerPanel.add(totalBillLabel);
        footerPanel.add(totalBillTextField);
        footerPanel.add(staffUsernameLabel);
        footerPanel.add(staffUsernameTextField);
        footerPanel.add(playerUsernameLabel);
        footerPanel.add(playerUsernameTextField);
        footerPanel.add(statusBillLabel);
        footerPanel.add(statusBillTextField);
        footerPanel.setBounds(0, 420, 550, 270);

        panel.add(headerPanel);
        panel.add(bodyPanel);
        panel.add(footerPanel);
        return panel;
    }

}
