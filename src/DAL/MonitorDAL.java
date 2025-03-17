package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Monitors;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MonitorDAL {

    public ArrayList<Monitors> getMonitorList() {
        ArrayList<Monitors> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("monitors");
            while (rs.next()) {
                arr.add(new Monitors(
                        rs.getInt("monitor_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getFloat("size"),
                        rs.getInt("refresh_rate"),
                        rs.getDate("purchase_date"),
                        rs.getDate("warranty_expiry"),
                        rs.getString("status")
                ));
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return arr;
    }

    public Monitors getMonitorById(int id) {
        ArrayList<Monitors> arr = this.getMonitorList();

        for (Monitors x : arr) {
            if (x.getMonitorId() == id) {
                return x;
            }
        }

        return null;
    }
}
