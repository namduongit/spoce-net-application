package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Mouse;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MouseDAL {

    public ArrayList<Mouse> getMouseList() {
        ArrayList<Mouse> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("mouse");
            while (rs.next()) {
                arr.add(new Mouse(
                        rs.getInt("mouse_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
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

    public Mouse getMouseById(int id) {
        ArrayList<Mouse> arr = this.getMouseList();

        for (Mouse x : arr) {
            if (x.getMouseId() == id) {
                return x;
            }
        }

        return null;
    }

    public ArrayList<Mouse> getMousesByStatus(String status) {
        ArrayList<Mouse> arr = new ArrayList<>();

        for (Mouse x : this.getMouseList()) {
            if (x.getStatus().equals(status)) {
                arr.add(x);
            }
        }

        return arr;
    }

    public boolean updateMouseById(int id, HashMap<String, Object> newvalues) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "mouse");
        params.put("WHERE", "mouse.mouse_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.updateData(newvalues, values);
    }
    public boolean deleteMouseById(int id) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "mouse");
        params.put("WHERE", "mouse.mouse_id = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(id);
        return helper.deleteData(values);
    }
}
