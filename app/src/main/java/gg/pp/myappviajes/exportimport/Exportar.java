package gg.pp.myappviajes.exportimport;

import pep.gg.HelpDialog;
import pep.gg.MiMateria;
import pep.gg.PromiclanDbAdapter;
import pep.gg.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public abstract class Exportar extends Activity {
	protected final static String MESSAGE = "msg";
	protected final static String TITLE = "title";
	protected final static String SUCCESS = "success";

	protected final static int DIALOG_FINISHED = 1;
	protected final static int DIALOG_EXPORTING = 2;

	protected static String s_title = "";
	protected static String s_message = "";

	protected TextView m_title;
	protected Button m_startBtn;
	protected String m_filename;
	protected String m_ext = "";
	protected Long mId;
	private PromiclanDbAdapter mDbHelper;
	private static final String TAG = "Exportar";
	
	public void onCreate(Bundle savedInstanceState, String ext) {
		super.onCreate(savedInstanceState);
		mDbHelper = new PromiclanDbAdapter(this);
		if (MiMateria.mId != null){
			Log.i(TAG, "MiD EN EXPORTAR oncreate  " + MiMateria.mId);
			mId = MiMateria.mId;
			m_ext = ext;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
//		initUI(); //NO lo quito de momento, pero deberia restaurarlo si funciona
export();
	}
	//el helpdialog no funciona de momento
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
//		HelpDialog.injectHelp(menu, 'h');
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case HelpDialog.MENU_HELP:
				createHelp();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void createHelp() {
//		HelpDialog.create(this, getHelpTitle(), getHelp());
	}

	protected abstract String getHelpTitle();
	protected abstract String getHelp();
	protected void initUI() {
		m_filename = PromiclanDbAdapter.nomMateria + ".csv";
		Log.i(TAG, "NAME EN initUI EXPORTAR m_filename: " + m_filename);
		export();  //muestra el dialog export	
	}
	protected void export() {
		showDialog(DIALOG_EXPORTING);
		new Thread(m_exporter).start();
	}

	@Override
	protected Dialog onCreateDialog(int which) {
		switch (which) {
			case DIALOG_EXPORTING:
				ProgressDialog dlg = new ProgressDialog(this);
				dlg.setTitle(R.string.exporting_title);
				dlg.setMessage(getString(R.string.exporting));
				return dlg;
			case DIALOG_FINISHED:
				return new AlertDialog.Builder(Exportar.this).setTitle(s_title).setMessage(s_message).setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismissDialog(DIALOG_FINISHED);
						finish();
					}
				}).create();
		}
		return null;
	}

	protected Runnable m_exporter = null;
	protected Handler m_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dismissDialog(DIALOG_EXPORTING);

			Bundle data = msg.getData();

			s_title = data.getString(TITLE);
			s_message = data.getString(MESSAGE);

			showDialog(DIALOG_FINISHED);
		}
	};
}