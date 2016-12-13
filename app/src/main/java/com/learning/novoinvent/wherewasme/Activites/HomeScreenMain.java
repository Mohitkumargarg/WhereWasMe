package com.learning.novoinvent.wherewasme.activites;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.learning.novoinvent.wherewasme.R;

public class HomeScreenMain extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback {

    private LinearLayout llAddNewData;
    private LinearLayout llSaveNewData;
    private TextView tvAddNewData;
    private TextView tvSeeSavedData;
    private LinearLayout llAddViewFile;
    private TextView tvAddViewFile;
    private ImageView ivAddViewFile;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_main);
        init();
    }

    private void init() {
        llAddNewData = (LinearLayout) findViewById(R.id.llAddNewData);
        llSaveNewData = (LinearLayout) findViewById(R.id.llSaveNewData);
        tvAddNewData = (TextView) findViewById(R.id.tvAddNewData);
        tvSeeSavedData = (TextView) findViewById(R.id.tvSeeSavedData);
        llAddViewFile = (LinearLayout) findViewById(R.id.llAddViewFile);
        tvAddViewFile = (TextView) findViewById(R.id.tvAddViewFile);
        ivAddViewFile = (ImageView) findViewById(R.id.ivAddViewFile);


        // On Start Add New Data will be selected by default
        llAddNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        tvAddNewData.setTextColor(ContextCompat.getColor(this, R.color.dark_purple));
        tvAddViewFile.setText(getResources().getString(R.string.add_new_file));
        ivAddViewFile.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_file));


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
                //Toast.makeText(getApplicationContext(),getString(R.string.add_new_data),Toast.LENGTH_SHORT).show();
                llAddNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                tvAddNewData.setTextColor(ContextCompat.getColor(this, R.color.dark_purple));
                llSaveNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_purple));
                tvSeeSavedData.setTextColor(ContextCompat.getColor(this, R.color.white));
                tvAddViewFile.setText(getResources().getString(R.string.add_new_file));
                ivAddViewFile.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_file));


                break;
            case R.id.llSaveNewData:
                //Toast.makeText(getApplicationContext(),getString(R.string.see_saved_data),Toast.LENGTH_SHORT).show();
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
                if(fileName.length() > 0) {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        GoogleMap map = googleMap;
        LatLng Australia = new LatLng(28.6119002,77.3628014);

        map.addMarker(new MarkerOptions().position(Australia).title("Marker at Me"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Australia));
        map.animateCamera(CameraUpdateFactory.zoomTo(16));

    }
}
