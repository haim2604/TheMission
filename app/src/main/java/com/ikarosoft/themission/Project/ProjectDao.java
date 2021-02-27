package com.ikarosoft.themission.Project;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ikarosoft.themission.Task.MyTask;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("select * from MyProject")
    LiveData<List<MyProject>>  getAllProject();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(MyProject... myProjects);

    @Delete
    void delete(MyProject myProject);


    @Query("DELETE FROM MyProject")
    public void nukeTable();

}
