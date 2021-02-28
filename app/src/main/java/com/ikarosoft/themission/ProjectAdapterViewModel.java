package com.ikarosoft.themission;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ikarosoft.themission.Project.MyProject;
import com.ikarosoft.themission.Project.ProjectModel;
import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.Task.TaskModel;

import java.util.List;

public class ProjectAdapterViewModel extends ViewModel {

    private LiveData<List<MyProject>> data;

    public ProjectAdapterViewModel() {


        data = ProjectModel.instance.getAllProject();
    }



    public LiveData<List<MyProject>> getData() {
        return data;
    }


}
