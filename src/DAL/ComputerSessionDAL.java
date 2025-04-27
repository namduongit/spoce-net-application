package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.ComputerSessions;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ComputerSessionDAL {

    public ArrayList<ComputerSessions> getComputerSessionList() {
        ArrayList<ComputerSessions> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("computer_sessions");
            while (rs.next()) {
                arr.add(
                    new ComputerSessions(
                        rs.getInt(0),
                        (Integer)rs.getObject(1),
                        rs.getInt(2),
                        rs.getDate(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getDouble(6),
                        rs.getBoolean(7)
                    )
                );
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return arr;
    }

    public boolean insertComputerSession(ArrayList<Object> values) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computer_sessions");
        params.put("FIELD", "computer_id");
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
        params.put("JOIN", "computers c ON cs.computer_id = c.computer_id");
        params.put("SELECT", "c.name, SUM(cs.total_cost) as total_cost");
        params.put("WHERE", "cs.start_time BETWEEN ? AND ?");
        params.put("GROUP BY", "c.computer_id, c.name");

        helper.buildingQueryParam(params);
        System.out.println("Query: " + params); // Gỡ lỗi
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(java.sql.Timestamp.valueOf(start));
        values.add(java.sql.Timestamp.valueOf(end));

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                Object[] row = new Object[]{
                    resultSet.getString("name"),
                    resultSet.getDouble("total_cost")
                };
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
