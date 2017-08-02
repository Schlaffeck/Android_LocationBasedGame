package com.slamcode.locationbasedgamelib.location;

import com.slamcode.locationbasedgamelib.model.LocationData;

/**
 * Simple interface abstracting retrieving current location data
 */

public interface LocationDataProvider {

    LocationData getLocationData();
}
