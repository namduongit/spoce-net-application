package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Monitors;
import DTO.Mouse;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<Monitors> getMonitorsByStatus(String status) {
        ArrayList<Monitors> arr = new ArrayList<>();

        for (Monitors x : this.getMonitorList()) {
            if (x.getStatus().equals(status)) {
                arr.add(x);
            }
        }

        return arr;
    }

    public boolean updateMonitorById(int id, HashMap<String, Object> newvalues) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "monitors");
        params.put("WHERE", "monitors.monitor_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.updateData(newvalues, values);
    }
    public boolean deleteMonitorById(int id) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "monitors");
        params.put("WHERE", "monitors.monitor_id = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(id);
        return helper.deleteData(values);
    }
}
