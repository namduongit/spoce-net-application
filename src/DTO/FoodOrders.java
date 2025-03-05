package DTO;

public class FoodOrders {
    private int billId;
    private int foodId;
    private int quantity;

    // Constructor không tham số
    public FoodOrders() {
    }

    // Constructor có tham số
    public FoodOrders(int billId, int foodId, int quantity) {
        this.billId = billId;
        this.foodId = foodId;
        this.quantity = quantity;
    }

    // Getter và Setter
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Phương thức toString để hiển thị thông tin đối tượng
    @Override
    public String toString() {
        return "FoodOrder{" +
                "billId=" + billId +
                ", foodId=" + foodId +
                ", quantity=" + quantity +
                '}';
    }
}
