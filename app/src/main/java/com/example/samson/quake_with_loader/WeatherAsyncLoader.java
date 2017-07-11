package com.example.samson.quake_with_loader;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMSON on 7/2/2017.
 */

public class WeatherAsyncLoader extends android.support.v4.content.AsyncTaskLoader<List<Weather>> {

    String url;
    public WeatherAsyncLoader(Context context, String url) {
        super(context);
        this.url = url;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Weather> loadInBackground() {

        NetworkUtil util = new NetworkUtil();
//            String url = "http://api.androidhive.info/contacts/";
//

//         url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=2";
        String jsonStr = util.makeServiceCall(url);

        List<Weather> weathers = new ArrayList<>();

        if(jsonStr != null){
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);

                JSONArray featuresArray = jsonObject.getJSONArray("features");
                for(int i = 0; i< featuresArray.length(); i++){
                    JSONObject featureObject = featuresArray.getJSONObject(i);

                    String id = featureObject.getString("id");
                    JSONObject propertiesObject = featureObject.getJSONObject("properties");
                    String place = propertiesObject.getString("place");
                    Double mag = propertiesObject.getDouble("mag");
                    String magnitude = String.valueOf(mag);

                    ///
                    Long dateObject = propertiesObject.getLong("time");

                    String urlLink = propertiesObject.getString("url");

                    weathers.add(new Weather(magnitude, place, String.valueOf(dateObject), urlLink));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.e("WEATHERASYN: ", "Response from url: " + jsonStr);

        return weathers;
    }

}
