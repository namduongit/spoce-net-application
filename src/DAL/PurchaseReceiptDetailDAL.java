package DAL;

import DAL.SQLHelper.MySQLHelper;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PurchaseReceiptDetailDAL {
    public boolean insertDataPurchaseReceipt(int inboundId, int foodId, int quantity, int price) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "purchase_receipt_detail");

        params.put("FIELD", "receipt_id, food_id, quantity, price");
        helper.buildingQueryParam(params);

        ArrayList<Object> insertData = new ArrayList<>();
        insertData.add(inboundId);
        insertData.add(foodId);
        insertData.add(quantity);
        insertData.add(price);

        return helper.insertData(insertData);
    }

    public ArrayList<ArrayList<Object>> getDetailById(int id) {
        MySQLHelper helper = new MySQLHelper();
        ArrayList<ArrayList<Object>> result = new ArrayList<>();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "purchase_receipt_detail prd");
        params.put("WHERE", "receipt_id = ?");
        params.put("JOIN", "foods ON prd.food_id = foods.food_id");
        params.put("SELECT", "prd.food_id, foods.name, prd.quantity, prd.price");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        try {
            ResultSet rs = helper.queryWithParam(values);
            while (rs.next()) {
                ArrayList<Object> row = new ArrayList<>();
                row.add(rs.getInt("food_id"));
                row.add(rs.getString("name"));
                row.add(rs.getInt("quantity"));
                row.add(rs.getInt("price"));

                result.add(row);
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Exception was thrown by getDetailById of PurchaseReceiptDetailDAL",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return result;
    }

    public boolean deleteAllRowsOfSpecificId(int id) {
        MySQLHelper helper = new MySQLHelper();
        ArrayList<ArrayList<Object>> result = new ArrayList<>();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "purchase_receipt_detail prd");
        params.put("WHERE", "receipt_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.deleteData(values);
    }
}
