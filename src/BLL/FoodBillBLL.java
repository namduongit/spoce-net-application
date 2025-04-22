package BLL;

import DAL.FoodBillDAL;

public class FoodBillBLL {
    private FoodBillDAL foodBillDAL;

    public FoodBillBLL() {
        this.foodBillDAL = new FoodBillDAL();
    }

    public int createLastestBill(int staffId) {
        return this.foodBillDAL.createLastestBill(staffId);
    }

    public boolean updateTotalPriceByBillId(int billId) {
        return this.foodBillDAL.updateTotalPriceByBillId(billId);
    }
    
    public boolean updateTotalPrice(int billId, int totalPrice) {
        return this.foodBillDAL.updateTotalPrice(billId, totalPrice);
    }
}
