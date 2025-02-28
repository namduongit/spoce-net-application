package Pojo;

public class MotherboardRam {
    private int motherboardId;
    private int ramId;

    // Constructor không tham số
    public MotherboardRam() {
    }

    // Constructor có tham số
    public MotherboardRam(int motherboardId, int ramId) {
        this.motherboardId = motherboardId;
        this.ramId = ramId;
    }

    // Getter và Setter
    public int getMotherboardId() {
        return motherboardId;
    }

    public void setMotherboardId(int motherboardId) {
        this.motherboardId = motherboardId;
    }

    public int getRamId() {
        return ramId;
    }

    public void setRamId(int ramId) {
        this.ramId = ramId;
    }

    // Phương thức toString để trả về thông tin đối tượng
    @Override
    public String toString() {
        return "MotherboardRam{" +
                "motherboardId=" + motherboardId +
                ", ramId=" + ramId +
                '}';
    }
}
