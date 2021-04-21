package com.ikarosoft.themission.User;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ikarosoft.themission.AppLocalDB;
import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.Project.MyProject;

import java.util.List;

public class UserModelSql {


    public final static UserModelSql instance = new UserModelSql();
    public UserModelSql(){

    }

    public LiveData<List<User>> getAllUser() {
        return AppLocalDB.db.userDao().getAllUser();

    }

    public void addUser(User user, ListenerVoid listener){
        @SuppressLint("StaticFieldLeak")
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.userDao().insertAll(user);
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener!=null){
                    listener.onComplete();

                }

            }
        }.execute();

    }

    public void deleteAll(){

        @SuppressLint("StaticFieldLeak")
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.userDao().nukeTable();
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

            }
        }.execute();

    }

    //all function - delete ,update...and more
}
