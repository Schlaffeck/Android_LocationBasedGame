package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.location.LocationDataHelper;
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
    public InputResult commitInput(LocationData currentLocation) {
        InputResult result = new InputResult();

        result.setInputCorrect(LocationDataHelper.isNearBy(this.targetLocation, currentLocation, this.acceptableDistanceMeters));

        this.onInputCommitted(result);
        return result;
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

}
