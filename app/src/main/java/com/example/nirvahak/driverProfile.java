package com.example.nirvahak;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

public class driverProfile extends AppCompatActivity {
    TextView driverEditPro,driverNameP,driverExperience,driverFleetName,vehicleNo,licenceNo,driverVehicleType,driverAddress;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        driverEditPro=findViewById(R.id.driverEditPro);
        driverNameP=findViewById(R.id.driverNameP);
        driverExperience=findViewById(R.id.driverExperience);
        driverFleetName=findViewById(R.id.driverFleetName);
        vehicleNo=findViewById(R.id.vehicleNo);
        licenceNo=findViewById(R.id.licenceNo);
        driverVehicleType=findViewById(R.id.driverVehicleType);
        driverAddress=findViewById(R.id.driverAddress);

        //firebase initialise
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        //firestore initialise
        db = FirebaseFirestore.getInstance();



        driverEditPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(driverProfile.this, DriverEditProfile.class));

            }
        });


        DocumentReference df=db.collection("users").document(mUser.getUid());
        df.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    driverNameP.append(value.getString("FirstName")+" "+value.getString("LastLast"));
                }
            }
        });




        df.collection("dprofile").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                    driverExperience.setText(document.getString("driverExperience"));
                    driverFleetName.setText(document.getString("dFleetName"));
                    vehicleNo.setText(document.getString("dVehicleNo"));
                    licenceNo.setText(document.getString("dLicenceNo"));
                    driverVehicleType.setText(document.getString("dVehicle"));
                    driverAddress.setText(document.getString("dAdd"));
                }
            }
        });

    }
}