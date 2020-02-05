package com.example.secondproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PickSingerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerSinger;
    Spinner spinnerGenre;
    List listSpinnerSingerAdapter;
    List spinnerGenreAdapter;
    String authorName;
    String genre;
    ArrayAdapter<String> singerAdapter;
    int state = 0;
    private RecyclerView songList;
    private MusicRecycleAdapter musicRecycleAdapter;

    public void fillAdapter() {
        listSpinnerSingerAdapter = new ArrayList();
        spinnerGenreAdapter = new ArrayList();
        String URL = "content://com.example.secondproject.MusicContentProvider/friends";
        Uri friends = Uri.parse(URL);
        Cursor c = getContentResolver().query(friends, null, null, null, "title");
        if (c.moveToFirst()) {
            do {

                listSpinnerSingerAdapter.add(c.getString(c.getColumnIndex(MusicContentProvider.SINGER_NAME)));
                spinnerGenreAdapter.add(c.getString(c.getColumnIndex(MusicContentProvider.GENRE_OF_MUSIC)));

            } while (c.moveToNext());
        }
        listSpinnerSingerAdapter = sortAdapter(listSpinnerSingerAdapter);
        spinnerGenreAdapter = sortAdapter(spinnerGenreAdapter);
    }

    public void recycleFiller(boolean state) {
        List songCurrentSinger = new ArrayList();
        String URL = "content://com.example.secondproject.MusicContentProvider/friends";
        Uri friends = Uri.parse(URL);
        Cursor c = getContentResolver().query(friends, null, null, null, "title");
        if (!c.moveToFirst()) {
        } else {
            do {
                if (state == true) {
                    if (authorName.equals(c.getString(c.getColumnIndex(MusicContentProvider.SINGER_NAME)))) {
                        songCurrentSinger.add(c.getString(c.getColumnIndex(MusicContentProvider.TITLE_OF_SONG)));
                    }
                } else {
                    if (genre.equals(c.getString(c.getColumnIndex(MusicContentProvider.GENRE_OF_MUSIC)))) {
                        songCurrentSinger.add(c.getString(c.getColumnIndex(MusicContentProvider.TITLE_OF_SONG)));
                    }
                }

            } while (c.moveToNext());
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

        ArrayAdapter<String> singerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSpinnerSingerAdapter);
        singerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSinger.setAdapter(singerAdapter);
        spinnerSinger.setOnItemSelectedListener(this);
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerGenreAdapter);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(genreAdapter);
        spinnerGenre.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.equals(singerAdapter)) {
            authorName = spinnerSinger.getSelectedItem().toString();
            recycleFiller(true);
        } else {
            genre = spinnerGenre.getSelectedItem().toString();
            recycleFiller(false);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Nothing TODO
    }

}
