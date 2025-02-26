package Pojo;

import java.time.LocalDateTime;

public class ComputerSessions {
    private int sessionId;
    private Integer playerId;
    private int computerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration;
    private double totalCost;
    private boolean isGuest;

    public ComputerSessions(int sessionId, Integer playerId, int computerId, LocalDateTime startTime,
                           LocalDateTime endTime, Integer duration, double totalCost, boolean isGuest) {
        this.sessionId = sessionId;
        this.playerId = playerId;
        this.computerId = computerId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.totalCost = totalCost;
        this.isGuest = isGuest;
    }

    public ComputerSessions() {
    }

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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
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
                '}';
    }
}
