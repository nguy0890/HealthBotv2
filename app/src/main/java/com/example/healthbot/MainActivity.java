package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Calendar;
import java.util.Date;


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
        Button chatBtn = findViewById(R.id.chatBtn);
        Button histBtn = findViewById(R.id.histBtn);

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHistory();
            }
        });

        histBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivityForResult(intent, 10);
            }
        });


    }
    //temporary
    protected void addHistory(){
        SharedPreferences dh_sp = getSharedPreferences("history_sp", MODE_PRIVATE);
        Date currentTime = Calendar.getInstance().getTime();
        JSONObject diagnosis = new JSONObject();
        String[] symptoms = {"runny nose", "headache"};

        try {
            diagnosis.put("diagnosis", "test");
            diagnosis.put("symptoms", String.join(",", symptoms));
            diagnosis.put("date", currentTime);
        } catch (JSONException e){
            Log.i("main", "tough");
        }

        SharedPreferences.Editor dh_sp_edit = dh_sp.edit();

        dh_sp_edit.putString(currentTime.toString(), diagnosis.toString());
        dh_sp_edit.commit();

    }

}