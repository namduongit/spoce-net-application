package GUI.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import GUI.Components.CustomPanel;

public class FoodCard extends CustomPanel {
    private int idProduct;
    private String imageProduct;
    private String nameProduct;
    private int priceProduct;
    private int typeProduct;
    private String statusProduct;


    public FoodCard(String imageProduct, int idProduct, String nameProduct, int priceProduct,
                    int typeProduct, String statusProduct) {
        this.idProduct = idProduct;
        this.imageProduct = imageProduct;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.typeProduct = typeProduct;
        this.statusProduct = statusProduct;
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(168, 250));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.setFocusable(true);

        // Sự kiện khi click vào card
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocus();
            }
        });

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            }
            @Override
            public void focusLost(FocusEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        });

        // Ảnh sản phẩm
        File imageFile = new File(System.getProperty("user.dir") + "/src/Assets/Food/" + this.imageProduct);
        if (!imageFile.exists()) {
            imageFile = new File(System.getProperty("user.dir") + "/src/Assets/Food/none.png");
        }
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(new ImageIcon(imageFile.getAbsolutePath()).getImage()
                .getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        imageLabel.setBounds(34, 10, 100, 100);
        this.add(imageLabel);

        // Thông tin sản phẩm
        JLabel idLabel = new JLabel("Mã sản phẩm: " + this.idProduct);
        idLabel.setBounds(10, 130, 230, 20);

        JLabel nameLabel = new JLabel("Tên: " + this.nameProduct);
        nameLabel.setBounds(10, 150, 230, 20);

        JLabel priceLabel = new JLabel("Giá: " + this.priceProduct);
        priceLabel.setBounds(10, 170, 230, 20);

        JLabel typeLabel = new JLabel("Loại: " + this.typeProduct);
        typeLabel.setBounds(10, 190, 230, 20);

        JLabel statusLabel = new JLabel(this.statusProduct);
        statusLabel.setBounds(10, 210, 230, 20);
        if (statusLabel.getText().equalsIgnoreCase("Hết hàng")) statusLabel.setForeground(Color.decode("#B71C1C"));
        else statusLabel.setForeground(Color.decode("#2E7D32"));

        // Thêm vào panel
        this.add(idLabel);
        this.add(nameLabel);
        this.add(priceLabel);
        this.add(typeLabel);
        this.add(statusLabel);
    }

    public int getIdProduct() {
        return idProduct;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public int getPriceProduct() {
        return priceProduct;
    }

    public int getTypeProduct() {
        return typeProduct;
    }

    public String getStatusProduct() {
        return statusProduct;
    }


}
