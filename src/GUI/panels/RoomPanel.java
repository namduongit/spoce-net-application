package GUI.panels;

import BLL.RoomBLL;
import DTO.Rooms;
import GUI.Components.*;
import GUI.Form.AddingRoom;
import GUI.Form.DetailsRoom;
import Utils.Helper.AdjustTableWidth;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "FieldMayBeFinal", "rawtypes"})
public class RoomPanel extends JPanel{
    private CustomPanel titlePanel;
    private CustomPanel controlPanel;
    private CustomPanel dataPanel;
    private CustomTextField searchTextField;
    private CustomCombobox roomTypeCombobox;
    private CustomCombobox statusCombobox;
    private JLabel selectedRoom;
    private DefaultTableModel model;
    private CustomTable roomTable;

    private ArrayList<Rooms> roomList;
    private String[] roomColumnList;

    private RoomBLL roomBLL;
    private DefaultTableCellRenderer tableCellRenderer;


    public RoomPanel() {
        this.roomBLL = new RoomBLL();
        this.tableCellRenderer = new DefaultTableCellRenderer();
        this.tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);

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
        this.controlPanel.setBounds(10, 110, 1080, 95);
        this.dataPanel.setBounds(10,215,1080,513);

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
        this.roomTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (roomTable.getSelectedRow() != -1) {
                        RoomPanel.this.selectedRoom.setText("Đang chọn: " + roomTable.getValueAt(roomTable.getSelectedRow(), 1).toString());
                    }
                }
            }

        });

        CustomScrollPane scrollPane = new CustomScrollPane(this.roomTable);
        scrollPane.setBounds(0,0,1080, 513);

        panel.add(scrollPane);

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

        Image addImage = new ImageIcon(
                System.getProperty("user.dir") + "/src/Assets/Icon/add.png"
        ).getImage()
         .getScaledInstance(30,30,Image.SCALE_SMOOTH);
        CustomDesignButton addButton = new CustomDesignButton("Thêm", new ImageIcon(addImage));
        addButton.setForeground(Color.BLACK);
        addButton.setBackground(Color.WHITE);
        addButton.setBorderColor(Color.BLACK);
        addButton.setBorderSize(3);
        addButton.setBounds(20, 10, 75, 70);
        addButton.addActionListener(e -> new AddingRoom());

        Image modifyImage = new ImageIcon(
                System.getProperty("user.dir") + "/src/Assets/Icon/pencil.png"
        ).getImage()
         .getScaledInstance(30,30,Image.SCALE_SMOOTH);
        CustomDesignButton modifyButton = new CustomDesignButton("Sửa", new ImageIcon(modifyImage));
        modifyButton.setForeground(Color.BLACK);
        modifyButton.setBackground(Color.WHITE);
        modifyButton.setBorderColor(Color.BLACK);
        modifyButton.setBorderSize(3);
        modifyButton.setBounds(110, 10, 75, 70);
        modifyButton.addActionListener(e -> {
            if (this.roomTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(
                        null,
                        "Bạn chưa chọn phòng chơi",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                new DetailsRoom((int)this.roomTable.getValueAt(this.roomTable.getSelectedRow(),0));
            }
        });

        Image deleteImage = new ImageIcon(
                System.getProperty("user.dir") + "/src/Assets/Icon/delete.png"
        ).getImage()
         .getScaledInstance(30,30,Image.SCALE_SMOOTH);
        CustomDesignButton deleteButton = new CustomDesignButton("Xóa", new ImageIcon(deleteImage));
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setBorderColor(Color.BLACK);
        deleteButton.setBorderSize(3);
        deleteButton.setBounds(200,10,75,70);
        deleteButton.addActionListener(e -> {
            if (this.roomTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(
                        null,
                        "Bạn chưa chọn phòng chơi",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                int result = JOptionPane.showConfirmDialog(
                                null,
                                "Bạn chắc chắn muốn xóa phòng chơi?",
                                "Thông báo",
                                JOptionPane.YES_NO_OPTION
                             );

                if (result == JOptionPane.YES_OPTION) {
                    this.roomBLL.deleteRoomById((int)this.roomTable.getValueAt(this.roomTable.getSelectedRow(),0));
                    this.resetTable();
                }
            }
        });

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(310, 10, 80, 30);

        searchTextField = new CustomTextField("Nhập thông tin tìm kiếm");
        searchTextField.setBounds(310, 38, 200, 35);
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
        filterLabel.setBounds(525, 10, 70, 30);


        String[] roomTypeList = {
                "Tất cả",
                "Phòng thường",
                "Phòng gaming",
                "Phòng thi đấu"
        };

        roomTypeCombobox = new CustomCombobox(roomTypeList);
        roomTypeCombobox.setBounds(525, 38, 150, 35);

        JLabel statusLabel = new JLabel("Trạng thái:");
        statusLabel.setBounds(690,10,70,30);

        String[] statusList = {
                "Tất cả",
                "Trống",
                "Đang hoạt động",
                "Bảo trì"
        };

        statusCombobox = new CustomCombobox(statusList);
        statusCombobox.setBounds(690,38,150,35);

        CustomButton filterButton = new CustomButton("Lọc");
        filterButton.setForeground(Color.WHITE);
        filterButton.setBackground(Color.decode("#03A9F4"));
        filterButton.setBorderColor(Color.decode("#03A9F4"));
        filterButton.setBorderSize(3);
        filterButton.setBounds(860, 38, 100, 35);
        filterButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RoomPanel.this.filterRoomList();
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
        resetButton.setBounds(965, 38, 100, 35);
        resetButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RoomPanel.this.searchTextField.setText("Nhập thông tin tìm kiếm");
                RoomPanel.this.roomTypeCombobox.setSelectedIndex(0);
                RoomPanel.this.statusCombobox.setSelectedIndex(0);
                RoomPanel.this.resetTable();
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
        selectedRoom.setBounds(860,10,300,20);
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
        panel.add(addButton);
        panel.add(modifyButton);
        panel.add(deleteButton);
        panel.add(statusLabel);
        panel.add(statusCombobox);

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

    private void updateTable() {
        Object[][] data = this.createDataForRoomTable(this.roomList);
        this.model = new DefaultTableModel(data, this.roomColumnList);
        this.roomTable.setModel(this.model);
        this.roomTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(this.roomTable);
        this.roomTable.getColumnModel()
                        .getColumn(0)
                        .setPreferredWidth(90);

        for (int i=0; i<this.roomTable.getColumnCount(); i++) {
            this.roomTable.getColumnModel()
                            .getColumn(i)
                            .setCellRenderer(this.tableCellRenderer);
        }
    }

    private void resetTable() {
        this.resetList();
        this.updateTable();
    }

    private void resetList() {
        this.roomList = this.roomBLL.getAllRooms();
    }

    private void filterRoomList() {
        String searchString = this.searchTextField.getText().trim().equals("Nhập thông tin tìm kiếm") ? "" : this.searchTextField.getText().trim();
        String roomType = this.roomTypeCombobox.getSelectedItem().toString();
        String status = this.statusCombobox.getSelectedItem().toString();

        if (searchString.isEmpty() && roomType.equals("Tất cả") && status.equals("Tất cả")) {
            this.resetTable();
        } else {
            this.filterRoomListOnNameAndTypeAndStatus(searchString,roomType,status);
        }
    }

    private void filterRoomListOnNameAndTypeAndStatus(String name, String type, String status) {
        this.resetList();

        List<Rooms> filteredRoomList = this.roomList.stream()
                                                    .filter(room -> name.isEmpty() || room.getRoomName().toLowerCase().contains(name.toLowerCase()))
                                                    .filter(room -> type.equals("Tất cả") || room.getType().equals(type))
                                                    .filter(room -> status.equals("Tất cả") || room.getStatus().equals(status))
                                                    .collect(Collectors.toList());

        this.roomList = new ArrayList(filteredRoomList);

        this.updateTable();
    }
}
