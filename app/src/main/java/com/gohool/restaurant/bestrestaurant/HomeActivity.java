package com.gohool.restaurant.bestrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gohool.restaurant.bestrestaurant.Adapter.MenuAdapter;
import com.gohool.restaurant.bestrestaurant.Model.MenuCategory;
import com.gohool.restaurant.bestrestaurant.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //A list to store all the Menu Categories
    List<MenuCategory> menuCategoryList;

    TextView txtUserName;
    RecyclerView recycler_menu_category;
    ProgressBar progressBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu"); //Set Title of Toolbar
        setSupportActionBar(toolbar);

        //getting the RecyclerView from xml
        recycler_menu_category = findViewById(R.id.recycler_menu);
        recycler_menu_category.setHasFixedSize(true);
        recycler_menu_category.setLayoutManager(new LinearLayoutManager(this));

        //Setting Progress bar
        progressBar = findViewById(R.id.progressBar);

        //initializing the productlist
        menuCategoryList = new ArrayList<>();

        //This method fetches and parses json
        //to display it in RecyclerView
        loadMenu();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.nav_cart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Set Name for Current User
        User user = SharedPrefManager.getInstance(this).getUser();

        View headerView = navigationView.getHeaderView(0);
        txtUserName = (TextView) headerView.findViewById(R.id.txtUserName);
        txtUserName.setText(user.getUsername());


    }

    private void loadMenu() {

         /*
        * Creating a String Request
        * The request type is GET defined by first parameter
        * The URL is defined in the second parameter
        * Then we have a Response Listener and a Error Listener
        * In response listener we will get the JSON response as a String
        * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_MENU_CAT,
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
                                JSONObject menuCat = array.getJSONObject(i);

                                //adding the product to product list
                                menuCategoryList.add(new MenuCategory(
                                        menuCat.getInt("catid"),
                                        menuCat.getString("description"),
                                        menuCat.getString("photo")
                                ));
                            }
//                            progressBar.setVisibility(View.GONE);
                            //creating adapter object and setting it to recyclerview
                            MenuAdapter menuAdapter = new MenuAdapter(HomeActivity.this, menuCategoryList);
                            recycler_menu_category.setAdapter(menuAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the menu
            Intent menuIntent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(menuIntent);

        } else if (id == R.id.cart) {
            //Open Cart
            Intent cartIntent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_orders) {
            //Opem orders
            Intent orderRequestIntent = new Intent(HomeActivity.this, OrderStatus.class);
            startActivity(orderRequestIntent);

        } else if (id == R.id.nav_log_out) {
            //Log user out

            finish();
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
