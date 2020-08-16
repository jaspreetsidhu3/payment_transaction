package com.example.paymenttransaction.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


import com.example.paymenttransaction.R;

import java.io.File;

public class AddDialogFragment extends AppCompatDialogFragment {
    private TextView textamount;
    AlertDialog.Builder builder;
    View view;
    private String txtamount;
    public AddDialogFragment(String textamount) {
        txtamount=textamount;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
         builder = new AlertDialog.Builder(getContext());
         view = LayoutInflater.from(getContext()).inflate(R.layout.adddialogfragment, null);
            creating_dialogview();
            textamount=view.findViewById(R.id.passamount);
            textamount.setText(txtamount);
         return builder.create();
    }
    public void creating_dialogview(){
        builder.setView(view)
                .setTitle("Fill transaction details")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences=getContext().getSharedPreferences("id_generator", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        String key=sharedPreferences.getString("file_created","-1");
                        assert key != null;
                        if(key.equals("-1")){
                            Log.d("test","-1");
                        }
                        else{
                            int count=Integer.parseInt(key);
                            Toast.makeText(getContext(),""+count,Toast.LENGTH_SHORT).show();
                            editor.putString("file_created",String.valueOf(++count));
                            editor.apply();
                        }

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
}

