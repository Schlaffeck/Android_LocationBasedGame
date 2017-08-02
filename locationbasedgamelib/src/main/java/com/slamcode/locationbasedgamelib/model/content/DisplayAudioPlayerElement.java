package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.model.DisplayContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple element for displaying audio player on screen
 */

public class DisplayAudioPlayerElement extends DisplayContentElementAbstract {

    public final static String CONTENT_TYPE = "DISPLAY_TEXT";

    public final static int CONTENT_TYPE_ID = CONTENT_TYPE.hashCode();

    private String contentType = CONTENT_TYPE;

    private String audioTitle;

    private int audioFileResourceId;

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public int getContentTypeId() {
        return CONTENT_TYPE_ID;
    }

    public String getAudioTitle() {
        return audioTitle;
    }

    public void setAudioTitle(String audioTitle) {
        this.audioTitle = audioTitle;
    }

    public int getAudioFileResourceId() {
        return audioFileResourceId;
    }

    public void setAudioFileResourceId(int audioFileResourceId) {
        this.audioFileResourceId = audioFileResourceId;
    }
}
