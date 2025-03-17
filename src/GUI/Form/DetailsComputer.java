package GUI.Form;

import DTO.Computers;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomPanel;
import GUI.Components.CustomTextField;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DetailsComputer extends JFrame {
    private JPanel content;
    private Computers computer;
    private CustomTextField idTextField;
    private CustomTextField nameTextField;
    private CustomTextField priceTextField;
    private CustomCombobox motherboardCb;
    private CustomCombobox mouseCb;
    private CustomCombobox keyboardCb;
    private CustomCombobox monitorCb;
    private CustomCombobox headphoneCb;
    private CustomCombobox romCb;
    private CustomCombobox roomCb;
    private CustomCombobox statusCb;


    public DetailsComputer(Computers computer) {
        this.computer = computer;
        this.initComponents();
    }

    private void initComponents() {
        this.setTitle("Thông tin chi tiết máy tính");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setSize(500,600);
        this.setResizable(false);

        this.content = createContent();


        this.add(this.content);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel createContent() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel idLabel = new JLabel("Mã máy tính:");
        idTextField = new CustomTextField();

        JLabel nameLabel = new JLabel("Tên máy tính:");
        nameTextField = new CustomTextField();

        JLabel priceLabel = new JLabel("Giá một giờ chơi:");
        priceTextField = new CustomTextField();

        JLabel motherboardLabel = new JLabel("Bo mạch chủ:");
        ArrayList<String> motherboardList = new ArrayList<>();
        motherboardCb = new CustomCombobox<>(motherboardList);

        JLabel mouseLabel = new JLabel("Chuột:");
        ArrayList<String> mouseList = new ArrayList<>();
        mouseCb = new CustomCombobox<>(mouseList);

        JLabel keyboardLabel = new JLabel("Bàn phím:");
        ArrayList<String> keyboardList = new ArrayList<>();
        keyboardCb = new CustomCombobox<>(keyboardList);

        JLabel monitorLabel = new JLabel("Màn hình:");
        ArrayList<String> monitorList = new ArrayList<>();
        monitorCb = new CustomCombobox<>(monitorList);

        JLabel headphoneLabel = new JLabel("Tai nghe:");
        ArrayList<String> headphoneList = new ArrayList<>();
        headphoneCb = new CustomCombobox<>(headphoneList);

        JLabel romLabel = new JLabel("Rom:");
        ArrayList<String> romList = new ArrayList<>();
        romCb = new CustomCombobox<>(romList);

        JLabel roomLabel = new JLabel("Phòng:");
        ArrayList<String> roomList = new ArrayList<>();
        roomCb = new CustomCombobox<>(roomList);

        JLabel statusLabel = new JLabel("Trạng thái:");
        ArrayList<String> statusList = new ArrayList<>();
        statusCb = new CustomCombobox<>(statusList);

        JPanel separator = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0,0,getWidth(),getHeight());
            }
        };
        separator.setBounds(245,215,2,250);

        CustomButton saveBtn = Utils.Helper.CreateComponent.createBlueButton("Lưu lại");
        saveBtn.setBounds(20,495,100,30);

        CustomButton resetBtn = Utils.Helper.CreateComponent.createOrangeButton("Đặt lại");
        resetBtn.setBounds(140,495,100,30);

        idLabel.setBounds(20,10,200,20);
        idTextField.setBounds(20,35, 450,30);

        nameLabel.setBounds(20,75,200,20);
        nameTextField.setBounds(20,100,450,30);

        priceLabel.setBounds(20,140,200,20);
        priceTextField.setBounds(20,165,450,30);

        motherboardLabel.setBounds(20,205,200,20);
        motherboardCb.setBounds(20,230,200,30);

        mouseLabel.setBounds(20,270,200,20);
        mouseCb.setBounds(20,295,200,30);

        keyboardLabel.setBounds(20,335,200,20);
        keyboardCb.setBounds(20,360,200,30);

        monitorLabel.setBounds(20,400,200,20);
        monitorCb.setBounds(20,425,200,30);

        headphoneLabel.setBounds(270,205,200,20);
        headphoneCb.setBounds(270,230,200,30);

        romLabel.setBounds(270,270,200,20);
        romCb.setBounds(270,295,200,30);

        roomLabel.setBounds(270,335,200,20);
        roomCb.setBounds(270,360,200,30);

        statusLabel.setBounds(270,400,200,20);
        statusCb.setBounds(270,425,200,30);

        panel.add(idLabel);
        panel.add(idTextField);
        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(priceLabel);
        panel.add(priceTextField);
        panel.add(motherboardLabel);
        panel.add(motherboardCb);
        panel.add(mouseLabel);
        panel.add(mouseCb);
        panel.add(keyboardLabel);
        panel.add(keyboardCb);
        panel.add(monitorLabel);
        panel.add(monitorCb);
        panel.add(headphoneLabel);
        panel.add(headphoneCb);
        panel.add(romLabel);
        panel.add(romCb);
        panel.add(statusLabel);
        panel.add(statusCb);
        panel.add(roomLabel);
        panel.add(roomCb);
        panel.add(separator);
        panel.add(saveBtn);
        panel.add(resetBtn);

        return panel;
    }

    public static void main(String[] args) {
        new DetailsComputer(new Computers());
    }
}
