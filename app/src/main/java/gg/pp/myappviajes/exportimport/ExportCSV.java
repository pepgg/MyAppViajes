package gg.pp.myappviajes.exportimport;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.DatabaseHelper;

import static android.os.Environment.getExternalStorageDirectory;

public class ExportCSV extends Exportar {
    /*
    @Override
	protected String getHelpTitle() {
		return null;
	}

	@Override
	protected String getHelp() {
		return null;
	}
	*/
    private static final String TAG = "ExportCSV";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //CSV file header
    private static final String FILE_HEADER = "_id, idviaje, idcateg, fechah, kmp, nom, descripcio, precio, modpag, moneda, totaleur, foto1, " +
            "foto2, valoracion, callenum, cp, ciudad, telef, mail, web, longitud, latitud, altitud,  comentari";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, "csv");
                Log.i(TAG, "EXPORTARCSV Eventos: ");

        final String[] columns = {"ID","IVIAJ","ICATG","FECHA","KMP","NOM","DESCRIP", "MODp","MONE","TOT€","FOT1","FOT2",
                "VALOR","DIRECC","CP","CIUDAD","TELEF","eMAIL","WEB","LONGIT","LATIT","ALTIT","COMENT","PRECIO"};
        Log.i(TAG, "filename EN EXPORTARCSV oncreate  " + m_filename);
        super.m_exporter = new Runnable() {
            @Override
            public void run() {

                Log.i(TAG, "filename EN EXPORTARCSV run  " + m_filename);
                // busca tots els registres
                SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/gg.pp.myappviajes/databases/cpviajes.db"
                     // +  DatabaseHelper.mDbHelper, null, SQLiteDatabase.OPEN_READONLY);
                        , null, SQLiteDatabase.OPEN_READONLY);
                ////////////////// aquí el cursorrrrrr: select * from eventos
                m_filename = "vi_notas.csv";
                //CSV file header
                try {
                    CSVWriter csv = null;
                    csv = new CSVWriter(new FileWriter(getExternalStorageDirectory() + "/Bkviajes/" + m_filename));
                   // CSVWriter csv = new CSVWriter(new FileWriter(csv, true));
//Write the CSV file header
                  csv.writeNext(columns);
                  //  csv.writeNext(FILE_HEADER.toString());

                    Cursor c = db.query(DatabaseHelper.mDbHelper.TABLE_EVENT, new String[]{"_id", "idviaje", "idcateg", "fechah", "kmp", "nom", "descripcio",
                            "modpag", "moneda", "totaleur",  "foto1", "foto2", "valoracion", "callenum", "cp", "ciudad", "telef", "mail", "web",
                            "longitud", "latitud", "altitud",  "comentari", "precio"}, null, null, null, null, null);
                    c.moveToFirst();

                  //  java.sql.ResultSet myResultSet = c();
                 //   csv.writeAll(myResultSet, includeHeaders); //writer is instance of CSVWriter

                    while (!c.isAfterLast()) {
                        int COLUM_INDEX_IDEV = c.getColumnIndexOrThrow(DatabaseHelper.E_ID);
                        int COLUM_INDEX_IDVIAJ = c.getColumnIndexOrThrow(DatabaseHelper.E_IDV);
                        int COLUM_INDEX_IDCATEG = c.getColumnIndexOrThrow(DatabaseHelper.E_IDCGT);
                        int COLUM_INDEX_FECHA = c.getColumnIndexOrThrow(DatabaseHelper.E_DATAH);
                        int COLUM_INDEX_KMP = c.getColumnIndexOrThrow(DatabaseHelper.E_KMP);
                        int COLUM_INDEX_NOM = c.getColumnIndexOrThrow(DatabaseHelper.E_NOM);
                        int COLUM_INDEX_DESCRIP = c.getColumnIndexOrThrow(DatabaseHelper.E_DESC);
                        int COLUM_INDEX_MODPAG = c.getColumnIndexOrThrow(DatabaseHelper.E_MPAG);
                        int COLUM_INDEX_NONED = c.getColumnIndexOrThrow(DatabaseHelper.E_MON);
                        int COLUM_INDEX_TOTEUR = c.getColumnIndexOrThrow(DatabaseHelper.E_TOT);
                        int COLUM_INDEX_FOT1 = c.getColumnIndexOrThrow(DatabaseHelper.E_FOT1);
                        int COLUM_INDEX_FOT2 = c.getColumnIndexOrThrow(DatabaseHelper.E_FOT2);
                        int COLUM_INDEX_VALOR = c.getColumnIndexOrThrow(DatabaseHelper.E_VAL);
                        int COLUM_INDEX_CALLE = c.getColumnIndexOrThrow(DatabaseHelper.E_DIR);
                        int COLUM_INDEX_CP = c.getColumnIndexOrThrow(DatabaseHelper.E_CP);
                        int COLUM_INDEX_CIUD = c.getColumnIndexOrThrow(DatabaseHelper.E_CIUD);
                        int COLUM_INDEX_TELEF = c.getColumnIndexOrThrow(DatabaseHelper.E_TEL);
                        int COLUM_INDEX_MAIL = c.getColumnIndexOrThrow(DatabaseHelper.E_MAIL);
                        int COLUM_INDEX_WEB = c.getColumnIndexOrThrow(DatabaseHelper.E_WEB);
                        int COLUM_INDEX_LONGI = c.getColumnIndexOrThrow(DatabaseHelper.E_LON);
                        int COLUM_INDEX_LATIT = c.getColumnIndexOrThrow(DatabaseHelper.E_LAT);
                        int COLUM_INDEX_ALTIT = c.getColumnIndexOrThrow(DatabaseHelper.E_ALT);
                        int COLUM_INDEX_COMEN = c.getColumnIndexOrThrow(DatabaseHelper.E_COM);
                        int COLUM_INDEX_PREU = c.getColumnIndexOrThrow(DatabaseHelper.E_PREU);

                        columns[0] = String.valueOf(c.getLong(COLUM_INDEX_IDEV));
                        columns[1] = String.valueOf(c.getString(COLUM_INDEX_IDVIAJ));
                        columns[2] = String.valueOf(c.getString(COLUM_INDEX_IDCATEG));
                        columns[3] = String.valueOf(c.getString(COLUM_INDEX_FECHA));
                        columns[4] = String.valueOf(c.getString(COLUM_INDEX_KMP));
                        columns[5] = String.valueOf(c.getString(COLUM_INDEX_NOM));
                        columns[6] = String.valueOf(c.getString(COLUM_INDEX_DESCRIP));
                        columns[7] = String.valueOf(c.getString(COLUM_INDEX_MODPAG));
                        columns[8] = String.valueOf(c.getString(COLUM_INDEX_NONED));
                        columns[9] = String.valueOf(c.getString(COLUM_INDEX_TOTEUR));
                        columns[10] = String.valueOf(c.getString(COLUM_INDEX_FOT1));
                        columns[11] = String.valueOf(c.getString(COLUM_INDEX_FOT2));
                        columns[12] = String.valueOf(c.getString(COLUM_INDEX_VALOR));
                        columns[13] = String.valueOf(c.getString(COLUM_INDEX_CALLE));
                        columns[14] = String.valueOf(c.getString(COLUM_INDEX_CP));
                        columns[15] = String.valueOf(c.getString(COLUM_INDEX_CIUD));
                        columns[16] = String.valueOf(c.getString(COLUM_INDEX_TELEF));
                        columns[17] = String.valueOf(c.getString(COLUM_INDEX_MAIL));
                        columns[18] = String.valueOf(c.getString(COLUM_INDEX_WEB));
                        columns[19] = String.valueOf(c.getString(COLUM_INDEX_LONGI));
                        columns[20] = String.valueOf(c.getString(COLUM_INDEX_LATIT));
                        columns[21] = String.valueOf(c.getString(COLUM_INDEX_ALTIT));
                        columns[22] = String.valueOf(c.getString(COLUM_INDEX_COMEN));
                        columns[23] = String.valueOf(c.getString(COLUM_INDEX_PREU));

                        csv.writeNext(columns);
                        c.moveToNext();
                    }
                    c.close();
                    db.close();
                    csv.close();
                    m_handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Bundle data = new Bundle();
                            data.putString(MESSAGE, getString(R.string.export_acabat_msg) + "\n" + m_filename);
                            data.putString(TITLE, getString(R.string.export_be));
                            data.putBoolean(SUCCESS, true);

                            Message msg = new Message();
                            msg.setData(data);
                            m_handler.handleMessage(msg);
                         //   MiMateria.mId = null;
                        }
                    });
                } catch (final IOException e) {
                  //  e.printStackTrace();
                    e.printStackTrace();
                    m_handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Bundle data = new Bundle();
                            data.putString(MESSAGE, e.getMessage());
                            data.putString(TITLE, getString(R.string.error));
                            data.putBoolean(SUCCESS, false);

                            Message msg = new Message();
                            msg.setData(data);
                            m_handler.handleMessage(msg);
                        }
                    });
                    return;
                } //catch
            }//run()
        };//runable()
    /*
        }else { // si no hay un id de la materia tiene que ser festius
			Log.i(TAG, "EXPORTARCSV FESTTTTTT  ");
			final String[] columns = {"festiu"};
			super.m_exporter = new Runnable() {
				@Override
				public void run() {
					
					Log.i(TAG, "filename EN EXPORTARCSV run  " + m_filename);
					// busca tots els registres 
					SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/pep.gg/databases/" + PromiclanDbAdapter.DATABASE_NOM, null, SQLiteDatabase.OPEN_READONLY);
					Cursor festiu = db.query(PromiclanDbAdapter.TABLE_FEST, new String[] {"*"}, null, null, null, null, null, null);
					festiu.moveToFirst();
					m_filename = "festius.csv";
	   	 			try {
	   	 				CSVWriter csv = new CSVWriter(new FileWriter(Environment.getExternalStorageDirectory() + "/" + m_filename));
	   	 				Cursor c = db.query(PromiclanDbAdapter.TABLE_FEST, new String[] {"*"}, null, null, null, null, null, null);
	   	 				c.moveToFirst();
	   	 				while (!c.isAfterLast()) {
	   	 					int COLUM_INDEX_FEST = c.getColumnIndexOrThrow(PromiclanDbAdapter.FEST_FEST);
	   	 					Log.i(TAG, "En COLUM_INDEX_FEST INT nommmm  FESTIU ES " + COLUM_INDEX_FEST);			
	   	 					String nomFestiu = c.getString(COLUM_INDEX_FEST);
	   	 					columns[0] = nomFestiu;
	   	 					Log.i(TAG, "En columns 0 nommmmmmmm FESTIU ES " + columns[0]);
	   	 					csv.writeNext(columns);
	   	 					c.moveToNext();
	   	 				}
	   	 				c.close();
	   	 				db.close();
	   	 				csv.close();
	   	 				m_handler.post(new Runnable() {
	   	 					@Override
							public void run() {
	   	 						Bundle data = new Bundle();
	   	 						data.putString(MESSAGE, getString(R.string.export_acabat_msg) + "\n" + m_filename);
	   	 						data.putString(TITLE, getString(R.string.export_be));
	   	 						data.putBoolean(SUCCESS, true);

	   	 						Message msg = new Message();
	   	 						msg.setData(data);
	   	 						m_handler.handleMessage(msg);
	   	 					}
	   	 				});
	   	 			} catch (final IOException e) {
	   	 				e.printStackTrace();
	   	 				m_handler.post(new Runnable() {
	   	 					@Override
							public void run() {
	   	 						Bundle data = new Bundle();
	   	 						data.putString(MESSAGE, e.getMessage());
	   	 						data.putString(TITLE, getString(R.string.error));
	   	 						data.putBoolean(SUCCESS, false);

	   	 						Message msg = new Message();
	   	 						msg.setData(data);
	   	 						m_handler.handleMessage(msg);
	   	 					}
	   	 				});
	   	 				return;
	   	 			} //catch
				}//run()
			};//runable()
		}//if
        */
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //el help no funciona todavia
    @Override
    protected String getHelp() {
        return getString(R.string.help_export_csv);
    }

    @Override
    protected String getHelpTitle() {
        return getString(R.string.help_export_csv_title);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ExportCSV Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}