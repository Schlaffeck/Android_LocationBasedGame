package com.slamcode.locationbasedgame.service.location;

/**
 * Set of configuration options for LocationTracker service
 */

public final class LocationTrackerConfiguration {

    private long minimalDistanceToUpdateLocationMeters = 10;

    private long minimalTimeBetweenUpdatesMillis = 60_000;

    public LocationTrackerConfiguration()
    {
    }

    public LocationTrackerConfiguration(long minimalDistanceMeters, long minimalTimeMillis)
    {
        this.minimalDistanceToUpdateLocationMeters = minimalDistanceMeters;
        this.minimalTimeBetweenUpdatesMillis = minimalTimeMillis;
    }

    public long getMinimalDistanceToUpdateLocationMeters() {
        return minimalDistanceToUpdateLocationMeters;
    }

    public long getMinimalTimeBetweenUpdatesMillis() {
        return minimalTimeBetweenUpdatesMillis;
    }
}
