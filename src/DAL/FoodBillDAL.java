package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import DAL.SQLHelper.MySQLHelper;
import DTO.FoodBills;

public class FoodBillDAL {

    public ArrayList<FoodBills> getFoodBillList() {
        ArrayList<FoodBills> list = new ArrayList<>();

        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("food_bills");
            while (rs.next()) {

                list.add(new FoodBills(
                    rs.getInt("bill_id"),
                    (Integer)rs.getObject("player_id"),
                    rs.getInt("staff_id"),
                    rs.getInt("total_price"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at")
                ));
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Lỗi truy vấn dữ liệu FoodBillDAL lấy tất cả Hóa đơn",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        return list;
    }

    public ArrayList<FoodBills> getFoodBillByCategoryId(int categoryId) {
        ArrayList<FoodBills> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();

        params.put("TABLE", "food_bills fb");
        params.put("SELECT", "DISTINCT fb.bill_id, fb.player_id, fb.staff_id, fb.total_price, fb.status, fb.created_at");
        params.put("JOIN", "food_orders fo ON fb.bill_id = fo.bill_id\n" +
                               "JOIN foods f ON fo.food_id = f.food_id");
        params.put("WHERE", "f.category_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(categoryId);

        try {
            ResultSet rs = helper.queryWithParam(values);
            while (rs.next()) {

                list.add(new FoodBills(
                    rs.getInt("bill_id"),
                    (Integer)rs.getObject("player_id"),
                    rs.getInt("staff_id"),
                    rs.getInt("total_price"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at")
                ));
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Lỗi truy vấn dữ liệu FoodBillDAL lấy hóa đơn theo danh mục",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        return list;
    }

    public ArrayList<Object[]> getDetailFoodBillById(int foodBillId) {
        ArrayList<Object[]> list = new ArrayList<>();

        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();

        params.put("TABLE", "food_bills");
        params.put("JOIN", "food_orders on food_bills.bill_id = food_orders.bill_id\n" +
                               "join foods on food_orders.food_id = foods.food_id");
        params.put("WHERE", "food_bills.bill_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(foodBillId);

        try {
            ResultSet rs = helper.queryWithParam(values);
            while (rs.next()) {
                list.add(new Object[] {
                    rs.getInt("bill_id"),
                    rs.getInt("player_id"),
                    rs.getInt("staff_id"),
                    rs.getInt("total_price"),
                    rs.getString("status"),
                    rs.getInt("food_id"),
                    rs.getInt("quantity"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getTimestamp("created_at")
                });
            }
            rs.close();
            helper.closeConnect();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Lỗi truy vấn dữ liệu FoodBillDAL lấy hóa đơn theo mã hóa đơn",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return list;
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public int createLastestBill(int staffId) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "food_bills");
        params.put("FIELD", "staff_id");

        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(staffId);

        return helper.insertDataLastest(values);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public boolean updateTotalPriceByBillId(int foodBillId) {
        MySQLHelper helper = new MySQLHelper();
    
        String sql = "UPDATE food_bills fb " +
                     "JOIN ( " +
                     "   SELECT fo.bill_id, SUM(fo.quantity * f.price) AS total " +
                     "   FROM food_orders fo " +
                     "   JOIN foods f ON fo.food_id = f.food_id " +
                     "   WHERE fo.bill_id = ? " +
                     "   GROUP BY fo.bill_id " +
                     ") AS total_data ON fb.bill_id = total_data.bill_id " +
                     "SET fb.total_price = total_data.total";
    
        return helper.executeUpdateWithParams(sql, new Object[]{foodBillId});
    }

    public boolean updateTotalPrice(int foodBillId, int totalPrice) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();

        params.put("TABLE", "food_bills");
        params.put("SET", "total_price = ?");
        params.put("WHERE", "bill_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> valueCondition = new ArrayList<>();
        valueCondition.add(totalPrice);
        valueCondition.add(foodBillId);

        return helper.updateData(valueCondition);
    }

    public boolean updateCofirmFoodBill(int foodBillId) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "food_bills");
        params.put("SET", "status = ?");
        params.put("WHERE", "bill_id = ?");
        helper.buildingQueryParam(params);


        ArrayList<Object> valueCondition = new ArrayList<>();
        valueCondition.add("Đã xử lý");
        valueCondition.add(foodBillId);

        return helper.updateData(valueCondition);
    }

    public boolean updateCancelFoodBill(int foodBillId) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "food_bills");
        params.put("SET", "status = ?");
        params.put("WHERE", "bill_id = ?");
        helper.buildingQueryParam(params);


        ArrayList<Object> valueCondition = new ArrayList<>();
        valueCondition.add("Đã hủy");
        valueCondition.add(foodBillId);

        return helper.updateData(valueCondition);
    }



    
}
