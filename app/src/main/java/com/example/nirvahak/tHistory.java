package com.example.nirvahak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class tHistory extends AppCompatActivity {
    EditText sSourcet,sDestinationt;
    Button btnLoct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thistory);
        sSourcet=findViewById(R.id.sSourcet);
        sDestinationt=findViewById(R.id.sDestinationt);
        btnLoct=findViewById(R.id.btnLoct);


        btnLoct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get value from EditText

                String SourceLoct=sSourcet.getText().toString().trim();
                String DestinationLoct=sDestinationt.getText().toString().trim();

                //Check Condition
                if(SourceLoct.equals("") && DestinationLoct.equals("")){
                    //When Both value are blank
                    Toast.makeText(tHistory.this, "Enter Both Location", Toast.LENGTH_SHORT).show();
                }else{
                    //When Both Location are Filled
                    //Display Track
                    DisplayTrack(SourceLoct,DestinationLoct);
                }



            }
        });




    }

    private void DisplayTrack(String sourceLoct, String destinationLoct) {
        // if the device doesnot have a map installed,then redirect it to playstore
        try{
            // when google map installed initialize URI
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + sourceLoct + "/" + destinationLoct);
            //initialize intent with action view
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            //set packages
            intent.setPackage("com.google.android.apps.maps");
            //set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //start activity
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            //when google map is not installed
            //initialize uri
            Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            //initialize intent with action view
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            //set flags
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //start activity
            startActivity(intent);
        }


    }
    }
