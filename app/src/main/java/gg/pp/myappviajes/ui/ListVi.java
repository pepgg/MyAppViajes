package gg.pp.myappviajes.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import gg.pp.myappviajes.R;

public class ListVi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);

        if (savedInstanceState == null) {
            ListFragmentVi fragment = new ListFragmentVi();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, ListFragmentVi.class.getSimpleName())
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
