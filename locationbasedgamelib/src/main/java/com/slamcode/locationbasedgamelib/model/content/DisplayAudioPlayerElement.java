package com.slamcode.locationbasedgamelib.model.content;

import android.net.Uri;

import com.slamcode.locationbasedgamelib.multimedia.AudioPlayer;
import com.slamcode.locationbasedgamelib.multimedia.NoMediaPlayerAttachedException;

/**
 * Simple element for displaying audio player on screen
 */

public class DisplayAudioPlayerElement extends DisplayContentElementAbstract {

    public final static String CONTENT_TYPE = "DISPLAY_AUDIO_PLAYER";

    public final static int CONTENT_TYPE_ID = CONTENT_TYPE.hashCode();

    private String contentType = CONTENT_TYPE;

    private String audioTitle;

    private int audioFileResourceId;

    private String audioFileUriString;

    private transient AudioPlayer audioPlayer;

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public int getContentTypeId() {
        return CONTENT_TYPE_ID;
    }

    public String getAudioTitle() {
        return audioTitle;
    }

    public void setAudioTitle(String audioTitle) {
        this.audioTitle = audioTitle;
    }

    public int getAudioFileResourceId() {
        return audioFileResourceId;
    }

    public void setAudioFileResourceId(int audioFileResourceId) {
        this.audioFileResourceId = audioFileResourceId;
    }

    public AudioPlayer getAudioPlayer() {

        return this.audioPlayer;
    }

    /**
     * Assign audio player to this display element.
     * Stops any other assigned audio player and overwrites it.
     * @param audioPlayer
     */
    public void useAudioPlayer(AudioPlayer audioPlayer)
    {
        if(this.audioPlayer != null)
            this.audioPlayer.stop();

        this.audioPlayer = audioPlayer;
    }

    public String getAudioFileUriString() {
        return audioFileUriString;
    }

    public void setAudioFileUriString(String audioFileUriString) {
        this.audioFileUriString = audioFileUriString;
    }
}
