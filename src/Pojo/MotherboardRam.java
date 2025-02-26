package Pojo;

public class MotherboardRam {
    private int motherboardId;
    private int ramId;

    public MotherboardRam() {
    }

    public MotherboardRam(int motherboardId, int ramId) {
        this.motherboardId = motherboardId;
        this.ramId = ramId;
    }

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

    @Override
    public String toString() {
        return "MotherboardRam{" +
                "motherboardId=" + motherboardId +
                ", ramId=" + ramId +
                '}';
    }
}
