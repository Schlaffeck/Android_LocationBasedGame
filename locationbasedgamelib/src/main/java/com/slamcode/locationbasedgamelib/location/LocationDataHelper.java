package com.slamcode.locationbasedgamelib.location;

import android.location.Location;

import com.slamcode.locationbasedgamelib.model.LocationData;

/**
 * Set of methods facilitating use of lcoation data
 */

public class LocationDataHelper {

    /**
     * Counts approximated distance between two location data
     * @param thisLocation
     * @param otherLocation
     * @return Distance between locations in meters
     */
    public static float countDistanceFrom(LocationData thisLocation, LocationData otherLocation)
    {
        float[] result = new float[2];

        Location.distanceBetween(otherLocation.getLatitude(), otherLocation.getLongitude(), thisLocation.getLatitude(), thisLocation.getLongitude(), result);

        return result[0];
    }

    /**
     * Checks whether one location is near to other one by given acceptable distance
     * @param targetLocation
     * @param currentLocation
     * @param acceptableDistanceMeters
     * @return If two locations are near by or not
     */
    public static boolean isNearBy(LocationData targetLocation, LocationData currentLocation, float acceptableDistanceMeters)
    {
        if(targetLocation != null && targetLocation.isDetermined()
                && currentLocation != null && currentLocation.isDetermined())
        {
            return countDistanceFrom(currentLocation, targetLocation) <= acceptableDistanceMeters;
        }

        return false;
    }
}
