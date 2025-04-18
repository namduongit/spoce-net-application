package BLL;

import java.util.ArrayList;
import java.util.HashMap;

import DAL.FoodDAL;
import DTO.Foods;

public class FoodBLL {
    private FoodDAL foodDAL;
    public FoodBLL() {
        this.foodDAL = new FoodDAL();
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

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
        return this.foodDAL.getFoodByStatus(status);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

    public ArrayList<Foods> searchFoodByName(String keyword) {
        return this.foodDAL.searchFoodByName(keyword);
    }

    public ArrayList<Foods> advancedSearch(String category, String status, String keyword) {
        return this.foodDAL.advancedSearch(category, status, keyword);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

    public boolean updateFoodDetailsById(int foodId, HashMap<String, Object> updateValues) {
        return this.foodDAL.updateFoodDetailsById(foodId, updateValues);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

    public boolean deleteFoodById(int foodId) {
       return this.foodDAL.deleteFoodById(foodId);
    }
     
    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

    public boolean createNewFood(String name, int price, int quantity, int categoryId, String image) {
       return this.foodDAL.createNewFood(name, price, quantity, categoryId, image);
    }

    public static void main(String[] args) {
        System.out.println(new FoodBLL().createNewFood("Test function", 0, 1, 1, null));
    }
}
