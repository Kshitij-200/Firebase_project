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
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_password extends AppCompatActivity {
    EditText forgotpasswordemail;
    Button forgotpasswordbutton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forgotpasswordemail = (EditText) findViewById(R.id.forgotpasswordemail);
        forgotpasswordbutton = (Button) findViewById(R.id.forgotpasswordbutton);
        mAuth = FirebaseAuth.getInstance();
        forgotpasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }
    private void resetPassword(){
        String email = forgotpasswordemail.getText().toString().trim();
        if (email.isEmpty()){
            forgotpasswordemail.setError("Email is required");
            forgotpasswordemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forgotpasswordemail.setError("Enter a valid email id");
            forgotpasswordemail.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Forgot_password.this, "Succesfully sent password reset email to your email id", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Forgot_password.this, "Try again! Something wrong happened", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}