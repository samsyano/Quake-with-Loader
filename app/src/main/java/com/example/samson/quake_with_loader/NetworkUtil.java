package com.example.samson.quake_with_loader;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by SAMSON on 7/2/2017.
 */

public class NetworkUtil {
    private static final String LOGCAT = NetworkUtil.class.getSimpleName();

    public String makeServiceCall(String reqUrl){
        String response = null;

        try {
            URL url = new URL(reqUrl);

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode() == 200){
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    response = convertStreamToString(in);
                    Log.e(LOGCAT, "The code response is: " + urlConnection.getResponseCode());
                }else {
                    Log.e(LOGCAT, "The code response is: " + urlConnection.getResponseCode());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return response;
    }
    private String convertStreamToString(InputStream in){
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while((line = reader.readLine()) != null){
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e(LOGCAT, "The value is: " + sb.toString());
        return sb.toString();

    }
}
