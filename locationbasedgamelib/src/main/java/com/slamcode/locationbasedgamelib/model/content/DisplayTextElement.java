package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.model.DisplayContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple display text content for task
 */

public final class DisplayTextElement extends DisplayContentElementAbstract {

    public final static String CONTENT_TYPE = "DISPLAY_TEXT";

    public final static int CONTENT_TYPE_ID = CONTENT_TYPE.hashCode();

    private String text;

    private String contentType = CONTENT_TYPE;

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public int getContentTypeId() {
        return CONTENT_TYPE_ID;
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
}
