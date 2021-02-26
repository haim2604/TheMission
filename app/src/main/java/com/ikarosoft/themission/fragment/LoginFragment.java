package com.ikarosoft.themission.fragment;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MyApplication;
import com.ikarosoft.themission.fragment.LoginFragmentDirections;
import com.ikarosoft.themission.MyListener;
import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.Task.TaskModel;
import com.ikarosoft.themission.User.User;
import com.ikarosoft.themission.User.UserModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;


public class LoginFragment extends Fragment {

    String myUser="0506666222",myPass="123456";
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences ref;
    List<User> userList;
    private FirebaseAuth mAuth;
    EditText user,pass;
    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button connectBtn = view.findViewById(R.id.login_btn_connect);
        Button newUserBtn = view.findViewById(R.id.login_btn_new);
        user = view.findViewById(R.id.login_username_et);
        pass = view.findViewById(R.id.login_pas_et);

/*
        User us= new User();
        us.setPassword("123456789");
        us.setPhone("02225123456789");
        UserModel.instance.addUser(us, new MyListener<FirebaseUser>() {
            @Override
            public void onComplete(FirebaseUser result) {
                Log.d("TAGL", "createUserWithEmail:success");
            }
        });*/

//        FirebaseUser userfb = FirebaseAuth.getInstance().getCurrentUser();
//
//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setDisplayName("Jane Q. User")
//                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
//                .build();
//
//        userfb.updateProfile(profileUpdates)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("TAG", "User profile updated.");
//                        }
//                    }
//                });



        // logout();

        //addUser(myUser,myPass);


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


//        UserModel.instance.getAllUser(new MyListener<List<User>>() {
//            @Override
//            public void onComplete(List<User> result) {
//                userList= result;
//                for (User us:userList ) {
//                    Log.d("TAGRoom...d",us.getName()+" "+us.getPassword());
//                    Log.i("TAGRoom...i",us.getName()+" "+us.getPassword());
//                    Log.v("TAGRoom...v",us.getName()+" "+us.getPassword());
//                }
//            }
//        });



//        UserModelClass.instance.getAllUser(new UserModelClass.GetAllUserListener() {
//            @Override
//            public void onComplete(List<User> data) {
//                userList= data;
//                for (User us:userList ) {
//                    Log.d("TAGRoom",us.getName()+" "+us.getPassword());
//                }
//
//            }
//        });


//        MyTask myTask = new MyTask("add","start","0501","26");
//
//
//        TaskModel.instance.addTask(myTask, new ListenerVoid() {
//            @Override
//            public void onComplete() {
//                    Log.d("TAGTaskADD","addddddd   ");
//            }
//        });



/*
        User user = new User();
        user.setName("nam4");
        user.setImageUrl("urll34l");
        user.setPassword("passsswppoooooo");
        user.setPhone("054123456");
        UserModel.instance.addUser(user, new UserModel.AddUserListener() {
            @Override
            public void onComplete() {
                Log.d("TAGAD","addddddd   ");
            }
        });

        UserModel.instance.getUserByPhone("054123456" , new UserModel.GetUserListener() {
            @Override
            public void onComplete(User user) {
                Log.d("TAGUSERRR",user.getName());
            }
        }) ;*/



        //end usermodelclase save local in room


        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn(user.getText().toString(), pass.getText().toString());

                String idd = "12346";


                LoginFragmentDirections.ActionLoginToAllMission action = LoginFragmentDirections.actionLoginToAllMission(idd,"idd",new MyTask("bb","cc","566","456"));
                //action pas in navigate
                Navigation.findNavController(view).navigate(action);
            }
        });

        newUserBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_login_to_newUser));

       

        return  view;
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Log.d("TAGLOGINPhone","signIn");


    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Log.d("TAGLOGIN","Logout");

    }

    private void addUser(String username, String password) {
                username = username+"@mission.com";

               mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("TAGLOGIN","add user");

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });



    }




        private void signIn(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                            // ...
                        }

                        // ...
                    }
                });

    }

    private void updateUI(FirebaseUser user) {
        Log.d("TAGLOGIN","siginin");


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        Log.d("TAGLOGIN","on start");

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload(currentUser);
        }
    }

    private void reload(FirebaseUser currentUser) {
        Log.d("TAGLOGIN","Conecet reload   " + currentUser.getUid());
    }
}