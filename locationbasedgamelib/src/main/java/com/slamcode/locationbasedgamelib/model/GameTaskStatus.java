package com.slamcode.locationbasedgamelib.model;

/**
 * Enumerable representing set of statuses game task can be in
 */

public enum GameTaskStatus {

    NotStarted,
    Ongoing,
    Success,
    Failure,
    TriesThresholdReached,
}
