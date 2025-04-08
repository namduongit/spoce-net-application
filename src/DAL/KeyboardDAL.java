package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Keyboards;
import DTO.Mouse;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class KeyboardDAL {

    public ArrayList<Keyboards> getKeyboardList() {
        ArrayList<Keyboards> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("keyboards");
            while (rs.next()) {
                arr.add(new Keyboards(
                        rs.getInt("keyboard_id"),
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

    public Keyboards getKeyboardById(int id) {
        ArrayList<Keyboards> arr = this.getKeyboardList();

        for (Keyboards x : arr) {
            if (x.getKeyboardId() == id) {
                return x;
            }
        }

        return null;
    }

    public ArrayList<Keyboards> getKeyboardsByStatus(String status) {
        ArrayList<Keyboards> arr = new ArrayList<>();

        for (Keyboards x : this.getKeyboardList()) {
            if (x.getStatus().equals(status)) {
                arr.add(x);
            }
        }

        return arr;
    }

    public boolean updateKeyboardById(int id, HashMap<String, Object> newvalues) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "keyboards");
        params.put("WHERE", "keyboards.keyboard_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.updateData(newvalues, values);
    }
    public boolean deleteKeyboardById(int id) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "keyboards");
        params.put("WHERE", "keyboards.keyboard_id = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(id);
        return helper.deleteData(values);
    }
}
