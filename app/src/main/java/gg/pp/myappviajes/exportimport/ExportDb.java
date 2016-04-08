package gg.pp.myappviajes.exportimport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import pep.gg.PromiclanDbAdapter;
import pep.gg.R;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;

public class ExportDb extends Exportar {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, "db");
		super.m_exporter = new Runnable() {
			@Override
			public void run() {
				FileInputStream in = null;
				FileOutputStream out = null;
				try {
					in = new FileInputStream("/data/data/pep.gg/databases/" + PromiclanDbAdapter.DATABASE_NOM);
					out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + PromiclanDbAdapter.DATABASE_NOM);
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
							data.putString(TITLE, getString(R.string.error));
							data.putBoolean(SUCCESS, false);
							Message msg = new Message();
							msg.setData(data);
							m_handler.handleMessage(msg);
						}
					});
					return;
				} finally {
					try {
						if (in != null) {
							in.close();
						}
						if (out != null) {
							out.close();
						}
					} catch (IOException e) {

					}
				}
				m_handler.post(new Runnable() {
					@Override
					public void run() {
						Bundle data = new Bundle();
						data.putString(MESSAGE, getString(R.string.export_acabat_msg) + "\n" + PromiclanDbAdapter.DATABASE_NOM);
						data.putString(TITLE, getString(R.string.export_be));
						data.putBoolean(SUCCESS, true);
						Message msg = new Message();
						msg.setData(data);
						m_handler.handleMessage(msg);
					}
				});
			}
		};
	}
	@Override
	protected String getHelp() {
		return getString(R.string.help_export_db);
	}
	@Override
	protected String getHelpTitle() {
		return getString(R.string.help_export_db_title);
	}
}