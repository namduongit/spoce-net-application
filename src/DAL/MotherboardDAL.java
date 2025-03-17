package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Motherboards;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MotherboardDAL {

    public ArrayList<Motherboards> getMotherboardList() {
        ArrayList<Motherboards> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("motherboards");
            while (rs.next()) {
                arr.add(new Motherboards(
                        rs.getInt("motherboard_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("socket"),
                        rs.getString("chipset"),
                        rs.getInt("ram_slots"),
                        rs.getInt("max_ram"),
                        rs.getInt("ram_speed"),
                        rs.getInt("storage_slots"),
                        rs.getInt("sata_ports"),
                        rs.getInt("m2_slots"),
                        rs.getInt("max_storage_capacity"),
                        rs.getString("status"),
                        rs.getInt("cpu_id"),
                        rs.getInt("psu_id"),
                        rs.getInt("gpu_id"),
                        rs.getDate("purchase_date"),
                        rs.getDate("warranty_expiry")
                ));
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return arr;
    }

    public Motherboards getMotherboardById(int id) {

        ArrayList<Motherboards> arr = this.getMotherboardList();
        for (Motherboards x : arr) {
            if (x.getMotherboardId() == id) {
                return x;
            }
        }

        return null;
    }

    public ArrayList<Motherboards> getMotherboardByBrand(String brand) {
        ArrayList<Motherboards> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "motherboards");
        params.put("WHERE", "motherboards.brand = ?");

        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(brand);

        try {
            ResultSet rs = helper.queryWithParam(values);
            while (rs.next()) {
                arr.add(new Motherboards(
                        rs.getInt("motherboard_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("socket"),
                        rs.getString("chipset"),
                        rs.getInt("ram_slots"),
                        rs.getInt("max_ram"),
                        rs.getInt("ram_speed"),
                        rs.getInt("storage_slots"),
                        rs.getInt("sata_ports"),
                        rs.getInt("m2_slots"),
                        rs.getInt("max_storage_capacity"),
                        rs.getString("status"),
                        rs.getInt("cpu_id"),
                        rs.getInt("psu_id"),
                        rs.getInt("gpu_id"),
                        rs.getDate("purchase_date"),
                        rs.getDate("warranty_expiry")
                ));
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return arr;
    }
}
