package com.buckeye.osu.yanyan.buddyfinder;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yanyan on 9/5/2015.
 */
public class UserLocalStore {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;
    public UserLocalStore(Context context){ // only activity can provide the context
        // Mode file can only be accessed by the calling application
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);  // Create one if doesn't exists
    }

    public void storeUserInfo(User user){
        // user an editor and commit the changes
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", user.getUserID());
        spEditor.putString("email", user.getEmail());
        spEditor.putString("password", user.getPassword());
        spEditor.commit();
    }

    public User getLoggedInUser() {
        String name = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email", "");
        String password = userLocalDatabase.getString("password", "");

        return new User(name, email, password);
    }

    public void setUserLogIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
       return userLocalDatabase.getBoolean("loggedIn", false);
    }

    public String getUserName() {
        return userLocalDatabase.getString("name","");
    }

    public String getUserEmail() {
        return userLocalDatabase.getString("email","");
    }

    public String getUserPassword() {
        return userLocalDatabase.getString("password", "");
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

}
