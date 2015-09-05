package com.buckeye.osu.yanyan.buddyfinder;

/**
 * Created by yanyan on 9/5/2015.
 */
public class User {
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String userID, email, password;

    public User(String uID, String email, String pw){
        this.userID = uID;
        this.email = email;
        this.password = pw;
    }

}
