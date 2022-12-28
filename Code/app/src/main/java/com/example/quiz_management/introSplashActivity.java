package com.example.quiz_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static java.lang.Thread.sleep;

public class introSplashActivity extends AppCompatActivity {

    //variables for Views
    ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        Splash activity stands for 2 seconds and shifts to another activity
         */

        //will hide the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_splash);
        splash = (ImageView) findViewById(R.id.imageView2);

        //thread to run sleep and show icon for 2 seconds, state : Non-Synchronized
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent intent_splash_main = new Intent(introSplashActivity.this, MainActivity.class);
                    startActivity(intent_splash_main);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}