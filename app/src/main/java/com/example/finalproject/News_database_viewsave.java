package com.example.finalproject;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class News_database_viewsave extends AppCompatActivity
{
    ArrayList<NewsFeed> contactList =new ArrayList<>();
    int positionClicked = 0;
    MyOwnAdapter myAdapter;
    private static int ACTIVITY_VIEW_CONTACT = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        myAdapter = new MyOwnAdapter();
        theList.setAdapter(myAdapter);


        //This listens for items being clicked in the list view
        theList.setOnItemClickListener(( parent,  view,  position,  id) -> {
            Log.e("you clicked on :" , "item "+ position);
            //save the position in case this object gets deleted or updated
            positionClicked = position;

            //When you click on a row, open selected contact on a new page (ViewContact)
            NewsFeed chosenOne = contactList.get(position);
            Intent nextPage = new Intent(News_database_viewsave.this, News_Database_Save.class);
            nextPage.putExtra("Title", chosenOne.getTitle());
            nextPage.putExtra("text", chosenOne.getNews());
            nextPage.putExtra("url", chosenOne.getUrl());
            startActivityForResult(nextPage,ACTIVITY_VIEW_CONTACT);
        });


    }




    //This class needs 4 functions to work properly:
    protected class MyOwnAdapter extends BaseAdapter
    {
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
