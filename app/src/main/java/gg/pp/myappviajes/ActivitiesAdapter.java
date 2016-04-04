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
public class ActivitiesAdapter extends CursorAdapter {

    public ActivitiesAdapter(Context context) {
        super(context, null, 0);
    }

    public static final String TAG = "En ActivitiesAdapter: ";

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        Log.i(TAG, "enn ActivitiesAdapter bindView  " );
        TextView categ_list = (TextView) view.findViewById(R.id.categ_list_text);
    //    Log.i(TAG, "enn ActivitiesAdapter bindView la ANTES de: " + cursor.getColumnIndex(ViajesContract.MonedasEntry.MON_NOM)); // esto es -1


///        registerForContextMenu(categ_list_text);


//////-------------->
        categ_list.setText(cursor.getString(cursor.getColumnIndex(ViajesContract.CategoriasEntry.CAT_CGT)));
    //    categ_list.setText(cursor.getString(cursor.getColumnIndex(ViajesContract.MonedasEntry.MON_NOM)));
                Log.i(TAG, "enn ActivitiesAdapter bindView la getColumnIndex de: " + cursor.getColumnIndex(ViajesContract.CategoriasEntry.CAT_CGT)); // esto es 1
     //   Log.i(TAG, "enn ActivitiesAdapter bindView la lista de: " + cursor.getColumnIndex(ViajesContract.MonedasEntry.MON_NOM)); // esto es -1
                Log.i(TAG, "enn ActivitiesAdapter bindView la getString de: " + cursor.getString(cursor.getColumnIndex(ViajesContract.CategoriasEntry.CAT_CGT))); // esto es

        //------------------- SI CAMBIO de tabla, Failed to read row 0, column -1 from a CursorWindow which has 5 rows, 2 columns.

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
       LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return inflater.inflate(R.layout.item_layout, null, false);
    }
}
