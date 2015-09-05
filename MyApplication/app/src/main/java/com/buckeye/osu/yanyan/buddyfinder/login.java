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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserId = (EditText)findViewById(R.id.log_userID);
        etPassword = (EditText)findViewById(R.id.log_password);
        logIn = (Button)findViewById(R.id.login);
        register = (TextView)findViewById(R.id.log_register);

        logIn.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.login:
                break;
            case R.id.log_register:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
}
