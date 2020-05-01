package com.rathore.newsweathertraffic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rathore on 08/08/17.
 */

public class WeatherModel implements Parcelable {
String weatherinfo,temp,humidity,pressure,date;

    public String getWeatherinfo() {
        return weatherinfo;
    }
    public WeatherModel(Parcel in) {
        // put your data using = in.readString();
        this.weatherinfo = in.readString();;
        this.temp = in.readString();;
        this.humidity = in.readString();
        this.date = in.readString();
        this.pressure = in.readString();


    }

    public WeatherModel() {
    }

    public void setWeatherinfo(String weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(weatherinfo);
        dest.writeString(temp);
        dest.writeString(humidity);
        dest.writeString(date);
        dest.writeString(pressure);
    }
    public static final Parcelable.Creator<WeatherModel> CREATOR = new Parcelable.Creator<WeatherModel>() {

        @Override
        public WeatherModel[] newArray(int size) {
            return new WeatherModel[size];
        }

        @Override
        public WeatherModel createFromParcel(Parcel source) {
            return new WeatherModel(source);
        }
    };
}
