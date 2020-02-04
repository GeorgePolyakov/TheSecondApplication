package com.example.secondproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MusicService mService;
    boolean mBound = false;
    public int musicPosition = 0;
    SharedPreferences sPref;
    Button createDataBase;
    String songId;
    TextView textView;
    private RecyclerView recycleView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Music> music;
    private RecyclerView numbersList;
   // private NumbersRecycleAdapter numbersRecycleAdapter;

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

    public void InitializeList() {
        Music music1 = new Music("Adele", "Someone like you");
        Music music2 = new Music("Coolio", "Gansta Paradise");
        Music music3 = new Music("Eminem", "I am not afraid");
        Music music4 = new Music("Eminem", "Lose yourself");
        Music music5 = new Music("Coolio", "C U get there");
        Music music6 = new Music("in Point ", "Never give up");
        Music music7 = new Music("Eminem", "Legacy");
        Music music8 = new Music("Rhiana", "Lie the way to love");
        Music music9 = new Music("Rhiana", "The Monster");
        Music music10 = new Music("In Point", "Orlovskiy Diamond");
        music = new ArrayList<>();
        music.add(music1);
        music.add(music2);
        music.add(music3);
        music.add(music4);
        music.add(music5);
        music.add(music6);
        music.add(music7);
        music.add(music8);
        music.add(music9);
        music.add(music10);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.titleOfSong);
        fiilDatabase();
        createDataBase = findViewById(R.id.button);
        InitializeList();
       /* LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        numbersList = findViewById(R.id.recyclerView);
        numbersList.setLayoutManager(layoutManager);
        numbersList.setHasFixedSize(true);
        numbersRecycleAdapter = new NumbersRecycleAdapter(10, this, music);
        numbersList.setAdapter(numbersRecycleAdapter);*/
        savePosition(0);
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
            mService.playerStart(musicPosition, songId);
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
                    textView.setText(title + "");
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

    public void createDataBase(View view) {
        String URL = "content://com.example.secondproject.MusicContentProvider/friends";
        Uri friends = Uri.parse(URL);
        Cursor c = getContentResolver().query(friends, null, null, null, "title");
        String result = "Javacodegeeks Results:";
        if (!c.moveToFirst()) {
            Toast.makeText(this, result + " no content yet!", Toast.LENGTH_LONG).show();
        } else {
            do {
                result = result + "\n" + c.getString(c.getColumnIndex(MusicContentProvider.TITLE_OF_SONG)) +
                        " with id " + c.getString(c.getColumnIndex(MusicContentProvider.ID)) +
                        " has birthday: " + c.getString(c.getColumnIndex(MusicContentProvider.SINGER_NAME)) +
                        " has genre: " + c.getString(c.getColumnIndex(MusicContentProvider.GENRE_OF_MUSIC));
            } while (c.moveToNext());
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }


}
