package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Headphones;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
}
