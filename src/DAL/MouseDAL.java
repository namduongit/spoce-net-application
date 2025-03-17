package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Mouse;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
}
