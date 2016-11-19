package com.example.leo.gofish;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Leo on 2016-11-19.
 */

public class WeatherFragment extends Fragment implements AsyncResponse{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment, container, false);

        Location loc = createNewLocation(44.041870,-79.502407);

        ForecastIO forecastIO = new ForecastIO();
        forecastIO.execute(loc);

        return view;
    }


    Location createNewLocation(double longitude, double latitude) {
        Location location = new Location("dummyprovider");
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        return location;
    }

    @Override
    public void onTaskComplete(Weather weather) {
        TextView summary = (TextView)getView().findViewById(R.id.summary);
        TextView icon = (TextView)getView().findViewById(R.id.icon);
        TextView lat = (TextView)getView().findViewById(R.id.latitude);
        TextView lng = (TextView)getView().findViewById(R.id.longitude);
        TextView temp = (TextView)getView().findViewById(R.id.temperature);
        TextView feel = (TextView)getView().findViewById(R.id.apparentTemperature);
        TextView wind = (TextView)getView().findViewById(R.id.windSpeed);
        TextView windDir = (TextView)getView().findViewById(R.id.windBearing);
        TextView pressure = (TextView)getView().findViewById(R.id.pressure);
    }
}
