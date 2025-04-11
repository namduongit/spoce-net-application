package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Rooms;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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

    public boolean deleteRoomById(int id) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "rooms");
        params.put("WHERE", "rooms.room_id = ?");

        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.deleteData(values);
    }

    public boolean updateRoomById(int roomId, HashMap<String, Object> updateValues) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "rooms");
        params.put("WHERE", "rooms.room_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> conditionValues = new ArrayList<>();
        conditionValues.add(roomId);

        return helper.updateData(updateValues, conditionValues);
    }

    public boolean insertRoom(ArrayList<Object> values) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "rooms");
        params.put("FIELD", "room_name, max_computers, type, status");
        helper.buildingQueryParam(params);

        return helper.insertData(values);
    }
}
