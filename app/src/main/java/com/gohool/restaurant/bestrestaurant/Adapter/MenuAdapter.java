package com.gohool.restaurant.bestrestaurant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
//import com.gohool.restaurant.bestrestaurant.FoodListActivity;
import com.gohool.restaurant.bestrestaurant.FoodListActivity;
import com.gohool.restaurant.bestrestaurant.Interface.ItemClickListener;
import com.gohool.restaurant.bestrestaurant.Model.MenuCategory;
import com.gohool.restaurant.bestrestaurant.R;

import java.util.List;

/**
 * Created by Akwasi on 3/17/2018.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {


    private Context mCtx;
    private List<MenuCategory> menuCategoryList;

    public MenuAdapter(Context mCtx, List<MenuCategory> menuCategoryList) {
        this.mCtx = mCtx;
        this.menuCategoryList = menuCategoryList;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = LayoutInflater.from(mCtx).inflate(R.layout.menu_category_list, parent, false);
//        View view = inflater.inflate(R.layout.menu_category_list, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        MenuCategory menuCategory = menuCategoryList.get(position);

        RequestOptions requestOptions = new RequestOptions();
        //loading the image
        Glide.with(mCtx)
                .load(menuCategory.getPhoto())
                .apply(requestOptions)
                .into(holder.menuImage);

        holder.textViewMenuDescription.setText(menuCategory.getDescription());
    }

    @Override
    public int getItemCount() {
        return menuCategoryList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewMenuDescription;
        public ImageView menuImage;
        private int value;

        //private ItemClickListener itemClickListener;

        public MenuViewHolder(View itemView) {
            super(itemView);

            textViewMenuDescription = itemView.findViewById(R.id.textViewMenu_name);
            menuImage = itemView.findViewById(R.id.imageViewMenu_image);

            itemView.setOnClickListener(this);
        }

//        public void setItemClickListener(ItemClickListener itemClickListener){
//            this.itemClickListener = itemClickListener;
//        }

        @Override
        public void onClick(View v) {
            //itemClickListener.onClick(v, getAdapterPosition(), false );
            MenuCategory menuItem = menuCategoryList.get(getAdapterPosition());
//            Toast.makeText(mCtx, menuItem.getDescription(), Toast.LENGTH_LONG).show();
//            Toast.makeText(mCtx.getApplicationContext(), menuItem.getDescription()+" Has ID: "+menuItem.getCatid(), Toast.LENGTH_LONG).show();

//            GET Menu ID and Send To New Activity
            Intent foodListIntent = new Intent(mCtx, FoodListActivity.class);
//            We use the 'GetCatId' method to get the menu Id as a KEY value and send along
            foodListIntent.putExtra("menuId", menuItem.getCatid());
            mCtx.startActivity(foodListIntent);
        }
    }
}