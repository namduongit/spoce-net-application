package DAL;

import java.util.ArrayList;
import java.util.HashMap;

import DAL.SQLHelper.MySQLHelper;

public class FoodBillDAL {
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

    public boolean updateTotalPriceByBillId(int billId) {
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
    
        return helper.executeUpdateWithParams(sql, new Object[]{billId});
    }

    public boolean updateTotalPrice(int billId, int totalPrice) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();

        params.put("TABLE", "food_bills");
        params.put("SET", "total_price = ?");
        params.put("WHERE", "bill_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> valueCondition = new ArrayList<>();
        valueCondition.add(totalPrice);
        valueCondition.add(billId);

        return helper.updateData(valueCondition);
    }

    
}
