package com.slamcode.locationbasedgamelib.model;

/**
 * Interface for any display content of task
 */

public interface DisplayContent extends GameTaskContentElement {

    void onDisplayContentChanged();

    void addOnDisplayChangedListener(OnDisplayContentChangedListener listener);

    void removeOnDisplayChangedListener(OnDisplayContentChangedListener listener);

    void clearOnDisplayChangedListeners();

    interface OnDisplayContentChangedListener{

        void onDisplayContentChanged();
    }
}
