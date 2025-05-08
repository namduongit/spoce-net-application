package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Foods;
import DTO.PurchaseReceipt;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PurchaseReceiptDAL {
    public ArrayList<PurchaseReceipt> getPurchaseReceiptList() {
        ArrayList<PurchaseReceipt> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        try {
            ResultSet rs = helper.selectAllFromTable("purchase_receipts");
            while (rs.next()) {
                list.add(new PurchaseReceipt(
                        rs.getInt("receipt_id"),
                        rs.getTimestamp("create_at"),
                        rs.getTimestamp("update_at"),
                        rs.getInt("staff_id"),
                        rs.getString("status"),
                        rs.getString("note"),
                        rs.getInt("total")
                ));
            }
            rs.close();
            helper.closeConnect();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    public PurchaseReceipt getPurchaseReceiptById(int purchaseReceiptId) {
        ArrayList<PurchaseReceipt> list = this.getPurchaseReceiptList();
        for (PurchaseReceipt purchaseReceipt : list) {
            if (purchaseReceipt.getReceiptId() == purchaseReceiptId) {
                return purchaseReceipt;
            }
        }
        return null;
    }

    public int createLastestPurchaseReceipt(int staffId, String note) {
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "purchase_receipts");
        params.put("FIELD", "staff_id, note");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(staffId);
        values.add(note);

        return helper.insertDataLastest(values);
    }

    public boolean updateNote(int id, String note) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "purchase_receipts");
        params.put("WHERE", "receipt_id = ?");
        helper.buildingQueryParam(params);

        HashMap<String, Object> changedValues = new HashMap<>();
        changedValues.put("note", note);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.updateData(changedValues, values);
    }

    public boolean confirmInbound(int id) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "purchase_receipts");
        params.put("WHERE", "receipt_id = ?");
        helper.buildingQueryParam(params);

        HashMap<String, Object> changedValues = new HashMap<>();
        changedValues.put("status", "Đã xác nhận");

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.updateData(changedValues, values);
    }

    public boolean cancelInbound(int id) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "purchase_receipts");
        params.put("WHERE", "receipt_id = ?");
        helper.buildingQueryParam(params);

        HashMap<String, Object> changedValues = new HashMap<>();
        changedValues.put("status", "Đã hủy");

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.updateData(changedValues, values);
    }
}
