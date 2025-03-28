package GUI.panels;

import BLL.*;
import DTO.*;
import GUI.Components.*;
import GUI.Form.AddingComputer;
import GUI.Form.DetailsComputer;
import Utils.Helper.AdjustTableWidth;
import Utils.Helper.CreateComponent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.List;

public class ComputerPanel extends JPanel{
    private CardLayout cardLayout;
    private CustomPanel titlePanel;
    private CustomPanel controlPanel;
    private JPanel dataPanel;
    private int selectedItemIndex;
    private JLabel selectionText;
    private JLabel sessionSelectionText;
    private CustomCombobox<String> statusComboBox;
    private CustomTextField searchTextField;
    private ComputerBLL computerBLL;
    private ComputerSessionBLL computerSessionBLL;
    private MotherboardBLL motherboardBLL;
    private CpuBLL cpuBLL;
    private GpuBLL gpuBLL;
    private MouseBLL mouseBLL;
    private KeyboardBLL keyboardBLL;
    private MonitorBLL monitorBLL;
    private HeadphoneBLL headphoneBLL;
    private RomBLL romBLL;
    private RoomBLL roomBLL;
    private ArrayList<Computers> list;
    private ArrayList<ComputerSessions> sessionList;
    private Object[][] data;
    private DefaultTableModel model;
    private String[] columnNames;
    private String[] sessionColumnNames;
    private CustomTable tableData;
    private CustomTable sessionData;
    private DefaultTableCellRenderer centeredRenderer;

    public ComputerPanel() {
        this.computerBLL = new ComputerBLL();
        this.computerSessionBLL = new ComputerSessionBLL();
        this.motherboardBLL = new MotherboardBLL();
        this.cpuBLL = new CpuBLL();
        this.gpuBLL = new GpuBLL();
        this.mouseBLL = new MouseBLL();
        this.keyboardBLL = new KeyboardBLL();
        this.monitorBLL = new MonitorBLL();
        this.headphoneBLL = new HeadphoneBLL();
        this.romBLL = new RomBLL();
        this.roomBLL = new RoomBLL();
        this.list = this.computerBLL.getAllComputers();
        this.sessionList = this.computerSessionBLL.getComputerSessionList();
        this.columnNames = new String[]{
                "ID",
                "Tên máy tính",
//                "Bo mạch chủ",
//                "CPU",
//                "GPU",
//                "Chuột",
//                "Bàn phím",
//                "Màn hình",
//                "Tai nghe",
//                "ROM",
                "Phòng",
                "Giá tiền",
                "Trạng thái"
        };
        this.sessionColumnNames = new String[]{
                "Session ID",
                "ID người chơi",
                "ID máy tính",
                "Thời gian chơi",
                "Tổng tiền"
        };
        this.centeredRenderer = new DefaultTableCellRenderer();
        this.centeredRenderer.setHorizontalAlignment(JLabel.CENTER);
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        this.titlePanel = this.createTitlePanel();
        this.controlPanel = this.createControlPanel();
        this.dataPanel = this.createDataPanel();

        this.titlePanel.setBounds( 10, 0, 1080, 100);
        this.controlPanel.setBounds(10, 110, 1080, 130);
        this.dataPanel.setBounds(10,250,1080,468);

        this.add(titlePanel);
        this.add(controlPanel);
        this.add(dataPanel);
    }

    private CustomPanel createTitlePanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("QUẢN LÝ MÁY TÍNH");
        title.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        title.setBounds(400, 25, 500, 50);

        panel.add(title);

        return panel;
    }

    private CustomPanel createControlPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        CustomButton manageButton = CreateComponent.createButtonNoIcon("Kho");
        manageButton.setBounds(20,15,200,35);
        manageButton.addActionListener(e -> this.cardLayout.show(this.dataPanel, "ManagePanel"));

        CustomButton playerButton = CreateComponent.createButtonNoIcon("Người chơi");
        playerButton.setBounds(240,15,200,35);
        playerButton.addActionListener(e -> this.cardLayout.show(this.dataPanel, "PlayerPanel"));

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(20, 75, 80, 30);

        searchTextField = new CustomTextField("Nhập thông tin tìm kiếm");
        searchTextField.setBounds(90, 73, 200, 35);
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

        JLabel filterLabel = new JLabel("Trạng thái:");
        filterLabel.setBounds(330, 75, 70, 30);

        String[] statusList = {
                "Tất cả" ,
                "Trong kho",
                "Đang sử dụng",
                "Thiếu linh kiện",
                "Bảo trì",
                "Hỏng"
        };
        statusComboBox = new CustomCombobox<>(statusList);
        statusComboBox.setBounds(405, 73, 150, 35);

        CustomButton filterButton = new CustomButton("Lọc");
        filterButton.setBorderSize(3);
        filterButton.setBackground(Color.decode("#03A9F4"));
        filterButton.setBorderColor(Color.decode("#03A9F4"));
        filterButton.setForeground(Color.WHITE);
        filterButton.setBounds(595, 73, 100, 35);
        filterButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                ComputerPanel.this.filterList();
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                filterButton.setForeground(Color.decode("#03A9F4"));
                filterButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                filterButton.setForeground(Color.WHITE);
                filterButton.setBackground(Color.decode("#03A9F4"));
            }
        });

        CustomButton resetButton = new CustomButton("Đặt lại");
        resetButton.setBackground(Color.RED);
        resetButton.setBorderColor(Color.RED);
        resetButton.setForeground(Color.WHITE);
        resetButton.setBorderSize(3);
        resetButton.setBounds(715, 73, 100, 35);
        resetButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                // Đặt lại placeholder
                ComputerPanel.this.searchTextField
                                    .setText("Nhập thông tin tìm kiếm");

                // Đặt lại trạng thái tất cả
                ComputerPanel.this.statusComboBox
                                    .setSelectedIndex(0);

                // Cập nhật lại bảng với dữ liệu là toàn bộ máy tính
                ComputerPanel.this.resetTable();
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
                resetButton.setForeground(Color.WHITE);
                resetButton.setBackground(Color.RED);
            }
        });

        selectionText = new JLabel("Đang chọn: NULL");
        selectionText.setFont(
                new Font("Sans-serif", Font.BOLD, 12)
        );
        selectionText.setBounds(845,79,300,20);

        panel.add(playerButton);
        panel.add(manageButton);
        panel.add(searchLabel);
        panel.add(searchTextField);
        panel.add(filterLabel);
        panel.add(statusComboBox);
        panel.add(filterButton);
        panel.add(resetButton);
        panel.add(selectionText);

        return panel;
    }

    private JPanel createDataPanel() {
        JPanel panel = new JPanel();
        this.cardLayout = new CardLayout();
        panel.setLayout(this.cardLayout);

        CustomPanel managePanel = this.createManagePanel();
        CustomPanel playerPanel = this.createPlayerPanel();

        panel.add(managePanel, "ManagePanel");
        panel.add(playerPanel, "PlayerPanel");

        return panel;
    }

    private void refreshAllDatas() {
        this.list = this.computerBLL.getAllComputers();
        this.sessionList = this.computerSessionBLL.getComputerSessionList();
    }


    private Object[][] createData(ArrayList<Computers> list) {
        Object[][] data = new Object[list.size()][5];

        for (int i=0; i<list.size(); i++) {
            data[i][0] = list.get(i).getComputerId();
            data[i][1] = list.get(i).getName();

//            Motherboards motherboard = this.motherboardBLL.getMotherboardById(list.get(i).getMotherboardId());
//            data[i][2] = motherboard.getModel();

//            Cpus cpu = this.cpuBLL.getCpuById(motherboard.getCpuId());
//            data[i][3] = cpu.getModel();
//
//            Gpus gpu = this.gpuBLL.getGpubyId(motherboard.getGpuId());
//            data[i][4] = gpu.getModel();
//
//            Mouse mouse = this.mouseBLL.getMouseById(list.get(i).getMouseId());
//            data[i][5] = mouse.getModel();
//
//            Keyboards keyboard = this.keyboardBLL.getKeyboardById(list.get(i).getKeyboardId());
//            data[i][6] = keyboard.getModel();
//
//            Monitors monitor = this.monitorBLL.getMonitorById(list.get(i).getMonitorId());
//            data[i][7] = monitor.getModel();
//
//            Headphones headphone = this.headphoneBLL.getHeadphoneById(list.get(i).getHeadphoneId());
//            data[i][8] = headphone.getModel();
//
//            Roms rom = this.romBLL.getRomById(list.get(i).getRomId());
//            data[i][9] = rom.getModel();


            data[i][2] = list.get(i)
                            .getRoomId() == null
                                ? "Không có Phòng"
                                : this.roomBLL
                                    .getRoomById(list.get(i).getRoomId())
                                    .getRoomName();

            data[i][3] = list.get(i).getPricePerHour() + "đ";
            data[i][4] = list.get(i).getStatus();
        }

        return data;
    }

    private Object[][] createSessionData() {
        Object[][] data = new Object[this.list.size()][5];

        for (int i=0; i<this.list.size(); i++) {
            ComputerSessions session = null;
            for (ComputerSessions x : this.sessionList) {
                if (x.getComputerId() == this.list.get(i).getComputerId()) {
                    session = x;
                }
            }

            if (session == null) {
                data[i][0] = "NULL";
                data[i][1] = "NULL";
                data[i][2] = this.list.get(i).getComputerId();
                data[i][3] = "NULL";
                data[i][4] = "NULL";
            } else {
                data[i][0] = session.getSessionId();
                data[i][1] = session.getPlayerId() != null ? session.getPlayerId() : "NULL";
                data[i][2] = session.getComputerId();
//                data[i][3] = session.;
//                data[i][4]
            }
        }
        return data;
    }

    private CustomPanel createManagePanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        this.refreshAllDatas();
        this.data = this.createData(this.list);
        this.model = new DefaultTableModel(this.data, this.columnNames);

        tableData = new CustomTable(this.model);
        tableData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(tableData);
        tableData.getColumnModel()
                    .getColumn(0)
                    .setPreferredWidth(100);
        tableData.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (tableData.getSelectedRow() != -1) {

                        // Cập nhật tên máy tính được chọn vào label
                        ComputerPanel.this.selectionText
                                            .setText("Đang chọn: " + (String) tableData.getValueAt(tableData.getSelectedRow(), 1));
                    }
                }
            }
        });

        JScrollPane scroll = new CustomScrollPane(tableData);
        scroll.setBounds(0,0,1080,400);

        CustomButton addButton = new CustomButton("Thêm");
        addButton.setBackground(Color.decode("#388E3C"));
        addButton.setBorderColor(Color.decode("#388E3C"));
        addButton.setForeground(Color.WHITE);
        addButton.setBounds(30, 416, 100, 35);
        addButton.setBorderSize(3);
        addButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                new AddingComputer();
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
        modifyButton.setBackground(Color.pink);
        modifyButton.setBorderColor(Color.pink);
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setBounds(150, 416, 100, 35);
        modifyButton.setBorderSize(3);
        modifyButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (tableData.getSelectedRow() != -1) {

                    // Lấy ra ID máy tính được chọn
                    int computerId = (int)tableData.getValueAt(tableData.getSelectedRow(), 0);

                    // Lấy ra đối tượng máy tính dựa trên ID được chọn
                    Computers computer = ComputerPanel.this.computerBLL
                                                            .getComputerById(computerId);

                    // Gọi hàm tạo cửa sổ chỉnh sửa với tham số là đối tượng máy tính
                    new DetailsComputer(computer);
                } else {
                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn máy tính", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                modifyButton.setForeground(Color.pink);
                modifyButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                modifyButton.setForeground(Color.WHITE);
                modifyButton.setBackground(Color.pink);
            }
        });

        CustomButton deleteButton = new CustomButton("Xóa");
        deleteButton.setBackground(Color.red);
        deleteButton.setBorderColor(Color.red);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBounds(270, 416, 100, 35);
        deleteButton.setBorderSize(3);
        deleteButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (tableData.getSelectedRow() != -1) {
                    // Lấy ID của máy tính được chọn và truyền vào hàm xóa máy tính dựa trên ID
                    boolean isDone = ComputerPanel.this.computerBLL.deleteComputerById((int)tableData.getValueAt(tableData.getSelectedRow(), 0));

                    // Kiểm tra xóa máy tính thành công hay không
                    if (isDone) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Xóa máy tính thành công!",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Xóa máy tính thất bại!",
                                "Lỗi",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Bạn chưa chọn máy tính",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                deleteButton.setForeground(Color.red);
                deleteButton.setBackground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deleteButton.setForeground(Color.white);
                deleteButton.setBackground(Color.red);
            }
        });

        panel.add(scroll);
        panel.add(addButton);
        panel.add(modifyButton);
        panel.add(deleteButton);

        return panel;
    }

    private CustomPanel createPlayerPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        this.refreshAllDatas();
        Object[][] data = this.createSessionData();
        DefaultTableModel model = new DefaultTableModel(data, this.sessionColumnNames);

        sessionData = new CustomTable(model);
        sessionData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(sessionData);
        sessionData.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(100);
        sessionData.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (sessionData.getSelectedRow() != -1) {

                        // Cập nhật tên máy tính được chọn vào label
                        ComputerPanel.this.selectionText
                                .setText("Đang chọn: " + sessionData.getValueAt(sessionData.getSelectedRow(), 2).toString());
                    }
                }
            }
        });

        JScrollPane scroll = new CustomScrollPane(sessionData);
        scroll.setBounds(0,0,1080,400);

        CustomButton addButton = new CustomButton("Mở máy");
        addButton.setBackground(Color.decode("#388E3C"));
        addButton.setBorderColor(Color.decode("#388E3C"));
        addButton.setForeground(Color.WHITE);
        addButton.setBounds(30, 416, 100, 35);
        addButton.setBorderSize(3);
        addButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
//                new AddingComputer();
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

        CustomButton modifyButton = new CustomButton("Tắt máy");
        modifyButton.setBackground(Color.pink);
        modifyButton.setBorderColor(Color.pink);
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setBounds(150, 416, 100, 35);
        modifyButton.setBorderSize(3);
        modifyButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
//                if (tableData.getSelectedRow() != -1) {
//
//                    // Lấy ra ID máy tính được chọn
//                    int computerId = (int)tableData.getValueAt(tableData.getSelectedRow(), 0);
//
//                    // Lấy ra đối tượng máy tính dựa trên ID được chọn
//                    Computers computer = ComputerPanel.this.computerBLL
//                            .getComputerById(computerId);
//
//                    // Gọi hàm tạo cửa sổ chỉnh sửa với tham số là đối tượng máy tính
//                    new DetailsComputer(computer);
//                } else {
//                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn máy tính", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                modifyButton.setForeground(Color.pink);
                modifyButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                modifyButton.setForeground(Color.WHITE);
                modifyButton.setBackground(Color.pink);
            }
        });

        CustomButton deleteButton = new CustomButton("Xóa");
        deleteButton.setBackground(Color.red);
        deleteButton.setBorderColor(Color.red);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBounds(270, 416, 100, 35);
        deleteButton.setBorderSize(3);
        deleteButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
//                if (tableData.getSelectedRow() != -1) {
//                    // Lấy ID của máy tính được chọn và truyền vào hàm xóa máy tính dựa trên ID
//                    boolean isDone = ComputerPanel.this.computerBLL.deleteComputerById((int)tableData.getValueAt(tableData.getSelectedRow(), 0));
//
//                    // Kiểm tra xóa máy tính thành công hay không
//                    if (isDone) {
//                        JOptionPane.showMessageDialog(
//                                null,
//                                "Xóa máy tính thành công!",
//                                "Thông báo",
//                                JOptionPane.INFORMATION_MESSAGE
//                        );
//                    } else {
//                        JOptionPane.showMessageDialog(
//                                null,
//                                "Xóa máy tính thất bại!",
//                                "Lỗi",
//                                JOptionPane.WARNING_MESSAGE
//                        );
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(
//                            null,
//                            "Bạn chưa chọn máy tính",
//                            "Thông báo",
//                            JOptionPane.INFORMATION_MESSAGE
//                    );
//                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                deleteButton.setForeground(Color.red);
                deleteButton.setBackground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deleteButton.setForeground(Color.white);
                deleteButton.setBackground(Color.red);
            }
        });

        panel.add(scroll);
        panel.add(addButton);
        panel.add(modifyButton);
        panel.add(deleteButton);

        return panel;
    }

    private void updateTable(Object[][] data) {

        // Cập nhật lại model với data được truyền vào
        this.model = new DefaultTableModel(data, this.columnNames);

        // Cập nhật model mới cho Table
        this.tableData.setModel(this.model);

        tableData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(tableData);

        // Chỉnh width của cột ID thành 100
        tableData.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(100);

        // Căn giữa cho chữ
        for (int i=0; i<tableData.getColumnCount(); i++) {
            tableData.getColumnModel()
                        .getColumn(i)
                        .setCellRenderer(this.centeredRenderer);
        }
    }

    private void filterList() {
        String textFieldContent = this.searchTextField
                                        .getText().trim()
                                        .equals("Nhập thông tin tìm kiếm")
                                        ? ""
                                        : this.searchTextField.getText().trim();
        String status = this.statusComboBox
                            .getSelectedItem()
                            .toString();

        // Nếu nội dung tìm kiếm rỗng và trạng thái là tất cả thì trả về toàn bộ máy tính
        if (textFieldContent.isEmpty() && status.equals("Tất cả")) {
            this.refreshAllDatas();
            Object[][] data = this.createData(this.list);
            this.updateTable(data);
        } else {
            // Gọi hàm trả về máy tính dựa trên nội dung tìm kiếm, trạng thái máy tính
            this.filterListOnNameAndStatus(textFieldContent, status);
        }
    }

    private void resetTable() {
        filterListOnNameAndStatus("", "Tất cả");
    }

    private void filterListOnNameAndStatus(String name, String status) {
        // Đọc lại danh sách máy tính từ database
        this.refreshAllDatas();

        // Lọc các máy tính thỏa nội dung tìm kiếm và trạng thái
        List<Computers> filteredList = this.list.stream()
                                                .filter(computer -> name.isEmpty() || computer.getName().toLowerCase().contains(name))
                                                .filter(computer -> status.equals("Tất cả") || computer.getStatus().equals(status))
                                                .collect(Collectors.toList());

        // Cập nhật lại danh sách máy tính đã lọc vào array list
        this.list = new ArrayList(filteredList);

        // Cập nhật lại nội dung hiển thị trong bảng
        Object[][] data = this.createData(this.list);
        this.updateTable(data);
    }
}
