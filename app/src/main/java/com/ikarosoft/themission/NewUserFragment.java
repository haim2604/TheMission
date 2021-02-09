package com.ikarosoft.themission;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


public class NewUserFragment extends Fragment {
    Button addBtn;
    ImageView avatar;
    EditText name,phone,passAgain,password;
    ImageButton tackPic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);
        addBtn = view.findViewById(R.id.newuser_btn_adduser);
        tackPic = view.findViewById(R.id.newuser_tackpic_ib);
        avatar = view.findViewById(R.id.newuser_pic_iv);
        name= view.findViewById(R.id.newuser_name_et);
        phone = view.findViewById(R.id.newuser_phone_et);
        passAgain = view.findViewById(R.id.newuser_confirmpass_et);
        password = view.findViewById(R.id.newuser_pass_et);

        //tackPic.bringToFront();

        tackPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                tackPicture();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                saveUser(view);
            }
        });

        return  view;
    }

    private void saveUser(View view) {
        Navigation.findNavController(view).popBackStack();

    }

    private void tackPicture() {
        Log.d("TAGADDDD"," ad       dadad");

    }
}