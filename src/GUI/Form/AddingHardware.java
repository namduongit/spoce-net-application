package GUI.Form;

import BLL.*;
import DTO.*;
import GUI.Components.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class AddingHardware extends JFrame {
    private JPanel content;
    private CustomTextField modelField;
    private CustomTextField priceField;
    private CustomCombobox<String> typeComboBox;
    private CustomCombobox<String> statusComboBox;
    private JPanel dynamicFieldsPanel;

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
    private ArrayList<String> statusList;

    public AddingHardware() {
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
        this.statusList = new ArrayList<>(Arrays.asList("Trong kho", "Đang sử dụng", "Thiếu linh kiện", "Bảo trì", "Hỏng"));

        this.initComponents();
    }

    private void initComponents() {
        this.setTitle("Thêm linh kiện");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(550, 860);
        this.setResizable(false);

        this.content = createContent();
        this.add(this.content);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel createContent() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#ECF0F1"));

        // Tên sản phẩm
        JLabel modelLabel = new JLabel("Tên sản phẩm:");
        modelLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        modelField = new CustomTextField("Nhập tên sản phẩm");
        modelField.setForeground(Color.GRAY);
        addPlaceholderBehavior(modelField, "Nhập tên sản phẩm");

        // Loại linh kiện
        JLabel typeLabel = new JLabel("Loại linh kiện:");
        typeLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        ArrayList<String> typeList = new ArrayList<>(Arrays.asList(
                "Chọn loại","RAM","Rom", "CPU", "GPU", "Mainboard", "Mouse", "Keyboard", "Monitor", "Headphone"
        ));
        typeComboBox = new CustomCombobox<>(typeList);
        typeComboBox.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        typeComboBox.addActionListener(e -> updateDynamicFields());

        // Giá tiền
        JLabel priceLabel = new JLabel("Giá tiền (VNĐ):");
        priceLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        priceField = new CustomTextField("Nhập giá tiền");
        priceField.setForeground(Color.GRAY);
        addPlaceholderBehavior(priceField, "Nhập giá tiền");

        // Trạng thái
        JLabel statusLabel = new JLabel("Trạng thái:");
        statusLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        ArrayList<String> statusComboList = new ArrayList<>();
        statusComboList.add("Chọn trạng thái");
        statusComboList.addAll(this.statusList);
        statusComboBox = new CustomCombobox<>(statusComboList);
        statusComboBox.setFont(new Font("Sans-serif", Font.PLAIN, 14));

        // Panel chứa các trường động
        dynamicFieldsPanel = new JPanel();
        dynamicFieldsPanel.setLayout(null);
        dynamicFieldsPanel.setBounds(20, 250, 510, 500);
        dynamicFieldsPanel.setBackground(Color.decode("#ECF0F1"));

        // Đường phân cách
        JPanel separator = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.decode("#BDC3C7"));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        separator.setBounds(270, 115, 1, 600);

        // Nút lưu
        CustomButton saveBtn = Utils.Helper.CreateComponent.createBlueButton("Lưu lại");
        saveBtn.setBounds(20, 780, 100, 35);
        saveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                insertDatas();
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
        resetBtn.setBounds(140, 780, 100, 35);
        resetBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetForm();
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

        // Đặt vị trí các thành phần
        modelLabel.setBounds(20, 20, 200, 20);
        modelField.setBounds(20, 45, 510, 30);
        typeLabel.setBounds(20, 85, 200, 20);
        typeComboBox.setBounds(20, 110, 225, 35);
        priceLabel.setBounds(275, 85, 200, 20);
        priceField.setBounds(275, 110, 255, 35);
        statusLabel.setBounds(20, 155, 200, 20);
        statusComboBox.setBounds(20, 180, 225, 35);

        // Thêm vào panel
        panel.add(modelLabel);
        panel.add(modelField);
        panel.add(typeLabel);
        panel.add(typeComboBox);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(statusLabel);
        panel.add(statusComboBox);
        panel.add(dynamicFieldsPanel);
        panel.add(separator);
        panel.add(saveBtn);
        panel.add(resetBtn);

        return panel;
    }

    private void addPlaceholderBehavior(CustomTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void updateDynamicFields() {
        dynamicFieldsPanel.removeAll();
        String selectedType = typeComboBox.getSelectedItem().toString();
        int yOffset = 10;

        switch (selectedType) {
            case "Rom":
                addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Loại:", "typeField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Dung lượng (GB):", "capacityField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset);
                break;
            case "CPU":
                addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Tốc độ (GHz):", "clockSpeedField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Số lõi:", "coresField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Số luồng:", "threadsField", yOffset);
                yOffset += 40;
                addCheckbox(dynamicFieldsPanel, "GPU tích hợp:", "integratedGpuField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset);
                break;
            case "GPU":
                addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "VRAM (GB):", "vramField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset);
                break;
            case "Mainboard":
                addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Socket:", "socketField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Chipset:", "chipsetField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Khe RAM:", "ramSlotsField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "RAM tối đa (GB):", "maxRamField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Tốc độ RAM (MHz):", "ramSpeedField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Khe lưu trữ:", "storageSlotsField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Cổng SATA:", "sataPortsField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Khe M.2:", "m2SlotsField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Dung lượng tối đa (TB):", "maxStorageCapacityField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset);
                break;
            case "Mouse":
                addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset);
                break;
            case "Keyboard":
                addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset);
                break;
            case "Monitor":
                addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Kích thước (inch):", "sizeField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Tần số quét (Hz):", "refreshRateField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset);
                break;
            case "Headphone":
                addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Loại:", "typeField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset);
                break;
            case "RAM":
                addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Dung lượng (GB):", "capacityField", yOffset);
                yOffset += 40;
                addTextField(dynamicFieldsPanel, "Tốc độ (MHz):", "speedField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset);
                yOffset += 40;
                addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset);
                break;
            default:
                break;
        }

        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
    }

    private void addTextField(JPanel panel, String labelText, String fieldName, int yOffset) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        label.setBounds(0, yOffset, 200, 20);
        CustomTextField textField = new CustomTextField();
        textField.setBounds(210, yOffset, 290, 30);
        textField.setName(fieldName);
        panel.add(label);
        panel.add(textField);
    }

    private void addDateField(JPanel panel, String labelText, String fieldName, int yOffset) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        label.setBounds(0, yOffset, 200, 20);
        CustomTextField textField = new CustomTextField("YYYY-MM-DD");
        textField.setForeground(Color.GRAY);
        textField.setBounds(210, yOffset, 290, 30);
        textField.setName(fieldName);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("YYYY-MM-DD")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("YYYY-MM-DD");
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        panel.add(label);
        panel.add(textField);
    }

    private void addCheckbox(JPanel panel, String labelText, String fieldName, int yOffset) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        label.setBounds(0, yOffset, 200, 20);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setBounds(210, yOffset, 20, 30);
        checkBox.setName(fieldName);
        panel.add(label);
        panel.add(checkBox);
    }

    private void insertDatas() {
        try {
            String modelText = modelField.getText().trim();
            String priceText = priceField.getText().trim();

            if (modelText.equals("Nhập tên sản phẩm") || priceText.equals("Nhập giá tiền")) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm và Giá tiền không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String model = modelText;
            double price = Double.parseDouble(priceText);
            String type = typeComboBox.getSelectedItem().toString();
            String status = statusComboBox.getSelectedIndex() == 0 ? "Trong kho" : statusComboBox.getSelectedItem().toString();

            if (type.equals("Chọn loại")) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn loại linh kiện!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Giá tiền không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = addHardware(model, type, price, status);
            if (success) {
                JOptionPane.showMessageDialog(this, "Thêm linh kiện thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm linh kiện thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá tiền phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean addHardware(String model, String type, double price, String status) {
        Component[] components = dynamicFieldsPanel.getComponents();
        String brand = getFieldValue(components, "brandField");
        Date purchaseDate = parseDate(getFieldValue(components, "purchaseDateField"));
        Date warrantyExpiry = parseDate(getFieldValue(components, "warrantyExpiryField"));

        try {
            switch (type) {
                case "Rom":
                    String ramType = getFieldValue(components, "typeField");
                    int capacity = Integer.parseInt(getFieldValue(components, "capacityField", "0"));
                    Roms rom = new Roms(brand, model, ramType, capacity, purchaseDate, warrantyExpiry, status, price);
                    return romBLL.addRom(rom);
                case "CPU":
                    float clockSpeed = Float.parseFloat(getFieldValue(components, "clockSpeedField", "0"));
                    int cores = Integer.parseInt(getFieldValue(components, "coresField", "0"));
                    int threads = Integer.parseInt(getFieldValue(components, "threadsField", "0"));
                    boolean integratedGpu = getCheckboxValue(components, "integratedGpuField");
                    Cpus cpu = new Cpus(brand, model, clockSpeed, cores, threads, integratedGpu, purchaseDate, warrantyExpiry, status, price);
                    return cpuBLL.addCpu(cpu);
                case "GPU":
                    int vram = Integer.parseInt(getFieldValue(components, "vramField", "0"));
                    Gpus gpu = new Gpus(brand, model, vram, purchaseDate, warrantyExpiry, status, price);
                    return gpuBLL.addGpu(gpu);
                case "Mainboard":
                    String socket = getFieldValue(components, "socketField");
                    String chipset = getFieldValue(components, "chipsetField");
                    int ramSlots = Integer.parseInt(getFieldValue(components, "ramSlotsField", "0"));
                    int maxRam = Integer.parseInt(getFieldValue(components, "maxRamField", "0"));
                    int ramSpeed = Integer.parseInt(getFieldValue(components, "ramSpeedField", "0"));
                    int storageSlots = Integer.parseInt(getFieldValue(components, "storageSlotsField", "0"));
                    int sataPorts = Integer.parseInt(getFieldValue(components, "sataPortsField", "0"));
                    int m2Slots = Integer.parseInt(getFieldValue(components, "m2SlotsField", "0"));
                    int maxStorageCapacity = Integer.parseInt(getFieldValue(components, "maxStorageCapacityField", "0"));
                    Motherboards motherboard = new Motherboards(brand, model, socket, chipset, ramSlots, maxRam, ramSpeed, storageSlots, sataPorts, m2Slots, maxStorageCapacity, status, null, null, null, purchaseDate, warrantyExpiry, price);
                    return motherboardBLL.addMotherboard(motherboard);
                case "Mouse":
                    Mouse mouse = new Mouse(brand, model, purchaseDate, warrantyExpiry, status, price);
                    return mouseBLL.addMouse(mouse);
                case "Keyboard":
                    Keyboards keyboard = new Keyboards(brand, model, purchaseDate, warrantyExpiry, status, price);
                    return keyboardBLL.addKeyboard(keyboard);
                case "Monitor":
                    float size = Float.parseFloat(getFieldValue(components, "sizeField", "0"));
                    int refreshRate = Integer.parseInt(getFieldValue(components, "refreshRateField", "0"));
                    Monitors monitor = new Monitors(brand, model, size, refreshRate, purchaseDate, warrantyExpiry, status, price);
                    return monitorBLL.addMonitor(monitor);
                case "Headphone":
                    String headphoneType = getFieldValue(components, "typeField");
                    Headphones headphone = new Headphones(brand, model, headphoneType, purchaseDate, warrantyExpiry, status, price);
                    return headphoneBLL.addHeadphone(headphone);
                case "RAM":
                    int ramCapacity = Integer.parseInt(getFieldValue(components, "capacityField", "0"));
                    int speed = Integer.parseInt(getFieldValue(components, "speedField", "0"));
                    Rams ram = new Rams(brand, model, ramCapacity, speed, purchaseDate, warrantyExpiry, status, price);
                    return ramBLL.addRam(ram);
                default:
                    return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu số không hợp lệ: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private String getFieldValue(Component[] components, String fieldName) {
        return getFieldValue(components, fieldName, "");
    }

    private String getFieldValue(Component[] components, String fieldName, String defaultValue) {
        for (Component comp : components) {
            if (comp instanceof CustomTextField && fieldName.equals(comp.getName())) {
                String text = ((CustomTextField) comp).getText().trim();
                return text.equals("YYYY-MM-DD") || text.isEmpty() ? defaultValue : text;
            }
        }
        return defaultValue;
    }

    private boolean getCheckboxValue(Component[] components, String fieldName) {
        for (Component comp : components) {
            if (comp instanceof JCheckBox && fieldName.equals(comp.getName())) {
                return ((JCheckBox) comp).isSelected();
            }
        }
        return false;
    }

    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty() || dateStr.equals("YYYY-MM-DD")) return null;
        try {
            return Date.valueOf(dateStr);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ (YYYY-MM-DD): " + dateStr, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void resetForm() {
        modelField.setText("Nhập tên sản phẩm");
        modelField.setForeground(Color.GRAY);
        typeComboBox.setSelectedIndex(0);
        priceField.setText("Nhập giá tiền");
        priceField.setForeground(Color.GRAY);
        statusComboBox.setSelectedIndex(0);
        updateDynamicFields();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddingHardware());
    }
}