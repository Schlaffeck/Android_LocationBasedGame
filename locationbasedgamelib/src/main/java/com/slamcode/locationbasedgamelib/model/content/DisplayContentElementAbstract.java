package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.model.DisplayContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract for display content element
 */

public abstract class DisplayContentElementAbstract implements DisplayContent {

    private transient List<OnDisplayContentChangedListener> listeners = new ArrayList<>();

    @Override
    public void onDisplayContentChanged() {
        this.validateListeners();
        for (OnDisplayContentChangedListener listener : this.listeners) {
            listener.onDisplayContentChanged();
        }
    }

    @Override
    public void addOnDisplayChangedListener(OnDisplayContentChangedListener listener) {
        this.validateListeners();
        listeners.add(listener);
    }

    @Override
    public void removeOnDisplayChangedListener(OnDisplayContentChangedListener listener) {
        this.validateListeners();
        listeners.remove(listener);
    }

    @Override
    public void clearOnDisplayChangedListeners() {
        this.validateListeners();
        this.listeners.clear();
    }

    private void validateListeners()
    {
        if(this.listeners == null)
            this.listeners = new ArrayList<>();
    }
}
