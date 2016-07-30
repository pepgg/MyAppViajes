package gg.pp.myappviajes.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import gg.pp.myappviajes.R;

public class FirstMapActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private FirstMapFragment mFirstMapFragment;

    public static final String TAG = "En FirstMapActivity: ";

    private Double longitu;
    private Double latitu;
    private String datafot;
    private String nombr;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
  ///  private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        longitu = Double.valueOf(getIntent().getStringExtra("lg"));
        latitu = Double.valueOf(getIntent().getStringExtra("lt"));
        datafot = getIntent().getStringExtra("dt");
        nombr =  getIntent().getStringExtra("nb");

        Log.i(TAG, "longitu 34 " + longitu);
        Log.i(TAG, "latitu 34 " + latitu);
        Log.i(TAG, "datafot 34 " + datafot);
        Log.i(TAG, "latitu 34 " + nombr);

        setContentView(R.layout.activity_first_map);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirstMapFragment = FirstMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map_container, mFirstMapFragment)
                .commit();

        // Registrar escucha onMapReadyCallback
        mFirstMapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    ///    client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //LatLng cali = new LatLng(3.4383, -76.5161);
     ///   Double longitu = 25.78101438678016;
     ///   Double latitu = 71.1697329955483;
        //datafot


        LatLng cali = new LatLng(longitu, latitu);
        googleMap.addMarker(new MarkerOptions()
                .position(cali)
                .title(nombr)
                .snippet(datafot));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(cali)
                .zoom(10)
                .build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
/*
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FirstMap Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://gg.pp.myappviajes.ui/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FirstMap Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://gg.pp.myappviajes.ui/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
*/
}
