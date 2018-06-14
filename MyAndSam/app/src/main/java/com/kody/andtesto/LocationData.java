package com.kody.andtesto;

import com.google.gson.annotations.Expose;

/**
 * Created on 10/16/17.
 */

public class LocationData {

    @Expose
    String name;
    @Expose
    double lat;
    @Expose
    double lng;

    String time;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String response;

    public String getName() {
        return name;
    }

    public LocationData setName(String name) {
        this.name = name;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public LocationData setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public LocationData setLng(double lng) {
        this.lng = lng;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public LocationData setResponse(String response) {
        this.response = response;
        return this;
    }
}
