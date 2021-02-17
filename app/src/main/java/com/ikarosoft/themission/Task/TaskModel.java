package com.ikarosoft.themission.Task;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

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

    TaskModelFirebase modelFirebase =  new TaskModelFirebase();
    TaskModelSql modelSql = new TaskModelSql();

    private TaskModel(){
    }

//    public void getAllTask(MyListener<List<MyTask>> listener){
//       // modelFirebase.getAllTask(listener);
//        modelSql.getAllTask(listener);
//    }
    LiveData<List<MyTask>> taskList ;

    public LiveData<List<MyTask>> getAllTask(){
        if (taskList== null){
            taskList = modelSql.getAllTask();


            refreshAllTask(null);
        }
        // modelSql.getAllTask(listener);
        return  taskList;
    }

    public void refreshAllTask(final ListenerVoid listener){
        //1.get local last updeat data

        SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdated",0);

        //2.get all update record from firebase form the last update data
        modelFirebase.getAllTask(lastUpdated,new MyListener<List<MyTask>>() {
            @Override
            public void onComplete(List<MyTask> result) {

                long lastU = 0;

                //3.insret the new updete to local db
                for (MyTask t:result) {
                modelSql.addTask(t,null);
                    if(t.getLastUpdated()>lastU){
                        lastU=t.getLastUpdated();
                    }
                }
               //4.update the local last update date
                sp.edit().putLong("lastUpdated", lastU).commit();

                //5.return the update data to the listeners
                if(listener != null){
                    listener.onComplete();
                }

            }
        });







//        modelFirebase.getAllTask(new MyListener<List<MyTask>>() {
//            @Override
//            public void onComplete(List<MyTask> result) {
//                taskList.setValue(result);
//                listener.onComplete(null);
//            }
//        });

        // modelSql.getAllTask(listener);

    }

    public void getTaskByPhone(String phone, MyListener<MyTask> listener) {
        modelFirebase.getTaskByPhone(phone,listener);
    }

    public void addTask(MyTask myTask, ListenerVoid listener){
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
        //modelSql.addTask(myTask,listener);
    }

    public void deleteUser(MyTask myTask, ListenerVoid listener){
        modelFirebase.deleteUser(myTask, listener);
    }

    public  void uploadImage(Bitmap bitmap, String name, final MyListener<String> listener){
        modelFirebase.uploadImage(bitmap,name,listener);
    }








    //public interface GetAllTask extends MyListener<>



}
