package com.slamcode.testgame;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.slamcode.locationbasedgamelib.location.LocationTracker;
import com.slamcode.locationbasedgamelib.model.LocationData;
import com.slamcode.testgame.app.ServiceNames;
import com.slamcode.testgame.app.ServiceRegistryAppCompatActivity;
import com.slamcode.testgame.databinding.ActivityTrackerBinding;
import com.slamcode.testgame.databinding.TrackerDataViewBinding;
import com.slamcode.testgame.viewmodels.TrackerDataViewModel;

public class TrackerActivity extends ServiceRegistryAppCompatActivity{

    private LocationTracker locationTracker;
    private TrackerDataViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        ActivityTrackerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_tracker);
        TrackerDataViewBinding trackerDataViewBinding = DataBindingUtil.findBinding(findViewById(R.id.tracker_data_view));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.locationTracker = (LocationTracker) this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.LOCATION_TRACKER);
        this.viewModel = new TrackerDataViewModel(this.locationTracker, new LocationData(51.070847, 16.996699));
        binding.setVm(this.viewModel);
        trackerDataViewBinding.setVm(this.viewModel);
    }

}
