package com.example.finalproject;

import android.content.ContentValues;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class DictionaryActivity extends AppCompatActivity {


    EditText search;
    ImageButton searchButton;
    Toolbar toolbar;
    Button save;
    ListView listView;
    boolean saved;
    DictionaryDatabaseHelper helper;
    SQLiteDatabase db;
    ArrayList<Definition> definitionList;
    DictionaryAdapter myAdapter;

    final static String TABLE_NAME = "Dictionary";
    final static String COL_TITLE = "TITLE";
    final static String COL_DEFINITION = "DEFINITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        /**
         * getting view id on variables
         */

        search = findViewById(R.id.DSearchBox);
        searchButton = findViewById(R.id.DSearchButton);
        toolbar = findViewById(R.id.DToolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.DListView);
        definitionList = new ArrayList<>();
        save = findViewById(R.id.DSavedFile);

        //is tablet
        boolean isTablet = findViewById(R.id.fragmentLayout) != null;
        helper = new DictionaryDatabaseHelper(this);
        db = helper.getWriteableDatabase();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("FileName", 0);
        SharedPreferences.Editor editor = sp.edit();

/**
 * toggle between saved words and search from api
 */
        save.setOnClickListener(c -> {
            if (saved == false) {
                saved = true;
            } else {
                saved = false;
            }
            editor.putBoolean("DictionaryS", saved);
            editor.commit();
            saved();
        });


        myAdapter = new DictionaryAdapter(getApplicationContext(), R.layout.activity_dictionary_listview, definitionList);
        listView.setAdapter(myAdapter);

        searchButton.setOnClickListener(c -> {

            if (search.getText() != null) {
                editor.putString("search", String.valueOf(search.getText()));
                editor.commit();
                definitionList.clear();
                DictionaryAsync query = new DictionaryAsync();
                query.execute("https://www.dictionaryapi.com/api/v1/references/sd3/xml/" + search.getText() + "?key=4556541c-b8ed-4674-9620-b6cba447184f");
                myAdapter.notifyDataSetChanged();
            }
        });

        /**
         * code from professor
         */
        listView.setOnItemClickListener((list, item, position, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putLong("ID", definitionList.get(position).getid());
            dataToPass.putString("Title", definitionList.get(position).getTitle());
            dataToPass.putString("Definition", definitionList.get(position).getDefinition());

            if (isTablet) {

                DictionaryFragement dFragment = new DictionaryFragement();
                dFragment.setArguments(dataToPass);
                dFragment.setTablet(true);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentLayout, dFragment).addToBackStack("AnyName").commit();
            } else //isPhone
            {

                Intent nextActivity = new Intent(DictionaryActivity.this, DictionaryFrameLayout.class);
                nextActivity.putExtras(dataToPass); //send data to next activity

                startActivityForResult(nextActivity, 345); //make the transition
            }
        });

        saved = sp.getBoolean("DictionaryS", false);
        saved();

        //set the text for search
        search.setText(sp.getString("DictionarySearch", null));
        if (search.getText() != null) {
            definitionList.clear();
            DictionaryAsync query = new DictionaryAsync();
            query.execute("https://www.dictionaryapi.com/api/v1/references/sd3/xml/" + search.getText() + "?key=4556541c-b8ed-4674-9620-b6cba447184f");
            myAdapter.notifyDataSetChanged();
        }

        if (saved == true) {
            Cursor cursor = db.rawQuery("SELECT * from " + TABLE_NAME, null);
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                long id = cursor.getLong(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                String definition = cursor.getString(cursor.getColumnIndex(COL_DEFINITION));
                addList(id, title, definition);
                cursor.moveToNext();
            }
        }
    }

    /**
     * saved list
     * get the list of searched word
     */
    public void saved() {
        if (saved == true) {

            searchButton.setEnabled(false);
            definitionList.clear();
            myAdapter.notifyDataSetChanged();

            Toast toast = Toast.makeText(getApplicationContext(), "geting data from saved list", Toast.LENGTH_SHORT);
            toast.show();

            Cursor cursor = db.rawQuery("SELECT * from " + TABLE_NAME, null);
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {

                long id = cursor.getLong(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                String definition = cursor.getString(cursor.getColumnIndex(COL_DEFINITION));
                addList(id, title, definition);
                cursor.moveToNext();
            }

            myAdapter.notifyDataSetChanged();

        } else if (saved == false) {

            searchButton.setEnabled(true);
            definitionList.clear();
            myAdapter.notifyDataSetChanged();
            DictionaryAsync query = new DictionaryAsync();
            query.execute("https://www.dictionaryapi.com/api/v1/references/sd3/xml/" + search.getText() + "?key=4556541c-b8ed-4674-9620-b6cba447184f");
            myAdapter.notifyDataSetChanged();

        }
    }

    /**
     *
     * @param id
     * @param title
     * @param definat
     */
    void addList(Long id, String title, String definat) {

        Definition definition = new Definition(id, title, definat);
        definitionList.add(definition);
    }

    /**
     *
     * @param title
     * @param definition
     */
    public void definationSave(String title, String definition) {

        //helped by professor
        ContentValues cv = new ContentValues();
        cv.put(helper.COL_TITLE, title);
        cv.put(helper.COL_DEFINITION, definition);

        long id = db.insert(TABLE_NAME, "ColumnName", cv);
        if (saved == true) {

            addList(id, title, definition);
            myAdapter.notifyDataSetChanged();
        }
    }

    /**
     *
     * @param id
     */
    public void deleteDefinitionId(int id) {

        //copied from my lab8
        db.delete(TABLE_NAME, "id=?", new String[]{Long.toString(id)});

        if (saved == true) {

            definitionList.clear();
            Cursor cursor = db.rawQuery("SELECT * from " + TABLE_NAME, null);
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                long idd = cursor.getLong(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                String definition = cursor.getString(cursor.getColumnIndex(COL_DEFINITION));
                addList(idd, title, definition);
                cursor.moveToNext();
            }
        }
        myAdapter.notifyDataSetChanged();
    }

    /**
     * create option menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dictionary_menu, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent nextPage;

        switch (item.getItemId()) {

            case R.id.newsMenuItem:
                nextPage = new Intent(DictionaryActivity.this, NewsFeedCover.class);
                startActivity(nextPage);
                break;

            case R.id.trackerMenuItem:
                nextPage = new Intent(DictionaryActivity.this, FlightTrackerActivity.class);
                startActivity(nextPage);
                break;

            case R.id.NYTMenuItem:
                nextPage = new Intent(DictionaryActivity.this, NYTArticle.class);
                startActivity(nextPage);
                break;

            case R.id.dictionary_help:
                alertMessage();
                break;

        }
        return true;
    }

    /**
     * alert message for help menu
     */
    public void alertMessage() {

        View middle = getLayoutInflater().inflate(R.layout.activity_dictionary_help, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.d_help_tittle);
        builder.setMessage(R.string.d_help_text);

                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create().show();

    }

    /**
     * result to initiate save and delete
     * and load the data from ID
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            long id = data.getLongExtra("ID", 0);

            boolean isDelete = data.getBooleanExtra("TYPE", false);

            if (isDelete == true) {

                deleteDefinitionId((int) id);

            } else {

                definationSave(data.getStringExtra("TITLE"), data.getStringExtra("DEFINITION"));
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "saved", Snackbar.LENGTH_LONG);
                snackbar.setAction("close", v -> snackbar.dismiss());
                snackbar.show();
            }
        }
    }


}

