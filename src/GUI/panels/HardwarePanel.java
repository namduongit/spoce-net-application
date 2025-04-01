package GUI.panels;

import BLL.*;
import DTO.*;
import GUI.Components.*;
import Utils.Helper.AdjustTableWidth;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HardwarePanel extends JPanel {
    private CardLayout cardLayout;
    private CustomPanel titlePanel;
    private CustomPanel controlPanel;
    private JPanel dataPanel;
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
    private CustomTable tableData;
    private DefaultTableModel tableModel;
    private String currentType = "Tất cả"; // Theo dõi loại sản phẩm hiện tại

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
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(null);
        this.setBackground(Color.decode("#ECF0F1"));

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

    private CustomPanel createTitlePanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#34495E"));

        JLabel title = new JLabel("QUẢN LÝ LINH KIỆN");
        title.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        title.setForeground(Color.WHITE);
        title.setBounds(400, 25, 500, 50);

        panel.add(title);
        return panel;
    }

    private CustomPanel createControlPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#ECF0F1"));

        JLabel typeHardwareLabel = new JLabel("Loại Sản Phẩm:");
        typeHardwareLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        typeHardwareLabel.setBounds(20, 20, 100, 30);

        String[] typeList = {"Tất cả", "Ram", "CPU", "Memory", "GPU", "Mainboard", "Mouse", "Keyboard", "Monitor", "Headphone"};
        CustomCombobox<String> typeComboBox = new CustomCombobox<>(typeList);
        typeComboBox.setBounds(120, 20, 150, 35);
        typeComboBox.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        typeComboBox.setMaximumRowCount(10);

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

        JLabel filterLabel = new JLabel("Trạng thái:");
        filterLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        filterLabel.setBounds(590, 20, 80, 30);

        String[] statusList = {"Tất cả", "Trong kho", "Đang sử dụng", "Thiếu linh kiện", "Bảo trì", "Hỏng"};
        CustomCombobox<String> statusComboBox = new CustomCombobox<>(statusList);
        statusComboBox.setBounds(660, 20, 150, 35);
        statusComboBox.setFont(new Font("Sans-serif", Font.PLAIN, 14));

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
            String type = (String) typeComboBox.getSelectedItem();
            currentType = type;
            filterTable(searchText, status, type);
        });

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
            currentType = "Tất cả";
            updateTable(getAllHardwareComponents());
        });

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

    private JPanel createDataPanel() {
        JPanel panel = new JPanel();
        this.cardLayout = new CardLayout();
        panel.setLayout(this.cardLayout);

        CustomPanel managePanel = this.createManagePanel();
        panel.add(managePanel, "ManagePanel");

        return panel;
    }

    private CustomPanel createManagePanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#ECF0F1"));

        String[] columnNames = {"ID", "Tên Sản Phẩm", "Loại", "Giá Tiền", "Trạng thái"};
        tableModel = new DefaultTableModel(getTableData(getAllHardwareComponents()), columnNames);
        tableData = new CustomTable(tableModel);
        tableData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(tableData);
        tableData.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableData.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        tableData.setRowHeight(30);

        tableData.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableData.getSelectedRow() != -1) {
                selectionText.setText("Đang chọn: " + tableData.getValueAt(tableData.getSelectedRow(), 1) + " (" + tableData.getValueAt(tableData.getSelectedRow(), 2) + ")");
            }
        });

        JScrollPane scroll = new CustomScrollPane(tableData);
        scroll.setBounds(0, 0, 1080, 400);
        scroll.setBorder(BorderFactory.createLineBorder(Color.decode("#BDC3C7"), 1));

        CustomButton addButton = new CustomButton("Thêm");
        addButton.setBackground(Color.decode("#388E3C"));
        addButton.setBorderColor(Color.decode("#388E3C"));
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
                addButton.setBackground(Color.decode("#388E3C"));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Giả định có form AddingHardware để thêm linh kiện mới
                JOptionPane.showMessageDialog(HardwarePanel.this, "Chức năng thêm linh kiện đang được phát triển! Vui lòng tạo form AddingHardware.");
                // Nếu có form AddingHardware, thay bằng: new AddingHardware();
                updateTable(getAllHardwareComponents()); // Cập nhật bảng sau khi thêm
            }
        });

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

            @Override
            public void mouseClicked(MouseEvent e) {
                if (tableData.getSelectedRow() != -1) {
                    int componentId = (int) tableData.getValueAt(tableData.getSelectedRow(), 0);
                    String componentType = (String) tableData.getValueAt(tableData.getSelectedRow(), 2);

                    // Giả định có form DetailsHardware để chỉnh sửa linh kiện
                    JOptionPane.showMessageDialog(HardwarePanel.this, "Chức năng thay đổi linh kiện đang được phát triển! Vui lòng tạo form DetailsHardware.");
                    // Nếu có form DetailsHardware, thay bằng: new DetailsHardware(componentId, componentType);
                    updateTable(getAllHardwareComponents()); // Cập nhật bảng sau khi thay đổi
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một linh kiện!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

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

            @Override
            public void mouseClicked(MouseEvent e) {
                if (tableData.getSelectedRow() != -1) {
                    int componentId = (int) tableData.getValueAt(tableData.getSelectedRow(), 0);
                    String componentType = (String) tableData.getValueAt(tableData.getSelectedRow(), 2);
                    boolean isDeleted = deleteHardwareComponent(componentId, componentType);
                    if (isDeleted) {
                        JOptionPane.showMessageDialog(HardwarePanel.this, "Xóa linh kiện thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        updateTable(getAllHardwareComponents()); // Cập nhật bảng sau khi xóa
                    } else {
                        JOptionPane.showMessageDialog(HardwarePanel.this, "Xóa linh kiện thất bại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một linh kiện!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        panel.add(scroll);
        panel.add(addButton);
        panel.add(modifyButton);
        panel.add(deleteButton);
        return panel;
    }

    private ArrayList<Object[]> getAllHardwareComponents() {
        ArrayList<Object[]> components = new ArrayList<>();
        ArrayList<Computers> computers = computerBLL.getAllComputers();

        for (Computers computer : computers) {
            // Lấy Motherboard
            Motherboards motherboard = motherboardBLL.getMotherboardById(computer.getMotherboardId()); // Sửa từ getComputerId thành getMotherboardId

            // RAM
            Integer romId = computer.getRomId(); // Dùng Integer để xử lý null
            if (romId != null) {
                Roms ram = romBLL.getRomById(romId);
                if (ram != null) {
                    components.add(new Object[]{ram.getRomId(), ram.getModel(), "Ram", computer.getPricePerHour(), computer.getStatus()});
                }
            }

            // CPU
            if (motherboard != null) {
                Integer cpuId = motherboard.getCpuId();
                if (cpuId != null) {
                    Cpus cpu = cpuBLL.getCpuById(cpuId);
                    if (cpu != null) {
                        components.add(new Object[]{cpu.getCpuId(), cpu.getModel(), "CPU", computer.getPricePerHour(), computer.getStatus()});
                    }
                }
            }

            // GPU
            if (motherboard != null) {
                Integer gpuId = motherboard.getGpuId();
                if (gpuId != null) {
                    Gpus gpu = gpuBLL.getGpubyId(gpuId);
                    if (gpu != null) {
                        components.add(new Object[]{gpu.getGpuId(), gpu.getModel(), "GPU", computer.getPricePerHour(), computer.getStatus()});
                    }
                }
            }

            // Mainboard
            if (motherboard != null) {
                components.add(new Object[]{motherboard.getMotherboardId(), motherboard.getModel(), "Mainboard", computer.getPricePerHour(), computer.getStatus()});
            }

            // Mouse
            Integer mouseId = computer.getMouseId();
            if (mouseId != null) {
                Mouse mouse = mouseBLL.getMouseById(mouseId);
                if (mouse != null) {
                    components.add(new Object[]{mouse.getMouseId(), mouse.getModel(), "Mouse", computer.getPricePerHour(), computer.getStatus()});
                }
            }

            // Keyboard
            Integer keyboardId = computer.getKeyboardId();
            if (keyboardId != null) {
                Keyboards keyboard = keyboardBLL.getKeyboardById(keyboardId);
                if (keyboard != null) {
                    components.add(new Object[]{keyboard.getKeyboardId(), keyboard.getModel(), "Keyboard", computer.getPricePerHour(), computer.getStatus()});
                }
            }

            // Monitor
            Integer monitorId = computer.getMonitorId();
            if (monitorId != null) {
                Monitors monitor = monitorBLL.getMonitorById(monitorId);
                if (monitor != null) {
                    components.add(new Object[]{monitor.getMonitorId(), monitor.getModel(), "Monitor", computer.getPricePerHour(), computer.getStatus()});
                }
            }

            // Headphone
            Integer headphoneId = computer.getHeadphoneId();
            if (headphoneId != null) {
                Headphones headphone = headphoneBLL.getHeadphoneById(headphoneId);
                if (headphone != null) {
                    components.add(new Object[]{headphone.getHeadphoneId(), headphone.getModel(), "Headphone", computer.getPricePerHour(), computer.getStatus()});
                }
            }
        }
        return components;
    }

    private Object[][] getTableData(ArrayList<Object[]> components) {
        Object[][] data = new Object[components.size()][5];
        for (int i = 0; i < components.size(); i++) {
            data[i] = components.get(i);
        }
        return data;
    }

    private void filterTable(String searchText, String status, String type) {
        ArrayList<Object[]> allComponents = getAllHardwareComponents();
        ArrayList<Object[]> filteredList = new ArrayList<>(allComponents);

        filteredList.removeIf(component -> {
            String productName = (String) component[1];
            String productType = (String) component[2];
            String componentStatus = (String) component[4];

            boolean matchesSearch = searchText.equals("Nhập thông tin tìm kiếm") ||
                    searchText.isEmpty() ||
                    productName.toLowerCase().contains(searchText.toLowerCase());
            boolean matchesStatus = status.equals("Tất cả") || componentStatus.equals(status);
            boolean matchesType = type.equals("Tất cả") || productType.equals(type) || (type.equals("Memory") && productType.equals("Ram"));

            return !(matchesSearch && matchesStatus && matchesType);
        });

        updateTable(filteredList);
    }

    private void updateTable(ArrayList<Object[]> components) {
        tableModel.setDataVector(getTableData(components),
                new String[]{"ID", "Tên Sản Phẩm", "Loại", "Giá Tiền", "Trạng thái"});
        AdjustTableWidth.automaticallyAdjustTableWidths(tableData);
        tableData.getColumnModel().getColumn(0).setPreferredWidth(100);
    }

    // Hàm xóa linh kiện dựa trên ID và loại
//    private boolean deleteHardwareComponent(int id, String type) {
//        switch (type) {
//            case "Ram":
//                return romBLL.deleteRomById(id);
//            case "CPU":
//                return cpuBLL.deleteCpuById(id);
//            case "GPU":
//                return gpuBLL.deleteGpuById(id);
//            case "Mainboard":
//                return motherboardBLL.deleteMotherboardById(id);
//            case "Mouse":
//                return mouseBLL.deleteMouseById(id);
//            case "Keyboard":
//                return keyboardBLL.deleteKeyboardById(id);
//            case "Monitor":
//                return monitorBLL.deleteMonitorById(id);
//            case "Headphone":
//                return headphoneBLL.deleteHeadphoneById(id);
//            default:
//                return false;
//        }
//    }
    private boolean deleteHardwareComponent(int id, String type) {
        // Kiểm tra xem linh kiện có đang được sử dụng bởi máy tính nào không
        ArrayList<Computers> computers = computerBLL.getAllComputers();
        switch (type) {
            case "Ram":
                for (Computers computer : computers) {
                    if (computer.getRomId() != null && computer.getRomId() == id) {
                        JOptionPane.showMessageDialog(this, "Không thể xóa RAM vì đang được sử dụng bởi máy tính " + computer.getComputerId(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
                return romBLL.deleteRomById(id);
            case "CPU":
                for (Computers computer : computers) {
                    Motherboards motherboard = motherboardBLL.getMotherboardById(computer.getMotherboardId());
                    if (motherboard != null && motherboard.getCpuId() != null && motherboard.getCpuId() == id) {
                        JOptionPane.showMessageDialog(this, "Không thể xóa CPU vì đang được sử dụng bởi máy tính " + computer.getComputerId(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
                return cpuBLL.deleteCpuById(id);
            case "GPU":
                for (Computers computer : computers) {
                    Motherboards motherboard = motherboardBLL.getMotherboardById(computer.getMotherboardId());
                    if (motherboard != null && motherboard.getGpuId() != null && motherboard.getGpuId() == id) {
                        JOptionPane.showMessageDialog(this, "Không thể xóa GPU vì đang được sử dụng bởi máy tính " + computer.getComputerId(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
                return gpuBLL.deleteGpuById(id);
            case "Mainboard":
                for (Computers computer : computers) {
                    if (computer.getMotherboardId() == id) {
                        JOptionPane.showMessageDialog(this, "Không thể xóa Mainboard vì đang được sử dụng bởi máy tính " + computer.getComputerId(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
                return motherboardBLL.deleteMotherboardById(id);
            case "Mouse":
                for (Computers computer : computers) {
                    if (computer.getMouseId() != null && computer.getMouseId() == id) {
                        JOptionPane.showMessageDialog(this, "Không thể xóa Mouse vì đang được sử dụng bởi máy tính " + computer.getComputerId(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
                return mouseBLL.deleteMouseById(id);
            case "Keyboard":
                for (Computers computer : computers) {
                    if (computer.getKeyboardId() != null && computer.getKeyboardId() == id) {
                        JOptionPane.showMessageDialog(this, "Không thể xóa Keyboard vì đang được sử dụng bởi máy tính " + computer.getComputerId(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
                return keyboardBLL.deleteKeyboardById(id);
            case "Monitor":
                for (Computers computer : computers) {
                    if (computer.getMonitorId() != null && computer.getMonitorId() == id) {
                        JOptionPane.showMessageDialog(this, "Không thể xóa Monitor vì đang được sử dụng bởi máy tính " + computer.getComputerId(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
                return monitorBLL.deleteMonitorById(id);
            case "Headphone":
                for (Computers computer : computers) {
                    if (computer.getHeadphoneId() != null && computer.getHeadphoneId() == id) {
                        JOptionPane.showMessageDialog(this, "Không thể xóa Headphone vì đang được sử dụng bởi máy tính " + computer.getComputerId(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
                return headphoneBLL.deleteHeadphoneById(id);
            default:
                return false;
        }
    }
}