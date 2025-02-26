package Pojo;

public class MotherboardStorage {
    private int motherboardId;
    private int storageId;

    public MotherboardStorage() {
    }

    public MotherboardStorage(int motherboardId, int storageId) {
        this.motherboardId = motherboardId;
        this.storageId = storageId;
    }

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

    @Override
    public String toString() {
        return "MotherboardStorage{" +
                "motherboardId=" + motherboardId +
                ", storageId=" + storageId +
                '}';
    }
}
