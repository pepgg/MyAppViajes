package gg.pp.myappviajes.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import gg.pp.myappviajes.R;

/**
 * Created by pepe on 14/03/16.
 */
public class InsertEvento extends AppCompatActivity {

    public static final String TAG = "En InsertEvento: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String id_viaje = "";
        //String idc = "";
           Bundle bundle = getIntent().getExtras();
         //   String idc = getIntent().getStringExtra("idc");
            String id_viaj = getIntent().getStringExtra("idv");



        Log.i(TAG, "idviajeeeeeeeeeeeee " + id_viaj); //lo tengo
     //   Log.i(TAG, "idcateggggggggggggg " + idc);

        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_done);

        if (savedInstanceState == null) {
           // InsertFragmentEv fragment = new InsertFragmentEv();
            EditFragmentEv fragment = new EditFragmentEv();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.insert, menu);
        return true;
    }
}
