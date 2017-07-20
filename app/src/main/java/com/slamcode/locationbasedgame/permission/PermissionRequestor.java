package com.slamcode.locationbasedgame.permission;

/**
 * Interface representing simple set of methods for requesting permissions within current app context
 */

public interface PermissionRequestor {

    /**
     * Sends request for given permission and synchronously waits for result whether permission was granted or not
     * @param permissions Set of permissions to request for
     * @param permissionRequestCode Request code
     * @return Flag indicating whether [ermission was granted or not
     */
    boolean requestPermissions(String[] permissions, int permissionRequestCode);

    void addRequestListener(RequestListener listener);

    void removeRequestListener(RequestListener listener);

    void clearRequestListeners();

    interface RequestListener{

        void requestFinished(int requestCode, boolean permissionGranted);
    }
}
