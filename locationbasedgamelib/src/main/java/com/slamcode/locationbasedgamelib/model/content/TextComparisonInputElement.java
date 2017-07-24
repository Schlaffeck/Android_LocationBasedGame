package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.model.InputContent;
import com.slamcode.locationbasedgamelib.model.InputResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents input used with text field to input by user and button or other action item to commit the
 * text and compare it with the expected one
 */

public final class TextComparisonInputElement implements InputContent {

    public final static TextComparisonConfiguration IgnoreAllConfiguration = new TextComparisonConfiguration(true, true);
    public static final String CONTENT_TYPE = "TEXT_COMPARISON_INPUT";

    public final static int CONTENT_TYPE_ID = CONTENT_TYPE.hashCode();
    private final TextComparisonConfiguration comparisonConfiguration;

    private List<OnInputCommittedListener> listeners = new ArrayList<>();
    private String inputText;
    private List<String> acceptableInputValues = new ArrayList<>();

    public TextComparisonInputElement(String expectedInputValue)
    {
        this(expectedInputValue, IgnoreAllConfiguration);
    }

    public TextComparisonInputElement(Collection<String> acceptableInputValues)
    {
        this(acceptableInputValues, IgnoreAllConfiguration);
    }

    public TextComparisonInputElement(String expectedInputValue, TextComparisonConfiguration comparisonConfiguration)
    {
        this.comparisonConfiguration = comparisonConfiguration;
        this.acceptableInputValues.add(expectedInputValue);
    }

    public TextComparisonInputElement(Collection<String> acceptableInputValues, TextComparisonConfiguration comparisonConfiguration)
    {
        this.comparisonConfiguration = comparisonConfiguration;
        this.acceptableInputValues.addAll(acceptableInputValues);
    }

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

    public Iterable<String> getAcceptableInputValues()
    {
        return this.acceptableInputValues;
    }

    @Override
    public InputResult commitInput() {
        InputResult result = new InputResult();

        String input = this.inputText;
        if(this.comparisonConfiguration.ignoreCase)
            input = this.inputText.toLowerCase();

        if(this.comparisonConfiguration.ignoreNonAlphaNumericCharacters)
            input = input.replace("[\\W]", "");

        for (int i = 0; i < this.acceptableInputValues.size() && !result.isInputCorrect(); i++) {
            String acceptableInput = this.acceptableInputValues.get(i);
            if(this.comparisonConfiguration.ignoreCase) {
                acceptableInput = acceptableInput.toLowerCase();
                input = this.inputText.toLowerCase();
            }

            if(this.comparisonConfiguration.ignoreNonAlphaNumericCharacters)
            {
                acceptableInput = acceptableInput.replace("[\\W]", "");
            }

            result.setInputCorrect(input.equals(acceptableInput));
        }
        
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


    public static class TextComparisonConfiguration
    {
        private boolean ignoreCase;

        private boolean ignoreNonAlphaNumericCharacters;

        public TextComparisonConfiguration(boolean ignoreCase, boolean ignoreNonAlphaNumericCharacters) {
            this.ignoreCase = ignoreCase;
            this.ignoreNonAlphaNumericCharacters = ignoreNonAlphaNumericCharacters;
        }

        public boolean isIgnoreCase() {
            return ignoreCase;
        }

        public boolean isIgnoreNonAlphaNumericCharacters() {
            return ignoreNonAlphaNumericCharacters;
        }
    }
}
