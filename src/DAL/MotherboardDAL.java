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
                        rs.getDate("warranty_expiry"),
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

    public ArrayList<Motherboards> getMotherboardsByStatus(String status) {
        ArrayList<Motherboards> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "motherboards");
        params.put("WHERE", "motherboards.status = ?");

        ArrayList<Object> values = new ArrayList<>();
        values.add(status);

        helper.buildingQueryParam(params);

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
                        rs.getDate("warranty_expiry"),
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

    public boolean updateMotherboardById(int id, HashMap<String, Object> newvalues) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "motherboards");
        params.put("WHERE", "motherboards.motherboard_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.updateData(newvalues, values);
    }
    public boolean deleteMotherboardById(int id) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "motherboards");
        params.put("WHERE", "motherboards.motherboard_id = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(id);
        boolean result = helper.deleteData(values);
        helper.closeConnect();
        return result;
    }
    public boolean addMotherboard(Motherboards motherboard) {
        MySQLHelper helper = new MySQLHelper();
        if(this.getMotherboardById(motherboard.getMotherboardId()) != null) {
            JOptionPane.showMessageDialog(null, "ID " + motherboard.getMotherboardId() + " đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        ArrayList<Object> values = new ArrayList<>();
            values.add(motherboard.getMotherboardId());
            values.add(motherboard.getBrand());
            values.add(motherboard.getModel());
            values.add(motherboard.getSocket());
            values.add(motherboard.getChipset());
            values.add(motherboard.getRamSlots());
            values.add(motherboard.getMaxRam());
            values.add(motherboard.getRamSpeed());
            values.add(motherboard.getStorageSlots());
            values.add(motherboard.getSataPorts());
            values.add(motherboard.getM2Slots());
            values.add(motherboard.getMaxStorageCapacity());
            values.add(motherboard.getStatus());
            values.add(motherboard.getCpuId());
            values.add(motherboard.getPsuId());
            values.add(motherboard.getGpuId());
            values.add(motherboard.getPurchaseDate());
            values.add(motherboard.getWarrantyExpiry());
            values.add(motherboard.getPrice());

         HashMap<String, String> params = new HashMap<>();
         params.put("TABLE", "motherboards");
         params.put("FIELD", "motherboard_id,brand,model,socket,chipset,ram_slots,max_ram,ram_speed,storage_slots,sata_ports,m2_slots,max_storage_capacity,status,cpu_id,psu_id,gpu_id,purchase_date,warranty_expiry,price");
         helper.buildingQueryParam(params);

        boolean result = helper.insertData(values);
        helper.closeConnect();
        return result;
    }
}
