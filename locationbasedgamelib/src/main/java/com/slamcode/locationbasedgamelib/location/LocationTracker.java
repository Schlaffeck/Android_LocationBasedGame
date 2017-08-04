package com.slamcode.locationbasedgamelib.location;

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
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.slamcode.locationbasedgamelib.general.Configurable;
import com.slamcode.locationbasedgamelib.model.LocationData;
import com.slamcode.locationbasedgamelib.permission.PermissionRequestCodes;
import com.slamcode.locationbasedgamelib.permission.PermissionRequestor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Simple service for tracking and updating current location
 */

public final class LocationTracker extends Service implements LocationListener, LocationDataProvider, Configurable<LocationTrackerConfiguration>, PermissionRequestor.RequestListener {

    private static final String LOG_TAG = "LBG_LocTr";
    private Location lastKnownLocation;
    private Context mContext;
    private final PermissionRequestor permissionRequestor;
    private LocationManager manager;
    private LocationTrackerConfiguration configuration;

    private List<LocationListener> locationListeners = new ArrayList<>();

    private Collection<ConfigurationChangedListener<LocationTrackerConfiguration>> configurationChangedListeners = new ArrayList<>();

    public LocationTracker(Context context, LocationTrackerConfiguration configuration, PermissionRequestor permissionRequestor) {
        this.mContext = context;
        this.permissionRequestor = permissionRequestor;
        this.configure(configuration);
        this.setupServices();
        this.lastKnownLocation = this.getLocation();
        this.permissionRequestor.addRequestListener(this);
    }

    public Location getLocation() {

        Location result = this.getBestLocation();

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

        Log.i(LOG_TAG, String.format("Location changed to: %f x %f", location.getLatitude(), location.getLongitude()));
        this.lastKnownLocation = location;
        for(LocationListener locationListener : this.locationListeners)
            locationListener.onLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        for(LocationListener locationListener : this.locationListeners)
            locationListener.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(String provider) {

        Log.i(LOG_TAG, String.format("'%s' provider enabled", provider));
        for(LocationListener locationListener : this.locationListeners)
            locationListener.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(String provider) {

        Log.i(LOG_TAG, String.format("'%s' provider disabled", provider));
        for(LocationListener locationListener : this.locationListeners)
            locationListener.onProviderDisabled(provider);
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

    @Override
    public void requestFinished(int requestCode, boolean permissionGranted) {
        if(requestCode == PermissionRequestCodes.LOCATION_ACCESS_CODE && permissionGranted) {

            Log.i(LOG_TAG, "Permission granted");
            this.getLocation();
        }
    }

    public void addLocationListener(LocationListener locationListener)
    {
        this.locationListeners.add(locationListener);
    }

    public void removeLocationListener(LocationListener locationListener)
    {
        this.locationListeners.remove(locationListener);
    }

    public void clearLocationListeners()
    {
        this.locationListeners.clear();
    }

    private void setupServices() {
        this.manager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        for(String provider : this.manager.getAllProviders())
            this.setupLocationProviderUpdates(provider);
    }

    private void setupLocationProviderUpdates(String providerName) {

        Log.i(LOG_TAG, String.format("'%s' provider - Setting up updates", providerName));

        boolean fineLocationGranted = ContextCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean coarseLocationGranted = ContextCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        if (!fineLocationGranted && !coarseLocationGranted) {
            this.requestLocationPermissions();
        }
        this.manager.requestLocationUpdates(
                providerName,
                this.configuration.getMinimalTimeBetweenUpdatesMillis(),
                this.configuration.getMinimalDistanceToUpdateLocationMeters(),
                this);

        Log.i(LOG_TAG, String.format("'%s' provider - Set up", providerName));
    }

    private void requestLocationPermissions()
    {
        Log.w(LOG_TAG, "Permissions needed");
        this.permissionRequestor.requestPermissions(PermissionRequestCodes.LOCATION_PERMISSION_REQUEST);
    }

    private Location getBestLocation()
    {
        Location location = null;

        if (ContextCompat.checkSelfPermission(
                this.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                this.mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            this.requestLocationPermissions();
            return null;
        }

        for(String provider : this.manager.getProviders(true))
        {
            Location providerLocation = this.manager.getLastKnownLocation(provider);
            if(providerLocation != null) {
                if (location == null
                        || providerLocation.hasAccuracy() && providerLocation.getAccuracy() > location.getAccuracy())
                {
                    location = providerLocation;
                }
            }
        }

        return location;
    }

    @Override
    public LocationData getLocationData() {
        Location location = this.getLocation();
        if(location == null)
            location = this.getLastKnownLocation();

        if (location != null)
            return new LocationData(location.getLatitude(), location.getLongitude());

        return null;
    }
}
