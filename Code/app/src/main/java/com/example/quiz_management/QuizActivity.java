package com.example.quiz_management;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;

public class QuizActivity extends MainActivity {

    //defining variables, views, database instances, countDownTimer, booleans
    TextView question, text;
    Button opt1, opt2, opt3, opt4, endQuiz;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference users = database.getReference("users");
    Integer sessionCounter = 1;
    Integer sessionCorrectOption;
    Integer resultScore=0;
    AnimationDrawable animationDrawable;
    ConstraintLayout constraintLayout;
    Boolean sessionStatus = false ;
    CountDownTimer timer;

    @RequiresApi(api = Build.VERSION_CODES.M) //fix api level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        schedules 10 question for selected quiz Id , each question having timebound of 15 seconds.
        Uses ambient background instead of progress , for displaying remaining time.
        Ambient background goes from green to red, and switches back to green when new question starts.
         */

        sessionCorrectOption = 0; //no correct answer detected

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);
        question = findViewById(R.id.question_);
        text = findViewById(R.id.text_);
        opt1 = findViewById(R.id.option1);
        opt2 = findViewById(R.id.option2);
        opt3 = findViewById(R.id.option3);
        opt4 = findViewById(R.id.option4);
        endQuiz = findViewById(R.id.endQuiz_);

        //geting user selected quiz Id from database
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id = snapshot.child(emailAsId).child("quizId").getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuizActivity.this, "Didnt get Quiz Id", Toast.LENGTH_LONG).show();
            }
        });

        //starting of quiz session(total question to be loaded = 10)
        setBackground();
        loadQuestionsOptions();

        endQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_quiz_main = new Intent(QuizActivity.this, ResulltActivity.class);
                intent_quiz_main.putExtra("id", Integer.toString(sessionCorrectOption));
                startActivity(intent_quiz_main);
                finish();
            }
        });
    }

    private void setBackground(){
        /*
        sets the ambient background
         */

        try{
            constraintLayout = findViewById(R.id.quizbg);
            animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
            animationDrawable.setEnterFadeDuration(4000);
            animationDrawable.setExitFadeDuration(4000);
            animationDrawable.start();

        } catch(Exception e){
            Toast.makeText(QuizActivity.this, "Error in loading animation", Toast.LENGTH_LONG).show();
        }
    }

    private void setCheckOptions(String[][] cred){
        ;
        /*
        randomize the wrong answers and correct answer and sets them to options
        Starts the count dowm of 15 seconds
         */

        //Here optionId is set to (1,2,3,4) at random index [0,1,2,3]
        Integer[] optionId = {1, 2, 3, 4};
        List<Integer> optionIdList = Arrays.asList(optionId);
        Collections.shuffle(optionIdList);
        optionIdList.toArray(optionId);

        //when next question is loaded, to show as none of button was selected before
        opt1.setBackgroundResource(R.drawable.rounded_corner_button);
        opt2.setBackgroundResource(R.drawable.rounded_corner_button);
        opt3.setBackgroundResource(R.drawable.rounded_corner_button);
        opt4.setBackgroundResource(R.drawable.rounded_corner_button);

        question.setText(cred[0][0]);
        opt1.setText(cred[optionId[0]][0]);    //passing random number from optionId
        opt2.setText(cred[optionId[1]][0]);
        opt3.setText(cred[optionId[2]][0]);
        opt4.setText(cred[optionId[3]][0]);
        text.setText(String.valueOf(sessionCounter));

        //timer starts for 15 seconds
        timer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                /*
                when session time (15 seconds) is not completed
                 */

                Log.d("idcheck", "time remaining : " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                /*
                when user selected any option for session time, thus loading next question
                and incrementing the sessionCounter
                 */

                sessionCounter += 1;
                try {
                    checkSessionLength();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //when session questions are completed ,set sessionCompletion = True
        if (sessionCounter > 10){
            sessionStatus = true;
            timer.cancel();
            return;
        }

        //option clicked , timer stops, ambient backgrounds stops ,
        // green red colour displayed at option button, loading next question
        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                animationDrawable.stop();
                if (opt1.getText().toString().trim().equals(cred[1][0])){
                    sessionCorrectOption = sessionCorrectOption + 1;
                    opt1.setBackgroundResource(R.drawable.green_button);
                }
                else{
                    opt1.setBackgroundResource(R.drawable.red_button);
                }
                try {
                    checkSessionLength();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setBackground();
            }
        });

        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                animationDrawable.stop();
                if (opt2.getText().toString().trim().equals(cred[1][0])){
                    sessionCorrectOption = sessionCorrectOption + 1;
                    opt2.setBackgroundResource(R.drawable.green_button);
                }
                else{
                    opt2.setBackgroundResource(R.drawable.red_button);
                }
                try {
                    checkSessionLength();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setBackground();
            }
        });

        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                animationDrawable.stop();
                if (opt3.getText().toString().trim().equals(cred[1][0])){
                    sessionCorrectOption = sessionCorrectOption + 1;
                    opt3.setBackgroundResource(R.drawable.green_button);
                }
                else{
                    opt3.setBackgroundResource(R.drawable.red_button);
                }
                try {
                    checkSessionLength();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setBackground();
            }
        });

        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                animationDrawable.stop();
                if (opt4.getText().toString().trim().equals(cred[1][0])){
                    sessionCorrectOption = sessionCorrectOption + 1;
                    opt4.setBackgroundResource(R.drawable.green_button);
                }
                else{
                    opt4.setBackgroundResource(R.drawable.red_button);
                }
                try {
                    checkSessionLength();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setBackground();
            }
        });

    }

    private void loadQuestionsOptions(){
        /*
        gets the response from the API , loads to a 2D array
         */

        RequestQueue mQueue;
        mQueue = Volley.newRequestQueue(this);
        final String[] quizQuestion__ = new String[1];
        final String[] wrongAns1 = new String[1];
        final String[] wrongAns2 = new String[1];
        final String[] wrongAns3 = new String[1];
        final String[] correctAns = new String[1];
        String quizUrl;

        try {
            if (id.equals("sports")) {
                quizUrl = "https://opentdb.com/api.php?amount=1&category=21&difficulty=easy&type=multiple";
            } else if (id.equals("artCraft")) {
                quizUrl = "https://opentdb.com/api.php?amount=1&category=25&difficulty=easy&type=multiple";
            } else if (id.equals("science")) {
                quizUrl = "https://opentdb.com/api.php?amount=1&category=17&difficulty=easy&type=multiple";
            } else if (id.equals("random")) {
                quizUrl = "https://opentdb.com/api.php?amount=1&category=25&difficulty=easy&type=multiple";
            } else {
                quizUrl = "https://opentdb.com/api.php?amount=1&category=25&difficulty=easy&type=multiple";
            }
        }catch (Exception e){
            quizUrl = "https://opentdb.com/api.php?amount=1&category=25&difficulty=easy&type=multiple";
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, quizUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String quizQuestion = String.valueOf(jsonObject.get("question"));
                    String wrongAns = String.valueOf((jsonObject.get("incorrect_answers")));
                    String wrongAnsArray = wrongAns.replace('"', ' ');
                    wrongAnsArray = wrongAnsArray.replace("[", "");
                    wrongAnsArray = wrongAnsArray.replace("]", "");
                    String[] wrongAnsArraySplit = wrongAnsArray.split(",");

                    Log.d("idcheck", String.valueOf((jsonObject.get("correct_answer"))));
                    Log.d("idcheck", String.valueOf((jsonObject.get("incorrect_answers"))));
                    quizQuestion__[0] = quizQuestion;
                    correctAns[0] = String.valueOf((jsonObject.get("correct_answer")));
                    wrongAns1[0] = wrongAnsArraySplit[0];
                    wrongAns2[0] = wrongAnsArraySplit[1];
                    wrongAns3[0] = wrongAnsArraySplit[2];

                    String[][] quizELe = new String[][]{
                            {quizQuestion__[0], String.valueOf(0)},
                            {correctAns[0], String.valueOf(1)},
                            {wrongAns1[0], String.valueOf(2)},
                            {wrongAns2[0], String.valueOf(3)},
                            {wrongAns3[0], String.valueOf(4)}
                    };
                    setCheckOptions(quizELe);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Quiz", "Something went wrong");
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    private void updateDatabase(String valueType,Integer addingValue){
        /*
        updating the database, called after the quiz session ends
         */

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userNameFromDB = snapshot.child(emailAsId).child("userName").getValue(String.class);
                Integer coinScoredFromDB = snapshot.child(emailAsId).child("coinScore").getValue(Integer.class);
                Integer quizWinFromDB = snapshot.child(emailAsId).child("quizWin").getValue(Integer.class);
                Integer quizLoseFromDB = snapshot.child(emailAsId).child("quizLose").getValue(Integer.class);
                Integer quizPlayedFromDB = snapshot.child(emailAsId).child("quizTotalPlayed").getValue(Integer.class);

                try {
                    if (valueType.equals("coinScore")) {
                        users.child(emailAsId).child(valueType).setValue(coinScoredFromDB + addingValue);
                    }

                    if (valueType.equals("quizWin")) {
                        users.child(emailAsId).child(valueType).setValue(quizWinFromDB + addingValue);
                    }

                    if (valueType.equals("quizLose")) {
                        users.child(emailAsId).child(valueType).setValue(quizLoseFromDB + addingValue);
                    }

                    if (valueType.equals("quizTotalPlayed")) {
                        users.child(emailAsId).child(valueType).setValue(quizPlayedFromDB + addingValue);
                    }
                } catch (Exception e){
                    Log.d("dataBaseError", "Failed to update data");
                }
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

    public void checkSessionLength() throws InterruptedException {
        /*
        checks the sessionCounter , sessionCorrectAnswers given by user and update them as required
         */

        if (sessionCounter < 11){
            loadQuestionsOptions();
        }

        if (sessionCounter == 10) {
            sessionStatus = true;
            updateDatabase("quizTotalPlayed", 1);

            if (sessionCorrectOption > 3){
                updateDatabase("coinScore", sessionCorrectOption);
            }
            if (sessionCorrectOption == 10){
                Log.d("idcheck", "updating win value");
                updateDatabase("quizWin", 1);
            }
            if (sessionCorrectOption <= 3){
                Log.d("idcheck", "updating lose value");
                updateDatabase("quizLose", 1);
            }
            onStop();

        }
        sessionCounter += 1;
        Log.d("idcheck",  "sessionCounter : " + sessionCounter+ ", correctANs = "+ sessionCorrectOption);

    }
    public Integer getValue(){
        return sessionCorrectOption;
    }
    @Override
    public void onStop(){
        /*
        to stop working of activity in backend , after switching the activity
         */

        super.onStop();
        Log.d("idcheck", "closing down");
        Intent intent = new Intent(new Intent(QuizActivity.this, ResulltActivity.class));
        intent.putExtra("id", Integer.toString(sessionCorrectOption));
        startActivity(intent);
        finish();
    }
}




