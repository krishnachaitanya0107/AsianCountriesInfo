package com.example.asiancountriesinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class SplashScreenActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        progressBar=findViewById(R.id.progressbar);

        Handler handler=new Handler();
        handler.postDelayed(() -> {
            progressBar.setVisibility(View.GONE);

            Intent intent=new Intent(SplashScreenActivity.this,MainActivity.class);
            startActivity(intent);

            finish();
        },3000);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}