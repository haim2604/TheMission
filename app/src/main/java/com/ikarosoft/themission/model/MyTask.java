package com.ikarosoft.themission.model;

public class MyTask {

    String nameTask;
    String statusTask;
    String numberTask;

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public String getNumberTask() {
        return numberTask;
    }

    public void setNumberTask(String numberTask) {
        this.numberTask = numberTask;
    }

    public MyTask(String numberTask, String nameTask, String statusTask) {
        this.numberTask = numberTask;
        this.nameTask = nameTask;
        this.statusTask = statusTask;

    }
}
