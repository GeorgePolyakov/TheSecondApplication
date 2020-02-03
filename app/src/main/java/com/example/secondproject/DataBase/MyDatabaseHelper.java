package com.example.secondproject.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DBTask";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + "TaskTable (id integer primary key autoincrement,Singer TEXT , Title TEXT NOT NULL UNIQUE ,Genre TEXT)";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL("INSERT INTO TaskTable (Singer,Title,Genre) VALUES ('Eminem', 'No Love','Rap');");
        database.execSQL("INSERT INTO TaskTable (Singer,Title,Genre) VALUES ('Agata Christy', 'On The War','Rock');");
        database.execSQL("INSERT INTO TaskTable (Singer,Title,Genre) VALUES ('Animals', 'Rains is Guns','Rock');");
        database.execSQL("INSERT INTO TaskTable (Singer,Title,Genre) VALUES ('Timur Mitsuraev', 'The Candles Went Out','Pop');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){

        database.execSQL("DROP TABLE IF EXISTS MyEmployees");
        onCreate(database);
    }
}
