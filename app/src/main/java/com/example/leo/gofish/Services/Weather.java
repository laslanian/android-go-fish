package com.example.leo.gofish.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Weather extends Service {
    private final String API_KEY="7f951e1e096f774a";
    //ex. http://api.wunderground.com/api/7f951e1e096f774a/geolookup/q/37.776289,-122.395234.json
    private final String WUN_WEATHER_API="http://api.wunderground.com/api/"+API_KEY+"/geolookup/q/";
    //ex. http://forecast.weather.gov/MapClick.php?FcstType=json&lat=31.4539&lon=-99.6025
    private final String GOV_WEATHER_API="http://forecast.weather.gov/MapClick.php?FcstType=json&";

    public Weather() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /*
    *Build request url for Wunderground API using lat/long
    *Return type based on format param => XML/JSON
    */
    private String getReqUrl(double lat, double lng, String format)
    {
        return WUN_WEATHER_API+lat+","+lng+(format.equals("XML")? ".xml":".json");
    }
}
