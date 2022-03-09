package com.gohool.restaurant.bestrestaurant.Model;

/**
 * Created by Akwasi on 3/15/2018.
 */

public class User {
    private int id;
    private String username, email, Phone;

    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
