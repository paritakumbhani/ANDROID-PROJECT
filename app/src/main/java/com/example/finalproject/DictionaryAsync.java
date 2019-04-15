package com.example.finalproject;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DictionaryAsync extends AsyncTask<String, Integer, String[]> {

    ProgressBar progressBar;
    ArrayList<Definition> definition;

    @Override
    protected String[] doInBackground(String... params) {

        definition = new ArrayList<>();

        try {

            String urlS = params[0];
            URL url = new URL(urlS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            InputStream inputStream = conn.getInputStream();


            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(inputStream, "UTF8");
            xpp.nextTag();



            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    String tagName = xpp.getName();
                    if (tagName.equals("entry")) {


                        definition.add(new Definition());
                        definition.get(xpp.getEventType()).setTitle(xpp.getAttributeValue(null, "id"));
                        publishProgress(25);

                    }

                    if (tagName.equals("dt")) {
                        xpp.next();
                        definition.get(xpp.getEventType()).setDefinition(xpp.getText());
                        publishProgress(50);
                    }
                }
                xpp.next();
            }

        } catch (Exception e) {
            Log.e("AsyncTask", "Error");
        }
        publishProgress(100);
        return null;
    }

}


