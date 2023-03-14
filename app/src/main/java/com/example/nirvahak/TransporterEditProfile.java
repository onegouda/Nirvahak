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

public class TransporterEditProfile extends AppCompatActivity {
    Button btnSaveTransporterProfile;
    EditText editCompanyName,editCompanyLicenceNo,editCompanyAddress;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_edit_profile);
        btnSaveTransporterProfile=findViewById(R.id.btnSaveTransporterProfile);
        editCompanyAddress=findViewById(R.id.editCompanyAddress);
        editCompanyName=findViewById(R.id.editCompanyName);
        editCompanyLicenceNo=findViewById(R.id.editCompanyLicenceNo);


        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        //firestore initialise
        db = FirebaseFirestore.getInstance();



        btnSaveTransporterProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(TransporterEditProfile.this, tProfile.class));
                performClick();
            }
        });

    }

    private void performClick() {
        String tname =editCompanyName.getText().toString();
        String tlin=editCompanyLicenceNo.getText().toString();
        String tadd=editCompanyAddress.getText().toString();


        if (tname.isEmpty()){
            editCompanyName.setError("Enter Company Name");
        }else if(tlin.isEmpty()){
            editCompanyLicenceNo.setError("Enter Company Licence No");
        }else if(tadd.isEmpty()){
            editCompanyAddress.setError("Enter Company Address");
        }else {

            DocumentReference documentReference = db.collection("users").document(mUser.getUid());
            Map<String, Object> tpro = new HashMap<>();
            tpro.put("CompanyName", tname);
            tpro.put("ComapanyLicenceNo", tlin);
            tpro.put("CompanyNo", tadd);

            documentReference.collection("Tprofile").add(tpro).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(TransporterEditProfile.this, "Profile Updated Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TransporterEditProfile.this, tProfile.class));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TransporterEditProfile.this, "Unable to save profile", Toast.LENGTH_SHORT).show();
                }
            });


        }


    }
}