package gg.pp.myappviajes.exportimport;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Export extends AppCompatActivity {
    private static final String TAG = "En Export: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       ///////////////////////// setContentView(R.layout.activity_export_import);
        //creating a new folder for the database to be backuped to
        Log.i(TAG, "MainFragmentito onActivityCreated uno ");
        File direct = new File(Environment.getExternalStorageDirectory() + "/Bkviajes");
        Log.i(TAG, "MainFragmentito onActivityCreated CUATRO ");
        if(!direct.exists())
        {
            if(direct.mkdir())
            {
                //directory is created;
            }

        }
        exportDB();

    }


    //exporting database
    private void exportDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "gg.pp.myappviajes"
                        + "//databases//" + "cpviajes.db";
                String backupDBPath  = "/Bkviajes/cpviajes.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), "Exportado el fichero: " + backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }
}