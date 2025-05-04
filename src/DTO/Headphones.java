package DTO;

import java.sql.Date;

public class Headphones {
    private int headphoneId;
    private String brand;
    private String model;
    private String type;
    private Date purchaseDate;
    private Date warrantyExpiry;
    private String status;
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Headphones(int headphoneId, String brand, String model, String type, Date purchaseDate, Date warrantyExpiry, String status, double price) {
        this.headphoneId = headphoneId;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
        this.price = price;
    }
    public Headphones( String brand, String model, String type, Date purchaseDate, Date warrantyExpiry, String status, double price) {
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
        this.price = price;
    }

    // Constructor không tham số
    public Headphones() {
    }

    // Constructor có tham số
    public Headphones(int headphoneId, String brand, String model, String type, Date purchaseDate, Date warrantyExpiry, String status) {
        this.headphoneId = headphoneId;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
    }

    public Headphones(int id, String brand, String model, Date purchaseDate, Date warrantyExpiry) {
        this.headphoneId = id;
        this.brand = brand;
        this.model = model;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
    }

    // Getter và Setter
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
