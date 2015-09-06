package com.buckeye.osu.yanyan.buddyfinder;

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

    @Override
    public void onStart() {
        super.onStart();
        if (authenticate() == true){
           displayUserDetail();
        }
    }

    public void displayUserDetail() {
        User user = mUserLocalStore.getLoggedInUser();

        etUserId.setText(user.getUserID());
        etPassword.setText("************");
    }

    public boolean authenticate() {
        return mUserLocalStore.getUserLoggedIn();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.login:
                User user = new User(null, null, null);
                mUserLocalStore.storeUserInfo(user);
                mUserLocalStore.setUserLogIn(true);
                break;
            case R.id.log_register:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
}
