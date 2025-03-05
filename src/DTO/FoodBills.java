package DTO;

import java.sql.Timestamp;

public class FoodBills {
    private int billId;
    private Integer playerId;
    private int staffId;
    private double totalPrice;
    private String paymentMethod;
    private String status;
    private Timestamp createdAt;

    // Constructor không tham số
    public FoodBills() {
    }

    // Constructor có tham số
    public FoodBills(int billId, Integer playerId, int staffId, double totalPrice, String paymentMethod, String status, Timestamp createdAt) {
        this.billId = billId;
        this.playerId = playerId;
        this.staffId = staffId;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getter và Setter
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Phương thức toString để hiển thị thông tin đối tượng
    @Override
    public String toString() {
        return "FoodBills{" +
                "billId=" + billId +
                ", playerId=" + playerId +
                ", staffId=" + staffId +
                ", totalPrice=" + totalPrice +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
