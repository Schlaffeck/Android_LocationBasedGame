package com.slamcode.locationbasedgamelib.model;

import android.location.Location;

/**
 * LocationData information model object
 */

public class LocationData {

    private double latitude;
    private double longitude;
    private boolean determined;

    public LocationData()
    {
        this.determined = false;
    }

    public LocationData(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.determined = true;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isDetermined() {
        return determined;
    }
}
