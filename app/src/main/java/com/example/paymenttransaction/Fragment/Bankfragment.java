package com.example.paymenttransaction.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.paymenttransaction.DetailActivity;
import com.example.paymenttransaction.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Bankfragment extends Fragment {
    private TextView textbankamount;
    private ListView listView;
    private ArrayList arrayList;
    private ArrayList filelinks;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = LayoutInflater.from(getContext()).inflate(R.layout.bankfragment, null);
        init();
        creating_sharedpreference();
        final File file = getContext().getFilesDir();
        final File file1[] = file.listFiles();
        filelinks = new ArrayList();

        arrayList = new ArrayList();
        for (int files_itr = file1.length - 1; files_itr >= 0; files_itr--) {
            try {
                arrayList.add(file1[files_itr].getCanonicalFile().toString().substring(file1[files_itr].toString().lastIndexOf("/") - 1));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        for (int files_itr = file1.length - 1; files_itr >= 0; files_itr--) {

            filelinks.add(file1[files_itr].toString());


        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("detail", filelinks.get(position).toString());

                startActivity(intent);
            }
        });

        return view;
    }

    public void init() {
        textbankamount = view.findViewById(R.id.bankamount);
        listView = view.findViewById(R.id.recylerview);
    }

    public void creating_sharedpreference() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("bankbalance", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String netamount = sharedPreferences.getString("netbalance", "-1");
        assert netamount != null;
        if (netamount.equals("-1")) {
            editor.putString("netbalance", "0");
            textbankamount.setText(netamount);

        } else {
            textbankamount.setText(netamount);
        }

        editor.apply();
    }


}
