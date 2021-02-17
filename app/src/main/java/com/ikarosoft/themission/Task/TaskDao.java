package com.ikarosoft.themission.Task;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;

@Dao
public interface TaskDao {

    @Query("select * from MyTask")
    LiveData<List<MyTask>>  getAllTask();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(MyTask... myTasks);

    @Delete
    void delete(MyTask myTask);


    @Query("DELETE FROM MyTask")
    public void nukeTable();

}
