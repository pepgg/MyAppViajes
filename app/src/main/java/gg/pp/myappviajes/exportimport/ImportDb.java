package gg.pp.myappviajes.exportimport;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import gg.pp.myappviajes.R;

public class ImportDb extends Importar {
	private static final String TAG = "ImportDb";

	//@Override
//	protected String getHelpTitle() {
//		return null;
//	}

	//@Override
//	protected String getHelp() {
//		return null;
//	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		Log.i(TAG, " estic a IMPORT DB: ");
			final String filename = "/sdcard/cpviajes.db";//getInput();
			FileInputStream in = null;
			FileOutputStream out = null;
			try {
				in = new FileInputStream(filename);
				            Log.i(TAG, " estic a IMPORT DB 1: ");
                out = new FileOutputStream("/data/data/gg.pp.myappviajes/databases/cpviajes.db");
				FileChannel inChannel = in.getChannel();
				            Log.i(TAG, " estic a IMPORT DB 2: ");
				FileChannel outChannel = out.getChannel();
				            Log.i(TAG, " estic a IMPORT DB 3: ");
				outChannel.transferFrom(inChannel, 0, inChannel.size());
				            Log.i(TAG, " estic a IMPORT DB 4: ");
				inChannel.close();
				            Log.i(TAG, " estic a IMPORT DB 5: ");
				outChannel.close();
				            Log.i(TAG, " estic a IMPORT DB 6: ");
				in.close();
				            Log.i(TAG, " estic a IMPORT DB 7: ");
				out.close();
			} catch (final IOException ioe) {
				m_handler.post(new Runnable() {
					@Override
					public void run() {
						Bundle data = new Bundle();
					    	    Log.i(TAG, " estic a IMPORT DB 8: ");
						data.putString(MESSAGE, ioe.getMessage());
						        Log.i(TAG, " estic a IMPORT DB 9: ");
						data.putBoolean(SUCCESS, false);
						        Log.i(TAG, " estic a IMPORT DB 10: ");
						Message msg = new Message();
						        Log.i(TAG, " estic a IMPORT DB 11: ");
						msg.setData(data);
						m_handler.handleMessage(msg);
					}
				});
				return;
			}
			m_handler.post(new Runnable() {
			@Override
			public void run() {
				Bundle data = new Bundle();
				data.putString(MESSAGE, getString(R.string.import_done_msg) + "\n" + filename);
				data.putBoolean(SUCCESS, true);
				Message msg = new Message();
				msg.setData(data);
				m_handler.handleMessage(msg);
			}
		});
	}

//	@Override
	protected String getHelp() {
		return getString(R.string.help_import_db);
	}

//	@Override
	protected String getHelpTitle() {
		return getString(R.string.help_import_db_title);
	}

}
