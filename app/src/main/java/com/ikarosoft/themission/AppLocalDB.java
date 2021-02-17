package com.ikarosoft.themission;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.Task.TaskDao;
import com.ikarosoft.themission.User.User;
import com.ikarosoft.themission.User.UserDao;


public class AppLocalDB {
    static public AppLocalDbRepostory db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepostory.class,
                    "dbTheMission.db")
            .fallbackToDestructiveMigration()
            .build();
}
