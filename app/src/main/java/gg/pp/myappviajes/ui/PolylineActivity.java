package gg.pp.myappviajes.ui;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;

//import android.app.LoaderManager;
//import android.content.Loader;

public class PolylineActivity extends AppCompatActivity
        implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Cursor> {

    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    public static final String TAG = "En PolylineActivity: ";

    private Double longitu;
    private Double latitu;
    private String datafot;
    private String nombr;
    private String idviaj;
    SimpleCursorAdapter latLongAdapter;
    public static final int LOADER_MAP = 1;
    public Cursor c;
    private GoogleMap map;
    double lat = 0;
    double lng = 0;
    float zoom = 10;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polyline);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

     //   getLoaderManager().initLoader(LOADER_MAP, null,  (android.app.LoaderManager.LoaderCallbacks<Cursor>) this);
        getSupportLoaderManager().initLoader(LOADER_MAP, null, this);
      //  getLoaderManager().initLoader(LOADER_MAP, null, this);
        mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        idviaj = getIntent().getStringExtra("idv");
        Log.i(TAG, "Polyline OnCrete 61 id VIAJE " + idviaj); // llega biennnnnn
        //iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
        latLongAdapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_spinner_item,
                null,
                new String[]{ViajesContract.EventosEntry.E_LAT},
                new int[]{android.R.id.text1}, 2);
        Log.i(TAG, "Polyline OnCrete 71 id VIAJE " + idviaj); // llega


        //  android.content.Loader<Object> objectLoader = getLoaderManager().initLoader(LOADER_MAP, null, this);
        //    getLoaderManager().initLoader(LOADER_MAP,  cl, Cursor arg);


        // Registrar escucha onMapReadyCallback
        mMapFragment.getMapAsync(this);
        Log.i(TAG, "Polyline OnCrete 73 id VIAJE " + idviaj); // llega
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //   client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onMapReady(GoogleMap retMap) {
        map = retMap;
        Log.i(TAG, "Polyline onMapReady 108 id VIAJE " + idviaj); // llega





        /*
        ************************************************
        mMap = googleMap;
/////////////aquí el loader que busca los eventos con lat y long y crea un cursor
        // Ejemplo: Delimitar a Sudamérica con un rectángulo
        PolylineOptions sudamericaRect = new PolylineOptions()
                .add(new LatLng(12.897489, -82.441406)) // P1
                .add(new LatLng(12.897489, -32.167969)) // P2
                .add(new LatLng(-55.37911, -32.167969)) // P3
                .add(new LatLng(-55.37911, -82.441406))  // P4
                .add(new LatLng(12.897489, -82.441406)) // P1
                .color(Color.parseColor("#f44336"));    // Rojo 500
        // Instancia para posteriores usos
        Polyline polyline = mMap.addPolyline(sudamericaRect);
        // Mover cámara
        // esto lllllllllllllllllo tengo que arreglarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(-20.96144, -61.347656)));
        ***********************************************
*/
        PolylineOptions markerOptions = new PolylineOptions().color(Color.parseColor("#f44336"));    // Rojo 500
        // Setting latitude and longitude for the marker
        LatLng location = new LatLng(lat, lng);
        markerOptions.add(location);
        // Adding polylines on the Google Map
        retMap.addPolyline(markerOptions);

      //  Polyline polyline = mMap.addPolyline(markerOptions);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_MAP:
                ArrayList<LatLng> markerPoints = new ArrayList<LatLng>();
                Log.i(TAG, "Polyline onCreateLoader 159: ");  //llega
                String[] PROJECTION = new String[]{ViajesContract.EventosEntry.E_LAT,
                        ViajesContract.EventosEntry.E_LON};

                CursorLoader cl = new CursorLoader(
                        this,                              // Parent activity context
                        ViajesContract.EventosEntry.URI_CONTENIDO,    // Table to query
                        PROJECTION,      // Projection to return: latitud y longitud
                        // ViajesContract.EventosEntry.E_LAT + " LIKE '%'",   // selection clause
                        //ViajesContract.EventosEntry.E_LAT + " != '' and " + ViajesContract.EventosEntry.E_IDV + "=" + idviaj,   // selection clause
                        ViajesContract.EventosEntry.E_LAT + " != '' ",
                        null,                                       // No selection arguments
                        null);                                      // Default sort order

                //el cursorloader se llama cl

                creaArray();
                c = cl.loadInBackground();
                c.moveToFirst();


                return cl;
            default:
                return null;


        }
    }


    //  Cursor c;


    private void creaArray() {
        // ArrayList<LatLng> mArrayList = new ArrayList<LatLng>();
        //      for(CursorLoader.cl.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
        // The Cursor is now set to the right position
        //        mArrayList.add(mCursor.getWhateverTypeYouWant(WHATEVER_COLUMN_INDEX_YOU_WANT));
        //  }
/*
        ArrayList<WhateverTypeYouWant> mArrayList = new ArrayList<WhateverTypeYouWant>();
        for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            // The Cursor is now set to the right position
            mArrayList.add(mCursor.getWhateverTypeYouWant(WHATEVER_COLUMN_INDEX_YOU_WANT));
        }
        */
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cl, Cursor args) {
        //Loader<Cursor> arg0, Cursor arg1

        /////latLongAdapter.swapCursor(data);
        /*
        ArrayList<LatLng> mArrayList = new ArrayList<LatLng>();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            // The Cursor is now set to the right position
          ///  mArrayList.add(Integer.valueOf(c.getString(0)));

        }


        ArrayList<LatLng> points = new ArrayList<LatLng>();
        PolylineOptions lineOptions;
        */
        /////////////////////////////////
        // http://wptrafficanalyzer.in/blog/storing-and-retrieving-locations-in-sqlite-from-google-maps-android-api-v2/
        int locationCount = 0;

        ArrayList<LatLng> points = new ArrayList<LatLng>();

        locationCount = args.getCount();
        Log.i(TAG, "Polylkine onLoadFinished 231 locationCount: " + locationCount); // llega perp locatiomcount = 0
        // Move the current record pointer to the first row of the table
        args.moveToFirst();

        for (int i = 0; i < locationCount; i++) {

            // Get the latitude
            lat = args.getDouble(args.getColumnIndex(ViajesContract.EventosEntry.E_LAT));
            Log.i(TAG, "Polyline onLoadFinished 206 lat " + lat); //
            // Get the longitude
            lng = args.getDouble(args.getColumnIndex(ViajesContract.EventosEntry.E_LON));

            // Get the zoom level
            //zoom = args.getFloat(args.getColumnIndex(LocationsDB.FIELD_ZOOM));

            // Creating an instance of LatLng to plot the location in Google Maps
            LatLng location = new LatLng(lat, lng);


            points.add(location);



            Log.i(TAG, "Polyline onLoadFinished 206 locatiom " + location); //  llega bien
            // Drawing the marker in the Google Maps
            ///////////////////////////////////////////////
            //PolylineOptions markerOptions = new PolylineOptions()
             //       .color(Color.parseColor("#f44336"));    // Rojo 500
            // Setting latitude and longitude for the marker
            Log.i(TAG, "Polyline onLoadFinished 248 locatiom " + location); //
           // markerOptions.add(location);
            ///Polyline polyline = map.addPolyline(markerOptions.add(location));


            // Adding marker on the Google Map
        ///    map.addPolyline(markerOptions);
            Log.i(TAG, "Polyline onLoadFinished 252 locatiom " + location); //


   /////////////////////////////////////////////////
         ///   drawMarker(location); ////<<<<<<<<<<<<<<<<<<esto no funciona

            // Traverse the pointer to the next row
            args.moveToNext();
        }
        PolylineOptions polylineOptions = new PolylineOptions();

// Create polyline options with existing LatLng ArrayList
        polylineOptions.addAll(points);
        polylineOptions
                .width(5)
                .color(Color.BLUE);

// Adding multiple points in map using polyline and arraylist
        map.addPolyline(polylineOptions);





        //////////////////
        /*
        PolylineOptions markerOptions = new PolylineOptions()
                .color(Color.BLUE);
      //  Polyline polyline =
                map.addPolyline(markerOptions).addAll(points);
                map.addPolyline(markerOptions).addAll(polylines).width(2.0f).color(Color.RED));
                */
/*
        PolylineOptions sudamericaRect = new PolylineOptions()
                .add(new LatLng(55.671665488,12.4335962)) // P1
                .add(new LatLng(58.4274476790541,8.73073204198695)) // P2
                .add(new LatLng(71.1697329955483,25.7810143867802)) // P3
                .color(Color.parseColor("#f44336"));    // Rojo 500
        // Instancia para posteriores usos
        Polyline polyline = map.addPolyline(sudamericaRect);
*/
     ///   if (locationCount > 0) {
            // Moving CameraPosition to last clicked position
       ///     map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));

            // Setting the zoom level in the map on last position  is clicked
         ///   map.animateCamera(CameraUpdateFactory.zoomTo(zoom));
     ///   }

        ///   try {
        ///      int columnIndexA = data.getColumnIndex(ViajesContract.EventosEntry.E_LAT);
        ///     int columnIndexO = data.getColumnIndex(ViajesContract.EventosEntry.E_LON);
            /*
             while (data.moveToNext()) {
            //String latLongStr = data.getString(columnIndex);
            StringTokenizer tokens = new StringTokenizer(latLongStr, ",");
            String latString = tokens.nextToken();
            String longString = tokens.nextToken();
            double lat = Double.parseDouble(latString);
            double lng = Double.parseDouble(longString);
            points.add(new LatLng(lat, lng));
        }

        /*
            while (data.moveToNext()) {
                String latLongStr = data.getString(columnIndexA);
                //String latStr = data.getString(columnIndexA);
                StringTokenizer tokens = new StringTokenizer(latLongStr, ",");
                String latString = tokens.nextToken();
                String longString = tokens.nextToken();
                double lat = Double.parseDouble(latString);
                double lng = Double.parseDouble(longString);
                points.add(new LatLng(lat, lng));
            }
            }catch(Exception e){
            Toast.makeText(getApplicationContext(), "NO pone lat long!!", Toast.LENGTH_SHORT).show();
        }
        */
    }

    private void drawMarker(LatLng point) {
        // Creating an instance of MarkerOptions
        //MarkerOptions markerOptions = new MarkerOptions();
        // Setting latitude and longitude for the marker
        // markerOptions.position(point);
        // Adding marker on the Google Map
        // googleMap.addMarker(markerOptions);


        // Creating an instance of MarkerOptions
        // MarkerOptions markerOptions = new MarkerOptions();
        PolylineOptions markerOptions = new PolylineOptions()
                .color(Color.parseColor("#f44336"));    // Rojo 500
        // Setting latitude and longitude for the marker
        markerOptions.add(point);
        // Adding marker on the Google Map
        map.addPolyline(markerOptions);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {


        latLongAdapter.swapCursor(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        ///   client.connect();
        ///  Action viewAction = Action.newAction(
        ///       Action.TYPE_VIEW, // TODO: choose an action type.
        ///    "Polyline Page", // TODO: Define a title for the content shown.
        // TODO: If you have web page content that matches this app activity's content,
        // make sure this auto-generated web page URL is correct.
        // Otherwise, set the URL to null.
        //   Uri.parse("http://host/path"),
        // TODO: Make sure this auto-generated app URL is correct.
        //   Uri.parse("android-app://gg.pp.myappviajes.ui/http/host/path")
        /// );
        ///    AppIndex.AppIndexApi.start(client, viewAction);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction2 = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Polyline Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://gg.pp.myappviajes.ui/http/host/path")
        );
      //  AppIndex.AppIndexApi.start(client2, viewAction2);
    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction2 = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Polyline Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://gg.pp.myappviajes.ui/http/host/path")
        );
     //   AppIndex.AppIndexApi.end(client2, viewAction2);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Polyline Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://gg.pp.myappviajes.ui/http/host/path")
        );
        //   AppIndex.AppIndexApi.end(client, viewAction);
        // client.disconnect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.disconnect();
    }
}
