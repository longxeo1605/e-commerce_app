package com.example.myapplication.models;

import static android.content.ContentValues.TAG;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.OrderDetailScreen;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    List<Orders>ordersList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    public OrderAdapter(Context context, List<Orders>ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.order, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        Orders orders = ordersList.get(position);
        List<OrderDetail> orderDetail = new ArrayList<>();
        db.collection("order_detail").whereEqualTo("orderId", orders.getOrderId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<OrderDetail> details = new ArrayList<>();
                for(QueryDocumentSnapshot doc: value) {
                    OrderDetail detail = doc.toObject(OrderDetail.class);
                    details.add(detail);
                }
                int total = 0;
                for (OrderDetail detail : details){
                    total+=detail.getQuantity()*Integer.parseInt(detail.getPrice());
                }
                String quant = String.valueOf(details.get(0).getQuantity());
                holder.quantity.setText("x"+quant);
                holder.price.setText("₫"+String.valueOf(total));

                db.collection("product").document((details.get(0)).getProductId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Product product = documentSnapshot.toObject(Product.class);
                        holder.productName.setText(product.getProductName());
                        db.collection("user_info").document(product.getuId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                holder.shopName.setText(documentSnapshot.get("displayName").toString());
                            }
                        });
                        Picasso.get().load(product.getListImageUrl().get(0)).into(holder.imgorders);
                    }
                });
            }
        });

        List<DSDetail> temp = new ArrayList<>();

        holder.tvOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailScreen.class);
                intent.putExtra("order", orders);
                startActivity(v.getContext(), intent, null);
            }
        });
        db.collection("ds_detail").whereEqualTo("orderId", orders.getOrderId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for( QueryDocumentSnapshot doc: value){
                    Log.d(TAG, "QueryDocumentSnapshot data: " + doc.getData());
                    DSDetail detail = doc.toObject(DSDetail.class);
                    temp.add(detail);
                    System.out.println(detail.toString());

                }
                Collections.sort(temp, new Comparator<DSDetail>() {
                    @Override
                    public int compare(DSDetail o1, DSDetail o2) {
                        return o2.getDateOfStatus().compareTo(o1.getDateOfStatus());
                    }
                });
                if(temp.get(0).getStatusId().equals("9BhGe5u1EvCUHqzxRrBk")){//cho xac nhan
                    holder.btnCancel.setVisibility(View.VISIBLE);
                    holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DSDetail detail = new DSDetail();
                            detail.setDateOfStatus( new Date());
                            detail.setOrderId(orders.getOrderId());
                            detail.setStatusId("ib6FdfszP8LIHH8MHoLU");
                            db.collection("ds_detail").add(detail).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    ordersList.remove(orders);
                                    notifyDataSetChanged();
                                }
                            });
                        }
                    });
                    holder.tvStatus.setText("Đơn hàng đang chờ xác nhận");
                }else if(temp.get(0).getStatusId().equals("ib6FdfszP8LIHH8MHoLU")){ // da huy
                    holder.btnNextUp.setVisibility(View.GONE);
                    holder.tvStatus.setText("Đơn hàng đã bị hủy");
                }else if(temp.get(0).getStatusId().equals("mxmP6sjcDdDsrrakSJs9")){//dagiao
                    holder.btnNextUp.setText("Đánh giá");
                    holder.tvStatus.setText("Đơn hàng đã giao thành công");
                }else {
                    holder.tvStatus.setText("Đơn hàng đang trên đường đến bạn");
                }
                holder.btnNextUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DSDetail detail = new DSDetail();
                        detail.setDateOfStatus( new Date());
                        detail.setOrderId(orders.getOrderId());
                        if(temp.get(0).getStatusId().equals("9BhGe5u1EvCUHqzxRrBk")){
                            detail.setStatusId("jZCQPKfr8ngb8nSXrbxG");
                        }else if(temp.get(0).getStatusId().equals("jZCQPKfr8ngb8nSXrbxG")){
                            detail.setStatusId("mxmP6sjcDdDsrrakSJs9");
                        }
                        db.collection("ds_detail").add(detail).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                ordersList.remove(orders);
                                notifyDataSetChanged();
                            }
                        });

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView  price, productName, shopName, quantity, tvStatus, tvOrderDetail;
        ImageView imgorders;
        Button btnNextUp,btnCancel;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.price);
            productName = itemView.findViewById(R.id.productName);
            shopName = itemView.findViewById(R.id.shopName);
            quantity = itemView.findViewById(R.id.quantity);
            imgorders = itemView.findViewById(R.id.imgProduct);
            btnNextUp = itemView.findViewById(R.id.btNextUp);
            btnCancel = itemView.findViewById(R.id.btCancel);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvOrderDetail = itemView.findViewById(R.id.tvOrderDetail);
        }

    }


}
