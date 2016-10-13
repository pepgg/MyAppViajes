package gg.pp.myappviajes.ui;


import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import gg.pp.myappviajes.ActivitiesAdapterEv;
import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;


/**
 * Fragment con  Listas para ver y suprimir, para Editar o insertar se va a InseretFragmentData_m
 * Viene del menú principal
 */
public class ListFragmentEv extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "En ListFragmentEv: ";
    /**      * Adaptador     */
    private ActivitiesAdapterEv adaptador;

    /**     * Views del formulario     */
    private TextView categoria;
    private TextView valormoneda;

    String nomTabla;
    String miTabla;
    private long id_categ;
    String idCate;
    private int idviaje;
    private String id_viaj;
    String esEdit;
    private static final int INSERT_EV = Menu.FIRST; //En la barra icon +
    private static final int EDIT_EV = Menu.FIRST + 1; //contextuales
    private static final int DELET_EV = Menu.FIRST + 2;

    public ListFragmentEv() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        idCate = getActivity().getIntent().getStringExtra( "idCategoria" ) ;
             Log.i(TAG, " OnCrete view 67 idCATEGORIAAAAAAAAA  " + idCate); //lo tengo<<<<<<<<<<<<<<>>
        id_viaj = getActivity().getIntent().getStringExtra("idv");
            Log.i(TAG, " OnCrete view 69 idVIAJJJJJJJJJJJE  " + id_viaj); //lo tengo<

        adaptador = new ActivitiesAdapterEv(getActivity());
                // Relacionar adaptador a la lista
        setListAdapter(adaptador);
                // Iniciar Loader
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            Log.i(TAG, "MainFragmento OnCrete  onCreateView 91");
        View view = inflater.inflate(R.layout.list_categor, container, false);
            Log.i(TAG, "MainFragmento OnCrete  onCreateView 93");

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            /*
            case android.R.id.home:
                saveData(); // Guardar datos
                getActivity().finish();
                return true;

            case R.id.action_nuevo:
               // String nomTabla = ViajesContract.ViajesEntry.TABLE_NAME.toString();
               // Intent intent = new Intent(getActivity(), EditEv.class); //funciona?
                Intent intent = new Intent(getActivity(), MainActivity.class);
               // intent.putExtra("NombreTabla", nomTabla);
                startActivity(intent);
                return true;
            */
            case R.id.action_discard:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void deleteData(long id) {
            Log.i(TAG, "En DEEEEEEEEEEEEEEELTEEEEEE data id: " + id); //aqui llega con el id
        Uri uri = ContentUris.withAppendedId(ViajesContract.EventosEntry.URI_CONTENIDO, id);
        getActivity().getContentResolver().delete(uri, null, null); //funciona
    }

    private void beginUpdate() {
    }
/*
    private void saveData() { //insert funciona
        // Obtención de valores actuales
        ContentValues values = new ContentValues();
        values.put(ViajesContract.EventosEntry.E_NOM, categoria.getText().toString());
        getActivity().getContentResolver().insert(
                ViajesContract.URI_BASE,
                values
        );
    }
*/
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, " onCreateLoader ");
        // Consultar todos los registros
        if (idCate == null) {

            return new CursorLoader(
                getActivity(),                              // Parent activity context
                ViajesContract.EventosEntry.URI_CONTENIDO,  // Table to query
                null,                                       // Projection to return
                null,                                        // No selection clause
                null,                                       // No selection arguments
                null);                                      // No selection arguments

            } else {    // solo los de la categoria idCate
                return new CursorLoader(
                    getActivity(),                              // Parent activity context
                    ViajesContract.EventosEntry.URI_CONTENIDO,  // Table to query
                    null,                                       // Projection to return
                    (ViajesContract.EventosEntry.E_IDCGT  + " = " +  idCate + " and " +
                        ViajesContract.EventosEntry.E_IDV + " = " + id_viaj),  // selection clause
                    null,                                       // No selection arguments
                    null);                                      // No selection arguments
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adaptador.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adaptador.swapCursor(null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(this.getListView());
    }

    @Override   //on list item click se va con el id a EditEvento
    public void onListItemClick(ListView l, View v, int position, long id) {

        Log.i(TAG, "En onContextItemSelected Edit: " + id);
        Intent intent = new Intent(getActivity(), EditEv.class);
        intent.putExtra(ViajesContract.EventosEntry.E_ID, id)
                .putExtra(esEdit, true);
        Log.i(TAG, "En onContextItemSelected EDDDDDDDIIIIIIIIIIIITTTT: " + id);
        startActivityForResult(intent, EDIT_EV);

    }

    // el menu contextual, con clic prolongado
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
            Log.i(TAG, "En onCreateContextMenu nouuuuuuuuuuuuu");
        MenuInflater inflater = getActivity().getMenuInflater();
            Log.i(TAG, "En onCreateContextMenu nnnnnnnnnnnnnnouu");
        inflater.inflate(R.menu.menu_ctx_list, menu);
            Log.i(TAG, "En onCreateContextMenu doooooossssssssssss");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.ctx_delete:
                //esborra un existent
                    Log.i(TAG, "En onContextItemSelected delete id: " + getId());
                AdapterView.AdapterContextMenuInfo infoDel = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    Log.i(TAG, "En onContextItemSelected deleeeeettttttte el III_DDDDD: " + infoDel.id); //funciona
                deleteData(infoDel.id);
                getActivity().finish();
                return true;
            case R.id.ctx_edit:
                AdapterView.AdapterContextMenuInfo infoEd = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    Log.i(TAG, "En onContextItemSelected Edit: " + infoEd.id);
                Intent intent = new Intent(getActivity(), EditEv.class);
                intent.putExtra(ViajesContract.EventosEntry.E_ID, infoEd.id)
                      .putExtra(esEdit, true);
                    Log.i(TAG, "En onContextItemSelected EDDDDDDDIIIIIIIIIIIITTTT: " + infoEd.id);
                startActivityForResult(intent, EDIT_EV);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
