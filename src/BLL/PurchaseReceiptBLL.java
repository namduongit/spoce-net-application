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
}
