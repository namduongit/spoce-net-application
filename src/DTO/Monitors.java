package DTO;

import java.sql.Date;

public class Monitors {
    private int monitorId;
    private String brand;
    private String model;
    private float size;
    private int refreshRate;
    private Date purchaseDate;
    private Date warrantyExpiry;
    private String status;

    // Constructor không tham số
    public Monitors() {
    }

    // Constructor có tham số
    public Monitors(int monitorId, String brand, String model, float size, int refreshRate, Date purchaseDate, Date warrantyExpiry, String status) {
        this.monitorId = monitorId;
        this.brand = brand;
        this.model = model;
        this.size = size;
        this.refreshRate = refreshRate;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
    }

    // Getter và Setter
    public int getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(int monitorId) {
        this.monitorId = monitorId;
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

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
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
        return "Monitors{" +
                "monitorId=" + monitorId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", size=" + size +
                ", refreshRate=" + refreshRate +
                ", purchaseDate=" + purchaseDate +
                ", warrantyExpiry=" + warrantyExpiry +
                ", status='" + status + '\'' +
                '}';
    }
}
