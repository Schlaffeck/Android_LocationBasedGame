package com.slamcode.testgame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.slamcode.locationbasedgamelib.location.LocationTracker;
import com.slamcode.locationbasedgamelib.location.LocationTrackerConfiguration;
import com.slamcode.locationbasedgamelib.permission.PermissionRequestor;

import java.util.ArrayList;
import java.util.List;

public class TrackerActivity extends AppCompatActivity implements PermissionRequestor{

    private LocationTracker locationTracker;
    private List<RequestListener> requestListeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        this.locationTracker = new LocationTracker(this, new LocationTrackerConfiguration(), this);
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
