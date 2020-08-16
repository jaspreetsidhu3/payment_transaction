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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


import com.example.paymenttransaction.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddDialogFragment extends AppCompatDialogFragment {
    private TextView textamount;
    private RadioButton radioPay;
    private RadioButton radioRecieve;
    private EditText edittofrom;
    private EditText edittitle;
    private EditText editdescription;
    int count;
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
            init();
         creating_dialogview();

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
                            count=Integer.parseInt(key);
                            Toast.makeText(getContext(),""+count,Toast.LENGTH_SHORT).show();
                            editor.putString("file_created",String.valueOf(++count));
                            editor.apply();
                        }
                        String paymenttype=null;
                        if(radioPay.isChecked()){
                            paymenttype="Pay";
                        }
                        if(radioRecieve.isChecked()){
                            paymenttype="Recieved";
                        }
                        String tofrom=edittofrom.getText().toString();
                        String title=edittitle.getText().toString();
                        String desc=editdescription.getText().toString();

                        FileOutputStream fileOutputStream= null;
                        try {
                            fileOutputStream = getContext().openFileOutput(count+".txt", Context.MODE_PRIVATE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        String data="Title = "+title +"\nAmount paid = "+txtamount+"\nto/from = "+tofrom+"\n description = "+desc;
                        try {
                            assert fileOutputStream != null;
                            fileOutputStream.write(data.getBytes());
                            Toast.makeText(getContext(),""+data,Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
    public void init(){
        textamount=view.findViewById(R.id.passamount);
        radioPay=view.findViewById(R.id.pay);
        radioRecieve=view.findViewById(R.id.recieve);
        edittofrom=view.findViewById(R.id.tofrom);
        edittitle=view.findViewById(R.id.wanttoaddtitle);
        editdescription=view.findViewById(R.id.wanttoadddescription);
    }
}

