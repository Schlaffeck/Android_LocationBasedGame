package com.slamcode.locationbasedgamelib.model;

/**
 * Encapsulates input parameters passed by input event
 */

public final class InputCommitParameters<InputValue> {

    private InputValue value;

    public InputCommitParameters(InputValue value)
    {
        this.value = value;
    }

    public InputValue getValue() {
        return value;
    }

    public void setValue(InputValue value) {
        this.value = value;
    }
}
