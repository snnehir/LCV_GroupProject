package com.example.lcv_project.models;

public class UserListItem extends User{
    private boolean is_friend;
    public UserListItem(String fullName, String username, String mail, String profile_picture_url, int friend_status) {
        super(fullName, username, mail, profile_picture_url);
        if(friend_status == 0){
            this.is_friend = false;
        }
        else{
            this.is_friend = true;
        }
    }

    public boolean getIs_friend() {
        return is_friend;
    }
}
