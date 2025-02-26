package Pojo;

import java.time.LocalDate;

public class Gpus {
    private int gpuId;
    private String brand;
    private String model;
    private int vram;
    private LocalDate purchaseDate;
    private LocalDate warrantyExpiry;
    private String status;

    public Gpus() {
    }

    public Gpus(int gpuId, String brand, String model, int vram, LocalDate purchaseDate, LocalDate warrantyExpiry, String status) {
        this.gpuId = gpuId;
        this.brand = brand;
        this.model = model;
        this.vram = vram;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
    }

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
