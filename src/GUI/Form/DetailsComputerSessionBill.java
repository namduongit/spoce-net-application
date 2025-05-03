package GUI.Form;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import BLL.ComputerSessionBLL;
import GUI.Components.CustomTextField;
import Utils.Helper.ChangeMinToDate;
import Utils.Helper.Comon;

import java.security.cert.PKIXRevocationChecker;
import java.util.HashMap;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class DetailsComputerSessionBill extends JFrame {
    private int sessionId;

    private ComputerSessionBLL computerSessionBLL;

    public DetailsComputerSessionBill(int sessionId) {
        this.sessionId = sessionId;
        this.computerSessionBLL = new ComputerSessionBLL();
        
        this.initComponents();
    }

    private void initComponents() {
        this.setTitle("Chi tiết hóa đơn phiên chơi");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(550,460);
        this.setResizable(false);

        JPanel content = this.createContent();

        this.add(content);
        this.setLocationRelativeTo(null);
    }

    private JPanel createContent() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        HashMap<String, Object> map = this.computerSessionBLL.getComputerInfoAndRoomInfoOfSession(this.sessionId);

        JLabel roomIdAndNameLabel = new JLabel("Mã phòng chơi - Tên phòng chơi");
        roomIdAndNameLabel.setBounds(20, 10, 200, 20);
        
        CustomTextField roomIdAndNameTextField = new CustomTextField(map.get("room_id") + " - " + map.get("room_name"));
        roomIdAndNameTextField.setBounds(20, 35, 500, 30);
        roomIdAndNameTextField.setFocusable(false);
        roomIdAndNameTextField.setEditable(false);

        JLabel computerIdAndNameLabel = new JLabel("Mã máy tính - Tên máy tính");
        computerIdAndNameLabel.setBounds(20, 75, 200, 20);

        CustomTextField computerIdAndNameTextField = new CustomTextField(map.get("computer_id") + " - " + map.get("name"));
        computerIdAndNameTextField.setBounds(20, 100, 500, 30);
        computerIdAndNameTextField.setFocusable(false);
        computerIdAndNameTextField.setEditable(false);

        JLabel computerPriceLabel = new JLabel("Giá một giờ chơi");
        computerPriceLabel.setBounds(20, 140, 200, 20);

        CustomTextField computerPriceTextField = new CustomTextField(Comon.formatMoney(map.get("price_per_hour")+""));
        computerPriceTextField.setBounds(20, 165, 500, 30);
        computerPriceTextField.setFocusable(false);
        computerPriceTextField.setEditable(false);

        JLabel startTimeLabel = new JLabel("Thời gian bắt đầu");
        startTimeLabel.setBounds(20, 205, 100, 20);

        JLabel endTimeLabel = new JLabel("Thời gian kết thúc");
        endTimeLabel.setBounds(275, 205, 150, 20);

        CustomTextField startTimeTextField = new CustomTextField(map.get("start_time")+"");
        startTimeTextField.setBounds(20, 230, 245, 30);
        startTimeTextField.setFocusable(false);
        startTimeTextField.setEditable(false);

        CustomTextField endTimeTextField = new CustomTextField(map.get("end_time")+"");
        endTimeTextField.setBounds(275, 230, 245, 30);
        endTimeTextField.setFocusable(false);
        endTimeTextField.setEditable(false);

        JLabel totalHoursPlayedLabel = new JLabel("Tổng thời gian chơi");
        totalHoursPlayedLabel.setBounds(20, 270, 200, 20);

        CustomTextField totalHoursPlayedTextField = new CustomTextField(ChangeMinToDate.changeData((int)map.get("duration")));
        totalHoursPlayedTextField.setBounds(20, 295, 500, 30);
        totalHoursPlayedTextField.setFocusable(false);
        totalHoursPlayedTextField.setEditable(false);

        JLabel totalBillLabel = new JLabel("Tổng tiền hóa đơn");
        totalBillLabel.setBounds(20, 335, 200, 20);

        CustomTextField totalBillTextField = new CustomTextField(Comon.formatMoney(map.get("total_cost")+""));
        totalBillTextField.setBounds(20, 360, 500, 30);
        totalBillTextField.setFocusable(false);
        totalBillTextField.setEditable(false);

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

        return panel;
    }
}
