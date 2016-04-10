package gg.pp.myappviajes.exportimport;

public class ImportCSV extends Importar {
	@Override
	protected String getHelpTitle() {
		return null;
	}

	@Override
	protected String getHelp() {
		return null;
	}

	/*
	public static final String INTENT_EXTRA = "intent_extra";
	private static final int DIALOG_CSV_IMPORT = 3;
	private static final String TAG = "ImportCSV";
	Context mContext;
	private PromiclanDbAdapter mDbHelper;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, "csv");
		 Log.i(TAG, " estic a IMPORTCSV: "); 
		mDbHelper = new PromiclanDbAdapter(this);
		mDbHelper.open();
		final String[] columns = {"idmat", "idclas", "data", "tema", "activitat", "descrip", "recursos"};
		try {
			startImport();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void startImport() throws IOException {
		m_filename = "/sdcard/" + Importar.m_filename + ".csv";
	   	Log.i(TAG, "Importando el archivito "+ m_filename);
	   	File file = new File(m_filename);
		if (true) { // (!file.exists()) {
			try{
				FileReader reader = new FileReader(file);
				reader.close();
				Toast.makeText(this, R.string.import_finished, Toast.LENGTH_SHORT).show();
				finish();
			} catch (FileNotFoundException e) {
				Toast.makeText(this, R.string.error_file_not_found, Toast.LENGTH_SHORT).show();
				Log.i(TAG, "File not found", e);
			} catch (IOException e) {
				Toast.makeText(this, R.string.error_reading_file, Toast.LENGTH_SHORT).show();
				Log.i(TAG, "IO exception", e);
			}
		}
					
		FileReader reader = new FileReader(m_filename);
		CSVLector csvreader = new CSVLector(reader);
		String[] nextLine = {"idmat", "idclas", "data", "tema", "activitat", "descrip", "recursos"};
		int n = nextLine.length;
		Log.i(TAG, "la nextline tiene elementos " + n);
		while ((nextLine = csvreader.readNext()) != null){
			String idmat 	= nextLine[0];
			Log.i(TAG, "El nextline 0 es: " + idmat);
			String idclas 	= nextLine[1];
			Log.i(TAG, "El nextline 1 es: " + idclas);
			String data 	= nextLine[2];
			Log.i(TAG, "El nextline 2 es: " + data);
			String tema 	= nextLine[3];
			Log.i(TAG, "El nextline 3 es: " + tema);
			String activitat= nextLine[4];
			Log.i(TAG, "El nextline 4 es: " + activitat);
			String descrip	= nextLine[5];
			Log.i(TAG, "El nextline 5 es: " + descrip);
			String recursos	= nextLine[6];
			Log.i(TAG, "El nextline 6 es: " + recursos);
			 
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
