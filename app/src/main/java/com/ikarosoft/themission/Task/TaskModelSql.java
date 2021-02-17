package com.ikarosoft.themission.Task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MyListener;
import com.ikarosoft.themission.AppLocalDB;


import java.util.List;

public class TaskModelSql {


    public final static TaskModelSql instance = new TaskModelSql();
    public TaskModelSql(){

    }



    public LiveData<List<MyTask>> getAllTask() {
//        @SuppressLint("StaticFieldLeak")
//        AsyncTask task = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                AppLocalDB.db.taskDao().nukeTable();
//                return null;
//            }
//            @Override
//            protected void onPostExecute(Object o) {
//                super.onPostExecute(o);
//
//
//            }
//        }.execute();

        return AppLocalDB.db.taskDao().getAllTask();
    }


    public void addTask(MyTask myTask, ListenerVoid listener){
        @SuppressLint("StaticFieldLeak")
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.taskDao().insertAll(myTask);
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
        Log.d("TAGSQL","ADDDDDDD");

    }


    public void deleteTask(MyTask myTask){
        @SuppressLint("StaticFieldLeak")
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.taskDao().delete(myTask);
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Log.d("TAGDELLLLL","DA:::::::::");



            }
        }.execute();


    }


    //all function - delete ,update...and more
}
