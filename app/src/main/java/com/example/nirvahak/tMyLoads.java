package com.example.nirvahak;

import static com.example.nirvahak.register.TAG;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class tMyLoads extends AppCompatActivity {
    ImageView attachLoad;
    RecyclerView recyclerView;
    ArrayList<MyLoads> myLoadsArrayList;
    MyLoadAdapter myLoadAdapter;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmy_loads);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data...!");
        progressDialog.setTitle("Nirvahak");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();




        attachLoad=findViewById(R.id.attachLoad);
        recyclerView=findViewById(R.id.MyLoadRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        myLoadsArrayList=new ArrayList<MyLoads>();
        myLoadAdapter=new MyLoadAdapter(tMyLoads.this,myLoadsArrayList);

        //recycler Adapter
        recyclerView.setAdapter(myLoadAdapter);


        //on change listener
        EventChangeListener();




        //onclick listener for attaching new load
        attachLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tMyLoads.this, AttachLoad.class));
            }

        });



    }

    private void EventChangeListener() {
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        //firestore initialise
        db = FirebaseFirestore.getInstance();

        DocumentReference df=db.collection("users").document(mUser.getUid());
        df.collection("Attach_Load")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value.isEmpty()){
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(tMyLoads.this, "No Data Found", Toast.LENGTH_SHORT).show();
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
                                myLoadsArrayList.add(dc.getDocument().toObject(MyLoads.class));
                            }
                            myLoadAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }



                    }}
                });


    }


}
