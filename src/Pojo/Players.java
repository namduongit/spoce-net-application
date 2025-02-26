package Pojo;

import java.math.BigDecimal;

public class Players {
    private int playerId;
    private int accountId;
    private BigDecimal balance;

    public Players() {
        this.balance = BigDecimal.ZERO;
    }

    public Players(int playerId, int accountId, BigDecimal balance) {
        this.playerId = playerId;
        this.accountId = accountId;
        this.balance = balance;
    }

    // Getters và Setters
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Players{" +
                "playerId=" + playerId +
                ", accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }
}
