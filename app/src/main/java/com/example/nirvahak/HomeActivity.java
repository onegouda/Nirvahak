package com.example.nirvahak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    TextView driver,fleetOwner,transporter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        driver=findViewById(R.id.driver);
        transporter=findViewById(R.id.transporter);
        fleetOwner=findViewById(R.id.fleetOwner);




        //driver page
        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(),Driver.class));

            }
        });


        //fleet owner page
        fleetOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(),FleetOwner.class));

            }
        });

        //transporter page
        transporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Transporter.class));

            }
        });


    }
}