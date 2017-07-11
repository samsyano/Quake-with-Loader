package com.example.samson.quake_with_loader;

/**
 * Created by SAMSON on 7/2/2017.
 */

public class Weather {

    public  String mUrl;

    String magnitude, location, date;

    public Weather(String magnitude, String location, String description, String mUrl) {
        this.magnitude = magnitude;
        this.location = location;
        this.date = description;
        this.mUrl = mUrl;
    }

    public String getmUrl() {
        return mUrl;
    }


    public String getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }
}
