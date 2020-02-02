package com.example.secondproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private MediaPlayer player;

    public class LocalBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        player = MediaPlayer.create(this, R.raw.rains_is_guns);
        player.setLooping(true);
        return mBinder;
    }

    public int playerPause() {
        player.pause();
        return player.getCurrentPosition();
    }

    public void playerStop() {
        player.pause();
    }

    public void playerStart(int musicPosition) {
        player.seekTo(musicPosition);
        player.start();
    }
}
