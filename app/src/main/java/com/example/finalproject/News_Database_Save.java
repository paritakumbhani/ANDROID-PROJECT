package com.example.finalproject;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class News_Database_Save extends AppCompatActivity {
    ArrayList<NewsFeed> contactsList = new ArrayList<>();
    private static int ACTIVITY_VIEW_CONTACT = 33;
    int positionClicked = 0;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_database_list_item);

        //Get the fields from the screen:
        TextView nameEdit = (TextView) findViewById(R.id.row_name);
        TextView TextEdit = (TextView) findViewById(R.id.row_text);
        TextView TitleEdit = (TextView) findViewById(R.id.row_title);
        //Button insertButton = (Button)findViewById(R.id.row_title);
        ListView theList = (ListView) findViewById(R.id.the_list);

        Intent intent = getIntent();
        ProgressBar progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String srticle = intent.getStringExtra("title");
        Log.i("title", intent.getStringExtra("Title"));
        TitleEdit.setText(srticle);
        String full1 = intent.getStringExtra("text");
        TextEdit.setText(full1);
        //  String a = intent.getStringExtra("url");
        // url.setText(a);
        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();
        theList.setOnClickListener( click ->
        {
            //get the email and name that were typed
            String name = TitleEdit.getText().toString();
            String email = TextEdit.getText().toString();


            //add to the database and get the new ID
            ContentValues newRowValues = new ContentValues();
            //put string name in the NAME column:
            newRowValues.put(MyDatabaseOpenHelper.COL_TITLE, name);
            //put string email in the EMAIL column:
            newRowValues.put(MyDatabaseOpenHelper.COL_NEWS, email);
            //insert in the database:
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);

            //now you have the newId, you can create the Contact object
            NewsFeed newContact = new NewsFeed(name, email, newId);

            //add the new contact to the list:
            contactsList.add(newContact);
            //update the listView:



        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu_save) {
        getMenuInflater().inflate(R.menu.menu_save, menu_save);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent nextPage;

        switch(item.getItemId()){


                   }
        return true;
    }
}
