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

import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;

public class DriverEditProfile extends AppCompatActivity {
    Button btnSaveProfile;
    EditText editExperience,editFleetNameDriver,editVehicleNo,editLicenceNo,editVehicle,editDriverAddress;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_edit_profile);
        btnSaveProfile=findViewById(R.id.btnSaveProfile);
        editExperience=findViewById(R.id.editExperience);
        editFleetNameDriver=findViewById(R.id.editFleetNameDriver);
        editVehicleNo=findViewById(R.id.editVehicleNo);
        editLicenceNo=findViewById(R.id.editLicenceNo);
        editVehicle=findViewById(R.id.editVehicle);
        editDriverAddress=findViewById(R.id.editDriverAddress);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        //firestore initialise
        db = FirebaseFirestore.getInstance();




        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(DriverEditProfile.this, driverProfile.class));
                performClick();

            }
        });

    }

    private void performClick() {
      String dExperience=editExperience.getText().toString();
      String dfleetName=editFleetNameDriver.getText().toString();
      String dVehicleno=editVehicleNo.getText().toString();
      String dLicenceNo=editLicenceNo.getText().toString();
      String dVehicle=editVehicle.getText().toString();
      String dAdd=editDriverAddress.getText().toString();


        if (dExperience.isEmpty()){
            editExperience.setError("Enter Experience");
        }else if(dfleetName.isEmpty()){
            editFleetNameDriver.setError("Enter Fleet Name");
        }else if(dVehicleno.isEmpty()){
            editVehicleNo.setError("Enter Vehicle No");
        }else if(dLicenceNo.isEmpty()){
            editLicenceNo.setError("Enter Licence No");
        }else if(dVehicle.isEmpty()){
            editVehicle.setError("Enter Vehicle Name ");
        }else if(dAdd.isEmpty()){
            editDriverAddress.setError("Enter Your Address");
        }else {

            DocumentReference documentReference = db.collection("users").document(mUser.getUid());
            Map<String, Object> driverP = new HashMap<>();
            driverP.put("driverExperience", dExperience);
            driverP.put("dFleetName", dfleetName);
            driverP.put("dVehicleNo", dVehicleno);
            driverP.put("dLicenceNo", dLicenceNo);
            driverP.put("dVehicle", dVehicle);
            driverP.put("dAdd", dAdd);


            documentReference.collection("dprofile").add(driverP).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(DriverEditProfile.this, "Profile Updated Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DriverEditProfile.this, driverProfile.class));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DriverEditProfile.this, "Unable to save profile", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }
}