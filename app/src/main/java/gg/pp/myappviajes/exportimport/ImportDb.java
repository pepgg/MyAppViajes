package gg.pp.myappviajes.exportimport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import pep.gg.PromiclanDbAdapter;
import pep.gg.R;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class ImportDb extends Importar {
	private static final String TAG = "ImportDb";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState, "db");
		Log.i(TAG, " estic a IMPORT DB: "); 
			final String filename = "/sdcard/miesqola.db";//getInput();
			FileInputStream in = null;
			FileOutputStream out = null;
			try {
				in = new FileInputStream(filename);					
				out = new FileOutputStream("/data/data/pep.gg/databases/" + PromiclanDbAdapter.DATABASE_NOM);
				FileChannel inChannel = in.getChannel();
				FileChannel outChannel = out.getChannel();
				outChannel.transferFrom(inChannel, 0, inChannel.size());					
				inChannel.close();
				outChannel.close();
				in.close();
				out.close();
			} catch (final IOException ioe) {
				m_handler.post(new Runnable() {
					@Override
					public void run() {
						Bundle data = new Bundle();
						data.putString(MESSAGE, ioe.getMessage());
						data.putBoolean(SUCCESS, false);
						Message msg = new Message();
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

	@Override
	protected String getHelp() {
		return getString(R.string.help_import_db);
	}

	@Override
	protected String getHelpTitle() {
		return getString(R.string.help_import_db_title);
	}
}
