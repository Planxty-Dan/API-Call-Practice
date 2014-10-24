package com.bunniesarecute.admin.api_call_practice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by admin on 10/22/14.
 */
public class DictionaryAPI extends AsyncTask {
    String badWord = "butt";
    String wordType = "noun";
    Context thisContext;
    private ProgressDialog progressDialog;

    // Gets context from fragment or activity by using this constructor with that fragment's or activity's context added.
    public DictionaryAPI(Context context) {
        thisContext = context;
        progressDialog = new ProgressDialog(thisContext);
    }


    InputStream inputStream = null;
    String result = "";
    private String searchURL = "";
    private final String API_key = "6aa015c0d84b01a6c205f6848a6dea42bcb91d757d4341dde";
    HttpURLConnection mURLConnection = null;



    protected void onPreExecute() {
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface arg0) {
                DictionaryAPI.this.cancel(true);
            }
        });
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String stringResult = "";
        try {
            StringBuilder jsonResult = new StringBuilder();
            URL wordURL = new URL(buildUrlString());
            mURLConnection = (HttpURLConnection) wordURL.openConnection();
            mURLConnection.setRequestMethod("GET");
            InputStream inputStream = mURLConnection.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = buffer.readLine()) != null) {
                jsonResult.append(line);
            }
            stringResult = jsonResult.toString();
        } catch (MalformedURLException e) {
            Log.e("TAG URL", e.getMessage());
        } catch (IOException e) {
            Log.e("TAG HTTP", e.getMessage());
        }
        return stringResult;
    }

    protected void onPostExecute(String result) {
        Log.e("POST EX", result);
    }

    public String buildUrlString() {
        String urlPartOne = "http://api.wordnik.com:80/v4/words.json/reverseDictionary?query=";
        String urlPartTwo = "&includePartOfSpeech=";
        String urlPartThree = "&maxCorpusCount=1&minLength=1&includeTags=false&limit=10&api_key=";
        searchURL = urlPartOne + badWord + urlPartTwo + wordType + urlPartThree + API_key;
        return searchURL;
    }
}
