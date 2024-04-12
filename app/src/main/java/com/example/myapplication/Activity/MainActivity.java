package com.example.myapplication.Activity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.models.MyAdapter;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.ViewPageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;
    private ViewPageAdapter mViewPageAdapter;

    RecyclerView recyclerView;
    List<Product>productList;
    MyAdapter myAdapter;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageView icon_left = findViewById(R.id.icon_left);
        ImageView icon_searching = findViewById(R.id.icon_searching);
        ImageView icon_right = findViewById(R.id.icon_right);
        TextView toolbar_text = findViewById(R.id.toolbar_text);
        mTabLayout = findViewById(R.id.tab_layout);
//        mViewPager = findViewById(R.id.vp2);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
        });



        db = FirebaseFirestore.getInstance();
        productList = new ArrayList<>();
        myAdapter = new MyAdapter(MainActivity.this, productList);
        recyclerView.setAdapter(myAdapter);
        
        EventChangeListener();


        icon_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Back", Toast.LENGTH_SHORT).show();
            }
        });

        icon_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Information", Toast.LENGTH_SHORT).show();
            }
        });

        icon_searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Searching", Toast.LENGTH_SHORT).show();
            }
        });

//        mViewPageAdapter = new ViewPageAdapter(this);
//        mViewPager.setAdapter(mViewPageAdapter);
//
//        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
//            switch (position){
//                case 0:
//                    tab.setText("Cho Xac Nhan");
//                    break;
//                case 1:
//                    tab.setText("Dang Giao");
//                    break;
//                case 2:
//                    tab.setText("Da Giao");
//                    break;
//                case 3:
//                    tab.setText("Da Huy");
//                    break;
//            }
//        }).attach();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    private void EventChangeListener() {

        db.collection("product")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null ){
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                            for(DocumentChange dc : value.getDocumentChanges()){
                                if(dc.getType() == DocumentChange.Type.ADDED){
                                    productList.add(dc.getDocument().toObject(Product.class));
                                }


                                myAdapter.notifyDataSetChanged();
                            }


                    }
                });




    }

    private void getStatus() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Lấy dữ liệu từ bảng orders
        db.collection("orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String orderId = document.getId();
                                // Lấy dữ liệu từ bảng ds_detail với orderId tương ứng
                                db.collection("ds_detail")
                                        .whereEqualTo("orderId", orderId)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot dsDetailDocument : task.getResult()) {
                                                        String deliveryId = dsDetailDocument.getString("deliveryId");
                                                        // Lấy dữ liệu từ bảng delivery_status với deliveryId tương ứng
                                                        db.collection("delivery_status")
                                                                .document(deliveryId)
                                                                .get()
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot deliveryStatusDocument) {
                                                                        if (deliveryStatusDocument.exists()) {
                                                                            Log.d(TAG, "Delivery Status: " + deliveryStatusDocument.getData());
                                                                        } else {
                                                                            Log.d(TAG, "No such document");
                                                                        }
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Log.w(TAG, "Error getting documents.", e);
                                                                    }
                                                                });
                                                    }
                                                } else {
                                                    Log.w(TAG, "Error getting documents.", task.getException());
                                                }
                                            }
                                        });
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


}
