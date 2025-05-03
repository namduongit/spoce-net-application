package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Rams;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RamDAL {

    public ArrayList<Rams> getRamList() {
        ArrayList<Rams> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("rams");
            while (rs.next()) {
                arr.add(new Rams(
                        rs.getInt("ram_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("capacity"),
                        rs.getInt("speed"),
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

    public Rams getRamById(int id) {
        ArrayList<Rams> arr = this.getRamList();

        for (Rams x : arr) {
            if (x.getRamId() == id) {
                return x;
            }
        }

        return null;
    }

    public ArrayList<Rams> getRamsByStatus(String status) {
        ArrayList<Rams> arr = new ArrayList<>();

        for (Rams x : this.getRamList()) {
            if (x.getStatus().equals(status)) {
                arr.add(x);
            }
        }

        return arr;
    }

    public boolean updateRamById(int id, HashMap<String, Object> newvalues) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "rams");
        params.put("WHERE", "rams.ram_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.updateData(newvalues, values);
    }

    public boolean deleteRamById(int id) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "rams");
        params.put("WHERE", "rams.ram_id = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(id);
        boolean res = helper.deleteData(values);
        helper.closeConnect();
        return res;
    }

    public boolean addRam(Rams ram) {
        MySQLHelper helper = new MySQLHelper();

        ArrayList<Object> values = new ArrayList<>();
        values.add(ram.getBrand());
        values.add(ram.getModel());
        values.add(ram.getCapacity());
        values.add(ram.getSpeed());
        values.add(ram.getPurchaseDate());
        values.add(ram.getWarrantyExpiry());
        values.add(ram.getStatus());
        values.add(ram.getPrice());

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "rams");
        params.put("FIELD", "brand,model,capacity,speed,purchase_date,warranty_expiry,status,price");
        helper.buildingQueryParam(params);

        boolean success = helper.insertData(values);
        helper.closeConnect();
        return success;
    }
}