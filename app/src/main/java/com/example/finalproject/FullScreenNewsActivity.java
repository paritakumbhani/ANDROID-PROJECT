package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;


public class FullScreenNewsActivity extends AppCompatActivity {
    Toolbar mytoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_news);
        mytoolbar = findViewById(R.id.toolbar);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String newspageurl = getIntent ( ).getExtras ( ).getString ("url");
            WebView mywebview = (WebView) findViewById (R.id.webView);

            mywebview.loadUrl ("" + newspageurl);

        }

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
                nextPage = new Intent(FullScreenNewsActivity.this, DictionaryActivity.class);
                startActivity(nextPage);
                break;

            case R.id.newsMenuItem:
                nextPage = new Intent(FullScreenNewsActivity.this, NewsActivity.class);
                startActivity(nextPage);
                break;

            case R.id.trackerMenuItem:
                nextPage = new Intent(FullScreenNewsActivity.this, FlightTrackerActivity.class);
                startActivity(nextPage);
                break;


            case R.id.NYTMenuItem:
                nextPage = new Intent(FullScreenNewsActivity.this, NYTArticle.class);
                startActivity(nextPage);
                break;

        }
        return true;
    }

}
