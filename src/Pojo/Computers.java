package Pojo;

import java.math.BigDecimal;

public class Computers {
    private int computerId;
    private String name;
    private BigDecimal pricePerHour;
    private int motherboardId;
    private Integer mouseId;
    private Integer keyboardId;
    private Integer monitorId;
    private Integer headphoneId;
    private Integer romId;
    private Integer roomId;
    private Integer playerId;
    private String status;

    public Computers() {}

    public Computers(int computerId, String name, BigDecimal pricePerHour, int motherboardId, Integer mouseId, Integer keyboardId, Integer monitorId, Integer headphoneId, Integer romId, Integer roomId, Integer playerId, String status) {
        this.computerId = computerId;
        this.name = name;
        this.pricePerHour = pricePerHour;
        this.motherboardId = motherboardId;
        this.mouseId = mouseId;
        this.keyboardId = keyboardId;
        this.monitorId = monitorId;
        this.headphoneId = headphoneId;
        this.romId = romId;
        this.roomId = roomId;
        this.playerId = playerId;
        this.status = status;
    }

    public int getComputerId() {
        return computerId;
    }

    public void setComputerId(int computerId) {
        this.computerId = computerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public int getMotherboardId() {
        return motherboardId;
    }

    public void setMotherboardId(int motherboardId) {
        this.motherboardId = motherboardId;
    }

    public Integer getMouseId() {
        return mouseId;
    }

    public void setMouseId(Integer mouseId) {
        this.mouseId = mouseId;
    }

    public Integer getKeyboardId() {
        return keyboardId;
    }

    public void setKeyboardId(Integer keyboardId) {
        this.keyboardId = keyboardId;
    }

    public Integer getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
    }

    public Integer getHeadphoneId() {
        return headphoneId;
    }

    public void setHeadphoneId(Integer headphoneId) {
        this.headphoneId = headphoneId;
    }

    public Integer getRomId() {
        return romId;
    }

    public void setRomId(Integer romId) {
        this.romId = romId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Computers{" +
                "computerId=" + computerId +
                ", name='" + name + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", motherboardId=" + motherboardId +
                ", mouseId=" + mouseId +
                ", keyboardId=" + keyboardId +
                ", monitorId=" + monitorId +
                ", headphoneId=" + headphoneId +
                ", romId=" + romId +
                ", roomId=" + roomId +
                ", playerId=" + playerId +
                ", status='" + status + '\'' +
                '}';
    }
}
