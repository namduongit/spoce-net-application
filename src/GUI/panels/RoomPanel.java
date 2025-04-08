package GUI.panels;

import BLL.RoomBLL;
import DTO.Rooms;
import GUI.Components.*;
import Utils.Helper.AdjustTableWidth;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class RoomPanel extends JPanel{
    private CustomPanel titlePanel;
    private CustomPanel controlPanel;
    private CustomPanel dataPanel;
    private CustomTextField searchTextField;
    private CustomCombobox roomTypeCombobox;
    private JLabel selectedRoom;
    private DefaultTableModel model;
    private CustomTable roomTable;

    private ArrayList<Rooms> roomList;
    private String[] roomColumnList;

    private RoomBLL roomBLL;


    public RoomPanel() {
        this.roomBLL = new RoomBLL();

        this.roomList = this.roomBLL.getAllRooms();
        this.roomColumnList = new String[]{
                "ID",
                "Tên phòng",
                "Loại phòng",
                "Số máy tối da",
                "Tình trạng"
        };

        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        this.titlePanel = this.createTitlePanel();
        this.controlPanel = this.createControlPanel();
        this.dataPanel = this.createDataPanel();

        this.titlePanel.setBounds( 10, 0, 1080, 100);
        this.controlPanel.setBounds(10, 110, 1080, 80);
        this.dataPanel.setBounds(10,200,1080,518);

        this.add(this.titlePanel);
        this.add(this.controlPanel);
        this.add(this.dataPanel);

    }

    private CustomPanel createDataPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        Object[][] data = this.createDataForRoomTable(this.roomList);
        this.model = new DefaultTableModel(data, this.roomColumnList);

        this.roomTable = new CustomTable(this.model);
        this.roomTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(this.roomTable);
        this.roomTable.getColumnModel()
                        .getColumn(0)
                        .setPreferredWidth(90);

        CustomScrollPane scrollPane = new CustomScrollPane(this.roomTable);
        scrollPane.setBounds(0,0,1080,450);


        CustomButton addButton = new CustomButton("Thêm");
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(Color.decode("#388E3C"));
        addButton.setBorderColor(Color.decode("#388E3C"));
        addButton.setBorderSize(3);
        addButton.setBounds(30, 466, 100, 35);
        addButton.addMouseListener(new MouseListener() {
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
                addButton.setForeground(Color.decode("#388E3C"));
                addButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addButton.setForeground(Color.WHITE);
                addButton.setBackground(Color.decode("#388E3C"));
            }
        });

        CustomButton modifyButton = new CustomButton("Thay đổi");
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setBackground(Color.PINK);
        modifyButton.setBorderColor(Color.PINK);
        modifyButton.setBorderSize(3);
        modifyButton.setBounds(150, 466, 100, 35);
        modifyButton.addMouseListener(new MouseListener() {
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
                modifyButton.setForeground(Color.PINK);
                modifyButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                modifyButton.setForeground(Color.WHITE);
                modifyButton.setBackground(Color.PINK);
            }
        });

        panel.add(scrollPane);
        panel.add(addButton);
        panel.add(modifyButton);

        return panel;
    }

    private CustomPanel createTitlePanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("QUẢN LÝ PHÒNG CHƠI");
        title.setFont(
                new Font("Sans-serif", Font.PLAIN, 30)
        );
        title.setBounds(400, 25, 500, 50);

        panel.add(title);

        return panel;
    }

    private CustomPanel createControlPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(20, 25, 80, 30);

        searchTextField = new CustomTextField("Nhập thông tin tìm kiếm");
        searchTextField.setBounds(90, 23, 200, 35);
        searchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchTextField.getText().equals("Nhập thông tin tìm kiếm")) {
                    searchTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchTextField.getText().equals("")) {
                    searchTextField.setText("Nhập thông tin tìm kiếm");
                }
            }
        });


        JLabel filterLabel = new JLabel("Loại phòng:");
        filterLabel.setBounds(330, 25, 70, 30);


        String[] roomTypeList = {
                "Tất cả",
                "Phòng thường",
                "Phòng gaming",
                "Phòng thi đấu"
        };

        roomTypeCombobox = new CustomCombobox(roomTypeList);
        roomTypeCombobox.setBounds(405, 23, 150, 35);

        CustomButton filterButton = new CustomButton("Lọc");
        filterButton.setForeground(Color.WHITE);
        filterButton.setBackground(Color.decode("#03A9F4"));
        filterButton.setBorderColor(Color.decode("#03A9F4"));
        filterButton.setBorderSize(3);
        filterButton.setBounds(595, 23, 100, 35);
        filterButton.addMouseListener(new MouseListener() {
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
                filterButton.setBackground(Color.WHITE);
                filterButton.setForeground(Color.decode("#03A9F4"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                filterButton.setBackground(Color.decode("#03A9F4"));
                filterButton.setForeground(Color.WHITE);
            }
        });

        CustomButton resetButton = new CustomButton("Đặt lại");
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(Color.RED);
        resetButton.setBorderColor(Color.RED);
        resetButton.setBorderSize(3);
        resetButton.setBounds(715, 23, 100, 35);
        resetButton.addMouseListener(new MouseListener() {
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
                resetButton.setForeground(Color.RED);
                resetButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetButton.setBackground(Color.RED);
                resetButton.setForeground(Color.WHITE);
            }
        });


        selectedRoom = new JLabel("Đang chọn: NULL");
        selectedRoom.setBounds(845,29,300,20);
        selectedRoom.setFont(
                new Font("Sans-serif", Font.BOLD, 12)
        );

        panel.add(searchLabel);
        panel.add(searchTextField);
        panel.add(filterLabel);
        panel.add(roomTypeCombobox);
        panel.add(filterButton);
        panel.add(resetButton);
        panel.add(selectedRoom);

        return panel;
    }

    private Object[][] createDataForRoomTable(ArrayList<Rooms> roomList) {
        Object[][] data = new Object[roomList.size()][5];

        for (int i=0; i<roomList.size(); i++) {
            Rooms room = roomList.get(i);
            data[i][0] = room.getRoomId();
            data[i][1] = room.getRoomName();
            data[i][2] = room.getType();
            data[i][3] = room.getMaxComputers();
            data[i][4] = room.getStatus();
        }

        return data;
    }
}
