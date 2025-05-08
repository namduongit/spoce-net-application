package BLL;

import DAL.PurchaseReceiptDetailDAL;

import java.util.ArrayList;

public class PurchaseReceiptDetailBLL {
    private PurchaseReceiptDetailDAL purchaseReceiptDetailDAL;
    public PurchaseReceiptDetailBLL() {
        this.purchaseReceiptDetailDAL = new PurchaseReceiptDetailDAL();
    }

    public boolean insertDataBill(int inboundId, int foodId, int quantity, int price) {
        return this.purchaseReceiptDetailDAL.insertDataPurchaseReceipt(inboundId, foodId, quantity, price);
    }

    public ArrayList<ArrayList<Object>> getDetailById(int id) {
        return this.purchaseReceiptDetailDAL.getDetailById(id);
    }

    public boolean deleteAllRowsOfSpecificId(int id) {
        return this.purchaseReceiptDetailDAL.deleteAllRowsOfSpecificId(id);
    }
}
