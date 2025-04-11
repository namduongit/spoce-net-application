package DTO;

import java.sql.Date;

public class Gpus {
    private int gpuId;
    private String brand;
    private String model;
    private int vram;
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

    public Gpus(int gpuId, String brand, String model, int vram, Date purchaseDate, Date warrantyExpiry, String status, double price) {
        this.gpuId = gpuId;
        this.brand = brand;
        this.model = model;
        this.vram = vram;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
        this.price = price;
    }

    // Constructor không tham số
    public Gpus() {
    }

    // Constructor có tham số
    public Gpus(int gpuId, String brand, String model, int vram, Date purchaseDate, Date warrantyExpiry, String status) {
        this.gpuId = gpuId;
        this.brand = brand;
        this.model = model;
        this.vram = vram;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
    }

    // Getter và Setter
    public int getGpuId() {
        return gpuId;
    }

    public void setGpuId(int gpuId) {
        this.gpuId = gpuId;
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

    public int getVram() {
        return vram;
    }

    public void setVram(int vram) {
        this.vram = vram;
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
        return "Gpus{" +
                "gpuId=" + gpuId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", vram=" + vram +
                ", purchaseDate=" + purchaseDate +
                ", warrantyExpiry=" + warrantyExpiry +
                ", status='" + status + '\'' +
                '}';
    }
}
