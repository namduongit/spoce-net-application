package model.Pojo;

import java.sql.Date;

public class Keyboards {
    private int keyboardId;
    private String brand;
    private String model;
    private Date purchaseDate;
    private Date warrantyExpiry;
    private String status;

    // Constructor không tham số
    public Keyboards() {
    }

    // Constructor có tham số
    public Keyboards(int keyboardId, String brand, String model, Date purchaseDate, Date warrantyExpiry, String status) {
        this.keyboardId = keyboardId;
        this.brand = brand;
        this.model = model;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.status = status;
    }

    // Getter và Setter
    public int getKeyboardId() {
        return keyboardId;
    }

    public void setKeyboardId(int keyboardId) {
        this.keyboardId = keyboardId;
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
        return "Keyboards{" +
                "keyboardId=" + keyboardId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", warrantyExpiry=" + warrantyExpiry +
                ", status='" + status + '\'' +
                '}';
    }
}
