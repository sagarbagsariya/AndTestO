package com.kody.andtesto;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.LocationResult;

import java.util.List;



/**
 * Handles incoming location updates and displays a notification with the location data.
 */
public class LocationUpdatesIntentService extends IntentService {

    static final String ACTION_PROCESS_UPDATES =
            "com.appsgit.locationupdate.retrofit.action" + ".PROCESS_UPDATES";

    private static final String TAG = LocationUpdatesIntentService.class.getSimpleName();


    public LocationUpdatesIntentService() {
        // Name the worker thread.
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {
                    List<Location> locations = result.getLocations();

                    Location firstLocation = locations.get(0);

                    LocationData data = new LocationData();
                    data.setLat(firstLocation.getLatitude());
                    data.setLng(firstLocation.getLongitude());
                    updateServer(data);

                }
            }
        }
    }

    public void updateServer(LocationData locationData) {

        Log.e("locationData ","...from service.."+locationData.getLat()+".....long..."+locationData.getLng());

    }
}

