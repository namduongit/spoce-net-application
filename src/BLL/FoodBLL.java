package BLL;

import java.util.ArrayList;

import DAL.FoodDAL;
import DTO.Foods;

public class FoodBLL {
    private FoodDAL foodDAL;
    public FoodBLL() {
        this.foodDAL = new FoodDAL();
    }

    public ArrayList<Foods> getAllFood() {
        return this.foodDAL.getFoodList();
    }

    public void updateStatusFood() {
        this.foodDAL.updateStatusFood();
    }
}
