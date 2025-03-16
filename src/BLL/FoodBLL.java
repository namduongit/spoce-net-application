package BLL;

import java.util.ArrayList;

import DAL.FoodDAL;
import DTO.Foods;

public class FoodBLL {
    private FoodDAL foodDAL;
    public FoodBLL() {
        this.foodDAL = new FoodDAL();
    }

    public ArrayList<Foods> getAllFoods() {
        return this.foodDAL.getFoodList();
    }


    public ArrayList<Foods> getFoodByCategory(String typeCategory) {
        return !typeCategory.equalsIgnoreCase("Tất cả") ? this.foodDAL.getFoodByCategory(typeCategory) : this.foodDAL.getFoodList();
    }

    public Foods getFoodByID(int id) {
        return this.foodDAL.getFoodByID(id);
    }

    public ArrayList<Foods> getFoodByStatus(String status) {
        return foodDAL.getFoodByStatus(status);
    }

    public ArrayList<Foods> searchFoodByName(String keyword) {
        return foodDAL.searchFoodByName(keyword);
    }

    public ArrayList<Foods> advancedSearch(String category, String status, String keyword) {
        return foodDAL.advancedSearch(category, status, keyword);
    }

}
