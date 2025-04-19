package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Monitors;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

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
                        rs.getString("status"),
                        rs.getDouble("price")
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
        boolean r = helper.deleteData(values);
        helper.closeConnect();
        return r;
    }
    public boolean addMonitor(Monitors monitor) {
        MySQLHelper helper = new MySQLHelper();
        if(this.getMonitorById(monitor.getMonitorId()) != null) {
            JOptionPane.showMessageDialog(null, "ID " + monitor.getMonitorId() + " đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        ArrayList<Object> values = new ArrayList<>();
            values.add(monitor.getMonitorId());
            values.add(monitor.getBrand());
            values.add(monitor.getModel());
            values.add(monitor.getSize());
            values.add(monitor.getRefreshRate());
            values.add(monitor.getPurchaseDate());
            values.add(monitor.getWarrantyExpiry());
            values.add(monitor.getStatus());
            values.add(monitor.getPrice());

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "monitors");
        params.put("FIELD", "monitor_id,brand,model,size,refresh_rate,purchase_date,warranty_expiry,status");
        helper.buildingQueryParam(params);


        boolean result = helper.insertData(values);
        helper.closeConnect();
        return result;
    }
}
