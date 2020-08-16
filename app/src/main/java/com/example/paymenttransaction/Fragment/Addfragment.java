package com.example.paymenttransaction.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.paymenttransaction.R;

public class Addfragment extends Fragment{
private Button btnproceeds;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.addfragment,null);
        btnproceeds=view.findViewById(R.id.proceed);
        btnproceeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialogFragment addDialogFragment=new AddDialogFragment();
                addDialogFragment.show(getFragmentManager(),"Detail dialog");
            }
        });
        return view;
    }
}
