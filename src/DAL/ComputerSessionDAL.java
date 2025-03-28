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
}
