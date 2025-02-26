package Pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transactions {
    private int transactionId;
    private int playerId;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDateTime createdAt;

    public Transactions() {
    }

    public Transactions(int transactionId, int playerId, BigDecimal amount, String paymentMethod, LocalDateTime createdAt) {
        this.transactionId = transactionId;
        this.playerId = playerId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

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
