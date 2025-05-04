package GUI.main;

import BLL.ComputerBLL;
import BLL.RoomBLL;
import DTO.Computers;
import DTO.Rooms;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomScrollPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChooseComputer extends JFrame {
    private ComputerBLL computerBLL;
    private RoomBLL roomBLL;
    private ArrayList<Computers> computerList;
    private ArrayList<Rooms> roomList;
    private ArrayList<String> roomStrings;
    private CustomCombobox<String> roomCombobox;

    private ArrayList<String> statusStrings;
    private CustomCombobox<String> statusCombobox;

    JPanel dataButtonPanel;
    CustomScrollPane scrollDataPanel;

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
        JPanel computerPanel = new JPanel(new BorderLayout());
        computerPanel.setBounds(0, 65, 1200, 425);

        this.dataButtonPanel = new JPanel();

        int cols = 4;
        int rows = (int) Math.ceil((double) this.computerList.size() / cols);
        this.dataButtonPanel.setLayout(new GridLayout(rows, cols, 20, 20));

        for (Computers computers : this.computerList) {
            CustomButton button = Utils.Helper.CreateComponent.createButton("icons8-this-pc-100.png", computers.getName());
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
        buttonPanel.add(detailsComputerButton);

        this.add(buttonPanel);
    }

    public static void main(String[] args) {
        new ChooseComputer().setVisible(true);
    }
}
