package com.gohool.restaurant.bestrestaurant;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gohool.restaurant.bestrestaurant.Adapter.CartAdapter;
import com.gohool.restaurant.bestrestaurant.Model.OrderItem;
import com.gohool.restaurant.bestrestaurant.DatabaseHandler.DBOrderItem;
import com.gohool.restaurant.bestrestaurant.Model.Request;
import com.gohool.restaurant.bestrestaurant.Model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    RecyclerView recycler_cart;
    RecyclerView.LayoutManager layoutManager;

    TextView txtTotalPrice;
    Button btnPlaceOrder;
    String customerPhone, waiterEmail, tableNo;

    List<OrderItem> cart = new ArrayList<>();

    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recycler_cart = (RecyclerView)findViewById(R.id.listCart);
        recycler_cart.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_cart.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView) findViewById(R.id.textViewtotal);
        btnPlaceOrder = (Button) findViewById(R.id.btnPlaceOrder);

        //Create and get user
        User user = SharedPrefManager.getInstance(this).getUser();

        waiterEmail = user.getEmail();

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if Cart empty
                if(cart.size()==0) {
                    Toast.makeText(CartActivity.this, "Your cart is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                else {

                    showAlertDialog();
                }

            }
        });

        loadCartFood();


    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(CartActivity.this);
        alertDialog1.setTitle("One more step!");
        alertDialog1.setMessage("Fill Table and Customer Details");

        final EditText editTextTableNo = new EditText(CartActivity.this);
        editTextTableNo.setHint("Table Number");
        editTextTableNo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);

        final EditText editTextCustomerPhone = new EditText(CartActivity.this);
        editTextCustomerPhone.setHint("Customer Phone");
        editTextCustomerPhone.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);

        LinearLayout layout = new LinearLayout(CartActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT
        );
        editTextTableNo.setLayoutParams(lp);
        editTextCustomerPhone.setLayoutParams(lp);

        layout.addView(editTextTableNo,LinearLayout.LayoutParams.MATCH_PARENT);
        layout.addView(editTextCustomerPhone,LinearLayout.LayoutParams.MATCH_PARENT);



        alertDialog1.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog1.setView(layout); //Add Edit Text To Alert Dialog1

        alertDialog1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tableNo = editTextTableNo.getText().toString();
                customerPhone = editTextCustomerPhone.getText().toString();

                if (editTextCustomerPhone.getText().toString().equals("")||editTextTableNo.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                    return;

                }

                //Create new Request
                Request request = new Request(
                        waiterEmail,
                        tableNo,
                        customerPhone,
                        txtTotalPrice.getText().toString(),
                        cart
                );

                //Submit to PHP
                List<Request> newRequest = new ArrayList<>();
                newRequest.add(request);
                Gson gson = new Gson();
                final String newDataArray = gson.toJson(newRequest);


                //if everything is fine
                StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URLs.URL_ORDER_REQUEST,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("response:", response);
                                //progressBar.setVisibility(View.VISIBLE);
                                try {
                                    //converting response to json object
                                    JSONObject obj = new JSONObject(response);

                                    //if no error in response
                                    if (!obj.getBoolean("error")) {
                                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();


                                        //Cleear Cart
                                        new DBOrderItem(getBaseContext()).clearCart();

                                        //Finishing activity after successful order
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                error.getMessage();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("list", newDataArray);
                        return params;
                    }
                };

                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            }
        });

        alertDialog1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }


        });

        alertDialog1.show();

    }


    private void loadCartFood() {
        cart = new DBOrderItem(this).getCart();
        adapter = new CartAdapter(cart, this);
        recycler_cart.setAdapter(adapter);

        //Calculate Total Price
        int total = 0;
        for(OrderItem orderItem:cart)
            total += ((Integer.parseInt(orderItem.getPrice()))*(Integer.parseInt(orderItem.getQuantity())));

        Locale locale = new Locale("en", "GH");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(SharedPrefManager.DELETE))
            deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int position) {
        //Remove single cart item long press
        cart.remove(position);

        //After that, we delete all old data from SQLite
        new DBOrderItem(this).clearCart();
        //And finally, we update from List<order> to SQLite
        for(OrderItem item: cart)
            new DBOrderItem(this).addToCart(item);
        //Refresh
        loadCartFood();
    }
}
