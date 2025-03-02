package Pojo;

import java.sql.Date;

public class Motherboards {
    private int motherboardId;
    private String brand;
    private String model;
    private String socket;
    private String chipset;
    private int ramSlots;
    private int maxRam;
    private int ramSpeed;
    private int storageSlots;
    private int sataPorts;
    private int m2Slots;
    private int maxStorageCapacity;
    private String status;
    private Integer cpuId;
    private Integer psuId;
    private Integer gpuId;
    private Date purchaseDate;
    private Date warrantyExpiry;

    // Constructor không tham số
    public Motherboards() {
    }

    // Constructor có tham số
    public Motherboards(int motherboardId, String brand, String model, String socket, String chipset, int ramSlots, int maxRam, int ramSpeed, int storageSlots, int sataPorts, int m2Slots, int maxStorageCapacity, String status, Integer cpuId, Integer psuId, Integer gpuId, Date purchaseDate, Date warrantyExpiry) {
        this.motherboardId = motherboardId;
        this.brand = brand;
        this.model = model;
        this.socket = socket;
        this.chipset = chipset;
        this.ramSlots = ramSlots;
        this.maxRam = maxRam;
        this.ramSpeed = ramSpeed;
        this.storageSlots = storageSlots;
        this.sataPorts = sataPorts;
        this.m2Slots = m2Slots;
        this.maxStorageCapacity = maxStorageCapacity;
        this.status = status;
        this.cpuId = cpuId;
        this.psuId = psuId;
        this.gpuId = gpuId;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
    }

    // Getter và Setter
    public int getMotherboardId() {
        return motherboardId;
    }

    public void setMotherboardId(int motherboardId) {
        this.motherboardId = motherboardId;
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

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public int getRamSlots() {
        return ramSlots;
    }

    public void setRamSlots(int ramSlots) {
        this.ramSlots = ramSlots;
    }

    public int getMaxRam() {
        return maxRam;
    }

    public void setMaxRam(int maxRam) {
        this.maxRam = maxRam;
    }

    public int getRamSpeed() {
        return ramSpeed;
    }

    public void setRamSpeed(int ramSpeed) {
        this.ramSpeed = ramSpeed;
    }

    public int getStorageSlots() {
        return storageSlots;
    }

    public void setStorageSlots(int storageSlots) {
        this.storageSlots = storageSlots;
    }

    public int getSataPorts() {
        return sataPorts;
    }

    public void setSataPorts(int sataPorts) {
        this.sataPorts = sataPorts;
    }

    public int getM2Slots() {
        return m2Slots;
    }

    public void setM2Slots(int m2Slots) {
        this.m2Slots = m2Slots;
    }

    public int getMaxStorageCapacity() {
        return maxStorageCapacity;
    }

    public void setMaxStorageCapacity(int maxStorageCapacity) {
        this.maxStorageCapacity = maxStorageCapacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCpuId() {
        return cpuId;
    }

    public void setCpuId(Integer cpuId) {
        this.cpuId = cpuId;
    }

    public Integer getPsuId() {
        return psuId;
    }

    public void setPsuId(Integer psuId) {
        this.psuId = psuId;
    }

    public Integer getGpuId() {
        return gpuId;
    }

    public void setGpuId(Integer gpuId) {
        this.gpuId = gpuId;
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

    // Phương thức toString để trả về thông tin đối tượng
    @Override
    public String toString() {
        return "Motherboards{" +
                "motherboardId=" + motherboardId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", socket='" + socket + '\'' +
                ", chipset='" + chipset + '\'' +
                ", ramSlots=" + ramSlots +
                ", maxRam=" + maxRam +
                ", ramSpeed=" + ramSpeed +
                ", storageSlots=" + storageSlots +
                ", sataPorts=" + sataPorts +
                ", m2Slots=" + m2Slots +
                ", maxStorageCapacity=" + maxStorageCapacity +
                ", status='" + status + '\'' +
                ", cpuId=" + cpuId +
                ", psuId=" + psuId +
                ", gpuId=" + gpuId +
                ", purchaseDate=" + purchaseDate +
                ", warrantyExpiry=" + warrantyExpiry +
                '}';
    }
}
