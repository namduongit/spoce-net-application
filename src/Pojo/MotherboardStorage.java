package Pojo;

public class MotherboardStorage {
    private int motherboardId;
    private int storageId;

    // Constructor không tham số
    public MotherboardStorage() {
    }

    // Constructor có tham số
    public MotherboardStorage(int motherboardId, int storageId) {
        this.motherboardId = motherboardId;
        this.storageId = storageId;
    }

    // Getter và Setter
    public int getMotherboardId() {
        return motherboardId;
    }

    public void setMotherboardId(int motherboardId) {
        this.motherboardId = motherboardId;
    }

    public int getStorageId() {
        return storageId;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    // Phương thức toString để trả về thông tin đối tượng
    @Override
    public String toString() {
        return "MotherboardStorage{" +
                "motherboardId=" + motherboardId +
                ", storageId=" + storageId +
                '}';
    }
}
