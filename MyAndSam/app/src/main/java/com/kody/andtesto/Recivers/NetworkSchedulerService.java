package com.kody.andtesto.Recivers;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

public class NetworkSchedulerService extends  JobService implements
        ConnectivityReceiver.ConnectivityReceiverListener
{

    private static final String TAG = NetworkSchedulerService.class.getSimpleName();
    private ConnectivityReceiver mConnectivityReceiver;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "Service created");
        mConnectivityReceiver = new ConnectivityReceiver(this);
    }

    /**
     * When the app's NetworkConnectionActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return START_NOT_STICKY;
    }



    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "onStartJob" );
        registerReceiver(mConnectivityReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG, "onStopJob");
        unregisterReceiver(mConnectivityReceiver);
        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        Log.e(TAG, "onNetworkConnectionChanged");
        String message = isConnected ? "Good! Connected to Internet" : "Sorry! Not connected to internet";
        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "......."+message);

    }
}