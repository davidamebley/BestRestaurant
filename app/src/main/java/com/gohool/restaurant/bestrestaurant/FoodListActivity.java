package com.gohool.restaurant.bestrestaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gohool.restaurant.bestrestaurant.Adapter.FoodAdapter;
import com.gohool.restaurant.bestrestaurant.Model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodListActivity extends AppCompatActivity {
    //A list to store all Foods
    List<Food> foodList;
//    List for search food
//    List<String> suggestFoodList;

    //Creating adapter for food list
    FoodAdapter foodAdapter;

    //Creating adapter for Search food list
    FoodAdapter searchFoodAdapter;

//    MaterialSearchBar materialSearchBar;

    RecyclerView recycler_food;
    RecyclerView.LayoutManager layoutManager;
    private Bundle extras;
    Integer menuId = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //getting the RecyclerView from xml
        recycler_food = findViewById(R.id.recycler_food);
        recycler_food.setHasFixedSize(true);
        recycler_food.setLayoutManager(new LinearLayoutManager(this));

        layoutManager = new LinearLayoutManager(this);
        recycler_food.setLayoutManager(layoutManager);

        //initializing the food list
        foodList = new ArrayList<>();
        //initializing the search list
//        suggestFoodList = new ArrayList<>();


        //setting food adapter to recyclerview with food list
        foodAdapter = new FoodAdapter(FoodListActivity.this, foodList);

        //creating Search Food adapter object and setting it to recyclerview
//        searchFoodAdapter = new FoodAdapter(FoodListActivity.this, foodList);


        //GET Intent Here
        extras = getIntent().getExtras();

        if(extras !=null){
            menuId = extras.getInt("menuId");
        }

        if(menuId !=null){

            //This method fetches and parses json
            //to display it in RecyclerView
            loadFoodList();

        }

    }


    private void loadFoodList() {

         /*
        * Creating a String Request
        *
        * In response listener we will get the JSON response as a String
        * */

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_MENU_FOOD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response: ",response);

                        try {
//                            progressBar.setVisibility(View.VISIBLE);
                            //converting response to json object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject foodItem = array.getJSONObject(i);

                                //adding the Food to Food list
                                foodList.add(new Food(
                                        foodItem.getInt("foodid"),
                                        foodItem.getString("name"),
                                        foodItem.getString("description"),
                                        foodItem.getString("unit_price"),
                                        foodItem.getInt("menu_id"),
                                        foodItem.getString("photo")
                                ));
                            }
//                            progressBar.setVisibility(View.GONE);
                            //setting adapter to recyclerview
//                            FoodAdapter foodAdapter = new FoodAdapter(FoodListActivity.this, foodList);
                            recycler_food.setAdapter(foodAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("menuID", menuId.toString());
                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
