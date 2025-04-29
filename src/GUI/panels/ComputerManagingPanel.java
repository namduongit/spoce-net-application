package GUI.panels;

import BLL.ComputerBLL;
import BLL.ComputerSessionBLL;
import BLL.RoomBLL;
import DTO.Accounts;
import DTO.Computers;
import DTO.Rooms;
import DTO.Staffs;
import GUI.Card.ComputerCard;
import GUI.Components.*;
import Utils.Helper.CreateComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class ComputerManagingPanel extends JPanel {
    
    private Accounts loginAccount;

    private Staffs loginStaff;

    
    private CustomPanel titlePanel;
    private CustomPanel dataPanel;
    private CustomPanel playedComputerPanel;
    private CustomPanel idlingComputerPanel;
    private CustomPanel controlPanel;
    private JScrollPane scrollPane;
    private CustomTextField searchTextField;
    private CustomCombobox<String> statusComboBox;
    private JLabel selectionText;
    private ComputerBLL computerBLL;

    private ComputerSessionBLL computerSessionBLL;
    private RoomBLL roomBLL;
    private CardLayout cardLayout;
    private CustomDesignButton turnOnButton;
   
    private CustomDesignButton turnOffButton;
    private CustomDesignButton controlButton;
    private int currentSelectedId;
    private String currentPanel;

    private ArrayList<Computers> playedComputerList;
    private ArrayList<Computers> idlingComputerList;

    public ComputerManagingPanel(Accounts loginAccount, Staffs loginStaff) {
        this.loginAccount = loginAccount;
        this.loginStaff = loginStaff;

        System.out.println(loginAccount);
        System.out.println(loginStaff);

        this.currentPanel = "PlayedComputer";
        this.currentSelectedId = -1;
        this.computerBLL = new ComputerBLL();
        this.computerSessionBLL = new ComputerSessionBLL();
        this.roomBLL = new RoomBLL();
        this.cardLayout = new CardLayout();

        Image addImage = new ImageIcon(
                System.getProperty("user.dir") + "/src/Assets/Icon/add.png"
        ).getImage()
                .getScaledInstance(30,30,Image.SCALE_SMOOTH);
        this.turnOnButton = new CustomDesignButton("Mở", new ImageIcon(addImage));
        this.turnOnButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.turnOnButton.setHorizontalTextPosition(SwingConstants.CENTER);
        this.turnOnButton.setBackground(Color.WHITE);
        this.turnOnButton.setBorderColor(Color.BLACK);
        this.turnOnButton.setForeground(Color.BLACK);
        this.turnOnButton.setBounds(20, 10, 75, 70);
        this.turnOnButton.setBorderSize(3);
        this.turnOnButton.addActionListener(e -> this.makeSession());

        this.turnOffButton = new CustomDesignButton("Tắt", new ImageIcon(addImage));
        this.turnOffButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.turnOffButton.setHorizontalTextPosition(SwingConstants.CENTER);
        this.turnOffButton.setBackground(Color.WHITE);
        this.turnOffButton.setBorderColor(Color.BLACK);
        this.turnOffButton.setForeground(Color.BLACK);
        this.turnOffButton.setBounds(20, 10, 75, 70);
        this.turnOffButton.setBorderSize(3);
        this.turnOffButton.addActionListener(e -> this.endSession());

        this.playedComputerList = this.computerBLL.getComputersByStatus("Đang sử dụng");
        this.idlingComputerList = this.computerBLL.getComputersByStatus("Đang chờ sử dụng");

        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        this.titlePanel = this.createTitlePanel();
        this.controlPanel = this.createControlPanel();
        this.dataPanel = this.createDataPanel();

        this.titlePanel.setBounds(10, 0, 1080, 150);
        this.controlPanel.setBounds(10, 160, 1080, 95);
        scrollPane = new CustomScrollPane(this.dataPanel);
        scrollPane.setBounds(10,265,1080,453);
        this.add(titlePanel);
        this.add(controlPanel);
        this.add(scrollPane);
    }

    private CustomPanel createPlayedComputerPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        for (Computers x : this.playedComputerList) {
            Rooms room = this.roomBLL.getRoomById(x.getRoomId());
            ComputerCard computerCard = new ComputerCard(x.getComputerId(), x.getName(), room.getRoomName(), x.getPricePerHour());
            computerCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ComputerManagingPanel.this.selectionText.setText("Đang chọn: " + x.getName());
                    ComputerManagingPanel.this.currentSelectedId = x.getComputerId();
                }
            });

            panel.add(computerCard);
        }

        panel.setPreferredSize(new Dimension(1080, (int) Math.ceil(this.playedComputerList.size() / 5) * 320));

        return panel;
    }

    private CustomPanel createIdlingComputerPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        for (Computers x : this.idlingComputerList) {
            Rooms room = this.roomBLL.getRoomById(x.getRoomId());
            ComputerCard computerCard = new ComputerCard(x.getComputerId(), x.getName(), room.getRoomName(), x.getPricePerHour());
            computerCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ComputerManagingPanel.this.selectionText.setText("Đang chọn: " + x.getName());
                    ComputerManagingPanel.this.currentSelectedId = x.getComputerId();
                }
            });
            panel.add(computerCard);
        }

        panel.setPreferredSize(new Dimension(1080, (int)Math.ceil(this.idlingComputerList.size() / 5) * 320));

        return panel;
    }

    private CustomPanel createDataPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(this.cardLayout);

        playedComputerPanel = this.createPlayedComputerPanel();
        idlingComputerPanel = this.createIdlingComputerPanel();

        panel.add(playedComputerPanel, "PlayedComputer");
        panel.add(idlingComputerPanel, "IdlingComputer");

        return panel;
    }

    private CustomPanel createControlPanel() {
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
        JLabel filterLabel = new JLabel("Loại phòng:");
        filterLabel.setBounds(690, 10, 70, 30);


        // Mảng lưu các giá trị của Combobox trạng thái
        ArrayList<String> roomTypeList = new ArrayList<>();
        roomTypeList.add("Tất cả");
        for (Rooms x : this.roomBLL.getAllRooms()) {
            roomTypeList.add(x.getRoomName());
        }

        // Khởi tạo Combobox trạng thái bằng mảng lưu các giá trị
        statusComboBox = new CustomCombobox<>(roomTypeList);
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
                ComputerManagingPanel.this.filterList();
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
                ComputerManagingPanel.this.searchTextField
                        .setText("Nhập thông tin tìm kiếm");

                // Đặt lại trạng thái tất cả
                ComputerManagingPanel.this.statusComboBox
                        .setSelectedIndex(0);

                // Cập nhật lại bảng với dữ liệu là toàn bộ máy tính
                ComputerManagingPanel.this.resetDataPanel();
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


        if (this.currentPanel.equals("PlayedComputer")) {
            panel.add(turnOffButton);
        } else if (this.currentPanel.equals("IdlingComputer")) {
            panel.add(turnOnButton);
        }

        panel.add(searchLabel);
        panel.add(searchTextField);
        panel.add(filterLabel);
        panel.add(statusComboBox);
        panel.add(filterButton);
        panel.add(resetButton);
        panel.add(selectionText);
        return panel;
    }


    private void filterList() {
        String searchText = this.searchTextField.getText().equals("Nhập thông tin tìm kiếm")
                            ? "" : this.searchTextField.getText();
        String roomType = this.statusComboBox.getSelectedItem().toString();

        if (searchText.isEmpty() && roomType.equals("Tất cả")) {
            this.resetDataPanel();
        } else {
            if (this.currentPanel.equals("PlayedComputer")) {
                int currentSelectedRoomType = this.statusComboBox.getSelectedIndex();
                String currentSearchText = this.searchTextField.getText();

                this.playedComputerList = this.computerBLL.getComputersByStatus("Đang sử dụng");
                List<Computers> filteredList = this.playedComputerList.stream()
                                                                      .filter(computer -> searchText.isEmpty() || computer.getName().contains(searchText))
                                                                      .filter(computer -> roomType.equals("Tất cả") || this.roomBLL.getRoomById(computer.getRoomId()).getRoomName().equals(roomType))
                                                                      .collect(Collectors.toList());

                this.playedComputerList = new ArrayList<>(filteredList);
                this.removeAll();
                this.revalidate();
                this.repaint();
                this.initComponents();
                this.cardLayout.show(this.dataPanel, "PlayedComputer");
                this.statusComboBox.setSelectedIndex(currentSelectedRoomType);
                this.searchTextField.setText(currentSearchText);
            } else if (this.currentPanel.equals("IdlingComputer")) {
                int currentSelectedRoomType = this.statusComboBox.getSelectedIndex();
                String currentSearchText = this.searchTextField.getText();

                this.idlingComputerList = this.computerBLL.getComputersByStatus("Đang chờ sử dụng");
                List<Computers> filteredList = this.idlingComputerList.stream()
                        .filter(computer -> searchText.isEmpty() || computer.getName().contains(searchText))
                        .filter(computer -> roomType.equals("Tất cả") || this.roomBLL.getRoomById(computer.getRoomId()).getRoomName().equals(roomType))
                        .collect(Collectors.toList());

                this.idlingComputerList = new ArrayList<>(filteredList);
                this.removeAll();
                this.revalidate();
                this.repaint();
                this.initComponents();
                this.cardLayout.show(this.dataPanel, "IdlingComputer");
                this.statusComboBox.setSelectedIndex(currentSelectedRoomType);
                this.searchTextField.setText(currentSearchText);
            }
        }
    }

    private CustomPanel createTitlePanel() {
        CustomPanel panel = new CustomPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("BẬT TẮT MÁY TÍNH");
        title.setBounds(400, 25, 500, 50);
        title.setFont(
                new Font("Sans-serif", Font.PLAIN, 30)
        );

        CustomButton playingComputerSectionButton = CreateComponent.createButtonNoIcon("Máy tính đang bật");
        playingComputerSectionButton.setBounds(20, 100, 175, 35);
        playingComputerSectionButton.addActionListener(e -> {
            this.cardLayout.show(this.dataPanel, "PlayedComputer");
            this.controlPanel.remove(this.turnOnButton);
            this.controlPanel.add(this.turnOffButton);
            this.controlPanel.revalidate();
            this.controlPanel.repaint();
            this.currentPanel = "PlayedComputer";
            this.currentSelectedId = -1;
        });

        CustomButton idlingComputerSectionButton = CreateComponent.createButtonNoIcon("Máy tính đang tắt");
        idlingComputerSectionButton.addActionListener(e -> {
            this.cardLayout.show(this.dataPanel, "IdlingComputer");
            this.controlPanel.add(this.turnOnButton);
            this.controlPanel.remove(this.turnOffButton);
            this.controlPanel.revalidate();
            this.controlPanel.repaint();
            this.currentPanel = "IdlingComputer";
            this.currentSelectedId = -1;
        });
        idlingComputerSectionButton.setBounds(205, 100, 175, 35);

        panel.add(title);
        panel.add(playingComputerSectionButton);
        panel.add(idlingComputerSectionButton);
        return panel;
    }

    private void makeSession() {
        if (currentSelectedId == -1) {
            JOptionPane.showMessageDialog(
                    null,
                    "Bạn chưa chọn máy tính để mở!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        ArrayList<Object> values = new ArrayList<>();
        values.add(this.currentSelectedId);
        values.add(this.loginStaff.getStaffId());

        if (this.computerSessionBLL.insertComputerSession(values)) {
            HashMap<String, Object> updateValues = new HashMap<>();
            updateValues.put("status", "Đang sử dụng");
            this.computerBLL.updateComputerById(this.currentSelectedId, updateValues);

            JOptionPane.showMessageDialog(
                    null,
                    "Mở máy thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );

            this.resetDataPanel();
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Mở máy thất bại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void endSession() {
        if (currentSelectedId == -1) {
            JOptionPane.showMessageDialog(
                    null,
                    "Bạn chưa chọn máy tính để tắt!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }
        Computers computer = this.computerBLL.getComputerById(this.currentSelectedId);
        HashMap<String, Object> values = new HashMap<>();
        values.put("status", "Đang chờ sử dụng");

        boolean result = this.computerSessionBLL.updateEndTimeOfComputerSession(this.currentSelectedId)
                         && this.computerBLL.updateComputerById(this.currentSelectedId, values)
                         && this.computerSessionBLL.updateTotalCostOfComputerSession(this.currentSelectedId, computer.getPricePerHour());

        if (result) {
            JOptionPane.showMessageDialog(
                    null,
                    "Tắt máy thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Tắt máy thất bại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        this.resetDataPanel();
    }
    
    private void resetData() {
        this.idlingComputerList = this.computerBLL.getComputersByStatus("Đang chờ sử dụng");
        this.playedComputerList = this.computerBLL.getComputersByStatus("Đang sử dụng");
    }
    
    private void resetDataPanel() {
        this.resetData();
        this.removeAll();
        this.revalidate();
        this.repaint();
        this.initComponents();
        this.cardLayout.show(this.dataPanel, this.currentPanel);
    }
}
