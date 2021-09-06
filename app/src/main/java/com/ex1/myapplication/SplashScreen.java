package com.ex1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        this.progressBar =this.findViewById(R.id.progressBar);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                SplashScreen.this.doWork();
                SplashScreen.this.startApp();
            }
        });
        thread.start();
    }
    public void doWork() {
        for(progress = 20; progress <= 100; progress += 20) {
            try {
                Thread.sleep(1000);
                this.progressBar.setProgress(this.progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void startApp() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }

}