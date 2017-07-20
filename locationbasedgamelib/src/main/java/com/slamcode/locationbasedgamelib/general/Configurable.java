package com.slamcode.locationbasedgamelib.general;

/**
 * Created by smoriak on 20/07/2017.
 */

public interface Configurable<Configuration> {

    void configure(Configuration configuration);

    void addConfigurationChangedListener(ConfigurationChangedListener<Configuration> listener);

    void removeConfigurationChangedListener(ConfigurationChangedListener<Configuration> listener);

    void clearConfigurationChangedListeners();

    void configurationChanged();

    interface ConfigurationChangedListener<Configuration>
    {
        void onConfigurationChanged(Configurable<Configuration> configuredObject, Configuration configuration);
    }
}
