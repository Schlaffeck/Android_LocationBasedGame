package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.model.DisplayContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple display text content for task
 */

public final class DisplayTextElement implements DisplayContent {

    public final static String CONTENT_TYPE = "DISPLAY_TEXT";

    private List<OnDisplayContentChangedListener> listeners = new ArrayList<>();

    private String text;

    @Override
    public String getContentType() {
        return CONTENT_TYPE;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if(this.text == text)
            return;
        this.text = text;
        this.onDisplayContentChanged();
    }

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
