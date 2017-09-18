package com.slamcode.testgame.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.slamcode.locationbasedgamelib.permission.PermissionRequestor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoriak on 04/08/2017.
 */

public abstract class ServiceRegistryAppCompatActivity extends AppCompatActivity implements PermissionRequestor {

    private ServiceRegistryApplication serviceRegistryApplication;

    private List<RequestListener> requestListeners = new ArrayList<>();

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

    public ServiceRegistryApplication getServiceRegistryApplication()
    {
        return this.serviceRegistryApplication;
    }

    public <ServiceType> ServiceType provideServiceFromRegistry(String serviceName)
    {
        Object service = this.serviceRegistryApplication.getRegistry().provideService(serviceName);

        if(service != null)
            return (ServiceType) service;

        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context appContext = this.getApplicationContext();
        if(appContext instanceof ServiceRegistryApplication) {
            this.serviceRegistryApplication = (ServiceRegistryApplication) appContext;
            this.serviceRegistryApplication.setCurrentActivity(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Context appContext = this.getApplicationContext();
        if(appContext instanceof ServiceRegistryApplication) {
            this.serviceRegistryApplication = (ServiceRegistryApplication) appContext;
            this.serviceRegistryApplication.setCurrentActivity(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(this.serviceRegistryApplication != null)
            this.serviceRegistryApplication.setCurrentActivity(this);
    }

    protected void onResume() {
        super.onResume();
        if(this.serviceRegistryApplication != null)
            this.serviceRegistryApplication.setCurrentActivity(this);
    }

    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    private void clearReferences(){
        Activity currActivity = this.serviceRegistryApplication.getCurrentActivity();
        if (this.equals(currActivity))
            this.serviceRegistryApplication.setCurrentActivity(null);
    }
}
