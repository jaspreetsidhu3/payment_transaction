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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddDialogFragment extends AppCompatDialogFragment {
    private TextView textAmount;
    private RadioButton radioPay;
    private RadioButton radioReceive;
    private RadioButton radioMasterChange;
    private EditText edittofrom;
    private EditText edittitle;
    private EditText editDescription;
    String formattedDate;
    int count;
    int payment_type = 0;
    AlertDialog.Builder builder;
    View view;
    private String txtAmount;

    public AddDialogFragment(String textAmount) {
        txtAmount = textAmount;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getContext());
        view = LayoutInflater.from(getContext()).inflate(R.layout.adddialogfragment, null);
        init();
        creatingDialogView();
        textAmount.setText(txtAmount);
        return builder.create();
    }

    public void creatingDialogView() {
        builder.setView(view)
                .setTitle("Fill transaction details")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!(radioPay.isChecked() || radioReceive.isChecked() || radioMasterChange.isChecked())) {
                            Toast.makeText(getContext(), "Please select operation type", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("id_generator", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String key = sharedPreferences.getString("file_created", "-1");
                            assert key != null;
                            if (key.equals("-1")) {
                                Log.d("test", "-1");
                            } else {
                                count = Integer.parseInt(key);
                                editor.putString("file_created", String.valueOf(++count));
                                editor.apply();
                            }
                            String paymenttype = null;
                            if (radioPay.isChecked()) {
                                paymenttype = "Paid";
                                payment_type = 1;
                            }
                            if (radioReceive.isChecked()) {
                                paymenttype = "Recieved";
                                payment_type = 2;
                            }
                            if (radioMasterChange.isChecked()) {
                                paymenttype = "Master Change";
                                payment_type = 3;
                            }
                            String tofrom = edittofrom.getText().toString();
                            String title = edittitle.getText().toString();
                            String desc = editDescription.getText().toString();
                            int result = on_net_balance();
                            if (payment_type == 0 && result == 0) {
                                Toast.makeText(getContext(), "Payment type not mentioned", Toast.LENGTH_SHORT).show();
                            }

                            if (payment_type != 0 && result == 1) {
                                FileOutputStream fileOutputStream = null;
                                try {
                                    Date c = Calendar.getInstance().getTime();
                                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                                    formattedDate = df.format(c);
                                    fileOutputStream = getContext().openFileOutput(formattedDate + " ->  " + paymenttype + " " + txtAmount + "rs.txt", Context.MODE_PRIVATE);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                String data = "On data = " + formattedDate + "\nAmount " + paymenttype + " = " + txtAmount + "\nto/from = " + tofrom + "\n\nTitle = " + title + "\nDescription = " + desc;
                                try {
                                    assert fileOutputStream != null;
                                    fileOutputStream.write(data.getBytes());
                                    Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }

    private int on_net_balance() {
        int sucess = -1;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("bankbalance", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String net_amount = sharedPreferences.getString("netbalance", "-1");
        assert net_amount != null;
        if (net_amount.equals("-1")) {
            Toast.makeText(getContext(), "something went wrong please restart", Toast.LENGTH_SHORT).show();
            sucess = 0;
        } else {
            double net_calculation = Double.parseDouble(net_amount);
            double useramount = Double.parseDouble(txtAmount);
            if (payment_type == 1) {
                net_calculation = net_calculation - useramount;
                if (net_calculation < 0) {
                    Toast.makeText(getContext(), "Not enough balance you have", Toast.LENGTH_SHORT).show();
                    sucess = 0;
                } else {
                    editor.putString("netbalance", "" + net_calculation);
                    editor.apply();
                    sucess = 1;
                }
            }
            if (payment_type == 2) {
                net_calculation = net_calculation + useramount;
                editor.putString("netbalance", "" + net_calculation);
                editor.apply();
                sucess = 1;
            }
            if (payment_type == 3) {
                net_calculation = useramount;
                editor.putString("netbalance", "" + net_calculation);
                editor.apply();
                sucess = 1;
            }
        }
        if (sucess == 1) {
            return 1;
        } else {
            return 0;
        }

    }

    public void init() {
        textAmount = view.findViewById(R.id.passamount);
        radioPay = view.findViewById(R.id.pay);
        radioReceive = view.findViewById(R.id.recieve);
        edittofrom = view.findViewById(R.id.tofrom);
        edittitle = view.findViewById(R.id.wanttoaddtitle);
        editDescription = view.findViewById(R.id.wanttoadddescription);
        radioMasterChange = view.findViewById(R.id.masteredit);
    }
}

