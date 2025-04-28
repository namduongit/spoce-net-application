package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import DAL.SQLHelper.MySQLHelper;
import DTO.FoodRevenue;

public class FoodRevenueDAL {
    // Lấy dữ liệu doanh thu món ăn
    public ArrayList<FoodRevenue> getFoodRevenue(LocalDateTime start, LocalDateTime end) {
        ArrayList<FoodRevenue> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "food_bills fb");
        params.put("JOIN", "food_orders fo ON fo.bill_id = fb.bill_id JOIN foods f ON f.food_id = fo.food_id");
        params.put("SELECT", "f.name, SUM(fo.quantity * f.price) as total");
        params.put("WHERE", "fb.created_at BETWEEN ? AND ?");
        params.put("GROUP BY", "fo.food_id, f.name");

        helper.buildingQueryParam(params);
        // System.out.println("Query Food : " + params); // Gỡ lỗi

        ArrayList<Object> values = new ArrayList<>();
        values.add(java.sql.Timestamp.valueOf(start));
        values.add(java.sql.Timestamp.valueOf(end));

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                FoodRevenue revenue = new FoodRevenue();
                revenue.setFood(resultSet.getString("name"));
                revenue.setTotalRevenue(resultSet.getDouble("total"));
                list.add(revenue);
                // System.out.println("Row: " + revenue.getFood() + ", " + revenue.getTotalRevenue()); // Gỡ lỗi
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }
}