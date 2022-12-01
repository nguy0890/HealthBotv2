package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity"; //debugging message


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declare button
        final Button bmiButton = findViewById(R.id.bmiButton);

        //Toolbar button on click
        bmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked BMI Button");
                Intent intent = new Intent(MainActivity.this, BMICalculator.class);
                startActivityForResult(intent, 10);
            }
        });
    }

}