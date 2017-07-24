package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.model.InputContent;
import com.slamcode.locationbasedgamelib.model.InputResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents input used with text field to input by user and button or other action item to commit the
 * text and compare it with the expected one
 */

public final class TextComparisonInputElement implements InputContent {

    public static final String CONTENT_TYPE = "TEXT_COMPARISON_INPUT";

    public final static int CONTENT_TYPE_ID = CONTENT_TYPE.hashCode();

    private List<OnInputCommittedListener> listeners = new ArrayList<>();
    private String inputText;

    @Override
    public String getContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public int getContentTypeId() {
        return CONTENT_TYPE_ID;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        if(this.inputText == inputText)
            return;
        this.inputText = inputText;
    }

    @Override
    public InputResult commitInput() {
        InputResult result = new InputResult();
        this.onInputCommitted(result);
        return result;
    }

    @Override
    public void onInputCommitted(InputResult result) {
        for (OnInputCommittedListener listener : this.listeners) {
            listener.inputCommitted(result);
        }
    }

    @Override
    public void addOnInputCommittedListener(OnInputCommittedListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeOnInputCommittedListener(OnInputCommittedListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void clearOnInputCommittedListeners() {
        this.listeners.clear();
    }
}
