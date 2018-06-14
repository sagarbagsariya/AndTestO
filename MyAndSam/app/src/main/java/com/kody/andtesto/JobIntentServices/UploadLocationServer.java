package com.kody.andtesto.JobIntentServices;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;

import com.kody.andtesto.Database.KemsDatabaseHelper;
import com.kody.andtesto.LocationData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


public class UploadLocationServer extends JobIntentService  {

    KemsDatabaseHelper dbhelper;



    static final int JOB_ID = 1000;
    final String TAG = "UploadLocationServer";

    // Random number generator
    private final Random mGenerator = new Random();


    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, UploadLocationServer.class, JOB_ID, work);
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        dbhelper=KemsDatabaseHelper.getInstance(this);

        LocationData data = new LocationData();
        data.setLat(intent.getExtras().getDouble("lat"));
        data.setLng(intent.getExtras().getDouble("lng"));
        data.setTime(getCurrentTime());
        data.setDate(getCurrentDate());



        updateServer(data);


    }

    @Override
    public boolean onStopCurrentWork() {
        return super.onStopCurrentWork();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "All work complete");
    }



    final Handler mHandler = new Handler();

    // Helper for showing tests
    void toast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(UploadLocationServer.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateServer(LocationData locationData) {




        dbhelper.addLocation(locationData);




    }



    public static String getCurrentTime() {
        //date output format
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }// end of getCurrentTime()

    public static String getCurrentDate() {
        //date output format
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }// end of getCurrentDate()\




}
