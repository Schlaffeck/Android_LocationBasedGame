package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.general.Configurable;
import com.slamcode.locationbasedgamelib.general.ConfigurableAbstract;
import com.slamcode.locationbasedgamelib.model.InputCommitParameters;
import com.slamcode.locationbasedgamelib.model.InputContent;
import com.slamcode.locationbasedgamelib.model.InputResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Represents input used with text field to input by user and button or other action item to commit the
 * text and compare it with the expected one
 */

public final class TextComparisonInputElement extends InputContentElementAbstract<String> {

    public final static TextInputComparator IgnoreAllComparator = new TextInputComparator(new TextComparisonConfiguration(true, true));
    public static final String CONTENT_TYPE = "TEXT_COMPARISON_INPUT";

    public final static int CONTENT_TYPE_ID = CONTENT_TYPE.hashCode();
    private final TextInputComparator comparator;

    private String commitCommandName;
    private transient List<OnInputCommittedListener> listeners = new ArrayList<>();
    private String inputText;
    private List<String> acceptableInputValues = new ArrayList<>();

    private String contentType = CONTENT_TYPE;

    public TextComparisonInputElement(String expectedInputValue, String commitCommandName)
    {
        this(expectedInputValue, commitCommandName, IgnoreAllComparator);
    }

    public TextComparisonInputElement(Collection<String> acceptableInputValues, String commitCommandName)
    {
        this(acceptableInputValues, commitCommandName, IgnoreAllComparator);
    }

    public TextComparisonInputElement(String expectedInputValue, String commitCommandName, TextInputComparator comparator)
    {
        this.commitCommandName = commitCommandName;
        this.comparator = comparator;
        this.acceptableInputValues.add(expectedInputValue);
    }

    public TextComparisonInputElement(Collection<String> acceptableInputValues, String commitCommandName, TextInputComparator comparator)
    {
        this.commitCommandName = commitCommandName;
        this.comparator = comparator;
        this.acceptableInputValues.addAll(acceptableInputValues);
    }

    @Override
    public String getContentType() {
        return this.contentType;
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
    public InputResult commitInput(String inputParameter) {
        InputCommitParameters<String> params = new InputCommitParameters<>(inputParameter);
        this.onInputCommitting(params);

        InputResult result = new InputResult();

        for (int i = 0; i < this.acceptableInputValues.size() && !result.isInputCorrect(); i++)
            result.setInputCorrect(this.comparator.compare(params.getValue(), this.acceptableInputValues.get(i)) == 0);
        
        this.onInputCommitted(result);
        return result;
    }

    @Override
    public void clearOnInputCommittedListeners() {
        this.listeners.clear();
    }

    public String getCommitCommandName() {
        return commitCommandName;
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

    public static class TextInputComparator extends ConfigurableAbstract<TextComparisonConfiguration> implements Comparator<String>
    {
        public TextInputComparator(TextComparisonConfiguration configuration)
        {
            this.configure(configuration);
        }

        @Override
        protected boolean configureCore(TextComparisonConfiguration textComparisonConfiguration) {
            return true;
        }

        @Override
        public int compare(String one, String another) {

            if(one == null)
                one = "";

            if(another == null)
                another = "";

            if(this.getCurrentConfiguration().ignoreCase) {
                one = one.toLowerCase();
                another = another.toLowerCase();
            }

            if(this.getCurrentConfiguration().ignoreNonAlphaNumericCharacters) {
                one = one.replaceAll("[\\W]|_", "");
                another = another.replaceAll("[\\W]|_", "");
            }

            return one.compareTo(another);
        }
    }
}
