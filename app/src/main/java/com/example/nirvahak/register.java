package com.example.nirvahak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextView AlreadyHaveAccount;
    EditText inputEmail,inputPassword,inputCPassword;
    Button btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    EditText inputFirstname,inputLastname,inputPhoneNo;
    RadioGroup rG01;
    RadioButton rBtnFO,rBtnT,rBtnDriver;
    String Role;


    //firebase authentication
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AlreadyHaveAccount=findViewById(R.id.AlreadyHaveAccount);
        inputFirstname=findViewById(R.id.inputFirstname);
        inputLastname=findViewById(R.id.inputLastname);
        inputPhoneNo=findViewById(R.id.inputPhoneNo);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputCPassword=findViewById(R.id.inputCPassword);
        btnRegister=findViewById(R.id.btnRegister);
        progressDialog=new ProgressDialog(this);
        rG01=findViewById(R.id.rG01);
        rBtnDriver=findViewById(R.id.rBtnDriver);
        rBtnFO=findViewById(R.id.rBtnFO);
        rBtnT=findViewById(R.id.rBtnT);


        //firebase initialise
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        //firestore initialise
        db = FirebaseFirestore.getInstance();


        //stores data in firebase
        String fname =inputFirstname.getText().toString();
        String lname =inputLastname.getText().toString();
        String phoneno =inputPhoneNo.getText().toString();
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();




        rG01.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.rBtnFO){
                    Role ="Fleet Owner";
                }else if(checkedId==R.id.rBtnT){
                    Role ="Transporter";
                }else{
                    Role="Driver";
                }

            }
        });







        AlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //passes the control back to login page
                startActivity(new Intent(register.this, MainActivity.class));
                finish();


            }
        });
        
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();

            }
        });




    }

    private void PerformAuth() {

        String fname =inputFirstname.getText().toString();
        String lname =inputLastname.getText().toString();
        String phoneno =inputPhoneNo.getText().toString();
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmPassword=inputCPassword.getText().toString();
        String role =Role;



        if (!email.matches(emailPattern))
        {
            inputEmail.setError("Error Context Email");

        }else if(password.isEmpty()||password.length()<6)
        {
            inputPassword.setError("Enter Proper Password");
        }else if(!password.equals(confirmPassword))
        {
            inputCPassword.setError("Password not Matches");
        }else
        {
            progressDialog.setTitle("Registration");
            progressDialog.setMessage("Please Wait While Registration...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {

                        progressDialog.dismiss();
                        //sendUserToNextActivity();
                        //addding data to database
                        DocumentReference documentReference=db.collection("users").document(mUser.getUid());
                        Map<String, Object> user = new HashMap<>();
                        user.put("FirstName",fname);
                        user.put("LastLast",lname);
                        user.put("Role",role);
                        user.put("PhoneNo",phoneno);
                        user.put("Email",email);
                        user.put("Password",password);
                        // Add a new document with a generated ID
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(register.this, "Registraton Sucessful", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(register.this, HomeActivity.class));
                                if (role=="Fleet Owner"){
                                    startActivity(new Intent(register.this, FleetOwner.class));
                                }else if (role=="Transporter"){
                                    startActivity(new Intent(register.this, Transporter.class));
                                }else{
                                    startActivity(new Intent(getApplicationContext(),Driver.class));
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: "+e.getMessage());
                            }
                        });


                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(register.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }


}