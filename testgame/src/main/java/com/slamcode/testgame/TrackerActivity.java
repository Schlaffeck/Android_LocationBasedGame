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
import com.slamcode.locationbasedgamelib.permission.PermissionRequestor;
import com.slamcode.testgame.databinding.ActivityTrackerBinding;
import com.slamcode.testgame.databinding.TrackerDataViewBinding;
import com.slamcode.testgame.viewmodels.TrackerDataViewModel;

import java.util.ArrayList;
import java.util.List;

public class TrackerActivity extends AppCompatActivity implements PermissionRequestor{

    private LocationTracker locationTracker;
    private List<RequestListener> requestListeners = new ArrayList<>();
    private TrackerDataViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        ActivityTrackerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_tracker);
        TrackerDataViewBinding trackerDataViewBinding = DataBindingUtil.findBinding(findViewById(R.id.tracker_data_view));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.locationTracker = new LocationTracker(this, new LocationTrackerConfiguration(), this);
        this.viewModel = new TrackerDataViewModel(this.locationTracker);
        binding.setVm(this.viewModel);
        trackerDataViewBinding.setVm(this.viewModel);
    }

    @Override
    public void requestPermissions(PermissionRequest request) {
        ActivityCompat.requestPermissions(this, request.getPermissions(), request.getPermissionRequestCode());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (RequestListener listener : this.requestListeners) {
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
