package com.example.secondproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PickSingerActivity extends AppCompatActivity {

    Spinner spinnerSinger;
    Spinner spinnerGenre;
    List spinnerSingerAdapter;
    List spinnerGenreAdapter;
    String authorName;
    String genre;
    int state = 0;
    private RecyclerView songList;
    private MusicRecycleAdapter musicRecycleAdapter;

    public void fillAdapter() {
        spinnerSingerAdapter = new ArrayList();
        spinnerGenreAdapter = new ArrayList();
        String URL = "content://com.example.secondproject.MusicContentProvider/friends";
        Uri friends = Uri.parse(URL);
        Cursor c = getContentResolver().query(friends, null, null, null, "title");
        if (c.moveToFirst()) {
            do {

                spinnerSingerAdapter.add(c.getString(c.getColumnIndex(MusicContentProvider.SINGER_NAME)));
                spinnerGenreAdapter.add(c.getString(c.getColumnIndex(MusicContentProvider.GENRE_OF_MUSIC)));

            } while (c.moveToNext());
        }
        spinnerSingerAdapter = sortAdapter(spinnerSingerAdapter);
        spinnerGenreAdapter = sortAdapter(spinnerGenreAdapter);
    }

    public void recycleFiller(boolean state)
    {
        List songCurrentSinger = new ArrayList();
        String URL = "content://com.example.secondproject.MusicContentProvider/friends";
        Uri friends = Uri.parse(URL);
        Cursor c = getContentResolver().query(friends, null, null, null, "title");
        String result = "Javacodegeeks Results:";
        if (!c.moveToFirst()) {
            Toast.makeText(this, result + " no content yet!", Toast.LENGTH_LONG).show();
        } else {
            do {
                 if(state==true) {
                     if (authorName.equals(c.getString(c.getColumnIndex(MusicContentProvider.SINGER_NAME)))) {
                         songCurrentSinger.add(c.getString(c.getColumnIndex(MusicContentProvider.TITLE_OF_SONG)));
                     }
                 }else{
                     if (genre.equals(c.getString(c.getColumnIndex(MusicContentProvider.GENRE_OF_MUSIC)))) {
                         songCurrentSinger.add(c.getString(c.getColumnIndex(MusicContentProvider.TITLE_OF_SONG)));
                     }
                 }

            } while (c.moveToNext());
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        songList = findViewById(R.id.recyclerView);
        songList.setLayoutManager(layoutManager);
        songList.setHasFixedSize(true);
        musicRecycleAdapter = new MusicRecycleAdapter(songCurrentSinger.size(), this, songCurrentSinger);
        songList.setAdapter(musicRecycleAdapter);
    }

    public List sortAdapter(List spinnerAdapter) {
        Set<String> uniqueSinger = new HashSet<String>(spinnerAdapter);
        spinnerAdapter = new ArrayList<String>(uniqueSinger);
        return spinnerAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_singer);
        fillAdapter();
        spinnerSinger = findViewById(R.id.pickSinger);
        spinnerGenre = findViewById(R.id.pickGenre);


        ArrayAdapter<String> SingerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerSingerAdapter);
        SingerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSinger.setAdapter(SingerAdapter);
        spinnerSinger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                authorName = spinnerSinger.getSelectedItem().toString();
                recycleFiller(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> GenreAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerGenreAdapter);
        GenreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(GenreAdapter);
        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                genre = spinnerGenre.getSelectedItem().toString();
                recycleFiller(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();


    }
}
