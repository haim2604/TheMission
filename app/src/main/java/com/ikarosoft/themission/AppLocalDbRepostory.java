package com.ikarosoft.themission;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ikarosoft.themission.Project.MyProject;
import com.ikarosoft.themission.Project.ProjectDao;
import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.Task.TaskDao;
import com.ikarosoft.themission.User.User;
import com.ikarosoft.themission.User.UserDao;

@Database(entities = {User.class, MyTask.class, MyProject.class},version = 8)
public abstract class AppLocalDbRepostory extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract TaskDao taskDao();
    public abstract ProjectDao  projDao();

}
