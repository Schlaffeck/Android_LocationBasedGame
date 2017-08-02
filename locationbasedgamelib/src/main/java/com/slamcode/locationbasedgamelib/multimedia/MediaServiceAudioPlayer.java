package com.slamcode.locationbasedgamelib.multimedia;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Audio player utilizing android media service to play audio files
 */

public final class MediaServiceAudioPlayer implements AudioPlayer {

    private final MediaPlayer mediaPlayer;
    private AudioStatus status;

    public MediaServiceAudioPlayer(Context context, int fileResourceId)
    {
        this.mediaPlayer = MediaPlayer.create(context, fileResourceId);
//        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        manager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);
    }

    public MediaServiceAudioPlayer(Context context, Uri filePathUri)
    {
        this.mediaPlayer = MediaPlayer.create(context, filePathUri);
//        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        manager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);
    }

    @Override
    public AudioStatus getStatus() {
        return this.status;
    }

    @Override
    public void start() {
        this.mediaPlayer.start();
        this.status = AudioStatus.Playing;
    }

    @Override
    public void pause() {
        this.mediaPlayer.pause();
        this.status = AudioStatus.Paused;
    }

    @Override
    public void stop() {
        this.mediaPlayer.stop();
        this.status = AudioStatus.NotStarted;
    }

    @Override
    public void restart() {
        this.mediaPlayer.stop();
        this.mediaPlayer.start();
        this.status = AudioStatus.Playing;
    }
}
