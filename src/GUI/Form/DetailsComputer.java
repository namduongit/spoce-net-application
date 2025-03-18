package GUI.Form;

import BLL.*;
import DTO.*;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomPanel;
import GUI.Components.CustomTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

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
    private MotherboardBLL motherboardBLL;
    private MouseBLL mouseBLL;
    private KeyboardBLL keyboardBLL;
    private MonitorBLL monitorBLL;
    private HeadphoneBLL headphoneBLL;
    private RomBLL romBLL;
    private ArrayList<String> statusList;

    public DetailsComputer(Computers computer) {
        this.computer = computer;
        this.motherboardBLL = new MotherboardBLL();
        this.mouseBLL = new MouseBLL();
        this.keyboardBLL = new KeyboardBLL();
        this.monitorBLL = new MonitorBLL();
        this.headphoneBLL = new HeadphoneBLL();
        this.romBLL = new RomBLL();
        this.statusList = new ArrayList<>(Arrays.asList("Trong kho", "Đang sử dụng", "Thiếu linh kiện", "Bảo trì", "Hỏng"));
        this.initComponents();
    }

    private void initComponents() {
        this.setTitle("Thông tin chi tiết máy tính");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setSize(550,600);
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
        idTextField = new CustomTextField(this.computer.getComputerId()+"");
        idTextField.setEditable(false);

        JLabel nameLabel = new JLabel("Tên máy tính:");
        nameTextField = new CustomTextField(this.computer.getName());

        JLabel priceLabel = new JLabel("Giá một giờ chơi:");
        priceTextField = new CustomTextField(this.computer.getPricePerHour()+"");

        JLabel motherboardLabel = new JLabel("Bo mạch chủ:");
        ArrayList<String> motherboardList = new ArrayList<>();

        for (Motherboards x : this.motherboardBLL.getMotherboardsByStatus("Trong kho")) {
            motherboardList.add(x.getModel());
        }

        String motherboardName = new String();
        for (Motherboards x : this.motherboardBLL.getAllMotherboards()) {
            if (x.getMotherboardId() == this.computer.getMotherboardId()) {
                motherboardName = x.getModel();
                break;
            }
        }

        motherboardList.add(0, "Đang chọn: " + motherboardName);
        motherboardCb = new CustomCombobox<>(motherboardList);

        JLabel mouseLabel = new JLabel("Chuột:");
        ArrayList<String> mouseList = new ArrayList<>();
        for (Mouse x : this.mouseBLL.getMousesByStatus("Trong kho")) {
            mouseList.add(x.getModel());
        }
        String mouseName = new String();
        for (Mouse x : this.mouseBLL.getAllMouses()) {
            if (x.getMouseId() == this.computer.getMouseId()) {
                mouseName = x.getModel();
            }
        }
        mouseList.add(0, "Đang chọn: " + mouseName);
        mouseCb = new CustomCombobox<>(mouseList);

        JLabel keyboardLabel = new JLabel("Bàn phím:");
        ArrayList<String> keyboardList = new ArrayList<>();
        for (Keyboards x : this.keyboardBLL.getKeyboardsByStatus("Trong kho")) {
            keyboardList.add(x.getModel());
        }
        String keyboardName = new String();
        for (Keyboards x : this.keyboardBLL.getAllKeyboards()) {
            if (x.getKeyboardId() == this.computer.getKeyboardId()) {
                keyboardName = x.getModel();
            }
        }
        keyboardList.add(0, "Đang chọn: " + keyboardName);
        keyboardCb = new CustomCombobox<>(keyboardList);

        JLabel monitorLabel = new JLabel("Màn hình:");
        ArrayList<String> monitorList = new ArrayList<>();
        for (Monitors x : this.monitorBLL.getMonitorsByStatus("Trong kho")) {
            monitorList.add(x.getModel());
        }
        String monitorName = new String();
        for (Monitors x : this.monitorBLL.getAllMonitors()) {
            if (x.getMonitorId() == this.computer.getMonitorId()) {
                monitorName = x.getModel();
            }
        }
        monitorList.add(0, "Đang chọn: " + monitorName);
        monitorCb = new CustomCombobox<>(monitorList);

        JLabel headphoneLabel = new JLabel("Tai nghe:");
        ArrayList<String> headphoneList = new ArrayList<>();
        for (Headphones x : this.headphoneBLL.getHeadphonesByStatus("Trong kho")) {
            headphoneList.add(x.getModel());
        }
        String headphoneName = new String();
        for (Headphones x : this.headphoneBLL.getAllHeadphones()) {
            if (x.getHeadphoneId() == this.computer.getHeadphoneId()) {
                headphoneName = x.getModel();
            }
        }
        headphoneList.add(0, "Đang chọn: " + headphoneName);
        headphoneCb = new CustomCombobox<>(headphoneList);

        JLabel romLabel = new JLabel("Rom:");
        ArrayList<String> romList = new ArrayList<>();
        for (Roms x : this.romBLL.getRomsByStatus("Trong kho")) {
            romList.add(x.getModel());
        }
        String romName = new String();
        for (Roms x : this.romBLL.getAllRoms()) {
            if (x.getRomId() == this.computer.getRomId()) {
                romName = x.getModel();
            }
        }
        romList.add(0, "Đang chọn: " + romName);
        romCb = new CustomCombobox<>(romList);

        JLabel roomLabel = new JLabel("Phòng:");
        ArrayList<String> roomList = new ArrayList<>();
        roomCb = new CustomCombobox<>(roomList);

        JLabel statusLabel = new JLabel("Trạng thái:");
        ArrayList<String> statusList = new ArrayList<>();
        statusList.add("Đang chọn: " + this.computer.getStatus());
        for (String x : this.statusList) {
            if (!x.equals(this.computer.getStatus())) {
                statusList.add(x);
            }
        }
        statusCb = new CustomCombobox<>(statusList);

        JPanel separator = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0,0,getWidth(),getHeight());
            }
        };
        separator.setBounds(270,215,2,250);

        CustomButton saveBtn = Utils.Helper.CreateComponent.createBlueButton("Lưu lại");
        saveBtn.setBounds(20,495,100,30);
        saveBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                saveBtn.setBackground(Color.WHITE);
                saveBtn.setForeground(Color.decode("#1E88E5"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveBtn.setBackground(Color.decode("#1E88E5"));
                saveBtn.setForeground(Color.WHITE);
            }
        });

        CustomButton resetBtn = Utils.Helper.CreateComponent.createOrangeButton("Đặt lại");
        resetBtn.setBounds(140,495,100,30);
        resetBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                resetBtn.setBackground(Color.WHITE);
                resetBtn.setForeground(Color.decode("#DD2C00"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetBtn.setBackground(Color.decode("#DD2C00"));
                resetBtn.setForeground(Color.WHITE);
            }
        });

        idLabel.setBounds(20,10,200,20);
        idTextField.setBounds(20,35, 500,30);

        nameLabel.setBounds(20,75,200,20);
        nameTextField.setBounds(20,100,500,30);

        priceLabel.setBounds(20,140,200,20);
        priceTextField.setBounds(20,165,500,30);

        motherboardLabel.setBounds(20,205,200,20);
        motherboardCb.setBounds(20,230,225,30);

        mouseLabel.setBounds(20,270,200,20);
        mouseCb.setBounds(20,295,225,30);

        keyboardLabel.setBounds(20,335,200,20);
        keyboardCb.setBounds(20,360,225,30);

        monitorLabel.setBounds(20,400,200,20);
        monitorCb.setBounds(20,425,225,30);

        headphoneLabel.setBounds(295,205,200,20);
        headphoneCb.setBounds(295,230,225,30);

        romLabel.setBounds(295,270,200,20);
        romCb.setBounds(295,295,225,30);

        roomLabel.setBounds(295,335,200,20);
        roomCb.setBounds(295,360,225,30);

        statusLabel.setBounds(295,400,200,20);
        statusCb.setBounds(295,425,225,30);

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
