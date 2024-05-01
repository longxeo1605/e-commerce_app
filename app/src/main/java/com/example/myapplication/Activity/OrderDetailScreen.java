package com.example.myapplication.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.DSDetail;
import com.example.myapplication.models.DeliveryStatus;
import com.example.myapplication.models.OrderDetail;
import com.example.myapplication.models.Orders;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.ProductAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderDetailScreen extends AppCompatActivity {

    TextView edtStatus, edtshopName, edtTotal;
    RecyclerView listOrderDetail;
    private Orders order;
    ProductAdapter adapter;
    private List<OrderDetail> orderDetailList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_detail_screen);
        setControl();
        setEvent();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setControl(){
        edtStatus = findViewById(R.id.statusName);
        edtshopName = findViewById(R.id.shopName1);
        edtTotal = findViewById(R.id.totalPrice1);
        listOrderDetail = findViewById(R.id.allProduct);
        Intent intent = getIntent();
        order = (Orders) intent.getSerializableExtra("order");
    }

    private void setEvent(){

        db.collection("order_detail").whereEqualTo("orderId",order.getOrderId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                orderDetailList.clear();
                for(QueryDocumentSnapshot doc : value){
                    OrderDetail detail = doc.toObject(OrderDetail.class);
                    orderDetailList.add(detail);
                }
                adapter.notifyDataSetChanged();
                db.collection("product").whereEqualTo("productId", orderDetailList.get(0).getProductId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(QueryDocumentSnapshot doc : value){
                            Product product = doc.toObject(Product.class);
                            db.collection("user_info").document(product.getuId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    edtshopName.setText(documentSnapshot.get("displayName").toString());
                                }
                            });
                        }
                    }
                });
            }
        });
        db.collection("ds_detail").whereEqualTo("orderId", order.getOrderId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<DSDetail> temp = new ArrayList<>();
                for( QueryDocumentSnapshot doc: value){
                    DSDetail detail = doc.toObject(DSDetail.class);
                    temp.add(detail);

                }
                Collections.sort(temp, new Comparator<DSDetail>() {
                    @Override
                    public int compare(DSDetail o1, DSDetail o2) {
                        return o2.getDateOfStatus().compareTo(o1.getDateOfStatus());
                    }
                });
                //status name
                db.collection("delivery_status").document(temp.get(0).getStatusId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        assert value != null;
                        DeliveryStatus ds = value.toObject(DeliveryStatus.class);
                        edtStatus.setText("Đơn hàng "+ds.getStatusName());
                    }
                });
                //shop name

            }

        });

        adapter = new ProductAdapter(this, orderDetailList);
        listOrderDetail.setAdapter(adapter);
        listOrderDetail.setLayoutManager(new LinearLayoutManager(this));
    }
}