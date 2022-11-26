package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LoginActivity"; //debugging message

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_login);

        //Declare buttons and SharedPreferences
        final Button loginButton = findViewById(R.id.loginButton);
        final EditText loginField = findViewById(R.id.signIn);
        final EditText passwordField = findViewById(R.id.password);
        final TextView loginError = findViewById(R.id.loginError);
        SharedPreferences sp = getSharedPreferences("DefaultEmail",MODE_PRIVATE);

        //default email
        String default_email = sp.getString("DefaultEmail", "email@domain.com");

        //setting loginField to default string
        loginField.setText(default_email);

        //Creating onClick function to store email to SharedPreferences (sp)
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Validate input of email and password
                String regex = "^(.+)@(.+)$";
                String loginErrorText = getString(R.string.loginError);
                if ((emailValidator(loginField.getText().toString(), regex)) && (passwordField.getText().length()>0)) {
                    SharedPreferences.Editor spEdit = sp.edit();
                    spEdit.putString("DefaultEmail", loginField.getText().toString());
                    //Commit changes
                    spEdit.commit();
                    //Call MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    loginError.setText(loginErrorText);
                }


            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    //email validator helper method
    public static boolean emailValidator(String emailAddress, String regex) {
        return Pattern.compile(regex)
                .matcher(emailAddress)
                .matches();
    }
}