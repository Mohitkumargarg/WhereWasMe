package com.learning.novoinvent.wherewasme.activites;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.learning.novoinvent.wherewasme.AppConstants;
import com.learning.novoinvent.wherewasme.R;
import com.learning.novoinvent.wherewasme.helperClass.DataStatic;
import com.learning.novoinvent.wherewasme.helperClass.LocationCustom;
import com.learning.novoinvent.wherewasme.model.LocationDataModel;

public class HomeScreenMain extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback {

    private LinearLayout llAddNewData;
    private LinearLayout llSaveNewData;
    private TextView tvAddNewData;
    private TextView tvSeeSavedData;
    private LinearLayout llAddViewFile;
    private TextView tvAddViewFile;
    private ImageView ivAddViewFile;
    private String fileName;
    private GoogleApiClient googleApiClient;
    private LocationCustom locationCustom = new LocationCustom();
    private GoogleApiClient.ConnectionCallbacks connectionCallbacks;
    private GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener;
    private LocationDataModel locationDataModel;
    private GoogleMap map;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_main);
        init(locationCustom);
    }


    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    private void init(LocationCustom locationCustom) {
        llAddNewData = (LinearLayout) findViewById(R.id.llAddNewData);
        llSaveNewData = (LinearLayout) findViewById(R.id.llSaveNewData);
        tvAddNewData = (TextView) findViewById(R.id.tvAddNewData);
        tvSeeSavedData = (TextView) findViewById(R.id.tvSeeSavedData);
        llAddViewFile = (LinearLayout) findViewById(R.id.llAddViewFile);
        tvAddViewFile = (TextView) findViewById(R.id.tvAddViewFile);
        ivAddViewFile = (ImageView) findViewById(R.id.ivAddViewFile);
        createGoogleApiCallbacks();
        googleApiClient = locationCustom.setUpClient(this, connectionCallbacks, onConnectionFailedListener);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        // On Start Add New Data will be selected by default
        llAddNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        tvAddNewData.setTextColor(ContextCompat.getColor(this, R.color.dark_purple));
        tvAddViewFile.setText(getResources().getString(R.string.add_new_file));
        ivAddViewFile.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_file));
        locationDataModel = new LocationDataModel();

        setMapFragment();

        llAddNewData.setOnClickListener(this);
        llSaveNewData.setOnClickListener(this);
        llAddViewFile.setOnClickListener(this);
    }

    private void setMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llAddNewData:
                llAddNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                tvAddNewData.setTextColor(ContextCompat.getColor(this, R.color.dark_purple));
                llSaveNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_purple));
                tvSeeSavedData.setTextColor(ContextCompat.getColor(this, R.color.white));
                tvAddViewFile.setText(getResources().getString(R.string.add_new_file));
                ivAddViewFile.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_file));


                break;
            case R.id.llSaveNewData:

                llSaveNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                tvSeeSavedData.setTextColor(ContextCompat.getColor(this, R.color.dark_purple));
                llAddNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_purple));
                tvAddNewData.setTextColor(ContextCompat.getColor(this, R.color.white));
                tvAddViewFile.setText(getResources().getString(R.string.show_stored_files));
                ivAddViewFile.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.documents));
                break;

            case R.id.llAddViewFile:
                getFileName();
                break;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                AppConstants.MY_PERMISSIONS_REQUEST_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case AppConstants.MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        locationCustom.setCurrentLocation(getApplicationContext(), locationDataModel);
                        setCurrentLocationMarker(locationDataModel);
                    } else {
                        DataStatic.showGPSDisabledAlertToUser(HomeScreenMain.this);
                    }

                } else {
                    finish();
                }

            }
        }

    }

    private void setCurrentLocationMarker(LocationDataModel locationDataModel) {
        LatLng myLocation = new LatLng(Double.parseDouble(locationDataModel.getLatitude()), Double.parseDouble(locationDataModel.getLongitude()));
        map.addMarker(new MarkerOptions().position(myLocation).title(getResources().getString(R.string.marker_at_me)));
        map.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        map.animateCamera(CameraUpdateFactory.zoomTo(16));
    }

    private void createGoogleApiCallbacks() {
        connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {

                if (googleApiClient.isConnected()) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions();
                    } else {
                        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            locationCustom.setCurrentLocation(getApplicationContext(), locationDataModel);
                            setCurrentLocationMarker(locationDataModel);
                        } else {
                            DataStatic.showGPSDisabledAlertToUser(HomeScreenMain.this);
                        }

                    }
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

    private void getFileName() {
        LayoutInflater li = LayoutInflater.from(this);
        final View promptsView = li.inflate(R.layout.alert_enter_file_name, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setView(promptsView);

        final EditText etFileName = (EditText) promptsView
                .findViewById(R.id.etFileName);
        TextView tvSubmit = (TextView) promptsView
                .findViewById(R.id.tvSubmit);
        TextView tvCancel = (TextView) promptsView
                .findViewById(R.id.tvCancel);

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileName = etFileName.getText().toString();
                alertDialog.cancel();
                if (fileName.length() > 0) {
                    Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
    }

}
