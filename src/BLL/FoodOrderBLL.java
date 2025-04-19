package BLL;

import DAL.FoodOrderDAL;

public class FoodOrderBLL {
    private FoodOrderDAL foodOrderDAL;
    
    public FoodOrderBLL() {
        this.foodOrderDAL = new FoodOrderDAL();
    }

    public boolean insertDataBill(int billId, int foodId, int quantity) {
        return this.foodOrderDAL.insertDataBill(billId, foodId, quantity);
    }

    
}
