package com.example.firebase;

import androidx.annotation.NonNull;
import   androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button login;
    TextView signup;
    TextView forgotpassword;
    EditText loginemail;
    EditText loginpassword;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        login = (Button) findViewById(R.id.login);
        signup = (TextView) findViewById(R.id.signup);
        forgotpassword = (TextView) findViewById(R.id.forgotpassword);
        loginemail = (EditText) findViewById(R.id.loginemail);
        loginpassword = (EditText) findViewById(R.id.loginpassword);
        mAuth = FirebaseAuth.getInstance();
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

    }
    public void onclick(View v){
        switch (v.getId()){
            case R.id.signup:
                startActivity(new Intent(this,MainActivity2.class));
                break;

            case R.id.forgotpassword:
                Toast.makeText(this, "forgot password", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,Forgot_password.class));
                break;

            case R.id.login:
                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
                loginUser();
                break;
        }
    }
    private void loginUser(){
        String email = loginemail.getText().toString().trim();
        String password = loginpassword.getText().toString().trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginemail.setError("Email cannot be empty and must be valid");
            loginemail.requestFocus();
            return;
        }

        if (password.isEmpty() || password.length() < 6 || !password.contains("@") ){
            loginpassword.setError("Password cannot be empty and it must be greater then 6 letters also contains some special symbols");
            loginpassword.requestFocus();
            return;
        }

//        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this,Profile_Activity.class));
                    }
                    else{
                        firebaseUser.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email for verification of your account", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Failed to login, Please try again!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void signin(View view){
        Toast.makeText(this, "hi google", Toast.LENGTH_LONG).show();

    }
}