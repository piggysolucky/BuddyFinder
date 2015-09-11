package com.buckeye.osu.yanyan.buddyfinder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Register extends ActionBarActivity implements View.OnClickListener{
    private EditText userId, password, email;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userId = (EditText)findViewById(R.id.id);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        register = (Button)findViewById(R.id.register);

        register.setOnClickListener(this);

    }

   @Override
    public void onClick(View v){
       switch (v.getId()){
           case R.id.register:
               String uId = userId.getText().toString();
               String pw = password.getText().toString();
               String em = email.getText().toString();
               User user = new User(uId, em, pw);

               registerUser(user);
               break;
       }
   }

    //Register the user with server
    private void registerUser (User user) {
        ServerRequests requests = new ServerRequests(this, "Register");
        requests.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User user) {
                startActivity(new Intent(Register.this, LogIn.class));
            }
        });
    }
}
