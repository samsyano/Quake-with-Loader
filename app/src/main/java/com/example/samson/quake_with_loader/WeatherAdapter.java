package com.example.samson.quake_with_loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by SAMSON on 7/2/2017.
 */

public class WeatherAdapter extends ArrayAdapter<Weather> {
    public WeatherAdapter(@NonNull Context context, @NonNull List<Weather> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_view, parent, false);
        }

        Weather weather = getItem(position);

        TextView tempView = (TextView) view.findViewById(R.id.magnitude);
        tempView.setText(weather.getMagnitude());

        TextView pressView = (TextView) view.findViewById(R.id.location);
        pressView.setText(weather.getLocation());

        TextView descView = (TextView) view.findViewById(R.id.date);

        Long rDate = Long.parseLong(weather.getDate());

        Date date = new Date(rDate);
        String formateDate = formatDate(date);
        descView.setText(formateDate);

        String formatTime = formatTime(date);
        TextView time = (TextView) view.findViewById(R.id.ttime);
        time.setText(formatTime);



        return view;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);

    }
}
