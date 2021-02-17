package com.ikarosoft.themission;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.Task.TaskModel;

import java.util.LinkedList;
import java.util.List;

public class MissionAdapterViewModel extends ViewModel {

    private LiveData<List<MyTask>> data;

    public MissionAdapterViewModel() {


        data = TaskModel.instance.getAllTask();
    }

    public LiveData<List<MyTask>> getData() {
        return data;
    }


}
