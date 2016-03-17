package gg.pp.myappviajes.ui;

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
import android.widget.ImageButton;
import android.widget.ListView;

import gg.pp.myappviajes.ActivitiesAdapter;
import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;

/**
 * Created by pepe on 5/03/16.
 */
public class MainFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TAG = "Provider";
    /**
     * Adaptador
     */
    private ActivitiesAdapter adaptador;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilitar al fragmento para contribuir en la action bar
        setHasOptionsMenu(true);

    }
    //////////////////////////////////////////////las cards se intercambian
    //final CardView card1 = new CardView(this);
    //card1.setVisibility(View.INVISIBLE);
    //Log.i(TAG, "Inserción en ");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "MainFragmentito OnCrete view un poquito");
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ImageButton fab = (ImageButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity()
                                .startActivity(
                                        new Intent(getActivity(), InsertCategory.class)
                                );

                    }
                }
        );

        return view;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_edit:
                beginUpdate(); // Actualizar
                return true;
            case R.id.action_delete:
                deleteData(); // Eliminar
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "MainFragmentito onActivityCreated un poquito");
        // Iniciar adaptador
        adaptador = new ActivitiesAdapter(getActivity());
        // Relacionar adaptador a la lista
        setListAdapter(adaptador);
        // Iniciar Loader
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "MainFragmentito onCreateLoader un poquito");
        // Consultar todos los registros
        return new CursorLoader(
                getActivity(),
                ViajesContract.CategoriasEntry.URI_CONTENIDO,
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

    @Override   //on list item click se va con el id a InsertCategory<<<<<<<<<<<<
    public void onListItemClick(ListView l, View v, int position, long id) {
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
