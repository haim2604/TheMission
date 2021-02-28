package com.ikarosoft.themission.Project;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProjectDao {
  // @Query("select * from MyProject WHERE usersPhone LIKE :myPhone")
    //@Query("select * from MyProject WHERE usersPhone LIKE  '%' || :myPhone  || '%'")

 //@Query("select * from MyProject WHERE usersPhone == :myPhone OR usersPhone == :myPhone2 OR usersPhone == :myPhone3 OR usersPhone == :myPhone4 ")
  @Query("select * from MyProject WHERE usersPhone LIKE  '%' || :myPhone  || '%'")
   LiveData<List<MyProject>> getAllProject(String myPhone);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(MyProject... myProjects);

    @Delete
    void delete(MyProject myProject);


    @Query("DELETE FROM MyProject")
    public void nukeTable();

}
