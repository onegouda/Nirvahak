package com.example.nirvahak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FleetOwnerEditProfile extends AppCompatActivity {
    Button btnSaveFleetProfile;
    EditText editFleetName,editTruckOwned,editDriverCount,editFleetLicenceNo,editFleetAddress;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_owner_edit_profile);
        btnSaveFleetProfile=findViewById(R.id.btnSaveFleetProfile);
        editFleetName=findViewById(R.id.editFleetName);
        editTruckOwned=findViewById(R.id.editTruckOwned);
        editDriverCount=findViewById(R.id.editDriverCount);
        editFleetLicenceNo=findViewById(R.id.editFleetLicenceNo);
        editFleetAddress=findViewById(R.id.editFleetAddress);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        //firestore initialise
        db = FirebaseFirestore.getInstance();




        btnSaveFleetProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(FleetOwnerEditProfile.this, fProfile.class));
                performClick();
            }
        });



    }

    private void performClick() {
        String efname =editFleetName.getText().toString();
        String etOwned =editTruckOwned.getText().toString();
        String edCount =editDriverCount.getText().toString();
        String efLicence=editFleetLicenceNo.getText().toString();
        String efAdd=editFleetAddress.getText().toString();


        if(efname.isEmpty()){
            editFleetName.setError("Enter Fleet Name");
        }else if(etOwned.isEmpty()){
            editTruckOwned.setError("Enter No of Truck Owned");
        }else if(edCount.isEmpty()){
            editDriverCount.setError("Enter No of Driver Owned");
        }else if(efLicence.isEmpty()){
            editFleetLicenceNo.setError("Enter Fleet Lincence No");
        }else if(efAdd.isEmpty()){
            editFleetAddress.setError("Enter Fleet Address");
        }else {

            DocumentReference documentReference = db.collection("users").document(mUser.getUid());
            Map<String, Object> fowner = new HashMap<>();
            fowner.put("fleetname", efname);
            fowner.put("etowned", etOwned);
            fowner.put("edcount", edCount);
            fowner.put("eflicence", efLicence);
            fowner.put("efadd", efAdd);

            documentReference.collection("profile").add(fowner).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(FleetOwnerEditProfile.this, "Profile Updated Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FleetOwnerEditProfile.this, fProfile.class));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FleetOwnerEditProfile.this, "Unable to save profile", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}