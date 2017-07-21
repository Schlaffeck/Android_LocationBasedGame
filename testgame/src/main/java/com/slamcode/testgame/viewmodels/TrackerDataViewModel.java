package com.slamcode.testgame.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.location.Location;

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

}
