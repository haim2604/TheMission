package com.ikarosoft.themission.model;

import com.ikarosoft.themission.User.User;

public class MyProject {
    String name;
    String number;
    String status;
    User[] users;

    public MyProject(String number, String name, String status) {
        this.name= name;
        this.number = number;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }
}
