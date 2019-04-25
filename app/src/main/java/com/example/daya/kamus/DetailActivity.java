package com.example.daya.kamus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private TextView detailKata;
    private TextView detailTerjemahan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getSupportActionBar()!= null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detailKata = findViewById(R.id.detail_kata);
        detailTerjemahan = findViewById(R.id.detail_terjemahan);


        Intent i = getIntent();
        String kata = i.getStringExtra(getString(R.string.doing));

        String[] split = kata.split("\t");

        Log.i("kata ", "onCreate: " + split[0]);
        Log.i("kata ", "onCreate: " + split[1]);

        detailKata.setText(split[0]);

        detailTerjemahan.setText(split[1]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
        }

        return super.onOptionsItemSelected(item);

    }
}
