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

public final class LocationComparisonInputElement implements InputContent<LocationData> {

    public static final String CONTENT_TYPE = "LOCATION_COMPARISON_INPUT";

    public final static int CONTENT_TYPE_ID = CONTENT_TYPE.hashCode();

    private String contentType = CONTENT_TYPE;
    private List<OnInputCommittedListener> listeners = new ArrayList<>();
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

        LocationComparisonResult result = new LocationComparisonResult();
        if(currentLocation != null) {
            result.setInputCorrect(LocationDataHelper.isNearBy(this.targetLocation, currentLocation, this.acceptableDistanceMeters));
            result.setCurrentLocationAvailable(true);
            result.setDistanceFromTargetMeters(LocationDataHelper.countDistanceFrom(currentLocation, this.targetLocation));
        }
        else
            result.setCurrentLocationAvailable(false);

        this.onInputCommitted(result);
        return result;
    }

    @Override
    public void onInputCommitting(InputCommitParameters<LocationData> parameters) {
        for (OnInputCommittedListener listener : this.listeners) {
            listener.inputCommitting(parameters);
        }
    }

    @Override
    public void onInputCommitted(InputResult result) {
        for (OnInputCommittedListener listener : this.listeners) {
            listener.inputCommitted(result);
        }
    }

    @Override
    public void addOnInputCommittedListener(OnInputCommittedListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeOnInputCommittedListener(OnInputCommittedListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void clearOnInputCommittedListeners() {
        this.listeners.clear();
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
