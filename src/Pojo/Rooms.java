package Pojo;

import java.time.LocalDateTime;

public class Rooms {
    private int roomId;
    private String roomName;
    private int maxComputers;
    private String type;
    private String status;
    private LocalDateTime createdAt;

    public Rooms() {
    }

    public Rooms(int roomId, String roomName, int maxComputers, String type, String status, LocalDateTime createdAt) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.maxComputers = maxComputers;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getMaxComputers() {
        return maxComputers;
    }

    public void setMaxComputers(int maxComputers) {
        this.maxComputers = maxComputers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "Rooms{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", maxComputers=" + maxComputers +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
