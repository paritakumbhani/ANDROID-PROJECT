package com.example.finalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsFeeddetail extends AppCompatActivity {

    private TextView title;
    private TextView full;
     private TextView url;
    //private Button url;
    private List<NewsFeed> newsList = new ArrayList<>();
    private Button savenews, B;
    Toolbar toolbar;
    Button B_SAVE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feeddetail);
        ProgressBar progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        title = (TextView) findViewById(R.id.title);
        full = (TextView) findViewById(R.id.text);
        url=(TextView)findViewById(R.id.url);
        //  Button insertButton = (Button) findViewById(R.id.url);

        savenews = (Button) findViewById(R.id.button);


        String srticle = intent.getStringExtra("title");
        Log.i("title", intent.getStringExtra("title"));
        title.setText(srticle);
        String full1 = intent.getStringExtra("text");
        full.setText(full1);
        String a = intent.getStringExtra("articleUrl");
        url.setText(a);
        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();
        savenews.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button c=(Button) v;
                String click=c.getText().toString();
                ContentValues newRowValues = new ContentValues();
                //put string name in the NAME column:
                newRowValues.put(MyDatabaseOpenHelper.COL_TITLE, srticle);
                //put string email in the EMAIL column:
                newRowValues.put(MyDatabaseOpenHelper.COL_NEWS,full1);
               // newRowValues.put(MyDatabaseOpenHelper.COL_ID,a);
                //insert in the database:
                long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);
                System.out.println(newId);




            }

        });
        Button button_save=findViewById(R.id.save_article);

button_save.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {
      //  Snackbar.make(v,"clicked button",Snackbar.LENGTH_LONG).show();

        Intent nextPage = new Intent(NewsFeeddetail.this, News_Database_Save.class);
        nextPage.putExtra("Title", srticle);
        nextPage.putExtra("text", full1);
     //   nextPage.putExtra("url", a);
        startActivityForResult(nextPage,33);

    }
});




    }
    public boolean onCreateOptionsMenu(Menu menu_news_article) {
        getMenuInflater().inflate(R.menu.menu_news_article, menu_news_article);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent nextPage;

        switch(item.getItemId()){



            case R.id.save:
                Toast.makeText(getBaseContext(),"saved data",Toast.LENGTH_LONG).show();



                break;

        }
        return true;
    }
    public void save()
    {
        setContentView(R.layout.news_database_savedlist);

    }

}
