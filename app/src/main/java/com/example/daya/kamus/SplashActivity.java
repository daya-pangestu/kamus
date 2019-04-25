package com.example.daya.kamus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.daya.kamus.db.KamusHelper;
import com.example.daya.kamus.db.KamusModel.KamusModel;
import com.facebook.stetho.Stetho;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    public static final int IDN = 100;
    public static final int ENG= 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Stetho.initializeWithDefaults(this);

        progressBar = findViewById(R.id.progress_bar_splash);

        new LoadKamus().execute();


    }

    @SuppressLint("StaticFieldLeak")
    private class LoadKamus extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadKamus.class.getSimpleName();
        KamusHelper kamusHelper;
        AppPreferences appPreferences;
        double progres;
        final double maxProgres = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(SplashActivity.this);
            appPreferences = new AppPreferences(SplashActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = appPreferences.getFirstRun();

            if (firstRun) {
                ArrayList<KamusModel> kamusModelEnglishes = preLoadRaw(ENG);
                ArrayList<KamusModel> kamusModelIndonesias = preLoadRaw(IDN);

                kamusHelper.open();
                progres = 30;
                publishProgress((int) progres);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progres) / (kamusModelEnglishes.size() + kamusModelIndonesias.size());

                Log.i(TAG, "doInBackground: progress diff " + progressDiff);
                Log.i(TAG, "doInBackground: gede eng " + kamusModelEnglishes.size());
                Log.i(TAG, "doInBackground: gede id " + kamusModelIndonesias.size());

                kamusHelper.beginTransaction();

                try {
                    for (KamusModel model : kamusModelEnglishes) {
                        kamusHelper.insertTransaction(model,ENG);
                        progres += progressDiff/2;
                        publishProgress((int) progres);
                    }
                    kamusHelper.setTransactionSucces();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                kamusHelper.endTransaction();

                kamusHelper.beginTransaction();

                try {
                    for (KamusModel model : kamusModelIndonesias) {
                        kamusHelper.insertTransaction(model,IDN);
                        progres += progressDiff;
                        publishProgress((int) progres);

                    }
                    kamusHelper.setTransactionSucces();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                kamusHelper.endTransaction();


                kamusHelper.close();
                appPreferences.setFirstRun();
                publishProgress((int) maxProgres);
            }else {
                try {
                    synchronized (this) {
                        this.wait(1500);
                        publishProgress(50);

                        this.wait(1500);
                        publishProgress((int)maxProgres);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent o = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(o);
            finish();
        }
    }

    private ArrayList<KamusModel> preLoadRaw(int id) {

        ArrayList<KamusModel> kamusModels = new ArrayList<>();

        String line;
        BufferedReader reader;

        try {
            Resources res = getResources();
            InputStream raw_dict;

            if (id == ENG) {

                raw_dict = res.openRawResource(R.raw.english_indonesia);
            }else{
                    raw_dict = res.openRawResource(R.raw.indonesia_english);
            }

            reader = new BufferedReader(new InputStreamReader(raw_dict));

            do {

                line = reader.readLine();
                String[] splitString = line.split("\t");

                KamusModel kamusModelEnglish;

                kamusModelEnglish = new KamusModel(splitString[0], splitString[1]);

                kamusModels.add(kamusModelEnglish);
            } while (line != null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return kamusModels;

    }
}