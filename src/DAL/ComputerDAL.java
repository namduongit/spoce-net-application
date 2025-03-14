package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Computers;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComputerDAL {

    public ArrayList<Computers> getComputerList() {
        ArrayList<Computers> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        // lấy tất cả dòng dữ liệu của bảng computers
        ResultSet rs = helper.selectAllFromTable("computers");

        try {
            while (rs.next()) {
                Computers computer = new Computers(
                        rs.getInt("computer_id"),
                        rs.getString("name"),
                        rs.getDouble("price_per_hour"),
                        rs.getInt("motherboard_id"),
                        rs.getInt("mouse_id"),
                        rs.getInt("keyboard_id"),
                        rs.getInt("monitor_id"),
                        rs.getInt("headphone_id"),
                        rs.getInt("rom_id"),
                        rs.getInt("room_id"),
                        rs.getString("status")
                );

                arr.add(computer);
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return arr;
    }
}
