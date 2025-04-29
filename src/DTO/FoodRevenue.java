package DTO;

public class FoodRevenue {
    private String food;
    private double totalRevenue;

    public FoodRevenue(String food, double totalRevenue) {
        this.food = food;
        this.totalRevenue = totalRevenue;
    }
    public FoodRevenue() {
    }

    public String getFood() {
        return food;
    }
    public void setFood(String food) {
        this.food = food;
    }
    public double getTotalRevenue() {
        return totalRevenue;
    }
    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

   
}
