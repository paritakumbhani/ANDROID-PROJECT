package com.example.finalproject;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NYTArticle extends AppCompatActivity {

    private static final String TAG = "TAG";


    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q=&api-key=89kmL9QdZSaSnHNrZtgRuPmf11e3mPQh";
    Toolbar mytoolbar;
    ArrayList<NewsModel> newsarray = new ArrayList<>();
    ListView newslistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_york_layout);
        //toolbar layout
        mytoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);

        //version info
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //getting list view to print data
        newslistview = findViewById(R.id.newslistview);
        sendAndRequestResponse();



    }

    private void sendAndRequestResponse() {

        mRequestQueue = Volley.newRequestQueue(this);

        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "" + response.toString());

                try {
                    JSONObject a = new JSONObject(response);
                    if (a.get("status").equals("OK")) {
                        JSONObject res = a.getJSONObject("response");
                        JSONArray docs = res.getJSONArray("docs");
                        for (int i = 0; i < docs.length(); i++) {
                            JSONObject obj = null;
                            try {
                                obj = docs.getJSONObject(i);


                                NewsModel model = new NewsModel();
                                model.setNewstitle(obj.getString("snippet"));
                                model.setNewsdesc(obj.getString("lead_paragraph"));
                                model.setNewsweb(obj.getString("web_url"));

                                newsarray.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        NewsAdapter newsAdapter = new NewsAdapter(NYTArticle.this, newsarray);
                        newslistview.setAdapter(newsAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "Error :" + error.toString());

            }
        });

        mRequestQueue.add(mStringRequest);
    }


    //menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nyt_menu, menu);
        return true;
    }

    //toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent nextPage;

        switch(item.getItemId()){

            case R.id.dictionaryMenuItem:
                nextPage = new Intent(NYTArticle.this, DictionaryActivity.class);
                startActivity(nextPage);
                break;

            case R.id.newsMenuItem:
                nextPage = new Intent(NYTArticle.this, NewsActivity.class);
                startActivity(nextPage);
                break;

            case R.id.trackerMenuItem:
                nextPage = new Intent(NYTArticle.this, FlightTrackerActivity.class);
                startActivity(nextPage);
                break;

            case R.id.help:
                alertExample();
                break;


            case R.id.snackbar:
                //Snackbar code:
                Snackbar sb = Snackbar.make(mytoolbar, "Exit", Snackbar.LENGTH_LONG)
                        .setAction("Go Back?", e -> finish());
                sb.show();
                break;

        }
        return true;
    }

    //help menu
    public void alertExample(){

        View middle = getLayoutInflater().inflate(R.layout.nyt_alert_box, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Author name:Parita Kumbhani, Activity version:v1.0.0, Instruction:This is alert");



        builder.create().show();

    }

}


