package Pojo;

import java.time.LocalDate;

public class Roms {
    private int romId;
    private String brand;
    private String model;
    private String type;
    private int capacity;
    private LocalDate purchaseDate;
    private LocalDate warrantyExpiry;
    private String status;

    public Roms() {
    }

    public Roms(int romId, String brand, String model, String type, int capacity, LocalDate purchaseDate, LocalDate warrantyExpiry, String status) {
        this.romId = romId;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.capacity = capacity;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
    }

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

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getWarrantyExpiry() {
        return warrantyExpiry;
    }

    public void setWarrantyExpiry(LocalDate warrantyExpiry) {
        this.warrantyExpiry = warrantyExpiry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Roms{" +
                "romId=" + romId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", capacity=" + capacity + "MB" +
                ", purchaseDate=" + purchaseDate +
                ", warrantyExpiry=" + warrantyExpiry +
                ", status='" + status + '\'' +
                '}';
    }
}
