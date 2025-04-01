package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Headphones;
import DTO.Mouse;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class HeadphoneDAL {

    public ArrayList<Headphones> getHeadphoneList() {
        ArrayList<Headphones> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("headphones");
            while (rs.next()) {
                arr.add(new Headphones(
                        rs.getInt("headphone_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("type"),
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

    public Headphones getHeadphoneById(int id) {
        ArrayList<Headphones> arr = this.getHeadphoneList();

        for (Headphones x : arr) {
            if (x.getHeadphoneId() == id) {
                return x;
            }
        }

        return null;
    }

    public ArrayList<Headphones> getHeadphonesByStatus(String status) {
        ArrayList<Headphones> arr = new ArrayList<>();

        for (Headphones x : this.getHeadphoneList()) {
            if (x.getStatus().equals(status)) {
                arr.add(x);
            }
        }

        return arr;
    }

    public boolean updateHeadphoneById(int id, HashMap<String, Object> newvalues) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "headphones");
        params.put("WHERE", "headphones.headphone_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.updateData(newvalues, values);
    }
    public boolean deleteHeadphoneById(int id) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "headphones");
        params.put("WHERE", "headphones.headphone_id = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(id);
        return helper.deleteData(values);
    }
}
