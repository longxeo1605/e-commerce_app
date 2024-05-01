package com.example.myapplication.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Fragment.ChoXacNhanFragment;
import com.example.myapplication.Fragment.DaGiaoFragment;
import com.example.myapplication.Fragment.DaHuyFragment;
import com.example.myapplication.Fragment.DangGiaoFragment;
import com.example.myapplication.R;
import com.example.myapplication.models.OrderAdapter;
import com.example.myapplication.models.Product;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;

    List<Product>productList;
    OrderAdapter myAdapter;



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




        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                if(tab.getPosition() == 0){
                    fragment = new ChoXacNhanFragment();

                }else if(tab.getPosition() == 1){
                    fragment = new DangGiaoFragment();
                }else if(tab.getPosition() == 2){
                    fragment = new DaGiaoFragment();
                }else {
                    fragment = new DaHuyFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fgContent, fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTabLayout.getTabAt(1).select();
        mTabLayout.getTabAt(0).select();

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}
