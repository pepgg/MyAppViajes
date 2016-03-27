package gg.pp.myappviajes;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;

import gg.pp.myappviajes.modelo.ViajesContract;



/**
 * {@link CursorAdapter} personalizado para las categorias de la pag. principal
 */
public class ActivitiesAdapter extends CursorAdapter {

    public ActivitiesAdapter(Context context) {
        super(context, null, 0);
    }
    public static final String TAG = "Provider";

    /**
     * Elimina la actividad actual
     */
    private void deleteData() {
        // Uri uri = ContentUris.withAppendedId(TechsContract.CONTENT_URI, id);
        //  getActivity().getContentResolver().delete(
        //         uri,
        //          null,
        //          null
        //  );
    }

    /**
     * Envía todos los datos de la actividad hacia el formulario
     * de actualización
     */
    private void beginUpdate() {
        /*
        getActivity()
                .startActivity(

                        new Intent(getActivity(), UpdateActivity.class)
                                .putExtra(TechsContract.Columnas._ID, id)
                                .putExtra(TechsContract.Columnas.DESCRIPCION, descripcion.getText())
                                .putExtra(TechsContract.Columnas.CATEGORIA, categoria.getText())
                                .putExtra(TechsContract.Columnas.TECNICO, entidad.getText())
                                .putExtra(TechsContract.Columnas.PRIORIDAD, prioridad.getText())
                                .putExtra(TechsContract.Columnas.ESTADO, estado.getText())

                );
                */
    }






    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.i(TAG, "enn ActivitiesAdapter bindView un poquito");
        ////////////////
        //aqui pndre los spinners de modopago y monedas
        //
        ////////////////
        ///////////
        ///////////
        TextView categ_list = (TextView) view.findViewById(R.id.categ_list_text);
        Log.i(TAG, "enn ActivitiesAdapter bindView haciendo la lista de categorias");
        categ_list.setText(cursor.getString(
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
        Log.i(TAG, "enn ActivitiesAdapter newView un poquito");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Log.i(TAG, "enn ActivitiesAdapter newView INFLATERRRR");
        return inflater.inflate(R.layout.item_layout, parent, false);





    }
}
