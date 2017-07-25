package com.slamcode.locationbasedgamelib.general;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoriak on 25/07/2017.
 */

public abstract class ConfigurableAbstract<Configuration> implements Configurable<Configuration> {

    private List<ConfigurationChangedListener<Configuration>> listeners = new ArrayList<>();
    private Configuration currentConfiguration;

    protected List<ConfigurationChangedListener<Configuration>> getConfigurationChangedListeners() {
        return listeners;
    }

    protected Configuration getCurrentConfiguration()
    {
        return this.currentConfiguration;
    }

    @Override
    public void configure(Configuration configuration)
    {
        if(this.configureCore(configuration)) {
            this.currentConfiguration = configuration;
            this.configurationChanged();
        }
    }

    @Override
    public void addConfigurationChangedListener(ConfigurationChangedListener<Configuration> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeConfigurationChangedListener(ConfigurationChangedListener<Configuration> listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void clearConfigurationChangedListeners() {
        this.listeners.clear();
    }

    @Override
    public void configurationChanged() {
        for(ConfigurationChangedListener<Configuration> listener : this.listeners)
            listener.onConfigurationChanged(this, this.currentConfiguration);
    }

    protected abstract boolean configureCore(Configuration configuration);
}
