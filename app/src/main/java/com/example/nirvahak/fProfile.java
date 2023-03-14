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

public class fProfile extends AppCompatActivity {
    TextView fleetOwnerEditProfile,fleetOwnerNameP,fleetName,truckOwned,driverCount,fleetLicenceNo,fleetAddress,textView6;
    Button btnVerifyFprofile;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fprofile);
        fleetOwnerEditProfile=findViewById(R.id.fleetOwnerEditProfile);
        fleetOwnerNameP=findViewById(R.id.fleetOwnerNameP);
        fleetAddress=findViewById(R.id.fleetAddress);
        fleetLicenceNo=findViewById(R.id.fleetLicenceNo);
        fleetName=findViewById(R.id.fleetName);
        truckOwned=findViewById(R.id.truckOwned);
        driverCount=findViewById(R.id.driverCount);
        btnVerifyFprofile=findViewById(R.id.btnVerifyFprofile);
        textView6=findViewById(R.id.textView6);
        //firebase initialise
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        //firestore initialise
        db = FirebaseFirestore.getInstance();



        if(!mAuth.getCurrentUser().isEmailVerified()){
            textView6.setVisibility(View.VISIBLE);
            btnVerifyFprofile.setVisibility(View.VISIBLE);


        }


        btnVerifyFprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send verification link

                mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(fProfile.this, "Verification Email Sent to your Mail", Toast.LENGTH_SHORT).show();
                        textView6.setVisibility(View.GONE);
                        btnVerifyFprofile.setVisibility(View.GONE);
                    }
                });
            }
        });



        fleetOwnerEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fProfile.this, FleetOwnerEditProfile.class));
            }
        });


        DocumentReference df=db.collection("users").document(mUser.getUid());
        df.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    fleetOwnerNameP.append(value.getString("FirstName")+" "+value.getString("LastLast"));
                }
            }
        });

        df.collection("profile").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                    fleetName.setText(document.getString("fleetname"));
                    truckOwned.setText(document.getString("etowned"));
                    driverCount.setText(document.getString("edcount"));
                    fleetLicenceNo.setText(document.getString("eflicence"));
                    fleetAddress.setText(document.getString("efadd"));
                }
            }
        });

    }
}