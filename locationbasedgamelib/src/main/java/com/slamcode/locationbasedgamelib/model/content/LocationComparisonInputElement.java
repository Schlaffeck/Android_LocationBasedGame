package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.location.LocationDataHelper;
import com.slamcode.locationbasedgamelib.model.InputCommitParameters;
import com.slamcode.locationbasedgamelib.model.InputContent;
import com.slamcode.locationbasedgamelib.model.InputResult;
import com.slamcode.locationbasedgamelib.model.LocationData;

import java.util.ArrayList;
import java.util.List;

/**
 * Game task content element comparing current with given location within acceptable radius
 */

public final class LocationComparisonInputElement extends InputContentElementAbstract<LocationData> {

    public static final String CONTENT_TYPE = "LOCATION_COMPARISON_INPUT";

    public final static int CONTENT_TYPE_ID = CONTENT_TYPE.hashCode();

    private String contentType = CONTENT_TYPE;
    private final LocationData targetLocation;
    private final float acceptableDistanceMeters;
    private final String commitCommandName;

    public LocationComparisonInputElement(LocationData targetLocation,  float acceptableDistanceMeters, String commitCommandName) {
        this.targetLocation = targetLocation;
        this.acceptableDistanceMeters = acceptableDistanceMeters;
        this.commitCommandName = commitCommandName;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public int getContentTypeId() {
        return CONTENT_TYPE_ID;
    }

    public String getCommitCommandName() {
        return commitCommandName;
    }

    @Override
    public LocationComparisonResult commitInput(LocationData currentLocation) {
        InputCommitParameters<LocationData> parameters = new InputCommitParameters<>(currentLocation);
        this.onInputCommitting(parameters);

        currentLocation = parameters.getValue();
        LocationComparisonResult result = new LocationComparisonResult();
        if(currentLocation != null && currentLocation.isDetermined()) {
            result.setInputCorrect(LocationDataHelper.isNearBy(this.targetLocation, currentLocation, this.acceptableDistanceMeters));
            result.setCurrentLocationAvailable(true);
            result.setDistanceFromTargetMeters(LocationDataHelper.countDistanceFrom(currentLocation, this.targetLocation));
        }
        else
            result.setCurrentLocationAvailable(false);

        this.onInputCommitted(result);
        return result;
    }

    /**
     * Extends input result by additional location related data
     */
    public class LocationComparisonResult extends InputResult
    {
        private boolean currentLocationAvailable;

        private float distanceFromTargetMeters;

        public float getDistanceFromTargetMeters() {
            return distanceFromTargetMeters;
        }

        void setDistanceFromTargetMeters(float distanceFromTargetMeters) {
            this.distanceFromTargetMeters = distanceFromTargetMeters;
        }

        public boolean isCurrentLocationAvailable() {
            return currentLocationAvailable;
        }

        void setCurrentLocationAvailable(boolean currentLocationAvailable) {
            this.currentLocationAvailable = currentLocationAvailable;
        }
    }
}
