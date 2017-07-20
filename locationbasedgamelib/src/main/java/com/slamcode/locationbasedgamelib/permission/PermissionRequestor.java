package com.slamcode.locationbasedgamelib.permission;

/**
 * Interface representing simple set of methods for requesting permissions within current app context
 */

public interface PermissionRequestor {

    /**
     * Sends request for given permission and synchronously waits for result whether permission was granted or not
     * @param permissions Set of permissions to request for
     * @param permissionRequestCode Request code
     * @return Flag indicating whether permission was granted or not
     */
    boolean requestPermissionsAndWait(String[] permissions, int permissionRequestCode);

    /**
     * Sends request for permission and returns. Result of the request can be handled by listener.
     * @param permissions Permissions to request for
     * @param permissionRequestCode Request code
     */
    void requestPermissions(String[] permissions, int permissionRequestCode);

    void addRequestListener(RequestListener listener);

    void removeRequestListener(RequestListener listener);

    void clearRequestListeners();

    interface RequestListener{

        void requestFinished(int requestCode, boolean permissionGranted);
    }
}
