package com.example.leo.gofish;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Karlo on 11/15/2016.
 */

public class Station {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private String province;
    private double waterLevel;
    private double discharge;

    public Station() {
        this.id = "";
        this.name = "";
        this.latitude = 0;
        this.longitude = 0;
        this.province = "";
        this.waterLevel = 0;
        this.discharge = 0;
    }

    public Station(String id, String name, double latitude, double longitude, String province) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.province = province;
        this.waterLevel = 0;
        this.discharge = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getDischarge() {
        return discharge;
    }

    public void setDischarge(double discharge) {
        this.discharge = discharge;
    }

    public double getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(double waterLevel) {
        this.waterLevel = waterLevel;
    }

    @Override
    public String toString() {
        return name + ", " + province;
    }
}
