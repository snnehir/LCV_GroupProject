package com.example.lcv_project.models;

public class User {
    private int userId;
    private String full_name, username, mail, password;

    public User(String fullName, String username, String mail, String password) {
        this.full_name = fullName;
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public User(int userId, String full_name, String username, String mail, String password) {
        this.userId = userId;
        this.full_name = full_name;
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getUsername() {
        return username;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", full_name='" + full_name + '\'' +
                ", username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
