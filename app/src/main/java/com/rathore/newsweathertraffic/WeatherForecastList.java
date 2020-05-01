package com.rathore.newsweathertraffic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WeatherForecastList extends AppCompatActivity {
ListView listView;
    ArrayList<WeatherModel> list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast_list);
        Intent intent = getIntent();
        if(intent!=null&&intent.getAction().equals("WeatherForcast")){
            list1 = intent.getParcelableArrayListExtra("wheatherModel");

            Log.i("weatherdatalist",list1.toString());
        }
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),0,list1);

        listView = (ListView) findViewById(R.id.listview);
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),R.layout.listtextview,list1);
        listView.setAdapter(customAdapter);
    }
}
