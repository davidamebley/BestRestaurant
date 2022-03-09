package com.gohool.restaurant.bestrestaurant;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.gohool.restaurant.bestrestaurant.DatabaseHandler.DBOrderItem;
import com.gohool.restaurant.bestrestaurant.Model.Food;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FoodDetailActivity extends AppCompatActivity{
    private Context mCtx;

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    private Bundle extras;

    Integer foodId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //Init View
        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);

        //Setting onClick Listener for Cart
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DBOrderItem(getBaseContext()).addToCart(new com.gohool.restaurant.bestrestaurant.Model.OrderItem(
                        currentFood.getFoodId().toString(),
                        currentFood.getName(),
                        currentFood.getUnitPrice().toString(),
                        numberButton.getNumber()

                ));
        Toast.makeText(getBaseContext(), "Added to cart",Toast.LENGTH_SHORT).show();

            }
        });

        food_description = (TextView) findViewById(R.id.food_description);
        food_name = (TextView) findViewById(R.id.food_name);
        food_price = (TextView) findViewById(R.id.food_price);
        food_image = (ImageView) findViewById(R.id.img_food_detail);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        //GET Intent Here
        extras = getIntent().getExtras();

        if(extras !=null){
            foodId = extras.getInt("FoodId");
        }

        if(foodId !=null){

            //This method fetches and parses json
            //to display it in RecyclerView
            loadFoodDetail();

        }
    }

    //Calling our Food class
    Food currentFood;
    private void loadFoodDetail() {
        /*
        * Creating a String Request
        *
        * In response listener we will get the JSON response as a String
        * */

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_FOOD_DETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response: ", response);
                        //progressBar.setVisibility(View.VISIBLE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                                //getting the Food Detail from the response
                                JSONObject foodDetailJson = obj.getJSONObject("food");

                                //creating a new Food object
                                currentFood = new Food(
                                        foodDetailJson.getInt("foodid"),
                                        foodDetailJson.getString("name"),
                                        foodDetailJson.getString("description"),
                                        foodDetailJson.getString("unit_price"),
                                        foodDetailJson.getInt("menu_id"),
                                        foodDetailJson.getString("photo")
                                );

                            //setting the values to the UI Controls

                            //SET Image
                            RequestOptions requestOptions = new RequestOptions();
                            //loading the image
                            Glide.with(FoodDetailActivity.this)
                                    .load(currentFood.getPhoto())
                                    .apply(requestOptions)
                                    .into(food_image);

                            collapsingToolbarLayout.setTitle(currentFood.getName());
                            food_price.setText(currentFood.getUnitPrice().toString());
                            food_name.setText(currentFood.getName());
                            food_description.setText(currentFood.getDescription());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("foodID", foodId.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



}
