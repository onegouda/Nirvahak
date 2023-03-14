package com.example.nirvahak;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyTruck extends AppCompatActivity {
        ImageView attachTruck;
        RecyclerView recyclerViewMyTruck;
        ArrayList<MyTruck1>myTruck1ArrayList;
        MyTruckAdapter myTruckAdapter;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_truck);
        attachTruck=findViewById(R.id.attachTruck);



        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data...!");
        progressDialog.setTitle("Nirvahak");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        recyclerViewMyTruck=findViewById(R.id.recyclerViewMyTruck);
        recyclerViewMyTruck.setHasFixedSize(true);
        recyclerViewMyTruck.setLayoutManager(new LinearLayoutManager(this));

        myTruck1ArrayList=new ArrayList<MyTruck1>();
        myTruckAdapter=new MyTruckAdapter(MyTruck.this,myTruck1ArrayList);


        //Recycler Adapter
        recyclerViewMyTruck.setAdapter(myTruckAdapter);


        //on change listener
        EventChangeListener();


        attachTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyTruck.this,AttachTruck.class));
            }
        });



    }

    private void EventChangeListener() {

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        //firestore initialise
        db = FirebaseFirestore.getInstance();


        DocumentReference df=db.collection("users").document(mUser.getUid());
        df.collection("Attach_Truck")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value.isEmpty()){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(MyTruck.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(error!=null){
                                if(progressDialog.isShowing())
                                    progressDialog.dismiss();
                                Log.e( "FireStore Error ", error.getMessage());
                                return;
                            }
                            for (DocumentChange dc: value.getDocumentChanges()){
                                if(dc.getType()==DocumentChange.Type.ADDED){
                                    myTruck1ArrayList.add(dc.getDocument().toObject(MyTruck1.class));
                                }
                                myTruckAdapter.notifyDataSetChanged();
                                if(progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }



                        }}
                });

    }
}