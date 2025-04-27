package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import DAL.SQLHelper.MySQLHelper;

public class FoodRevenueDAL {
    // Lấy dữ liệu doanh thu món ăn
    public ArrayList<Object[]> getFoodRevenue(LocalDateTime start, LocalDateTime end) {
        ArrayList<Object[]> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "food_orders fo");
        params.put("JOIN", "foods f ON fo.food_id = f.food_id JOIN food_bills fb ON fo.bill_id = fb.bill_id");
        params.put("SELECT", "f.name, SUM(fo.quantity * f.price) as total");
        params.put("WHERE", "fb.created_at BETWEEN ? AND ?");
        params.put("GROUP BY", "f.food_id, f.name");

        helper.buildingQueryParam(params);
        System.out.println("Query: " + params); // Gỡ lỗi
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(java.sql.Timestamp.valueOf(start));
        values.add(java.sql.Timestamp.valueOf(end));

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                Object[] row = new Object[]{
                    resultSet.getString("name"),
                    resultSet.getDouble("total")
                };
                list.add(row);
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }
}
