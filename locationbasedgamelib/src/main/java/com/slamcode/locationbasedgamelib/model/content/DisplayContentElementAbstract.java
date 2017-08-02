package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.model.DisplayContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract for display content element
 */

public abstract class DisplayContentElementAbstract implements DisplayContent {

    private List<OnDisplayContentChangedListener> listeners = new ArrayList<>();

    @Override
    public void onDisplayContentChanged() {
        for (OnDisplayContentChangedListener listener : this.listeners) {
            listener.onDisplayContentChanged();
        }
    }

    @Override
    public void addOnDisplayChangedListener(OnDisplayContentChangedListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeOnDisplayChangedListener(OnDisplayContentChangedListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void clearOnDisplayChangedListeners() {
        this.listeners.clear();
    }
}
