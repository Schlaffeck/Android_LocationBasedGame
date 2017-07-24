package com.slamcode.locationbasedgamelib.model;

/**
 * Represents simple result of an user input into task
 */

public class InputResult {

    private boolean inputCorrect;

    private String inputCommitMessage;

    public boolean isInputCorrect() {
        return inputCorrect;
    }

    public void setInputCorrect(boolean inputCorrect) {
        this.inputCorrect = inputCorrect;
    }

    public String getInputCommitMessage() {
        return inputCommitMessage;
    }

    public void setInputCommitMessage(String inputCommitMessage) {
        this.inputCommitMessage = inputCommitMessage;
    }
}
