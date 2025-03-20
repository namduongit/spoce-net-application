package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Rooms;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDAL {
    public ArrayList<Rooms> getRoomList() {
        ArrayList<Rooms> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("rooms");
            while (rs.next()) {
                arr.add(new Rooms(
                        rs.getInt("room_id"),
                        rs.getString("room_name"),
                        rs.getInt("max_computers"),
                        rs.getString("type"),
                        rs.getString("status"),
                        rs.getDate("created_at")
                ));
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return arr;
    }

    public Rooms getRoomById(int id) {
        ArrayList<Rooms> arr = this.getRoomList();

        for (Rooms x : arr) {
            if (x.getRoomId() == id) {
                return x;
            }
        }

        return null;
    }

    public ArrayList<Rooms> getRoomsByStatus(String status) {
        ArrayList<Rooms> arr = new ArrayList<>();

        for (Rooms x : this.getRoomList()) {
            if (x.getStatus().equals(status)) {
                arr.add(x);
            }
        }

        return arr;
    }
}
