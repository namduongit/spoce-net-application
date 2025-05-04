package BLL;

import java.util.ArrayList;

import DAL.FoodBillDAL;
import DTO.FoodBills;

public class FoodBillBLL {
    private FoodBillDAL foodBillDAL;

    public FoodBillBLL() {
        this.foodBillDAL = new FoodBillDAL();
    }

    public ArrayList<FoodBills> getFoodBillList() {
        return this.foodBillDAL.getFoodBillList();
    }

    public ArrayList<FoodBills> getFoodBillByCategoryId(int categoryId) {
        return this.foodBillDAL.getFoodBillByCategoryId(categoryId);
    }

    public ArrayList<Object[]> getDetailFoodBillById(int foodBillId) {
        return this.foodBillDAL.getDetailFoodBillById(foodBillId);
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

    public boolean updateCofirmFoodBill(int foodBillId) {
        return this.foodBillDAL.updateCofirmFoodBill(foodBillId);
    }

    public boolean updateCancelFoodBill(int foodBillId) {
        return this.foodBillDAL.updateCancelFoodBill(foodBillId);
    }


}
