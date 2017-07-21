package com.slamcode.locationbasedgamelib.permission;

import android.Manifest;

/**
 * List of permission request codes and helper methods
 */

public class PermissionRequestCodes {

    public final static int LOCATION_ACCESS_CODE = 1;

    public final static PermissionRequestor.PermissionRequest LOCATION_PERMISSION_REQUEST
            = new PermissionRequestor.PermissionRequest(new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, PermissionRequestCodes.LOCATION_ACCESS_CODE);
}
