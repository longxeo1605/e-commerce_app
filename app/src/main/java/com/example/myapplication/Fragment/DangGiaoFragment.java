package com.example.myapplication.Fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.DSDetail;
import com.example.myapplication.models.OrderAdapter;
import com.example.myapplication.models.Orders;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class DangGiaoFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Orders> orderList = new ArrayList<>();
    //    List<DSDetail> orderDetailList = new ArrayList<>();
    OrderAdapter myAdapter;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myAdapter = new OrderAdapter(getContext(), orderList);
        View view = inflater.inflate(R.layout.fragment_cho_xac_nhan, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getOrdersList();
        return view;
    }
    public void getOrdersList(){
        db.collection("orders").whereEqualTo("uid", "current").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                orderList.clear();
                for (QueryDocumentSnapshot doc: value){
                    System.out.println(doc.getData());
                    Orders orders = new Orders();
                    orders.setOrderId(doc.getData().get("orderId").toString());
                    orders.setUid(doc.getData().get("uid").toString());
                    orders.setAddress(doc.getData().get("address").toString());
                    Timestamp timestamp = doc.getTimestamp("createDate");
                    if (timestamp != null) {
                        // Chuyển đổi Timestamp thành java.util.Date
                        Date date = timestamp.toDate();
                        orders.setCreateDate(date);
//                    System.out.println("add");
                        orderList.add(orders);
                        System.out.println(orders.toString());
                    }
                }
                myAdapter.notifyDataSetChanged();
                getOrderPending();
            }
        });
    }
    public void getOrderPending(){
        for (Orders order : orderList){
            System.out.println("get ds_detail order id: " + order.getOrderId());
            List<DSDetail> temp = new ArrayList<>();
            db.collection("ds_detail").whereEqualTo("orderId", order.getOrderId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                    if(!temp.get(0).getStatusId().equals("jZCQPKfr8ngb8nSXrbxG")){
                        orderList.remove(order);
                    }
                }
            });
        }
        myAdapter.notifyDataSetChanged();
    }
}