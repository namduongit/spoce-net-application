package DTO;

import java.sql.Date;

public class ComputerSessions {
    private int sessionId;
    private Integer playerId; // Có thể null nếu là khách vãng lai
    private int computerId;
    private Date startTime;
    private Date endTime;
    private int duration; // Tính toán tự động từ start_time và end_time
    private double totalCost;
    private boolean isGuest;
    private int staffId;

    // Constructor không tham số
    public ComputerSessions() {
    }

    // Constructor có tham số
    public ComputerSessions(int sessionId, Integer playerId, int computerId, Date startTime, Date endTime, int duration, double totalCost, boolean isGuest, int staffId) {
        this.sessionId = sessionId;
        this.playerId = playerId;
        this.computerId = computerId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.totalCost = totalCost;
        this.isGuest = isGuest;
        this.staffId = staffId;
    }

    // Getter và Setter
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public int getComputerId() {
        return computerId;
    }

    public void setComputerId(int computerId) {
        this.computerId = computerId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public int getStaffId() {
        return this.staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    // Phương thức toString để hiển thị thông tin
    @Override
    public String toString() {
        return "ComputerSession{" +
                "sessionId=" + sessionId +
                ", playerId=" + playerId +
                ", computerId=" + computerId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", duration=" + duration +
                ", totalCost=" + totalCost +
                ", isGuest=" + isGuest +
                ", staffId=" + staffId +
                '}';
    }
}
