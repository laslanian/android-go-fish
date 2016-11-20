package com.example.leo.gofish;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Leo on 2016-11-19.
 */

public class ForecastIO extends AsyncTask<Location, Void, Weather> {

    private static final String TAG = ForecastIO.class.getSimpleName();
    private final String key = "e281e3e3f062b04bae195d56fb181e1a/";
    private final String url = "https://api.forecast.io/forecast/";

    public ForecastIOResponse delegate = null;

    @Override
    protected Weather doInBackground(Location... params) {
        Weather weather=null;
        Location location = params[0];
        String reqUrl = url + key+location.getLongitude()+","+location.getLatitude();

        HTTPHandler http = new HTTPHandler();

        String jsonStr = http.makeServiceCall(reqUrl);

         Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject weatherObj = new JSONObject(jsonStr);
                JSONObject conditions = weatherObj.getJSONObject("currently");

                 weather = new Weather(
                        weatherObj.getDouble("latitude"),
                        weatherObj.getDouble("longitude"),
                        conditions.getString("summary"),
                        conditions.getString("icon"),
                        conditions.getDouble("temperature"),
                        conditions.getDouble("apparentTemperature"),
                        conditions.getDouble("windSpeed"),
                        windBearingToString(conditions.getInt("windBearing")),
                        conditions.getDouble("pressure")
                );

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json.");
        }

        return weather;
    }

    @Override
    protected void onPostExecute(Weather weather){
        delegate.onTaskComplete(weather);
    }


    private String windBearingToString(int windBearing)
    {
        String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        return directions[ (int)Math.round((  ((double)windBearing % 360) / 45)) % 8 ];
    }

}