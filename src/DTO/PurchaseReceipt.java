package DTO;

import java.sql.Timestamp;

public class PurchaseReceipt {
    private int receiptId;
    private Timestamp createAt;
    private Timestamp updateAt;
    private int staffId;
    private String status;
    private String note;
    private int total;

    public PurchaseReceipt() {
    }

    public PurchaseReceipt(int receiptId, Timestamp createAt, Timestamp updateAt, int staffId, String status, String note, int total) {
        this.receiptId = receiptId;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.staffId = staffId;
        this.status = status;
        this.note = note;
        this.total = total;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PurchaseReceipt{" +
                "receiptId=" + receiptId +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", staffId=" + staffId +
                ", status='" + status + '\'' +
                ", note='" + note + '\'' +
                ", total=" + total +
                '}';
    }
}
