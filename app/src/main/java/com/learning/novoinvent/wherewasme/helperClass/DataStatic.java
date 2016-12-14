package com.learning.novoinvent.wherewasme.helperClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.learning.novoinvent.wherewasme.R;

/**
 * Created by Mohit Kumar on 12/14/2016.
 */

public class DataStatic {

    public static void showGPSDisabledAlertToUser(final Activity activity) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setCancelable(true);
        dialog.setTitle(activity.getResources().getString(R.string.gps_is_disabled_go_to_settings));

        dialog.setPositiveButton(activity.getResources().getString(R.string.settings),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent callGPSSettingIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivity(callGPSSettingIntent);
                        dialog.dismiss();

                    }
                });
        dialog.setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(activity, activity.getResources().getString(R.string.please_enable_gps),Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        });
        dialog.show();
    }

}
