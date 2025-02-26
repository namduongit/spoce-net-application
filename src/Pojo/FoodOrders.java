package Pojo;

public class FoodOrders {
    private int billId;
    private int foodId;
    private int quantity;

    public FoodOrders(int billId, int foodId, int quantity) {
        this.billId = billId;
        this.foodId = foodId;
        this.quantity = quantity;
    }

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
        this.quantity = quantity > 0 ? quantity : this.quantity; // Thực ra có như không :)))) nhưng do tạo bảng có tiên quyết lớn hơn 0
    }

    @Override
    public String toString() {
        return "FoodOrder{" +
                "billId=" + billId +
                ", foodId=" + foodId +
                ", quantity=" + quantity +
                '}';
    }
}
