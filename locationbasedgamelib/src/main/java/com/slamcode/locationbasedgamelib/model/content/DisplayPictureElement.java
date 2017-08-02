package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.model.DisplayContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Dimple display content with single picture got either from resource or path to file
 */

public final class DisplayPictureElement extends DisplayContentElementAbstract {

    public final static String CONTENT_TYPE = "DISPLAY_PICTURE";

    public final static int CONTENT_TYPE_ID = CONTENT_TYPE.hashCode();

    private int pictureResourceId;

    private String picturePath;

    private String contentType = CONTENT_TYPE;

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public int getContentTypeId() {
        return CONTENT_TYPE_ID;
    }

    public boolean isPathProvided()
    {
        return this.picturePath != null && this.picturePath != "";
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
}
