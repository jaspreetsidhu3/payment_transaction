package com.example.paymenttransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.paymenttransaction.Adapter.MyPageViewerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.File;

// Home Screen Activity
public class HomeScreen extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        init_view();
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_account_balance_wallet_24));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_account_balance_24));
        MyPageViewerAdapter myPageViewerAdapter = new MyPageViewerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(myPageViewerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void init_view() {
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuoptions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.developer:
                Toast.makeText(getApplicationContext(), "Developed by Jaspreet Singh", Toast.LENGTH_LONG).show();

                return true;
            case R.id.masterreset:
                final File file = getApplication().getFilesDir();
                final File file1[] = file.listFiles();
                for (int itrfile = 0; itrfile < file1.length; itrfile++) {
                    file1[itrfile].delete();
                }
                SharedPreferences sharedPreferences1 = getApplication().getSharedPreferences("bankbalance", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                editor1.putString("netbalance", "0");
                editor1.apply();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
