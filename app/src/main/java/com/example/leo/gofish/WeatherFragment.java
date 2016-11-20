package com.example.leo.gofish;

import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Leo on 2016-11-19.
 */

public class WeatherFragment extends Fragment implements ForecastIOResponse {

    private static final String TAG = WeatherFragment.class.getSimpleName();
    TextView summary,temp,feel,wind,pressure; //lat,lng
    ImageView icon;
    private String PACKAGE_NAME="";
    private String[] windDirections;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PACKAGE_NAME= new MainActivity().getClass().getPackage().getName();
        Resources res = getResources();
        windDirections = res.getStringArray(R.array.wind_directions);
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
        forecastIO.delegate=this;
        forecastIO.execute(loc);

        return view;
    }

    Location createNewLocation(double longitude, double latitude) {
        Location location = new Location("");
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        return location;
    }

    @Override
    public void onTaskComplete(Weather weather) {
        summary = (TextView)getView().findViewById(R.id.summary);
        icon = (ImageView)getView().findViewById(R.id.icon);
        //lat = (TextView)getView().findViewById(R.id.latitude);
        //lng = (TextView)getView().findViewById(R.id.longitude);
        temp = (TextView)getView().findViewById(R.id.temperature);
        feel = (TextView)getView().findViewById(R.id.apparentTemperature);
        wind = (TextView)getView().findViewById(R.id.windSpeed);

        pressure = (TextView)getView().findViewById(R.id.pressure);

        summary.setText(weather.getSummary());
        setIcon(weather.getIcon());
        //lat.setText(Double.toString(weather.getLatitude()));
        //lng.setText(Double.toString(weather.getLongitude()));
        temp.setText(weather.temperatureToString());
        feel.setText(weather.apparentTempToString());
        wind.setText(formatWind(weather.getWindSpeed(),weather.getWindBearing()));
        pressure.setText(weather.pressureToString());
    }

    private String formatWind(double speed, int direction){
        return (Math.round(speed)+"km/h "+windBearingToString(direction));
    }
    private void setIcon(String image){
        image=image.replace("-","");
        Log.i(TAG,image);
        icon.setImageResource(getResources().getIdentifier(image, "mipmap", PACKAGE_NAME));
    }
    private String windBearingToString(int windBearing)
    {
        return windDirections[ (int)Math.round((((double)windBearing % 360) / 45)) % 8 ];
    }
}
