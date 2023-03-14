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

public class fHistory extends AppCompatActivity {
    EditText sSourcef,sDestinationf;
    Button btnLocf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fhistory);
        sSourcef=findViewById(R.id.sSourcef);
        sDestinationf=findViewById(R.id.sDestinationf);
        btnLocf=findViewById(R.id.btnLocf);


        btnLocf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get value from EditText

                String SourceLocf=sSourcef.getText().toString().trim();
                String DestinationLocf=sDestinationf.getText().toString().trim();

                //Check Condition
                if(SourceLocf.equals("") && DestinationLocf.equals("")){
                    //When Both value are blank
                    Toast.makeText(fHistory.this, "Enter Both Location", Toast.LENGTH_SHORT).show();
                }else{
                    //When Both Location are Filled
                    //Display Track
                    DisplayTrack(SourceLocf,DestinationLocf);
                }



            }
        });




    }

    private void DisplayTrack(String SourceLocf, String DestinationLocf) {
        // if the device doesnot have a map installed,then redirect it to playstore
        try{
            // when google map installed initialize URI
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + SourceLocf + "/" + DestinationLocf);
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
