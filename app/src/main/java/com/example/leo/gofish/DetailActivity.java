package com.example.leo.gofish;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DetailActivity extends AppCompatActivity implements AsyncDLResponse{
    private Station station;
    private TextView mStationId;
    private TextView mStationName;
    private TextView mStationProvince;
    private TextView mStationLong;
    private TextView mStationLat;
    private TextView mStationgWaterLevel;
    private TextView mStationDischarge;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        station=(Station) getIntent().getSerializableExtra("Station");

        DownloadFile df = new DownloadFile(DetailActivity.this);
        if(!(df.getStatus() == AsyncTask.Status.RUNNING)) {
            df.execute(station);
            df.delegate = this;
        }
    }

    @Override
    public void onTaskComplete(Station s) {
        //station.setFileName(s.getFileName());
        init();
        mStationId = (TextView) findViewById(R.id.station_id);
        mStationId.setText("Station Id: " + station.getId());

        mStationName = (TextView)findViewById(R.id.station_name);
        mStationName.setText("Station Name: " + station.getName());

        mStationProvince = (TextView)findViewById(R.id.station_province);
        mStationProvince.setText("Province: " + station.getProvince());

        mStationLat = (TextView)findViewById(R.id.station_lat);
        mStationLat.setText("Latitude: " + Double.toString(station.getLatitude()));

        mStationLong = (TextView)findViewById(R.id.station_long);
        mStationLong.setText("Longitude: " + Double.toString(station.getLongitude()));

        mStationgWaterLevel = (TextView)findViewById(R.id.station_waterlevel);
        mStationgWaterLevel.setText("Water Level: " + Double.toString(station.getWaterLevel()));

        mStationDischarge = (TextView)findViewById(R.id.station_discharge);
        mStationDischarge.setText("Discharge: " + Double.toString(station.getDischarge()));
    }

    public void init() {
        try {
            InputStream inputstream = new FileInputStream(getFilesDir() + "/stations/hourly/" + station.getFileName());
            CSVFile csv = new CSVFile(inputstream);
            station = csv.readStation(station);
        } catch (IOException e) {
            Log.i("Detail Fragment: ", "File not found");
        }
    }
    @Override
    public void onBackPressed() {
       //super.onBackPressed();
       finish();
    }
}
