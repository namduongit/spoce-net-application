package GUI.panels;

import BLL.*;
import DTO.*;
import GUI.Components.*;
import GUI.Form.AddingHardware;
import GUI.Form.DetailsHardware;
import Utils.Helper.AdjustTableWidth;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
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
    private RamBLL ramBLL;
    private CustomTable tableData;
    private DefaultTableModel tableModel;
    private String currentType = "Tất cả";
    private DefaultTableCellRenderer tableCellRenderer;

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
        this.ramBLL = new RamBLL();
        this.tableCellRenderer = new DefaultTableCellRenderer();
        this.tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);

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

        JLabel title = new JLabel("QUẢN LÝ LINH KIỆN");
        title.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        title.setBounds(400, 25, 500, 50);

        panel.add(title);
        return panel;
    }

    private CustomPanel createControlPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel typeHardwareLabel = new JLabel("Loại Sản Phẩm:");
        typeHardwareLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        typeHardwareLabel.setBounds(20, 20, 100, 30);

        String[] typeList = {"Tất cả", "RAM", "Rom", "CPU", "GPU", "Mainboard", "Mouse", "Keyboard", "Monitor", "Headphone"};
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

        String[] columnNames = {"ID", "Tên Sản Phẩm", "Loại", "Trạng thái"};
        tableModel = new DefaultTableModel(getTableData(getAllHardwareComponents()), columnNames);
        tableData = new CustomTable(tableModel);
        tableData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // tắt tự động điều chỉnh kích thước bảng

        // Đặt chiều rộng cố định cho các cột
        tableData.getColumnModel().getColumn(0).setPreferredWidth(150); // ID
        tableData.getColumnModel().getColumn(1).setPreferredWidth(400); // Tên Sản Phẩm
        tableData.getColumnModel().getColumn(2).setPreferredWidth(200); // Loại
        tableData.getColumnModel().getColumn(3).setPreferredWidth(330); // Trạng thái

        // Tùy chỉnh giao diện bảng
        tableData.setFont(new Font("Sans-serif", Font.PLAIN, 16));
        tableData.setRowHeight(40);
        tableData.setGridColor(Color.decode("#ECF0F1")); //Đặt màu đường kẻ lưới giữa các ô
        tableData.setShowGrid(true); // Hiện đường kẻ lưới
        tableData.setBackground(Color.WHITE);
        tableData.getTableHeader().setFont(new Font("Sans-serif", Font.BOLD, 16));
        tableData.getTableHeader().setBackground(Color.decode("#3498DB"));//màu nền cho tiêu đề cột 
        tableData.getTableHeader().setForeground(Color.WHITE); //màu chữ cho tiêu đề cột
        tableData.getTableHeader().setReorderingAllowed(false); // Không cho phép kéo thả tiêu đề cột
        

        // Áp dụng renderer cho tất cả cột
        for (int i = 0; i < tableData.getColumnCount(); i++) {
            tableData.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
        }

        tableData.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableData.getSelectedRow() != -1) {
                selectionText.setText("Đang chọn: " + tableData.getValueAt(tableData.getSelectedRow(), 1) + " (" + tableData.getValueAt(tableData.getSelectedRow(), 2) + ")");
            }
        });

        JScrollPane scroll = new CustomScrollPane(tableData);
        scroll.setBounds(0, 0, 1080, 400);
        scroll.setBorder(BorderFactory.createLineBorder(Color.decode("#BDC3C7"), 1));
        scroll.getViewport().setBackground(Color.WHITE);

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
                new AddingHardware();
                updateTable(getAllHardwareComponents());
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
                    new DetailsHardware(componentType, componentId);
                    updateTable(getAllHardwareComponents());
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
                        updateTable(getAllHardwareComponents());
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

        // ROM
        ArrayList<Roms> roms = romBLL.getAllRoms();
        if (roms == null || roms.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu ROM!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else {
            for (Roms rom : roms) {
                components.add(new Object[]{rom.getRomId(), rom.getModel(), "Rom", rom.getStatus()});
            }
        }

        // CPU
        ArrayList<Cpus> cpus = cpuBLL.getAllCpus();
        if (cpus == null || cpus.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu CPU!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else {
            for (Cpus cpu : cpus) {
                components.add(new Object[]{cpu.getCpuId(), cpu.getModel(), "CPU", cpu.getStatus()});
            }
        }

        // GPU
        ArrayList<Gpus> gpus = gpuBLL.getAllGpus();
        if (gpus == null || gpus.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu GPU!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else {
            for (Gpus gpu : gpus) {
                components.add(new Object[]{gpu.getGpuId(), gpu.getModel(), "GPU", gpu.getStatus()});
            }
        }

        // Mainboard
        ArrayList<Motherboards> motherboards = motherboardBLL.getAllMotherboards();
        if (motherboards == null || motherboards.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu Mainboard!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else {
            for (Motherboards motherboard : motherboards) {
                components.add(new Object[]{motherboard.getMotherboardId(), motherboard.getModel(), "Mainboard", motherboard.getStatus()});
            }
        }

        // Mouse
        ArrayList<Mouse> mice = mouseBLL.getAllMouses();
        if (mice == null || mice.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu Mouse!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else {
            for (Mouse mouse : mice) {
                components.add(new Object[]{mouse.getMouseId(), mouse.getModel(), "Mouse", mouse.getStatus()});
            }
        }

        // Keyboard
        ArrayList<Keyboards> keyboards = keyboardBLL.getAllKeyboards();
        if (keyboards == null || keyboards.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu Keyboard!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else {
            for (Keyboards keyboard : keyboards) {
                components.add(new Object[]{keyboard.getKeyboardId(), keyboard.getModel(), "Keyboard", keyboard.getStatus()});
            }
        }

        // Monitor
        ArrayList<Monitors> monitors = monitorBLL.getAllMonitors();
        if (monitors == null || monitors.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu Monitor!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else {
            for (Monitors monitor : monitors) {
                components.add(new Object[]{monitor.getMonitorId(), monitor.getModel(), "Monitor", monitor.getStatus()});
            }
        }

        // Headphone
        ArrayList<Headphones> headphones = headphoneBLL.getAllHeadphones();
        if (headphones == null || headphones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu Headphone!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else {
            for (Headphones headphone : headphones) {
                components.add(new Object[]{headphone.getHeadphoneId(), headphone.getModel(), "Headphone", headphone.getStatus()});
            }
        }

        // RAM
        ArrayList<Rams> rams = ramBLL.getAllRams();
        if (rams == null || rams.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu RAM!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else {
            for (Rams ram : rams) {
                components.add(new Object[]{ram.getRamId(), ram.getModel(), "RAM", ram.getStatus()});
            }
        }

        return components;
    }

    private Object[][] getTableData(ArrayList<Object[]> components) {
        Object[][] data = new Object[components.size()][4];
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
            String componentStatus = (String) component[3];

            boolean matchesSearch = searchText.equals("Nhập thông tin tìm kiếm") ||
                    searchText.isEmpty() ||
                    productName.toLowerCase().contains(searchText.toLowerCase());
            boolean matchesStatus = status.equals("Tất cả") || componentStatus.equals(status);
            boolean matchesType = type.equals("Tất cả") || productType.equals(type);

            return !(matchesSearch && matchesStatus && matchesType);
        });

        updateTable(filteredList);
    }

    private void updateTable(ArrayList<Object[]> components) {
        tableModel.setDataVector(getTableData(components),
                new String[]{"ID", "Tên Sản Phẩm", "Loại", "Trạng thái"});
        // Đặt lại chiều rộng cột cố định
        tableData.getColumnModel().getColumn(0).setPreferredWidth(150); // ID
        tableData.getColumnModel().getColumn(1).setPreferredWidth(400); // Tên Sản Phẩm
        tableData.getColumnModel().getColumn(2).setPreferredWidth(200); // Loại
        tableData.getColumnModel().getColumn(3).setPreferredWidth(330); // Trạng thái
        // Áp dụng renderer
        for (int i = 0; i < tableData.getColumnCount(); i++) {
            tableData.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
        }
    }

    private boolean deleteHardwareComponent(int id, String type) {
        switch (type) {
            case "Rom":
                return romBLL.deleteRomById(id);
            case "CPU":
                return cpuBLL.deleteCpuById(id);
            case "GPU":
                return gpuBLL.deleteGpuById(id);
            case "Mainboard":
                return motherboardBLL.deleteMotherboardById(id);
            case "Mouse":
                return mouseBLL.deleteMouseById(id);
            case "Keyboard":
                return keyboardBLL.deleteKeyboardById(id);
            case "Monitor":
                return monitorBLL.deleteMonitorById(id);
            case "Headphone":
                return headphoneBLL.deleteHeadphoneById(id);
            case "RAM":
                return ramBLL.deleteRamById(id);
            default:
                return false;
        }
    }
}