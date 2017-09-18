package com.slamcode.locationbasedgamelib.model;

import com.slamcode.locationbasedgamelib.model.LocationData;

public class PlaceData {

    private LocationData locationData;

    private String name;

    public LocationData getLocationData() {
        return locationData;
    }

    public void setLocationData(LocationData locationData) {
        this.locationData = locationData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
