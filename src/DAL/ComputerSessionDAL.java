package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.ComputerSessions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class ComputerSessionDAL {

    public ArrayList<ComputerSessions> getComputerSessionList() {
        ArrayList<ComputerSessions> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("computer_sessions");
            while (rs.next()) {
                arr.add(
                        new ComputerSessions(
                                rs.getInt("session_id"),
                                (Integer) rs.getObject("player_id"),
                                rs.getInt("computer_id"),
                                rs.getDate("start_time"),
                                rs.getDate("end_time"),
                                rs.getInt("duration"),
                                rs.getInt("total_cost"),
                                rs.getBoolean("is_guest"),
                                rs.getInt("staff_id")));
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Lỗi ComputerSessionDAL dòng 42",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }

        return arr;
    }

    public boolean insertComputerSession(ArrayList<Object> values) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computer_sessions");
        params.put("FIELD", "computer_id, staff_id");
        helper.buildingQueryParam(params);

        return helper.insertData(values);
    }

    public boolean updateComputerSession(int computerId, HashMap<String, Object> updatingValues) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computer_sessions");
        params.put("WHERE", "computer_sessions.computer_id = ? AND computer_sessions.end_time = NULL");
        helper.buildingQueryParam(params);

        ArrayList<Object> conditionValues = new ArrayList<>();
        conditionValues.add(computerId);

        return helper.updateData(updatingValues, conditionValues);
    }

    public boolean updateEndTimeOfComputerSession(int computerId) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computer_sessions");
        params.put("SET", "computer_sessions.end_time = CURRENT_TIMESTAMP()");
        params.put("WHERE", "computer_sessions.computer_id = ? AND computer_sessions.end_time IS NULL");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(computerId);

        return helper.updateData(values);
    }

    public boolean updateTotalCostOfComputerSession(int computerId, int pricePerHour) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computer_sessions");
        params.put("SET", "total_cost = (duration/60) * " + pricePerHour);
        params.put("WHERE", "computer_id = ? AND total_cost = 0.00");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(computerId);

        return helper.updateData(values);
    }

    // Lấy dữ liệu doanh thu máy
    public ArrayList<Object[]> getComputerRevenue(LocalDateTime start, LocalDateTime end) {
        ArrayList<Object[]> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computer_sessions cs");
        params.put("JOIN", "computers c ON cs.computer_id = c.computer_id LEFT JOIN rooms r ON c.room_id = r.room_id");
        params.put("SELECT", "c.name, cs.duration, cs.total_cost, r.room_name");
        params.put("WHERE", "cs.start_time BETWEEN ? AND ? AND cs.end_time IS NOT NULL");
        params.put("GROUP BY", "cs.session_id, c.name, cs.duration, cs.total_cost, r.room_name");

        helper.buildingQueryParam(params);
        // System.out.println("Query Computer : " + params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(java.sql.Timestamp.valueOf(start));
        values.add(java.sql.Timestamp.valueOf(end));

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int duration = resultSet.getInt("duration");
                double totalCost = resultSet.getDouble("total_cost");
                String roomName = resultSet.getString("room_name");
                // System.out.println("ResultSet: Name=" + name + ", TotalCost=" + totalCost);
                Object[] row = new Object[] { name, duration, totalCost, roomName };
                list.add(row);
            }
            // list.stream().forEach(r -> {
            // System.out.println("List :" + r[0]);
            // });
            // for(int i = 0; i < list.size(); i++) {
            // System.out.println("List " + i + ": " + list.get(i)[0] + ", " +
            // list.get(i)[1]);
            // }
            // list = [
            // ["Máy 1", 50000.0],
            // ["Máy 2", 70000.0],
            // ["Máy 3", 45000.0]
            // ];
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    public HashMap<String, Object> getComputerInfoAndRoomInfoOfSession(int sessionId) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, Object> map = new HashMap<>();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computer_sessions cs");
        params.put("WHERE", "cs.session_id = ?");
        params.put("JOIN", "computers c ON cs.computer_id = c.computer_id JOIN rooms r ON c.room_id = r.room_id");
        params.put("SELECT",
                "r.room_id, r.room_name, c.computer_id, c.name, c.price_per_hour, cs.start_time, cs.end_time, cs.duration, cs.total_cost");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(sessionId);

        try {
            ResultSet rs = helper.queryWithParam(values);
            while (rs.next()) {
                map.put("room_id", rs.getInt("room_id"));
                map.put("room_name", rs.getString("room_name"));
                map.put("computer_id", rs.getInt("computer_id"));
                map.put("name", rs.getString("name"));
                map.put("price_per_hour", rs.getInt("price_per_hour"));
                map.put("start_time", rs.getTimestamp("start_time"));
                map.put("end_time", rs.getTimestamp("end_time"));
                map.put("duration", rs.getInt("duration"));
                map.put("total_cost", rs.getInt("total_cost"));
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Lỗi tại hàm getComputerInfoAndRoomInfoOfSession",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }

        return map;
    }

    public ArrayList<String> getComputersInRoom(String roomName) {
        ArrayList<String> computers = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computers c");
        params.put("JOIN", "rooms r ON c.room_id = r.room_id");
        params.put("SELECT", "c.name");
        params.put("WHERE", "r.room_name = ?");

        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(roomName);

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                computers.add(resultSet.getString("name"));
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return computers;
    }

    public ArrayList<String> getRoomNames() {
        ArrayList<String> roomNames = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "rooms");
        params.put("SELECT", "room_name");

        helper.buildingQueryParam(params);

        ResultSet resultSet = helper.queryWithParam(new ArrayList<>());
        try {
            while (resultSet.next()) {
                roomNames.add(resultSet.getString("room_name"));
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return roomNames;
    }

    // Thêm phương thức để lấy tất cả các phòng
    public ArrayList<String> getAllRooms() {
        ArrayList<String> roomNames = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "rooms");
        params.put("SELECT", "room_name");
        params.put("ORDER BY", "room_name");

        helper.buildingQueryParam(params);

        ResultSet resultSet = helper.queryWithParam(new ArrayList<>());
        try {
            while (resultSet.next()) {
                roomNames.add(resultSet.getString("room_name"));
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return roomNames;
    }

    public ArrayList<Object[]> getComputersWithRevenue(LocalDateTime start, LocalDateTime end) {
        ArrayList<Object[]> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computer_sessions cs");
        params.put("JOIN", "computers c ON cs.computer_id = c.computer_id LEFT JOIN rooms r ON c.room_id = r.room_id");
        params.put("SELECT", "c.name, r.room_name");
        params.put("WHERE", "cs.start_time BETWEEN ? AND ? AND cs.total_cost > 0 AND cs.end_time IS NOT NULL");
        params.put("GROUP BY", "c.computer_id, c.name, r.room_name");

        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(java.sql.Timestamp.valueOf(start));
        values.add(java.sql.Timestamp.valueOf(end));

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                String computerName = resultSet.getString("name");
                String roomName = resultSet.getString("room_name");
                Object[] row = new Object[]{computerName, roomName};
                list.add(row);
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

}
