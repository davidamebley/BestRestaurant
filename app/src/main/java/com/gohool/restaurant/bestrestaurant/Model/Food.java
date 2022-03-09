package com.gohool.restaurant.bestrestaurant.Model;

/**
 * Created by Akwasi on 3/19/2018.
 */

public class Food {
    private String Name, Description, Photo;
    private String UnitPrice;
    private Integer MenuId, FoodId;

    public Food() {
    }

    public Food(Integer foodId, String name, String description, String unitPrice, Integer menuId, String photo) {

        this.FoodId = foodId;
        Name = name;
        Description = description;
        UnitPrice = unitPrice;
        MenuId = menuId;
        Photo = photo;
    }

    public Integer getFoodId() {
        return FoodId;
    }

    public void setFoodId(Integer foodId) {
        this.FoodId = foodId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public Integer getMenuId() {
        return MenuId;
    }

    public void setMenuId(Integer menuId) {
        MenuId = menuId;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
