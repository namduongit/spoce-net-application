package GUI.panels;

import BLL.*;
import DTO.*;
import GUI.Components.*;
import GUI.Form.DetailsComputer;
import Utils.Helper.AdjustTableWidth;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HardwarePanel extends JPanel {
    private CardLayout cardLayout;
    private CustomPanel titlePanel;
    private CustomPanel controlPanel;
    private JPanel dataPanel;
    private int selectedItemIndex;
    private JLabel selectionText;
    private ComputerBLL computerBLL;
    private MotherboardBLL motherboardBLL;
    private CpuBLL cpuBLL;
    private GpuBLL gpuBLL;
    private MouseBLL mouseBLL;
    private KeyboardBLL keyboardBLL;
    private MonitorBLL monitorBLL;
    private HeadphoneBLL headphoneBLL;
    private RomBLL romBLL;
    private ArrayList<Computers> list;
    private CustomTable tableData;
    private DefaultTableModel tableModel;

    public HardwarePanel() {
        this.computerBLL = new ComputerBLL();
        this.motherboardBLL = new MotherboardBLL();
        this.cpuBLL = new CpuBLL();
        this.gpuBLL = new GpuBLL();
        this.mouseBLL = new MouseBLL();
        this.keyboardBLL = new KeyboardBLL();
        this.monitorBLL = new MonitorBLL();
        this.headphoneBLL = new HeadphoneBLL();
        this.romBLL = new RomBLL();
        this.list = this.computerBLL.getAllComputers();
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(null);
        this.setBackground(Color.decode("#ECF0F1")); // Màu nền nhẹ nhàng

        this.titlePanel = this.createTitlePanel();
        this.controlPanel = this.createControlPanel();
        this.dataPanel = this.createDataPanel();

        this.titlePanel.setBounds(10, 0, 1080, 100);
        this.controlPanel.setBounds(10, 110, 1080, 130);
        this.dataPanel.setBounds(10, 250, 1080, 468);

        this.add(titlePanel);
        this.add(controlPanel);
        this.add(dataPanel);
    }

    // Tiêu đề
    private CustomPanel createTitlePanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#34495E")); // Xanh đen đậm

        JLabel title = new JLabel("QUẢN LÝ LINH KIỆN");
        title.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        title.setForeground(Color.WHITE);
        title.setBounds(400, 25, 500, 50);

        panel.add(title);
        return panel;
    }

    // Các chức năng
    private CustomPanel createControlPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#ECF0F1"));

        // Combobox Loại Sản Phẩm
        JLabel typeHardwareLabel = new JLabel("Loại Sản Phẩm:");
        typeHardwareLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        typeHardwareLabel.setBounds(20, 20, 100, 30);

        String[] typeList = {"Tất cả", "Ram", "CPU", "Memory", "GPU", "Mainboard", "Mouse", "Keyboard", "Monitor", "Headphone"};
        CustomCombobox<String> typeComboBox = new CustomCombobox<>(typeList);
        typeComboBox.setBounds(120, 20, 150, 35);
        typeComboBox.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        typeComboBox.setMaximumRowCount(10); // Hiển thị tối đa 10 mục, không cần cuộn


        // Tìm kiếm
        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        searchLabel.setBounds(300, 20, 80, 30);

        CustomTextField searchTextField = new CustomTextField("Nhập thông tin tìm kiếm");
        searchTextField.setBounds(370, 20, 200, 35);
        searchTextField.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        searchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchTextField.getText().equals("Nhập thông tin tìm kiếm")) {
                    searchTextField.setText("");
                    searchTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchTextField.getText().isEmpty()) {
                    searchTextField.setText("Nhập thông tin tìm kiếm");
                    searchTextField.setForeground(Color.GRAY);
                }
            }
        });
        searchTextField.setForeground(Color.GRAY);

        // Trạng thái
        JLabel filterLabel = new JLabel("Trạng thái:");
        filterLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        filterLabel.setBounds(590, 20, 80, 30);

        String[] statusList = {"Tất cả", "Trong kho", "Đang sử dụng", "Thiếu linh kiện", "Bảo trì", "Hỏng"};
        CustomCombobox<String> statusComboBox = new CustomCombobox<>(statusList);
        statusComboBox.setBounds(660, 20, 150, 35);
        statusComboBox.setFont(new Font("Sans-serif", Font.PLAIN, 14));

        // Lọc
        CustomButton filterButton = new CustomButton("Lọc");
        filterButton.setBackground(Color.decode("#3498DB"));
        filterButton.setBorderColor(Color.decode("#3498DB"));
        filterButton.setForeground(Color.WHITE);
        filterButton.setBounds(830, 20, 100, 35);
        filterButton.setBorderSize(3);
        filterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                filterButton.setBackground(Color.decode("#2980B9"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                filterButton.setBackground(Color.decode("#3498DB"));
            }
        });
        filterButton.addActionListener(e -> {
            String searchText = searchTextField.getText();
            String status = (String) statusComboBox.getSelectedItem();
//            -------
            String type = (String) typeComboBox.getSelectedItem();

            filterTable(searchText, status, type);
        });

        // Đặt lại
        CustomButton resetButton = new CustomButton("Đặt lại");
        resetButton.setBackground(Color.decode("#E74C3C"));
        resetButton.setBorderColor(Color.decode("#E74C3C"));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBounds(950, 20, 100, 35);
        resetButton.setBorderSize(3);
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resetButton.setBackground(Color.decode("#C0392B"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetButton.setBackground(Color.decode("#E74C3C"));
            }
        });
        resetButton.addActionListener(e -> {
            searchTextField.setText("Nhập thông tin tìm kiếm");
            searchTextField.setForeground(Color.GRAY);
            statusComboBox.setSelectedIndex(0);
            typeComboBox.setSelectedIndex(0);
            updateTable(computerBLL.getAllComputers());
        });

        // Đang chọn
        selectionText = new JLabel("Đang chọn: NULL");
        selectionText.setFont(new Font("Sans-serif", Font.BOLD, 14));
        selectionText.setForeground(Color.decode("#7F8C8D"));
        selectionText.setBounds(20, 80, 300, 30);

        panel.add(typeHardwareLabel);
        panel.add(typeComboBox);
        panel.add(searchLabel);
        panel.add(searchTextField);
        panel.add(filterLabel);
        panel.add(statusComboBox);
        panel.add(filterButton);
        panel.add(resetButton);
        panel.add(selectionText);
        return panel;
    }

    // Bảng table
    private JPanel createDataPanel() {
        JPanel panel = new JPanel();
        this.cardLayout = new CardLayout();
        panel.setLayout(this.cardLayout);

        CustomPanel managePanel = this.createManagePanel();
        panel.add(managePanel, "ManagePanel");

        return panel;
    }

    // Tùy cài table
    private CustomPanel createManagePanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#ECF0F1"));

        String[] columnNames = {"ID", "Tên máy tính", "Bo mạch chủ", "CPU", "GPU", "Chuột",
                "Bàn phím", "Màn hình", "Tai nghe", "ROM", "Phòng",
                "Giá tiền", "Trạng thái"};

        tableModel = new DefaultTableModel(getTableData(list), columnNames);
        tableData = new CustomTable(tableModel);
        tableData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(tableData);
        tableData.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableData.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        tableData.setRowHeight(30); // Tăng chiều cao dòng cho dễ đọc

        tableData.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableData.getSelectedRow() != -1) {
                selectionText.setText("Đang chọn: " + tableData.getValueAt(tableData.getSelectedRow(), 1));
            }
        });

        JScrollPane scroll = new CustomScrollPane(tableData);
        scroll.setBounds(0, 0, 1080, 400);
        scroll.setBorder(BorderFactory.createLineBorder(Color.decode("#BDC3C7"), 1)); // Viền nhẹ

        // Nút Thêm
        CustomButton addButton = new CustomButton("Thêm");
        addButton.setBackground(Color.decode("#2ECC71"));
        addButton.setBorderColor(Color.decode("#2ECC71"));
        addButton.setForeground(Color.WHITE);
        addButton.setBounds(30, 416, 100, 40);
        addButton.setBorderSize(3);
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addButton.setBackground(Color.decode("#27AE60"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addButton.setBackground(Color.decode("#2ECC71"));
            }
        });
        addButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Chức năng thêm đang được phát triển!");
        });

        // Nút Thay đổi
        CustomButton modifyButton = new CustomButton("Thay đổi");
        modifyButton.setBackground(Color.decode("#F39C12"));
        modifyButton.setBorderColor(Color.decode("#F39C12"));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setBounds(150, 416, 100, 40);
        modifyButton.setBorderSize(3);
        modifyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                modifyButton.setBackground(Color.decode("#E67E22"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                modifyButton.setBackground(Color.decode("#F39C12"));
            }
        });
        modifyButton.addActionListener(e -> {
            if (tableData.getSelectedRow() != -1) {
                int computerId = (int) tableData.getValueAt(tableData.getSelectedRow(), 0);
                Computers computer = computerBLL.getComputerById(computerId);
                new DetailsComputer(computer);
                updateTable(computerBLL.getAllComputers());
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một máy tính!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Nút Xóa
        CustomButton deleteButton = new CustomButton("Xóa");
        deleteButton.setBackground(Color.decode("#E74C3C"));
        deleteButton.setBorderColor(Color.decode("#E74C3C"));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBounds(270, 416, 100, 40);
        deleteButton.setBorderSize(3);
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteButton.setBackground(Color.decode("#C0392B"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deleteButton.setBackground(Color.decode("#E74C3C"));
            }
        });
        deleteButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Chức năng xóa đang được phát triển!");
        });

        panel.add(scroll);
        panel.add(addButton);
        panel.add(modifyButton);
        panel.add(deleteButton);
        return panel;
    }

    // Lấy dữ liệu cho bảng
    private Object[][] getTableData(ArrayList<Computers> computers) {
        Object[][] data = new Object[computers.size()][13];
        for (int i = 0; i < computers.size(); i++) {
            Computers computer = computers.get(i);
            data[i][0] = computer.getComputerId();
            data[i][1] = computer.getName();
            Motherboards motherboard = motherboardBLL.getMotherboardById(computer.getComputerId());
            data[i][2] = motherboard != null ? motherboard.getModel() : "N/A";
            Cpus cpu = cpuBLL.getCpuById(motherboard != null ? motherboard.getCpuId() : -1);
            data[i][3] = cpu != null ? cpu.getModel() : "N/A";
            Gpus gpu = gpuBLL.getGpubyId(motherboard != null ? motherboard.getGpuId() : -1);
            data[i][4] = gpu != null ? gpu.getModel() : "N/A";
            Mouse mouse = mouseBLL.getMouseById(computer.getMouseId());
            data[i][5] = mouse != null ? mouse.getModel() : "N/A";
            Keyboards keyboard = keyboardBLL.getKeyboardById(computer.getKeyboardId());
            data[i][6] = keyboard != null ? keyboard.getModel() : "N/A";
            Monitors monitor = monitorBLL.getMonitorById(computer.getMonitorId());
            data[i][7] = monitor != null ? monitor.getModel() : "N/A";
            Headphones headphone = headphoneBLL.getHeadphoneById(computer.getHeadphoneId());
            data[i][8] = headphone != null ? headphone.getModel() : "N/A";
            Roms rom = romBLL.getRomById(computer.getRomId());
            data[i][9] = rom != null ? rom.getModel() : "N/A";
            data[i][10] = computer.getRoomId();
            data[i][11] = computer.getPricePerHour();
            data[i][12] = computer.getStatus();
        }
        return data;
    }

    // Lọc bảng (cập nhật để thêm type)
    private void filterTable(String searchText, String status, String type) {
        ArrayList<Computers> filteredList = new ArrayList<>();
        list = computerBLL.getAllComputers();

        for (Computers computer : list) {
            boolean matchesSearch = searchText.equals("Nhập thông tin tìm kiếm") ||
                    searchText.isEmpty() ||
                    computer.getName().toLowerCase().contains(searchText.toLowerCase());
            boolean matchesStatus = status.equals("Tất cả") || computer.getStatus().equals(status);
            boolean matchesType = type.equals("Tất cả") ||
                    (type.equals("Ram") && romBLL.getRomById(computer.getRomId()) != null) ||
                    (type.equals("CPU") && cpuBLL.getCpuById(motherboardBLL.getMotherboardById(computer.getComputerId()).getCpuId()) != null) ||
                    (type.equals("Memory") && romBLL.getRomById(computer.getRomId()) != null) ||
                    (type.equals("GPU") && gpuBLL.getGpubyId(motherboardBLL.getMotherboardById(computer.getComputerId()).getGpuId()) != null) ||
                    (type.equals("Mainboard") && motherboardBLL.getMotherboardById(computer.getComputerId()) != null) ||
                    (type.equals("Mouse") && mouseBLL.getMouseById(computer.getMouseId()) != null) ||
                    (type.equals("Keyboard") && keyboardBLL.getKeyboardById(computer.getKeyboardId()) != null) ||
                    (type.equals("Monitor") && monitorBLL.getMonitorById(computer.getMonitorId()) != null) ||
                    (type.equals("Headphone") && headphoneBLL.getHeadphoneById(computer.getHeadphoneId()) != null);

            if (matchesSearch && matchesStatus && matchesType) {
                filteredList.add(computer);
            }
        }
        updateTable(filteredList);
    }

//    // Lọc bảng
//    private void filterTable(String searchText, String status) {
//        ArrayList<Computers> filteredList = new ArrayList<>();
//        list = computerBLL.getAllComputers();
//
//        for (Computers computer : list) {
//            boolean matchesSearch = searchText.equals("Nhập thông tin tìm kiếm") ||
//                    searchText.isEmpty() ||
//                    computer.getName().toLowerCase().contains(searchText.toLowerCase());
//            boolean matchesStatus = status.equals("Tất cả") ||
//                    computer.getStatus().equals(status);
//
//            if (matchesSearch && matchesStatus) {
//                filteredList.add(computer);
//            }
//        }
//        updateTable(filteredList);
//    }

    // Cập nhật bảng
    private void updateTable(ArrayList<Computers> computers) {
        tableModel.setDataVector(getTableData(computers),
                new String[]{"ID", "Tên máy tính", "Bo mạch chủ", "CPU", "GPU", "Chuột",
                        "Bàn phím", "Màn hình", "Tai nghe", "ROM", "Phòng",
                        "Giá tiền", "Trạng thái"});
        AdjustTableWidth.automaticallyAdjustTableWidths(tableData);
        tableData.getColumnModel().getColumn(0).setPreferredWidth(100);
    }
}