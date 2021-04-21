package com.ikarosoft.themission;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ikarosoft.themission.Project.MyProject;
import com.ikarosoft.themission.Project.ProjectModel;
import com.ikarosoft.themission.User.User;
import com.ikarosoft.themission.User.UserModel;

import java.util.List;

public class UserViewModel extends ViewModel {

    private LiveData<List<User>> data;

    public UserViewModel() {
        data = UserModel.instance.getAllUsers();
    }



    public LiveData<List<User>> getData() {
        return data;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("TAGBACKC", "oncler users");

    }

}
