package com.example.quiz_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUpActivity extends AppCompatActivity {

    private Button signUpButtonId;
    private EditText userNameId, userEmailId, userPasswordId, userPhoneNumberId;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootNode ;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        sign-in process
         */

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide(); // hide the title bar

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButtonId =(Button) findViewById(R.id.signBtn);
        userNameId = (EditText) findViewById(R.id.signUpusername);
        userPasswordId =(EditText) findViewById(R.id.signUppassword);
        userEmailId =(EditText) findViewById(R.id.signUpEmail);
        userPhoneNumberId = (EditText) findViewById(R.id.phoneNumber);
        mAuth = FirebaseAuth.getInstance();

        signUpButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkcredentials();
                String email = userEmailId.getText().toString().trim();
                String username = userNameId.getText().toString().trim();
                String password = userPasswordId.getText().toString().trim();
                String phoneNumber = userPhoneNumberId.getText().toString().trim();

                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    userEmailId.setError("Please enter valid email");
                    userEmailId.requestFocus();
                    return ;
                }

                if (username.isEmpty()){
                    userNameId.setError("Please enter your name");
                    userNameId.requestFocus();
                    return ;
                }

                if (password.isEmpty() || password.length() < 6){
                    userPasswordId.setError("Password must be greater than 6 characters");
                    userPasswordId.requestFocus();
                    return ;
                }

                if (phoneNumber.isEmpty() || phoneNumber.length() < 10){
                    userPhoneNumberId.setError("Please enter valid phone number");
                    userPhoneNumberId.requestFocus();
                    return ;
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            rootNode = FirebaseDatabase.getInstance();
                            reference = rootNode.getReference("users");
                            userManegement userData = new userManegement(username, email, 0,phoneNumber, 0, 0, 0, "default");
                            reference.child(formatEmail(email)).setValue(userData);
                            Log.d("idcheck", "data added to database");
                            Toast.makeText(signUpActivity.this, "User account created successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(signUpActivity.this, MainActivity.class));
                            finish();
                        }
                        if (!task.isSuccessful()){
                            Toast.makeText(signUpActivity.this, "Incorrect cred " + task.getException().getMessage() , Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private String formatEmail(String email){
        email = email.replace("@","");
        email = email.replace(".","");
        return email;
    }
}