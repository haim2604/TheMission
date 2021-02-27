package com.ikarosoft.themission.Project;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.ikarosoft.themission.AppLocalDB;
import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.Task.MyTask;

import java.util.List;

public class ProjectModelSql {


    public final static ProjectModelSql instance = new ProjectModelSql();
    public ProjectModelSql(){
    }

    public LiveData<List<MyProject>> getAllProj() {
        return AppLocalDB.db.projDao().getAllProject();
    }


    public void addProject(MyProject project, ListenerVoid listener){
        @SuppressLint("StaticFieldLeak")
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.projDao().insertAll(project);
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


    public void deleteTask(MyProject project){
        @SuppressLint("StaticFieldLeak")
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.projDao().delete(project);
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
