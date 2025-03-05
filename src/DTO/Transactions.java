package DTO;

import java.math.BigDecimal;
import java.sql.Date;

public class Transactions {
    private int transactionId;
    private int playerId;
    private BigDecimal amount;
    private String paymentMethod;
    private Date createdAt;

    // Constructor không tham số
    public Transactions() {
    }

    // Constructor có tham số
    public Transactions(int transactionId, int playerId, BigDecimal amount, String paymentMethod, Date createdAt) {
        this.transactionId = transactionId;
        this.playerId = playerId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
    }

    // Getter và Setter
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Phương thức toString để trả về thông tin đối tượng
    @Override
    public String toString() {
        return "Transactions{" +
                "transactionId=" + transactionId +
                ", playerId=" + playerId +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
