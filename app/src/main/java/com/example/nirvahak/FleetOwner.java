package com.example.nirvahak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class FleetOwner extends AppCompatActivity {
    TextView foLoadMarket,foHistory,foMyTruck,foProfile;
    public DrawerLayout drawerLayout1;
    NavigationView navigationViewHome;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolBar;
    Button btnLogoutF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_owner);
        drawerLayout1 = findViewById(R.id.drawer_layout1);
        foLoadMarket=findViewById(R.id.foLoadMarket);
        foHistory=findViewById(R.id.foHistory);
        foMyTruck=findViewById(R.id.foMyTruck);
        foProfile=findViewById(R.id.foProfile);
        toolBar = findViewById(R.id.toolBar);
        navigationViewHome = findViewById(R.id.navigationViewHome);
        btnLogoutF=findViewById(R.id.btnLogoutF);
        setSupportActionBar(toolBar);


        //drawer bar (side bar)

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout1, toolBar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout1.addDrawerListener(toggle);
        toggle.syncState();


        //Button Logout
        btnLogoutF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });



        //intent to load market
        foLoadMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FleetOwner.this,LoadMarket.class);
                startActivity(intent);
            }
        });

        //intent to history
        foHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FleetOwner.this,fHistory.class);
                startActivity(intent);
            }
        });

        //intent to My truck
        foMyTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FleetOwner.this,MyTruck.class);
                startActivity(intent);
            }
        });


        //intent to Profile
        foProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FleetOwner.this,fProfile.class);
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