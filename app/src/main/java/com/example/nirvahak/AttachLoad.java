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

public class AttachLoad extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btnAttachLoad;
    Spinner LoadCategory;
    EditText LoadSource, LoadDestination, LoadTonnage, LoadRent, TransporterNo;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach_load);
        btnAttachLoad = findViewById(R.id.btnAttachLoad);
        LoadCategory = findViewById(R.id.LoadCategory);
        LoadSource = findViewById(R.id.LoadSource);
        LoadDestination = findViewById(R.id.LoadDestination);
        LoadTonnage = findViewById(R.id.LoadTonnage);
        LoadRent = findViewById(R.id.LoadRent);
        TransporterNo = findViewById(R.id.TransporterNo);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //firestore initialise
        db = FirebaseFirestore.getInstance();


        //Spinner Code For Load Category

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.LoadCategoryAl, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LoadCategory.setAdapter(adapter);


        //select listener

        LoadCategory.setOnItemSelectedListener(this);

        //Attach Truck Button Set on Click listener
        btnAttachLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AttachLoad.this, tMyLoads.class));
                performClick();
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void performClick() {
        String lCat = LoadCategory.getSelectedItem().toString();
        String lSor = LoadSource.getText().toString();
        String lDest = LoadDestination.getText().toString();
        String lTon = LoadTonnage.getText().toString();
        String lCost = LoadRent.getText().toString();
        String lOwnerNo = TransporterNo.getText().toString();

       if (lSor.isEmpty())
        {
            LoadSource.setError("Please Enter Source Location");
        }else if (lDest.isEmpty()){
            LoadDestination.setError("Please Enter Destination Location");
        }else if(lTon.isEmpty()){
            LoadTonnage.setError("Please Enter Tonnage");

        }else if(lCost.isEmpty()){
            LoadRent.setError("Please Enter Rent");
        }else if(lOwnerNo.isEmpty()){
            TransporterNo.setError("Enter Phone Number");
        }else {




        DocumentReference documentReference = db.collection("users").document(mUser.getUid());
        Map<String, Object> lOwner = new HashMap<>();
        lOwner.put("LoadCategory", lCat);
        lOwner.put("LoadSource", lSor);
        lOwner.put("LoadDestination", lDest);
        lOwner.put("LoadTonnage", lTon);
        lOwner.put("LoadRent", lCost);
        lOwner.put("TransporterNo", lOwnerNo);

        documentReference.collection("Attach_Load").add(lOwner).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AttachLoad.this, "Load Attached", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AttachLoad.this, tMyLoads.class));


                String lCat = LoadCategory.getSelectedItem().toString();
                String lSor = LoadSource.getText().toString();
                String lDest = LoadDestination.getText().toString();
                String lTon = LoadTonnage.getText().toString();
                String lCost = LoadRent.getText().toString();
                String lOwnerNo = TransporterNo.getText().toString();

                DocumentReference admin = db.collection("admin").document("transporter");
                Map<String, Object> alOwner = new HashMap<>();
                alOwner.put("LoadCategory1", lCat);
                alOwner.put("LoadSource1", lSor);
                alOwner.put("LoadDestination1", lDest);
                alOwner.put("LoadTonnage1", lTon);
                alOwner.put("LoadRent1", lCost);
                alOwner.put("TransporterNo1", lOwnerNo);

                admin.collection("loads").add(alOwner);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AttachLoad.this, "Unable to Attach", Toast.LENGTH_SHORT).show();
            }
        });


        }

    }

}