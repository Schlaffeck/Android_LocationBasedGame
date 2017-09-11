package com.slamcode.testgame.app;

import android.app.Activity;
import android.app.Application;

import com.slamcode.locationbasedgamelayout.view.GameTaskContentSimpleLayoutProvider;
import com.slamcode.locationbasedgamelib.location.LocationTracker;
import com.slamcode.locationbasedgamelib.location.LocationTrackerConfiguration;
import com.slamcode.locationbasedgamelib.permission.PermissionRequestor;
import com.slamcode.testgame.data.PersistenceContextContainer;
import com.slamcode.testgame.messaging.sms.SmsMessagingService;
import com.slamcode.testgame.services.ServicesRegistry;

/**
 * Created by smoriak on 04/08/2017.
 */

public final class ServiceRegistryApplication extends Application implements ServicesRegistry.ServiceFactory, PermissionRequestor.Provider {

    private ServicesRegistry registry = new ServicesRegistry();

    private Activity currentActivity;

    public ServicesRegistry getRegistry() {
        return registry;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.setupServiceRegistry();
    }

    private void setupServiceRegistry()
    {
        registry.registerService(ServiceNames.PERMISSION_REQUESTOR_PROVIDER, this);
        registry.registerService(ServiceNames.PERSISTENCE_CONTEXT, PersistenceContextContainer.initializePersistenceContext(this.getApplicationContext()));
        registry.registerServiceFactory(ServiceNames.LOCATION_TRACKER, this, true);
        registry.registerService(ServiceNames.CONTENT_LAYOUT_PROVIDER, new GameTaskContentSimpleLayoutProvider());
        registry.registerService(ServiceNames.SMS_MESSAGING_SERVICE, new SmsMessagingService(this.getApplicationContext()));
    }

    public Activity getCurrentActivity()
    {
        return this.currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    public Object createService(String serviceName) {

        if(serviceName == ServiceNames.LOCATION_TRACKER)
            return new LocationTracker(this.getApplicationContext(), new LocationTrackerConfiguration(1f, 2_000),
                    (PermissionRequestor.Provider) registry.provideService(ServiceNames.PERMISSION_REQUESTOR_PROVIDER));

        return null;
    }

    @Override
    public PermissionRequestor getPermissionRequestor() {

        if(this.currentActivity instanceof PermissionRequestor)
            return (PermissionRequestor) this.currentActivity;

        return null;
    }
}
