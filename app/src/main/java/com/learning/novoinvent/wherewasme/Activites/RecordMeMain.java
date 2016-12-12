package com.learning.novoinvent.wherewasme.Activites;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.learning.novoinvent.wherewasme.helperClass.LocationCustom;
import com.learning.novoinvent.wherewasme.adapter.LocationListViewAdapter;
import com.learning.novoinvent.wherewasme.R;
import com.learning.novoinvent.wherewasme.model.LocationDataModel;
import com.learning.novoinvent.wherewasme.model.LocationListModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecordMeMain extends Activity implements LocationListener {

    private GoogleApiClient googleApiClient;
    private LocationRequest mLocationRequest;
    private GoogleApiClient.ConnectionCallbacks connectionCallbacks;
    private GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener;
    private LocationCustom locationCustom = new LocationCustom();
    private LocationDataModel locationDataModel;
    private LocationListModel locationListModel;
    private ArrayList<LocationDataModel> locationDataEntry;
    private ListView lvLocationData;
    private LocationListViewAdapter locationListViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_me_main);
        init(locationCustom);
    }

    private void init(LocationCustom locationCustom) {
        createGoogleApiCallbacks();
        googleApiClient = locationCustom.setUpClient(this, connectionCallbacks, onConnectionFailedListener);
        mLocationRequest = locationCustom.createLocationRequest();
        lvLocationData = (ListView) findViewById(R.id.lvLocation);
        locationListModel = new LocationListModel();  // For all the location data
        locationDataModel = new LocationDataModel();  // for location time which is updated every time location updates is received
        locationDataEntry = new ArrayList<>();        // used to add location data to the location list model
        locationListViewAdapter = new LocationListViewAdapter(RecordMeMain.this, locationListModel);
    }

    private void createGoogleApiCallbacks() {
        connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {

                if (googleApiClient.isConnected()) {
                    Toast.makeText(getApplicationContext(), "OnConnected", Toast.LENGTH_SHORT).show();
                    locationCustom.setCurrentLocation(getApplicationContext(), locationDataModel);
                    startLocationUpdates();
                    updateUIStart(locationDataModel);
                }
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        };

        onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(getApplicationContext(), "OnConnectionFailed", Toast.LENGTH_SHORT).show();

            }
        };
    }

    private void updateUIStart(LocationDataModel locationDataModel) {
        addLocationEntry(locationDataModel);
        lvLocationData.setAdapter(locationListViewAdapter);

    }

    private void addLocationEntry(LocationDataModel locationDataModel) {
        locationDataEntry.add(locationDataModel);
        locationListModel.setLocation(locationDataEntry);
    }


    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();
        locationCustom.stopLocationUpdates(this);
    }


    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onLocationChanged(Location location) {
        setDataModel(location);
    }

    private void setDataModel(Location location) {
        locationDataModel = new LocationDataModel();
        locationDataModel.setLatitude(String.valueOf(location.getLongitude()));
        locationDataModel.setLongitude(String.valueOf(location.getLongitude()));
        locationDataModel.setTimeUpdate(DateFormat.getTimeInstance().format(new Date()));
        addLocationEntry(locationDataModel);
        updateUI();


    }


    private void updateUI() {
        locationListViewAdapter.notifyDataSetChanged();
    }

    public void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
    }
}
