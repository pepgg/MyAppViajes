package gg.pp.myappviajes.exportimport;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.DatabaseHelper;

public abstract class Importar extends Activity {


	protected final static String MESSAGE = "msg";
	protected final static String TITLE = "title";
	protected final static String SUCCESS = "success";

	protected final static int DIALOG_FINISHED = 1;
	protected final static int DIALOG_IMPORTING = 2;

	protected TextView m_title;
	protected String m_ext = "";
	protected Spinner m_fileSelector;
	protected Button m_startBtn;
	protected static String m_filename;
	protected Long mId;
	protected static int s_title;
	protected static String s_message;
	protected static boolean s_success = false;
	private DatabaseHelper mDbHelper;
	private static final String TAG = "En Importar";
	public void onCreate(Bundle savedInstanceState, String ext) {
		super.onCreate(savedInstanceState);
		mDbHelper = new DatabaseHelper(this);
		m_filename = "vi_notas.csv";
		m_ext = ext;
		Log.i(TAG, " ESTIC EN IMPORT onCreate i l'extensió es: " + m_ext);
	//	Log.i(TAG, "MiD EN IMPORTAR oncreate  " + MiMateria.mId);
	//	mId = MiMateria.mId;
		/* ********************************** para los CSV *******************************
		if (m_ext == "csv"){
		
			SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/pep.gg/databases/" + PromiclanDbAdapter.DATABASE_NOM, null, SQLiteDatabase.OPEN_READONLY);
			Cursor mater = db.query(PromiclanDbAdapter.TABLE_MAT, new String[] {"nom"}, "_id = " + MiMateria.mId, null, null, null, null, null);
			mater.moveToFirst();
			int nomMater = mater.getColumnIndexOrThrow(PromiclanDbAdapter.MAT_NOM);
        	Log.i(TAG, "En importarCSV oncreat INT nommmmmmmmmmmmMATERIA ES " + nomMater);
	 		String nomMateria = mater.getString(nomMater);
	 		Log.i(TAG, "En importarCSV oncreat nommmmmmmmmmmmMATERIA ES " + nomMateria);
	 		m_filename = nomMateria;
		}
		if (m_ext == "festius.csv"){
			
			SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/pep.gg/databases/" + PromiclanDbAdapter.DATABASE_NOM, null, SQLiteDatabase.OPEN_READONLY);
			Cursor festiu = db.query(PromiclanDbAdapter.TABLE_FEST, new String[] {"*"}, null, null, null, null, null, null);
			festiu.moveToFirst();
		}
		*/
	}
	@Override
	public void onResume() {
		super.onResume();
		initUI();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
        switch (item.getItemId()) {
			case HelpDialog.MENU_HELP:
				return true;
		}
        */
		return super.onOptionsItemSelected(item);
	}
// el help no funciona de momento
	protected void createHelp() {
	}

	protected abstract String getHelpTitle();
	protected abstract String getHelp();
	protected void initUI() {
		Log.i(TAG, " ESTIC EN IMPORT initUI con m_filename= " + m_filename);
		input();
	}
	protected void postProcessing() {
		Log.i(TAG, " ESTIC EN IMPORT postProcessing");
	}

	protected void input() {
		Log.i(TAG, " ESTIC EN IMPORT input()"); //SI
		s_success = false;
		Log.i(TAG, " ESTIC EN IMPORT input() " + s_success); //si, false
		Log.i(TAG, " ESTIC EN IMPORT DESPYUES DEL THREAD input()" + m_importer); //aqui da null
		/// aqui activa el dialog importing de import
		showDialog(DIALOG_IMPORTING);
     /*
		if (m_ext=="csv"){
			Intent intent;
			intent = new Intent(this, ImportCSV.class);
			startActivityForResult(intent, 0);
		};
	*/
	}

	@Override
	protected Dialog onCreateDialog(int which) {
		switch (which) {
			case DIALOG_FINISHED:
				return new AlertDialog.Builder(Importar.this).setTitle(s_title).setMessage(s_message).setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismissDialog(DIALOG_FINISHED);
						if (s_success) {
							postProcessing();
						}
					}
				}).create();
			case DIALOG_IMPORTING:
				
				Log.i(TAG, " ESTIC EN IMPORT DIALOG_IMPORTING");
				
				ProgressDialog dlg = new ProgressDialog(this);
				dlg.setTitle(R.string.importing_title);
				dlg.setMessage(getString(R.string.importing));
				return dlg;
		}
		return null;
	}

	protected Runnable m_importer = null;

	protected FilenameFilter m_filter = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String filename) {
			// get the extension
			int dot_loc = filename.lastIndexOf(".");
			if (dot_loc >= 0) {
				String ext = filename.substring(dot_loc + 1);
				return ext.equalsIgnoreCase(m_ext);
			}
			return false;
		}
	};

	protected Handler m_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dismissDialog(DIALOG_IMPORTING);
			final Bundle data = msg.getData();
			final boolean success = data.getBoolean(SUCCESS, false);
			s_title = success ? R.string.import_be : R.string.error;
			s_success = success;
			s_message = data.getString(MESSAGE);

			showDialog(DIALOG_FINISHED);
		}
	};

}
