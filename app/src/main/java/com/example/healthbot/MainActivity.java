package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.ImageButton;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity"; //debugging message

    Dialog warningDialog;
    Button understoodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declare button
        final Button bmiButton = findViewById(R.id.bmiButton);

        //Buttons for warning message
        final ImageButton warningButton = findViewById(R.id.warningButton);

        //BMI button on click
        bmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked BMI Button");
                Intent intent = new Intent(MainActivity.this, BMICalculator.class);
                startActivityForResult(intent, 10);
            }
        });

        //Warning button on click
        warningDialog = new Dialog(this);
        warningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Warning Button");
                openWarningDialog();
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
        SharedPreferences.Editor dh_sp_edit = dh_sp.edit();

        dh_sp_edit.putString(currentTime.toString(), "Diagnosis Description");
        dh_sp_edit.commit();

    }
    private void openWarningDialog() {
        warningDialog.setContentView(R.layout.warning_message);
        warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button understoodButton = warningDialog.findViewById(R.id.understoodButton);
        warningDialog.show();

        //Warning button on click
        understoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Understood Button");
                warningDialog.dismiss();
            }
        });
    }

}