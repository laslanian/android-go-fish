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
    private final String key = "0123456789abcdef9876543210fedcba/";
    private final String url = "https://api.darksky.net/forecast/";

    public AsyncResponse delegate = null;

    @Override
    protected Weather doInBackground(Location... params) {
        Location location = params[0];
        String reqUrl = url + key+location.getLatitude()+","+location.getLongitude();

        HTTPHandler http = new HTTPHandler();

        String jsonStr = http.makeServiceCall(reqUrl);

        // Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject weatherObj = new JSONObject(jsonStr);
                JSONObject conditions = weatherObj.getJSONObject("currently");

                Weather weather = new Weather(
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

        return null;
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