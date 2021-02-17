package com.ikarosoft.themission.User;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.ikarosoft.themission.AppLocalDB;

import java.util.List;

public class UserModelSqlClass {


    public final static UserModelSqlClass instance = new UserModelSqlClass();
    public UserModelSqlClass(){

    }

    public interface GetAllUserListener{
        void onComplete(List<User> data);
    }


    public void getAllUser(UserModel.GetAllUserListener listener){
        class MyAsyncTask extends AsyncTask {
            List<User> data;
            @Override
            protected Object doInBackground(Object[] objects) {
                data = AppLocalDB.db.userDao().getAllUser();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(data);

            }
        }
        MyAsyncTask task = new MyAsyncTask();
        task.execute();

      /*  AsyncTask task1 = new AsyncTask() {
            List<User> data;

            @Override
            protected Object doInBackground(Object[] objects) {
                data = AppLocalDB.db.userDao().getAllUser();
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(data);

            }
        }.execute();*/
    }

    public interface AddUserListener{
        void onComlete();
    }
    public void addUser(User user, UserModel.AddUserListener listener){
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
                listener.onComplete();

            }
        }.execute();

    }

    //all function - delete ,update...and more
}
