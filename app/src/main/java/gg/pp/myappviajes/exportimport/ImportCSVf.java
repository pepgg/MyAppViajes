package gg.pp.myappviajes.exportimport;

public class ImportCSVf extends Importar {
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
	private static final String TAG = "ImportCSVf";
	Context mContext;
	private PromiclanDbAdapter mDbHelper;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, "festius.csv");
		Log.i(TAG, " estic a IMPORTCSVfff: "); 
		mDbHelper = new PromiclanDbAdapter(this);
		mDbHelper.open();
		try {
			startImport();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void startImport() throws IOException {
		m_filename = "/sdcard/festius.csv";
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
		String[] nextLine = {"festiu"};
		int n = nextLine.length;
		Log.i(TAG, "la nextline tiene elementos " + n);
		while ((nextLine = csvreader.readNext()) != null){
			String festiu 	= nextLine[0];
			Log.i(TAG, "El nextline 0 es: " + festiu);			 
			addRegistre(mContext, festiu);
		}
	}
	public long addRegistre(Context context, String festiu) 
	{
		Log.i(TAG, "Estoy en importCSVf, addRegistre, con la tabla ");
		// Add item to list:
		ContentValues values = new ContentValues();
		values.put(PromiclanDbAdapter.FEST_FEST, PromiclanDbAdapter.FEST_ID);
				
		try {
			mDbHelper.creaFestiu(festiu);
			Log.i(TAG, "Insertando new festiu ");
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
	    	String festiu = nextLine[0];   	
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