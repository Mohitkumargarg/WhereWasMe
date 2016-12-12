package com.learning.novoinvent.wherewasme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.learning.novoinvent.wherewasme.Activites.RecordMeMain;
import com.learning.novoinvent.wherewasme.R;
import com.learning.novoinvent.wherewasme.model.LocationListModel;

/**
 * Created by Mohit Kumar on 12/7/2016.
 */

public class LocationListViewAdapter extends BaseAdapter {

    Context context;
    LocationListModel locationData = new LocationListModel();
    private static LayoutInflater inflater = null;

    public LocationListViewAdapter(RecordMeMain callingActivity, LocationListModel locationListModel) {
        context = callingActivity;
        this.locationData = locationListModel;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return locationData.getLocation().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.row_location_time, null);

        holder.latitude = (TextView) rowView.findViewById(R.id.tvLatitudeValue);
        holder.longitude = (TextView) rowView.findViewById(R.id.tvLongitudeValue);
        holder.timeUpdate = (TextView) rowView.findViewById(R.id.tvTimeUpdateValue);

        holder.latitude.setText(locationData.getLocation().get(position).getLatitude());
        holder.longitude.setText(locationData.getLocation().get(position).getLongitude());
        holder.timeUpdate.setText(locationData.getLocation().get(position).getTimeUpdate());
        return rowView;
    }

    public class Holder {
        private TextView latitude;
        private TextView longitude;
        private TextView timeUpdate;
    }
}
