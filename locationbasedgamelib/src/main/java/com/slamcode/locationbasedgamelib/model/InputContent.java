package com.slamcode.locationbasedgamelib.model;

/**
 * Represents input content simple interface data for task displayed to user with interaction
 */

public interface InputContent<InputValue> extends GameTaskContentElement {

    /**
     * Commit given input status and return result
     * @return Input result
     */
    InputResult commitInput(InputValue inputValue);

    void onInputCommitting(InputCommitParameters<InputValue> parameters);

    void onInputCommitted(InputResult result);

    void addOnInputCommittedListener(OnInputCommittedListener listener);

    void removeOnInputCommittedListener(OnInputCommittedListener listener);

    void clearOnInputCommittedListeners();

    interface OnInputCommittedListener<InputValue>{

        void inputCommitting(InputCommitParameters<InputValue> parameters);

        void inputCommitted(InputResult result);
    }
}
