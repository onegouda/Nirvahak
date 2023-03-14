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

public class LoadMarket extends AppCompatActivity {
    RecyclerView recyclerViewLoadMarket;
    ArrayList<LoadMarket1>loadMarket1ArrayList;
    LoadMarketAdapter loadMarketAdapter;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_market);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data...!");
        progressDialog.setTitle("Nirvahak");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        recyclerViewLoadMarket=findViewById(R.id.recyclerViewLoadMarket);
        recyclerViewLoadMarket.setHasFixedSize(true);
        recyclerViewLoadMarket.setLayoutManager(new LinearLayoutManager(this));


        loadMarket1ArrayList=new ArrayList<LoadMarket1>();
        loadMarketAdapter=new LoadMarketAdapter(LoadMarket.this,loadMarket1ArrayList);

        //Recycler Adapter
        recyclerViewLoadMarket.setAdapter(loadMarketAdapter);

        //on change listener
        EventChangeListener();


    }

    private void EventChangeListener() {

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        //firestore initialise
        db = FirebaseFirestore.getInstance();
        DocumentReference df=db.collection("admin").document("transporter");
        df.collection("loads")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value.isEmpty()){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(LoadMarket.this, "No Data Found", Toast.LENGTH_SHORT).show();
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
                                    loadMarket1ArrayList.add(dc.getDocument().toObject(LoadMarket1.class));
                                }
                                loadMarketAdapter.notifyDataSetChanged();
                                if(progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }



                        }}
                });

    }
}