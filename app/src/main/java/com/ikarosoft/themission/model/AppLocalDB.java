package com.ikarosoft.themission.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ikarosoft.themission.MyApplication;


@Database(entities = {User.class/*,MyTask.class*/},version = 1)
abstract class AppLocalDbRepostory extends RoomDatabase {
    public abstract UserDao userDao();
}


public class AppLocalDB {
    static public AppLocalDbRepostory db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepostory.class,
                    "dbTheMission.db")
            .fallbackToDestructiveMigration()
            .build();
}
