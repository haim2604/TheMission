package com.ikarosoft.themission.Task;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ikarosoft.themission.AppLocalDB;
import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MyApplication;
import com.ikarosoft.themission.MyListener;
import com.ikarosoft.themission.User.User;
import com.ikarosoft.themission.User.UserModel;

import java.util.LinkedList;
import java.util.List;

public class TaskModel {
    public final static TaskModel instance = new TaskModel();

    TaskModelFirebase modelFirebase = new TaskModelFirebase();
    TaskModelSql modelSql = new TaskModelSql();
    SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
    String myProject;

    private TaskModel() {
    }

    LiveData<List<MyTask>> taskList;

    public LiveData<List<MyTask>> getAllTask() {
        myProject = sp.getString("myProject", "nn");
        taskList = modelSql.getAllTask(myProject);
        refreshAllTask(null);

        return taskList;
    }

    public void refreshAllTask(final ListenerVoid listener) {
        //1.get local last updeat data

        myProject = sp.getString("myProject", "nn");
        Log.d("TAGBACKC", myProject + " => myprojex");

        long lastUpdated = sp.getLong("lastUpdated", 0);

        //2.get all update record from firebase form the last update data
        modelFirebase.getAllTask(myProject,lastUpdated, new MyListener<List<MyTask>>() {
            @Override
            public void onComplete(List<MyTask> result) {

                long lastU = 0;

                //3.insret the new updete to local db
                for (MyTask t : result) {
                    modelSql.addTask(t, null);
                    if (t.getLastUpdated() > lastU) {
                        lastU = t.getLastUpdated();
                    }
                }
                //4.update the local last update date
                sp.edit().putLong("lastUpdated", lastU).commit();

                //5.return the update data to the listeners
                if (listener != null) {
                    listener.onComplete();
                }

            }
        });


    }

    public void getTaskByPhone(String phone, MyListener<MyTask> listener) {
        modelFirebase.getTaskByPhone(phone, listener);
    }

    public void addTask(MyTask myTask, ListenerVoid listener) {
        modelFirebase.addTask(myTask, new ListenerVoid() {
            @Override
            public void onComplete() {
                refreshAllTask(new ListenerVoid() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
    }

    public void deleteTask(MyTask myTask, ListenerVoid listener) {
        modelSql.deleteTask(myTask, new ListenerVoid() {
            @Override
            public void onComplete() {
                modelFirebase.deleteTask(myTask, listener);

            }
        });
    }

    public void updateTask(MyTask myTask, ListenerVoid listener) {
        modelSql.addTask(myTask, new ListenerVoid() {
            @Override
            public void onComplete() {
                modelFirebase.updateTask(myTask, listener);

            }
        });
    }



    public void uploadImage(Bitmap bitmap, String name, final MyListener<String> listener) {
        modelFirebase.uploadImage(bitmap, name, listener);
    }


    public void deleteall() {
        modelSql.deleteAll();
    }



}
