package com.ikarosoft.themission.Task;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class MyTask implements Serializable {

    @PrimaryKey
    @NonNull
    String numberTask;
    String nameTask;
    String statusTask;
    String phoneUser;
    public String numberProject;
    private Long lastUpdated;
    String description;
    String note;
    String progress;
    String takenByUser;
    String urlPhotoTask;
    String users;

   private boolean isDeleted = false;

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdate) {
        this.lastUpdated = lastUpdate;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("numberTask", numberTask);
        result.put("nameTask", nameTask);
        result.put("statusTask", statusTask);
        result.put("phoneUser", phoneUser);
        result.put("numberProject", numberProject);
        result.put("description", description);
        result.put("note", note);
        result.put("progress", progress);
        result.put("takenByUser", takenByUser);
        result.put("urlPhotoTask", urlPhotoTask);
        result.put("isDeleted", isDeleted);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }

    public void fromMap(Map<String, Object> map){
        numberTask = (String)map.get("numberTask");
        nameTask = (String)map.get("nameTask");
        statusTask = (String)map.get("statusTask");
        phoneUser = (String)map.get("phoneUser");
        numberProject = (String)map.get("numberProject");
        note = (String)map.get("note");
        progress = (String)map.get("progress");
        takenByUser = (String)map.get("takenByUser");
        urlPhotoTask = (String)map.get("urlPhotoTask");
        users = (String)map.get("users");
        isDeleted =(boolean)map.get("isDeleted");
        Timestamp ts = (Timestamp)map.get("lastUpdated");
        lastUpdated = ts.getSeconds();
        //long time = ts.toDate().getTime();
    }

    public String getPhoneUser() {
        return phoneUser;
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

    public String getNumberTask() {
        return numberTask;
    }

    public void setNumberTask(String numberTask) {
        this.numberTask = numberTask;
    }

    public MyTask(String nameTask, String statusTask,String phoneUser ,String numberProject) {
        this.numberTask = phoneUser+"_"+numberProject;
        this.nameTask = nameTask;
        this.statusTask = statusTask;
        this.phoneUser= phoneUser;
        this.numberProject = numberProject;

    }

    public MyTask(String nameTask, String statusTask,String phoneUser ,String numberProject,String numberTask) {
        this.numberTask = numberTask;
        this.nameTask = nameTask;
        this.statusTask = statusTask;
        this.phoneUser= phoneUser;
        this.numberProject = numberProject;

    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public String getNumberProject() {
        return numberProject;
    }

    public void setNumberProject(String numberProject) {
        this.numberProject = numberProject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getTakenByUser() {
        return takenByUser;
    }

    public void setTakenByUser(String takenByUser) {
        this.takenByUser = takenByUser;
    }

    public String getUrlPhotoTask() {
        return urlPhotoTask;
    }

    public void setUrlPhotoTask(String urlPhotoTask) {
        this.urlPhotoTask = urlPhotoTask;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public MyTask(){

    }
}
