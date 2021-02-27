package com.ikarosoft.themission.Project;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MyApplication;
import com.ikarosoft.themission.MyListener;
import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.Task.TaskModelFirebase;
import com.ikarosoft.themission.Task.TaskModelSql;

import java.util.List;

public class ProjectModel {
    public final static ProjectModel instance = new ProjectModel();

    ProjectModelFirebase modelFirebase = new ProjectModelFirebase();
    ProjectModelSql modelSql = new ProjectModelSql();

    private ProjectModel() {
    }

   LiveData<List<MyProject>> taskList;

    public LiveData<List<MyProject>> getAllProject() {
        if (taskList == null) {
            taskList = modelSql.getAllProj();


            refreshAllProject(null);
        }
        // modelSql.getAllTask(listener);
        return taskList;
    }

    public void refreshAllProject(final ListenerVoid listener) {
        //1.get local last updeat data

        SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdatedProj", 0);

        //2.get all update record from firebase form the last update data
        modelFirebase.getAllProject(lastUpdated, new MyListener<List<MyProject>>() {
            @Override
            public void onComplete(List<MyProject> result) {

                long lastU = 0;

                //3.insret the new updete to local db
                for (MyProject p : result) {
                    modelSql.addProject(p, null);
                    if (p.getLastUpdated() > lastU) {
                        lastU = p.getLastUpdated();
                    }
                }
                //4.update the local last update date
                sp.edit().putLong("lastUpdatedProj", lastU).commit();

                //5.return the update data to the listeners
                if (listener != null) {
                    listener.onComplete();
                }

            }
        });



    }

    public void getProjectbyPhone(String phone, MyListener<MyProject> listener) {
        modelFirebase.getProjectByPhone(phone, listener);
    }

    public void addProject(MyProject myProject, ListenerVoid listener) {
        modelFirebase.addProject(myProject, new ListenerVoid() {
            @Override
            public void onComplete() {
                refreshAllProject(new ListenerVoid() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
        //modelSql.addTask(myTask,listener);
    }

    public void deleteProject(MyProject myProject, ListenerVoid listener) {
        modelFirebase.deleteProject(myProject, listener);
    }

    public void uploadImage(Bitmap bitmap, String name, final MyListener<String> listener) {
        modelFirebase.uploadImage(bitmap, name, listener);
    }

}
