package DAL;

import DAL.SQLHelper.MySQLHelper;

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

}
