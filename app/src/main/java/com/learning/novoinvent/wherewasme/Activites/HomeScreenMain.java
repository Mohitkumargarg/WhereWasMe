package com.learning.novoinvent.wherewasme.Activites;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learning.novoinvent.wherewasme.R;

public class HomeScreenMain extends Activity implements View.OnClickListener {

    private LinearLayout llAddNewData;
    private LinearLayout llSaveNewData;
    private TextView tvAddNewData;
    private TextView tvSeeSavedData;

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
        // On Start Add New Data will be selected by default
        llAddNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        tvAddNewData.setTextColor(ContextCompat.getColor(this, R.color.purple));

        llAddNewData.setOnClickListener(this);
        llSaveNewData.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llAddNewData:
                //Toast.makeText(getApplicationContext(),getString(R.string.add_new_data),Toast.LENGTH_SHORT).show();
                llAddNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                tvAddNewData.setTextColor(ContextCompat.getColor(this, R.color.purple));
                llSaveNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.purple));
                tvSeeSavedData.setTextColor(ContextCompat.getColor(this, R.color.white));

                break;
            case R.id.llSaveNewData:
                //Toast.makeText(getApplicationContext(),getString(R.string.see_saved_data),Toast.LENGTH_SHORT).show();
                llSaveNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                tvSeeSavedData.setTextColor(ContextCompat.getColor(this, R.color.purple));
                llAddNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.purple));
                tvAddNewData.setTextColor(ContextCompat.getColor(this, R.color.white));
                break;
        }

    }
}
