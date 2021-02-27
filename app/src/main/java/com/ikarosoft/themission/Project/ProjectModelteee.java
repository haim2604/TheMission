package com.ikarosoft.themission.Project;

import java.util.LinkedList;
import java.util.List;

public class ProjectModelteee {
    List<MyProject> data = new LinkedList<MyProject>();
    public final static ProjectModelteee instance = new ProjectModelteee();

    private ProjectModelteee(){
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
