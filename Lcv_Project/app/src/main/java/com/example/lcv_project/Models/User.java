package com.example.lcv_project.Models;

public class User {
        private String UserName, Password, UserId, AccountType;
        private String bride,groom;


        public User(String UserName, String Password, String UserId, String AccountType) {
            this.UserName = UserName;
            this.Password = Password;
            this.UserId = UserId;
            this.AccountType =AccountType;
        }

        public User() {

        }

        public String getUserName() {

            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public void setPassword(String Password) {

            this.Password = Password;
        }

        public String getPassword() {

            return Password;
        }

        public String getUserId() {

            return UserId;
        }
    public String getAccountType() {

        return AccountType;
    }

    public void setAccountType(String accountType) {
        this.AccountType = accountType;
    }

}
