package GUI.panels;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BLL.*;
import DTO.Accounts;
import DTO.Categories;
import DTO.ComputerSessions;
import DTO.FoodBills;
import DTO.Rooms;
import DTO.Staffs;
import GUI.Components.*;
import GUI.Form.DetailsComputerSessionBill;
import GUI.Form.DetailsFoodOrderBill;
import Utils.Helper.*;

public class BillPanel extends JPanel {
    @SuppressWarnings("unused")
    private Staffs loginStaff;
    @SuppressWarnings("unused")
    private Accounts loginAccount;

    private CustomPanel headerPanel;
    private CustomPanel controlPanel;
    private CustomPanel dataPanel;

    private CardLayout dataCardLayout;
    private CardLayout filterCardLayout;

    // Phần danh cho phiên chơi
    private CustomTextField monthTextFieldSession;
    private CustomTextField yearTextFieldSession;
    private JLabel selectionTextSession;
    private CustomCombobox<String> roomTypeCombobox;
    private CustomTable tableSession;

    private RoomBLL roomBLL;
    private ComputerBLL computerBLL;
    private StaffBLL staffBLL;
    private AccountBLL accountBLL;
    private CategoryBLL categoryBLL;

    private ComputerSessionBLL computerSessionBLL;
    private ArrayList<ComputerSessions> sessionList;
    private String[] columnNameSession;
    private DefaultTableCellRenderer rendererSession;

    // Phần dành cho hóa đơn
    private CustomTextField monthTextFieldFoodBill;
    private CustomTextField yearTextFieldFoodBill;
    private JLabel selectionTextFoodBill;

    private CustomCombobox<String> foodBillTypeCombobox;
    private CustomCombobox<String> foodBillStatusCombobox;
    private CustomTable tableFoodBill;

    private FoodBillBLL foodBillBLL;
    private FoodOrderBLL foodOrderBLL;
    private FoodBLL foodBLL;
    private ArrayList<FoodBills> foodBillList;
    private String[] columnNameFoodBill;
    private DefaultTableCellRenderer rendererFoodBill;

    public BillPanel(Accounts loginAccount, Staffs loginStaff) {
        this.loginAccount = loginAccount;
        this.loginStaff = loginStaff;

        this.filterCardLayout = new CardLayout();
        this.dataCardLayout = new CardLayout();

        this.staffBLL = new StaffBLL();
        this.accountBLL = new AccountBLL();

        this.roomBLL = new RoomBLL();
        this.computerBLL = new ComputerBLL();
        this.computerSessionBLL = new ComputerSessionBLL();
        this.foodOrderBLL = new FoodOrderBLL();
        this.foodBLL = new FoodBLL();

        this.categoryBLL = new CategoryBLL();

        this.sessionList = this.computerSessionBLL.getComputerSessionList();
        this.columnNameSession = new String[] {
                "Mã phiên chơi",
                "Tài khoản nhân viên",
                "Tổng giờ chơi",
                "Tổng tiền",
                "Mã người chơi"
        };
        this.rendererSession = new DefaultTableCellRenderer();
        this.rendererSession.setHorizontalAlignment(JLabel.CENTER);

        // Phần chuẩn bị cho foodBillPanel
        this.foodBillBLL = new FoodBillBLL();
        this.foodBillList = this.foodBillBLL.getFoodBillList();
        this.columnNameFoodBill = new String[] {
                "Mã hóa đơn",
                "Tài khoản nhân viên",
                "Tổng tiền",
                "Trạng thái",
                "Ngày tạo"
        };
        this.rendererFoodBill = new DefaultTableCellRenderer();
        this.rendererFoodBill.setHorizontalAlignment(JLabel.CENTER);

        String[] foodBillStatus = new String[] { "Tất cả", "Chưa xử lý", "Đã xử lý", "Đã hủy" };
        this.foodBillStatusCombobox = new CustomCombobox<>(foodBillStatus);

        this.initComponents();
    }

    private CustomPanel createHeaderPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("HÓA ĐƠN");
        title.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        title.setBounds(450, 25, 500, 50);

        CustomButton sessionButton = CreateComponent.createButtonNoIcon("Phiên chơi");
        sessionButton.addActionListener(e -> {
            this.dataCardLayout.show(this.dataPanel, "SessionPanel");
            this.filterCardLayout.show(this.controlPanel, "SessionPanel");
            this.monthTextFieldSession.setText("Nhập tháng");
            this.yearTextFieldSession.setText("Nhập năm");
            this.roomTypeCombobox.setSelectedIndex(0);
            this.selectionTextSession.setText("Đang chọn: NULL");
            this.resetSessionDataPanel();
        });
        sessionButton.setBounds(20, 100, 175, 35);

        CustomButton billButton = CreateComponent.createButtonNoIcon("Hóa đơn");
        billButton.addActionListener(e -> {
            this.dataCardLayout.show(this.dataPanel, "BillPanel");
            this.filterCardLayout.show(this.controlPanel, "BillPanel");
            this.monthTextFieldFoodBill.setText("Nhập tháng");
            this.yearTextFieldFoodBill.setText("Nhập năm");
            this.foodBillStatusCombobox.setSelectedIndex(0);
            this.selectionTextFoodBill.setText("Đang chọn: NULL");
            this.resetFoodBillDataPanel();
        });
        billButton.setBounds(205, 100, 165, 35);

        panel.add(title);
        panel.add(sessionButton);
        panel.add(billButton);

        return panel;
    }

    private CustomPanel createFoodBillControlPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel timeLabel = new JLabel("Khoảng thời gian:");
        timeLabel.setBounds(10, 10, 100, 30);

        monthTextFieldFoodBill = new CustomTextField("Nhập tháng");
        monthTextFieldFoodBill.setBounds(10, 38, 90, 35);
        monthTextFieldFoodBill.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (monthTextFieldFoodBill.getText().equalsIgnoreCase("Nhập tháng")) {
                    monthTextFieldFoodBill.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (monthTextFieldFoodBill.getText().isEmpty()) {
                    monthTextFieldFoodBill.setText("Nhập tháng");
                }
            }
        });

        yearTextFieldFoodBill = new CustomTextField("Nhập năm");
        yearTextFieldFoodBill.setBounds(105, 38, 90, 35);
        yearTextFieldFoodBill.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (yearTextFieldFoodBill.getText().equalsIgnoreCase("Nhập năm")) {
                    yearTextFieldFoodBill.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (yearTextFieldFoodBill.getText().isEmpty()) {
                    yearTextFieldFoodBill.setText("Nhập năm");
                }
            }
        });

        JLabel foodBillCategoryLabel = new JLabel("Loại sản phẩm:");
        foodBillCategoryLabel.setBounds(220, 10, 100, 30);

        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("Tất cả");
        for (Categories x : this.categoryBLL.getAllCategories()) {
            categoryList.add(x.getCategoryId() + " - " + x.getName());
        }
        this.foodBillTypeCombobox = new CustomCombobox<>(categoryList);
        this.foodBillTypeCombobox.setBounds(220, 38, 150, 35);

        // Trạng thái hóa đơn
        JLabel foodBillStatusLabel = new JLabel("Trạng thái");
        foodBillStatusLabel.setBounds(380, 10, 100, 30);

        this.foodBillStatusCombobox.setBounds(380, 38, 150, 35);

        // Tạo hiệu ứng khi hover qua Button và hành động khi click vào button
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
                BillPanel.this.filterFoodBillList();

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
        resetButton.setBackground(Color.decode("#424242"));
        // Màu border
        resetButton.setBorderColor(Color.decode("#424242"));
        // Màu chữ
        resetButton.setForeground(Color.WHITE);
        // Chỉnh kích thước của Border
        resetButton.setBorderSize(3);
        resetButton.setBounds(860, 38, 100, 35);
        resetButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                // Đặt lại placeholder
                BillPanel.this.monthTextFieldFoodBill
                        .setText("Nhập tháng");

                BillPanel.this.yearTextFieldFoodBill
                        .setText("Nhập năm");
                BillPanel.this.foodBillTypeCombobox.setSelectedIndex(0);
                BillPanel.this.foodBillStatusCombobox.setSelectedIndex(0);
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
                resetButton.setForeground(Color.decode("#424242"));
                resetButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Khi chuột ra khỏi Button thì Button sẽ trở về màu cũ
                resetButton.setForeground(Color.WHITE);
                resetButton.setBackground(Color.decode("#424242"));
            }
        });

        // Khởi tạo Button in hóa đơn
        CustomButton printButton = new CustomButton("In hóa đơn");
        // Màu nền
        printButton.setBackground(Color.decode("#4527A0"));
        // Màu border
        printButton.setBorderColor(Color.decode("#4527A0"));
        // Màu chữ
        printButton.setForeground(Color.WHITE);
        // Chỉnh kích thước của Border
        printButton.setBorderSize(3);
        printButton.setBounds(965, 38, 100, 35);
        printButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Đã ấn vào nút in hóa đơn trên giao diện");
                BillPanel.this.printFoodBill();
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
                printButton.setForeground(Color.decode("#4527A0"));
                printButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Khi chuột ra khỏi Button thì Button sẽ trở về màu cũ
                printButton.setForeground(Color.WHITE);
                printButton.setBackground(Color.decode("#4527A0"));
            }
        });

        selectionTextFoodBill = new JLabel("Đang chọn: NULL");
        selectionTextFoodBill.setFont(
                new Font("Sans-serif", Font.BOLD, 12));
        selectionTextFoodBill.setBounds(755, 10, 300, 20);

        panel.add(timeLabel);
        panel.add(monthTextFieldFoodBill);
        panel.add(yearTextFieldFoodBill);

        panel.add(foodBillCategoryLabel);
        panel.add(foodBillTypeCombobox);

        panel.add(foodBillStatusLabel);
        panel.add(this.foodBillStatusCombobox);

        panel.add(filterButton);
        panel.add(selectionTextFoodBill);
        panel.add(resetButton);
        panel.add(printButton);

        return panel;
    }

    private CustomPanel createSessionControlPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel timeLabel = new JLabel("Ngày bắt đầu:");
        timeLabel.setBounds(10, 10, 100, 30);

        monthTextFieldSession = new CustomTextField("Nhập ngày bắt đầu");
        monthTextFieldSession.setBounds(10, 38, 130, 35);
        monthTextFieldSession.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (monthTextFieldSession.getText().equalsIgnoreCase("Nhập ngày bắt đầu")) {
                    monthTextFieldSession.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (monthTextFieldSession.getText().isEmpty()) {
                    monthTextFieldSession.setText("Nhập ngày bắt đầu");
                }
            }
        });

        JLabel endDateLabel = new JLabel("Ngày kết thúc:");
        endDateLabel.setBounds(150, 10, 100, 30);

        yearTextFieldSession = new CustomTextField("Nhập ngày kết thúc");
        yearTextFieldSession.setBounds(150, 38, 130, 35);
        yearTextFieldSession.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (yearTextFieldSession.getText().equalsIgnoreCase("Nhập ngày kết thúc")) {
                    yearTextFieldSession.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (yearTextFieldSession.getText().isEmpty()) {
                    yearTextFieldSession.setText("Nhập ngày kết thúc");
                }
            }
        });

        JLabel roomTypeLabel = new JLabel("Loại phòng:");
        roomTypeLabel.setBounds(290, 10, 70, 30);

        ArrayList<String> roomTypeList = new ArrayList<>();
        roomTypeList.add("Tất cả");
        for (Rooms x : this.roomBLL.getAllRooms()) {
            roomTypeList.add(x.getRoomName());
        }
        roomTypeCombobox = new CustomCombobox<>(roomTypeList);
        roomTypeCombobox.setBounds(290, 38, 150, 35);

        CustomButton detailButton = new CustomButton("Chi tiết");
        detailButton.setBorderSize(3);
        detailButton.setBorderColor(Color.orange);
        detailButton.setBackground(Color.orange);
        detailButton.setForeground(Color.WHITE);
        detailButton.setBounds(650, 38, 100, 35);
        detailButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (BillPanel.this.tableSession.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Bạn chưa chọn phiên chơi để xem chi tiết!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    String[] regexStrings = BillPanel.this.selectionTextSession.getText().split("\\s+");
                    if (regexStrings[regexStrings.length - 1].equalsIgnoreCase("NULL")) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Bạn chưa chọn phiên chơi để xem chi tiết!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    new DetailsComputerSessionBill(Integer.parseInt(regexStrings[regexStrings.length - 1])).setVisible(true);
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
                detailButton.setBackground(Color.WHITE);
                detailButton.setForeground(Color.ORANGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                detailButton.setBackground(Color.ORANGE);
                detailButton.setForeground(Color.WHITE);
            }
        });

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
                BillPanel.this.monthTextFieldSession
                        .setText("Nhập tháng");

                BillPanel.this.yearTextFieldSession
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
        printButton.setBackground(Color.decode("#4527A0"));
        // Màu border
        printButton.setBorderColor(Color.decode("#4527A0"));
        // Màu chữ
        printButton.setForeground(Color.WHITE);
        // Chỉnh kích thước của Border
        printButton.setBorderSize(3);
        printButton.setBounds(965, 38, 100, 35);
        printButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Đã ấn nút in hóa đơn phiên chơi trên giao diện");
                BillPanel.this.printSessionBill();
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
                printButton.setForeground(Color.decode("#4527A0"));
                printButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Khi chuột ra khỏi Button thì Button sẽ trở về màu cũ
                printButton.setForeground(Color.WHITE);
                printButton.setBackground(Color.decode("#4527A0"));
            }
        });

        // JLabel hiển thị máy đang được chọn
        selectionTextSession = new JLabel("Đang chọn: NULL");
        selectionTextSession.setFont(
                new Font("Sans-serif", Font.BOLD, 12));
        selectionTextSession.setBounds(655, 10, 300, 20);

        panel.add(timeLabel);
        panel.add(monthTextFieldSession);
        panel.add(yearTextFieldSession);
        panel.add(roomTypeLabel);
        panel.add(roomTypeCombobox);
        panel.add(detailButton);
        panel.add(endDateLabel);
        panel.add(filterButton);
        panel.add(selectionTextSession);
        panel.add(resetButton);
        panel.add(printButton);

        return panel;
    }

    // Phương thức reset lại table session
    private void resetSessionDataPanel() {
        this.sessionList = this.computerSessionBLL.getComputerSessionList();
        Object[][] data = this.createSessionData(this.sessionList);
        DefaultTableModel model = new DefaultTableModel(data, this.columnNameSession);
        this.tableSession.setModel(model);
        this.tableSession.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(this.tableSession);

        this.tableSession.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(100);

        for (int i = 0; i < this.tableSession.getColumnCount(); i++) {
            this.tableSession.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(this.rendererSession);
        }
    }

    // Phương thức reset lại table food bill
    private void resetFoodBillDataPanel() {
        this.foodBillList = this.foodBillBLL.getFoodBillList();
        Object[][] data = this.createFoodBillData(this.foodBillList);
        DefaultTableModel model = new DefaultTableModel(data, this.columnNameFoodBill);
        this.tableFoodBill.setModel(model);
        this.tableFoodBill.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(this.tableFoodBill);

        this.tableFoodBill.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(80);

        for (int i = 0; i < this.tableFoodBill.getColumnCount(); i++) {
            this.tableFoodBill.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(this.rendererFoodBill);
        }
    }

    // Phương thức cập nhật table session dựa trên mảng đưa vào
    private void updateSessionDataPanel(ArrayList<ComputerSessions> list) {
        Object[][] data = this.createSessionData(list);
        DefaultTableModel model = new DefaultTableModel(data, this.columnNameSession);
        this.tableSession.setModel(model);
        this.tableSession.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(this.tableSession);

        this.tableSession.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(100);
    }

    // Phương thức cập nhật table food bill dựa vào mảng đưa vào
    private void updateFoodBillDataPanel(ArrayList<FoodBills> list) {
        Object[][] data = this.createFoodBillData(list);
        DefaultTableModel model = new DefaultTableModel(data, this.columnNameFoodBill);
        this.tableFoodBill.setModel(model);
        this.tableFoodBill.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(this.tableFoodBill);

        this.tableFoodBill.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(80);
    }

    private void filterSessionList() {
        String startDate = this.monthTextFieldSession.getText().trim().equals("Nhập ngày bắt đầu") ? "" : this.monthTextFieldSession.getText().trim();
        String endDate = this.yearTextFieldSession.getText().trim().equals("Nhập ngày kết thúc") ? "" : this.yearTextFieldSession.getText();

        String roomType = this.roomTypeCombobox.getSelectedItem().toString();

        if (startDate.isEmpty() && endDate.isEmpty() && roomType.equalsIgnoreCase("Tất cả")) {
            this.resetSessionDataPanel();
        } else {
            if ((!startDate.isEmpty() && endDate.isEmpty()) || (startDate.isEmpty() && !endDate.isEmpty())) {
                JOptionPane.showMessageDialog(
                        null,
                        "Vui lòng nhập đủ cả 2 ngày!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if ((!startDate.isEmpty() && !endDate.isEmpty()) && (!Comon.isTrueDate(startDate) || !Comon.isTrueDate(endDate))) {
                JOptionPane.showMessageDialog(
                        null,
                        "Ngày không hợp lệ!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            LocalDate startDateObj = this.getLocalDateFromString(startDate);
            LocalDate endDateObj = this.getLocalDateFromString(endDate);

            if ((!startDate.isEmpty() && !endDate.isEmpty()) && startDateObj.isAfter(endDateObj)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Ngày bắt đầu phải bằng hoặc trước ngày kết thúc!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }


            ZoneId zoneId = ZoneId.of("UTC+7");
            // Đọc lại dữ liệu trước khi lọc
            this.sessionList = this.computerSessionBLL.getComputerSessionList();
            List<ComputerSessions> list = this.sessionList.stream()
                    .filter(session -> (startDate.isEmpty() && endDate.isEmpty())
                            || session.getStartTime().toLocalDate().isEqual(startDateObj) || session.getStartTime().toLocalDate().isEqual(endDateObj) || (session.getStartTime().toLocalDate().isAfter(startDateObj) && session.getStartTime().toLocalDate().isBefore(endDateObj)))
                    .filter(session -> roomType.equalsIgnoreCase("Tất cả")
                            || roomBLL.getRoomById(computerBLL.getComputerById(session.getComputerId()).getRoomId())
                                    .getRoomName().equalsIgnoreCase(roomType))
                    .collect(Collectors.toList());

            // Sau khi lọc sẽ cập nhật vào table
            this.sessionList = new ArrayList<>(list);
            this.updateSessionDataPanel(this.sessionList);
        }

        // Căn giữa lại các cột
        for (int i = 0; i < this.tableSession.getColumnCount(); i++) {
            this.tableSession.getColumnModel().getColumn(i).setCellRenderer(this.rendererSession);
        }
    }

    private LocalDate getLocalDateFromString(String dateStr) {
        if (dateStr.isEmpty()) return null;

        String[] arr = dateStr.split("-");
        return LocalDate.of(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
    }

    private void filterFoodBillList() {
        String month = this.monthTextFieldFoodBill.getText().equalsIgnoreCase("Nhập tháng")
                ? ""
                : this.monthTextFieldFoodBill.getText();

        String year = this.yearTextFieldFoodBill.getText().equalsIgnoreCase("Nhập năm")
                ? ""
                : this.yearTextFieldFoodBill.getText();

        String foodType = this.foodBillTypeCombobox.getSelectedItem().toString().equalsIgnoreCase("Tất cả")
                ? ""
                : this.foodBillTypeCombobox.getSelectedItem().toString();

        String foodBillStatus = this.foodBillStatusCombobox.getSelectedItem().toString();

        if (month.isEmpty() && year.isEmpty() && foodType.equalsIgnoreCase("Tất cả")
                && foodBillStatus.equalsIgnoreCase("Tất cả") && foodType.equalsIgnoreCase("Tất cả")) {
            this.resetFoodBillDataPanel();
        } else {

            // Nếu chỉ nhập một trong hai tháng hoặc năm thì thông báo lỗi
            if ((month.isEmpty() && !year.isEmpty()) || (!month.isEmpty() && year.isEmpty())) {
                JOptionPane.showMessageDialog(
                        null,
                        "Vui lòng nhập đủ tháng và năm!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
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
            if (foodType.isEmpty()) {
                this.foodBillList = this.foodBillBLL.getFoodBillList();
            } else {
                String[] regexFoodType = foodType.split(" - ");
                String idTypeFood = regexFoodType[0];
                System.out.println(idTypeFood);
                this.foodBillList = this.foodBillBLL.getFoodBillByCategoryId(Integer.parseInt(idTypeFood));
            }

            // Lọc dữ liệu
            List<FoodBills> list = this.foodBillList.stream()
                    .filter(foodBill -> month.isEmpty()
                            || foodBill.getCreatedAt().toLocalDateTime().getMonthValue() == monthInt)
                    .filter(foodBill -> year.isEmpty()
                            || foodBill.getCreatedAt().toLocalDateTime().getYear() == yearInt)

                    .filter(foodBill -> foodBillStatus.equalsIgnoreCase("Tất cả")
                            || foodBill.getStatus().equalsIgnoreCase(foodBillStatus))
                    .collect(Collectors.toList());

            this.foodBillList = new ArrayList<>(list);
            this.updateFoodBillDataPanel(this.foodBillList);
        }

        // Căn giữa lại các cột
        for (int i = 0; i < this.tableFoodBill.getColumnCount(); i++) {
            this.tableFoodBill.getColumnModel().getColumn(i).setCellRenderer(this.rendererFoodBill);
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
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void initComponents() {
        this.setLayout(null);

        headerPanel = this.createHeaderPanel();

        controlPanel = this.createFilterPanel();

        dataPanel = this.createDataPanel();

        headerPanel.setBounds(10, 0, 1080, 150);
        controlPanel.setBounds(10, 160, 1080, 95);
        dataPanel.setBounds(10, 265, 1080, 453);

        this.add(headerPanel);
        this.add(controlPanel);
        this.add(dataPanel);
    }

    private Object[][] createSessionData(ArrayList<ComputerSessions> list) {
        Object[][] data = new Object[this.sessionList.size()][5];

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getSessionId();
            data[i][1] = list.get(i)
                    .getStaffId() > 0 ?  this.accountBLL.getAccountById(
                    this.staffBLL.getStaffById(list.get(i)
                            .getStaffId())
                            .getAccountId())
                    .getUsername() : "Khách tự đăng nhập";
            data[i][2] = ChangeMinToDate.changeData(list.get(i).getDuration());
            data[i][3] = Comon.formatMoney(list.get(i).getTotalCost() + "");
            data[i][4] = list.get(i).getPlayerId() == null
                    ? "Mở máy"
                    : list.get(i).getPlayerId();
        }

        return data;
    }

    private Object[][] createFoodBillData(ArrayList<FoodBills> list) {
        Object[][] data = new Object[this.foodBillList.size()][5];

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getBillId();
            data[i][1] = this.accountBLL.getAccountById(
                    this.staffBLL.getStaffById(list.get(i)
                            .getStaffId())
                            .getAccountId())
                    .getUsername();
            data[i][2] = Comon.formatMoney(list.get(i).getTotalPrice() + "");
            data[i][3] = list.get(i).getStatus();
            data[i][4] = list.get(i).getCreatedAt();
        }

        return data;
    }

    private CustomPanel createDataPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(this.dataCardLayout);

        CustomPanel sessionPanel = this.createDataPanelForSession();
        CustomPanel billPanel = this.createDataPanelForBill();

        panel.add(sessionPanel, "SessionPanel");
        panel.add(billPanel, "BillPanel");

        return panel;
    }

    private CustomPanel createFilterPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(this.filterCardLayout);

        CustomPanel buttonSessionPanel = this.createSessionControlPanel();
        CustomPanel buttonBillPanel = this.createFoodBillControlPanel();

        panel.add(buttonSessionPanel, "SessionPanel");
        panel.add(buttonBillPanel, "BillPanel");

        return panel;
    }

    // Phương thức tạo panel cho Bill
    private CustomPanel createDataPanelForBill() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        // Đọc dữ liệu mới nhất
        this.foodBillList = this.foodBillBLL.getFoodBillList();
        DefaultTableModel model = new DefaultTableModel(this.createFoodBillData(this.foodBillList),
                this.columnNameFoodBill);
        this.tableFoodBill = new CustomTable(model);

        // Tắt tự điều chỉnh kích thước của table
        this.tableFoodBill.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Gọi hàm căn lại width các cột dựa trên giá trị dài nhất trong cột
        AdjustTableWidth.automaticallyAdjustTableWidths(this.tableFoodBill);

        // Điều chỉnh width của cột ID
        this.tableFoodBill.getColumnModel().getColumn(0).setPreferredWidth(80);

        // THêm sự kiện khi chọn một dòng nào đó trong table
        this.tableFoodBill.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (tableFoodBill.getSelectedRow() != -1) {
                        BillPanel.this.selectionTextFoodBill
                                .setText("Đang chọn hóa đơn "
                                        + tableFoodBill.getValueAt(tableFoodBill.getSelectedRow(), 0));
                    }
                }
            }
        });

        // Thêm bảng vào ScrollPane
        JScrollPane scrollPane = new CustomScrollPane(this.tableFoodBill);
        scrollPane.setBounds(0, 10, 1080, 360);

        CustomPanel buttonPanel = new CustomPanel();
        buttonPanel.setLayout(null);

        buttonPanel.setBounds(0, 380, 1080, 65);
        buttonPanel.setBackground(Color.WHITE);

        // Tạo một button hủy đơn
        CustomButton cancelButton = new CustomButton("Hủy đơn");
        // Chỉnh kích thước của Border
        cancelButton.setBorderSize(3);
        // Màu nền
        cancelButton.setBackground(Color.decode("#F44336"));
        // Màu border
        cancelButton.setBorderColor(Color.decode("#F44336"));
        // Màu chữ
        cancelButton.setForeground(Color.WHITE);

        cancelButton.setBounds(140, 15, 100, 35);
        cancelButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                BillPanel.this.updateCancelFoodBill();
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
                cancelButton.setForeground(Color.decode("#F44336"));
                cancelButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Khi chuột ra khỏi Button thì Button sẽ trở về màu cũ
                cancelButton.setForeground(Color.WHITE);
                cancelButton.setBackground(Color.decode("#F44336"));
            }
        });

        // Tạo một button với xác nhận đơn
        CustomButton confirmButton = new CustomButton("Xác nhận");
        // Chỉnh kích thước của Border
        confirmButton.setBorderSize(3);
        // Màu nền
        confirmButton.setBackground(Color.decode("#00695C"));
        // Màu border
        confirmButton.setBorderColor(Color.decode("#00695C"));
        // Màu chữ
        confirmButton.setForeground(Color.WHITE);

        confirmButton.setBounds(20, 15, 100, 35);
        confirmButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                BillPanel.this.updateCofirmFoodBill();
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
                confirmButton.setForeground(Color.decode("#00695C"));
                confirmButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Khi chuột ra khỏi Button thì Button sẽ trở về màu cũ
                confirmButton.setForeground(Color.WHITE);
                confirmButton.setBackground(Color.decode("#00695C"));
            }
        });

        // Tạo một button chi tiết đơn
        CustomButton detailButton = new CustomButton("Chi tiết");
        // Chỉnh kích thước của Border
        detailButton.setBorderSize(3);
        // Màu nền
        detailButton.setBackground(Color.decode("#6D4C41"));
        // Màu border
        detailButton.setBorderColor(Color.decode("#6D4C41"));
        // Màu chữ
        detailButton.setForeground(Color.WHITE);

        detailButton.setBounds(260, 15, 100, 35);
        detailButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (BillPanel.this.tableFoodBill.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Bạn chưa chọn hóa đơn để xem chi tiết!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                else {
                    String[] regexStrings = BillPanel.this.selectionTextFoodBill.getText().split("\\s+");
                    if (regexStrings[regexStrings.length - 1].equalsIgnoreCase("NULL")) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Bạn chưa chọn hóa đơn để xem chi tiết!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    new DetailsFoodOrderBill(Integer.parseInt(regexStrings[regexStrings.length - 1])).setVisible(true);
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
                // Khi di chuột vào thì Button sẽ đổi màu
                detailButton.setForeground(Color.decode("#6D4C41"));
                detailButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Khi chuột ra khỏi Button thì Button sẽ trở về màu cũ
                detailButton.setForeground(Color.WHITE);
                detailButton.setBackground(Color.decode("#6D4C41"));
            }
        });


        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(detailButton);

        panel.add(scrollPane);
        panel.add(buttonPanel);

        return panel;
    }

    // Phương thức tạo panel cho Session
    private CustomPanel createDataPanelForSession() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        // Đọc dữ liệu mới nhất
        this.sessionList = this.computerSessionBLL.getComputerSessionList();
        DefaultTableModel model = new DefaultTableModel(this.createSessionData(this.sessionList),
                this.columnNameSession);
        this.tableSession = new CustomTable(model);

        // Tắt tự động điều chỉnh kích thước của table
        this.tableSession.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Gọi hàm căn lại width các cột dựa trên giá trị dài nhất trong cột
        AdjustTableWidth.automaticallyAdjustTableWidths(this.tableSession);

        // Điều chỉnh width của cột ID
        this.tableSession.getColumnModel().getColumn(0).setPreferredWidth(100);

        // THêm sự kiện khi chọn một dòng nào đó trong table
        this.tableSession.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (tableSession.getSelectedRow() != -1) {
                        BillPanel.this.selectionTextSession
                                .setText("Đang chọn phiên chơi "
                                        + tableSession.getValueAt(tableSession.getSelectedRow(), 0));
                    }
                }
            }
        });

        // Thêm bảng vào ScrollPane
        JScrollPane scrollPane = new CustomScrollPane(this.tableSession);
        scrollPane.setBounds(0, 0, 1080, 453);

        panel.add(scrollPane);

        return panel;
    }

    private void updateCofirmFoodBill() {
        int confirmUpdate = JOptionPane.showConfirmDialog(this, "Bạn có chắc cập nhật trạng thái hóa đơn ?",
                "Thông báo", JOptionPane.YES_NO_OPTION);

        if (confirmUpdate == JOptionPane.NO_OPTION)
            return;

        String[] regexString = this.selectionTextFoodBill.getText().split("\\s+");
        if (Comon.isTrueNumber(regexString[regexString.length - 1])) {

            int row = this.tableFoodBill.getSelectedRow();
            if (row != -1) {
                String valueStatus = (String) this.tableFoodBill.getValueAt(row, 3);
                if (valueStatus.equalsIgnoreCase("Đã xử lý") || valueStatus.equalsIgnoreCase("Đã hủy")) {
                    JOptionPane.showMessageDialog(this, "Hóa đơn đã hủy hoặc đã xử lý thì không xác nhận lại được",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!this.checkQuantityOfBill(Integer.parseInt(regexString[regexString.length - 1]))) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Số lượng trong kho không đủ!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                    if (this.foodBillBLL.updateCancelFoodBill(Integer.parseInt(regexString[regexString.length - 1]))) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Đơn hàng đã bị hủy do không đủ số lượng!",
                                "Lỗi",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                    this.filterFoodBillList();
                    return;
                }

                this.updateFoodQuantityBasedOnBillId(Integer.parseInt(regexString[regexString.length - 1]));

                boolean resultUpdate = this.foodBillBLL
                        .updateCofirmFoodBill(Integer.parseInt(regexString[regexString.length - 1]));
                if (resultUpdate) {
                    JOptionPane.showMessageDialog(this, "Cập nhật trạng thái hóa đơn thành công", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    this.filterFoodBillList();
                    return;
                }
                JOptionPane.showMessageDialog(this, "Cập nhật trạng thái hóa đơn thất bại", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;

            } else {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra vui lòng chọn dòng khác", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xác nhận", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }

    private void printFoodBill() {
        int confirmUpdate = JOptionPane.showConfirmDialog(this, "Bạn có chắc in hóa đơn ?",
                "Thông báo", JOptionPane.YES_NO_OPTION);

        if (confirmUpdate == JOptionPane.NO_OPTION)
            return;

        String[] regexString = this.selectionTextFoodBill.getText().split("\\s+");
        if (Comon.isTrueNumber(regexString[regexString.length - 1])) {
            new InvoicePrinter().printFoodOrder(Integer.parseInt(regexString[regexString.length - 1]));
        }
        else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để in", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void printSessionBill() {
        int confirmUpdate = JOptionPane.showConfirmDialog(this, "Bạn có chắc in hóa đơn ?",
                "Thông báo", JOptionPane.YES_NO_OPTION);

        if (confirmUpdate == JOptionPane.NO_OPTION)
            return;

        String[] regexString = this.selectionTextSession.getText().split("\\s+");
        if (Comon.isTrueNumber(regexString[regexString.length - 1])) {
            new InvoicePrinter().printSessionOrder(Integer.parseInt(regexString[regexString.length - 1]));
        }
        else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để in", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }



    private void updateCancelFoodBill() {
        int confirmUpdate = JOptionPane.showConfirmDialog(this, "Bạn có chắc cập nhật trạng thái hóa đơn ?",
                "Thông báo", JOptionPane.YES_NO_OPTION);

        if (confirmUpdate == JOptionPane.NO_OPTION)
            return;

        String[] regexString = this.selectionTextFoodBill.getText().split("\\s+");
         if (Comon.isTrueNumber(regexString[regexString.length - 1])) {

            int row = this.tableFoodBill.getSelectedRow();
            if (row != -1) {
                String valueStatus = (String) this.tableFoodBill.getValueAt(row, 3);
                if (valueStatus.equalsIgnoreCase("Đã xử lý")) {
                    JOptionPane.showMessageDialog(this, "Hóa đơn đã xử lý thì không hủy được",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                boolean resultUpdate = this.foodBillBLL
                        .updateCancelFoodBill(Integer.parseInt(regexString[regexString.length - 1]));
                if (resultUpdate) {
                    JOptionPane.showMessageDialog(this, "Cập nhật trạng thái hóa đơn thành công", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    this.filterFoodBillList();
                    return;
                }
                JOptionPane.showMessageDialog(this, "Cập nhật trạng thái hóa đơn thất bại", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;

            } else {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra vui lòng chọn dòng khác", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

        }else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xác nhận", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }

    private boolean checkQuantityOfBill(int billId) {
        ArrayList<ArrayList<Integer>> orderDetails = this.foodOrderBLL.getOrderDetailFromBillId(billId);

        for (ArrayList<Integer> detail : orderDetails) {
            if (this.foodBLL.getFoodByID(detail.get(0)).getQuantity() < detail.get(1)) {
                return false;
            }
        }

        return true;
    }

    private void updateFoodQuantityBasedOnBillId(int billId) {
        ArrayList<ArrayList<Integer>> orderDetails = this.foodOrderBLL.getOrderDetailFromBillId(billId);

        for (ArrayList<Integer> detail : orderDetails) {
            HashMap<String, Object> updateValues = new HashMap<>();
            updateValues.put("quantity", this.foodBLL.getFoodByID(detail.get(0)).getQuantity() - detail.get(1));
            this.foodBLL.updateFoodDetailsById(detail.get(0), updateValues);
        }
    }
}
