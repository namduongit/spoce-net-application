package model.Pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Foods {
    private int foodId;
    private String name;
    private BigDecimal price;
    private int categoryId;
    private int quantity;
    private String status;
    private Timestamp createdAt;

    // Constructor không tham số
    public Foods() {
    }

    // Constructor có tham số
    public Foods(int foodId, String name, BigDecimal price, int categoryId, int quantity, String status, Timestamp createdAt) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.quantity = quantity;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getter và Setter
    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Phương thức toString để trả về thông tin đối tượng
    @Override
    public String toString() {
        return "Foods{" +
                "foodId=" + foodId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
