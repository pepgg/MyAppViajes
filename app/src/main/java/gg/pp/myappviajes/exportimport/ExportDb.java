package gg.pp.myappviajes.exportimport;

import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import gg.pp.myappviajes.R;

public class ExportDb extends Exportar {
	public static final String TAG = "En ExportDb: ";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState, "db");
		super.onCreate(savedInstanceState);
        ////////////////////
        boolean sdDisponible = false;
        boolean sdAccesoEscritura = false;

//Comprobamos el estado de la memoria externa (tarjeta SD)
        String estado = Environment.getExternalStorageState();

        if (estado.equals(Environment.MEDIA_MOUNTED))
        {
            sdDisponible = true;
			Log.i(TAG, "Comprobamos el estado de la memoria externa (tarjeta SD) MEDIA_MOUNTED sdDisponible: SI");
            sdAccesoEscritura = true;
			Log.i(TAG, "Comprobamos el estado de la memoria externa (tarjeta SD) MEDIA_MOUNTED sdAccesoEscritura: SI");


			super.m_exporter = new Runnable() {
				@Override
				public void run() {
					FileInputStream in = null;
					FileOutputStream out = null;
					try {
						in = new FileInputStream("/data/data/gg.pp.myappviajes/databases/cpviajes.db");

						Log.i(TAG, "Comprobamos el estado en super.m_exporter 1" + in);

						out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/cpviajes.db");
						Log.i(TAG, "Comprobamos el estado en super.m_exporter 2");
						FileChannel inChannel = in.getChannel();
						Log.i(TAG, "Comprobamos el estado en super.m_exporter 3");
						FileChannel outChannel = out.getChannel();
						Log.i(TAG, "Comprobamos el estado en super.m_exporter 4");
						outChannel.transferFrom(inChannel, 0, inChannel.size());
						Log.i(TAG, "Comprobamos el estado en super.m_exporter 5");
						inChannel.close();
						Log.i(TAG, "Comprobamos el estado en super.m_exporter 6");
						outChannel.close();
						Log.i(TAG, "Comprobamos el estado en super.m_exporter 7");
						in.close();
						Log.i(TAG, "Comprobamos el estado en super.m_exporter 8");
						out.close();
						Log.i(TAG, "Comprobamos el estado en super.m_exporter 9");
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
							data.putString(MESSAGE, getString(R.string.export_acabat_msg) + "\n" + "cpviajes.db");
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
        else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
        {
            sdDisponible = true;
			Log.i(TAG, "Comprobamos el estado de la memoria externa (tarjeta SD) MEDIA_MOUNTED_READ_ONLY sdDisponible: SI");
            sdAccesoEscritura = false;
			Log.i(TAG, "Comprobamos el estado de la memoria externa (tarjeta SD) MEDIA_MOUNTED_READ_ONLY sdAccesoEscritura: NO");
        }
        else
        {
            sdDisponible = false;
			Log.i(TAG, "Comprobamos el estado de la memoria externa (tarjeta SD)  sdDisponible: NO");
            sdAccesoEscritura = false;
			Log.i(TAG, "Comprobamos el estado de la memoria externa (tarjeta SD)  sdDisponible: NO");
        }




        //////////////////// aquí estaba lo que he llevado a if (estado.equals(Environment.MEDIA_MOUNTED))


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