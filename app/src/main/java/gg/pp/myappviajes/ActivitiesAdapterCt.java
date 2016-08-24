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
public class ActivitiesAdapterCt extends CursorAdapter {

    public ActivitiesAdapterCt(Context context) {
        super(context, null, 0);
    }

    public static final String TAG = "En ActivitAdapterCt: ";

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

                Log.i(TAG, "enn ActivitiesAdapterCt bindView  " );
        TextView categ_list = (TextView) view.findViewById(R.id.categ_list_text);
        categ_list.setText(cursor.getString(cursor.getColumnIndex(ViajesContract.CategoriasEntry.CAT_CGT)));

                Log.i(TAG, "enn ActivitiesAdapterCt bindView la getColumnIndex de: " + cursor.getColumnIndex(ViajesContract.CategoriasEntry.CAT_CGT)); // esto es 1

                Log.i(TAG, "enn ActivitiesAdapterCt bindView la getString de: " + cursor.getString(cursor.getColumnIndex(ViajesContract.CategoriasEntry.CAT_CGT))); // esto es
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
       LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return inflater.inflate(R.layout.item_layout, null, false);
    }
}
