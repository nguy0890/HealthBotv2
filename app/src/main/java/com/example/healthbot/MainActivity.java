package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        SharedPreferences.Editor dh_sp_edit = dh_sp.edit();

        dh_sp_edit.putString(currentTime.toString(), "Diagnosis Description");
        dh_sp_edit.commit();
    }
}