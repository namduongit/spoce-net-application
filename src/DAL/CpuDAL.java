package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Cpus;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
}
