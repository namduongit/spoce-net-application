package DTO;

import java.sql.Date;

public class Cpus {
    private int cpuId;
    private String brand;
    private String model;
    private float clockSpeed;
    private int cores;
    private int threads;
    private boolean integratedGpu;
    private Date purchaseDate;
    private Date warrantyExpiry;
    private String status;

    // Constructor không tham số
    public Cpus() {
    }

    // Constructor có tham số
    public Cpus(int cpuId, String brand, String model, float clockSpeed, int cores, int threads, boolean integratedGpu, Date purchaseDate, Date warrantyExpiry, String status) {
        this.cpuId = cpuId;
        this.brand = brand;
        this.model = model;
        this.clockSpeed = clockSpeed;
        this.cores = cores;
        this.threads = threads;
        this.integratedGpu = integratedGpu;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
    }

    // Getter và Setter
    public int getCpuId() {
        return cpuId;
    }

    public void setCpuId(int cpuId) {
        this.cpuId = cpuId;
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

    public float getClockSpeed() {
        return clockSpeed;
    }

    public void setClockSpeed(float clockSpeed) {
        this.clockSpeed = clockSpeed;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public boolean isIntegratedGpu() {
        return integratedGpu;
    }

    public void setIntegratedGpu(boolean integratedGpu) {
        this.integratedGpu = integratedGpu;
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
        return "Cpus{" +
                "cpuId=" + cpuId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", clockSpeed=" + clockSpeed +
                ", cores=" + cores +
                ", threads=" + threads +
                ", integratedGpu=" + integratedGpu +
                ", purchaseDate=" + purchaseDate +
                ", warrantyExpiry=" + warrantyExpiry +
                ", status='" + status + '\'' +
                '}';
    }
}
