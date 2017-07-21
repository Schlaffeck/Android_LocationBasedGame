package com.slamcode.locationbasedgamelib.location;

/**
 * Set of configuration options for LocationTracker service
 */

public final class LocationTrackerConfiguration {

    private float minimalDistanceToUpdateLocationMeters;

    private long minimalTimeBetweenUpdatesMillis;

    public LocationTrackerConfiguration()
    {
    }

    public LocationTrackerConfiguration(float minimalDistanceMeters, long minimalTimeMillis)
    {
        this.minimalDistanceToUpdateLocationMeters = minimalDistanceMeters;
        this.minimalTimeBetweenUpdatesMillis = minimalTimeMillis;
    }

    public float getMinimalDistanceToUpdateLocationMeters() {
        return minimalDistanceToUpdateLocationMeters;
    }

    public long getMinimalTimeBetweenUpdatesMillis() {
        return minimalTimeBetweenUpdatesMillis;
    }
}
