package com.slamcode.locationbasedgamelib.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents content of game task divided onto multimedia, info and input parts
 */

public class GameTaskContent {

    private List<GameTaskContentElement> contentElements;

    public List<GameTaskContentElement> getContentElements() {
        if(contentElements == null)
            contentElements = new ArrayList<>();

        return contentElements;
    }

    public void setContentElements(List<GameTaskContentElement> contentElements) {
        this.contentElements = contentElements;
    }

    public Iterable<InputContentElement> getInputContentElements()
    {
        List<InputContentElement> result = new ArrayList<>();

        if(this.contentElements != null) {
            for (GameTaskContentElement element : this.contentElements) {
                if (element instanceof InputContentElement) {
                    result.add((InputContentElement) element);
                }
            }
        }

        return result;
    }
}
