package com.example.secondproject;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {

    private final IBinder mBinder = new LocalBinder();

    public MediaPlayer getPlayer() {
        return player;
    }

    private MediaPlayer player = new MediaPlayer();
    Uri mediaPath;


    public class LocalBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    public int playerPause() {

        if (player.isPlaying()) {
            player.pause();
        }
        return player.getCurrentPosition();
    }

    public void playerStop() {
        if (player.isPlaying()) {
            player.pause();
        }
    }

    public int playerStart(int musicPosition, String songId) {
        Resources res = this.getResources();
        if (player == null) {
            player = new MediaPlayer();
        }
        int soundId = res.getIdentifier(songId, "raw", this.getPackageName());
        mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + soundId);
        try {
            player.setDataSource(getApplicationContext(), mediaPath);
            player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.seekTo(musicPosition);
        player.start();
        return player.getCurrentPosition();
    }

    public void destroyPlayer() {
        player.release();
        player = null;
    }

}
