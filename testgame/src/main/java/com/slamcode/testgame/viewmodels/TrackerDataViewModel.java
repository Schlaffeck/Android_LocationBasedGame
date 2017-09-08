package com.slamcode.testgame.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.android.databinding.library.baseAdapters.BR;
import com.slamcode.locationbasedgamelib.location.LocationDataHelper;
import com.slamcode.locationbasedgamelib.location.LocationTracker;
import com.slamcode.locationbasedgamelib.model.LocationData;
import com.slamcode.testgame.DialogService;
import com.slamcode.locationbasedgamelib.model.PlaceData;
import com.slamcode.testgame.view.dialog.AddNewPlaceDialogFragment;
import com.slamcode.testgame.view.dialog.base.ModelBasedDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by smoriak on 20/07/2017.
 */

public class TrackerDataViewModel extends BaseObservable {

    private LocationTracker locationTracker;
    private final LocationData targetLocation;
    private final DialogService dialogService;
    private final List<PlaceData> placeDataList;
    private String lastLog;

    private ObservableList<PlaceDataViewModel> placeList = new ObservableArrayList<>();

    public TrackerDataViewModel(LocationTracker locationTracker, LocationData targetLocation, DialogService dialogService, List<PlaceData> placeDataList) {
        this.locationTracker = locationTracker;
        this.targetLocation = targetLocation;
        this.dialogService = dialogService;
        this.placeDataList = placeDataList;
        if(placeDataList != null)
        {
            for(PlaceData placeData : placeDataList)
                placeList.add(new PlaceDataViewModel(placeData));
        }
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

    @Bindable
    public ObservableList<PlaceDataViewModel> getPlaceList() {
        return placeList;
    }

    public void showAddNewPlaceDialog()
    {
        final AddNewPlaceDialogFragment dialog = new AddNewPlaceDialogFragment();
        final PlaceData model = new PlaceData();
        model.setLocationData(this.getLocation());
        final PlaceDataViewModel newPlaceViewModel = new PlaceDataViewModel(model);
        dialog.setModel(newPlaceViewModel);
        dialog.setDialogStateChangedListener(new ModelBasedDialog.DialogStateChangedListener() {
            @Override
            public void onDialogClosed(boolean confirmed) {
                if(confirmed)
                {
                    placeList.add(newPlaceViewModel);
                    placeDataList.add(model);
                }
            }
        });

        this.dialogService.showDialog(dialog);
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
