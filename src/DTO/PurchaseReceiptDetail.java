package DTO;

public class PurchaseReceiptDetail {
    private int receiptId;
    private int foodId;
    private int quantity;
    private int price;

    public PurchaseReceiptDetail() {
    }

    public PurchaseReceiptDetail(int receiptId, int foodId, int quantity, int price) {
        this.receiptId = receiptId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PurchaseReceiptDetail{" +
                "receiptId=" + receiptId +
                ", foodId=" + foodId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
