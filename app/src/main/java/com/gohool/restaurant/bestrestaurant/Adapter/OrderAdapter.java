package com.gohool.restaurant.bestrestaurant.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gohool.restaurant.bestrestaurant.Interface.ItemClickListener;
import com.gohool.restaurant.bestrestaurant.Model.Request;
import com.gohool.restaurant.bestrestaurant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akwasi on 5/27/2018.
 */

class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView textViewOrderNo, textViewOrderStatus, textViewOrderPhone, textViewOrderTableNo;

        private ItemClickListener itemClickListener;

        public OrderViewHolder(View itemView) {
            super(itemView);

            textViewOrderTableNo = (TextView) itemView.findViewById(R.id.order_table_no);
            textViewOrderStatus = itemView.findViewById(R.id.order_status);
            textViewOrderNo = itemView.findViewById(R.id.order_no);
            textViewOrderPhone = itemView.findViewById(R.id.order_customer_phone);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(),false);

        }
    }

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder>{

    private List<Request> requestList = new ArrayList<>();
    private Context context;

    public OrderAdapter(List<Request> requestList, Context context) {
        this.requestList = requestList;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.order_layout, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {

        holder.textViewOrderNo.setText(requestList.get(position).getOrderNo());
        holder.textViewOrderPhone.setText(requestList.get(position).getCustomerPhone());
        holder.textViewOrderTableNo.setText(requestList.get(position).getTableNo());
        holder.textViewOrderStatus.setText(requestList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return requestList.size();

    }
}
