package com.ikarosoft.themission.model;

import java.util.LinkedList;
import java.util.List;

public class ProjectModel {
    List<MyProject> data = new LinkedList<MyProject>();
    public final static ProjectModel instance = new ProjectModel();

    private ProjectModel(){
        MyProject project = new MyProject(""+123414,"name","Start");
        MyProject project2 = new MyProject(""+555555,"aaaaaaa","fssfsfs");
        data.add(project);
        data.add(project2);
        data.add(project);
        data.add(project2);
        data.add(project);
        data.add(project2);
        data.add(project);
        data.add(project2);
        data.add(project);
        data.add(project2);
        data.add(project);
        data.add(project2);
        data.add(project);
        data.add(project2);
    }



    public List<MyProject> getAllTask(){
        return data;
    }
}
