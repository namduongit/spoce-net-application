package GUI.Form;

import BLL.RoomBLL;
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


public class AddingRoom extends JFrame {
    private JPanel content;
    private CustomTextField nameTextField;
    private CustomTextField numberOfComputersTextField;
    private CustomCombobox roomTypeCombobox;
    private CustomCombobox roomStatusCombobox;

    private RoomBLL roomBLL;

    public AddingRoom() {
        this.roomBLL = new RoomBLL();

        this.setTitle("Thêm phòng chơi");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(550,600);
        this.setResizable(false);

        this.content = this.createContent();

        this.add(this.content);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel createContent() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Tên phòng:");
        nameLabel.setBounds(20,10,200,20);

        nameTextField = new CustomTextField("Nhập tên phòng chơi");
        nameTextField.setBounds(20,35,500,30);
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
        numberOfComputersLabel.setBounds(20,75,200,20);

        numberOfComputersTextField = new CustomTextField("Nhập số máy tính tối đa của phòng chơi");
        numberOfComputersTextField.setBounds(20,100,500,30);
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
        roomTypeLabel.setBounds(20,140,200,20);

        String[] typeList = {
                "Chọn loại phòng",
                "Phòng thường",
                "Phòng gaming",
                "Phòng thi đấu"
        };
        roomTypeCombobox = new CustomCombobox(typeList);
        roomTypeCombobox.setBounds(20,165,500,30);

        JLabel roomStatusLabel = new JLabel("Trạng thái:");
        roomStatusLabel.setBounds(20,205,200,20);

        String[] statusList = {
                "Chọn trạng thái",
                "Trống",
                "Đang hoạt động",
                "Bảo trì"
        };
        roomStatusCombobox = new CustomCombobox(statusList);
        roomStatusCombobox.setBounds(20,230,500,30);

        CustomButton saveButton = Utils.Helper.CreateComponent.createBlueButton("Lưu lại");
        saveButton.setBounds(20,495,100,30);
        saveButton.setBorderSize(2);
        saveButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddingRoom.this.updateDatas();
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
                AddingRoom.this.resetForm();
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

        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(numberOfComputersLabel);
        panel.add(numberOfComputersTextField);
        panel.add(roomTypeLabel);
        panel.add(roomTypeCombobox);
        panel.add(roomStatusLabel);
        panel.add(roomStatusCombobox);
        panel.add(saveButton);
        panel.add(resetButton);

        return panel;
    }

    private void updateDatas() {
        ArrayList<Object> values = new ArrayList<>();

        if (this.nameTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Tên phòng không được rỗng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }
        values.add(this.nameTextField.getText().trim());

        try {
            int numberOfComputers = Integer.parseInt(this.numberOfComputersTextField.getText().trim());
            if (numberOfComputers < 0) {
                throw new NumberFormatException();
            }
            values.add(numberOfComputers);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Giá trị không đúng định dạng là số nguyên không âm!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (this.roomTypeCombobox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "Loại phòng không được rỗng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        } else {
            values.add(this.roomTypeCombobox.getSelectedItem().toString());
        }

        if (this.roomStatusCombobox.getSelectedIndex() == 0) {
            values.add("Trống");
        } else {
            values.add(this.roomStatusCombobox.getSelectedItem().toString());
        }

        boolean result = this.roomBLL.insertRoom(values);
        if (result) {
            JOptionPane.showMessageDialog(
                    null,
                    "Thêm phòng chơi thành công!",
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
        this.nameTextField.setText("Nhập tên phòng chơi");
        this.numberOfComputersTextField.setText("Nhập số máy tính tối đa của phòng chơi");
        this.roomTypeCombobox.setSelectedIndex(0);
        this.roomStatusCombobox.setSelectedIndex(0);
    }
}
