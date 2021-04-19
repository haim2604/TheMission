package com.ikarosoft.themission.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseUser;

import com.ikarosoft.themission.MyApplication;
import com.ikarosoft.themission.MyListener;
import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.User.User;
import com.ikarosoft.themission.User.UserModel;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class LoginFragment extends Fragment {

    String myUser = "0506666222", myPass = "123456";
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences ref;
    List<User> userList;
    EditText phone, pass;
    View view;
    SharedPreferences sp ;
    String myPhone;
    Button newUserBtn,connectBtn;

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
        User tempUser = new User();
        tempUser.setPhone(phone.getText().toString());
        tempUser.setPassword(pass.getText().toString());
        UserModel.instance.signIn(tempUser, new MyListener<FirebaseUser>() {
            @Override
            public void onComplete(FirebaseUser result) {
                if (result != null) {
                    reload();
                }
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        sp.edit().putString("myPhone", "nn").commit();

        myPhone = sp.getString("myPhone", "nn");
        if (!myPhone.equals("nn")){
            reload();
        }

    }


    private void reload() {

       // sp.edit().putString("myPhone", myphone [0]).commit();

        Navigation.findNavController(view).navigate(R.id.action_logint_to_allProj);
    }
}