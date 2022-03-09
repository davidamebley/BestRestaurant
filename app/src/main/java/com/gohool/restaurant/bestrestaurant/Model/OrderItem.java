package com.gohool.restaurant.bestrestaurant.Model;

/**
 * Created by Akwasi on 3/24/2018.
 */

public class OrderItem {
    private String FoodId, FoodName, Quantity, Price;

    public OrderItem(String foodId) {

    }

    public OrderItem(String foodId,String foodName, String price, String quantity) {
        FoodId = foodId;
        FoodName = foodName;
        Price = price;
        Quantity = quantity;

    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
