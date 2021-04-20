package com.ikarosoft.themission.User;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ikarosoft.themission.Project.MyProject;
import com.ikarosoft.themission.User.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from User")
    LiveData<List<User>> getAllUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Delete
    void delete(User user);


}
