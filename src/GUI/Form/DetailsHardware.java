package GUI.Form;

import BLL.*;
import DTO.*;
import GUI.Components.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DetailsHardware extends JFrame {
    private JPanel content;
    private CustomTextField idField;
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
    private ArrayList<String> statusList;

    private String hardwareType; // Loại linh kiện được chọn
    private int hardwareId;      // ID của linh kiện cần hiển thị chi tiết

    public DetailsHardware(String type, int id) {
        this.hardwareType = type;
        this.hardwareId = id;

        this.computerBLL = new ComputerBLL();
        this.motherboardBLL = new MotherboardBLL();
        this.cpuBLL = new CpuBLL();
        this.gpuBLL = new GpuBLL();
        this.mouseBLL = new MouseBLL();
        this.keyboardBLL = new KeyboardBLL();
        this.monitorBLL = new MonitorBLL();
        this.headphoneBLL = new HeadphoneBLL();
        this.romBLL = new RomBLL();
        this.statusList = new ArrayList<>(Arrays.asList("Trong kho", "Đang sử dụng", "Thiếu linh kiện", "Bảo trì", "Hỏng"));

        this.initComponents();
        this.loadHardwareDetails(); // Tải thông tin chi tiết khi khởi tạo
    }

    private void initComponents() {
        this.setTitle("Chi tiết linh kiện");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(550, 700);
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

        // ID (chỉ đọc)
        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        idField = new CustomTextField("");
        idField.setEditable(false); // Không cho chỉnh sửa ID
        idField.setForeground(Color.BLACK);

        // Tên sản phẩm
        JLabel modelLabel = new JLabel("Tên sản phẩm:");
        modelLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        modelField = new CustomTextField("");

        // Loại linh kiện (chỉ đọc)
        JLabel typeLabel = new JLabel("Loại linh kiện:");
        typeLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        ArrayList<String> typeList = new ArrayList<>(Arrays.asList(
                "Chọn loại", "Rom", "CPU", "GPU", "Mainboard", "Mouse", "Keyboard", "Monitor", "Headphone"
        ));
        typeComboBox = new CustomCombobox<>(typeList);
        typeComboBox.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        typeComboBox.setEnabled(false); // Không cho thay đổi loại

        // Giá tiền
        JLabel priceLabel = new JLabel("Giá tiền (VNĐ):");
        priceLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        priceField = new CustomTextField("");

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
        dynamicFieldsPanel.setBounds(20, 270, 500, 300);
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
        separator.setBounds(270, 155, 1, 400);

        // Nút lưu thay đổi
        CustomButton saveBtn = Utils.Helper.CreateComponent.createBlueButton("Lưu thay đổi");
        saveBtn.setBounds(20, 620, 120, 35);
        saveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateHardware();
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

        // Nút đóng
        CustomButton closeBtn = Utils.Helper.CreateComponent.createOrangeButton("Đóng");
        closeBtn.setBounds(150, 620, 100, 35);
        closeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                closeBtn.setBackground(Color.WHITE);
                closeBtn.setForeground(Color.decode("#DD2C00"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                closeBtn.setBackground(Color.decode("#DD2C00"));
                closeBtn.setForeground(Color.WHITE);
            }
        });

        // Đặt vị trí
        idLabel.setBounds(20, 10, 200, 20);
        idField.setBounds(20, 35, 500, 30);
        modelLabel.setBounds(20, 75, 200, 20);
        modelField.setBounds(20, 100, 500, 30);
        typeLabel.setBounds(20, 145, 200, 20);
        typeComboBox.setBounds(20, 170, 225, 35);
        priceLabel.setBounds(295, 145, 200, 20);
        priceField.setBounds(295, 170, 225, 35);
        statusLabel.setBounds(20, 215, 200, 20);
        statusComboBox.setBounds(20, 240, 225, 35);

        // Thêm vào panel
        panel.add(idLabel);
        panel.add(idField);
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
        panel.add(closeBtn);

        return panel;
    }

    private void loadHardwareDetails() {
        switch (hardwareType) {
            case "Rom":
                Roms rom = romBLL.getRomById(hardwareId);
                if (rom != null) loadRomDetails(rom);
                break;
            case "CPU":
                Cpus cpu = cpuBLL.getCpuById(hardwareId);
                if (cpu != null) loadCpuDetails(cpu);
                break;
            case "GPU":
                Gpus gpu = gpuBLL.getGpubyId(hardwareId);
                if (gpu != null) loadGpuDetails(gpu);
                break;
            case "Mainboard":
                Motherboards motherboard = motherboardBLL.getMotherboardById(hardwareId);
                if (motherboard != null) loadMotherboardDetails(motherboard);
                break;
            case "Mouse":
                Mouse mouse = mouseBLL.getMouseById(hardwareId);
                if (mouse != null) loadMouseDetails(mouse);
                break;
            case "Keyboard":
                Keyboards keyboard = keyboardBLL.getKeyboardById(hardwareId);
                if (keyboard != null) loadKeyboardDetails(keyboard);
                break;
            case "Monitor":
                Monitors monitor = monitorBLL.getMonitorById(hardwareId);
                if (monitor != null) loadMonitorDetails(monitor);
                break;
            case "Headphone":
                Headphones headphone = headphoneBLL.getHeadphoneById(hardwareId);
                if (headphone != null) loadHeadphoneDetails(headphone);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Loại linh kiện không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                dispose();
        }
    }

    private void loadRomDetails(Roms rom) {
        idField.setText(String.valueOf(rom.getRomId()));
        modelField.setText(rom.getModel());
        typeComboBox.setSelectedItem("Rom");
        priceField.setText(String.valueOf(rom.getPrice()));
        statusComboBox.setSelectedItem(rom.getStatus());

        int yOffset = 10;
        addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset, rom.getBrand());
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Loại:", "typeField", yOffset, rom.getType());
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Dung lượng (GB):", "capacityField", yOffset, String.valueOf(rom.getCapacity()));
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset, rom.getPurchaseDate());
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset, rom.getWarrantyExpiry());
        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
    }

    private void loadCpuDetails(Cpus cpu) {
        idField.setText(String.valueOf(cpu.getCpuId()));
        modelField.setText(cpu.getModel());
        typeComboBox.setSelectedItem("CPU");
        priceField.setText(String.valueOf(cpu.getPrice()));
        statusComboBox.setSelectedItem(cpu.getStatus());

        int yOffset = 10;
        addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset, cpu.getBrand());
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Tốc độ (GHz):", "clockSpeedField", yOffset, String.valueOf(cpu.getClockSpeed()));
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Số lõi:", "coresField", yOffset, String.valueOf(cpu.getCores()));
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Số luồng:", "threadsField", yOffset, String.valueOf(cpu.getThreads()));
        yOffset += 40;
        addCheckbox(dynamicFieldsPanel, "GPU tích hợp:", "integratedGpuField", yOffset, cpu.isIntegratedGpu());
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset, cpu.getPurchaseDate());
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset, cpu.getWarrantyExpiry());
        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
    }

    private void loadGpuDetails(Gpus gpu) {
        idField.setText(String.valueOf(gpu.getGpuId()));
        modelField.setText(gpu.getModel());
        typeComboBox.setSelectedItem("GPU");
        priceField.setText(String.valueOf(gpu.getPrice()));
        statusComboBox.setSelectedItem(gpu.getStatus());

        int yOffset = 10;
        addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset, gpu.getBrand());
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "VRAM (GB):", "vramField", yOffset, String.valueOf(gpu.getVram()));
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset, gpu.getPurchaseDate());
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset, gpu.getWarrantyExpiry());
        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
    }

    private void loadMotherboardDetails(Motherboards motherboard) {
        idField.setText(String.valueOf(motherboard.getMotherboardId()));
        modelField.setText(motherboard.getModel());
        typeComboBox.setSelectedItem("Mainboard");
        priceField.setText(String.valueOf(motherboard.getPrice()));
        statusComboBox.setSelectedItem(motherboard.getStatus());

        int yOffset = 10;
        addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset, motherboard.getBrand());
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Socket:", "socketField", yOffset, motherboard.getSocket());
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Chipset:", "chipsetField", yOffset, motherboard.getChipset());
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Khe RAM:", "ramSlotsField", yOffset, String.valueOf(motherboard.getRamSlots()));
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "RAM tối đa (GB):", "maxRamField", yOffset, String.valueOf(motherboard.getMaxRam()));
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Tốc độ RAM (MHz):", "ramSpeedField", yOffset, String.valueOf(motherboard.getRamSpeed()));
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Khe lưu trữ:", "storageSlotsField", yOffset, String.valueOf(motherboard.getStorageSlots()));
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Cổng SATA:", "sataPortsField", yOffset, String.valueOf(motherboard.getSataPorts()));
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Khe M.2:", "m2SlotsField", yOffset, String.valueOf(motherboard.getM2Slots()));
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Dung lượng tối đa (TB):", "maxStorageCapacityField", yOffset, String.valueOf(motherboard.getMaxStorageCapacity()));
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset, motherboard.getPurchaseDate());
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset, motherboard.getWarrantyExpiry());
        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
    }

    private void loadMouseDetails(Mouse mouse) {
        idField.setText(String.valueOf(mouse.getMouseId()));
        modelField.setText(mouse.getModel());
        typeComboBox.setSelectedItem("Mouse");
        priceField.setText(String.valueOf(mouse.getPrice()));
        statusComboBox.setSelectedItem(mouse.getStatus());

        int yOffset = 10;
        addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset, mouse.getBrand());
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset, mouse.getPurchaseDate());
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset, mouse.getWarrantyExpiry());
        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
    }

    private void loadKeyboardDetails(Keyboards keyboard) {
        idField.setText(String.valueOf(keyboard.getKeyboardId()));
        modelField.setText(keyboard.getModel());
        typeComboBox.setSelectedItem("Keyboard");
        priceField.setText(String.valueOf(keyboard.getPrice()));
        statusComboBox.setSelectedItem(keyboard.getStatus());

        int yOffset = 10;
        addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset, keyboard.getBrand());
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset, keyboard.getPurchaseDate());
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset, keyboard.getWarrantyExpiry());
        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
    }

    private void loadMonitorDetails(Monitors monitor) {
        idField.setText(String.valueOf(monitor.getMonitorId()));
        modelField.setText(monitor.getModel());
        typeComboBox.setSelectedItem("Monitor");
        priceField.setText(String.valueOf(monitor.getPrice()));
        statusComboBox.setSelectedItem(monitor.getStatus());

        int yOffset = 10;
        addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset, monitor.getBrand());
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Kích thước (inch):", "sizeField", yOffset, String.valueOf(monitor.getSize()));
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Tần số quét (Hz):", "refreshRateField", yOffset, String.valueOf(monitor.getRefreshRate()));
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset, monitor.getPurchaseDate());
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset, monitor.getWarrantyExpiry());
        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
    }

    private void loadHeadphoneDetails(Headphones headphone) {
        idField.setText(String.valueOf(headphone.getHeadphoneId()));
        modelField.setText(headphone.getModel());
        typeComboBox.setSelectedItem("Headphone");
        priceField.setText(String.valueOf(headphone.getPrice()));
        statusComboBox.setSelectedItem(headphone.getStatus());

        int yOffset = 10;
        addTextField(dynamicFieldsPanel, "Thương hiệu:", "brandField", yOffset, headphone.getBrand());
        yOffset += 40;
        addTextField(dynamicFieldsPanel, "Loại:", "typeField", yOffset, headphone.getType());
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Ngày mua:", "purchaseDateField", yOffset, headphone.getPurchaseDate());
        yOffset += 40;
        addDateField(dynamicFieldsPanel, "Hết bảo hành:", "warrantyExpiryField", yOffset, headphone.getWarrantyExpiry());
        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
    }

    private void addTextField(JPanel panel, String labelText, String fieldName, int yOffset, String value) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        label.setBounds(0, yOffset, 200, 20);
        CustomTextField textField = new CustomTextField(value);
        textField.setBounds(200, yOffset, 300, 30);
        textField.setName(fieldName);
        panel.add(label);
        panel.add(textField);
    }

    private void addDateField(JPanel panel, String labelText, String fieldName, int yOffset, Date value) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        label.setBounds(0, yOffset, 200, 20);
        CustomTextField textField = new CustomTextField(value != null ? value.toString() : "YYYY-MM-DD");
        textField.setForeground(value != null ? Color.BLACK : Color.GRAY);
        textField.setBounds(200, yOffset, 300, 30);
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

    private void addCheckbox(JPanel panel, String labelText, String fieldName, int yOffset, boolean value) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        label.setBounds(0, yOffset, 200, 20);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(value);
        checkBox.setBounds(200, yOffset, 20, 30);
        checkBox.setName(fieldName);
        panel.add(label);
        panel.add(checkBox);
    }

    private void updateHardware() {
        try {
            String model = modelField.getText().trim();
            String priceText = priceField.getText().trim();
            if (model.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm và Giá tiền không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double price = Double.parseDouble(priceText);
            String status = statusComboBox.getSelectedIndex() == 0 ? "Trong kho" : statusComboBox.getSelectedItem().toString();

            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Giá tiền không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Component[] components = dynamicFieldsPanel.getComponents();
            String brand = getFieldValue(components, "brandField");
            Date purchaseDate = parseDate(getFieldValue(components, "purchaseDateField"));
            Date warrantyExpiry = parseDate(getFieldValue(components, "warrantyExpiryField"));

            HashMap<String, Object> updates = new HashMap<>();
            updates.put("model", model);
            updates.put("price", price);
            updates.put("status", status);
            updates.put("brand", brand);
            updates.put("purchase_date", purchaseDate);
            updates.put("warranty_expiry", warrantyExpiry);

            boolean success = false;
            switch (hardwareType) {
                case "Rom":
                    updates.put("type", getFieldValue(components, "typeField"));
                    updates.put("capacity", Integer.parseInt(getFieldValue(components, "capacityField", "0")));
                    success = romBLL.updateRomById(hardwareId, updates);
                    break;
                case "CPU":
                    updates.put("clock_speed", Float.parseFloat(getFieldValue(components, "clockSpeedField", "0")));
                    updates.put("cores", Integer.parseInt(getFieldValue(components, "coresField", "0")));
                    updates.put("threads", Integer.parseInt(getFieldValue(components, "threadsField", "0")));
                    updates.put("integrated_gpu", getCheckboxValue(components, "integratedGpuField"));
                    success = cpuBLL.updateCpuById(hardwareId, updates);
                    break;
                case "GPU":
                    updates.put("vram", Integer.parseInt(getFieldValue(components, "vramField", "0")));
                    success = gpuBLL.updateGpuById(hardwareId, updates);
                    break;
                case "Mainboard":
                    updates.put("socket", getFieldValue(components, "socketField"));
                    updates.put("chipset", getFieldValue(components, "chipsetField"));
                    updates.put("ram_slots", Integer.parseInt(getFieldValue(components, "ramSlotsField", "0")));
                    updates.put("max_ram", Integer.parseInt(getFieldValue(components, "maxRamField", "0")));
                    updates.put("ram_speed", Integer.parseInt(getFieldValue(components, "ramSpeedField", "0")));
                    updates.put("storage_slots", Integer.parseInt(getFieldValue(components, "storageSlotsField", "0")));
                    updates.put("sata_ports", Integer.parseInt(getFieldValue(components, "sataPortsField", "0")));
                    updates.put("m2_slots", Integer.parseInt(getFieldValue(components, "m2SlotsField", "0")));
                    updates.put("max_storage_capacity", Integer.parseInt(getFieldValue(components, "maxStorageCapacityField", "0")));
                    success = motherboardBLL.updateMotherboardById(hardwareId, updates);
                    break;
                case "Mouse":
                    success = mouseBLL.updateMouseById(hardwareId, updates);
                    break;
                case "Keyboard":
                    success = keyboardBLL.updateKeyboardById(hardwareId, updates);
                    break;
                case "Monitor":
                    updates.put("size", Float.parseFloat(getFieldValue(components, "sizeField", "0")));
                    updates.put("refresh_rate", Integer.parseInt(getFieldValue(components, "refreshRateField", "0")));
                    success = monitorBLL.updateMonitorById(hardwareId, updates);
                    break;
                case "Headphone":
                    updates.put("type", getFieldValue(components, "typeField"));
                    success = headphoneBLL.updateHeadphoneById(hardwareId, updates);
                    break;
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật linh kiện thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật linh kiện thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu số không hợp lệ: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new DetailsHardware("Headphone", 1)); // Ví dụ gọi với Headphone ID = 1
//    }
}