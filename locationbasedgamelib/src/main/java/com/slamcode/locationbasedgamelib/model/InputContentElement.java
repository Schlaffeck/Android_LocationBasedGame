package com.slamcode.locationbasedgamelib.model;

/**
 * Represents input content simple interface data for task displayed to user with interaction
 */

public interface InputContentElement<InputValue> extends GameTaskContentElement {

    /**
     * Commit given input status and return result
     * @return Input result
     */
    InputResult commitInput(InputValue inputValue);

    void onInputCommitting(InputCommitParameters<InputValue> parameters);

    void onInputCommitted(InputResult result);

    void addOnInputCommittedListener(OnInputCommittedListener<InputValue> listener);

    void removeOnInputCommittedListener(OnInputCommittedListener<InputValue> listener);

    void clearOnInputCommittedListeners();

    interface OnInputCommittedListener<InputValue>{

        void inputCommitting(InputContentElement<InputValue> element, InputCommitParameters<InputValue> parameters);

        void inputCommitted(InputContentElement<InputValue> element, InputResult result);
    }
}
