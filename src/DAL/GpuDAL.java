package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Gpus;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GpuDAL {

    public ArrayList<Gpus> getGpuList() {
        ArrayList<Gpus> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("gpus");
            while (rs.next()) {
                arr.add(new Gpus(
                        rs.getInt("gpu_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("vram"),
                        rs.getDate("purchase_date"),
                        rs.getDate("warranty_expiry"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return arr;
    }

    public Gpus getGpuById(int id) {
        ArrayList<Gpus> arr = this.getGpuList();

        for (Gpus x : arr) {
            if (x.getGpuId() == id) {
                return x;
            }
        }

        return null;
    }

    public boolean deleteGpuById(int id) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String,String> params = new HashMap<>();
        params.put("TABLE", "gpus");
        params.put("WHERE", "gpus.gpu_id= ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(id);
        return helper.deleteData(values);
    }
}
