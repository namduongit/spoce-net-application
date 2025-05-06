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
    public ArrayList<FoodRevenue> getFoodRevenue(LocalDateTime start, LocalDateTime end) {
        ArrayList<FoodRevenue> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "food_bills fb");
        params.put("JOIN", "food_orders fo ON fo.bill_id = fb.bill_id JOIN foods f ON f.food_id = fo.food_id");
        params.put("SELECT", "f.name, SUM(fo.quantity * f.price) as total");
        params.put("WHERE", "fb.created_at BETWEEN ? AND ?");
        params.put("GROUP BY", "f.food_id, f.name");

        helper.buildingQueryParam(params);

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
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    public ArrayList<String> getAllCategory() {
        ArrayList<String> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "categories");
        params.put("SELECT", "name");

        helper.buildingQueryParam(params);

        ResultSet resultSet = helper.queryWithParam(new ArrayList<>());
        try {
            while (resultSet.next()) {
                list.add(resultSet.getString("name"));
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    public ArrayList<FoodRevenue> getFoodRevenueByRoom(LocalDateTime start, LocalDateTime end, String status) {
        ArrayList<FoodRevenue> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "food_bills fb");
        params.put("JOIN", "food_orders fo ON fo.bill_id = fb.bill_id JOIN foods f ON f.food_id = fo.food_id");
        params.put("SELECT", "f.name, SUM(fo.quantity * f.price) as total");
        params.put("WHERE", "fb.created_at BETWEEN ? AND ? AND fb.status = ?");
        params.put("GROUP BY", "f.food_id, f.name");

        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(java.sql.Timestamp.valueOf(start));
        values.add(java.sql.Timestamp.valueOf(end));
        values.add(status);

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                FoodRevenue revenue = new FoodRevenue();
                revenue.setFood(resultSet.getString("name"));
                revenue.setTotalRevenue(resultSet.getDouble("total"));
                list.add(revenue);
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    public FoodRevenue getPendingBillsInfo(LocalDateTime start, LocalDateTime end) {
        FoodRevenue pendingInfo = new FoodRevenue();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "food_bills fb");
        params.put("JOIN", "food_orders fo ON fo.bill_id = fb.bill_id JOIN foods f ON f.food_id = fo.food_id");
        params.put("SELECT", "SUM(fo.quantity * f.price) as total");
        params.put("WHERE", "fb.created_at BETWEEN ? AND ? AND fb.status != 'Đã hủy' AND fb.status != 'Chưa xử lý'"); 

        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(java.sql.Timestamp.valueOf(start));
        values.add(java.sql.Timestamp.valueOf(end));

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            if (resultSet.next()) {
                pendingInfo.setTotalRevenue(resultSet.getDouble("total"));
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return pendingInfo;
    }

    public ArrayList<Object[]> getFoodBillDetails(LocalDateTime start, LocalDateTime end, String status) {
        ArrayList<Object[]> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "food_bills fb");
        params.put("JOIN", "food_orders fo ON fo.bill_id = fb.bill_id JOIN foods f ON f.food_id = fo.food_id");
        params.put("SELECT", "fb.bill_id, fb.created_at, f.name, fo.quantity, f.price, (fo.quantity * f.price) as total");
        params.put("WHERE", "fb.created_at BETWEEN ? AND ? AND fb.status = ?");
        params.put("ORDER BY", "fb.bill_id");

        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(java.sql.Timestamp.valueOf(start));
        values.add(java.sql.Timestamp.valueOf(end));
        values.add(status);

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                Object[] row = new Object[]{
                    resultSet.getInt("bill_id"),
                    resultSet.getTimestamp("created_at"),
                    resultSet.getString("name"),
                    resultSet.getInt("quantity"),
                    resultSet.getInt("price"),
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

    public ArrayList<Object[]> getFoodRevenueByCategory(LocalDateTime start, LocalDateTime end, String category) {
        ArrayList<Object[]> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "food_bills fb");
        params.put("JOIN", "food_orders fo ON fo.bill_id = fb.bill_id JOIN foods f ON f.food_id = fo.food_id JOIN categories c ON f.category_id = c.category_id");
        params.put("SELECT", "c.name as category_name, f.name as food_name, f.price, SUM(fo.quantity) as quantity, SUM(fo.quantity * f.price) as total");
        params.put("WHERE", "fb.created_at BETWEEN ? AND ? AND fb.status = 'Đã xử lý'");
        if (!category.equals("Tất cả")) {
            params.put("WHERE", params.get("WHERE") + " AND c.name = ?");
        }
        params.put("GROUP BY", "c.name, f.name, f.price");
        params.put("ORDER BY", "c.name, f.name");

        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(java.sql.Timestamp.valueOf(start));
        values.add(java.sql.Timestamp.valueOf(end));
        if (!category.equals("Tất cả")) {
            values.add(category);
        }

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                Object[] row = new Object[]{
                    resultSet.getString("category_name"),
                    resultSet.getString("food_name"),
                    resultSet.getInt("price"),
                    resultSet.getInt("quantity"),
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

    // Thêm phương thức đếm số lượng hóa đơn theo status
    public HashMap<String, Integer> getBillStatusCounts(LocalDateTime start, LocalDateTime end) {
        HashMap<String, Integer> statusCounts = new HashMap<>();
        statusCounts.put("Đã xử lý", 0);
        statusCounts.put("Chưa xử lý", 0);
        statusCounts.put("Đã hủy", 0);

        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "food_bills fb");
        params.put("SELECT", "fb.status, COUNT(*) as count");
        params.put("WHERE", "fb.created_at BETWEEN ? AND ?");
        params.put("GROUP BY", "fb.status");

        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(java.sql.Timestamp.valueOf(start));
        values.add(java.sql.Timestamp.valueOf(end));

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                String status = resultSet.getString("status");
                int count = resultSet.getInt("count");
                statusCounts.put(status, count);
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return statusCounts;
    }
}