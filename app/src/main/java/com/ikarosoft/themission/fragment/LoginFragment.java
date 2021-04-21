package com.ikarosoft.themission.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.ikarosoft.themission.MyApplication;
import com.ikarosoft.themission.MyListener;
import com.ikarosoft.themission.R;
import com.ikarosoft.themission.User.User;
import com.ikarosoft.themission.User.UserModel;


public class LoginFragment extends Fragment {

    EditText phone, pass;
    View view;
    SharedPreferences sp;
    String myPhone;
    Button newUserBtn, connectBtn;
    ProgressDialog progressDialog;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        connectBtn = view.findViewById(R.id.login_btn_connect);
        newUserBtn = view.findViewById(R.id.login_btn_new);
        phone = view.findViewById(R.id.login_username_et);
        pass = view.findViewById(R.id.login_pas_et);
        sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect();
            }
        });

        newUserBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_login_to_newUser));

        return view;
    }

    private void connect() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("connect...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        User tempUser = new User();
        tempUser.setPhone(phone.getText().toString());
        tempUser.setPassword(pass.getText().toString());
        UserModel.instance.signIn(tempUser, new MyListener<Boolean>() {
            @Override
            public void onComplete(Boolean result) {
                progressDialog.dismiss();

                if (result) {
                    reload();
                } else {
                    phone.getText().clear();
                    pass.getText().clear();
                    phone.setError("phone or password is incorrect");

                }
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();

        myPhone = sp.getString("myPhone", "nn");

//        TaskModel.instance.deleteall();
//        ProjectModel.instance.deleteall();
//       UserModel.instance.deleteAll();

        if (!myPhone.equals("nn")) {
            reload();
        }

    }


    private void reload() {
        Navigation.findNavController(view).navigate(R.id.action_logint_to_allProj);
    }
}