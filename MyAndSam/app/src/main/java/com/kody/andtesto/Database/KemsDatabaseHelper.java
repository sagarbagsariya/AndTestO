package com.kody.andtesto.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kody.andtesto.LocationData;
import com.kody.andtesto.Model.Gpsdata;
import com.kody.andtesto.Model.Phonedata;

import java.util.ArrayList;
import java.util.List;


public class KemsDatabaseHelper extends SQLiteOpenHelper {
    public static KemsDatabaseHelper kemsDb;

    private static final String TAG = "KemsDatabaseHelper";


    // General Constunt

    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY";
    private static final String TYPE_TEXT = " TEXT";
    private static final String SELECT_FROM = "SELECT  * FROM ";







    // Database Info
    private static final String DATABASE_NAME = "kemsmaindbdemo";
    private static final int DATABASE_VERSION = 1;


    // Table Names
    private static final String STATUS_GPS = "gps";
    private static final String STATUS_PHONE= "phone";
    private static final String STATUS_LOCATION= "location";



    // STATUS_GPS Table Columns
    private static final String KEY_GPS_ID= "id";
    private static final String KEY_GPS_STATUS = "status";
    private static final String KEY_GPS_TIME = "time";
    private static final String KEY_GPS_DATE = "date";


    // STATUS_PHONE Table Columns
    private static final String KEY_PHONE_ID= "id";
    private static final String KEY_PHONE_STATUS = "status";
    private static final String KEY_PHONE_TIME = "time";
    private static final String KEY_PHONE_DATE = "date";


    // STATUS_LOCATIONTable Columns
    private static final String KEY_LOC_ID= "id";
    private static final String KEY_LOC_LAT= "lat";
    private static final String KEY_LOC_LONG= "long";
    private static final String KEY_LOC_TIME= "time";
    private static final String KEY_LOC_DATE= "date";














    public static synchronized KemsDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (kemsDb == null) {
            kemsDb = new KemsDatabaseHelper(context.getApplicationContext());
        }
        return kemsDb;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    public KemsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }





    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_GPS_TABLE = CREATE_TABLE + STATUS_GPS +
                "(" +
                KEY_GPS_ID+ INTEGER_PRIMARY_KEY +"," +
                KEY_GPS_STATUS + TYPE_TEXT +"," +
                KEY_GPS_TIME + TYPE_TEXT +"," +
                KEY_GPS_DATE + TYPE_TEXT +
                ")";



        String CREATE_PHONE_TABLE = CREATE_TABLE + STATUS_PHONE+
                "(" +
                KEY_PHONE_ID+ INTEGER_PRIMARY_KEY +"," +
                KEY_PHONE_STATUS + TYPE_TEXT+"," +
                KEY_PHONE_TIME + TYPE_TEXT+"," +
                KEY_PHONE_DATE + TYPE_TEXT+
                ")";

        String CREATE_LOCATION_TABLE= CREATE_TABLE + STATUS_LOCATION+
                "(" +
                KEY_LOC_ID+ INTEGER_PRIMARY_KEY +"," +
                KEY_LOC_LAT + TYPE_TEXT+"," +
                KEY_LOC_LONG + TYPE_TEXT+"," +
                KEY_LOC_TIME+ TYPE_TEXT+"," +
                KEY_LOC_DATE+ TYPE_TEXT+

                ")";


        db.execSQL(CREATE_GPS_TABLE);
        db.execSQL(CREATE_PHONE_TABLE);
        db.execSQL(CREATE_LOCATION_TABLE);



    }


    // Insert a gps status into the database
    public void addGpsStatus(Gpsdata gpsdata) {
        // Create and/or open the database for writing

        SQLiteDatabase db = this.getWritableDatabase();
        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_GPS_STATUS, gpsdata.getStatus());
            values.put(KEY_GPS_TIME, gpsdata.getTime());
            values.put(KEY_GPS_DATE, gpsdata.getDate());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(STATUS_GPS, null, values);
            db.setTransactionSuccessful();
            Log.e(TAG, "Gps Insert done at ......"+gpsdata.getTime());

        } catch (Exception e) {
            Log.e(TAG, "Error while trying to add gpsstatus to database");
        } finally {
            db.endTransaction();
            db.close();
        }
    } // End Of Add Gps Status
    public List<Gpsdata> getAllGpsStatus() {

        List<Gpsdata> gpsstatuslist = new ArrayList<>();

        String selectQueryGPS = SELECT_FROM + STATUS_GPS;

        Log.e(TAG, selectQueryGPS);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

             c = db.rawQuery(selectQueryGPS, null);
            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Gpsdata item = new Gpsdata();
                    item.setStatus(c.getString((c.getColumnIndex(KEY_GPS_STATUS))));
                    item.setTime((c.getString(c.getColumnIndex(KEY_GPS_TIME))));
                    item.setDate(c.getString(c.getColumnIndex(KEY_GPS_DATE)));

                    gpsstatuslist.add(item);
                } while (c.moveToNext());
            }


        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get  gpsstatus to database");
        } finally {
            if (c != null && ! c.isClosed()) {
                c.close();
            }
            db.endTransaction();
            db.close();

        }
        return gpsstatuslist;
    }



    // Insert a phone status into the database
    public void addPhoneStatus(Phonedata gpsdata) {
        // Create and/or open the database for writing

        SQLiteDatabase db = this.getWritableDatabase();
        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_PHONE_STATUS, gpsdata.getStatus());
            values.put(KEY_PHONE_TIME, gpsdata.getTime());
            values.put(KEY_PHONE_DATE, gpsdata.getDate());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(STATUS_PHONE, null, values);
            db.setTransactionSuccessful();
            Log.e(TAG, "Phone Status Insert done at ......"+gpsdata.getTime());

        } catch (Exception e) {
            Log.e(TAG, "Error while trying to add gpsstatus to database");
        } finally {
            db.endTransaction();
            db.close();
        }
    } // End Of Add Phone Status

    public List<Phonedata> getAllPhoneStatus() {

        List<Phonedata> phonestatuslist = new ArrayList<>();

        String selectQueryPhone = SELECT_FROM + STATUS_PHONE;

        Log.e(TAG, selectQueryPhone);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

            c = db.rawQuery(selectQueryPhone, null);
            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Phonedata item = new Phonedata();
                    item.setStatus(c.getString((c.getColumnIndex(KEY_PHONE_STATUS))));
                    item.setTime((c.getString(c.getColumnIndex(KEY_PHONE_TIME))));
                    item.setDate(c.getString(c.getColumnIndex(KEY_PHONE_DATE)));

                    phonestatuslist.add(item);
                } while (c.moveToNext());
            }


        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get  gpsstatus to database");
        } finally {
            if (c != null && ! c.isClosed()) {
                c.close();
            }
            db.endTransaction();
            db.close();

        }
        return phonestatuslist;
    }






    // Insert a location into the database
    public void addLocation(LocationData item) {
        // Create and/or open the database for writing

        SQLiteDatabase db = this.getWritableDatabase();
        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_LOC_LAT, item.getLat());
            values.put(KEY_LOC_LONG, item.getLng());
            values.put(KEY_LOC_TIME, item.getTime());
            values.put(KEY_LOC_DATE, item.getDate());


            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(STATUS_LOCATION, null, values);
            db.setTransactionSuccessful();
            Log.e(TAG, "Location Insert in database at ......"+item.getLat());

        } catch (Exception e) {
            Log.e(TAG, "Error while trying to add Location to database");
        } finally {
            db.endTransaction();
            db.close();
        }
    } // End Of Add Location

    public List<LocationData> getAllLocation() {

        List<LocationData> locationlist = new ArrayList<>();

        String selectQueryLocation= SELECT_FROM + STATUS_LOCATION;

        Log.e(TAG, selectQueryLocation);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

            c = db.rawQuery(selectQueryLocation, null);
            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    LocationData item = new LocationData();
                    item.setLat((c.getDouble(c.getColumnIndex(KEY_LOC_LAT))));
                    item.setLng((c.getDouble(c.getColumnIndex(KEY_LOC_LONG))));
                    item.setTime((c.getString(c.getColumnIndex(KEY_LOC_TIME))));
                    item.setDate((c.getString(c.getColumnIndex(KEY_LOC_DATE))));

                    locationlist.add(item);
                } while (c.moveToNext());
            }


        } catch (Exception e) {
            Log.e(TAG, "Error while trying to get  locations to database");
        } finally {
            if (c != null && ! c.isClosed()) {
                c.close();
            }
            db.endTransaction();
            db.close();

        }
        return locationlist;
    }














    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG,"onUpgrade");

    }
}
