package com.example.myapplication.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    List<Product>productList;

    public MyAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        Product product = productList.get(position);
        holder.price.setText(product.getPrice());
        holder.productName.setText(product.getProductName());
        holder.shopName.setText(product.getuId());
        holder.quantity.setText(String.valueOf(product.getQuantity()));
        Picasso.get().load(product.getListImageUrl().get(0)).into(holder.imgProduct);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView  price, productName, shopName, quantity;
        ImageView imgProduct;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.price);
            productName = itemView.findViewById(R.id.productName);
            shopName = itemView.findViewById(R.id.shopName);
            quantity = itemView.findViewById(R.id.quantity);
            imgProduct = itemView.findViewById(R.id.imgProduct);

        }
    }
}
