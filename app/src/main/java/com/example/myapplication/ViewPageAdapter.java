package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPageAdapter extends FragmentStateAdapter {
    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ChoXacNhanFragment();
            case 1:
                return new DangGiaoFragment();
            case 2:
                return new DaGiaoFragment();
            case 3:
                return new DaHuyFragment();
            default:
                return new DaGiaoFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
