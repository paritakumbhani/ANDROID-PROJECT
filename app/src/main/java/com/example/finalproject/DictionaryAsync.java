package com.example.finalproject;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.net.wifi.WifiConfiguration.Status.strings;

public class DictionaryAsync extends AsyncTask<String, Integer, String[]> {

    /**
     *
     * referred from professors code
     */

    ProgressBar progressBar;
    ArrayList<Definition> definition;

    @Override
    protected void onPreExecute() {
        //Setup precondition to execute some task
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    protected String[] doInBackground(String... params) {
        //do the task
        definition = new ArrayList<>();
        String url1 = strings[0];
        try {
            URL url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            InputStream inputStream = conn.getInputStream();
            /*BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));*/

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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        publishProgress(100);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //Update the progress of current task
       // progressBar.setVisibility(View.VISIBLE);
        //progressBar.setProgress(values[0]);
    }

}


