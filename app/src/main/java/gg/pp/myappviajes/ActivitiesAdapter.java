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
    //String nomTabla;
    public static final String TAG = "En ActivitiesAdapter: ";

   /**
     * Elimina la actividad actual
     */
    private void deleteData() {
        // Uri uri = ContentUris.withAppendedId(TechsContract.CONTENT_URI, id);
        //  getActivity().getContentResolver().delete(uri, null, null);
    }

    /**
     * Envía todos los datos de la actividad hacia el formulario de actualización
     */
    private void beginUpdate() {
        /*
        getActivity().startActivity(new Intent(getActivity(), UpdateActivity.class).putExtra(TechsContract.Columnas._ID, id)
        .putExtra(TechsContract.Columnas.DESCRIPCION, descripcion.getText()).putExtra(TechsContract.Columnas.CATEGORIA, categoria.getText())
        .putExtra(TechsContract.Columnas.TECNICO, entidad.getText()).putExtra(TechsContract.Columnas.PRIORIDAD, prioridad.getText())
        .putExtra(TechsContract.Columnas.ESTADO, estado.getText())  );
         */
    }
  //  String nomTabla = context.getIntent().getStringExtra("NombreTabla");
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
                Log.i(TAG, "enn ActivitiesAdapter bindView  " );
        TextView categ_list = (TextView) view.findViewById(R.id.categ_list_text);
    //    Log.i(TAG, "enn ActivitiesAdapter bindView la ANTES de: " + cursor.getColumnIndex(ViajesContract.MonedasEntry.MON_NOM)); // esto es -1

//////--------------quito esto:
        categ_list.setText(cursor.getString(cursor.getColumnIndex(ViajesContract.CategoriasEntry.CAT_CGT)));
    //    categ_list.setText(cursor.getString(cursor.getColumnIndex(ViajesContract.MonedasEntry.MON_NOM)));
     //   Log.i(TAG, "enn ActivitiesAdapter bindView la lista de: " + cursor.getColumnIndex(ViajesContract.CategoriasEntry.CAT_CGT)); // esto es 1
     //   Log.i(TAG, "enn ActivitiesAdapter bindView la lista de: " + cursor.getColumnIndex(ViajesContract.MonedasEntry.MON_NOM)); // esto es -1
     //   Log.i(TAG, "enn ActivitiesAdapter bindView la lista de: " + (cursor.getString(cursor.getColumnIndex(ViajesContract.CategoriasEntry.CAT_CGT))); // esto es

        //------------------- SI CAMBIO de tabla, Failed to read row 0, column -1 from a CursorWindow which has 5 rows, 2 columns.


        /*
        String estado = cursor.getString(cursor.getColumnIndex(TechsContract.Columnas.ESTADO));
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

                Log.i(TAG, "enn ActivitiesAdapter newView un context: "  + context + "cursor: " + cursor + "parent: " + parent );
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                Log.i(TAG, "enn ActivitiesAdapter newView INFLATERRRR" + parent.getContext());
     //   View view = inflater.inflate(R.layout.item_layout, null, false);
     //   return inflater.inflate(R.layout.item_layout, parent, false);
        //pruebo con esto
        return inflater.inflate(R.layout.item_layout, null, false);
    }
}
