package com.gohool.restaurant.bestrestaurant.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.gohool.restaurant.bestrestaurant.Interface.ItemClickListener;
import com.gohool.restaurant.bestrestaurant.Model.OrderItem;
import com.gohool.restaurant.bestrestaurant.R;
import com.gohool.restaurant.bestrestaurant.SharedPrefManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Akwasi on 3/24/2018.
 */

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    , View.OnCreateContextMenuListener{

    public TextView txt_cart_name, txt_price;
    public ImageView img_cart_count;

    private ItemClickListener itemClickListener;


    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }



    public CartViewHolder(View itemView) {
        super(itemView);
        txt_cart_name = (TextView) itemView.findViewById(R.id.cart_item_name);
        txt_price = (TextView) itemView.findViewById(R.id.cart_item_Price);
        img_cart_count = (ImageView) itemView.findViewById(R.id.cart_item_count);

        itemView.setOnCreateContextMenuListener(this);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // For the COntext Menu
        menu.setHeaderTitle("Select action");
        menu.add(0,0,getAdapterPosition(), SharedPrefManager.DELETE);
    }
}


public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<OrderItem> orderItemList = new ArrayList<>();
    private Context context;

    public CartAdapter(List<OrderItem> orderItemList, Context context) {
        this.orderItemList = orderItemList;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+orderItemList.get(position).getQuantity(), Color.BLUE);
        holder.img_cart_count.setImageDrawable(drawable);

        Locale locale = new Locale("en", "GH");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(orderItemList.get(position).getPrice()))*(Integer.parseInt(orderItemList.get(position).getQuantity()));
        holder.txt_price.setText(fmt.format(price));

        holder.txt_cart_name.setText(orderItemList.get(position).getFoodName());
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }
}
