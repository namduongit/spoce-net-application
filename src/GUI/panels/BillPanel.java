package GUI.panels;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BLL.*;
import DTO.Accounts;
import DTO.ComputerSessions;
import DTO.Rooms;
import DTO.Staffs;
import GUI.Components.*;
import Utils.Helper.AdjustTableWidth;
import Utils.Helper.CreateComponent;

public class BillPanel extends JPanel{
    private Staffs loginStaff;
    private Accounts loginAccount;

    private CustomPanel headerPanel;
    private CustomPanel controlPanel;
    private CustomPanel dataPanel;
    private CustomTextField monthTextField;
    private CustomTextField yearTextField;
    private JLabel selectionText;
    private CustomCombobox<String> roomTypeCombobox;
    private CustomTable table;
    private CardLayout cardLayout;

    private RoomBLL roomBLL;
    private ComputerSessionBLL computerSessionBLL;
    private ComputerBLL computerBLL;
    private StaffBLL staffBLL;
    private AccountBLL accountBLL;
    private ArrayList<ComputerSessions> sessionList;
    private String[] columnName;
    private DefaultTableCellRenderer renderer;

    public BillPanel(Accounts loginAccount, Staffs loginStaff) {
        this.loginAccount = loginAccount;
        this.loginStaff = loginStaff;
        this.roomBLL = new RoomBLL();
        this.staffBLL = new StaffBLL();
        this.accountBLL = new AccountBLL();
        this.computerBLL = new ComputerBLL();
        this.computerSessionBLL = new ComputerSessionBLL();
        this.sessionList = this.computerSessionBLL.getComputerSessionList();
        this.columnName = new String[]{
                "Mã phiên chơi",
                "Tài khoản nhân viên",
                "Tổng giờ chơi",
                "Tổng tiền",
                "Mã người chơi"
        };
        this.renderer = new DefaultTableCellRenderer();
        this.renderer.setHorizontalAlignment(JLabel.CENTER);
        this.cardLayout = new CardLayout();

        this.initComponents();
    }

    private CustomPanel createHeaderPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("HÓA ĐƠN");
        title.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        title.setBounds(450,25,500,50);

        CustomButton sessionButton = CreateComponent.createButtonNoIcon("Phiên chơi");
        sessionButton.addActionListener(e -> {
            this.cardLayout.show(this.dataPanel, "SessionPanel");
            this.monthTextField.setText("Nhập tháng");
            this.yearTextField.setText("Nhập năm");
            this.roomTypeCombobox.setSelectedIndex(0);
            this.selectionText.setText("Đang chọn: NULL");
            this.resetSessionDataPanel();
        });
        sessionButton.setBounds(20,100,175,35);

        CustomButton billButton = CreateComponent.createButtonNoIcon("Hóa đơn");
        billButton.addActionListener(e -> {
            this.cardLayout.show(this.dataPanel, "BillPanel");
        });
        billButton.setBounds(205,100,165,35);

        panel.add(title);
        panel.add(sessionButton);
        panel.add(billButton);

        return panel;
    }

    private CustomPanel createControlPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel timeLabel = new JLabel("Khoảng thời gian:");
        timeLabel.setBounds(10,10,100,30);

        monthTextField = new CustomTextField("Nhập tháng");
        monthTextField.setBounds(10,38,90,35);
        monthTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (monthTextField.getText().equals("Nhập tháng")) {
                    monthTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (monthTextField.getText().isEmpty()) {
                    monthTextField.setText("Nhập tháng");
                }
            }
        });

        yearTextField = new CustomTextField("Nhập năm");
        yearTextField.setBounds(105,38,90,35);
        yearTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (yearTextField.getText().equals("Nhập năm")) {
                    yearTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (yearTextField.getText().isEmpty()) {
                    yearTextField.setText("Nhập năm");
                }
            }
        });

        JLabel roomTypeLabel = new JLabel("Loại phòng:");
        roomTypeLabel.setBounds(220, 10, 70, 30);

        ArrayList<String> roomTypeList = new ArrayList<>();
        roomTypeList.add("Tất cả");
        for (Rooms x : this.roomBLL.getAllRooms()) {
            roomTypeList.add(x.getRoomName());
        }
        roomTypeCombobox = new CustomCombobox<>(roomTypeList);
        roomTypeCombobox.setBounds(220,38,150,35);

        // Tạo một Button với chữ "Lọc"
        CustomButton filterButton = new CustomButton("Lọc");
        // Chỉnh kích thước của Border
        filterButton.setBorderSize(3);
        // Màu nền
        filterButton.setBackground(Color.decode("#03A9F4"));
        // Màu border
        filterButton.setBorderColor(Color.decode("#03A9F4"));
        // Màu chữ
        filterButton.setForeground(Color.WHITE);
        filterButton.setBounds(755, 38, 100, 35);
        // Tạo hiệu ứng khi hover qua Button và hành động khi click vào button
        filterButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                // Khi Button lọc được click thì sẽ gọi hàm lọc lại bảng
                BillPanel.this.filterSessionList();
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Khi di chuột vào thì Button sẽ đổi màu
                filterButton.setForeground(Color.decode("#03A9F4"));
                filterButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Khi chuột ra khỏi Button thì Button sẽ trở về màu cũ
                filterButton.setForeground(Color.WHITE);
                filterButton.setBackground(Color.decode("#03A9F4"));
            }
        });


        // Khởi tạo Button đặt lại
        CustomButton resetButton = new CustomButton("Đặt lại");
        // Màu nền
        resetButton.setBackground(Color.RED);
        // Màu border
        resetButton.setBorderColor(Color.RED);
        // Màu chữ
        resetButton.setForeground(Color.WHITE);
        // Chỉnh kích thước của Border
        resetButton.setBorderSize(3);
        resetButton.setBounds(860, 38, 100, 35);
        resetButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                // Đặt lại placeholder
                BillPanel.this.monthTextField
                        .setText("Nhập tháng");

                BillPanel.this.yearTextField
                        .setText("Nhập năm");

                // Đặt lại trạng thái tất cả
                BillPanel.this.roomTypeCombobox
                        .setSelectedIndex(0);

                // Cập nhật lại bảng với dữ liệu là toàn bộ máy tính
                BillPanel.this.resetSessionDataPanel();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Khi di chuột vào thì Button sẽ đổi màu
                resetButton.setForeground(Color.RED);
                resetButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Khi chuột ra khỏi Button thì Button sẽ trở về màu cũ
                resetButton.setForeground(Color.WHITE);
                resetButton.setBackground(Color.RED);
            }
        });

        // Khởi tạo Button in hóa đơn
        CustomButton printButton = new CustomButton("In hóa đơn");
        // Màu nền
        printButton.setBackground(Color.green);
        // Màu border
        printButton.setBorderColor(Color.green);
        // Màu chữ
        printButton.setForeground(Color.WHITE);
        // Chỉnh kích thước của Border
        printButton.setBorderSize(3);
        printButton.setBounds(965, 38, 100, 35);
        printButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Khi di chuột vào thì Button sẽ đổi màu
                printButton.setForeground(Color.green);
                printButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Khi chuột ra khỏi Button thì Button sẽ trở về màu cũ
                printButton.setForeground(Color.WHITE);
                printButton.setBackground(Color.green);
            }
        });

        // JLabel hiển thị máy đang được chọn
        selectionText = new JLabel("Đang chọn: NULL");
        selectionText.setFont(
                new Font("Sans-serif", Font.BOLD, 12)
        );
        selectionText.setBounds(755,10,300,20);

        panel.add(timeLabel);
        panel.add(monthTextField);
        panel.add(yearTextField);
        panel.add(roomTypeLabel);
        panel.add(roomTypeCombobox);
        panel.add(filterButton);
        panel.add(selectionText);
        panel.add(resetButton);
        panel.add(printButton);

        return panel;
    }

    // Phương thức reset lại table
    private void resetSessionDataPanel() {
        this.sessionList = this.computerSessionBLL.getComputerSessionList();
        Object[][] data = this.createSessionData(this.sessionList);
        DefaultTableModel model = new DefaultTableModel(data, this.columnName);
        this.table.setModel(model);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(this.table);
        this.table.getColumnModel()
                  .getColumn(0)
                  .setPreferredWidth(100);

        for (int i=0; i<this.table.getColumnCount(); i++) {
            this.table.getColumnModel()
                      .getColumn(i)
                      .setCellRenderer(this.renderer);
        }
    }

    // Phương thức cập nhật table dựa trên mảng đưa vào
    private void updateSessionDataPanel(ArrayList<ComputerSessions> list) {
        Object[][] data = this.createSessionData(list);
        DefaultTableModel model = new DefaultTableModel(data, this.columnName);
        this.table.setModel(model);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(this.table);
        this.table.getColumnModel()
                  .getColumn(0)
                  .setPreferredWidth(100);
    }

    private void filterSessionList() {
        String month = this.monthTextField.getText().equals("Nhập tháng")
                                        ? ""
                                        : this.monthTextField.getText();

        String year = this.yearTextField.getText().equals("Nhập năm")
                                        ? ""
                                        : this.yearTextField.getText();

        String roomType = this.roomTypeCombobox.getSelectedItem().toString();

        if (month.isEmpty() && year.isEmpty() && roomType.equals("Tất cả")) {
            this.resetSessionDataPanel();
        } else {

            // Nếu chỉ nhập một trong hai tháng hoặc năm thì thông báo lỗi
            if ((month.isEmpty() && !year.isEmpty()) || (!month.isEmpty() && year.isEmpty())) {
                JOptionPane.showMessageDialog(
                        null,
                        "Vui lòng nhập đủ tháng và năm!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Trường hợp không nhập tháng, năm thì không kiểm tra tháng, năm
            if (!month.isEmpty() && !year.isEmpty()) {
                if (!this.checkDate(month, year)) {
                    return;
                }
            }

            // Nếu người dùng không nhập tháng năm (rỗng) thì cho bằng -1
            int monthInt = month.isEmpty() ? -1 : Integer.parseInt(month);
            int yearInt = year.isEmpty() ? -1 : Integer.parseInt(year);

            // Đọc lại dữ liệu trước khi lọc
            this.sessionList = this.computerSessionBLL.getComputerSessionList();
            List<ComputerSessions> list = this.sessionList.stream()
                                                          .filter(session -> month.isEmpty() || session.getStartTime().toLocalDate().getMonthValue() == monthInt)
                                                          .filter(session -> year.isEmpty() || session.getStartTime().toLocalDate().getYear() == yearInt)
                                                          .filter(session -> roomType.equals("Tất cả") || roomBLL.getRoomById(computerBLL.getComputerById(session.getComputerId()).getRoomId()).getRoomName().equals(roomType))
                                                          .collect(Collectors.toList());

            // Sau khi lọc sẽ cập nhật vào table
            this.sessionList = new ArrayList<>(list);
            this.updateSessionDataPanel(this.sessionList);
        }

        // Căn giữa lại các cột
        for (int i=0; i<this.table.getColumnCount(); i++) {
            this.table.getColumnModel().getColumn(i).setCellRenderer(this.renderer);
        }
    }

    // Phương thức kiểm tra tháng, năm
    private boolean checkDate(String month, String year) {
        try {
            int monthInt = Integer.parseInt(month);
            int yearInt = Integer.parseInt(year);

            if (!(monthInt >= 1 && monthInt <= 12)) {
                throw new NumberFormatException();
            }

            if (yearInt < 0) {
                throw new NumberFormatException();
            }

            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Định dạng của tháng và năm không đúng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    private void initComponents() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        headerPanel = this.createHeaderPanel();
        controlPanel = this.createControlPanel();
        dataPanel = this.createDataPanel();

        headerPanel.setBounds(10, 0, 1080, 150);
        controlPanel.setBounds(10, 160, 1080, 95);
        dataPanel.setBounds(10,265,1080,453);

        this.add(headerPanel);
        this.add(controlPanel);
        this.add(dataPanel);
    }

    private Object[][] createSessionData(ArrayList<ComputerSessions> list) {
        Object[][] data = new Object[this.sessionList.size()][5];

        for (int i=0; i<list.size(); i++) {
            data[i][0] = list.get(i).getSessionId();
            data[i][1] = this.accountBLL.getAccountById(
                    this.staffBLL.getStaffById(list.get(i)
                                                   .getStaffId())
                                                   .getAccountId()
            ).getUsername();
            data[i][2] = list.get(i).getDuration() * 1.0 / 60;
            data[i][3] = list.get(i).getTotalCost() + "đ";
            data[i][4] = list.get(i).getPlayerId() == null
                                            ? "Mở máy"
                                            : list.get(i).getPlayerId();
        }

        return data;
    }

    private CustomPanel createDataPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(this.cardLayout);

        CustomPanel sessionPanel = this.createDataPanelForSession();
        CustomPanel billPanel = this.createDataPanelForBill();

        panel.add(sessionPanel, "SessionPanel");
        panel.add(billPanel, "BillPanel");
        return panel;
    }

    // Phương thức tạo panel cho Bill
    private CustomPanel createDataPanelForBill() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        return panel;
    }

    // Phương thức tạo panel cho Session
    private CustomPanel createDataPanelForSession() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        // Đọc dữ liệu mới nhất
        this.sessionList = this.computerSessionBLL.getComputerSessionList();
        DefaultTableModel model = new DefaultTableModel(this.createSessionData(this.sessionList), this.columnName);
        this.table = new CustomTable(model);

        // Tắt tự động điều chỉnh kích thước của table
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Gọi hàm căn lại width các cột dựa trên giá trị dài nhất trong cột
        AdjustTableWidth.automaticallyAdjustTableWidths(this.table);

        // Điều chỉnh width của cột ID
        this.table.getColumnModel().getColumn(0).setPreferredWidth(100);

        // THêm sự kiện khi chọn một dòng nào đó trong table
        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (table.getSelectedRow() != -1) {
                        BillPanel.this.selectionText.setText("Đang chọn phiên chơi " + table.getValueAt(table.getSelectedRow(), 0));
                    }
                }
            }
        });

        // Thêm bảng vào ScrollPane
        JScrollPane scrollPane = new CustomScrollPane(this.table);
        scrollPane.setBounds(0,0,1080,453);

        panel.add(scrollPane);


        return panel;
    }
}
