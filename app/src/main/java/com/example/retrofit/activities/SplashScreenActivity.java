package com.example.retrofit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.retrofit.R;
import com.example.retrofit.config.RoomConfig;
import com.facebook.stetho.Stetho;

public class SplashScreenActivity extends AppCompatActivity {

    private RoomConfig dbInstance;

    public RoomConfig getDbInstance() {
        return dbInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // remove a barra de título apenas desta janela
        getSupportActionBar().hide();

        dbInstance = RoomConfig.getInstance(SplashScreenActivity.this);

        Stetho.initializeWithDefaults(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
}