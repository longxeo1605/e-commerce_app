package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;
    private ViewPageAdapter mViewPageAdapter;


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
}