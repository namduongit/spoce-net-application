package model.Pojo;

import java.sql.Date;

public class Roms {
    private int romId;
    private String brand;
    private String model;
    private String type;
    private int capacity;
    private Date purchaseDate;
    private Date warrantyExpiry;
    private String status;

    // Constructor không tham số
    public Roms() {
    }

    // Constructor có tham số
    public Roms(int romId, String brand, String model, String type, int capacity, Date purchaseDate, Date warrantyExpiry, String status) {
        this.romId = romId;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.capacity = capacity;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
    }

    // Getter và Setter
    public int getRomId() {
        return romId;
    }

    public void setRomId(int romId) {
        this.romId = romId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getWarrantyExpiry() {
        return warrantyExpiry;
    }

    public void setWarrantyExpiry(Date warrantyExpiry) {
        this.warrantyExpiry = warrantyExpiry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Phương thức toString để trả về thông tin đối tượng
    @Override
    public String toString() {
        return "Roms{" +
                "romId=" + romId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", capacity=" + capacity +
                ", purchaseDate=" + purchaseDate +
                ", warrantyExpiry=" + warrantyExpiry +
                ", status='" + status + '\'' +
                '}';
    }
}