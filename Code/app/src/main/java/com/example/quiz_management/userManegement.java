package com.example.quiz_management;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userManegement {
    String userName, userEmail, userPhoneNumber, quizId;
    Integer coinScore, quizWin, quizLose , quizTotalPlayed;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");

    userManegement(){ }

    public userManegement(String userName, String userEmail, Integer coinScore, String userPhoneNumber, Integer quizWin, Integer quizLose, Integer quizTotalPlayed, String quizId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.coinScore = coinScore;
        this.userPhoneNumber= userPhoneNumber;
        this.quizWin = quizWin;
        this.quizLose = quizLose;
        this.quizTotalPlayed = quizTotalPlayed;
        this.quizId = quizId;
    }

    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public Integer getQuizTotalPlayed() {
        return quizTotalPlayed;
    }

    public void setQuizTotalPlayed(Integer quizTotalPlayed) {
        this.quizTotalPlayed = quizTotalPlayed;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getCoinScore() {
        return coinScore;
    }

    public void setCoinScore(Integer coinScore) {
        this.coinScore = coinScore;
    }

    private void userCoinScore(String userEmail){
    }

    private void userProfile(String userEmail){
    }

    public Integer getQuizWin() {
        return quizWin;
    }

    public void setQuizWin(Integer quizWin) {
        this.quizWin = quizWin;
    }

    public Integer getQuizLose() {
        return quizLose;
    }

    public void setQuizLose(Integer quizLose) {
        this.quizLose = quizLose;
    }
}
