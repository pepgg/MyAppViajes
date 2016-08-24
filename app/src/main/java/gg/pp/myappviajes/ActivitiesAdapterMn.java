package gg.pp.myappviajes;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gg.pp.myappviajes.modelo.ViajesContract;
/**
 * {@link CursorAdapter} personalizado para las categorias de la pag. principal
 */
public class ActivitiesAdapterMn extends CursorAdapter {
    public ActivitiesAdapterMn(Context context) {
        super(context, null, 0);
    }
    public static final String TAG = "En ActiviAdapterMn: ";

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
            Log.i(TAG, "en ActiviAdapterMn bindView  ");
        TextView mone_list = (TextView) view.findViewById(R.id.categ_list_text);
        mone_list.setText(cursor.getString(cursor.getColumnIndex(ViajesContract.MonedasEntry.MON_NOM)));
            Log.i(TAG, "enn ActivitiesAdapterCt bindView la getColumnIndex de: " + cursor.getColumnIndex(ViajesContract.MonedasEntry.MON_NOM)); // esto es 1
        TextView valor_list = (TextView) view.findViewById(R.id.valor_list_text);
        valor_list.setText(cursor.getString(cursor.getColumnIndex(ViajesContract.MonedasEntry.MON_VAL)));
            Log.i(TAG, "enn ActivitiesAdapterCt bindView la getString de: " + cursor.getString(cursor.getColumnIndex(ViajesContract.MonedasEntry.MON_NOM))); // esto es
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return inflater.inflate(R.layout.item_layout, null, false);
    }
}