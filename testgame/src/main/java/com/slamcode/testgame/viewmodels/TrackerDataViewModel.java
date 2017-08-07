package com.slamcode.testgame.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.android.databinding.library.baseAdapters.BR;
import com.slamcode.locationbasedgamelib.location.LocationDataHelper;
import com.slamcode.locationbasedgamelib.location.LocationTracker;
import com.slamcode.locationbasedgamelib.model.LocationData;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by smoriak on 20/07/2017.
 */

public class TrackerDataViewModel extends BaseObservable {

    private LocationTracker locationTracker;
    private final LocationData targetLocation;
    private String lastLog;

    public TrackerDataViewModel(LocationTracker locationTracker, LocationData targetLocation) {
        this.locationTracker = locationTracker;
        this.targetLocation = targetLocation;
        this.locationTracker.addLocationListener(new TrackerLocationListener());
    }

    @Bindable
    public LocationData getLocation() {
        return this.locationTracker.getLocationData();
    }

    public void refreshLocation()
    {
        notifyPropertyChanged(BR.location);
        notifyPropertyChanged(BR.distance);
    }

    @Bindable
    public float getDistance()
    {
        return LocationDataHelper.countDistanceFrom(this.targetLocation, this.getLocation());
    }

    @Bindable
    public String getLastLog() {
        return lastLog;
    }

    private void setLastLog(String lastLog) {
        this.lastLog = lastLog;
        notifyPropertyChanged(BR.lastLog);
    }

    private class TrackerLocationListener implements LocationListener{

        private String getTimeStampString()
        {
            return new SimpleDateFormat("HH:mm:ss").format(new Date());
        }

        @Override
        public void onLocationChanged(Location location) {
            setLastLog(String.format("%s: Location changed %f, %f", getTimeStampString(), location != null ? location.getLatitude() : 0, location != null ? location.getLongitude() : 0));
            refreshLocation();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            setLastLog(String.format("%s: Status changed '%s' = %d", getTimeStampString(), provider, status));
            refreshLocation();
        }

        @Override
        public void onProviderEnabled(String provider) {
            setLastLog(String.format("%s: Provider enabled '%s'", getTimeStampString(), provider));
            refreshLocation();
        }

        @Override
        public void onProviderDisabled(String provider) {
            setLastLog(String.format("%s: Provider disabled '%s'", getTimeStampString(), provider));
            refreshLocation();
        }
    }
}
