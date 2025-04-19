package DAL;


import java.util.ArrayList;
import java.util.HashMap;

import DAL.SQLHelper.MySQLHelper;

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

    public static void main(String[] args) {
        boolean insert = new FoodOrderDAL().insertDataBill(4, 15, 7);
        System.out.println(insert ? "Thành công" : "Thất bại");
    }

}
