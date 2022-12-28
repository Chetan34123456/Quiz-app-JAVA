package com.example.quiz_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class settingActivity extends AppCompatActivity {

    Button logoutBtnId;
    TextView userName ,quizWin, quizLose, totalquiz, coinsEarned;
    ImageView homeBtn, settingBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference users = database.getReference("users");
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String emailAsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        user's quiz parameters are shown,
        user is able to logout from Logout button
         */

        //will hide the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        logoutBtnId = (Button) findViewById(R.id.logoutBtn);
        userName = (TextView) findViewById(R.id.userNameSetting) ;
        quizWin = (TextView) findViewById(R.id.quiizQinvalue) ;
        quizLose = (TextView) findViewById(R.id.quizLosevalue) ;
        totalquiz = (TextView) findViewById(R.id.totalQuizvalue) ;
        coinsEarned = (TextView) findViewById(R.id.coinEarnedValue) ;
        homeBtn = (ImageView) findViewById(R.id.homeBtn);
        settingBtn = (ImageView) findViewById(R.id.settingBtn);

        //getting current user logged in
        firebaseAuth = FirebaseAuth.getInstance();
        try{
            firebaseUser = firebaseAuth.getCurrentUser();
            emailAsId = emailModifier(firebaseUser.getEmail());
            setUsernameInActivity(emailAsId);

        }catch (Exception e) {
            Toast.makeText(settingActivity.this, "Error from Database", Toast.LENGTH_LONG).show();
            this.finish();
        }

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settingActivity.this, MainActivity.class));
                finish();
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settingActivity.this, settingActivity.class));
                finish();
            }
        });

        logoutBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(settingActivity.this, "logout successfull", Toast.LENGTH_LONG).show();
                startActivity(new Intent(settingActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
    private void setUsernameInActivity(String emailAsId) {
        /*
        user quiz playing performance parameters are fetch from database and updated on activity
         */

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userNameFromDB = snapshot.child(emailAsId).child("userName").getValue(String.class);
                Integer coinScoredFromDB = snapshot.child(emailAsId).child("coinScore").getValue(Integer.class);
                Integer quizWinFromDB = snapshot.child(emailAsId).child("quizWin").getValue(Integer.class);
                Integer quizLoseFromDB = snapshot.child(emailAsId).child("quizLose").getValue(Integer.class);
                Integer totalQuizPlayedFromDB = snapshot.child(emailAsId).child("quizTotalPlayed").getValue(Integer.class);

                quizLose.setText(String.valueOf(quizLoseFromDB));
                quizWin.setText(String.valueOf(quizWinFromDB));
                totalquiz.setText(String.valueOf(totalQuizPlayedFromDB));
                coinsEarned.setText(String.valueOf(coinScoredFromDB));
                userName.setText(userNameFromDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String emailModifier(String email){
        email = email.replace("@","");
        email = email.replace(".","");
        return email;
    }
}