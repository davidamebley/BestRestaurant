package com.gohool.restaurant.bestrestaurant;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gohool.restaurant.bestrestaurant.Adapter.FoodAdapter;
import com.gohool.restaurant.bestrestaurant.Adapter.OrderAdapter;
import com.gohool.restaurant.bestrestaurant.Model.Food;
import com.gohool.restaurant.bestrestaurant.Model.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderStatus extends AppCompatActivity {
    //A list to store all Foods
    List<Request> requestList;
    int curReqSize;
    String newOrderStatus;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    String waiterEmail = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //getting the RecyclerView from xml
        recyclerView = (RecyclerView) findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing current request size
        curReqSize = 0;

        //Initializing new order status
        newOrderStatus = "";

        //initializing the request list
        requestList = new ArrayList<>();

        //Getting Current Waiter email
        waiterEmail = SharedPrefManager.getInstance(this).getUser().getEmail();

        loadOrders();

        //Setting Request list size to the Current Request size variable
        curReqSize = requestList.size();
        if(curReqSize<requestList.size()){

        }

    }

    private void loadOrders() {
        /*
        * Creating a String Request
        *
        * In response listener we will get the JSON response as a String
        * */

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URLs.URL_ORDER_STATUS,
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
                                JSONObject requestItem = array.getJSONObject(i);

                                //adding the Food to Food list
                                requestList.add(new Request(
                                        requestItem.getString("orderno"),
                                        requestItem.getString("tableno"),
                                        requestItem.getString("customer_phone"),
                                        requestItem.getString("status")
                                ));

                                //Checking if order status is "Done" or "Cancelled"
                                newOrderStatus = requestItem.getString("status");
                                if(newOrderStatus.toLowerCase().equalsIgnoreCase("done")
                                        || newOrderStatus.toLowerCase().equalsIgnoreCase("cancelled")){
                                    displayNotification();
                                }
                            }
//                            progressBar.setVisibility(View.GONE);
                            //creating adapter object and setting it to recyclerview
                            OrderAdapter orderAdapter = new OrderAdapter(requestList,OrderStatus.this);
                            recyclerView.setAdapter(orderAdapter);

                            //If no orders to display
                            if(requestList.size()==0){
                                Toast.makeText(getApplicationContext(), "No orders to display yet!", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("waiterEmail", waiterEmail);
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void displayNotification() {

        //Get an instance of NotificationManager//
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Order status changed!")
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentText("Order No.");


        //        Create an intent that wi ll be fired when user taps on notification
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/amebleykwasi"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, notificationIntent, 0);
        notificationBuilder.setContentIntent(pendingIntent);    /*Fire up the intent*/

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(001, notificationBuilder.build());
    }
}
