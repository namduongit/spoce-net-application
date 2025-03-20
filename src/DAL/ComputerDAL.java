package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Computers;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
                        rs.getInt("price_per_hour"),
                        rs.getInt("motherboard_id"),
                        (Integer)rs.getObject("mouse_id"),
                        (Integer)rs.getObject("keyboard_id"),
                        (Integer)rs.getObject("monitor_id"),
                        (Integer)rs.getObject("headphone_id"),
                        (Integer)rs.getObject("rom_id"),
                        (Integer)rs.getObject("room_id"),
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

    public Computers getComputerById(int id) {
        ArrayList<Computers> arr = this.getComputerList();

        for (Computers x : arr) {
            if (x.getComputerId() == id) {
                return x;
            }
        }

        return null;
    }

    public boolean updateComputerById(int id, HashMap<String, Object> newValues) {
        if (newValues == null || newValues.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computers");
        params.put("WHERE", "computers.computer_id = ?");

        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.updateData(newValues, values);
    }
}
