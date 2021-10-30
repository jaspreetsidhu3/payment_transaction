package com.example.paymenttransaction.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.paymenttransaction.Fragment.AddFragment;
import com.example.paymenttransaction.Fragment.BankFragment;


public class MyPageViewerAdapter extends FragmentPagerAdapter {
    FragmentManager fragmentManager;
    int size;

    public MyPageViewerAdapter(@NonNull FragmentManager fm, int size) {
        super(fm);
        this.fragmentManager = fm;
        this.size = size;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AddFragment addfragment = new AddFragment();
                return addfragment;
            case 1:
                BankFragment bankfragment = new BankFragment();
                return bankfragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
