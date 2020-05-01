package com.rathore.newsweathertraffic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rathore on 15/08/17.
 */

public class RecylerviewAdapter extends RecyclerView.Adapter<RecylerviewAdapter.ViewHolder> {
Context context;
    ArrayList<WeatherModel> list;

public RecylerviewAdapter(Context context, ArrayList<WeatherModel> list){
    this.context = context;
    this.list = list;

}



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("onCreateViewHolder","called");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerviewlayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeatherModel weatherModel = list.get(position);
        double temp = Double.parseDouble(weatherModel.getTemp());
        int temp1 =  (int) (temp-273.15);
        Log.i("weathorinAdapter",weatherModel.getWeatherinfo()+ " " +weatherModel.getTemp()+" "+weatherModel.getHumidity());
        holder.date.setText(weatherModel.getDate());
        holder.weather.setText(weatherModel.getWeatherinfo());
        holder.temp.setText("Temprature : "+temp1+"°c");
        holder.humidity.setText("Humidity : "+weatherModel.getHumidity()+"%");
        holder.pressure.setText("Pressure : "+weatherModel.getPressure()+"hPa");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView weather;
        TextView temp;
        TextView humidity;
        TextView pressure;


        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            weather = (TextView) itemView.findViewById(R.id.weather1);
            temp = (TextView) itemView.findViewById(R.id.temp1);
            humidity = (TextView) itemView.findViewById(R.id.humidity1);
            pressure = (TextView) itemView.findViewById(R.id.pressure1);

        }
    }
}
