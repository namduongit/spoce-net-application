package GUI.panels;

import BLL.*;
import DTO.*;
import GUI.Components.*;
import GUI.Form.AddingComputer;
import GUI.Form.DetailsComputer;
import Utils.Helper.AdjustTableWidth;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class ComputerPanel extends JPanel{
    private Accounts loginAccount;
    private Staffs loginStaff;

    private CardLayout cardLayout;
    private CustomPanel titlePanel;
    private CustomPanel controlPanel;
    private JPanel dataPanel;
    private int selectedItemIndex;
    private JLabel selectionText;
    private JLabel sessionSelectionText;
    private CustomCombobox<String> statusComboBox;
    private CustomTextField searchTextField;
    private ComputerBLL computerBLL;
    private MotherboardBLL motherboardBLL;
    private CpuBLL cpuBLL;
    private GpuBLL gpuBLL;
    private MouseBLL mouseBLL;
    private KeyboardBLL keyboardBLL;
    private MonitorBLL monitorBLL;
    private HeadphoneBLL headphoneBLL;
    private RomBLL romBLL;
    private RoomBLL roomBLL;
    private ArrayList<Computers> list;
    private Object[][] data;
    private DefaultTableModel model;
    private String[] columnNames;
    // private String[] sessionColumnNames;
    private CustomTable tableData;
    // private CustomTable sessionData;
    private DefaultTableCellRenderer centeredRenderer;

    public ComputerPanel(Accounts loginAccount, Staffs loginStaff) {
        this.loginAccount = loginAccount;
        this.loginStaff = loginStaff;
        

        // Các BLL dùng để truy xuất dữ liệu
        this.computerBLL = new ComputerBLL();
        this.motherboardBLL = new MotherboardBLL();
        this.cpuBLL = new CpuBLL();
        this.gpuBLL = new GpuBLL();
        this.mouseBLL = new MouseBLL();
        this.keyboardBLL = new KeyboardBLL();
        this.monitorBLL = new MonitorBLL();
        this.headphoneBLL = new HeadphoneBLL();
        this.romBLL = new RomBLL();
        this.roomBLL = new RoomBLL();
        this.list = this.computerBLL.getAllComputers(); // Mảng lưu các máy tính

        // Mảng các thuộc tính của JTable hiển thị máy tính
        this.columnNames = new String[]{
                "ID",
                "Tên máy tính",
                "Phòng",
                "Giá tiền",
                "Trạng thái"
        };

        // Khởi tạo object dùng để canh giữa cho chữ trong ô trong JTable
        this.centeredRenderer = new DefaultTableCellRenderer();
        this.centeredRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Gọi hàm khởi tạo các thành phần của Computer Panel
        this.initComponents();
    }

    // Hàm khởi tạo các thành phần của Computer Panel
    private void initComponents() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        // Panel tiêu đề
        this.titlePanel = this.createTitlePanel();

        // Panel chứa ô tìm kiếm, lọc
        this.controlPanel = this.createControlPanel();

        // Panel hiển thị chứa JTable hiển thị thông tin
        this.dataPanel = this.createManagePanel();

        this.titlePanel.setBounds(10, 0, 1080, 100);
        this.controlPanel.setBounds(10, 110, 1080, 95);
        this.dataPanel.setBounds(10,215,1080,503);

        // Thêm các Panel thành phần vào Panel tổng để hiển thị lên
        this.add(titlePanel);
        this.add(controlPanel);
        this.add(dataPanel);
    }

    // Hàm tạo ra Panel tiêu đề
    private CustomPanel createTitlePanel() {

        // Khởi tạo Panel
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        // Tạo tiêu đề bằng JLabel
        JLabel title = new JLabel("QUẢN LÝ MÁY TÍNH");
        title.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        title.setBounds(400, 25, 500, 50);

        // Thêm tiêu đề vào panel
        panel.add(title);

        return panel;
    }

    // Hàm tạo Panel điều khiển
    private CustomPanel createControlPanel() {

        // Khởi tạo Panel
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        // Khởi tạo Label với chữ "Tìm kiếm"
        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(475, 10, 80, 30);


        // Khởi tạo một Text Field để nhập thông tin tìm kiếm
        searchTextField = new CustomTextField("Nhập thông tin tìm kiếm");
        searchTextField.setBounds(475, 38, 200, 35);

        // Tạo Placeholder cho Text Field
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


        // Tạo JLabel vơi chữ "Trạng thái"
        JLabel filterLabel = new JLabel("Trạng thái:");
        filterLabel.setBounds(690, 10, 70, 30);


        // Mảng lưu các giá trị của Combobox trạng thái
        String[] statusList = {
                "Tất cả" ,
                "Trong kho",
                "Đang sử dụng",
                "Đang chờ sử dụng",
                "Thiếu linh kiện",
                "Bảo trì",
                "Hỏng"
        };

        // Khởi tạo Combobox trạng thái bằng mảng lưu các giá trị
        statusComboBox = new CustomCombobox<>(statusList);
        statusComboBox.setBounds(690, 38, 150, 35);


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
        filterButton.setBounds(860, 38, 100, 35);
            // Tạo hiệu ứng khi hover qua Button và hành động khi click vào button
        filterButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                // Khi Button lọc được click thì sẽ gọi hàm lọc lại bảng
                ComputerPanel.this.filterList();
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
        resetButton.setBounds(965, 38, 100, 35);
        resetButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                // Đặt lại placeholder
                ComputerPanel.this.searchTextField
                                    .setText("Nhập thông tin tìm kiếm");

                // Đặt lại trạng thái tất cả
                ComputerPanel.this.statusComboBox
                                    .setSelectedIndex(0);

                // Cập nhật lại bảng với dữ liệu là toàn bộ máy tính
                ComputerPanel.this.resetTable();
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


        // JLabel hiển thị máy đang được chọn
        selectionText = new JLabel("Đang chọn: NULL");
        selectionText.setFont(
                new Font("Sans-serif", Font.BOLD, 12)
        );
        selectionText.setBounds(860,10,300,20);


        Image addImage = new ImageIcon(
                System.getProperty("user.dir") + "/src/Assets/Icon/add.png"
        ).getImage()
         .getScaledInstance(30,30,Image.SCALE_SMOOTH);
        CustomDesignButton addButton = new CustomDesignButton("Thêm", new ImageIcon(addImage));
        addButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        addButton.setHorizontalTextPosition(SwingConstants.CENTER);
        addButton.setBackground(Color.WHITE);
        addButton.setBorderColor(Color.BLACK);
        addButton.setForeground(Color.BLACK);
        addButton.setBounds(20, 10, 75, 70);
        addButton.setBorderSize(3);
        addButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                new AddingComputer();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
//                addButton.setForeground(Color.decode("#388E3C"));
//                addButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                addButton.setForeground(Color.WHITE);
//                addButton.setBackground(Color.decode("#388E3C"));
            }
        });

        Image modifyImage = new ImageIcon(
                System.getProperty("user.dir") + "/src/Assets/Icon/pencil.png"
        ).getImage()
         .getScaledInstance(30,30,Image.SCALE_SMOOTH);
        CustomDesignButton modifyButton = new CustomDesignButton("Sửa", new ImageIcon(modifyImage));
        modifyButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        modifyButton.setHorizontalTextPosition(SwingConstants.CENTER);
        modifyButton.setBackground(Color.WHITE);
        modifyButton.setBorderColor(Color.BLACK);
        modifyButton.setForeground(Color.BLACK);
        modifyButton.setBounds(110, 10, 75, 70);
        modifyButton.setBorderSize(3);
        modifyButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (tableData.getSelectedRow() != -1) {

                    // Lấy ra ID máy tính được chọn
                    int computerId = (int)tableData.getValueAt(tableData.getSelectedRow(), 0);

                    // Lấy ra đối tượng máy tính dựa trên ID được chọn
                    Computers computer = ComputerPanel.this.computerBLL
                            .getComputerById(computerId);

                    // Gọi hàm tạo cửa sổ chỉnh sửa với tham số là đối tượng máy tính
                    new DetailsComputer(computer);
                } else {
                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn máy tính", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
//                modifyButton.setForeground(Color.pink);
//                modifyButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                modifyButton.setForeground(Color.WHITE);
//                modifyButton.setBackground(Color.pink);
            }
        });

        Image deleteImage = new ImageIcon(
                System.getProperty("user.dir") + "/src/Assets/Icon/delete.png"
                ).getImage()
                 .getScaledInstance(30,30,Image.SCALE_SMOOTH);
        CustomDesignButton deleteButton = new CustomDesignButton("Xóa", new ImageIcon(deleteImage));
        deleteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        deleteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setBorderColor(Color.BLACK);
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setBounds(200, 10, 75, 70);
        deleteButton.setBorderSize(3);
        deleteButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (tableData.getSelectedRow() != -1) {
                    // Lấy ID của máy tính được chọn và truyền vào hàm xóa máy tính dựa trên ID
                    boolean isDone = ComputerPanel.this.computerBLL.deleteComputerById((int)tableData.getValueAt(tableData.getSelectedRow(), 0));

                    // Kiểm tra xóa máy tính thành công hay không
                    if (isDone) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Xóa máy tính thành công!",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Xóa máy tính thất bại!",
                                "Lỗi",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Bạn chưa chọn máy tính",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE
                    );
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
//                deleteButton.setForeground(Color.red);
//                deleteButton.setBackground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                deleteButton.setForeground(Color.white);
//                deleteButton.setBackground(Color.red);
            }
        });


        // Thêm tất cả component vào Panel điều khiển
        panel.add(searchLabel);
        panel.add(searchTextField);
        panel.add(filterLabel);
        panel.add(statusComboBox);
        panel.add(filterButton);
        panel.add(resetButton);
        panel.add(selectionText);
        panel.add(addButton);
        panel.add(modifyButton);
        panel.add(deleteButton);


        return panel;
    }

    // Hàm đọc lại dữ liệu từ database cho mảng máy tính và mảng phiên chơi
    private void refreshAllDatas() {
        this.list = this.computerBLL.getAllComputers();
    }


    // Hàm tạo ra dữ liệu cho Table Model của JTable máy tính
    private Object[][] createData(ArrayList<Computers> list) {
        // Khai báo một mảng Object 2 chiều với số dòng là số máy tính, số cột là số thuộc tính sẽ hiển thị trong JTable
        Object[][] data = new Object[list.size()][5];

        for (int i=0; i<list.size(); i++) {
            data[i][0] = list.get(i).getComputerId();
            data[i][1] = list.get(i).getName();

            // Nếu máy tính chưa có ID phòng thì hiển thị "Không có Phòng"
            // Ngược lại hiển thị lên tên phòng (Lấy ra object Phòng thông qua ID)
            data[i][2] = list.get(i)
                            .getRoomId() == null
                                ? "Không có Phòng"
                                : this.roomBLL
                                    .getRoomById(list.get(i).getRoomId())
                                    .getRoomName();

            data[i][3] = Utils.Helper.Comon.formatMoney(list.get(i).getPricePerHour()+"");
            data[i][4] = list.get(i).getStatus();
        }

        return data;
    }

    // Hàm tạo Panel thành phần của Data Panel (CardLayout)
    private CustomPanel createManagePanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        // Gọi hàm đọc lại dữ liệu mới nhất
        this.refreshAllDatas();
            // Tạo ra data cho Model từ mảng máy tính vừa đọc
        this.data = this.createData(this.list);
            // Tạo Model mới từ data vừa tạo
        this.model = new DefaultTableModel(this.data, this.columnNames);

            // Tạo Table với Model
        tableData = new CustomTable(this.model);
            // Tắt tự động điều chỉnh động rộng của cột
            // AUTO_RESIZE thì Table sẽ tự động điều chỉnh độ rộng của cột để có thể hiển thị tất cả cột
        tableData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            // Gọi hàm điều chỉnh độ rộng của cột phù hợp với nội dung
        AdjustTableWidth.automaticallyAdjustTableWidths(tableData);
        tableData.getColumnModel()
                    .getColumn(0)
                    .setPreferredWidth(100);
        tableData.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (tableData.getSelectedRow() != -1) {

                        // Cập nhật tên máy tính được chọn vào label
                        ComputerPanel.this.selectionText
                                            .setText("Đang chọn: " + (String) tableData.getValueAt(tableData.getSelectedRow(), 1));
                    }
                }
            }
        });

        JScrollPane scroll = new CustomScrollPane(tableData);
        scroll.setBounds(0,0,1080,503);



        panel.add(scroll);

        return panel;
    }

    private void updateTable(Object[][] data) {

        // Cập nhật lại model với data được truyền vào
        this.model = new DefaultTableModel(data, this.columnNames);

        // Cập nhật model mới cho Table
        this.tableData.setModel(this.model);

        tableData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        AdjustTableWidth.automaticallyAdjustTableWidths(tableData);

        // Chỉnh width của cột ID thành 100
        tableData.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(100);

        // Căn giữa cho chữ
        for (int i=0; i<tableData.getColumnCount(); i++) {
            tableData.getColumnModel()
                        .getColumn(i)
                        .setCellRenderer(this.centeredRenderer);
        }
    }

    private void filterList() {
        String textFieldContent = this.searchTextField
                                        .getText().trim()
                                        .equals("Nhập thông tin tìm kiếm")
                                        ? ""
                                        : this.searchTextField.getText().trim();
        String status = this.statusComboBox
                            .getSelectedItem()
                            .toString();

        // Nếu nội dung tìm kiếm rỗng và trạng thái là tất cả thì trả về toàn bộ máy tính
        if (textFieldContent.isEmpty() && status.equals("Tất cả")) {
            this.refreshAllDatas();
            Object[][] data = this.createData(this.list);
            this.updateTable(data);
        } else {
            // Gọi hàm trả về máy tính dựa trên nội dung tìm kiếm, trạng thái máy tính
            this.filterListOnNameAndStatus(textFieldContent, status);
        }
    }

    private void resetTable() {
        filterListOnNameAndStatus("", "Tất cả");
    }

    private void filterListOnNameAndStatus(String name, String status) {
        // Đọc lại danh sách máy tính từ database
        this.refreshAllDatas();

        // Lọc các máy tính thỏa nội dung tìm kiếm và trạng thái
        List<Computers> filteredList = this.list.stream()
                                                .filter(computer -> name.isEmpty() || computer.getName().toLowerCase().contains(name))
                                                .filter(computer -> status.equals("Tất cả") || computer.getStatus().equals(status))
                                                .collect(Collectors.toList());

        // Cập nhật lại danh sách máy tính đã lọc vào array list
        this.list = new ArrayList<Computers>(filteredList);

        // Cập nhật lại nội dung hiển thị trong bảng
        Object[][] data = this.createData(this.list);
        this.updateTable(data);
    }
}
