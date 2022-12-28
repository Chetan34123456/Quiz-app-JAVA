package com.example.quiz_management;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResulltActivity extends AppCompatActivity {


    TextView scoreViewId;
    Button backToHomeId, anotherQuizId;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        value passed from Quiz activity is grabbed in this activity and shown as score
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resullt);

        //ambient background
        ConstraintLayout constraintLayout = findViewById(R.id.resultMainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        scoreViewId = (TextView) findViewById(R.id.showScoreview);
        backToHomeId = (Button) findViewById(R.id.backToHomeBtn);
        anotherQuizId = (Button) findViewById(R.id.anotherQuizbtn);

        //grabbing value from Quiz activity using Intent
        String temp = getIntent().getStringExtra("id");

        Log.d("idcheck", temp);
        scoreViewId.setText(String.valueOf(temp));

        backToHomeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResulltActivity.this, MainActivity.class));
                finish();
            }
        });

        anotherQuizId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResulltActivity.this, QuizActivity.class));
                finish();
            }
        });
    }
}