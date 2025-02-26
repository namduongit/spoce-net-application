package Pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FoodBills {
    private int billId;
    private Integer playerId; // Có thể null
    private int staffId;
    private BigDecimal totalPrice;
    private String paymentMethod; // Tiền mặt, Chuyển khoản
    private String status; // Chưa xử lý, Đã xử lý, Đã hủy
    private LocalDateTime createdAt;

    public FoodBills(int billId, Integer playerId, int staffId, BigDecimal totalPrice, String paymentMethod, String status, LocalDateTime createdAt) {
        this.billId = billId;
        this.playerId = playerId;
        this.staffId = staffId;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "FoodBill{" +
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
