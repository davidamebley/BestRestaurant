package com.gohool.restaurant.bestrestaurant.Model;

/**
 * Created by Akwasi on 3/17/2018.
 */

public class MenuCategory {
    private int catid;
    private String description;
    private String photo;


    public MenuCategory(int id, String description, String photo) {
        this.catid = id;
        this.description = description;
        this.photo = photo;
    }

    public int getCatid() {
        return catid;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }


}