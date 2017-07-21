package com.slamcode.testgame.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.android.databinding.library.baseAdapters.BR;
import com.slamcode.locationbasedgamelib.location.LocationTracker;
import com.slamcode.locationbasedgamelib.model.LocationData;

/**
 * Created by smoriak on 20/07/2017.
 */

public class TrackerDataViewModel extends BaseObservable {

    private LocationTracker locationTracker;

    public TrackerDataViewModel(LocationTracker locationTracker) {
        this.locationTracker = locationTracker;
        this.locationTracker.addLocationListener(new TrackerLocationListener());
    }

    @Bindable
    public LocationData getLocation() {
        LocationData result = new LocationData();
        Location location = this.locationTracker.getLocation();
        if(location == null)
            location = this.locationTracker.getLastKnownLocation();

        if (location != null)
            result = new LocationData(location.getLatitude(), location.getLongitude());

        return result;
    }

    public void refreshLocation()
    {
        notifyPropertyChanged(BR.location);
    }

    private class TrackerLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            refreshLocation();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            refreshLocation();
        }

        @Override
        public void onProviderEnabled(String provider) {
            refreshLocation();
        }

        @Override
        public void onProviderDisabled(String provider) {
            refreshLocation();
        }
    }
}
