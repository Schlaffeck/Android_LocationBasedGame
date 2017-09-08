package com.slamcode.testgame;

import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.slamcode.locationbasedgamelib.location.LocationTracker;
import com.slamcode.locationbasedgamelib.model.LocationData;
import com.slamcode.locationbasedgamelib.persistence.PersistenceContext;
import com.slamcode.testgame.app.ServiceNames;
import com.slamcode.testgame.app.ServiceRegistryAppCompatActivity;
import com.slamcode.testgame.databinding.ActivityTrackerBinding;
import com.slamcode.testgame.databinding.TrackerDataViewBinding;
import com.slamcode.testgame.viewmodels.TrackerDataViewModel;

public class TrackerActivity extends ServiceRegistryAppCompatActivity implements DialogService{

    private LocationTracker locationTracker;
    private TrackerDataViewModel viewModel;
    private PersistenceContext persistenceContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        ActivityTrackerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_tracker);
        TrackerDataViewBinding trackerDataViewBinding = DataBindingUtil.findBinding(findViewById(R.id.tracker_data_view));
        ViewDataBinding locationListBinding = DataBindingUtil.findBinding(findViewById(R.id.tracker_view_location_list_layout));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.locationTracker = (LocationTracker) this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.LOCATION_TRACKER);

        this.persistenceContext = (PersistenceContext) this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.PERSISTENCE_CONTEXT);

        this.viewModel = new TrackerDataViewModel(this.locationTracker, new LocationData(51.070847, 16.996699), this, (persistenceContext.getData()).getPlaceList());
        binding.setVm(this.viewModel);
        trackerDataViewBinding.setVm(this.viewModel);
        locationListBinding.setVariable(BR.vm, this.viewModel);
    }

    @Override
    public void showDialog(DialogFragment dialogFragment) {
        dialogFragment.show(this.getFragmentManager(), null);
    }

    @Override
    protected void onStop() {
        persistenceContext.persist();
        super.onStop();
    }
}
