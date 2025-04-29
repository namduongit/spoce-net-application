package BLL;

import DAL.FoodRevenueDAL;
import DTO.FoodRevenue;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class FoodRevenueBLL {
    private FoodRevenueDAL foodRevenueDAL;

    public FoodRevenueBLL() {
        this.foodRevenueDAL = new FoodRevenueDAL();
    }

    public ArrayList<FoodRevenue> getFoodRevenueByRoom(LocalDateTime start, LocalDateTime end, String status) {
        return foodRevenueDAL.getFoodRevenueByRoom(start, end, status);
    }

    public FoodRevenue getPendingBillsInfo(LocalDateTime start, LocalDateTime end) {
        return foodRevenueDAL.getPendingBillsInfo(start, end);
    }

    public ArrayList<Object[]> getFoodBillDetails(LocalDateTime start, LocalDateTime end, String status) {
        return foodRevenueDAL.getFoodBillDetails(start, end, status);
    }

    public ArrayList<Object[]> getFoodRevenueByCategory(LocalDateTime start, LocalDateTime end, String category) {
        return foodRevenueDAL.getFoodRevenueByCategory(start, end, category);
    }

    public HashMap<String, Integer> getBillStatusCounts(LocalDateTime start, LocalDateTime end) {
        return foodRevenueDAL.getBillStatusCounts(start, end);
    }
    public ArrayList<String> getCategory() {
        return foodRevenueDAL.getCategory();
    }
}