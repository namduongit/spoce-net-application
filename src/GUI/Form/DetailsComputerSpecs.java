package GUI.Form;

import BLL.ComputerBLL;
import DTO.Computers;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomTextField;
import Utils.Helper.ChangeMinToDate;
import Utils.Helper.Comon;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailsComputerSpecs extends JFrame {
    private int computerId;

    private ComputerBLL computerBLL;

    public DetailsComputerSpecs(int computerId) {
        this.computerId = computerId;
        this.computerBLL = new ComputerBLL();
        this.initComponents();
    }

    private void initComponents() {
        this.setTitle("Chi tiết máy");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(550,900);
        this.setResizable(false);

        JPanel content = this.createContent();

        this.add(content);
        this.setLocationRelativeTo(null);
    }

    private JPanel createContent() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        HashMap<String, Object> map = this.computerBLL.getComputerInfoAndSpec(this.computerId);

        JLabel roomIdAndNameLabel = new JLabel("Mã máy tính - Tên máy tính");
        roomIdAndNameLabel.setBounds(20, 10, 200, 20);

        CustomTextField roomIdAndNameTextField = new CustomTextField(map.get("computer_id") + " - " + map.get("name"));
        roomIdAndNameTextField.setBounds(20, 35, 500, 30);
        roomIdAndNameTextField.setFocusable(false);
        roomIdAndNameTextField.setEditable(false);

        JLabel computerIdAndNameLabel = new JLabel("Giá một giờ chơi");
        computerIdAndNameLabel.setBounds(20, 75, 100, 20);

        CustomTextField computerIdAndNameTextField = new CustomTextField(Comon.formatMoney(map.get("price_per_hour")+""));
        computerIdAndNameTextField.setBounds(20, 100, 245, 30);
        computerIdAndNameTextField.setFocusable(false);
        computerIdAndNameTextField.setEditable(false);

        JLabel computerPriceLabel = new JLabel("Trạng thái");
        computerPriceLabel.setBounds(275, 75, 150, 20);

        CustomTextField computerPriceTextField = new CustomTextField(map.get("status")+"");
        computerPriceTextField.setBounds(275, 100, 245, 30);
        computerPriceTextField.setFocusable(false);
        computerPriceTextField.setEditable(false);

        JLabel startTimeLabel = new JLabel("Bo mạch chủ");
        startTimeLabel.setBounds(20, 140, 100, 20);

        CustomTextField startTimeTextField = new CustomTextField(map.get("motherboard_name")+"");
        startTimeTextField.setBounds(20, 165, 500, 30);
        startTimeTextField.setFocusable(false);
        startTimeTextField.setEditable(false);

        JLabel endTimeLabel = new JLabel("CPU");
        endTimeLabel.setBounds(20, 205, 150, 20);

        CustomTextField endTimeTextField = new CustomTextField(map.get("cpu_name")+"");
        endTimeTextField.setBounds(20, 230, 500, 30);
        endTimeTextField.setFocusable(false);
        endTimeTextField.setEditable(false);

        JLabel totalHoursPlayedLabel = new JLabel("GPU");
        totalHoursPlayedLabel.setBounds(20, 270, 200, 20);

        CustomTextField totalHoursPlayedTextField = new CustomTextField(map.get("gpu_name")+"");
        totalHoursPlayedTextField.setBounds(20, 295, 500, 30);
        totalHoursPlayedTextField.setFocusable(false);
        totalHoursPlayedTextField.setEditable(false);

        JLabel totalBillLabel = new JLabel("PSU");
        totalBillLabel.setBounds(20, 335, 200, 20);

        CustomTextField totalBillTextField = new CustomTextField(map.get("psu_name")+"");
        totalBillTextField.setBounds(20, 360, 500, 30);
        totalBillTextField.setFocusable(false);
        totalBillTextField.setEditable(false);

        JLabel mouseLabel = new JLabel("Chuột");
        mouseLabel.setBounds(20, 400, 200, 20);

        CustomTextField mouseTextField = new CustomTextField(map.get("mouse_name") == null ? "Chưa có chuột" : map.get("mouse_name")+"");
        mouseTextField.setBounds(20, 425, 500, 30);
        mouseTextField.setFocusable(false);
        mouseTextField.setEditable(false);

        JLabel keyboardLabel = new JLabel("Bàn phím");
        keyboardLabel.setBounds(20, 465, 200, 20);

        CustomTextField keyboardTextField = new CustomTextField(map.get("keyboard_name") == null ? "Chưa có bàn phím" : map.get("keyboard_name")+"");
        keyboardTextField.setBounds(20, 490, 500, 30);
        keyboardTextField.setFocusable(false);
        keyboardTextField.setEditable(false);

        JLabel monitorLabel = new JLabel("Màn hình");
        monitorLabel.setBounds(20, 530, 200, 20);

        CustomTextField monitorTextField = new CustomTextField(map.get("monitor_name") == null ? "Chưa có màn hình" : map.get("monitor_name")+"");
        monitorTextField.setBounds(20, 555, 500, 30);
        monitorTextField.setFocusable(false);
        monitorTextField.setEditable(false);

        JLabel headphoneLabel = new JLabel("Tai nghe");
        headphoneLabel.setBounds(20, 595, 200, 20);

        CustomTextField headphoneTextField = new CustomTextField(map.get("headphone_name") == null ? "Chưa có tai nghe" : map.get("headphone_name")+"");
        headphoneTextField.setBounds(20, 620, 500, 30);
        headphoneTextField.setFocusable(false);
        headphoneTextField.setEditable(false);

        JLabel romLabel = new JLabel("ROM");
        romLabel.setBounds(20, 660, 200, 20);

        CustomTextField romTextField = new CustomTextField(map.get("rom_name") == null ? "Chưa có ROM" : map.get("rom_name")+"");
        romTextField.setBounds(20, 685, 500, 30);
        romTextField.setFocusable(false);
        romTextField.setEditable(false);

        JLabel ramLabel = new JLabel("RAM");
        ramLabel.setBounds(20, 725, 200, 20);

        HashMap<String, Object> ram = (HashMap<String, Object>) map.get("ram");
        ArrayList<String> ramList = new ArrayList<>();
        for (Object x : ram.values()) {
            ramList.add(x.toString());
        }
        CustomCombobox<String> ramCombobox = new CustomCombobox<>(ramList);
        ramCombobox.setBounds(20, 750, 500, 30);

        HashMap<String, Object> storage = (HashMap<String, Object>) map.get("storage");
        ArrayList<String> storageList = new ArrayList<>();
        for (Object x : storage.values()) {
            storageList.add(x.toString());
        }
        JLabel storageLabel = new JLabel("Bộ nhớ");
        storageLabel.setBounds(20, 790, 200, 20);

        CustomCombobox<String> storageCombobox = new CustomCombobox<>(storageList);
        storageCombobox.setBounds(20, 815, 500, 30);

        panel.add(roomIdAndNameLabel);
        panel.add(roomIdAndNameTextField);
        panel.add(computerIdAndNameLabel);
        panel.add(computerIdAndNameTextField);
        panel.add(computerPriceLabel);
        panel.add(computerPriceTextField);
        panel.add(startTimeLabel);
        panel.add(endTimeLabel);
        panel.add(startTimeTextField);
        panel.add(endTimeTextField);
        panel.add(totalHoursPlayedLabel);
        panel.add(totalHoursPlayedTextField);
        panel.add(totalBillLabel);
        panel.add(totalBillTextField);
        panel.add(mouseLabel);
        panel.add(mouseTextField);
        panel.add(keyboardLabel);
        panel.add(keyboardTextField);
        panel.add(monitorLabel);
        panel.add(monitorTextField);
        panel.add(headphoneLabel);
        panel.add(headphoneTextField);
        panel.add(romLabel);
        panel.add(romTextField);
        panel.add(ramLabel);
        panel.add(ramCombobox);
        panel.add(storageCombobox);
        panel.add(storageLabel);

        return panel;
    }
}
