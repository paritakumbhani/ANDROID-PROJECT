package com.example.finalproject;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DictionaryAdapter extends ArrayAdapter <Definition> {



    ArrayList<Definition> DefinitionList;
    Context context;
    int i;


    public DictionaryAdapter(Context context,int i, ArrayList <Definition> DefinitionList){
        super(context,i,DefinitionList);
        this.context = context;
         this.i= i;
        DefinitionList = DefinitionList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        Definition definition = DefinitionList.get(position);

        view = LayoutInflater.from(context).inflate(R.layout.activity_dictionary_listview, parent, false);

        TextView title = view.findViewById(R.id.DTitle);
        TextView lDefinition = convertView.findViewById(R.id.Ddefination);

        title.setText(definition.getTitle());
        lDefinition.setText(definition.getDefinition());

        return view;
    }
}