package GUI.Form;

import BLL.RoomBLL;
import DTO.Rooms;
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
import java.util.HashMap;

@SuppressWarnings("rawtypes")
public class DetailsRoom extends JFrame {
    private JPanel content;
    private CustomCombobox roomStatusCombobox;
    private CustomTextField idTextField;
    private CustomTextField nameTextField;
    private CustomTextField numberOfComputersTextField;
    private CustomCombobox roomTypeCombobox;

    private RoomBLL roomBLL;
    private Rooms room;
    private String[] typeList;
    private String[] statusList;

    public DetailsRoom(int roomId) {
        this.roomBLL = new RoomBLL();
        this.room = this.roomBLL.getRoomById(roomId);
        this.typeList = new String[]{
                "Phòng thường",
                "Phòng gaming",
                "Phòng thi đấu"
        };
        this.statusList = new String[]{
                "Trống",
                "Đang hoạt động",
                "Bảo trì"
        };
        this.setTitle("Thông tin chi tiết phòng chơi");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(550,600);
        this.setResizable(false);

        this.content = this.createContent();

        this.add(this.content);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private JPanel createContent() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel idLabel = new JLabel("Mã phòng:");
        idLabel.setBounds(20,10,200,20);

        idTextField = new CustomTextField(this.room.getRoomId()+"");
        idTextField.setBounds(20,35,500,30);
        idTextField.setEditable(false);

        JLabel nameLabel = new JLabel("Tên phòng:");
        nameLabel.setBounds(20,75,200,20);

        nameTextField = new CustomTextField(this.room.getRoomName());
        nameTextField.setBounds(20,100,500,30);
        nameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameTextField.getText().equals("Nhập tên phòng chơi")) {
                    nameTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameTextField.getText().isEmpty()) {
                    nameTextField.setText("Nhập tên phòng chơi");
                }
            }
        });

        JLabel numberOfComputersLabel = new JLabel("Số máy tính tối đa của phòng:");
        numberOfComputersLabel.setBounds(20,140,200,20);

        numberOfComputersTextField = new CustomTextField(this.room.getMaxComputers()+"");
        numberOfComputersTextField.setBounds(20,165,500,30);
        numberOfComputersTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (numberOfComputersTextField.getText().equals("Nhập số máy tính tối đa của phòng chơi")) {
                    numberOfComputersTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (numberOfComputersTextField.getText().isEmpty()) {
                    numberOfComputersTextField.setText("Nhập số máy tính tối đa của phòng chơi");
                }
            }
        });

        JLabel roomTypeLabel = new JLabel("Loại phòng:");
        roomTypeLabel.setBounds(20,205,200,20);

        ArrayList<String> roomTypeList = new ArrayList<>();
        roomTypeList.add("Đang chọn: " + this.room.getType());
        for (String x : this.typeList) {
            if (!x.equals(this.room.getType())) {
                roomTypeList.add(x);
            }
        }
        roomTypeCombobox = new CustomCombobox(roomTypeList);
        roomTypeCombobox.setBounds(20,230,500,30);

        JLabel statusLabel = new JLabel("Trạng thái:");
        statusLabel.setBounds(20,270,200,20);

        ArrayList<String> roomStatusList = new ArrayList<>();
        roomStatusList.add("Đang chọn: " + this.room.getStatus());
        for (String x : this.statusList) {
            if (!x.equals(this.room.getStatus())) {
                roomStatusList.add(x);
            }
        }
        roomStatusCombobox = new CustomCombobox(roomStatusList);
        roomStatusCombobox.setBounds(20,295,500,30);

        CustomButton saveButton = Utils.Helper.CreateComponent.createBlueButton("Lưu lại");
        saveButton.setBounds(20,495,100,30);
        saveButton.setBorderSize(2);
        saveButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DetailsRoom.this.updateDatas();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(Color.WHITE);
                saveButton.setForeground(Color.decode("#1E88E5"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setBackground(Color.decode("#1E88E5"));
                saveButton.setForeground(Color.WHITE);
            }
        });

        CustomButton resetButton = Utils.Helper.CreateComponent.createOrangeButton("Đặt lại");
        resetButton.setBounds(140,495,100,30);
        resetButton.setBorderSize(2);
        resetButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DetailsRoom.this.resetForm();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                resetButton.setBackground(Color.WHITE);
                resetButton.setForeground(Color.decode("#DD2C00"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetButton.setBackground(Color.decode("#DD2C00"));
                resetButton.setForeground(Color.WHITE);
            }
        });

        panel.add(idLabel);
        panel.add(idTextField);
        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(numberOfComputersLabel);
        panel.add(numberOfComputersTextField);
        panel.add(roomTypeLabel);
        panel.add(roomTypeCombobox);
        panel.add(statusLabel);
        panel.add(roomStatusCombobox);
        panel.add(saveButton);
        panel.add(resetButton);

        return panel;
    }

    private void updateDatas() {
        HashMap<String, Object> newValues = new HashMap<>();

        if (this.nameTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Tên phòng không được rỗng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        newValues.put("room_name", this.nameTextField.getText().trim());

        try {
            int maxComputer = Integer.parseInt(this.numberOfComputersTextField.getText().trim());
            if (maxComputer < 0) {
                throw new NumberFormatException();
            }
            newValues.put("max_computers", maxComputer);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Giá trị không đúng định dạng là số nguyên không âm!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (this.roomTypeCombobox.getSelectedIndex() != 0) {
            newValues.put("type", this.roomTypeCombobox.getSelectedItem().toString());
        }

        if (this.roomStatusCombobox.getSelectedIndex() != 0) {
            newValues.put("status", this.roomStatusCombobox.getSelectedItem().toString());
        }

        boolean result = this.roomBLL.updateRoomById(this.room.getRoomId(), newValues);
        if (result) {
            JOptionPane.showMessageDialog(
                    null,
                    "Cập nhật thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Cập nhật thất bại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void resetForm() {
        this.nameTextField.setText(this.room.getRoomName());
        this.numberOfComputersTextField.setText(this.room.getMaxComputers()+"");
        this.roomTypeCombobox.setSelectedIndex(0);
        this.roomStatusCombobox.setSelectedIndex(0);
    }
}
