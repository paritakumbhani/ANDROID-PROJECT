package com.example.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import android.support.design.widget.Snackbar;
public class SearchedNewsList extends AppCompatActivity {

    private String searchedWord;
    ProgressBar progressBar;
    private ListView listView;
    private List<NewsFeed> newsList= new ArrayList<>();
    private NewsListAdaptor newsListAdaptor;
    private String title;
    private String siteUrl;
    private  String text;

Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_news_list);
        newsList.clear();
        listView=(ListView)findViewById(R.id.listview);
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        searchedWord=intent.getStringExtra("LastNewsSearch");
        Log.i("LastNewsSearch",searchedWord);

        new ReadDataFromAPI().execute(searchedWord);

        newsListAdaptor = new NewsListAdaptor(newsList);
        Log.i("Adaptor",newsListAdaptor.toString());
        listView.setAdapter(newsListAdaptor);

            listView.setOnItemClickListener((parent,view,position,id)->{

                Snackbar.make(view,"searching detail data",Snackbar.LENGTH_LONG).show();

            NewsFeed article=newsList.get(position);
            Intent next=new Intent(SearchedNewsList.this,NewsFeeddetail.class);
            next.putExtra("title",article.getTitle().toString());
            Log.i("title",article.getTitle());
            next.putExtra("text",article.getNews().toString());
            next.putExtra("articleUrl",article.getUrl().toString());
            startActivity(next);

        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu_article) {
        getMenuInflater().inflate(R.menu.menu_article, menu_article);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent nextPage;

        switch(item.getItemId()){

            case R.id.dictionaryMenuItem:

            Snackbar  sb = Snackbar.make(toolbar, "Go Back?", Snackbar.LENGTH_LONG)
                    .setAction("CLICK HERE", e -> finish());
            sb.show();
                nextPage = new Intent(SearchedNewsList.this, DictionaryActivity.class);
                startActivity(nextPage);

                break;



            case R.id.trackerMenuItem:
                nextPage = new Intent(SearchedNewsList.this, FlightTrackerActivity.class);
                startActivity(nextPage);
                break;


            case R.id.NYTMenuItem:
                nextPage = new Intent(SearchedNewsList.this, NYTArticleActivity.class);
                startActivity(nextPage);
                break;
            case R.id.help:
                alertExample();

                break;

        }
        return true;
    }
    public void alertExample() {
        View middle = getLayoutInflater().inflate(R.layout.news_alert_dialog, null);


        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Author Name: Bansri Shah" + "\n\n" +
                "Activity version: v1.0.0" + "\n\n" +"Instruction:It is very useful application to view latest news based on your search"
        )
                .setView(middle);
        builder.create().show();

    }

    protected class ReadDataFromAPI extends AsyncTask<String,Integer,String>
    {
        HttpURLConnection connection=null;

        @Override
        protected String doInBackground(String... strings) {

            String query=strings[0];
            publishProgress(25);
            URL url = null;
            String result=null;
            try {
                url = new URL("http://webhose.io/filterWebContent?token=86940a5c-b094-4465-942e-81ce096fe5c9&format=json&sort=crawled&q="+URLEncoder.encode(query,"UTF-8"));
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                BufferedReader reader = null;
                publishProgress(50);
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"), 8);
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
                    title = object.getString("title") + "\n";
                    siteUrl = object.getString("url");
                    text = object.getString("text");
                    if(title!=null&&!title.isEmpty()&&siteUrl!=null&&!siteUrl.isEmpty()&&text!=null&&!text.isEmpty())
                        newsList.add(new NewsFeed(title,text,siteUrl));
                }
                publishProgress(75);
            } catch (Exception e1) {
                e1.printStackTrace();
                Log.i("Error",e1.getMessage());
            } return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setProgress(100);
            newsListAdaptor.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);





        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }
    }

    protected class NewsListAdaptor extends BaseAdapter
    {
        private List<NewsFeed> newsFeed= null;


        public NewsListAdaptor(List<NewsFeed> newsFeedList) {
            this.newsFeed = newsFeedList;
        }

        @Override
        public int getCount() {
            return newsFeed.size();
        }

        @Override
        public Object getItem(int position) {
            return newsFeed;
        }

        @Override
        public long getItemId(int position) {
            return newsFeed.get(position).getId();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listItem = inflater.inflate(R.layout.news_listitem, parent, false);
            TextView newsFeedRow;
            newsFeedRow = (TextView) listItem.findViewById(R.id.news_listitem);
            newsFeedRow.setText(newsFeed.get(position).getTitle());
            return listItem;
        }
    }


}
