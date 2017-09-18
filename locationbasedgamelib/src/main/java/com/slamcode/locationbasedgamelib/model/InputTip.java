package com.slamcode.locationbasedgamelib.model;

/**
 * Tip data for input given by user in tasks or parts of tasks
 */
public class InputTip<InputValue> {

    private boolean inputValueAssigned;

    private InputValue inputValue;

    private String tipMessage;

    public boolean isInputValueAssigned() {
        return inputValueAssigned;
    }

    public void setInputValueAssigned(boolean inputValueAssigned) {
        this.inputValueAssigned = inputValueAssigned;
    }

    public InputValue getInputValue() {
        return inputValue;
    }

    public void setInputValue(InputValue inputValue) {
        this.inputValue = inputValue;
    }

    public String getTipMessage() {
        return tipMessage;
    }

    public void setTipMessage(String tipMessage) {
        this.tipMessage = tipMessage;
    }
}
