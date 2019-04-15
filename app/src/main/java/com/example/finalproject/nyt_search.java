package com.example.finalproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class nyt_search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState);
        setContentView (R.layout.nyt_search_view);

    }

    protected   class  SearchDataFromApi extends AsyncTask<String,Integer,String>{

    HttpURLConnection connection = null;
    @Override
        protected String doInBackground(String...strings){

        String query=strings[0];
        publishProgress (25);
        URL url  =null;
        String result =null;
         try{
             url=new URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?q=&api-key=89kmL9QdZSaSnHNrZtgRuPmf11e3mPQh"+ URLEncoder.encode (query,"UTF-8"));
             connection = (HttpURLConnection) url.openConnection();
             connection.setDoInput(true);
             connection.setReadTimeout(10000);
             connection.setConnectTimeout(15000);
             connection.setRequestMethod("GET");

             connection.connect();
             BufferedReader reader = null;
             publishProgress(25);
             reader = new BufferedReader(new InputStreamReader (connection.getInputStream(),"UTF-8"), 8);
             StringBuilder sb = new StringBuilder();
             String line = null;
             while ((line = reader.readLine()) != null)
             {
                 sb.append(line + "\n");
             }
             result = sb.toString();
             JSONObject jObject = new JSONObject(result);
             JSONArray mArray = jObject.getJSONArray("posts");

             for (int i = 0; i < mArray.length(); i++) {
                 JSONObject object=mArray.getJSONObject(i);
               //  snippet = object.getString("snippet") + "\n";
                // lead_paragraph = object.getString("lead_paragraph");
                 //web_url = object.getString("web_url");
                 //if(snippet!=null&&!snippet.isEmpty()&&web_url!=null&&!web_url.isEmpty()&&lead_paragraph!=null&&!lead_paragraph.isEmpty())
                   //  newsList.add(new NewsAdapter (snippet,lead_paragraph,web_url));
             }
             publishProgress(100);
         } catch (Exception e1) {
             e1.printStackTrace();
             Log.i("Error",e1.getMessage());
         } return null;

    }



    }

}
