package com.gohool.restaurant.bestrestaurant.DatabaseHandler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import com.gohool.restaurant.bestrestaurant.Utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akwasi on 3/24/2018.
 */
//This Class will hold all our Cart items before we submit
public class DBOrderItem extends SQLiteOpenHelper {

    public DBOrderItem(Context context) {
        super(context, Util.DATABASE_NAME,null, Util.DATABASE_VERSION );
    }


    //Create Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL
        String CREATE_ORDER_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS "+Util.TABLE_NAME +
                " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.KEY_FOOD_ID + " TEXT,  "
                + Util.KEY_FOOD_NAME + " TEXT, "
                + Util.KEY_FOOD_PRICE + " TEXT, "
                + Util.KEY_FOOD_QUANTITY + " TEXT" + ")";

        db.execSQL(CREATE_ORDER_ITEM_TABLE);
        Log.d("Create Table ", "OrderItem Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Util.TABLE_NAME);
        //ReCreate Table
        onCreate(db);
    }

    //Do STUFF

    //Get Cart
    public List <com.gohool.restaurant.bestrestaurant.Model.OrderItem> getCart(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String [] sqlSelect = {Util.KEY_FOOD_ID, Util.KEY_FOOD_NAME, Util.KEY_FOOD_PRICE, Util.KEY_FOOD_QUANTITY};
        String sqlTable = Util.TABLE_NAME;

        queryBuilder.setTables(sqlTable);
        Cursor cursor = queryBuilder.query(db, sqlSelect, null,  null, null, null, null);

        final List<com.gohool.restaurant.bestrestaurant.Model.OrderItem> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                result.add(new com.gohool.restaurant.bestrestaurant.Model.OrderItem(cursor.getString(cursor.getColumnIndex(Util.KEY_FOOD_ID)),
                        cursor.getString(cursor.getColumnIndex(Util.KEY_FOOD_NAME)),
                        cursor.getString(cursor.getColumnIndex(Util.KEY_FOOD_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Util.KEY_FOOD_QUANTITY))
                ));

            }while (cursor.moveToNext());
        }
        return result;
    }

    //Add to Cart
    public void addToCart(com.gohool.restaurant.bestrestaurant.Model.OrderItem orderItem){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO "+ Util.TABLE_NAME+ "(FoodID, FoodName, Price, Quantity) VALUES ('%s', '%s', '%s', '%s');",
                orderItem.getFoodId(),
                orderItem.getFoodName(),
                orderItem.getPrice(),
                orderItem.getQuantity());
        db.execSQL(query);
    }

    //Clear Cart
    public void clearCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM "+ Util.TABLE_NAME);

        db.execSQL(query);
    }

    //Drop Table
    public void dropOrderItem(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DROP TABLE "+ Util.TABLE_NAME);

        db.execSQL(query);
    }

    //Create Table
    public void createOrderItem(){
        SQLiteDatabase db = getWritableDatabase();
        String CREATE_ORDER_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS "+Util.TABLE_NAME +
                " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.KEY_FOOD_ID + " TEXT, "
                + Util.KEY_FOOD_NAME + " TEXT, "
                + Util.KEY_FOOD_PRICE + " TEXT, "
                + Util.KEY_FOOD_QUANTITY + " TEXT" + ")";

        db.execSQL(CREATE_ORDER_ITEM_TABLE);
        Log.d("Create Table ", "OrderItem Created");
    }

}
