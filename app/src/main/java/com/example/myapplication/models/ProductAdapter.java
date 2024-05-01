package com.example.myapplication.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.StructuredQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;

    private List<OrderDetail> orderDetailList;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ProductAdapter(Context context, List<OrderDetail> orderDetailList) {
        this.context = context;
        this.orderDetailList= orderDetailList;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        OrderDetail orderDetail = orderDetailList.get(position);
        db.collection("product").whereEqualTo("productId", orderDetail.getProductId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot doc : value){
                    Product product = doc.toObject(Product.class);
                    holder.productName1.setText(product.getProductName());
                    Picasso.get().load(product.getListImageUrl().get(0)).into(holder.imgProduct1);
                }


            }
        });
        String quant = String.valueOf(orderDetail.getQuantity());
        double total = orderDetail.getQuantity()* Double.parseDouble(orderDetail.getPrice());
        holder.price.setText("₫" + String.valueOf(total));
        holder.quantity.setText("x"+ quant);

    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName1, productName1, quantity, price;
        ImageView imgProduct1;

        public ProductViewHolder(View itemView) {
            super(itemView);
            categoryName1 = itemView.findViewById(R.id.shopName1);
            productName1 = itemView.findViewById(R.id.productName1);
            quantity = itemView.findViewById(R.id.quantity1);
            price = itemView.findViewById(R.id.price1);
            imgProduct1 = itemView.findViewById(R.id.imgProduct1);
        }
    }
}
