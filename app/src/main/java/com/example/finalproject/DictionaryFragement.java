package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DictionaryFragement extends Fragment {

    /**
     * this code is copied from DetailFragement.java
     */

    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;

    public void setTablet(boolean tablet) {
        isTablet = tablet;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        id = dataFromActivity.getLong("ID");

        View result = inflater.inflate(R.layout.activity_dictionary_fragment, container, false);

        TextView title = result.findViewById(R.id.DFragTitle);
        title.setText(dataFromActivity.getString("Title"));

        TextView defination = result.findViewById(R.id.DFragDefination);
        defination.setText(dataFromActivity.getString("Defination"));

        // get the delete button, and add a click listener:
        Button deleteB = (Button) result.findViewById(R.id.DFragDelete);
        deleteB.setOnClickListener(clk -> {

            if (isTablet) { //both the list and details are on the screen:
                DictionaryActivity parent = (DictionaryActivity) getActivity();
                parent.deleteDefinitionId((int) id);//this deletes the item and updates the list
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            } else {
                DictionaryFrameLayout parent = (DictionaryFrameLayout) getActivity();
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra("ID", dataFromActivity.getLong("ID"));

                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
            }
        });


        Button saveB = (Button) result.findViewById(R.id.DFragSave);
        saveB.setOnClickListener(clk -> {

            if (isTablet) {
                DictionaryActivity parent = (DictionaryActivity) getActivity();
                parent.definationSave(dataFromActivity.getString("Title"), dataFromActivity.getString("Definition"));

                Snackbar sb = Snackbar.make(result.findViewById(android.R.id.content), "Saved", Snackbar.LENGTH_LONG);
                sb.setAction("Close", v -> sb.dismiss());

                sb.show();
            } else {

                DictionaryFrameLayout parent = (DictionaryFrameLayout) getActivity();
                Intent backToFragment = new Intent();
                backToFragment.putExtra("ID", dataFromActivity.getLong("ID"));
                backToFragment.putExtra("TITLE", dataFromActivity.getString("Title"));
                backToFragment.putExtra("DEFINITION", dataFromActivity.getString("Definition"));
                parent.setResult(Activity.RESULT_OK, backToFragment);
                parent.finish();
            }

        });

        return result;
    }
}
