package GUI.Card;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;

import GUI.Components.CustomButton;
import Utils.Helper.Comon;

public class FoodCard extends JPanel {
    private ImageIcon imageProduct;
    private String nameProduct;
    private String price;
    private CustomButton addButton;
    private CustomButton editButton;

    public FoodCard(ImageIcon imageProduct, String nameProduct, String price, CustomButton addButton, CustomButton editButton) {
        this.imageProduct = imageProduct != null ? imageProduct : new ImageIcon(System.getProperty("user.dir") +"/src/Assets/Food/none.png");
        this.nameProduct = nameProduct;
        this.price = Comon.formatMoney(price);
        this.addButton = addButton;
        this.editButton = editButton;

        this.initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new DimensionUIResource(200, 200));
        this.setLayout(null);

        // Ảnh sản phẩm
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(imageProduct.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        imageLabel.setBounds(50, 10, 100, 100); // Căn giữa theo chiều ngang
        this.add(imageLabel);

        // Tên sản phẩm
        JLabel nameLabel = new JLabel(nameProduct, SwingConstants.LEFT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setBounds(10, 110, 180, 20);
        this.add(nameLabel);

        // Giá sản phẩm
        JLabel priceLabel = new JLabel(price, SwingConstants.LEFT);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceLabel.setBounds(10, 130, 180, 20);
        this.add(priceLabel);

        // Nút chức năng
        addButton.setBounds(10, 160, 85, 30);
        editButton.setBounds(105, 160, 85, 30);
        this.add(addButton);
        this.add(editButton);
    }
}