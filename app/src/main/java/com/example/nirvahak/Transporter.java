package com.example.nirvahak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Transporter extends AppCompatActivity {
    TextView truckMarket,tHistory,tMyLoads,tProfile;
    public DrawerLayout drawerLayout2;
    NavigationView navigationViewHome;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolBar;
    Button btnLogoutT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter);
        drawerLayout2=findViewById(R.id.drawer_layout2);
        truckMarket=findViewById(R.id.truckMarket);
        tHistory=findViewById(R.id.tHistory);
        tMyLoads=findViewById(R.id.tMyLoads);
        tProfile=findViewById(R.id.tProfile);
        toolBar = findViewById(R.id.toolBar);
        btnLogoutT=findViewById(R.id.btnLogoutT);
        navigationViewHome = findViewById(R.id.navigationViewHome);
        setSupportActionBar(toolBar);


        //drawer bar (side bar)

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout2, toolBar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout2.addDrawerListener(toggle);
        toggle.syncState();


        //intent to load market
        truckMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Transporter.this,TruckMarket.class);
                startActivity(intent);
            }
        });

        //Button to Logout

        btnLogoutT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });




        //intent to Transporter history
        tHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Transporter.this,tHistory.class);
                startActivity(intent);
            }
        });


        //intent to My Loads
        tMyLoads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),tMyLoads.class));
                finish();
            }
        });


        //intent to Transporter Profile
        tProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Transporter.this,tProfile.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}