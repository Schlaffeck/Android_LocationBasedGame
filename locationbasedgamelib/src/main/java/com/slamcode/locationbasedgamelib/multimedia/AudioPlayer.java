package com.slamcode.locationbasedgamelib.multimedia;

import android.content.Context;

import com.slamcode.locationbasedgamelib.permission.PermissionRequestor;

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

    interface Provider{

        AudioPlayer provideAudioPlayer(int audioResourceId);

        AudioPlayer provideAudioPlayer(String audioFilePathUri);
    }
}
