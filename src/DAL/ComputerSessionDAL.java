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
                        rs.getInt("total_cost"),
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
        params.put("SET", "total_cost = FLOOR( (duration / 60) * " + pricePerHour + " )");
        params.put("WHERE", "computer_id = ? AND total_cost = 0");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(computerId);

        return helper.updateData(values);
    }
    /**
    'computer_sessions', 'CREATE TABLE `computer_sessions` (
    \n  `session_id` int NOT NULL AUTO_INCREMENT,
    \n  `player_id` int DEFAULT NULL,
    \n  `computer_id` int NOT NULL,
    \n  `start_time` datetime DEFAULT CURRENT_TIMESTAMP,
    \n  `end_time` datetime DEFAULT NULL,
    \n  `duration` int GENERATED ALWAYS AS (timestampdiff(MINUTE,`start_time`,`end_time`)) STORED,
    \n  `total_cost` int DEFAULT NULL,
    \n  `is_guest` tinyint(1) GENERATED ALWAYS AS ((`player_id` is null)) STORED,
    \n  `staff_id` int NOT NULL,
    \n  PRIMARY KEY (`session_id`),
    \n  KEY `staff_id` (`staff_id`),
    \n  KEY `player_id` (`player_id`),
    \n  KEY `computer_id` (`computer_id`),
    \n  CONSTRAINT `computer_sessions_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `staffs` (`staff_id`),
    \n  CONSTRAINT `computer_sessions_ibfk_2` FOREIGN KEY (`player_id`) REFERENCES `players` (`player_id`),
    \n  CONSTRAINT `computer_sessions_ibfk_3` FOREIGN KEY (`computer_id`) REFERENCES `computers` (`computer_id`) ON DELETE CASCADE\n) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci'

     */
}
