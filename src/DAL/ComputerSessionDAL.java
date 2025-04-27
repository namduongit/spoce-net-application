package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.ComputerSessions;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                        rs.getInt("session_id"),
                        (Integer)rs.getObject("player_id"),
                        rs.getInt("computer_id"),
                        rs.getDate("start_time"),
                        rs.getDate("end_time"),
                        rs.getInt("duration"),
                        rs.getDouble("total_cost"),
                        rs.getBoolean("is_guest"),
                        rs.getInt("staff_id")
                    )
                );
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Never",
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
}
