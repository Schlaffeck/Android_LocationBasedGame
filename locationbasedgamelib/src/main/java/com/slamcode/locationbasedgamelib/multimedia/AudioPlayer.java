package com.slamcode.locationbasedgamelib.multimedia;

/**
 * Interface for simple audio player interaction
 */

public interface AudioPlayer {

    AudioStatus getStatus();

    void start();

    void pause();

    void stop();

    void restart();

    enum AudioStatus
    {
        NotStarted,
        Playing,
        Paused,
        Finished,
    }
}
