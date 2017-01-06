package gg.pp.myappviajes.exportimport;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ImportCSV extends Importar {
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

    public static final String INTENT_EXTRA = "intent_extra";
    private static final int DIALOG_CSV_IMPORT = 3;

    private static final String TAG = "ImportCSV";
    private GoogleApiClient client;
    //CSV file header
    private static final String FILE_HEADER = "ID, idcateg, nom, descripcio, precio, modpag, moneda, totaleur, fechah, foto1, " +
            "foto2, callenum, cp, ciudad, telef, mail, web, longitud, latitud, altitud, valoracion, kmp, comentari";


    /*
	Context mContext;
	private PromiclanDbAdapter mDbHelper;
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, "csv");
        Log.i(TAG, " estic a IMPORTCSV Eventos: ");
        //	mDbHelper = new PromiclanDbAdapter(this);
        //	mDbHelper.open();
        final String[] columns = {"ID", "IVIAJ", "ICATG", "NOM", "DESCRIP", "PREU", "MODp", "MON", "TOT€", "FECHA", "FOT1", "FOT2",
                "DIRECC", "CP", "CIUDAD", "TELEF", "eMAIL", "WEB", "LONGIT", "LATIT", "ALTIT", "VALOR", "KMp", "COMENT"};
        m_filename = "vi_notas.csv";
        Log.i(TAG, "filename EN IMPORTARCSV oncreate  " + m_filename);

        try {
            startImport();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void startImport() throws IOException {
        m_filename = "/sdcard/" + Importar.m_filename;
        Log.i(TAG, "Importando el archivito " + m_filename);
        File file = new File(m_filename);
        if (true) { // (!file.exists()) {
            try {
                FileReader reader = new FileReader(file);
                reader.close();
                //	Toast.makeText(this, R.string.import_finished, Toast.LENGTH_SHORT).show();
                finish();
            } catch (FileNotFoundException e) {
                //	Toast.makeText(this, R.string.error_file_not_found, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "File not found", e);
            } catch (IOException e) {
                //	Toast.makeText(this, R.string.error_reading_file, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "IO exception", e);
            }
        }

        FileReader reader = new FileReader(m_filename);
        CSVLector csvreader = new CSVLector(reader);
        String[] nextLine = {"_id", "idviaje", "idcateg", "nom", "descripcio",
                "precio", "modpag", "moneda", "totaleur", "fechah", "foto1", "foto2", "callenum", "cp", "ciudad", "telef", "mail", "web",
                "longitud", "latitud", "altitud", "valoracion", "kmp", "comentari"};
        int n = nextLine.length;
        Log.i(TAG, "la nextline tiene elementos " + n);
        while ((nextLine = csvreader.readNext()) != null) {
            String idev = nextLine[0];
            Log.i(TAG, "El nextline 0 es: " + idev);
            String idviaj = nextLine[1];
            Log.i(TAG, "El nextline 1 es: " + idviaj);
            String idcateg = nextLine[2];
            Log.i(TAG, "El nextline 2 es: " + idcateg);
            String nom = nextLine[3];
            Log.i(TAG, "El nextline 3 es: " + nom);
            String descrip = nextLine[4];
            Log.i(TAG, "El nextline 4 es: " + descrip);
            String preu = nextLine[5];
            Log.i(TAG, "El nextline 5 es: " + preu);
            String modpag = nextLine[6];
            Log.i(TAG, "El nextline 6 es: " + modpag);
            String moned = nextLine[7];
            Log.i(TAG, "El nextline 2 es: " + moned);
            String toteur = nextLine[8];
            Log.i(TAG, "El nextline 0 es: " + toteur);
            String fecha = nextLine[9];
            Log.i(TAG, "El nextline 1 es: " + fecha);
            String fot1 = nextLine[10];
            Log.i(TAG, "El nextline 2 es: " + fot1);
            String fot2 = nextLine[11];
            Log.i(TAG, "El nextline 3 es: " + fot2);
            String calle = nextLine[12];
            Log.i(TAG, "El nextline 4 es: " + calle);
            String cp = nextLine[13];
            Log.i(TAG, "El nextline 5 es: " + cp);
            String ciud = nextLine[14];
            Log.i(TAG, "El nextline 6 es: " + ciud);
            String telef = nextLine[15];
            Log.i(TAG, "El nextline 0 es: " + telef);
            String mail = nextLine[16];
            Log.i(TAG, "El nextline 1 es: " + mail);
            String web = nextLine[17];
            Log.i(TAG, "El nextline 3 es: " + web);
            String longi = nextLine[18];
            Log.i(TAG, "El nextline 4 es: " + longi);
            String latit = nextLine[19];
            Log.i(TAG, "El nextline 5 es: " + latit);
            String altit = nextLine[20];
            Log.i(TAG, "El nextline 6 es: " + altit);
            String valor = nextLine[21];
            Log.i(TAG, "El nextline 0 es: " + valor);
            String kmp = nextLine[22];
            Log.i(TAG, "El nextline 1 es: " + kmp);
            String comen = nextLine[23];
            Log.i(TAG, "El nextline 2 es: " + comen);

////////////////////////////
/*  http://stackoverflow.com/questions/16672074/import-csv-file-to-sqlite-in-android



*/
/////////////////////////
           /*

			 
			addRegistre(mContext, idclas, data, tema, activitat, idmat, descrip, recursos);
		}
	}
	public long addRegistre(Context context, String idclas, String data, String tema, String activitat, String idmat, String descrip, String recursos)
	{
		Log.i(TAG, "Estoy en TeachPlanUtils, addRegistre, con la tabla ");
		// Add item to list:
		ContentValues values = new ContentValues(1);
		values.put(PromiclanDbAdapter.CL_IDCLAS, PromiclanDbAdapter.CL_DATA);
		try {
			mDbHelper.insertClasses(data, Long.parseLong(idclas), tema, activitat, Long.parseLong(idmat), descrip, recursos);
			Log.i(TAG, "Insertando new nom: ");
			return 1;
		} catch (Exception e) {
			Log.i(TAG, "Insert item fallo", e);
			return -1;
		}

	}
	private void doImport(FileReader reader) throws IOException {
		Log.i(TAG, "Ahora convertidorCsvBASEActivity a Importar el archivo... ");
		Log.i(TAG, "la tabla a Importar es: " + m_filename);
		
		CSVLector csvreader = new CSVLector(reader);
	    String [] nextLine;
	    while ((nextLine = csvreader.readNext()) != null) {
	    	String nom = nextLine[0];   	
	    }
	}
//el help aun no funciona:
	@Override
	protected String getHelp() {
		return getString(R.string.help_import_csv);
	}

	@Override
	protected String getHelpTitle() {
		return getString(R.string.help_import_csv_title);
	}
*/
        }
    }

    @Override
    protected String getHelpTitle() {
        return null;
    }

    @Override
    protected String getHelp() {
        return null;
    }
}