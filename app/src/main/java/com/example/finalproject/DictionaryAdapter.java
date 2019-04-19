package com.example.finalproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * custom adapter
 */
public class DictionaryAdapter extends ArrayAdapter<Definition> {

    /**
     * got from lab
     */
    ArrayList<Definition> DefinitionList;
    Context context;
    int i;


    public DictionaryAdapter(Context context, int i, ArrayList<Definition> DefinitionList) {
        super(context, i, DefinitionList);
        this.context = context;
        this.i = i;
        this.DefinitionList = DefinitionList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Definition definition = DefinitionList.get(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.activity_dictionary_listview, parent, false);
        TextView title = convertView.findViewById(R.id.DTitle);
        TextView lDefinition = convertView.findViewById(R.id.Ddefination);
        title.setText(definition.getTitle());
        lDefinition.setText(definition.getDefinition());

        return convertView;
    }
}