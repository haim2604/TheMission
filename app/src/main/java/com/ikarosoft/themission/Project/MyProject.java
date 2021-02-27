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
    private String name;
    private String status;
    private String usersPhone;
    private String description;
    private Long lastUpdated;

    public MyProject() {

    }


    public MyProject(String number, String name, String status) {
        this.name= name;
        this.numProj = number;
        this.status = status;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("numProj", numProj);
        result.put("nameTask", name);
        result.put("description", description);
        result.put("statusTask", status);
        String users[]= usersPhone.split("#");
        result.put("usersPhone1", users[0]);
        result.put("usersPhone2", users[1]);
        result.put("usersPhone3", users[2]);
        result.put("usersPhone4", users[3]);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }

    public void fromMap(Map<String, Object> map){
        numProj = (String)map.get("numProj");
        name = (String)map.get("name");
        description = (String)map.get("description");
        status = (String)map.get("status");
        usersPhone=(String)map.get("usersPhone1");
        for (int i = 2; i < 5; i++) {
            usersPhone= usersPhone+"#"+((String)map.get("usersPhone"+i));
        }

        Timestamp ts = (Timestamp)map.get("lastUpdated");
        lastUpdated = ts.getSeconds();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
