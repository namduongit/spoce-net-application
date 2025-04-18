package GUI.Card;

import GUI.Components.CustomPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComputerCard extends CustomPanel {
    private int computerId;
    private String computerName;
    private String roomType;
    private int pricePerHour;
    private Border blackBorder;
    private Border blueBorder;

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Border getBlackBorder() {
        return blackBorder;
    }

    public void setBlackBorder(Border blackBorder) {
        this.blackBorder = blackBorder;
    }

    public Border getBlueBorder() {
        return blueBorder;
    }

    public void setBlueBorder(Border blueBorder) {
        this.blueBorder = blueBorder;
    }

    public int getComputerId() {
        return computerId;
    }

    public void setComputerId(int computerId) {
        this.computerId = computerId;
    }

    public ComputerCard(int computerId, String computerName, String roomType, int pricePerHour) {
        this.computerId = computerId;
        this.computerName = computerName;
        this.roomType = roomType;
        this.pricePerHour = pricePerHour;
        this.blackBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        this.blueBorder = BorderFactory.createLineBorder(Color.BLUE, 2);

        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(204, 200));
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.setBorder(this.blackBorder);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocus();
            }
        });
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                ComputerCard.this.setBorder(ComputerCard.this.blueBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                ComputerCard.this.setBorder(ComputerCard.this.blackBorder);
            }
        });

        JLabel idLabel = new JLabel("Mã máy tính: " + this.computerId);
        idLabel.setBounds(10, 40, 230, 20);

        JLabel nameLabel = new JLabel("Tên máy tính: " + this.computerName);
        nameLabel.setBounds(10, 70, 230, 20);

        JLabel roomTypeLabel = new JLabel("Phòng: " + this.roomType);
        roomTypeLabel.setBounds(10, 100, 230, 20);

        JLabel priceLabel = new JLabel("Giá tiền một giờ: " + this.pricePerHour);
        priceLabel.setBounds(10, 130, 230, 20);

        this.add(idLabel);
        this.add(nameLabel);
        this.add(roomTypeLabel);
        this.add(priceLabel);
    }
}
