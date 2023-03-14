package com.example.nirvahak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends AppCompatActivity {
    Button btnForgotP;
    EditText inputEmailId;
    String email;


    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        inputEmailId=findViewById(R.id.inputEmailId);

        btnForgotP=findViewById(R.id.btnForgotP);


        //firebase initialise
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        btnForgotP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    private void validateData() {
        email=inputEmailId.getText().toString();
        if(email.isEmpty()){
            inputEmailId.setError("Required");
        }else
        {
            forgotpass();
        }

    }

    private void forgotpass() {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check Your Mail...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPassword.this,MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(ForgotPassword.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}