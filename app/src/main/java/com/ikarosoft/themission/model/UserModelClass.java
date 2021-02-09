package com.ikarosoft.themission.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.util.List;

public class UserModelClass {
    public final static UserModelClass instance = new UserModelClass();
    UserModelSqlClass sqlClass = new UserModelSqlClass();
    UserModelFirebaseClass modelfirebaseClass = new UserModelFirebaseClass();


    private UserModelClass(){

    }

    public void getUserByPhone(String phone, GetUserListener listener) {
        modelfirebaseClass.getUserByPhone(phone,listener);
    }


    public interface MListener<T>{
        void onComplete(T result);
    }

    public interface GetAllUserListener extends MListener<List<User>>{};


    public interface GetUserListener{
        void onComplete(User user);
    }

//TODO l;fsfs;
    public void getAllUser(GetAllUserListener listener){
         //sqlClass.getAllUser(listener);
       modelfirebaseClass.getAllUser(listener);

     }

     public interface AddUserListener{
        void onComplete();
     }





    public void addUser(User user,AddUserListener listener){
        modelfirebaseClass.addUser(user,listener);

    }

    interface DeleteListener extends AddUserListener{};

    public void deleteUser(User user,DeleteListener listener){
        modelfirebaseClass.deleteUser(user,listener);
    }
    //all function - delete ,update...and more
}
