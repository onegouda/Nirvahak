package com.example.nirvahak;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class tProfile extends AppCompatActivity {
    TextView transporterEditProfile,transporterNameP,companyName,companyLicenceNo,companyAddress,textView10;
    Button btnVerifyTprofile;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tprofile);
        transporterEditProfile=findViewById(R.id.transporterEditProfile);
        companyAddress=findViewById(R.id.companyAddress);
        companyName=findViewById(R.id.companyName);
        companyLicenceNo=findViewById(R.id.companyLicenceNo);
        transporterNameP=findViewById(R.id.transporterNameP);
        btnVerifyTprofile=findViewById(R.id.btnVerifyTprofile);
        textView10=findViewById(R.id.textView10);
        //firebase initialise
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        //firestore initialise
        db = FirebaseFirestore.getInstance();


        if(!mAuth.getCurrentUser().isEmailVerified()){
            textView10.setVisibility(View.VISIBLE);
            btnVerifyTprofile.setVisibility(View.VISIBLE);


        }

        btnVerifyTprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send verification link

                mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(tProfile.this, "Verification Email Sent to your Mail", Toast.LENGTH_SHORT).show();
                        textView10.setVisibility(View.GONE);
                        btnVerifyTprofile.setVisibility(View.GONE);
                    }
                });
            }
        });


        transporterEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tProfile.this, TransporterEditProfile.class));
            }
        });


        DocumentReference df=db.collection("users").document(mUser.getUid());
        df.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    transporterNameP.append(value.getString("FirstName")+" "+value.getString("LastLast"));
                }
            }
        });

        df.collection("Tprofile").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                    companyName.setText(document.getString("CompanyName"));
                    companyLicenceNo.setText(document.getString("ComapanyLicenceNo"));
                    companyAddress.setText(document.getString("CompanyNo"));

                }
            }
        });



    }
}