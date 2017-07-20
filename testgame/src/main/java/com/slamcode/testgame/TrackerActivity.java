package com.slamcode.testgame;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.slamcode.locationbasedgamelib.location.LocationTracker;
import com.slamcode.locationbasedgamelib.location.LocationTrackerConfiguration;
import com.slamcode.locationbasedgamelib.permission.PermissionRequestor;
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
        TrackerDataViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_tracker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.locationTracker = new LocationTracker(this, new LocationTrackerConfiguration(), this);
        this.viewModel = new TrackerDataViewModel(this.locationTracker);
        binding.setVm(this.viewModel);
    }

    @Override
    public boolean requestPermissionsAndWait(String[] permissions, int permissionRequestCode) {
        return false;
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
