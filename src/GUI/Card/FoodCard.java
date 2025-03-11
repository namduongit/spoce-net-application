package GUI.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.math.BigDecimal;

import GUI.Components.CustomPanel;

public class FoodCard extends CustomPanel {
    private int idProduct;
    private String imageProduct;
    private String nameProduct;
    private BigDecimal priceProduct;
    private int typeProduct;
    private String statusProduct;

    public FoodCard(String imageProduct, int idProduct, String nameProduct, BigDecimal priceProduct,
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
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
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
                setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
            }
            @Override
            public void focusLost(FocusEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
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
        JLabel idLabel = new JLabel("<html><b>Mã sản phẩm:</b> <b>" + this.idProduct + "</b> </html>");
        idLabel.setBounds(10, 130, 230, 20);

        JLabel nameLabel = new JLabel("<html><b>Tên:</b> " + this.nameProduct + "</html>");
        nameLabel.setBounds(10, 150, 230, 20);

        JLabel priceLabel = new JLabel("<html><b>Giá:</b> " + this.priceProduct + " VND</html>");
        priceLabel.setBounds(10, 170, 230, 20);

        JLabel typeLabel = new JLabel("<html><b>Loại:</b> " + this.typeProduct + "</html>");
        typeLabel.setBounds(10, 190, 230, 20);

        JLabel statusLabel = new JLabel("<html><b>Trạng thái:</b>" + this.statusProduct + "</html>");
        statusLabel.setBounds(10, 210, 230, 20);

        // Thêm vào panel
        this.add(idLabel);
        this.add(nameLabel);
        this.add(priceLabel);
        this.add(typeLabel);
        this.add(statusLabel);
    }
}
