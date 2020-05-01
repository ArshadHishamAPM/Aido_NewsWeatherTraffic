package com.rathore.newsweathertraffic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rathore on 09/08/17.
 */

public class CustomAdapter extends ArrayAdapter {
    ArrayList<WeatherModel> list;
    LayoutInflater inflater;
    public CustomAdapter(Context context,int  resource,ArrayList<WeatherModel> list) {
        super(context, resource,list);
        this.list =  list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // View view;
        WeatherModel obj = list.get(position);
        convertView=inflater.inflate(R.layout.customadapterlayout,parent,false);
        TextView temp = (TextView)convertView.findViewById(R.id.temp);
        TextView humidity = (TextView)convertView.findViewById(R.id.humidity);
        TextView date = (TextView)convertView.findViewById(R.id.date);
        TextView pressure = (TextView)convertView.findViewById(R.id.pressure);
        TextView content = (TextView)convertView.findViewById(R.id.content);
        Log.i("temp,humi,date,pres",obj.getTemp()+" "+obj.getHumidity()+" "+obj.getDate()+" "+obj.getPressure());
        temp.setText("Temp: "+obj.getTemp()+"k");
        date.setText("Date And Time: "+list.get(position).getDate());
        humidity.setText("Humidity: "+obj.getHumidity()+"%");
        pressure.setText("Pressure: "+obj.getPressure()+"hPa");
        content.setText(obj.getWeatherinfo());





        return convertView;
    }
}
