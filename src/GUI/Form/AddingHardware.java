package GUI.Form;

import BLL.*;
import DTO.*;
import GUI.Components.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddingHardware extends JFrame {
    private CustomPanel mainPanel;
    private ComputerBLL computerBLL;
    private MotherboardBLL motherboardBLL;
    private CpuBLL cpuBLL;
    private GpuBLL gpuBLL;
    private MouseBLL mouseBLL;
    private KeyboardBLL keyboardBLL;
    private MonitorBLL monitorBLL;
    private HeadphoneBLL headphoneBLL;
    private RomBLL romBLL;

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

        setTitle("Thêm Linh Kiện Mới");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        mainPanel = new CustomPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode("#ECF0F1"));

        // Tiêu đề
        JLabel title = new JLabel("THÊM LINH KIỆN MỚI");
        title.setFont(new Font("Sans-serif", Font.BOLD, 20));
        title.setForeground(Color.decode("#34495E"));
        title.setBounds(100, 20, 250, 30);
        mainPanel.add(title);

        // ID sản phẩm
        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        idLabel.setBounds(20, 70, 100, 30);
        CustomTextField idField = new CustomTextField("Nhập ID");
        idField.setBounds(120, 70, 250, 35);
        mainPanel.add(idLabel);
        mainPanel.add(idField);

        // Tên sản phẩm
        JLabel nameLabel = new JLabel("Tên Sản Phẩm:");
        nameLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        nameLabel.setBounds(20, 120, 100, 30);
        CustomTextField nameField = new CustomTextField("Nhập tên sản phẩm");
        nameField.setBounds(120, 120, 250, 35);
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);

        // Loại sản phẩm
        JLabel typeLabel = new JLabel("Loại:");
        typeLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        typeLabel.setBounds(20, 170, 100, 30);
        String[] typeList = {"Ram", "CPU", "GPU", "Mainboard", "Mouse", "Keyboard", "Monitor", "Headphone"};
        CustomCombobox<String> typeComboBox = new CustomCombobox<>(typeList);
        typeComboBox.setBounds(120, 170, 250, 35);
        mainPanel.add(typeLabel);
        mainPanel.add(typeComboBox);

        // Giá tiền
        JLabel priceLabel = new JLabel("Giá Tiền:");
        priceLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        priceLabel.setBounds(20, 220, 100, 30);
        CustomTextField priceField = new CustomTextField("Nhập giá tiền");
        priceField.setBounds(120, 220, 250, 35);
        mainPanel.add(priceLabel);
        mainPanel.add(priceField);

        // Trạng thái
        JLabel statusLabel = new JLabel("Trạng thái:");
        statusLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        statusLabel.setBounds(20, 270, 100, 30);
        String[] statusList = {"Trong kho", "Đang sử dụng", "Thiếu linh kiện", "Bảo trì", "Hỏng"};
        CustomCombobox<String> statusComboBox = new CustomCombobox<>(statusList);
        statusComboBox.setBounds(120, 270, 250, 35);
        mainPanel.add(statusLabel);
        mainPanel.add(statusComboBox);

        // Nút Lưu
        CustomButton saveButton = new CustomButton("Lưu");
        saveButton.setBackground(Color.decode("#388E3C"));
        saveButton.setBorderColor(Color.decode("#388E3C"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBounds(80, 350, 100, 40);
        saveButton.setBorderSize(3);
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(Color.decode("#27AE60"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setBackground(Color.decode("#388E3C"));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String type = (String) typeComboBox.getSelectedItem();
                    int price = Integer.parseInt(priceField.getText());
                    String status = (String) statusComboBox.getSelectedItem();

                    // Kiểm tra dữ liệu đầu vào
                    if (name.isEmpty() || name.equals("Nhập tên sản phẩm")) {
                        JOptionPane.showMessageDialog(AddingHardware.this, "Tên sản phẩm không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (price < 0) {
                        JOptionPane.showMessageDialog(AddingHardware.this, "Giá tiền không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    boolean success = addHardware(id, name, type, price, status);
                    if (success) {
                        JOptionPane.showMessageDialog(AddingHardware.this, "Thêm linh kiện thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        dispose(); // Đóng form sau khi thêm thành công
                    } else {
                        JOptionPane.showMessageDialog(AddingHardware.this, "Thêm linh kiện thất bại! ID có thể đã tồn tại.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddingHardware.this, "ID và Giá Tiền phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Nút Hủy
        CustomButton cancelButton = new CustomButton("Hủy");
        cancelButton.setBackground(Color.decode("#E74C3C"));
        cancelButton.setBorderColor(Color.decode("#E74C3C"));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBounds(220, 350, 100, 40);
        cancelButton.setBorderSize(3);
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelButton.setBackground(Color.decode("#C0392B"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelButton.setBackground(Color.decode("#E74C3C"));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); // Đóng form khi hủy
            }
        });

        mainPanel.add(saveButton);
        mainPanel.add(cancelButton);
        add(mainPanel);
    }

    private boolean addHardware(int id, String name, String type, int price, String status) {
//        switch (type) {
//            case "Ram":
//                Roms ram = new Roms(id, name);
//                ram.setPrice(price); // Giả định Roms có setPrice
//                ram.setStatus(status); // Giả định Roms có setStatus
//                return romBLL.addRom(ram); // Giả định RomBLL có addRom
//            case "CPU":
//                Cpus cpu = new Cpus(id, name);
//                cpu.setPrice(price);
//                cpu.setStatus(status);
//                return cpuBLL.addCpu(cpu);
//            case "GPU":
//                Gpus gpu = new Gpus(id, name);
//                gpu.setPrice(price);
//                gpu.setStatus(status);
//                return gpuBLL.addGpu(gpu);
//            case "Mainboard":
//                Motherboards motherboard = new Motherboards(id, name);
//                motherboard.setPrice(price);
//                motherboard.setStatus(status);
//                return motherboardBLL.addMotherboard(motherboard);
//            case "Mouse":
//                Mouse mouse = new Mouse(id, name);
//                mouse.setPrice(price);
//                mouse.setStatus(status);
//                return mouseBLL.addMouse(mouse);
//            case "Keyboard":
//                Keyboards keyboard = new Keyboards(id, name);
//                keyboard.setPrice(price);
//                keyboard.setStatus(status);
//                return keyboardBLL.addKeyboard(keyboard);
//            case "Monitor":
//                Monitors monitor = new Monitors(id, name);
//                monitor.setPrice(price);
//                monitor.setStatus(status);
//                return monitorBLL.addMonitor(monitor);
//            case "Headphone":
//                Headphones headphone = new Headphones(id, name);
//                headphone.setPrice(price);
//                headphone.setStatus(status);
//                return headphoneBLL.addHeadphone(headphone);
//            default:
//                return false;
//        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddingHardware frame = new AddingHardware();
            frame.setVisible(true);
        });
    }
}