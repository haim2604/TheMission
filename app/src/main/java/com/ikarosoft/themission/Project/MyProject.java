package com.ikarosoft.themission.Project;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.ikarosoft.themission.User.User;

import java.util.HashMap;
import java.util.Map;

@Entity
public class MyProject {

    @PrimaryKey
    @NonNull
    private String numProj;
    private String nameTask;
    private String statusTask;
    private String usersPhone;
    private String description;
    private Long lastUpdated;
    private boolean isDeleted =false;
    public MyProject() {

    }


    public MyProject(String number, String nameTask, String statusTask) {
        this.nameTask= nameTask;
        this.numProj = number;
        this.statusTask = statusTask;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("numProj", numProj);
        result.put("nameTask", nameTask);
        result.put("description", description);
        result.put("statusTask", statusTask);
        result.put("usersPhone", usersPhone);
        result.put("isDeleted", isDeleted);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }

    public void fromMap(Map<String, Object> map){
        numProj = (String)map.get("numProj");
        nameTask = (String)map.get("nameTask");
        description = (String)map.get("description");
        statusTask = (String)map.get("statusTask");
        usersPhone=(String)map.get("usersPhone");
        isDeleted=(boolean)map.get("isDeleted");


        Timestamp ts = (Timestamp)map.get("lastUpdated");
        lastUpdated = ts.getSeconds();
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdate) {
        this.lastUpdated = lastUpdate;
    }



    @NonNull
    public String getNumProj() {
        return numProj;
    }

    public void setNumProj(@NonNull String numProj) {
        this.numProj = numProj;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public String getUsersPhone() {
        return usersPhone;
    }



    public void setUsersPhone(String usersPhone) {
        this.usersPhone = usersPhone;
    }

    public String [] stringToArray() {
       String[] users = null;
        return users;
    }

    public String  arrayToString() {
        String users="aaa";
        return users;
    }

}
