package com.example.nirvahak;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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

public class TruckMarket extends AppCompatActivity {
    RecyclerView recyclerViewTruckMarket;
    ArrayList<TruckMarket1>truckMarket1ArrayList;
    TruckMarketAdapter truckMarketAdapter;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_market);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data...!");
        progressDialog.setTitle("Nirvahak");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        recyclerViewTruckMarket=findViewById(R.id.recyclerViewTruckMarket);
        recyclerViewTruckMarket.setHasFixedSize(true);
        recyclerViewTruckMarket.setLayoutManager(new LinearLayoutManager(this));

        truckMarket1ArrayList=new ArrayList<TruckMarket1>();
        truckMarketAdapter=new TruckMarketAdapter(TruckMarket.this,truckMarket1ArrayList);

        //Recycler Adapter
        recyclerViewTruckMarket.setAdapter(truckMarketAdapter);

        //on change listener
        EventChangeListener();

    }

    private void EventChangeListener() {
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        //firestore initialise
        db = FirebaseFirestore.getInstance();

        DocumentReference df=db.collection("admin").document("truckmarket");
        df.collection("Trucks")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value.isEmpty()){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(TruckMarket.this, "No Data Found", Toast.LENGTH_SHORT).show();
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
                                    truckMarket1ArrayList.add(dc.getDocument().toObject(TruckMarket1.class));
                                }
                                truckMarketAdapter.notifyDataSetChanged();
                                if(progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }

                        }}
                });
    }
}