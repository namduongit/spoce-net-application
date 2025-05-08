package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Cpus;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CpuDAL {

    public ArrayList<Cpus> getCpuList() {
        ArrayList<Cpus> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("cpus");
            while (rs.next()) {
                arr.add(new Cpus(
                        rs.getInt("cpu_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getFloat("clock_speed"),
                        rs.getInt("cores"),
                        rs.getInt("threads"),
                        rs.getBoolean("integrated_gpu"),
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

    public Cpus getCpuById(int id) {
        ArrayList<Cpus> arr = this.getCpuList();

        for (Cpus x : arr) {
            if (x.getCpuId() == id) {
                return x;
            }
        }

        return null;
    }

    public boolean updateCpuById(int id, HashMap<String, Object> updates) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "cpus");
        params.put("WHERE", "cpus.cpu_id = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> conditionValues = new ArrayList<>();
        conditionValues.add(id);
        boolean result = helper.updateData(updates, conditionValues);
        helper.closeConnect();
        return result;
    }

    public boolean deleteCpuById(int id) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE","cpus");
        params.put("WHERE","cpus.cpu_id = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(id);
        boolean r = helper.deleteData(values);
        helper.closeConnect();
        return r;
    }
    public boolean addCpu(Cpus cpu) {
        MySQLHelper helper = new MySQLHelper();
       
        ArrayList<Object> values = new ArrayList<>();
            // values.add(cpu.getCpuId());
            values.add(cpu.getBrand());
            values.add(cpu.getModel());
            values.add(cpu.getClockSpeed());
            values.add(cpu.getCores());
            values.add(cpu.getThreads());
            values.add(cpu.isIntegratedGpu());
            values.add(cpu.getPurchaseDate());
            values.add(cpu.getWarrantyExpiry());
            values.add(cpu.getStatus());

       HashMap<String, String> params = new HashMap<>();
       params.put("TABLE","cpus");
       params.put("FIELD","brand,model,clock_speed,cores,threads,integrated_gpu,purchase_date,warranty_expiry,status");
       helper.buildingQueryParam(params);

        boolean result = helper.insertData(values);
        helper.closeConnect();
        return result;
    }
}
