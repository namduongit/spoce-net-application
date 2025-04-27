package BLL;

import DAL.FoodRevenueDAL;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FoodRevenueBLL {
    private FoodRevenueDAL foodRevenueDAL;

    public FoodRevenueBLL() {
        this.foodRevenueDAL = new FoodRevenueDAL();
    }

    // Lấy dữ liệu cho biểu đồ doanh thu món ăn
    public ArrayList<Object[]> getFoodRevenue(LocalDateTime start, LocalDateTime end) {
        return foodRevenueDAL.getFoodRevenue(start, end);
    }
}

