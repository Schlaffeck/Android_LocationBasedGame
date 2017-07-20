package com.slamcode.locationbasedgame.service.location;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.slamcode.locationbasedgame.general.Configurable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Simple service for tracking and updating current location
 */

public final class LocationTracker extends Service implements LocationListener, Configurable<LocationTrackerConfiguration> {

    private Location lastKnownLocation;
    private Context mContext;
    private boolean isLocationProviderEnabled;
    private boolean isNetworkEnabled;
    private LocationManager manager;
    private LocationTrackerConfiguration configuration;

    private Collection<ConfigurationChangedListener<LocationTrackerConfiguration>> configurationChangedListeners = new ArrayList<>();

    LocationTracker(Context context, LocationTrackerConfiguration configuration) {
        this.mContext = context;
        this.configure(configuration);
        this.setupServices();
        this.lastKnownLocation = this.getLocation();
    }

    public Location getLocation() {
        Location result = null;
        if(!this.isNetworkEnabled && !this.isLocationProviderEnabled)
            return result;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }

        if (this.isNetworkEnabled) {
            result = this.manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        else if(isLocationProviderEnabled){
            result = this.manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if(result != null)
            this.lastKnownLocation = result;
        return result;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.lastKnownLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        switch (provider) {
            case LocationManager.GPS_PROVIDER:
                this.isLocationProviderEnabled = true;
                break;
            case LocationManager.NETWORK_PROVIDER:
                this.isNetworkEnabled = true;
                break;
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        switch (provider) {
            case LocationManager.GPS_PROVIDER:
                this.isLocationProviderEnabled = false;
                break;
            case LocationManager.NETWORK_PROVIDER:
                this.isNetworkEnabled = false;
                break;
        }
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    @Override
    public void configure(LocationTrackerConfiguration locationTrackerConfiguration) {
        this.configuration = locationTrackerConfiguration;
        this.configurationChanged();
    }

    @Override
    public void addConfigurationChangedListener(ConfigurationChangedListener<LocationTrackerConfiguration> listener) {
        this.configurationChangedListeners.add(listener);
    }

    @Override
    public void removeConfigurationChangedListener(ConfigurationChangedListener<LocationTrackerConfiguration> listener) {
        this.configurationChangedListeners.remove(listener);
    }

    @Override
    public void clearConfigurationChangedListeners() {
        this.configurationChangedListeners.clear();
    }

    @Override
    public void configurationChanged() {
        if (this.configurationChangedListeners == null ||
                this.configurationChangedListeners.isEmpty())
            return;

        for (ConfigurationChangedListener<LocationTrackerConfiguration> listener :
                this.configurationChangedListeners) {
            listener.onConfigurationChanged(this, this.configuration);
        }
    }

    private void setupServices() {
        this.manager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        if(this.isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            this.setupNetworkLocationUpdates();
        }

        if(this.isLocationProviderEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            this.setupLocationProviderUpdates();
        }
    }

    private void setupNetworkLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.manager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                this.configuration.getMinimalTimeBetweenUpdatesMillis(),
                this.configuration.getMinimalTimeBetweenUpdatesMillis(),
                this);
    }

    private void setupLocationProviderUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                this.configuration.getMinimalTimeBetweenUpdatesMillis(),
                this.configuration.getMinimalTimeBetweenUpdatesMillis(),
                this);
    }
}
