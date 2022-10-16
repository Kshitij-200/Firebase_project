package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {
    Button registerbutton;
    private FirebaseAuth mAuth;
    EditText signupname;
    EditText signuppassword;
    EditText signupage;
    EditText signupemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Objects.requireNonNull(getSupportActionBar()).hide();
        registerbutton = (Button) findViewById(R.id.buttonregister);
        signupname = (EditText)findViewById(R.id.signupname);
        signuppassword = (EditText)findViewById(R.id.signuppassword);
        signupage = (EditText)findViewById(R.id.signupage);
        signupemail = (EditText)findViewById(R.id.signupemail);
        mAuth = FirebaseAuth.getInstance();

    }
    public void onClick(View v){
        registerUser();
    }
    private void registerUser(){
        String name = signupname.getText().toString().trim();
        String email =signupemail.getText().toString().trim();
        String age =signupage.getText().toString().trim();
        String password =signuppassword.getText().toString().trim();
        if (name.isEmpty()){
            signupname.setError("Name cannot be empty");
            signupname.requestFocus();
            return;
        }
        if (age.isEmpty() || age.length() > 80){
            signupage.setError("Age cannot be empty nor greater then 80");
            signupage.requestFocus();
            return;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signupemail.setError("Email cannot be empty and must be valid");
            signupemail.requestFocus();
            return;
        }

        if (password.isEmpty() || password.length() < 6 || !password.contains("@") ){
            signuppassword.setError("Password cannot be empty and it must be greater then 6 letters also contains some special symbols");
            signuppassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity2.this, "User is registered successfully", Toast.LENGTH_SHORT).show();
                    user User = new user(name,age,email);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(MainActivity2.this, "User is registered successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(MainActivity2.this, "Failed to register! Try again ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(MainActivity2.this, "Failed to register! Try again ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}