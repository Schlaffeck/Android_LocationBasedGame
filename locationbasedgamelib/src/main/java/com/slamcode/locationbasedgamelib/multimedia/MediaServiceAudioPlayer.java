package com.slamcode.locationbasedgamelib.multimedia;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Audio player utilizing android media service to play audio files
 */

public final class MediaServiceAudioPlayer implements AudioPlayer {

    private final Context context;
    private final int fileResourceId;
    private final Uri filePathUri;

    private MediaPlayer mediaPlayer;
    private AudioStatus status = AudioStatus.NotStarted;

    public MediaServiceAudioPlayer(Context context, int fileResourceId)
    {
        this.context = context;
        this.fileResourceId = fileResourceId;
        this.filePathUri = null;
    }

    public MediaServiceAudioPlayer(Context context, Uri filePathUri)
    {
        this.context = context;
        this.fileResourceId = 0;
        this.filePathUri = filePathUri;
    }

    @Override
    public AudioStatus getStatus() {
        return this.status;
    }

    @Override
    public void start() {
        if(this.mediaPlayer == null)
            this.initializePlayer();
        this.mediaPlayer.start();
        this.status = AudioStatus.Playing;
    }

    @Override
    public void pause() {
        if(mediaPlayer == null)
            return;
        this.mediaPlayer.pause();
        this.status = AudioStatus.Paused;
    }

    @Override
    public void stop() {
        if(mediaPlayer == null)
            return;
        if(this.mediaPlayer.isPlaying())
            this.mediaPlayer.pause();

        this.mediaPlayer.seekTo(0);
        this.releasePlayer();

        this.status = AudioStatus.NotStarted;
    }

    @Override
    public void restart() {

        if(this.mediaPlayer == null)
            this.initializePlayer();

        if(this.mediaPlayer.isPlaying())
            this.mediaPlayer.pause();

        this.mediaPlayer.seekTo(0);
        this.mediaPlayer.start();
        this.status = AudioStatus.Playing;
    }

    private void initializePlayer()
    {
        if(this.fileResourceId != 0)
        {
            this.mediaPlayer = MediaPlayer.create(this.context, this.fileResourceId);
        }
        else if(this.filePathUri != null)
        {
            this.mediaPlayer = MediaPlayer.create(this.context, this.filePathUri);
        }

        if(this.mediaPlayer != null)
            this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    releasePlayer();
                }
            });
    }

    private void releasePlayer()
    {
        if(this.mediaPlayer == null)
            return;

        this.mediaPlayer.reset();
        this.mediaPlayer.release();
        this.mediaPlayer = null;
    }
}
