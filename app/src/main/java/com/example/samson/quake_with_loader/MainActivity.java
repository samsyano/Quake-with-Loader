package com.example.samson.quake_with_loader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Weather>>{

    WeatherAdapter adapter;
//    ArrayList<Weather> weatherlist;
//    List<Weather> weather_data;

    TextView emptyTview;

    ListView view;

    //    ArrayList<HashMap<String, String>> weatherlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportLoaderManager().initLoader(1, null, this).forceLoad();


            getSupportLoaderManager().initLoader(1, null, this);
            Log.i("MAINACTIVITY: ", "Loading the init...");

    }
    @Override
    public android.support.v4.content.Loader<List<Weather>> onCreateLoader(int id, Bundle args) {
        Log.i("MAINACTIVITY onCreateLoader: ", "Loading the onCreateLoader...");
         progressDialog = findViewById(R.id.progressBar);

        emptyTview = (TextView)findViewById(R.id.emptyView);
        ConnectivityManager connMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMan.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            progressDialog.setVisibility(View.VISIBLE);

            /*\
            * Using a sharedPreference*/
            String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String minMagnitude = sharedPrefs.getString(getString(R.string.settings_min_magnitude_key),
                    getString(R.string.settings_min_magnitude_default));

            //ORDER BY
            String orderBy = sharedPrefs.getString(
                    getString(R.string.settings_order_by_key),
                    getString(R.string.settings_order_by_default));


            Uri baseUri = Uri.parse(USGS_REQUEST_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter("format", "geojson");
            uriBuilder.appendQueryParameter("limit", "10");
            uriBuilder.appendQueryParameter("minmag", minMagnitude);
            uriBuilder.appendQueryParameter("orderby", orderBy);

            return new WeatherAsyncLoader(MainActivity.this, uriBuilder.toString());

        }else {
            progressDialog.setVisibility(View.GONE);
            emptyTview.setText("No internet connection");
        }

           return null;
    }
    View progressDialog;

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<Weather>> loader, List<Weather> data) {

        progressDialog.setVisibility(View.GONE);

        Log.i("MAINACTIVITY onLoadFinished: ", "Loading the onLoadFinished...");

        view = (ListView)findViewById(R.id.listItem_View);
        adapter = new WeatherAdapter(MainActivity.this, data);


        view.setEmptyView(emptyTview);

        if(data != null){
            view.setAdapter(adapter);
            Log.i("LOADING THE LISTVIEW: ", "The LIstView is being populated...");

            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Weather earthquake = adapter.getItem(position);
                    Uri earthquakeUri = Uri.parse(earthquake.getmUrl());
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,earthquakeUri);
                    startActivity(webIntent);
                }
            });
        }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Weather>> loader) {
        Log.i("MAINACTIVITY onLoadReset: ", "Loading the onLoadReset...");

        view.setAdapter(new WeatherAdapter(MainActivity.this, new ArrayList<Weather>()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
    return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
