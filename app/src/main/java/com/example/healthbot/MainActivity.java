package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;


public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity"; //debugging message

    Dialog warningDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

        Button chatBtn = findViewById(R.id.chatBtn);

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { addHistory(); }});

        //Warning button on click
        warningDialog = new Dialog(this);
        warningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Warning Button");
                openWarningDialog();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu, m );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {
        Integer id = mi.getItemId();
        switch (id) {
            case R.id.menuHome:
                Log.d("Toolbar", "You selected item Home");
                break;
            case R.id.menuHistory:
                Log.d("Toolbar", "You selected item History");
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivityForResult(intent, 10);
                break;
            case R.id.menuAboutUs:
                Log.d("Toolbar", "You selected item AboutUs");
                aboutUsDialog();
        }
        return super.onOptionsItemSelected(mi);
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
    private void aboutUsDialog() {
        AboutUsDialog aboutUsDialog = new AboutUsDialog();
        aboutUsDialog.show(getSupportFragmentManager(),"About Us Dialog");
    }


}