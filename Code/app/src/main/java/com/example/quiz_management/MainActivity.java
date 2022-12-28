package com.example.quiz_management;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
//newuser@gmail.com , newuser

public class MainActivity extends AppCompatActivity {


        String emailAsId; //modified email
        String id; // user selected Quiz category

        //Declaring View variables
        ImageButton sportsQuiz_b, scienceQuiz_b, artAndCraftQuiz_b, randomQuiz_b;
        ImageButton mainHome, mainSetting ;
        TextView userName, coinEarnedId;

        //database objects
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users = database.getReference("users");
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;

        @RequiresApi(api = Build.VERSION_CODES.M) //error fix, manages api level

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            /*
            listens for user to select any type of quiz category provided.
            Sends quiz category to datebase
             */

            //will hide the title
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            super.onCreate(savedInstanceState);

            firebaseAuth = FirebaseAuth.getInstance();

            //address of layout and elements
            setContentView(R.layout.activity_main);
            sportsQuiz_b = (ImageButton) findViewById(R.id.sportsButton);
            scienceQuiz_b = (ImageButton) findViewById(R.id.scienceButton);
            artAndCraftQuiz_b = (ImageButton) findViewById(R.id.artAndCraftButton);
            randomQuiz_b = (ImageButton) findViewById(R.id.randomButton);
            mainHome = (ImageButton) findViewById(R.id.homeButton);
            mainSetting = (ImageButton) findViewById(R.id.mainSettingButton);
            userName = (TextView) findViewById(R.id.mainUsername);
            coinEarnedId = (TextView) findViewById(R.id.coinEarned);

            try{
                firebaseUser = firebaseAuth.getCurrentUser();
                emailAsId = emailModifier(firebaseUser.getEmail());
                setUsernameInActivity(emailAsId);
            }catch (Exception e) {
                startActivity(new Intent(MainActivity.this , LoginActivity.class));
                this.finish();
            }

            //buttons task methods to shift over new activity
            mainSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_main_login = new Intent(MainActivity.this, settingActivity.class);
                    startActivity(intent_main_login);
                }
            });

            sportsQuiz_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setQuizID("sports");
                }
            });

            artAndCraftQuiz_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setQuizID("artCraft");
                }
            });

            scienceQuiz_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setQuizID("science");
                }
            });

            randomQuiz_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setQuizID("random");
                }
            });
        }

    private void setUsernameInActivity(String emailAsId) {
        /*
        sets the current login username and coin-score in TextView at Top of Activity
         */

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userNameFromDB = snapshot.child(emailAsId).child("userName").getValue(String.class);
                Integer coinScoredFromDB = snapshot.child(emailAsId).child("coinScore").getValue(Integer.class);
                userName.setText(userNameFromDB);
                coinEarnedId.setText(String.valueOf(coinScoredFromDB));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setQuizID(String Id){
            /*
            sends user selected quiz category to database, thus can be in quiz session
             */

            users.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    users.child(emailAsId).child("quizId").setValue(Id);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Failed to load Quiz category", Toast.LENGTH_LONG).show();
                }
            });

            startActivity(new Intent(MainActivity.this, startQuizActivity.class));
    }

    public String emailModifier(String email){
            /*
            modifies the email in such a way, where "@" and "." is replaced by ""
            Google Firebase child keys doesn't accept strings with characters "@", "." ","
            return : modified email (with no special characters)
             */

            email = email.replace("@","");
            email = email.replace(".","");
            return email;
        }
}


