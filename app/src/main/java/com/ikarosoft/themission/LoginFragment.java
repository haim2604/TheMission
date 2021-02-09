package com.ikarosoft.themission;

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

import com.ikarosoft.themission.model.MyTask;
import com.ikarosoft.themission.model.User;
import com.ikarosoft.themission.model.UserModelClass;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class LoginFragment extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences ref;
    List<User> userList;

    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button connectBtn = view.findViewById(R.id.login_btn_connect);
        Button newUserBtn = view.findViewById(R.id.login_btn_new);



        // save data local
        ref = getContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                 //red
        String tt=ref.getString("test1","default value or empty");
        Log.d("TagSave",tt+"");
                //write
        SharedPreferences.Editor ed =ref.edit();
        ed.putString("test1","test");
        ed.commit();
        //end Shared Preferences

        //start usermodelclase save local in room

        UserModelClass.instance.getAllUser(new UserModelClass.GetAllUserListener() {
            @Override
            public void onComplete(List<User> data) {
                userList= data;
                for (User us:userList ) {
                    Log.d("TAGRoom",us.getName()+" "+us.getPassword());
                }

            }
        });

        User user = new User();
        user.setName("nam4");
        user.setImageUrl("urll34l");
        user.setPassword("passsswppoooooo");
        user.setPhone("054123456");
        UserModelClass.instance.addUser(user, new UserModelClass.AddUserListener() {
            @Override
            public void onComplete() {
                Log.d("TAGAD","addddddd   ");
            }
        });

        UserModelClass.instance.getUserByPhone("054123456" , new UserModelClass.GetUserListener() {
            @Override
            public void onComplete(User user) {
                Log.d("TAGUSERRR",user.getName());
            }
        }) ;



        //end usermodelclase save local in room


        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String idd = "12346";
                LoginFragmentDirections.ActionLoginToAllMission action = LoginFragmentDirections.actionLoginToAllMission(idd,"idd",new MyTask("aa","bb","cc"));
                //action pas in navigate
                Navigation.findNavController(view).navigate(action);
            }
        });

        newUserBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_login_to_newUser));

       

        return  view;
    }
}