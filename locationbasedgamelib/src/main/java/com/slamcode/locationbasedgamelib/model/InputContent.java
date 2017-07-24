package com.slamcode.locationbasedgamelib.model;

/**
 * Represents input content simple interface data for task displayed to user
 */

public interface InputContent extends GameTaskContentElement {

    /**
     * Commit given input status and return result
     * @return Input result
     */
    InputResult commitInput();
}
