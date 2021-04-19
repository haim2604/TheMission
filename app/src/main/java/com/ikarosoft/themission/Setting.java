package com.ikarosoft.themission;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Setting extends Fragment {

    View view;
    Button logout, back;
    TextView detail;
    String myPhone, myName;
    SharedPreferences sp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        logout = view.findViewById(R.id.setting_btn_logout);
        back = view.findViewById(R.id.setting_btn_back);
        detail = view.findViewById(R.id.setting_tv_detail);
        sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);

        myPhone =sp.getString("myPhone", "nn");
        myName = sp.getString("myName", "nn");
        detail.setText(myPhone+" - "+myName);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();


            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString("myPhone", "nn").commit();
                Navigation.findNavController(view).navigate(R.id.action_setting_to_login);

            }
        });



        return view;
    }
}