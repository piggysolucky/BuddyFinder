package com.buckeye.osu.yanyan.buddyfinder;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LogIn extends ActionBarActivity implements View.OnClickListener {
    private EditText etUserId, etPassword;
    private TextView register;
    private Button logIn;
    private UserLocalStore mUserLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserLocalStore = new UserLocalStore(this);

        etUserId = (EditText)findViewById(R.id.log_userID);
        etPassword = (EditText)findViewById(R.id.log_password);

        logIn = (Button)findViewById(R.id.login);
        register = (TextView)findViewById(R.id.log_register);

        logIn.setOnClickListener(this);
        register.setOnClickListener(this);
    }
    // Check if the user already logged in and whether show user detail
    @Override
    public void onStart() {
        super.onStart();
        if (authenticateLoggedIn() == true){
           displayUserDetail();
        }
    }

    public void displayUserDetail() {
        User user = mUserLocalStore.getLoggedInUser();

        etUserId.setText(user.getUserID());
        etPassword.setText("************");
    }

    public boolean authenticateLoggedIn() {
        return mUserLocalStore.getUserLoggedIn();
    }


    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.login:
                String userID = etUserId.getText().toString();
                String password = etPassword.getText().toString();
                User user = new User(userID, null, password);

                authenticateUser(user);




                break;
            case R.id.log_register:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }

    //Authenticate user information after the click log in
    private void authenticateUser(User user) {
        ServerRequests serverRequests = new ServerRequests(this, "Log In");
        serverRequests.fetchUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    showErrorMessage();
                } else {
                    LogUserIn(returnedUser);
                }
            }
        });
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LogIn.this);
        dialogBuilder.setMessage("Incorrect user or password");
        dialogBuilder.setPositiveButton("ok", null);
        dialogBuilder.show();
    }

    private void LogUserIn(User returnedUser) {
        mUserLocalStore.storeUserInfo(returnedUser);
        mUserLocalStore.setUserLogIn(true);
    }
}
