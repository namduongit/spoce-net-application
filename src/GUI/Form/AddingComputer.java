package GUI.Form;

import BLL.*;
import DTO.*;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@SuppressWarnings("rawtypes")
public class AddingComputer extends JFrame {
    private JPanel content;
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
    private ComputerBLL computerBLL;

    public AddingComputer() {
        this.motherboardBLL = new MotherboardBLL();
        this.mouseBLL = new MouseBLL();
        this.keyboardBLL = new KeyboardBLL();
        this.monitorBLL = new MonitorBLL();
        this.headphoneBLL = new HeadphoneBLL();
        this.romBLL = new RomBLL();
        this.roomBLL = new RoomBLL();
        this.computerBLL = new ComputerBLL();
        this.statusList = new ArrayList<>(Arrays.asList("Trong kho", "Đang sử dụng", "Thiếu linh kiện", "Bảo trì", "Hỏng"));
        this.initComponents();
    }

    private void initComponents() {
        this.setTitle("Thêm máy tính");
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

        // Tên máy tính
        JLabel nameLabel = new JLabel("Tên máy tính:");
        nameTextField = new CustomTextField("Nhập tên máy tính");
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

        // Giá một giờ chơi
        JLabel priceLabel = new JLabel("Giá một giờ chơi:");
        priceTextField = new CustomTextField();
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

        // Bo mạch chủ
        JLabel motherboardLabel = new JLabel("Bo mạch chủ:");
        ArrayList<String> motherboardList = new ArrayList<>();
        for (Motherboards x : this.motherboardBLL.getMotherboardsByStatus("Trong kho")) {
            motherboardList.add(x.getMotherboardId() + ". " + x.getModel());
        }

        motherboardList.add(0, "Chọn linh kiện");
        motherboardCb = new CustomCombobox<>(motherboardList);

        // Chuột
        JLabel mouseLabel = new JLabel("Chuột:");
        ArrayList<String> mouseList = new ArrayList<>();
        for (Mouse x : this.mouseBLL.getMousesByStatus("Trong kho")) {
            mouseList.add(x.getMouseId() + ". " + x.getModel());
        }

        mouseList.add(0, "Chọn linh kiện");
        mouseCb = new CustomCombobox<>(mouseList);

        // Bàn phím
        JLabel keyboardLabel = new JLabel("Bàn phím:");
        ArrayList<String> keyboardList = new ArrayList<>();
        for (Keyboards x : this.keyboardBLL.getKeyboardsByStatus("Trong kho")) {
            keyboardList.add(x.getKeyboardId() + ". " + x.getModel());
        }

        keyboardList.add(0, "Chọn linh kiện");
        keyboardCb = new CustomCombobox<>(keyboardList);

        // Màn hình
        JLabel monitorLabel = new JLabel("Màn hình:");
        ArrayList<String> monitorList = new ArrayList<>();
        for (Monitors x : this.monitorBLL.getMonitorsByStatus("Trong kho")) {
            monitorList.add(x.getMonitorId() + ". " + x.getModel());
        }

        monitorList.add(0, "Chọn linh kiện");
        monitorCb = new CustomCombobox<>(monitorList);

        // Tai nghe
        JLabel headphoneLabel = new JLabel("Tai nghe:");
        ArrayList<String> headphoneList = new ArrayList<>();
        for (Headphones x : this.headphoneBLL.getHeadphonesByStatus("Trong kho")) {
            headphoneList.add(x.getHeadphoneId() + ". " + x.getModel());
        }

        headphoneList.add(0, "Chọn linh kiện");
        headphoneCb = new CustomCombobox<>(headphoneList);

        // Rom
        JLabel romLabel = new JLabel("Rom:");
        ArrayList<String> romList = new ArrayList<>();
        for (Roms x : this.romBLL.getRomsByStatus("Trong kho")) {
            romList.add(x.getRomId() + ". " + x.getModel());
        }

        romList.add(0, "Chọn linh kiện");
        romCb = new CustomCombobox<>(romList);

        // Phòng
        JLabel roomLabel = new JLabel("Phòng:");
        ArrayList<String> roomList = new ArrayList<>();
        for (Rooms x : this.roomBLL.getRoomsByStatus("Trống")) {
            roomList.add(x.getRoomId() + ". " + x.getRoomName());
        }

        roomList.add(0, "Chọn phòng");
        roomCb = new CustomCombobox<>(roomList);

        // Tình trạng
        JLabel statusLabel = new JLabel("Trạng thái:");
        ArrayList<String> statusList = new ArrayList<>();
        statusList.add("Chọn trạng thái");
        statusList.addAll(this.statusList);
        statusCb = new CustomCombobox<>(statusList);

        // Đường kẻ màu đen
        JPanel separator = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0,0,getWidth(),getHeight());
            }
        };
        separator.setBounds(270,155,1,250);

        // Nút lưu
        CustomButton saveBtn = Utils.Helper.CreateComponent.createBlueButton("Lưu lại");
        saveBtn.setBounds(20,495,100,30);
        saveBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddingComputer.this.insertDatas();
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

        // Nút đặt lại
        CustomButton resetBtn = Utils.Helper.CreateComponent.createOrangeButton("Đặt lại");
        resetBtn.setBounds(140,495,100,30);
        resetBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddingComputer.this.resetForm();
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

        nameLabel.setBounds(20,10,200,20);
        nameTextField.setBounds(20,35,500,30);

        priceLabel.setBounds(20,75,200,20);
        priceTextField.setBounds(20,100,500,30);

        motherboardLabel.setBounds(20,145,200,20);
        motherboardCb.setBounds(20,170,225,30);

        mouseLabel.setBounds(20,210,200,20);
        mouseCb.setBounds(20,235,225,30);

        keyboardLabel.setBounds(20,275,200,20);
        keyboardCb.setBounds(20,300,225,30);

        monitorLabel.setBounds(20,340,200,20);
        monitorCb.setBounds(20,365,225,30);

        headphoneLabel.setBounds(295,145,200,20);
        headphoneCb.setBounds(295,170,225,30);

        romLabel.setBounds(295,210,200,20);
        romCb.setBounds(295,235,225,30);

        roomLabel.setBounds(295,275,200,20);
        roomCb.setBounds(295,300,225,30);

        statusLabel.setBounds(295,340,200,20);
        statusCb.setBounds(295,365,225,30);

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

    // Hàm đẩy dữ liệu được nhập lên database
    private void insertDatas() {
        ArrayList<Object> values = new ArrayList<>();

        if (this.nameTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Tên máy tính không được rỗng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        } else {
            values.add(this.nameTextField.getText());
        }

        if (this.priceTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Giá chơi máy tính không được rỗng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        } else {
            try {
                int price = Integer.parseInt(this.priceTextField.getText().trim());
                if (price < 0) {
                    throw new NumberFormatException();
                }
                values.add(price);
            } catch (NumberFormatException e) {

            }
        }

        if (this.motherboardCb.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "Bo mạch chủ không được rỗng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        } else {
            int id = getIdFromString(this.motherboardCb
                                            .getSelectedItem()
                                            .toString()
            );

            values.add(id);

            HashMap<String, Object> statusValue = new HashMap<>();
            statusValue.put("status", "Đang sử dụng");
            this.motherboardBLL.updateMotherboardById(id, statusValue);
        }

        if (this.mouseCb.getSelectedIndex() == 0) {
            values.add(null);
        } else {
            int id = getIdFromString(this.mouseCb
                                        .getSelectedItem()
                                        .toString()
            );

            values.add(id);

            HashMap<String, Object> statusValue = new HashMap<>();
            statusValue.put("status", "Đang sử dụng");
            this.mouseBLL.updateMouseById(id, statusValue);
        }

        if (this.keyboardCb.getSelectedIndex() == 0) {
            values.add(null);
        } else {
            int id = getIdFromString(this.keyboardCb
                                        .getSelectedItem()
                                        .toString()
            );

            values.add(id);

            HashMap<String, Object> statusValue = new HashMap<>();
            statusValue.put("status", "Đang sử dụng");
            this.keyboardBLL.updateKeyboardById(id, statusValue);
        }

        if (this.monitorCb.getSelectedIndex() == 0) {
            values.add(null);
        } else {
            int id = getIdFromString(this.monitorCb
                                        .getSelectedItem()
                                        .toString()
            );

            values.add(id);

            HashMap<String, Object> statusValue = new HashMap<>();
            statusValue.put("status", "Đang sử dụng");
            this.monitorBLL.updateMonitorById(id, statusValue);
        }

        if (this.headphoneCb.getSelectedIndex() == 0) {
            values.add(null);
        } else {
            int id = getIdFromString(this.headphoneCb
                                        .getSelectedItem()
                                        .toString()
            );

            values.add(id);

            HashMap<String, Object> statusValue = new HashMap<>();
            statusValue.put("status", "Đang sử dụng");
            this.headphoneBLL.updateHeadphoneById(id, statusValue);
        }

        if (this.romCb.getSelectedIndex() == 0) {
            values.add(null);
        } else {
            int id = getIdFromString(this.romCb
                                        .getSelectedItem()
                                        .toString()
            );

            values.add(id);

            HashMap<String, Object> statusValue = new HashMap<>();
            statusValue.put("status", "Đang sử dụng");
            this.romBLL.updateRomById(id, statusValue);
        }

        if (this.roomCb.getSelectedIndex() == 0) {
            values.add(null);
        } else {
            values.add(
                    getIdFromString(this.roomCb
                                        .getSelectedItem()
                                        .toString()
                    )
            );
        }

        if (this.statusCb.getSelectedIndex() == 0) {
            values.add("Trong kho");
        } else {
            values.add(
                this.statusCb
                    .getSelectedItem()
                    .toString()
            );
        }

        boolean result = this.computerBLL.insertComputer(values);

        if (result) {
            JOptionPane.showMessageDialog(
                    null,
                    "Thêm máy tính thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Thêm máy tính thất bại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void resetForm() {
        this.nameTextField.setText("Nhập tên máy tính");
        this.priceTextField.setText("Nhập giá một giờ chơi");
        this.motherboardCb.setSelectedIndex(0);
        this.mouseCb.setSelectedIndex(0);
        this.keyboardCb.setSelectedIndex(0);
        this.monitorCb.setSelectedIndex(0);
        this.headphoneCb.setSelectedIndex(0);
        this.romCb.setSelectedIndex(0);
        this.roomCb.setSelectedIndex(0);
        this.statusCb.setSelectedIndex(0);
    }

    public int getIdFromString(String str) {
        String[] arr = str.split("\\.");
        return Integer.parseInt(arr[0]);
    }
}
