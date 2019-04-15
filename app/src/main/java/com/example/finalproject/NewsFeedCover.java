package com.example.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedCover extends AppCompatActivity {


    private EditText editText;
    public static final int PUSHED_DELETE = 35;
    private Button button ,b;
    SharedPreferences sp;
    Toolbar toolbar;
    ArrayList<NewsFeed> contactList =new ArrayList<>();
    int positionClicked = 0;
    MyOwnAdapter myAdapter;
    private static int ACTIVITY_VIEW_CONTACT = 33;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_cover);
        ProgressBar progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText= (EditText) findViewById(R.id.searchbox);
        sp = getSharedPreferences("LastSearched", Context.MODE_PRIVATE);

        String savedString = sp.getString("LastItem", "");
        button=(Button) findViewById(R.id.search_button);
        editText.setText(savedString);

        button.setOnClickListener(e->
        {
            if(editText==null || editText.length()==0)
            {
                Toast.makeText(getBaseContext(),"please enter search term",Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(this, SearchedNewsList.class);
                intent.putExtra("LastNewsSearch", editText.getText().toString());
                startActivity(intent);
            }
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



                nextPage = new Intent(NewsFeedCover.this, DictionaryActivity.class);
                startActivity(nextPage);
                break;



            case R.id.trackerMenuItem:
                nextPage = new Intent(NewsFeedCover.this, FlightTrackerActivity.class);
                startActivity(nextPage);
                break;


            case R.id.NYTMenuItem:
                nextPage = new Intent(NewsFeedCover.this, NYTArticleActivity.class);
                startActivity(nextPage);
                break;
            case R.id.help:
               alertExample();

                break;


        }
        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        // This is an editor object
        SharedPreferences.Editor edit = sp.edit();
        // this was we are saving what user has type.
        // This is similar to Java String = next().
        String whatWasTyped = editText.getText().toString();
        edit.putString("LastItem", whatWasTyped);
        // It apply changes that are made
        edit.commit();


    }
    public void getExample(){
setContentView(R.layout.news_database_delete);
        MyDatabaseOpenHelper opener = new MyDatabaseOpenHelper(this);
     SQLiteDatabase   db =  opener.getWritableDatabase();
        Intent fromPreviousPage = getIntent();
        String name = fromPreviousPage.getStringExtra("Name");
        String news = fromPreviousPage.getStringExtra("Email");
        long id = fromPreviousPage.getLongExtra("Id", -1);
        TextView idView = (TextView)findViewById(R.id.ID);
        idView.setText("ID="+id);
        TextView titleview = (TextView)findViewById(R.id.title);
        idView.setText("ID="+name);
        TextView newsView = (TextView)findViewById(R.id.text);
        idView.setText("ID="+news);

        //When you click on the delete button:
        Button deleteButton = (Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(b -> {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);

            //This is the builder pattern, just call many functions on the same object:
            AlertDialog dialog = builder.setTitle("Alert!")
                    .setMessage("Do you want to delete?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //If you click the "Delete" button
                            int numDeleted = db.delete(MyDatabaseOpenHelper.TABLE_NAME, MyDatabaseOpenHelper.COL_ID + "=?", new String[] {Long.toString(id)});

                            Log.i("ViewContact", "Deleted " + numDeleted + " rows");

                            //set result to PUSHED_DELETE to show clicked the delete button
                            setResult(PUSHED_DELETE);
                            //go back to previous page:
                            finish();
                        }
                    })
                    //If you click the "Cancel" button:
                    .setNegativeButton("Cancel", (d,w) -> {  /* nothing */})
                    .create();

            //then show the dialog
            dialog.show();
        });


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
    public void saveExample() {


        setContentView(R.layout.news_database_savedlist);

        //Get the fields from the screen:

        ListView theList = (ListView)findViewById(R.id.the_list);

        //  Button insertButton = (Button)findViewById(R.id.save);
        //get a database:
        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();

        //query all the results from the database:
        String [] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_TITLE, MyDatabaseOpenHelper.COL_NEWS};
        Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        //find the column indices:
        int Title = results.getColumnIndex(MyDatabaseOpenHelper.COL_TITLE);
        int news = results.getColumnIndex(MyDatabaseOpenHelper.COL_NEWS);
        int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String name = results.getString(Title);
            String email = results.getString(news);
            String id = results.getString(idColIndex);

            //add the new Contact to the array list:
            contactList .add(new NewsFeed(name, email, id));
        }

        //create an adapter object and send it to the listVIew
        myAdapter = new MyOwnAdapter(contactList);
        theList.setAdapter(myAdapter);


        //This listens for items being clicked in the list view
        theList.setOnItemClickListener(( parent,  view,  position,  id) -> {
            Log.e("you clicked on :" , "item "+ position);
            //save the position in case this object gets deleted or updated
            positionClicked = position;

            //When you click on a row, open selected contact on a new page (ViewContact)
            NewsFeed chosenOne = contactList.get(position);
            Intent nextPage = new Intent(NewsFeedCover.this, News_Database_Save.class);
            nextPage.putExtra("Title", chosenOne.getTitle());
            nextPage.putExtra("text", chosenOne.getNews());
            nextPage.putExtra("url", chosenOne.getUrl());
            startActivityForResult(nextPage,ACTIVITY_VIEW_CONTACT);
        });


    }




    //This class needs 4 functions to work properly:
    protected class MyOwnAdapter extends BaseAdapter
    {
        private List<NewsFeed> listOFNews= new ArrayList<>();

        public MyOwnAdapter(List<NewsFeed> listOfnews ) {
          this.listOFNews = listOfnews;
        }

        @Override
        public int getCount() {
            return contactList.size();
        }

        public NewsFeed getItem(int position){
            return contactList.get(position);
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(R.layout.news_database_savedlist, parent, false );

            NewsFeed thisRow = getItem(position);
            TextView rowName = (TextView)newView.findViewById(R.id.row_name);
            TextView rowEmail = (TextView)newView.findViewById(R.id.row_title);

            TextView rowId = (TextView)newView.findViewById(R.id.row_text);

            rowName.setText("id:" + thisRow.getId());
            rowEmail.setText("title:" + thisRow.getTitle());
            rowId.setText("news:" + thisRow.getNews());
            //return the row:
            return newView;
        }

        public long getItemId(int position)
        {
            return getItem(position).getId();
        }
    }


}

