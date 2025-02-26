package Pojo;

import java.time.LocalDate;

public class Headphones {
    private int headphoneId;
    private String brand;
    private String model;
    private String type;
    private LocalDate purchaseDate;
    private LocalDate warrantyExpiry;
    private String status;

    public Headphones() {
    }

    public Headphones(int headphoneId, String brand, String model, String type, LocalDate purchaseDate, LocalDate warrantyExpiry, String status) {
        this.headphoneId = headphoneId;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
    }

    public int getHeadphoneId() {
        return headphoneId;
    }

    public void setHeadphoneId(int headphoneId) {
        this.headphoneId = headphoneId;
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
        return "Headphones{" +
                "headphoneId=" + headphoneId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", warrantyExpiry=" + warrantyExpiry +
                ", status='" + status + '\'' +
                '}';
    }
}
