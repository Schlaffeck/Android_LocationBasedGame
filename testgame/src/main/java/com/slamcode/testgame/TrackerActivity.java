package com.slamcode.testgame;

import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.slamcode.locationbasedgamelib.location.LocationTracker;
import com.slamcode.locationbasedgamelib.location.LocationTrackerConfiguration;
import com.slamcode.locationbasedgamelib.permission.PermissionRequestCodes;
import com.slamcode.locationbasedgamelib.permission.PermissionRequestor;
import com.slamcode.testgame.databinding.ActivityTrackerBinding;
import com.slamcode.testgame.viewmodels.TrackerDataViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class TrackerActivity extends AppCompatActivity implements PermissionRequestor{

    private LocationTracker locationTracker;
    private List<RequestListener> requestListeners = new ArrayList<>();
    private TrackerDataViewModel viewModel;

    private Semaphore locationPermissionRequestSemaphore = new Semaphore(1);
    private boolean locationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        ActivityTrackerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_tracker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.locationTracker = new LocationTracker(this, new LocationTrackerConfiguration(), this);
        this.viewModel = new TrackerDataViewModel(this.locationTracker);
        binding.setVm(this.viewModel);
    }

    @Override
    public boolean requestPermissionsAndWait(String[] permissions, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this, permissions, permissionRequestCode);
        try {
            locationPermissionRequestSemaphore.acquire();
            return this.locationPermissionGranted;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PermissionRequestCodes.LOCATION_ACCESS_CODE) {
            this.locationPermissionRequestSemaphore.release();
            this.locationPermissionGranted =  grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        for (RequestListener listener :
                this.requestListeners) {
            listener.requestFinished(requestCode, grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }

    @Override
    public void addRequestListener(RequestListener listener) {
        this.requestListeners.add(listener);
    }

    @Override
    public void removeRequestListener(RequestListener listener) {
        this.requestListeners.remove(listener);
    }

    @Override
    public void clearRequestListeners() {
        this.requestListeners.clear();
    }

}
