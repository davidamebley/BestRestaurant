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
import com.gohool.restaurant.bestrestaurant.FoodDetailActivity;
import com.gohool.restaurant.bestrestaurant.FoodListActivity;
import com.gohool.restaurant.bestrestaurant.Interface.ItemClickListener;
import com.gohool.restaurant.bestrestaurant.Model.Food;
import com.gohool.restaurant.bestrestaurant.Model.MenuCategory;
import com.gohool.restaurant.bestrestaurant.R;

import java.util.List;

/**
 * Created by Akwasi on 3/20/2018.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {


    private Context mCtx;
    private List<Food> foodList;

    public FoodAdapter(Context mCtx, List<Food> foodList) {
        this.mCtx = mCtx;
        this.foodList = foodList;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = LayoutInflater.from(mCtx).inflate(R.layout.food_item, parent, false);
//        View view = inflater.inflate(R.layout.menu_category_list, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        Food food = foodList.get(position);

        RequestOptions requestOptions = new RequestOptions();
        //loading the image
        Glide.with(mCtx)
                .load(food.getPhoto())
                .apply(requestOptions)
                .into(holder.foodImage);

        holder.textViewFoodName.setText(food.getName());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewFoodName;
        public ImageView foodImage;

        //private ItemClickListener itemClickListener;

        public FoodViewHolder(View itemView) {
            super(itemView);

            textViewFoodName = itemView.findViewById(R.id.textViewFood_name);
            foodImage = itemView.findViewById(R.id.imageViewFood_image);

            itemView.setOnClickListener(this);
        }

//        public void setItemClickListener(ItemClickListener itemClickListener){
//            this.itemClickListener = itemClickListener;
//        }

        @Override
        public void onClick(View v) {
            //itemClickListener.onClick(v, getAdapterPosition(), false );
            Food food = foodList.get(getAdapterPosition());
//            Toast.makeText(mCtx.getApplicationContext(), food.getName()+":  "+food.getDescription(), Toast.LENGTH_LONG).show();

            //Start New Activity
            Intent foodDetailIntent = new Intent(mCtx, FoodDetailActivity.class);
            foodDetailIntent.putExtra("FoodId", food.getFoodId());

            mCtx.startActivity (foodDetailIntent);
        }
    }
}