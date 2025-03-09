package GUI.Card;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import GUI.Components.CustomButton;
import Utils.Helper.Comon;

public class FoodCard extends JPanel {
    private String idProduct;
    private ImageIcon imageProduct;
    private String nameProduct;
    private String price;

    public FoodCard(ImageIcon imageProduct, String idProduct, String nameProduct, String price) {
        this.imageProduct = imageProduct != null ? imageProduct : new ImageIcon(System.getProperty("user.dir") +"/src/Assets/Food/none.png");
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.price = Comon.formatMoney(price);
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(null);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(150, 200));

        // Ảnh sản phẩm
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(imageProduct.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        imageLabel.setBounds(25, 10, 100, 100); // Căn giữa theo chiều ngang
        this.add(imageLabel);

        
    }
}