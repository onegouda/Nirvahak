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

public class Driver extends AppCompatActivity {
    public DrawerLayout drawerLayout3;
    NavigationView navigationViewHome;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolBar;
    TextView driverProfile,driverAssignment;
    Button btnLogoutD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        drawerLayout3 = findViewById(R.id.drawer_layout3);
        driverProfile=findViewById(R.id.driverProfile);
        driverAssignment=findViewById(R.id.driverAssignment);
        btnLogoutD=findViewById(R.id.btnLogoutD);
        toolBar = findViewById(R.id.toolBar);
        navigationViewHome = findViewById(R.id.navigationViewHome);
        setSupportActionBar(toolBar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout3, toolBar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout3.addDrawerListener(toggle);
        toggle.syncState();



        //intent to driver profile
        driverProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Driver.this,driverProfile.class);
                startActivity(intent);
            }
        });


        //intent to driver assignment
        driverAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Driver.this,driverAssignment.class);
                startActivity(intent);
            }
        });


        btnLogoutD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
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