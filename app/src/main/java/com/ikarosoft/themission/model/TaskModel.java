package com.ikarosoft.themission.model;

import java.util.LinkedList;
import java.util.List;

public class TaskModel {
    public final static TaskModel instance = new TaskModel();

    private TaskModel(){
        MyTask myTask = new MyTask(""+123414,"name","Start");
        data.add(myTask);
        data.add(myTask);
        data.add(myTask);
        data.add(myTask);
        data.add(myTask);
        data.add(myTask);
        data.add(myTask);
        data.add(myTask);
        data.add(myTask);
        data.add(myTask);
        data.add(myTask);

    }
    List<MyTask> data = new LinkedList<MyTask>();


    public List<MyTask> getAllTask(){
        return data;
    }
}
