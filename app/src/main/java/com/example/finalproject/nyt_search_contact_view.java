package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

public class nyt_search_contact_view extends   AppCompatActivity {

    private EditText editText;
    private Button button;
    SharedPreferences sp;
    Toolbar toolbar;






    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate (savedInstanceState);
        setContentView (R.layout.nyt_search_view);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent nextpage;
        switch (item.getItemId ()){
            case R.id.dictionaryMenuItem:
                nextpage = new Intent(nyt_search_contact_view.this, DictionaryActivity.class);
                startActivity(nextpage);
                break;
            case R.id.trackerMenuItem:
                nextpage = new Intent(nyt_search_contact_view.this, FlightTrackerActivity.class);
                startActivity(nextpage);
                break;

            case R.id.newsMenuItem:
                nextpage = new Intent(nyt_search_contact_view.this, NewFeedActivity.class);
                startActivity(nextpage);
                break;


        }
        return true;
    }



}
