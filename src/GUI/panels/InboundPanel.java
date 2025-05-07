package GUI.panels;

import BLL.AccountBLL;
import BLL.PurchaseReceiptBLL;
import BLL.StaffBLL;
import DTO.Accounts;
import DTO.PurchaseReceipt;
import DTO.Staffs;
import GUI.Components.*;
import GUI.Form.AddingInbound;
import GUI.Form.DetailsInbound;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class InboundPanel extends JPanel {
    private Accounts accounts;
    private Staffs staffs;

    CustomPanel titlePanel;
    //------------------------------------------------------
    CustomPanel filterButtonPanel;
    private CustomTextField searchTextField;
    private CustomTextField dateStartTextField;
    private CustomTextField dateEndTextField;
    private CustomCombobox<String> statusCombobox;
    private JLabel selectionText;

    //------------------------------------------------------
    private CustomPanel dataPanel;
    private String[] columnNames;
    private Object[][] data;
    private DefaultTableModel model;
    private CustomTable tableData;
    private int currentSelectedId = -1;
    //------------------------------------------------------
    private PurchaseReceiptBLL purchaseReceiptBLL;
    private StaffBLL staffBLL;
    private AccountBLL accountBLL;
    private ArrayList<PurchaseReceipt> list;
    private DefaultTableCellRenderer renderer;



    public InboundPanel(Accounts accounts, Staffs staffs) {
        // Init
        this.accounts = accounts;
        this.staffs = staffs;
        this.renderer = new DefaultTableCellRenderer();
        this.renderer.setHorizontalAlignment(JLabel.CENTER);

        // BLL Layer
        this.purchaseReceiptBLL = new PurchaseReceiptBLL();
        this.staffBLL = new StaffBLL();
        this.accountBLL = new AccountBLL();


        // Data table
        this.list = this.purchaseReceiptBLL.getPurchaseReceiptList();
        this.columnNames = new String[]{"Mã phiếu nhập", "Ngày tạo", "Nhân viên lập", "Tổng tiên", "Trạng thái", "Ngày cập nhật"};
        this.initComponents();
    }

    private CustomPanel createTitlePanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);
        JLabel title = new JLabel("QUẢN LÝ PHIẾU NHẬP");
        title.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        title.setBounds(400, 25, 500, 50);
        panel.add(title);

        return panel;
    }

    private CustomPanel createFilterButtonPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        // Them phieu nhap
        Image addImage = new ImageIcon(
                System.getProperty("user.dir") + "/src/Assets/Icon/add.png"
        ).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        CustomDesignButton addButton = new CustomDesignButton("Thêm",  new ImageIcon(addImage));
        addButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        addButton.setHorizontalTextPosition(SwingConstants.CENTER);
        addButton.setBackground(Color.WHITE);
        addButton.setBorderColor(Color.BLACK);
        addButton.setForeground(Color.BLACK);
        addButton.setBounds(20, 40, 90, 70);
        addButton.setBorderSize(3);
        addButton.addActionListener(e -> {
            new AddingInbound(this.staffs).setVisible(true);
        });

        // Detail phieu nhap
        Image infoImage = new ImageIcon(
                System.getProperty("user.dir") + "/src/Assets/Icon/pencil.png"
        ).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        CustomDesignButton detailButton = new CustomDesignButton("Sửa", new ImageIcon(infoImage));
        detailButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        detailButton.setHorizontalTextPosition(SwingConstants.CENTER);
        detailButton.setBackground(Color.WHITE);
        detailButton.setBorderColor(Color.BLACK);
        detailButton.setForeground(Color.BLACK);
        detailButton.setBounds(125, 40, 90, 70);
        detailButton.setBorderSize(3);
        detailButton.addActionListener(e -> {
            if (currentSelectedId == -1) {
                JOptionPane.showMessageDialog(
                        null,
                        "Bạn chưa chọn phiếu nhập!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            new DetailsInbound(currentSelectedId).setVisible(true);
        });

        // Cofirm phieu nhap
        Image confirmImage = new ImageIcon(
                System.getProperty("user.dir") + "/src/Assets/Icon/icons8-yes-100.png"
        ).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);;
        CustomDesignButton confirmButon = new CustomDesignButton("Xác nhận", new ImageIcon(confirmImage));
        confirmButon.setVerticalTextPosition(SwingConstants.BOTTOM);
        confirmButon.setHorizontalTextPosition(SwingConstants.CENTER);
        confirmButon.setBackground(Color.WHITE);
        confirmButon.setBorderColor(Color.BLACK);
        confirmButon.setForeground(Color.BLACK);
        confirmButon.setBounds(230, 40, 90, 70);
        confirmButon.setBorderSize(3);
        confirmButon.addActionListener(e -> {
            if (currentSelectedId == -1) {
                JOptionPane.showMessageDialog(
                        null,
                        "Bạn chưa chọn phiếu nhập!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            PurchaseReceipt purchaseReceipt = this.purchaseReceiptBLL.getPurchaseReceiptById(this.currentSelectedId);
            if (purchaseReceipt.getStatus().equals("Đã xác nhận")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Phiếu nhập đã được xác nhận!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            } else if (purchaseReceipt.getStatus().equals("Đã hủy")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Phiếu nhập đã hủy thì không thể xác nhận!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            if (this.purchaseReceiptBLL.confirmInbound(this.currentSelectedId)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Xác nhận đơn hàng thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Xác nhận đơn hàng thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            this.refreshTable();
        });


        // Cancel phieu nhap
        Image cancelImage = new ImageIcon(
                System.getProperty("user.dir") + "/src/Assets/Icon/icons8-cancel-100.png"
        ).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);;
        CustomDesignButton cancelButton = new CustomDesignButton("Hủy", new ImageIcon(cancelImage));
        cancelButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setBorderColor(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setBounds(335, 40, 90, 70);
        cancelButton.setBorderSize(3);
        cancelButton.addActionListener(e -> {
            if (currentSelectedId == -1) {
                JOptionPane.showMessageDialog(
                        null,
                        "Bạn chưa chọn phiếu nhập!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            PurchaseReceipt purchaseReceipt = this.purchaseReceiptBLL.getPurchaseReceiptById(this.currentSelectedId);
            if (purchaseReceipt.getStatus().equals("Đã xác nhận")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Phiếu nhập đã xác nhận thì không thể hủy!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            } else if (purchaseReceipt.getStatus().equals("Đã hủy")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Phiếu nhập đã hủy!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            if (this.purchaseReceiptBLL.cancelInbound(this.currentSelectedId)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Hủy đơn hàng thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Hủy đơn hàng thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            this.refreshTable();
        });


        // Phần tìm kiếm input
        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(475, 10, 80, 30);

        searchTextField = new CustomTextField("Nhập thông tin tìm kiếm");
        searchTextField.setBounds(475, 38, 200, 35);
        searchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Khi người dùng focus vào Text Field, nếu nội dung Text Field là Placeholder thì xóa Placeholder để người dùng nhập nội dung tìm kiếm
                if (searchTextField.getText().equals("Nhập thông tin tìm kiếm")) {
                    searchTextField.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                // Nếu Text Field không bị focus nữa mà nội dung tìm kiếm là rỗng thì đặt lại nội dung là Placeholder
                if (searchTextField.getText().equals("")) {
                    searchTextField.setText("Nhập thông tin tìm kiếm");
                }
            }
        });

        JLabel dateStart = new JLabel("Ngày bắt đầu");
        dateStart.setBounds(475, 80, 80, 30);

        dateStartTextField = new CustomTextField("2000-01-01");
        dateStartTextField.setBounds(475, 108, 200, 35);
        dateStartTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = InboundPanel.this.dateStartTextField.getText();
                if (text.equalsIgnoreCase("2000-01-01")) {
                    InboundPanel.this.dateStartTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = InboundPanel.this.dateStartTextField.getText();
                if (text.equalsIgnoreCase("")) {
                    InboundPanel.this.dateStartTextField.setText("2000-01-01");
                }
            }
        });


        JLabel filterLabel = new JLabel("Trạng thái:");
        filterLabel.setBounds(690, 10, 70, 30);

        String[] status = {"Tất cả", "Đã xác nhận", "Đã hủy"};
        statusCombobox = new CustomCombobox<>(status);
        statusCombobox.setBounds(690, 38, 150, 35);

        JLabel dateEnd = new JLabel("Ngày kết thúc");
        dateEnd.setBounds(690, 80, 80, 30);

        dateEndTextField = new CustomTextField("2030-01-01");
        dateEndTextField.setBounds(690, 108, 150, 35);
        dateEndTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = InboundPanel.this.dateEndTextField.getText();
                if (text.equalsIgnoreCase("2030-01-01")) {
                    InboundPanel.this.dateEndTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = InboundPanel.this.dateEndTextField.getText();
                if (text.equalsIgnoreCase("")) {
                    InboundPanel.this.dateEndTextField.setText("2030-01-01");
                }
            }
        });

        // Tạo một Button với chữ "Lọc"
        CustomButton filterButton = new CustomButton("Lọc");
        filterButton.setBorderSize(3);
        filterButton.setBackground(Color.decode("#03A9F4"));
        filterButton.setBorderColor(Color.decode("#03A9F4"));
        filterButton.setForeground(Color.WHITE);
        filterButton.setBounds(860, 38, 100, 35);


        CustomButton resetButton = new CustomButton("Đặt lại");
        resetButton.setBackground(Color.RED);
        resetButton.setBorderColor(Color.RED);
        resetButton.setForeground(Color.WHITE);
        resetButton.setBorderSize(3);
        resetButton.setBounds(965, 38, 100, 35);


        selectionText = new JLabel("Đang chọn: NULL");
        selectionText.setFont(
                new Font("Sans-serif", Font.BOLD, 12)
        );
        selectionText.setBounds(860,10,300,20);


        panel.add(addButton);
        panel.add(detailButton);
        panel.add(confirmButon);
        panel.add(cancelButton);

        panel.add(searchLabel);
        panel.add(searchTextField);
        panel.add(filterLabel);
        panel.add(statusCombobox);
        panel.add(filterButton);
        panel.add(dateStart);
        panel.add(dateStartTextField);
        panel.add(dateEnd);
        panel.add(dateEndTextField);
        panel.add(resetButton);
        panel.add(selectionText);

        return panel;
    }


    // -------------------------------------------------------------------

    private void refreshAllDatas() {
        this.list = this.purchaseReceiptBLL.getPurchaseReceiptList();
    }

    private void refreshTable() {
        this.refreshAllDatas();
        Object[][] data = this.createData(this.list);
        DefaultTableModel model = new DefaultTableModel(data, this.columnNames);
        this.tableData.setModel(model);
        for (int i=0; i<this.tableData.getColumnCount(); i++) {
            this.tableData.getColumnModel().getColumn(i).setCellRenderer(this.renderer);
        }
    }

    private Object[][] createData(ArrayList<PurchaseReceipt> list) {
        Object[][] data = new Object[list.size()][6];
        //{"Mã phiếu nhập", "Ngày tạo", "Nhân viên lập", "Tổng tiên", "Trạng thái", "Ngày cập nhật"};
        for (int i = 0; i < list.size(); i++) {
            PurchaseReceipt purchaseReceipt = list.get(i);
            Staffs staffs = this.staffBLL.getStaffById(purchaseReceipt.getStaffId());
            data[i][0] = purchaseReceipt.getReceiptId();
            data[i][1] = purchaseReceipt.getCreateAt();
            data[i][2] = this.accountBLL.getAccountById(staffs.getAccountId()).getUsername()
                        +" - "+ staffs.getFullName();
            data[i][3] = Utils.Helper.Comon.formatMoney(purchaseReceipt.getTotal() + "");
            data[i][4] = purchaseReceipt.getStatus();
            data[i][5] = purchaseReceipt.getUpdateAt();
        }

        return data;
    }

    private CustomPanel createDataPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        this.refreshAllDatas();
        this.data = this.createData(this.list);
        this.model = new DefaultTableModel(this.data, this.columnNames);

        tableData = new CustomTable(this.model);
        tableData.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (tableData.getSelectedRow() != -1) {
                        InboundPanel.this.selectionText.setText("Đang chọn: " + InboundPanel.this.tableData.getValueAt(tableData.getSelectedRow(), 0));
                        InboundPanel.this.currentSelectedId = Integer.parseInt(""+InboundPanel.this.tableData.getValueAt(tableData.getSelectedRow(), 0));
                    }
                }
            }
        });

        JScrollPane scroll = new CustomScrollPane(tableData);
        scroll.setBounds(0,0,1080,503);
        panel.add(scroll);

        return panel;
    }
    // -------------------------------------------------------------------

    private void initComponents() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        this.titlePanel = this.createTitlePanel();

        this.filterButtonPanel = this.createFilterButtonPanel();

        this.dataPanel = this.createDataPanel();

        this.titlePanel.setBounds(10, 0, 1080, 100);
        this.filterButtonPanel.setBounds(10, 110, 1080, 155);
        this.dataPanel.setBounds(10,275,1080,443);

        this.add(titlePanel);
        this.add(filterButtonPanel);
        this.add(dataPanel);
    }
}
