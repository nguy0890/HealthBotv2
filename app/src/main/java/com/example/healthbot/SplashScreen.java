package com.example.healthbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    ImageView splashLogo;
    ImageView redBar;
    ImageView blueBar;
    TextView splashName;
    TextView splashSlogan;
    Animation topAnimation;
    Animation botAnimation;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ProgressBar progressBar = findViewById(R.id.topProgress);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //Animation for logo
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.from_top);
        botAnimation = AnimationUtils.loadAnimation(this, R.anim.from_bottom);

        progressBar.setProgress(25);
        redBar = findViewById(R.id.redBar);
        blueBar = findViewById(R.id.blueBar);
        splashLogo = findViewById(R.id.splashLogo);
        splashName = findViewById(R.id.tvappName);
        splashSlogan = findViewById(R.id.tvappSlogan);

        progressBar.setAnimation(botAnimation);
        redBar.setAnimation(topAnimation);
        blueBar.setAnimation(botAnimation);
        splashLogo.setAnimation(topAnimation);
        splashName.setAnimation(botAnimation);
        splashSlogan.setAnimation(botAnimation);

        //Delay for 3 seconds
        handler = new Handler();

        handler.postDelayed(new Runnable () {
            public void run() {
                progressBar.setProgress(100);
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }
}