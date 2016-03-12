package gg.pp.myappviajes;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
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
    public static final String TAG = "Provider";




    @Override
    public void bindView(View view, Context context, Cursor cursor) {
       // Log.i(TAG, "enn ActivitiesAdapter bindView un poquito");
        TextView categ_list_text = (TextView) view.findViewById(R.id.categ_list_text);
        categ_list_text.setText(cursor.getString(
                cursor.getColumnIndex(ViajesContract.CategoriasEntry.CAT_CGT)));

       
        /*
        String estado = cursor.getString(
                cursor.getColumnIndex(TechsContract.Columnas.ESTADO));

        View indicator = view.findViewById(R.id.indicator);

        switch (estado) {
            case "Cerrada":
                indicator.setBackgroundResource(R.drawable.green_indicator);
                break;
            case "En Curso":
                indicator.setBackgroundResource(R.drawable.red_indicator);
                break;
            case "Abierta":
                indicator.setBackgroundResource(R.drawable.yellow_indicator);
                break;
        }
        */
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
      //  Log.i(TAG, "enn ActivitiesAdapter newView un poquito");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.item_layout, parent, false);
    }
}
