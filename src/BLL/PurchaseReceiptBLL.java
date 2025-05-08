package BLL;

import DAL.PurchaseReceiptDAL;
import DTO.PurchaseReceipt;

import java.util.ArrayList;

public class PurchaseReceiptBLL {
    private PurchaseReceiptDAL purchaseReceiptDAL;

    public PurchaseReceiptBLL() { this.purchaseReceiptDAL = new PurchaseReceiptDAL(); }

    public ArrayList<PurchaseReceipt> getPurchaseReceiptList() {
        return this.purchaseReceiptDAL.getPurchaseReceiptList();
    }

    public PurchaseReceipt getPurchaseReceiptById(int purchaseReceiptId) {
        return this.purchaseReceiptDAL.getPurchaseReceiptById(purchaseReceiptId);
    }

    public int createLastestPurchaseReceipt(int staffId, String note) {
        return this.purchaseReceiptDAL.createLastestPurchaseReceipt(staffId, note);
    }

    public boolean updateNote(int id, String note) {
        return this.purchaseReceiptDAL.updateNote(id, note);
    }

    public boolean confirmInbound(int id) {
        return this.purchaseReceiptDAL.confirmInbound(id);
    }

    public boolean cancelInbound(int id) {
        return this.purchaseReceiptDAL.cancelInbound(id);
    }
}
