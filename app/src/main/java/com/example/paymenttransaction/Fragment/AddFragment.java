package com.example.paymenttransaction.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.paymenttransaction.R;

public class AddFragment extends Fragment {
    private Button btnProceeds;
    private EditText edtAmount;
    static int count = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.addfragment, null);
        btnProceeds = view.findViewById(R.id.proceed);
        edtAmount = view.findViewById(R.id.fillamount);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("id_generator", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String key = sharedPreferences.getString("file_created", "-1");
        assert key != null;
        if (key.equals("-1")) {
            editor.putString("file_created", "-0");
        } else {
            Log.d("test", "test");
        }
        editor.apply();
        btnProceeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtAmount.getText())) {
                    Toast.makeText(getContext(), "Please fill amount for further proceeder", Toast.LENGTH_SHORT).show();
                } else {
                    AddDialogFragment addDialogFragment = new AddDialogFragment(edtAmount.getText().toString());
                    addDialogFragment.show(getFragmentManager(), "Detail dialog");
                }
            }
        });
        return view;
    }
}
