package com.example.nirvahak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class driverAssignment extends AppCompatActivity {
    EditText sSource,sDestination;
    Button btnLoc;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_assignment);
        sSource=findViewById(R.id.sSource);
        sDestination=findViewById(R.id.sDestination);
        btnLoc=findViewById(R.id.btnLoc);


        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get value from EditText

                String SourceLoc=sSource.getText().toString().trim();
                String DestinationLoc=sDestination.getText().toString().trim();

                //Check Condition
                if(SourceLoc.equals("") && DestinationLoc.equals("")){
                    //When Both value are blank
                    Toast.makeText(driverAssignment.this, "Enter Both Location", Toast.LENGTH_SHORT).show();
                }else{
                    //When Both Location are Filled
                    //Display Track
                    DisplayTrack(SourceLoc,DestinationLoc);
                }



            }
        });




    }

    private void DisplayTrack(String sourceLoc, String destinationLoc) {
        // if the device doesnot have a map installed,then redirect it to playstore
        try{
            // when google map installed initialize URI
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + sourceLoc + "/" + destinationLoc);
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