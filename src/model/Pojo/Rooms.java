package model.Pojo;

import java.sql.Date;

public class Rooms {
    private int roomId;
    private String roomName;
    private int maxComputers;
    private String type;
    private String status;
    private Date createdAt;

    // Constructor không tham số
    public Rooms() {
    }

    // Constructor có tham số
    public Rooms(int roomId, String roomName, int maxComputers, String type, String status, Date createdAt) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.maxComputers = maxComputers;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getter và Setter
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Phương thức toString để trả về thông tin đối tượng
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
