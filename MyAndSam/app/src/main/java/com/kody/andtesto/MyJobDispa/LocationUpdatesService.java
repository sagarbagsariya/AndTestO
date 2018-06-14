package com.kody.andtesto.MyJobDispa;


import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.kody.andtesto.JobIntentServices.UploadLocationServer;


/**
 * location update service continues to running and getting location information
 */
public class LocationUpdatesService extends JobService  implements LocationUpdatesComponent.ILocationProvider{

    private static final String TAG = LocationUpdatesService.class.getSimpleName();
    public static final int LOCATION_MESSAGE = 9999;

    private Messenger mActivityMessenger;

    private LocationUpdatesComponent locationUpdatesComponent;

    public LocationUpdatesService() {
    }


    @Override
    public void onCreate() {
        Log.e(TAG, "created...............");

        locationUpdatesComponent = new LocationUpdatesComponent(this);

        locationUpdatesComponent.onCreate(this);
    }

    @Override
    public boolean onStartJob(JobParameters params) {

        //hey request for location updates
        locationUpdatesComponent.onStart();

        return true;
    }


    @Override
    public boolean onStopJob(JobParameters job) {
        Log.i(TAG, "onStopJob....");

        locationUpdatesComponent.onStop();

        return false;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy....");
    }

    @Override
    public void onLocationUpdate(Location location) {
        Log.e(TAG,"..."+location.getLongitude());

        Intent i = new Intent(this, UploadLocationServer.class);  //is any of that needed?  idk.
        //note, putExtra remembers type and I need this to be an integer.  so get an integer first.
        i.putExtra("lat", location.getLatitude());
        i.putExtra("lng", location.getLongitude());
        UploadLocationServer.enqueueWork(this,i);


    }
}