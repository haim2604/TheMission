package com.ikarosoft.themission.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.util.List;

public class UserModelClass {
    public final static UserModelClass instance = new UserModelClass();
    private UserModelClass(){

    }

    public interface GetAllUserListener{
        void onComplete(List<User>data);
    }


    public void getAllUser(GetAllUserListener listener){
        class MyAsyncTask extends AsyncTask{
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
    public void addUser(User user,AddUserListener listener){
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
                listener.onComlete();

            }
        }.execute();

    }

    //all function - delete ,update...and more
}
