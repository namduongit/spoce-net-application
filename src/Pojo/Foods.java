package Pojo;

import java.time.LocalDateTime;

public class Foods {
    private int foodId;
    private String name;
    private double price;
    private String category; // "Đồ ăn" hoặc "Nước uống"
    private int quantity;
    private String status; // "Còn hàng" hoặc "Hết hàng"
    private LocalDateTime createdAt;

    public Foods(int foodId, String name, double price, String category, int quantity, LocalDateTime createdAt) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.status = (quantity > 0) ? "Còn hàng" : "Hết hàng";
        this.createdAt = createdAt;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.status = (quantity > 0) ? "Còn hàng" : "Hết hàng";
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Foods{" +
                "foodId=" + foodId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
