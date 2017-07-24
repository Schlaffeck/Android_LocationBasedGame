package com.slamcode.locationbasedgamelib.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents content of game task divided onto multimedia, info and input parts
 */

public class GameTaskContent {

    private List<GameTaskContentElement> contentElements = new ArrayList<>();

    public Iterable<GameTaskContentElement> getContentElements() {
        return contentElements;
    }

    public void addContentElement(GameTaskContentElement element)
    {
        this.contentElements.add(element);
    }

    public void clearContentElements()
    {
        this.contentElements.clear();
    }
}
