package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.PurchaseReceipt;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


    // Dưới đâu dùng để thống kê
    public ArrayList<Object[]> getInboundRevenueByCategory(LocalDateTime start, LocalDateTime end, String category) {
        ArrayList<Object[]> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "purchase_receipts pr");
        params.put("JOIN", "purchase_receipt_detail rrd on pr.receipt_id = rrd.receipt_id " +
                               "join foods fs on rrd.food_id = fs.food_id " +
                               "join categories cates on fs.category_id = cates.category_id");
        params.put("SELECT", "pr.receipt_id, pr.create_at, pr.update_at, pr.staff_id, pr.status, total, cates.category_id, cates.name as cate_name, fs.food_id, fs.name as food_name, fs.price, rrd.quantity");
        params.put("WHERE", "pr.create_at BETWEEN ? AND ? AND pr.status = 'Đã xác nhận'");

        if (!category.equals("Tất cả")) {
            params.put("WHERE", params.get("WHERE") + " AND cates.name = ?");
        }

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
                    resultSet.getInt("receipt_id"),
                    resultSet.getTimestamp("create_at"),
                    resultSet.getTimestamp("update_at"),
                    resultSet.getInt("staff_id"),
                    resultSet.getString("status"),
                    resultSet.getInt("total"),
                    resultSet.getInt("category_id"),
                    resultSet.getString("cate_name"),
                    resultSet.getInt("food_id"),
                    resultSet.getString("food_name"),
                    resultSet.getInt("price"),
                    resultSet.getInt("quantity")
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

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime start = LocalDateTime.parse("2000-01-01" + " 00:00:00", formatter); 
        LocalDateTime end = LocalDateTime.parse("2025-12-30" + " 23:59:59", formatter);

        ArrayList<Object[]> list = new PurchaseReceiptDAL().getInboundRevenueByCategory(start, end, "Tất cả");

        for (Object[] objects : list) {
            for (int i = 0; i < objects.length; i++) {
                System.out.print(objects[i] +" ");
            }
            System.out.println();
        }

    }
}
