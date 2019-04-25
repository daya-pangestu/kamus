package com.example.daya.kamus.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.daya.kamus.SplashActivity;
import com.example.daya.kamus.db.KamusModel.KamusModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static android.support.constraint.Constraints.TAG;
import static com.example.daya.kamus.db.DatabaseContract.KamusEnglish.TRANSLATE;
import static com.example.daya.kamus.db.DatabaseContract.KamusEnglish.WORD;
import static com.example.daya.kamus.db.DatabaseContract.KamusIndonesia.KATA;
import static com.example.daya.kamus.db.DatabaseContract.KamusIndonesia.TERJEMAHAN;
import static com.example.daya.kamus.db.DatabaseContract.TABLE_ENGLISH;
import static com.example.daya.kamus.db.DatabaseContract.TABLE_INDONESIA;

public class KamusHelper {


    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }


    public void close() {
        databaseHelper.close();
    }

    public String getDataByName(String word,int id) {
        StringBuilder gabung = new StringBuilder();
        Cursor cursor = null;
        if (id == SplashActivity.ENG) {
            cursor = database.query(TABLE_ENGLISH, null, WORD + " LIKE ?", new String[]{word}, null, null, _ID + " ASC", null);

        } else {
            cursor = database.query(TABLE_INDONESIA, null, KATA + " LIKE ?", new String[]{word}, null, null, _ID + " ASC", null);

        }

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            do {
                String sWord = null;
                String sTranslate = null;

                switch (id) {
                    case 200:
                        sWord = cursor.getString(cursor.getColumnIndexOrThrow(WORD));
                        sTranslate = cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATE));
                        break;
                    case 100:
                        sWord = cursor.getString(cursor.getColumnIndexOrThrow(KATA));
                        sTranslate = cursor.getString(cursor.getColumnIndexOrThrow(TERJEMAHAN));
                }

                Log.i(TAG, "getDataByName: hasil " + sWord);
                Log.i(TAG, "getDataByName: hasil " + sTranslate);

                gabung.append(sWord);
                gabung.append("\t");
                gabung.append(sTranslate);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return gabung.toString();
    }


    public ArrayList<KamusModel> getAllDataEng() {
        Cursor  cursor = database.query(TABLE_ENGLISH, null, null, null, null, null, _ID + " ASC", null);

        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModelEnglish;
        if (cursor.getCount() > 0) {
            do {
                kamusModelEnglish= new KamusModel();

                        kamusModelEnglish.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                        kamusModelEnglish.setWORD(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                        kamusModelEnglish.setTRANSLATE(cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATE)));


                arrayList.add(kamusModelEnglish);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<KamusModel> getAllDataIDN() {
        Cursor  cursor = database.query(TABLE_INDONESIA, null, null, null, null, null, _ID + " ASC", null);

        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModelEnglish;
        if (cursor.getCount() > 0) {
            do {
                kamusModelEnglish= new KamusModel();

                kamusModelEnglish.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModelEnglish.setWORD(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                kamusModelEnglish.setTRANSLATE(cursor.getString(cursor.getColumnIndexOrThrow(TERJEMAHAN)));

                arrayList.add(kamusModelEnglish);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSucces() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransaction(KamusModel kamusmodelenglish, int id) {
        String sql = null;
        if (id == 200) {

            sql = "INSERT INTO " + TABLE_ENGLISH + " (" + WORD + ", " + TRANSLATE
                    + ") VALUES (?, ?)";
        }else{

                sql = "INSERT INTO " + TABLE_INDONESIA+ " (" + KATA+ ", " + TERJEMAHAN
                        + ") VALUES (?, ?)";

        }

        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusmodelenglish.getWORD());
        stmt.bindString(2, kamusmodelenglish.getTRANSLATE());
        stmt.execute();
        stmt.clearBindings();
    }

}