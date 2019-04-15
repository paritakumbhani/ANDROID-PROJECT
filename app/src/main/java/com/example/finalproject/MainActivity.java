package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button dictionary;
    Button news;
    Button tracker;
    Button NYT;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dictionary = findViewById(R.id.dictionary);
        dictionary.setOnClickListener( c -> {
            Intent nextPage = new Intent(MainActivity.this, DictionaryActivity.class);
            startActivity(nextPage);
        });

        news = findViewById(R.id.news);
        news.setOnClickListener( c -> {
            Intent nextPage = new Intent(MainActivity.this, NewsFeedCover.class);
            startActivity(nextPage);
        });

        tracker = findViewById(R.id.tracker);
        tracker.setOnClickListener(c -> {
            Intent nextPage = new Intent(MainActivity.this, FlightTrackerActivity.class);
            startActivity(nextPage);
        });

        NYT = findViewById(R.id.NYT);
        NYT.setOnClickListener(c -> {
            Intent nextPage = new Intent(MainActivity.this, NYTArticleActivity.class);
            startActivity(nextPage);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent nextPage;

        switch(item.getItemId()){

            case R.id.dictionaryMenuItem:
                nextPage = new Intent(MainActivity.this, DictionaryActivity.class);
                startActivity(nextPage);
                break;

            case R.id.newsMenuItem:
                nextPage = new Intent(MainActivity.this, NewsFeedCover.class);
                startActivity(nextPage);
                break;

            case R.id.trackerMenuItem:
                nextPage = new Intent(MainActivity.this, FlightTrackerActivity.class);
                startActivity(nextPage);
                break;


            case R.id.NYTMenuItem:
                nextPage = new Intent(MainActivity.this, NYTArticleActivity.class);
                startActivity(nextPage);
                break;

        }
        return true;
    }



}