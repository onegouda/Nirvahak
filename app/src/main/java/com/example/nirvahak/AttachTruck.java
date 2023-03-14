package com.example.nirvahak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AttachTruck extends AppCompatActivity {

    Button btnAttachTruck;
    RadioGroup atCategory;
    RadioButton HCV, LCV;
    EditText vehicleNameAt, vehicleNumberAt, Tonnage, OwnerNo;
    String cat;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach_truck);

        HCV = findViewById(R.id.HCV);
        LCV = findViewById(R.id.LCV);
        btnAttachTruck = findViewById(R.id.btnAttachTruck);
        atCategory = findViewById(R.id.atCategory);
        vehicleNameAt = findViewById(R.id.vehicleNameAt);
        vehicleNumberAt = findViewById(R.id.vehicleNumberAt);
        Tonnage = findViewById(R.id.Tonnage);
        OwnerNo = findViewById(R.id.OwnerNo);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //firestore initialise
        db = FirebaseFirestore.getInstance();


        atCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.LCV) {
                    cat = "Light Weight Commercial Vehicle";
                } else {
                    cat = "Heavy Weight Commercial Vehicle";
                }

            }
        });


        /*
        //Spinner Code For HCV Category

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.HCVCategoryAt, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        HCVcategory.setAdapter(adapter);

        //select listener

        HCVcategory.setOnItemSelectedListener(this);
        */


        //Attach Truck Button Set on Click listener
        btnAttachTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(AttachTruck.this, MyTruck.class));
                performClick();
            }
        });


    }

    private void performClick() {
        String tCat = cat;
        String tReg = vehicleNumberAt.getText().toString();
        String tname = vehicleNameAt.getText().toString();
        String tTon = Tonnage.getText().toString();
        String tOno = OwnerNo.getText().toString();


        if (tname.isEmpty()) {
            vehicleNameAt.setError("Required Name");
        } else if (tReg.isEmpty()) {
            vehicleNumberAt.setError("Required Register No");
        } else if (tTon.isEmpty()) {
            Tonnage.setError("Required Tonnage");
        } else if (tOno.isEmpty()) {
            OwnerNo.setError("Required Owner No");
        } else {

            DocumentReference documentReference = db.collection("users").document(mUser.getUid());
            Map<String, Object> tOwner = new HashMap<>();
            tOwner.put("TruckCategory", tCat);
            tOwner.put("TruckRegisterNo", tReg);
            tOwner.put("Truck", tname);
            tOwner.put("TruckTonnage", tTon);
            tOwner.put("TruckOwnerNo", tOno);

            documentReference.collection("Attach_Truck").add(tOwner).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(AttachTruck.this, "Truck Attached", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AttachTruck.this, MyTruck.class));

                    String tCat = cat;
                    String tReg = vehicleNumberAt.getText().toString();
                    String tname = vehicleNameAt.getText().toString();
                    String tTon = Tonnage.getText().toString();
                    String tOno = OwnerNo.getText().toString();


                    DocumentReference admin = db.collection("admin").document("truckmarket");
                    Map<String, Object> tOwner = new HashMap<>();
                    tOwner.put("TruckCategory1", tCat);
                    tOwner.put("TruckRegisterNo1", tReg);
                    tOwner.put("Truck1", tname);
                    tOwner.put("TruckTonnage1", tTon);
                    tOwner.put("TruckOwnerNo1", tOno);


                    admin.collection("Trucks").add(tOwner);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AttachTruck.this, "Unable to Attach Your Truck", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }
}