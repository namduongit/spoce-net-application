package DTO;

import java.sql.Date;

public class Rams {
    private int ramId;
    private String brand;
    private String model;
    private int capacity;
    private int speed;
    private Date purchaseDate;
    private Date warrantyExpiry;
    private String status;
    private double price;

    // Constructor không tham số
    public Rams() {
    }

    // Constructor có tham số
    public Rams(int ramId, String brand, String model, int capacity, int speed, Date purchaseDate, Date warrantyExpiry, String status) {
        this.ramId = ramId;
        this.brand = brand;
        this.model = model;
        this.capacity = capacity;
        this.speed = speed;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
    }
    public Rams(int ramId, String brand, String model, int capacity, int speed, Date purchaseDate, Date warrantyExpiry, String status, double price) {
        this.ramId = ramId;
        this.brand = brand;
        this.model = model;
        this.capacity = capacity;
        this.speed = speed;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
        this.price = price;
    }
    public Rams( String brand, String model, int capacity, int speed, Date purchaseDate, Date warrantyExpiry, String status, double price) {
        this.brand = brand;
        this.model = model;
        this.capacity = capacity;
        this.speed = speed;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
        this.price = price;
    }

    // Getter và Setter
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getRamId() {
        return ramId;
    }

    public void setRamId(int ramId) {
        this.ramId = ramId;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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
        return "Rams{" +
                "ramId=" + ramId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                ", speed=" + speed +
                ", purchaseDate=" + purchaseDate +
                ", warrantyExpiry=" + warrantyExpiry +
                ", status='" + status + '\'' +
                '}';
    }
}
