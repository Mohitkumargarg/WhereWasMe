package com.learning.novoinvent.wherewasme.model;

/**
 * Created by Mohit Kumar on 12/6/2016.
 */

public class LocationDataModel {

    private String Latitude;
    private String Longitude;
    private String TimeUpdate;

    public String getTimeUpdate() {
        return TimeUpdate;
    }

    public void setTimeUpdate(String timeUpdate) {
        TimeUpdate = timeUpdate;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }
}
