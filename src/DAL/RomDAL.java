package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Roms;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RomDAL {

    public ArrayList<Roms> getRomList() {
        ArrayList<Roms> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("roms");
            while (rs.next()) {
                arr.add(new Roms(
                        rs.getInt("rom_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("type"),
                        rs.getInt("capacity"),
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

    public Roms getRomById(int id) {
        ArrayList<Roms> arr = this.getRomList();

        for (Roms x : arr) {
            if (x.getRomId() == id) {
                return x;
            }
        }

        return null;
    }
}
