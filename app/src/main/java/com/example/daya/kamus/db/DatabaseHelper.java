package com.example.daya.kamus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.daya.kamus.db.DatabaseContract.KamusEnglish.TRANSLATE;
import static com.example.daya.kamus.db.DatabaseContract.KamusEnglish.WORD;
import static com.example.daya.kamus.db.DatabaseContract.KamusIndonesia.KATA;
import static com.example.daya.kamus.db.DatabaseContract.KamusIndonesia.TERJEMAHAN;
import static com.example.daya.kamus.db.DatabaseContract.TABLE_ENGLISH;
import static com.example.daya.kamus.db.DatabaseContract.TABLE_INDONESIA;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbkamus";
    private static final int DATABASE_VERSION = 5;

    public static String CREATE_TABLE_ENGLISH = "create table "+ TABLE_ENGLISH +
            "(" + _ID + " integer primary key autoincrement, " +
            WORD + " text not null, " +
            TRANSLATE + " text not null);";


    public static String CREATE_TABLE_INDONESIA ="create table "+ TABLE_INDONESIA+
            "(" + _ID + " integer primary key autoincrement, " +
            KATA + " text not null, " +
            TERJEMAHAN+ " text not null);";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENGLISH);
        db.execSQL(CREATE_TABLE_INDONESIA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENGLISH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDONESIA);
        onCreate(db);
    }
}
