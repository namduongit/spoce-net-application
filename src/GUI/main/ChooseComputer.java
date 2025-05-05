package GUI.main;

import BLL.ComputerBLL;
import BLL.RoomBLL;
import DTO.Computers;
import DTO.Rooms;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomScrollPane;
import GUI.Form.DetailsComputerSpecs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChooseComputer extends JFrame {
    private ComputerBLL computerBLL;
    private RoomBLL roomBLL;
    private ArrayList<Computers> computerList;
    private ArrayList<Rooms> roomList;
    private ArrayList<String> roomStrings;
    private CustomCombobox<String> roomCombobox;

    private ArrayList<String> statusStrings;
    private CustomCombobox<String> statusCombobox;

    private int currentSelectedComputerId;

    private JPanel dataButtonPanel;
    private JPanel computerPanel;
    private CustomScrollPane scrollDataPanel;

    private int cols;
    private int rows;

    private JLabel chooseComputer;

    public ChooseComputer() {
        this.computerBLL = new ComputerBLL();
        this.roomBLL = new RoomBLL();

        this.computerList = this.computerBLL.getAllComputers();
        this.roomList = this.roomBLL.getAllRooms();

        this.roomStrings = new ArrayList<>();
        roomStrings.add("Tất cả");
        for (Rooms rooms : this.roomList) {
            roomStrings.add(rooms.getRoomId() + " - " + rooms.getRoomName());
        }

        this.statusStrings = new ArrayList<>(List.of("Tất cả", "Đang sử dụng", "Đang chờ sử dụng"));
        this.cols = 4;
        this.rows = (int) Math.ceil((double) this.computerList.size() / cols);
        this.initComponent();
    }

    private void initComponent() {
        this.setTitle("Mời người dùng chọn máy tính");
        this.setSize(1200, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        // Panel chọn phòng và trạng thái
        JPanel chooseRoomPanel = new JPanel(null);
        chooseRoomPanel.setBounds(0, 0, 1200, 65);

        JLabel chooseRoomsLabel = new JLabel("Chọn phòng chơi");
        chooseRoomsLabel.setBounds(20, 20, 150, 25);
        chooseRoomPanel.add(chooseRoomsLabel);

        this.roomCombobox = new CustomCombobox<>(this.roomStrings);
        this.roomCombobox.setBounds(150, 15, 200, 35);
        chooseRoomPanel.add(this.roomCombobox);

        JLabel statusComputerLabel = new JLabel("Trạng thái máy tính");
        statusComputerLabel.setBounds(400, 20, 150, 25);
        chooseRoomPanel.add(statusComputerLabel);

        this.statusCombobox = new CustomCombobox<>(this.statusStrings);
        this.statusCombobox.setBounds(550, 15, 200, 35);
        chooseRoomPanel.add(this.statusCombobox);

        this.chooseComputer = new JLabel("Đang chọn: NULL");
        this.chooseComputer.setBounds(800, 15, 100, 25);
        chooseRoomPanel.add(this.chooseComputer);

        this.add(chooseRoomPanel);

        // Panel chứa danh sách máy tính dạng nút
        computerPanel = new JPanel(new BorderLayout());
        computerPanel.setBounds(0, 65, 1200, 425);

        this.rows = (int) Math.ceil((double) this.computerList.size() / cols);
        this.dataButtonPanel = new JPanel();
        this.dataButtonPanel.setLayout(new GridLayout(rows, cols, 20, 20));

        for (Computers computers : this.computerList) {
            CustomButton button = Utils.Helper.CreateComponent.createButton("icons8-this-pc-100.png", computers.getName());
            button.addActionListener(e -> this.currentSelectedComputerId = computers.getComputerId());
            button.addActionListener(e -> {
                this.chooseComputer.setText("Đang chọn: "+ computers.getComputerId());
            });
            this.dataButtonPanel.add(button);
        }

        this.dataButtonPanel.setPreferredSize(new Dimension(1200, rows * 50));

        this.scrollDataPanel = new CustomScrollPane(this.dataButtonPanel);
        computerPanel.add(this.scrollDataPanel, BorderLayout.CENTER);

        this.add(computerPanel);

        // Panel chứa các nút chọn, xem chi tiết
        JPanel buttonPanel = new JPanel(null);
        buttonPanel.setBounds(0, 490, 1200, 100);

        CustomButton chooseComputerButton = Utils.Helper.CreateComponent.createGreenButton("Chọn máy");
        chooseComputerButton.setBounds(20, 20, 150, 30);
        chooseComputerButton.addActionListener(e -> {
            String[] regexStrings = this.chooseComputer.getText().split("\\s+");
            if (regexStrings[regexStrings.length - 1].equalsIgnoreCase("NULL")) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn máy tính", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int computerId = Integer.parseInt(regexStrings[regexStrings.length - 1]);
            int result = JOptionPane.showConfirmDialog(this, "Chắc chắn chọn máy này chứ ?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                new PlayerLoginScreen(computerId).setVisible(true);
                this.dispose();
            }
        });
        buttonPanel.add(chooseComputerButton);

        CustomButton detailsComputerButton = Utils.Helper.CreateComponent.createBrownButton("Thông tin");
        detailsComputerButton.setBounds(190, 20, 150, 30);
        detailsComputerButton.addActionListener(e -> new DetailsComputerSpecs(this.currentSelectedComputerId).setVisible(true));
        buttonPanel.add(detailsComputerButton);

        CustomButton filterComputerButton = Utils.Helper.CreateComponent.createOrangeButton("Lọc");
        filterComputerButton.setBounds(360, 20, 150, 30);
        filterComputerButton.addActionListener(e -> this.filterComputerList());
        buttonPanel.add(filterComputerButton);

        CustomButton resetComputerButton = Utils.Helper.CreateComponent.createBlueButton("Đặt lại");
        resetComputerButton.setBounds(530, 20, 150, 30);
        resetComputerButton.addActionListener(e -> this.resetComputerList());
        buttonPanel.add(resetComputerButton);

        this.add(buttonPanel);
    }

    private void filterComputerList() {
        String roomId = this.roomCombobox.getSelectedItem().toString().equals("Tất cả") ? "" : this.roomCombobox.getSelectedItem().toString().split(" ")[0];
        String status = this.statusCombobox.getSelectedItem().toString();

        this.computerList = this.computerBLL.getAllComputers();
        List<Computers> filteredList = this.computerList.stream()
                                                        .filter(computer -> roomId.isEmpty() || computer.getRoomId() == Integer.parseInt(roomId))
                                                        .filter(computer -> status.equals("Tất cả") || computer.getStatus().equals(status))
                                                        .collect(Collectors.toList());

        this.computerList = new ArrayList<>(filteredList);

        this.computerPanel.removeAll();
        this.computerPanel.revalidate();
        this.computerPanel.repaint();

        this.rows = (int) Math.ceil((double) this.computerList.size() / this.cols);
        this.dataButtonPanel = new JPanel(new GridLayout(this.rows, this.cols, 20, 20));
        for (Computers computers : this.computerList) {
            CustomButton button = Utils.Helper.CreateComponent.createButton("icons8-this-pc-100.png", computers.getName());
            button.addActionListener(e -> this.currentSelectedComputerId = computers.getComputerId());
            this.dataButtonPanel.add(button);
        }

        this.dataButtonPanel.setPreferredSize(new Dimension(1200, rows * 50));

        this.scrollDataPanel = new CustomScrollPane(this.dataButtonPanel);
        this.computerPanel.add(this.scrollDataPanel, BorderLayout.CENTER);
    }

    private void resetComputerList() {
        this.computerList = this.computerBLL.getAllComputers();
        this.computerPanel.removeAll();
        this.computerPanel.revalidate();
        this.computerPanel.repaint();

        this.rows = (int) Math.ceil((double) this.computerList.size() / this.cols);
        this.dataButtonPanel = new JPanel(new GridLayout(this.rows, this.cols, 20, 20));
        for (Computers computer : this.computerList) {
            CustomButton button = Utils.Helper.CreateComponent.createButton("icons8-this-pc-100.png", computer.getName());
            button.addActionListener(e -> this.currentSelectedComputerId = computer.getComputerId());
            this.dataButtonPanel.add(button);
        }

        this.dataButtonPanel.setPreferredSize(new Dimension(1200, this.rows * 50));
        this.scrollDataPanel = new CustomScrollPane(this.dataButtonPanel);
        this.computerPanel.add(this.scrollDataPanel, BorderLayout.CENTER);

        this.roomCombobox.setSelectedIndex(0);
        this.statusCombobox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        new ChooseComputer().setVisible(true);
    }
}
