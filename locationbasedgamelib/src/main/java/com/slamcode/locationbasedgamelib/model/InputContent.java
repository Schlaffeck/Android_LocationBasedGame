package com.slamcode.locationbasedgamelib.model;

/**
 * Represents input content simple interface data for task displayed to user with interaction
 */

public interface InputContent extends GameTaskContentElement {

    /**
     * Commit given input status and return result
     * @return Input result
     */
    InputResult commitInput();

    void onInputCommitted(InputResult result);

    void addOnInputCommittedListener(OnInputCommittedListener listener);

    void removeOnInputCommittedListener(OnInputCommittedListener listener);

    void clearOnInputCommittedListeners();

    interface OnInputCommittedListener{

        void inputCommitted(InputResult result);
    }
}
