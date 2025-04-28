package BLL;

import DAL.FoodRevenueDAL;
import DTO.FoodRevenue;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FoodRevenueBLL {
    private FoodRevenueDAL foodRevenueDAL;

    public FoodRevenueBLL() {
        this.foodRevenueDAL = new FoodRevenueDAL();
    }

    public ArrayList<FoodRevenue> getFoodRevenue(LocalDateTime start, LocalDateTime end) {
        return foodRevenueDAL.getFoodRevenue(start, end);
    }
}

