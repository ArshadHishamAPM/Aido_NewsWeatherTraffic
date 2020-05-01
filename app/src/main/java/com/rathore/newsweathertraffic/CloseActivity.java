package com.rathore.newsweathertraffic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CloseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);
        Intent intentbr = getIntent();
        if(intentbr!=null){
            if(intentbr.getAction().equals("com.rathore.newsweathertraffic.CloseActivity"));

            String data = intentbr.getStringExtra("data");

            if(data!=null){

                if(data.equals("close it")){
                    finishAffinity();


                    System.exit(0);

                    //this.finish();
                    // finishAndRemoveTask();
                    //this.onBackPressed();
                }
            }}

    }
}
