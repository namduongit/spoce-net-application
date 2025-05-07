package BLL;

import DAL.PurchaseReceiptDetailDAL;

public class PurchaseReceiptDetailBLL {
    private PurchaseReceiptDetailDAL purchaseReceiptDetailDAL;
    public PurchaseReceiptDetailBLL() {
        this.purchaseReceiptDetailDAL = new PurchaseReceiptDetailDAL();
    }

    public boolean insertDataBill(int inboundId, int foodId, int quantity, int price) {
        return this.purchaseReceiptDetailDAL.insertDataPurchaseReceipt(inboundId, foodId, quantity, price);
    }
}
