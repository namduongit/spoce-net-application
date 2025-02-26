package Pojo;

import java.time.LocalDate;

public class Psus {
    private int psuId;
    private String brand;
    private String model;
    private int wattage;
    private LocalDate purchaseDate;
    private LocalDate warrantyExpiry;
    private String status;

    public Psus() {
    }

    public Psus(int psuId, String brand, String model, int wattage, LocalDate purchaseDate, LocalDate warrantyExpiry, String status) {
        this.psuId = psuId;
        this.brand = brand;
        this.model = model;
        this.wattage = wattage;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
    }

    public int getPsuId() {
        return psuId;
    }

    public void setPsuId(int psuId) {
        this.psuId = psuId;
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

    public int getWattage() {
        return wattage;
    }

    public void setWattage(int wattage) {
        this.wattage = wattage;
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
        return "Psus{" +
                "psuId=" + psuId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", wattage=" + wattage +
                ", purchaseDate=" + purchaseDate +
                ", warrantyExpiry=" + warrantyExpiry +
                ", status='" + status + '\'' +
                '}';
    }
}
