package DTO;


public class Players {
    private int playerId;
    private int accountId;
    private int balance;

    // Constructor không tham số
    public Players() {
    }

    // Constructor có tham số
    public Players(int playerId, int accountId, int balance) {
        this.playerId = playerId;
        this.accountId = accountId;
        this.balance = balance;
    }

    // Getter và Setter
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    // Phương thức toString để trả về thông tin đối tượng
    @Override
    public String toString() {
        return "Players{" +
                "playerId=" + playerId +
                ", accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }
}
