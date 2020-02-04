package com.example.secondproject;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

public class MusicService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private MediaPlayer player;
    Uri mediaPath;

    public class LocalBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        player = new MediaPlayer();
        //player = MediaPlayer.create(this, R.raw.rains_is_guns);
        String path = "rains_is_guns";
        //int soundId = res.getIdentifier(path, "raw", this.getPackageName());
        return mBinder;
    }

    public int playerPause() {
        player.pause();
        return player.getCurrentPosition();
    }

    public void playerStop() {
        player.pause();
    }

    public int playerStart(int musicPosition,String songId) {
        Resources res = this.getResources();
        int soundId = res.getIdentifier(songId, "raw", this.getPackageName());
        mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + soundId);
        Toast.makeText(getBaseContext(),
                songId + "", Toast.LENGTH_LONG).show();
        try {
            player.setDataSource(getApplicationContext(), mediaPath);
            player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.seekTo(musicPosition);
            player.start();

        return  player.getCurrentPosition();
    }
}
