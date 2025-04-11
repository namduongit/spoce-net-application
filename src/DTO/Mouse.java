package DTO;

import java.sql.Date;

public class Mouse {
    private int mouseId;
    private String brand;
    private String model;
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

    public Mouse(int mouseId, String brand, String model, Date purchaseDate, Date warrantyExpiry, String status, double price) {
        this.mouseId = mouseId;
        this.brand = brand;
        this.model = model;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
        this.price = price;
    }

    // Constructor không tham số
    public Mouse() {
    }

    // Constructor có tham số
    public Mouse(int mouseId, String brand, String model, Date purchaseDate, Date warrantyExpiry, String status) {
        this.mouseId = mouseId;
        this.brand = brand;
        this.model = model;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
    }

    public Mouse(int id, String brand, String model) {
        this.mouseId = id;
        this.brand = brand;
        this.model = model;
    }

    public Mouse(int id, String brand, String model, Date purchaseDate, Date warrantyExpiry) {
        this.mouseId = id;
        this.brand = brand;
        this.model = model;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
    }

    // Getter và Setter
    public int getMouseId() {
        return mouseId;
    }

    public void setMouseId(int mouseId) {
        this.mouseId = mouseId;
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
        return "Mouse{" +
                "mouseId=" + mouseId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", warrantyExpiry=" + warrantyExpiry +
                ", status='" + status + '\'' +
                '}';
    }
}
