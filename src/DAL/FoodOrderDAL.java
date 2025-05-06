package DAL;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DAL.SQLHelper.MySQLHelper;

import javax.swing.*;

public class FoodOrderDAL {
    public boolean insertDataBill(int billId, int foodId, int quantity) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "food_orders");

        params.put("FIELD", "bill_id, food_id, quantity");
        helper.buildingQueryParam(params);

        ArrayList<Object> insertData = new ArrayList<>();
        insertData.add(billId);
        insertData.add(foodId);
        insertData.add(quantity);

        return helper.insertData(insertData);
    }

    public boolean updateQuantityBill(int billId, int foodId, int quantity) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "food_orders");
        params.put("SET", "quantity = ?");
        params.put("WHERE", "bill_id = ? AND food_id = ?");

        helper.buildingQueryParam(params);

        ArrayList<Object> valueCondition = new ArrayList<>();

        valueCondition.add(quantity);
        valueCondition.add(billId);
        valueCondition.add(foodId);
        return helper.updateData(valueCondition);
    }

    public ArrayList<ArrayList<Integer>> getOrderDetailFromBillId(int billId) {
        MySQLHelper helper = new MySQLHelper();
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        HashMap<String, String> params = new HashMap<>();
        params.put("SELECT", "food_id, quantity");
        params.put("TABLE", "food_orders");
        params.put("WHERE", "bill_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(billId);

        try {
            ResultSet rs = helper.queryWithParam(values);
            while (rs.next()) {
                ArrayList<Integer> orderDetail = new ArrayList<>();
                orderDetail.add(rs.getInt("food_id"));
                orderDetail.add(rs.getInt("quantity"));

                result.add(orderDetail);
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error was thrown by getOrderDetailFromBillId function Of FoodOrderDAL",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return result;
    }

    public static void main(String[] args) {
        boolean insert = new FoodOrderDAL().insertDataBill(4, 15, 7);
        System.out.println(insert ? "Thành công" : "Thất bại");
    }

}
