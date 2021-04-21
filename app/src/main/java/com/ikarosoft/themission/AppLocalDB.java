package com.ikarosoft.themission;

import androidx.room.Room;

public class AppLocalDB {
    static public AppLocalDbRepostory db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepostory.class,
                    "dbTheMission.db")
            .fallbackToDestructiveMigration()
            .build();
}
