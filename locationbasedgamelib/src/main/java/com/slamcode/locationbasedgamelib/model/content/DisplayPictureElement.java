package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.model.DisplayContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Dimple display content with single picture got either from resource or path to file
 */

public final class DisplayPictureElement implements DisplayContent {

    public final static String CONTENT_TYPE = "DISPLAY_PICTURE";

    public final static int CONTENT_TYPE_ID = CONTENT_TYPE.hashCode();

    private List<OnDisplayContentChangedListener> listeners = new ArrayList<>();

    private int pictureResourceId;

    private String picturePath;

    @Override
    public String getContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public int getContentTypeId() {
        return CONTENT_TYPE_ID;
    }

    public int getPictureResourceId() {
        return pictureResourceId;
    }

    public void setPictureResourceId(int pictureResourceId) {
        if(this.pictureResourceId == pictureResourceId)
            return;
        this.pictureResourceId = pictureResourceId;
        this.onDisplayContentChanged();
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        if(this.picturePath == picturePath)
            return;
        this.picturePath = picturePath;
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
