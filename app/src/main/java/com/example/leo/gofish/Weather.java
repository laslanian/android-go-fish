package com.example.leo.gofish;

/**
 * Created by Leo on 2016-11-19.
 */

public class Weather {
    private String summary;
    private String icon;
    private double latitude;
    private double longitude;

    private double temperature;
    private double apparentTemperature;
    private double windSpeed;
    private String windBearing;
    private double pressure;

    public Weather() {
        this.latitude = 0;
        this.longitude = 0;
        this.summary ="";
        this.icon="";
        this.temperature=0;
        this.apparentTemperature=0;
        this.windSpeed=0;
        this.windBearing="";
        this.pressure=0;
    }
    //basic weather
    public Weather(double latitude, double longitude, double temperature, double windSpeed, String windBearing, double pressure) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature=temperature;
        this.windSpeed=windSpeed;
        this.windBearing=windBearing;
        this.pressure=pressure;
    }

    //detail weather
    public Weather(double latitude, double longitude,String summary, String icon, double temperature, double apparentTemperature, double windSpeed, String windBearing, double pressure) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.summary =summary;
        this.icon=icon;
        this.temperature=temperature;
        this.apparentTemperature=apparentTemperature;
        this.windSpeed=windSpeed;
        this.windBearing=windBearing;
        this.pressure=pressure;

    }



}
