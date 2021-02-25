package com.example.testvideoview;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask extends AsyncTask<String, Void, String> {

    String myurl;
    Callback callback;

    public  NetworkTask(String myurl, Callback callback){
        this.myurl= myurl;
        this.callback= callback;
    }


    @Override
    protected String doInBackground(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        try{
            URL url= new URL(strings[0]);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.connect();

            InputStreamReader inputStreamReader=new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String inputLine;
            //Check if the line we are reading is not null
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            inputStreamReader.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        
        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callback.returnString(s);
    }
    
    public interface Callback{
        void returnString(String s);
    }
    
}
