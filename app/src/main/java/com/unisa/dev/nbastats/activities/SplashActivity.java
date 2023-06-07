package com.unisa.dev.nbastats.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.unisa.dev.nbastats.R;

public class SplashActivity extends AppCompatActivity {

    private static final int DELAY_TIME = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentMain = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intentMain);
                finish();
            }
        }, DELAY_TIME);
    }
}