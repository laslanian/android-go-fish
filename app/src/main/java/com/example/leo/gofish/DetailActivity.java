package com.example.leo.gofish;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DetailActivity extends AppCompatActivity implements AsyncDLResponse, NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawer;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        station = (Station) getIntent().getSerializableExtra("Station");

        DownloadFile df = new DownloadFile(DetailActivity.this);
        if(!(df.getStatus() == AsyncTask.Status.RUNNING)) {
            df.execute(station);
            df.delegate = this;

        }
    }

    @Override
    public void onTaskComplete(Station s) {
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_favourites) {

        } else if (id == R.id.nav_topstations) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
