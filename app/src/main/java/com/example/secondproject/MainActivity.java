package com.example.secondproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.secondproject.DataBase.MyDataBaseClass;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyDataBaseClass objMyDataBaseClass;
    private RecyclerView recycleView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Music> music;
    private RecyclerView numbersList;
    private NumbersRecycleAdapter numbersRecycleAdapter;

    public void InitializeList()
    {
        Music music1 = new Music("Adele","Someone like you");
        Music music2 = new Music("Coolio","Gansta Paradise");
        Music music3 = new Music("Eminem","I am not afraid");
        Music music4 = new Music("Eminem","Lose yourself");
        Music music5 = new Music("Coolio","C U get there");
        Music music6 = new Music("in Point ","Never give up");
        Music music7 = new Music("Eminem","Legacy");
        Music music8 = new Music("Rhiana","Lie the way to love");
        Music music9 = new Music("Rhiana","The Monster");
        Music music10 = new Music("In Point","Orlovskiy Diamond");
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
        InitializeList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        numbersList = findViewById(R.id.recyclerView);
        numbersList.setLayoutManager(layoutManager);
        numbersList.setHasFixedSize(true);
        numbersRecycleAdapter = new NumbersRecycleAdapter(10, this,music);
        numbersList.setAdapter(numbersRecycleAdapter);
    }

    public void createDataBase(View view){
        try{
            objMyDataBaseClass.getReadableDatabase();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Exception while creating database: "+e.getMessage(),Toast.LENGTH_SHORT );
        }
    }


}
