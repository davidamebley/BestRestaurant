package com.gohool.restaurant.bestrestaurant;

import android.util.Log;

/**
 * Created by Akwasi on 3/15/2018.
 */

public class URLs {
    private static final String ROOT_URL = "http://192.168.56.1:80/gentelellamaster/production/Android/";

    public static final String URL_REGISTER = ROOT_URL + "signup.php";
    public static final String URL_LOGIN = ROOT_URL + "login.php";
    public static final String URL_MENU_CAT = ROOT_URL + "menu_cat.php";
    public static final String URL_MENU_FOOD = ROOT_URL + "menu_food.php";
    public static final String URL_FOOD_DETAIL = ROOT_URL + "food_detail.php";
    public static final String URL_ORDER_REQUEST = ROOT_URL + "order_request.php";
    public static final String URL_ORDER_STATUS = ROOT_URL + "order_requested_status.php";
}


//192.168.56.1:80 Virtual Box 1
//192.168.71.1  Virtual Box 2
//192.168.1.3 Wireless LAN