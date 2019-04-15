package com.example.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DictionaryFrameLayout extends AppCompatActivity {

    /**
     * this code is copied from FragementExample.java
     */

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dictionary_framelayout);
            Bundle dataToPass = getIntent().getExtras();
            DictionaryFragement dFragment = new DictionaryFragement();
            dFragment.setArguments( dataToPass );
            dFragment.setTablet(false);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentLayout, dFragment)
                    .addToBackStack("AnyName")
                    .commit();
        }
    }
