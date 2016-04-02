package gg.pp.myappviajes.ui;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import gg.pp.myappviajes.ActivitiesAdapter;
import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;


/**
 * Fragment con  Listassssssssssssssssssssssssssssssss para ver
 * para Editar o insertar se va a InseretFragmentData_m
 * Viene del menú principal
 */
public class ListFragmentCt extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "En ListFragmentCt: ";
    /**
     * Adaptador
     */
    private ActivitiesAdapter adaptador;
    /**
     * Views del formulario
     */
    private TextView categoria;
    private TextView valormoneda;

    String nomTabla;
    String miTabla;
    public ListFragmentCt() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

                Log.i(TAG, "MainFragmen8888888888888888888888888888888888888tito OnCrete  TRES");

//////////////////////////El nombre de la tsbla: /////////////////////////////////////////////////////
        nomTabla = getActivity().getIntent().getStringExtra("NombreTabla");
                Log.i(TAG, " OnCrete view T4444444444444444444444444RES  " + nomTabla);
//////////////////////////////////////////////////////////////////////////
//Envio el nomTabla a ActivitiesAdapter:
       // miTabla = nomTabla;
   //     Intent inte = new Intent(getContext(), ActivitiesAdapter. class) ;
   //     inte.putExtra( "NombreTabla" , nomTabla);
    //    startActivity(inte) ;




        adaptador = new ActivitiesAdapter(getActivity());
        // Relacionar adaptador a la lista
        setListAdapter(adaptador);
        // Iniciar Loader
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_categor, container, false);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                saveData(); // Guardar datos
                getActivity().finish();
                return true;
            case R.id.action_edit:
                beginUpdate(); // Actualizar
                return true;
            case R.id.action_delete:
                deleteData(); // Eliminar
                getActivity().finish();
                return true;
            case R.id.action_discard:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void deleteData() {
        // Uri uri = ContentUris.withAppendedId(TechsContract.CONTENT_URI, id);
        //  getActivity().getContentResolver().delete(uri, null, null);
    }
    /**
     * Envía todos los datos de la actividad hacia el formulario
     * de actualización
     */
    private void beginUpdate() {
        /*
        getActivity().startActivity(new Intent(getActivity(), UpdateActivity.class)
                                .putExtra(TechsContract.Columnas._ID, id)
                                .putExtra(TechsContract.Columnas.DESCRIPCION, descripcion.getText())
                                .putExtra(TechsContract.Columnas.CATEGORIA, categoria.getText())
                                .putExtra(TechsContract.Columnas.TECNICO, entidad.getText())
                                .putExtra(TechsContract.Columnas.PRIORIDAD, prioridad.getText())
                                .putExtra(TechsContract.Columnas.ESTADO, estado.getText())
                );
                */
    }

    private void saveData() {
        // Obtención de valores actuales
        ContentValues values = new ContentValues();
        values.put(ViajesContract.CategoriasEntry.CAT_CGT, categoria.getText().toString());
    //    values.put(TechsContract.Columnas.PRIORIDAD, prioridad.getSelectedItem().toString());
    //    values.put(TechsContract.Columnas.CATEGORIA, categoria.getSelectedItem().toString());
    //    values.put(TechsContract.Columnas.TECNICO, entidad.getSelectedItem().toString());
    //    values.put(TechsContract.Columnas.DESCRIPCION, descripcion.getText().toString());

        getActivity().getContentResolver().insert(
                ViajesContract.URI_BASE,
                values
        );
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, " onCreateLoader ");
        // Consultar todos los registros
        return new CursorLoader(
                getActivity(),
                ViajesContract.MonedasEntry.URI_CONTENIDO,
           //     ViajesContract.CategoriasEntry.URI_CONTENIDO,
                null, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adaptador.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adaptador.swapCursor(null);
    }

    @Override   //on list item click se va con el id a InsertEvento<<<<<<<<<<<<
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(TAG, " onListItemClick ===>");
        getActivity().startActivity(new Intent(getActivity(), InsertEvento.class)
                .putExtra(ViajesContract.CategoriasEntry.CAT_ID, id));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getLoaderManager().destroyLoader(0);
            if (adaptador != null) {
                adaptador.changeCursor(null);
                adaptador = null;
            }
        } catch (Throwable localThrowable) {
            // Proyectar la excepción
        }
    }
}
