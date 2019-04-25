package com.example.daya.kamus.PemrosesKata;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.daya.kamus.DetailActivity;
import com.example.daya.kamus.R;
import com.example.daya.kamus.SplashActivity;
import com.example.daya.kamus.db.KamusHelper;

import static android.support.constraint.Constraints.TAG;

public class Find  extends AsyncTask<String, Void, String> {

    private String temp;
    private final String word;
    private final String from;
    private final Context  context;

        public Find(Context activity,String word,String from) {
            this.context = activity;
            this.word = word;
            this.from = from;
        }

        @Override
        protected String doInBackground(String... strings) {
            KamusHelper kamusHelper = new KamusHelper(context);

            kamusHelper.open();

            if (from.equals(context.getString(R.string.eng))) {

                temp = kamusHelper.getDataByName(word, SplashActivity.ENG);

            } else {
                temp = kamusHelper.getDataByName(word, SplashActivity.IDN);
            }

            Log.i(TAG, "doInBackground: hasil gabung" + temp);

            kamusHelper.close();
            return temp;
        }

        @Override
        protected void onPostExecute(String aVoid) {

            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra(context.getString(R.string.doing), aVoid);
            context.startActivity(i);
        }
}
