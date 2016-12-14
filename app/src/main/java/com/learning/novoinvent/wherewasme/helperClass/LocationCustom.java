package com.learning.novoinvent.wherewasme.helperClass;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.learning.novoinvent.wherewasme.AppConstants;
import com.learning.novoinvent.wherewasme.model.LocationDataModel;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Mohit Kumar on 12/6/2016.
 */

public class LocationCustom {

    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private GoogleApiClient googleApiClient;

    public GoogleApiClient setUpClient(Context context, GoogleApiClient.ConnectionCallbacks connectionCallbacks,
                                       GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(onConnectionFailedListener)
                .build();
        return this.googleApiClient;
    }


    public LocationRequest createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(AppConstants.LOCATION_INTERNVAL_TIME);
        mLocationRequest.setFastestInterval(AppConstants.LOCATION_FASTEST_INTERNVAL_TIME);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return mLocationRequest;
    }

    public void setCurrentLocation(Context context, LocationDataModel locationDataModel) {

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (mLastLocation != null) {
            locationDataModel.setLatitude(String.valueOf(mLastLocation.getLatitude()));
            locationDataModel.setLongitude(String.valueOf(mLastLocation.getLongitude()));
            locationDataModel.setTimeUpdate(DateFormat.getTimeInstance().format(new Date()));

        }
        else {
            setCurrentLocation(context,locationDataModel);
        }
    }



    public void stopLocationUpdates(Context context) {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, (LocationListener) context);
    }


}

