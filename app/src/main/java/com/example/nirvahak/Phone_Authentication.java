package com.example.nirvahak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Phone_Authentication extends AppCompatActivity {
    EditText inputPhoneNoOtp;
    TextView alert1;
    Button btnSendOtp;
    ProgressBar progressBar;
    String fullNumber;
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_authentication);
        inputPhoneNoOtp=findViewById(R.id.inputPhoneNoOtp);
        alert1=findViewById(R.id.alert1);
        btnSendOtp=findViewById(R.id.btnSendOtp);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        alert1.setVisibility(View.GONE);


        //firebase initialise
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=inputPhoneNoOtp.getText().toString();
                if(number.length()==0)
                {
                    Toast.makeText(Phone_Authentication.this, "Please Enter Correct Number", Toast.LENGTH_SHORT).show();
                }else
                {
                    String fullNumber="+91"+number;
                    AttemptAuth(fullNumber);
                }
            }
        });


    }

    private void AttemptAuth(String fullNumber) {
        progressBar.setVisibility(View.VISIBLE);
        alert1.setVisibility(View.VISIBLE);


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(fullNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.

            Log.d("TAG", "onVerificationCompleted:" + credential);

            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("TAG", "onVerificationFailed", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e instanceof FirebaseTooManyRequestsException) {

                // The SMS quota for the project has been exceeded
                Toast.makeText(Phone_Authentication.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            // Show a message and update the UI
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Intent intent=new Intent(Phone_Authentication.this,Otp_Auth.class);
            intent.putExtra("verificationId",verificationId);
            intent.putExtra("number",fullNumber);
            startActivity(intent);
            Log.d("TAG", "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later
            //mVerificationId = verificationId;
            //mResendToken = token;
        }
    };

    private void sendActivityToNextActivity() {
        Intent intent=new Intent(Phone_Authentication.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            sendActivityToNextActivity();
                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}