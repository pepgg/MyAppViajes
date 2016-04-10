package gg.pp.myappviajes.exportimport;

public class ExportCSV extends Exportar {
	@Override
	protected String getHelpTitle() {
		return null;
	}

	@Override
	protected String getHelp() {
		return null;
	}
	/*
	private static final String TAG = "ExportCSV";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, "csv");
		
		if (MiMateria.mId != null) { //si hay un id de la materia => es una materia
			
			Log.i(TAG, "EXPORTARCSV mattt");
			final String[] columns = {"idmat", "idclas", "data", "tema", "activitat", "descrip", "recursos"};
			Log.i(TAG, "filename EN EXPORTARCSV oncreate  " + m_filename);
			super.m_exporter = new Runnable() {
				@Override
				public void run() {
				
					Log.i(TAG, "filename EN EXPORTARCSV run  " + m_filename);
					// busca tots els registres 
					SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/pep.gg/databases/" + PromiclanDbAdapter.DATABASE_NOM, null, SQLiteDatabase.OPEN_READONLY);
					Cursor mater = db.query(PromiclanDbAdapter.TABLE_MAT, new String[] {"nom"}, "_id = " + MiMateria.mId, null, null, null, null, null);
					mater.moveToFirst();
					int nomMater = mater.getColumnIndexOrThrow(PromiclanDbAdapter.MAT_NOM);
					Log.i(TAG, "En Promiclandbadapter INT nommmmmmmmmmmmMATERIA ES " + nomMater);
	   	 			String nomMateria = mater.getString(nomMater);
	   	 			Log.i(TAG, "En Promiclandbadapter nommmmmmmmmmmmMATERIA ES " + nomMateria);
	   	 			m_filename = nomMateria + ".csv";
	   	 			
	   	 			try {
	   	 				CSVWriter csv = new CSVWriter(new FileWriter(Environment.getExternalStorageDirectory() + "/" + m_filename));
	   	 				Cursor c = db.query(PromiclanDbAdapter.TABLE_CL, new String[] {"idmat", "idclas", "data", "tema", "activitat", "descrip", "recursos"}, "idmat = " + MiMateria.mId, null, null, null, null, null);
	   	 				c.moveToFirst();
	   	 				while (!c.isAfterLast()) {
	   	 					int COLUM_INDEX_IDCLAS = c.getColumnIndexOrThrow(PromiclanDbAdapter.CL_IDCLAS);
	   	 					int COLUM_INDEX_DATA = c.getColumnIndexOrThrow(PromiclanDbAdapter.CL_DATA);
	   	 					int COLUM_INDEX_TEMA = c.getColumnIndexOrThrow(PromiclanDbAdapter.CL_TEMA);
	   	 					int COLUM_INDEX_ACTIV = c.getColumnIndexOrThrow(PromiclanDbAdapter.CL_ACTIV);
	   	 					int COLUM_INDEX_DESCRIP = c.getColumnIndexOrThrow(PromiclanDbAdapter.CL_DESCRIP);
	   	 					int COLUM_INDEX_RECUR = c.getColumnIndexOrThrow(PromiclanDbAdapter.CL_RECUR);
						
	   	 					columns[0] = String.valueOf(c.getLong(COLUM_INDEX_IDCLAS));
	   	 					columns[1] = String.valueOf(c.getString(COLUM_INDEX_DATA));
	   	 					columns[2] = String.valueOf(c.getString(COLUM_INDEX_TEMA));
	   	 					columns[3] = String.valueOf(c.getString(COLUM_INDEX_ACTIV));
	   	 					columns[4] = String.valueOf(c.getString(COLUM_INDEX_DESCRIP));
	   	 					columns[5] = String.valueOf(c.getString(COLUM_INDEX_RECUR));
						
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
	   	 						MiMateria.mId = null;
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
*/
}