package com.slamcode.locationbasedgamelib.permission;

import java.util.concurrent.Future;

/**
 * Interface representing simple set of methods for requesting permissions within current app context
 */

public interface PermissionRequestor {

    void requestPermissions(PermissionRequest request);

    void addRequestListener(RequestListener listener);

    void removeRequestListener(RequestListener listener);

    void clearRequestListeners();

    class PermissionRequest
    {
        private String[] permissions;
        private int permissionRequestCode;

        public PermissionRequest(String[] permissions, int permissionRequestCode) {
            this.permissions = permissions;
            this.permissionRequestCode = permissionRequestCode;
        }

        public String[] getPermissions() {
            return permissions;
        }

        public int getPermissionRequestCode() {
            return permissionRequestCode;
        }
    }
    interface RequestListener{

        void requestFinished(int requestCode, boolean permissionGranted);
    }
}
