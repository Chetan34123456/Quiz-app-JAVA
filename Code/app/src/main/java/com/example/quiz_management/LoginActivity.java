package com.example.quiz_management;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    //declaring View variables
    ImageView login_shape;
    TextView login_greet;
    EditText login_email, login_password;
    Button signup, loginBtn ;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        uses Firebase methods for for Login process.
         */

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide(); // hide the title bar
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        login_shape = findViewById(R.id.login_shape);
        login_greet =  findViewById(R.id.login_greet);
        login_email =  findViewById(R.id.mainUsername);
        login_password =  findViewById(R.id.password);
        signup =  findViewById(R.id.signUp);
        loginBtn =  findViewById(R.id.login);
        firebaseAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_login_signup = new Intent(LoginActivity.this, signUpActivity.class );
                startActivity(intent_login_signup);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginEmail = login_email.getText().toString().trim();
                String loginPassword = login_password.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(login_email.getText().toString()).matches()){
                    login_email.setError("Invalid email");
                    login_email.requestFocus();
                    return ;
                }
                if (login_password.getText().toString().isEmpty()){
                    login_password.setError("Enter Password");
                    login_password.requestFocus();
                    return ;
                }
                if (login_password.getText().toString().length() <= 6){
                    login_password.setError("Password must be >= 6 characters");
                    login_password.requestFocus();
                    return ;
                }

                firebaseAuth.signInWithEmailAndPassword(login_email.getText().toString(), login_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this , "Login succesfull", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                        else{
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}





/*
private void userLogin(){
        //authentication of user
        mAuth.signInWithEmailAndPassword(loginEmail, loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            //on putting non register emaiil nd password
            //Attempt to invoke virtual method 'com.google.android.gms.tasks.Task com.google.firebase.auth.FirebaseAuth.signInWithEmailAndPassword(java.lang.String, java.lang.String)' on a null object reference
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){ ;
                    Log.d("outin","login");
                    Toast.makeText(LoginActivity.this, "Login successfull", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                if (!task.isSuccessful()){
                    try{
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidCredentialsException e){
                        login_email.setError("Wrong password or email entered");
                        login_email.requestFocus();
                        return;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    finally {
                        Log.d("outin", "login uncessfull");
                    }
                    //onFailure();

                }
                else{
                    Log.d("outin","falied");
                    Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage() , Toast.LENGTH_LONG).show();
                }
            }
            public void onFailure(@Nullable Exception e){
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (e instanceof FirebaseAuthInvalidUserException) {
                    Toast.makeText(LoginActivity.this, "Incorrect email address", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    return;
                }
            }

        });

    }
*/
