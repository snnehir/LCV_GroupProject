package com.example.lcv_project.Models;

public class User {
    private int userId;
    private String full_name, username, mail, password, profile_picture_url;

    public User(String fullName, String username, String mail, String password, String profile_img_url) {
        this.full_name = fullName;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.profile_picture_url = profile_img_url;
    }

    public User(int userId, String full_name, String username, String mail, String profile_img_url) {
        this.userId = userId;
        this.full_name = full_name;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.profile_picture_url = profile_img_url;
    }

    public User(String full_name, String username, String mail, String profile_picture_url) {
        this.full_name = full_name;
        this.username = username;
        this.mail = mail;
        this.profile_picture_url = profile_picture_url;
    }

    public User(){

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

    public String getProfile_picture_url() {
        return profile_picture_url;
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

    public void setProfile_picture_url(String profile_picture_url) {
        this.profile_picture_url = profile_picture_url;
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
