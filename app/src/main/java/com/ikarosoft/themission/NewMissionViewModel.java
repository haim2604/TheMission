package com.ikarosoft.themission;

import androidx.lifecycle.ViewModel;

public class NewMissionViewModel extends ViewModel {

    String numProject,phoneUser,nameTask,numberTask;


    public void setNumProject(String numProject) {
        this.numProject = numProject;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public void setNumberTask(String numberTask) {
        this.numberTask = numberTask;
    }


    public String getNumProject() {
        return numProject;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public String getNameTask() {
        return nameTask;
    }

    public String getNumberTask() {
        return numberTask;
    }



}
