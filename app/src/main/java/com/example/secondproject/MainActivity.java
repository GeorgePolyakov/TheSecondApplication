package com.example.secondproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MusicService mService;
    boolean mBound = false;
    public int musicPosition = 0;
    SharedPreferences sPref;
    String songId;
    TextView textViewTitle;
    TextView textViewSinger;
    TextView textViewGenre;
    BroadcastReceiver getKeyReciver;
    Context context;
    String data = "";
    private static final String SHARED_PREFS = "sharedPrefs";

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTitle = findViewById(R.id.titleOfSong);
        textViewSinger = findViewById(R.id.textView2);
        textViewGenre = findViewById(R.id.textView3);
        fiilDatabase();
        context = this;
        songId = loadData(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction("data");
        getKeyReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null)
                    Toast.makeText(context, intent.getStringExtra("data").toString(), Toast.LENGTH_LONG).show();
                String URL = "content://com.example.secondproject.MusicContentProvider/friends";
                Uri friends = Uri.parse(URL);
                data = intent.getExtras().getString("data");
                Cursor c = getContentResolver().query(friends, null, null, null, "title");

                if (c.moveToFirst()) {
                    do {
                        if (data.equals(c.getString(c.getColumnIndex(MusicContentProvider.TITLE_OF_SONG)))) {
                            songId = c.getString(c.getColumnIndex(MusicContentProvider.PATH_TO_MUSIC));
                            textViewTitle.setText(data);
                            textViewSinger.setText(c.getString(c.getColumnIndex(MusicContentProvider.SINGER_NAME)));
                            textViewGenre.setText(c.getString(c.getColumnIndex(MusicContentProvider.GENRE_OF_MUSIC)));
                            Toast.makeText(getBaseContext(),
                                    songId + "", Toast.LENGTH_LONG).show();
                            saveData(context,songId);
                            savePosition(0);
                        }

                    } while (c.moveToNext());
                }
            }
        };
        registerReceiver(getKeyReciver, filter);
    }

    public static String loadData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String text = sharedPreferences.getString("KEY", "");
        return text;
    }

    public static void saveData(Context context,String position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("KEY", position);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void savePosition(int position) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("musicPositionKey", position);
        ed.commit();
    }

    public void onClickStart(View v) {
        if (mBound) {
            sPref = getPreferences(MODE_PRIVATE);
            musicPosition = sPref.getInt("musicPositionKey", 0);
            savePosition(mService.playerStart(musicPosition, songId));
        }
    }

    public void onClickPause(View v) {
        if (mBound) {
            savePosition(mService.playerPause());
        }
    }

    public void onClickStop(View v) {
        if (mBound) {
            mService.playerStop();
            savePosition(0);
        }
    }

    public void onClickPick(View v) {
        Intent intent = new Intent(this, PickSingerActivity.class);
        startActivity(intent);
    }

    public void deleteAllBirthdays() {
        // delete all the records and the table of the database provider
        String URL = "content://com.example.secondproject.MusicContentProvider/friends";
        Uri friends = Uri.parse(URL);
        int count = getContentResolver().delete(
                friends, null, null);
        String countNum = "Javacodegeeks: " + count + " records are deleted.";
        Toast.makeText(getBaseContext(),
                countNum, Toast.LENGTH_LONG).show();

    }

    public void fiilDatabase() {
        deleteAllBirthdays();
        ContentValues values = new ContentValues();
        Resources res = this.getResources();
        XmlResourceParser _xml = res.getXml(R.xml.fill_music_data);
        try {
            int eventType = _xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if ((eventType == XmlPullParser.START_TAG)
                        && (_xml.getName().equals("record"))) {
                    String genreName = _xml.getAttributeValue(0);
                    String pathMusic = _xml.getAttributeValue(1);
                    String singerName = _xml.getAttributeValue(2);
                    String title = _xml.getAttributeValue(3);
                    values.put(MusicContentProvider.TITLE_OF_SONG, title);
                    values.put(MusicContentProvider.SINGER_NAME, singerName);
                    values.put(MusicContentProvider.GENRE_OF_MUSIC, genreName);
                    values.put(MusicContentProvider.PATH_TO_MUSIC, pathMusic);
                    Uri uri = getContentResolver().insert(
                            MusicContentProvider.CONTENT_URI, values);
                }
                eventType = _xml.next();
            }
        } catch (XmlPullParserException e) {
            Log.e("Test", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("Test", e.getMessage(), e);

        } finally {
            _xml.close();
        }
    }


}
