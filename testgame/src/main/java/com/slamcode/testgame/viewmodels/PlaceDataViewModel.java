package com.slamcode.testgame.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.slamcode.locationbasedgamelib.model.LocationData;
import com.slamcode.testgame.BR;
import com.slamcode.locationbasedgamelib.model.PlaceData;

public class PlaceDataViewModel extends BaseObservable {

    private final PlaceData placeData;

    public PlaceDataViewModel(PlaceData placeData) {
        this.placeData = placeData;
    }

    public LocationData getLocationData() {
        return placeData.getLocationData();
    }

    @Bindable
    public String getName() {
        return placeData.getName();
    }

    public void setName(String name) {
        placeData.setName(name);
        notifyPropertyChanged(BR.name);
    }
}
