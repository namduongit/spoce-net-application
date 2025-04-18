package GUI.Form;

import BLL.*;
import DTO.*;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomPanel;
import GUI.Components.CustomTextField;
import GUI.panels.ComputerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
    private RoomBLL roomBLL;
    private ArrayList<String> statusList;
    private int currentMotherboardId;
    private int currentMouseId;
    private int currentKeyboardId;
    private int currentMonitorId;
    private int currentHeadphoneId;
    private int currentRomId;
    private int currentRoomId;
    private String currentStatus;

    public DetailsComputer(Computers computer) {
        this.computer = computer;
        this.motherboardBLL = new MotherboardBLL();
        this.mouseBLL = new MouseBLL();
        this.keyboardBLL = new KeyboardBLL();
        this.monitorBLL = new MonitorBLL();
        this.headphoneBLL = new HeadphoneBLL();
        this.romBLL = new RomBLL();
        this.roomBLL = new RoomBLL();
        this.currentMotherboardId = computer.getMotherboardId();
        this.currentMouseId = computer.getMouseId() == null ? 0 : computer.getMouseId();
        this.currentKeyboardId = computer.getKeyboardId() == null ? 0 : computer.getKeyboardId();
        this.currentMonitorId = computer.getMonitorId() == null ? 0 : computer.getMonitorId();
        this.currentHeadphoneId = computer.getHeadphoneId() == null ? 0 : computer.getHeadphoneId();
        this.currentRomId = computer.getRomId() == null ? 0 : computer.getRomId();
        this.currentRoomId = computer.getRoomId() == null ? 0 : computer.getRoomId();
        this.currentStatus = computer.getStatus();
        this.statusList = new ArrayList<>(Arrays.asList("Trong kho", "Đang sử dụng", "Đang chờ sử dụng", "Thiếu linh kiện", "Bảo trì", "Hỏng"));
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
        nameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameTextField.getText().equals("Nhập tên máy tính")) {
                    nameTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameTextField.getText().isEmpty()) {
                    nameTextField.setText("Nhập tên máy tính");
                }
            }
        });

        JLabel priceLabel = new JLabel("Giá một giờ chơi:");
        priceTextField = new CustomTextField(this.computer.getPricePerHour()+"");
        priceTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (priceTextField.getText().equals("Nhập giá một giờ chơi")) {
                    priceTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (priceTextField.getText().isEmpty()) {
                    priceTextField.setText("Nhập giá một giờ chơi");
                }
            }
        });

        JLabel motherboardLabel = new JLabel("Bo mạch chủ:");
        ArrayList<String> motherboardList = new ArrayList<>();

        for (Motherboards x : this.motherboardBLL.getMotherboardsByStatus("Trong kho")) {
            motherboardList.add(x.getMotherboardId() + ". " + x.getModel());
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
        motherboardCb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });

        JLabel mouseLabel = new JLabel("Chuột:");
        ArrayList<String> mouseList = new ArrayList<>();
        for (Mouse x : this.mouseBLL.getMousesByStatus("Trong kho")) {
            mouseList.add(x.getMouseId() + ". " + x.getModel());
        }

        if (this.currentMouseId == 0) {
            mouseList.add(0, "Không gắn chuột");
        } else {
            String mouseName = new String();
            for (Mouse x : this.mouseBLL.getAllMouses()) {
                if (x.getMouseId() == this.computer.getMouseId()) {
                    mouseName = x.getModel();
                }
            }
            mouseList.add(0, "Đang chọn: " + mouseName);
            mouseList.add(1, "Gỡ chuột");
        }

        mouseCb = new CustomCombobox<>(mouseList);

        JLabel keyboardLabel = new JLabel("Bàn phím:");
        ArrayList<String> keyboardList = new ArrayList<>();
        for (Keyboards x : this.keyboardBLL.getKeyboardsByStatus("Trong kho")) {
            keyboardList.add(x.getKeyboardId() + ". " + x.getModel());
        }

        if (this.currentKeyboardId == 0) {
            keyboardList.add(0, "Không gắn bàn phím");
        } else {
            String keyboardName = new String();
            for (Keyboards x : this.keyboardBLL.getAllKeyboards()) {
                if (x.getKeyboardId() == this.computer.getKeyboardId()) {
                    keyboardName = x.getModel();
                }
            }
            keyboardList.add(0, "Đang chọn: " + keyboardName);
            keyboardList.add(1, "Gỡ bàn phím");
        }

        keyboardCb = new CustomCombobox<>(keyboardList);

        JLabel monitorLabel = new JLabel("Màn hình:");
        ArrayList<String> monitorList = new ArrayList<>();
        for (Monitors x : this.monitorBLL.getMonitorsByStatus("Trong kho")) {
            monitorList.add(x.getMonitorId() + ". " + x.getModel());
        }

        if (this.currentMonitorId == 0) {
            monitorList.add(0, "Không gắn màn hình");
        } else {
            String monitorName = new String();
            for (Monitors x : this.monitorBLL.getAllMonitors()) {
                if (x.getMonitorId() == this.computer.getMonitorId()) {
                    monitorName = x.getModel();
                }
            }
            monitorList.add(0, "Đang chọn: " + monitorName);
            monitorList.add(1, "Gỡ màn hình");
        }


        monitorCb = new CustomCombobox<>(monitorList);

        JLabel headphoneLabel = new JLabel("Tai nghe:");
        ArrayList<String> headphoneList = new ArrayList<>();
        for (Headphones x : this.headphoneBLL.getHeadphonesByStatus("Trong kho")) {
            headphoneList.add(x.getHeadphoneId() + ". " + x.getModel());
        }

        if (this.currentHeadphoneId == 0) {
            headphoneList.add(0, "Không gắn tai nghe");
        } else {
            String headphoneName = new String();
            for (Headphones x : this.headphoneBLL.getAllHeadphones()) {
                if (x.getHeadphoneId() == this.computer.getHeadphoneId()) {
                    headphoneName = x.getModel();
                }
            }
            headphoneList.add(0, "Đang chọn: " + headphoneName);
            headphoneList.add(1, "Gỡ tai nghe");
        }


        headphoneCb = new CustomCombobox<>(headphoneList);

        JLabel romLabel = new JLabel("Rom:");
        ArrayList<String> romList = new ArrayList<>();
        for (Roms x : this.romBLL.getRomsByStatus("Trong kho")) {
            romList.add(x.getRomId() + ". " + x.getModel());
        }

        if (this.currentRomId == 0) {
            romList.add(0, "Không gắn rom");
        } else {
            String romName = new String();
            for (Roms x : this.romBLL.getAllRoms()) {
                if (x.getRomId() == this.computer.getRomId()) {
                    romName = x.getModel();
                }
            }
            romList.add(0, "Đang chọn: " + romName);
            romList.add(1, "Gỡ rom");
        }


        romCb = new CustomCombobox<>(romList);

        JLabel roomLabel = new JLabel("Phòng:");
        ArrayList<String> roomList = new ArrayList<>();
        for (Rooms x : this.roomBLL.getRoomsByStatus("Trống")) {
            if (x.getRoomId() != this.currentRoomId) {
                roomList.add(x.getRoomId() + ". " + x.getRoomName());
            }
        }

        if (this.currentRoomId == 0) {
            roomList.add(0,"Không có phòng");
        } else {
            String roomName = new String();
            for (Rooms x : this.roomBLL.getAllRooms()) {
                if (x.getRoomId() == this.computer.getRoomId()) {
                    roomName = x.getRoomName();
                }
            }
            roomList.add(0, "Đang chọn: " + roomName);
            roomList.add(1, "Tháo máy khỏi phòng hiện tại");
        }

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
        separator.setBounds(270,215,1,250);

        CustomButton saveBtn = Utils.Helper.CreateComponent.createBlueButton("Lưu lại");
        saveBtn.setBounds(20,495,100,30);
        saveBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DetailsComputer.this.updateDatas();
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
                DetailsComputer.this.resetForm();
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

    private void resetForm() {
        idTextField.setText(this.computer.getComputerId()+"");
        nameTextField.setText(this.computer.getName());
        priceTextField.setText(this.computer.getPricePerHour()+"");
        motherboardCb.setSelectedIndex(0);
        mouseCb.setSelectedIndex(0);
        keyboardCb.setSelectedIndex(0);
        monitorCb.setSelectedIndex(0);
        headphoneCb.setSelectedIndex(0);
        romCb.setSelectedIndex(0);
        roomCb.setSelectedIndex(0);
        statusCb.setSelectedIndex(0);
    }

    public void updateDatas() {
        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put("computer_id", this.idTextField.getText());
        newValues.put("name", this.nameTextField.getText());
        newValues.put("price_per_hour", Integer.parseInt(this.priceTextField.getText()));

        if (!this.motherboardCb.getSelectedItem().toString().contains("Đang chọn:")) {
            int newMotherboardId = this.getIdFromString(this.motherboardCb.getSelectedItem().toString());
            newValues.put("motherboard_id", newMotherboardId);


            HashMap<String, Object> oldMotherboardValues = new HashMap<>();
            oldMotherboardValues.put("status", "Trong kho");
            this.motherboardBLL.updateMotherboardById(this.currentMotherboardId, oldMotherboardValues);


            HashMap<String, Object> newMotherboardValues = new HashMap<>();
            oldMotherboardValues.put("status", "Đang sử dụng");
            this.motherboardBLL.updateMotherboardById(newMotherboardId, oldMotherboardValues);
        }

        if (!this.mouseCb.getSelectedItem().toString().contains("Đang chọn:") && !this.mouseCb.getSelectedItem().toString().contains("Không gắn chuột")) {

            HashMap<String, Object> oldMouseValues = new HashMap<>();
            oldMouseValues.put("status", "Trong kho");
            this.mouseBLL.updateMouseById(this.currentMouseId, oldMouseValues);

            if (this.mouseCb.getSelectedItem().toString().equals("Gỡ chuột")) {

                newValues.put("mouse_id", null);

            } else {

                int newMouseId = this.getIdFromString(this.mouseCb.getSelectedItem().toString());
                newValues.put("mouse_id", newMouseId);

                HashMap<String, Object> newMouseValues = new HashMap<>();
                newMouseValues.put("status", "Đang sử dụng");
                this.mouseBLL.updateMouseById(newMouseId, newMouseValues);

            }
        }

        if (!this.keyboardCb.getSelectedItem().toString().contains("Đang chọn:") && !this.keyboardCb.getSelectedItem().toString().contains("Không gắn bàn phím")) {

            HashMap<String, Object> oldKeyboardValues = new HashMap<>();
            oldKeyboardValues.put("status", "Trong kho");
            this.keyboardBLL.updateKeyboardById(this.currentKeyboardId, oldKeyboardValues);

            if (this.keyboardCb.getSelectedItem().toString().equals("Gỡ bàn phím")) {

                newValues.put("keyboard_id", null);

            } else {
                int newKeyboardId = this.getIdFromString(this.keyboardCb.getSelectedItem().toString());
                newValues.put("keyboard_id", newKeyboardId);

                HashMap<String, Object> newKeyboardValues = new HashMap<>();
                newKeyboardValues.put("status", "Đang sử dụng");
                this.keyboardBLL.updateKeyboardById(newKeyboardId, newKeyboardValues);
            }
        }

        if (!this.monitorCb.getSelectedItem().toString().contains("Đang chọn:") && !this.monitorCb.getSelectedItem().toString().contains("Không gắn màn hình")) {

            HashMap<String, Object> oldMonitorValues = new HashMap<>();
            oldMonitorValues.put("status", "Trong kho");
            this.monitorBLL.updateMonitorById(this.currentMonitorId, oldMonitorValues);

            if (this.monitorCb.getSelectedItem().toString().equals("Gỡ màn hình")) {
                newValues.put("monitor_id", null);
            } else {
                int newMonitorId = this.getIdFromString(this.monitorCb.getSelectedItem().toString());
                newValues.put("monitor_id", newMonitorId);

                HashMap<String, Object> newMonitorValues = new HashMap<>();
                newMonitorValues.put("status", "Đang sử dụng");
                this.monitorBLL.updateMonitorById(newMonitorId, newMonitorValues);
            }
        }

        if (!this.headphoneCb.getSelectedItem().toString().contains("Đang chọn:") && !this.headphoneCb.getSelectedItem().toString().contains("Không gắn tai nghe")) {

            HashMap<String, Object> oldHeadphoneValues = new HashMap<>();
            oldHeadphoneValues.put("status", "Trong kho");
            this.headphoneBLL.updateHeadphoneById(this.currentHeadphoneId, oldHeadphoneValues);

            if (this.headphoneCb.getSelectedItem().toString().equals("Gỡ tai nghe")) {
                newValues.put("headphone_id", null);
            } else {
                int newHeadphoneId = this.getIdFromString(this.headphoneCb.getSelectedItem().toString());
                newValues.put("headphone_id", newHeadphoneId);

                HashMap<String, Object> newHeadphoneValues = new HashMap<>();
                newHeadphoneValues.put("status", "Đang sử dụng");
                this.headphoneBLL.updateHeadphoneById(newHeadphoneId, newHeadphoneValues);
            }
        }

        if (!this.romCb.getSelectedItem().toString().contains("Đang chọn:") && !this.romCb.getSelectedItem().toString().contains("Không gắn rom")) {

            HashMap<String, Object> oldRomValues = new HashMap<>();
            oldRomValues.put("status", "Trong kho");
            this.romBLL.updateRomById(this.currentRomId, oldRomValues);

            if (this.romCb.getSelectedItem().toString().equals("Gỡ rom")) {
                newValues.put("rom_id", null);
            } else {
                int newRomId = this.getIdFromString(this.romCb.getSelectedItem().toString());
                newValues.put("rom_id", newRomId);

                HashMap<String, Object> newRomValues = new HashMap<>();
                newRomValues.put("status", "Đang sử dụng");
                this.romBLL.updateRomById(newRomId, newRomValues);
            }
        }

        if (!this.roomCb.getSelectedItem().toString().contains("Đang chọn:") && !this.roomCb.getSelectedItem().toString().contains("Không có phòng")) {
            if (this.roomCb.getSelectedItem().toString().equals("Tháo máy khỏi phòng hiện tại")) {
                newValues.put("room_id", null);
            } else {
                newValues.put("room_id", this.getIdFromString(this.roomCb.getSelectedItem().toString()));
            }
        }

        if (!this.statusCb.getSelectedItem().toString().contains("Đang chọn:")) {
            newValues.put("status", this.statusCb.getSelectedItem().toString());
        }

        boolean result = new ComputerBLL().updateComputerById(this.computer.getComputerId(), newValues);
        if (result) {
            JOptionPane.showMessageDialog(null, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getIdFromString(String str) {
        String[] arr = str.split("\\.");
        return Integer.parseInt(arr[0]);
    }

    public static void main(String[] args) {
        new DetailsComputer(new Computers());
    }
}
