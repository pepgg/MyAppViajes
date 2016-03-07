package gg.pp.myappviajes.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import gg.pp.myappviajes.R;

/**
 * Created by pepe on 5/03/16.
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Provider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "En MainActivity llega");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainFragment fragment = new MainFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, MainFragment.class.getSimpleName())
                    .commit();

        }

    }
}