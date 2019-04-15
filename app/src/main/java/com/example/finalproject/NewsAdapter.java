package com.example.finalproject;



import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<NewsModel> {
    private final Activity context;
    private final ArrayList<NewsModel> data;

    public NewsAdapter(Activity context, ArrayList<NewsModel> dataset) {
        super(context, R.layout.news_adapter, dataset);
        this.context = context;
        this.data = dataset;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final NewsModel singleitem = data.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.news_adapter, null, true);
        TextView txttitle = (TextView) rowView.findViewById(R.id.txttitle);
        TextView txtdesc = (TextView) rowView.findViewById(R.id.txtdesc);
        txttitle.setText("" + singleitem.getNewstitle());
        txtdesc.setText("" + singleitem.getNewsdesc());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, FullScreenNewsActivity.class).putExtra("url", "" + singleitem.getNewsweb()));
            }
        });
        return rowView;
    }
}