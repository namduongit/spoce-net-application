package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Mouse;
import DTO.Roms;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<Roms> getRomsByStatus(String status) {
        ArrayList<Roms> arr = new ArrayList<>();

        for (Roms x : this.getRomList()) {
            if (x.getStatus().equals(status)) {
                arr.add(x);
            }
        }

        return arr;
    }

    public boolean updateRomById(int id, HashMap<String, Object> newvalues) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "roms");
        params.put("WHERE", "roms.rom_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.updateData(newvalues, values);
    }
}
