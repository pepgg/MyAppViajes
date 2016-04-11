package gg.pp.myappviajes.ui;


import android.content.ContentUris;
import android.content.ContentValues;
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

import gg.pp.myappviajes.ActivitiesAdapter;
import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;


/**
 * Fragment con  Listas para ver y suprimir, para Editar o insertar se va a InseretFragmentData_m
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

    private static final int INSERT_CT = Menu.FIRST; //INSERT_ID_G EDITAR_GASTO DELETE_ID_G EXPORTAR_GASTOS IMPORTAR_GASTOS
    private static final int EDIT_CT = Menu.FIRST + 1;
    private static final int DELET_CT = Menu.FIRST + 2;

    public ListFragmentCt() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

                Log.i(TAG, "MainFragmento OnCrete  TRES");
/*
//////////////////////////El nombre de la tabla: /////////////////////////////////////////////////////
        nomTabla = getActivity().getIntent().getStringExtra("NombreTabla");
                Log.i(TAG, " OnCrete view T4444444444444444444444444RES  " + nomTabla);
//////////////////////////////////////////////////////////////////////////
*/
        adaptador = new ActivitiesAdapter(getActivity());
                // Relacionar adaptador a la lista
        setListAdapter(adaptador);
                // Iniciar Loader
        getLoaderManager().initLoader(0, null, this);
    //    registerForContextMenu(categ_list_text);
      //  registerForContextMenu(list_categor);
/*

* el menu contextual deberia ser así:
  //Obtenemos las referencias a los controles
    lblMensaje = (TextView)findViewById(R.id.LblMensaje);

    //Asociamos los menús contextuales a los controles
    registerForContextMenu(lblMensaje);
        */

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_categor, container, false);
      ///  Object list_categor;
       // registerForContextMenu(this.getListView());

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //
        super.onCreateOptionsMenu(menu, inflater);
    }
    //esto es para el menú de la barra, que aquí solo hay el hoe y el discard
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                saveData(); // Guardar datos
                getActivity().finish();
                return true;
            case R.id.action_nuevo:
                String nomTabla = ViajesContract.CategoriasEntry.TABLE_NAME.toString();
                Intent intent = new Intent(getActivity(), EditCt.class); //funciona
                intent.putExtra("NombreTabla", nomTabla);
                startActivity(intent);
                return true;
            //case R.id.action_delete:
            //    deleteData(id); // Eliminar
            //    getActivity().finish();
            //    return true;
            case R.id.action_discard:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void deleteData(long id) {
        Log.i(TAG, "En DEEEEEEEEEEEEEEELTEEEEEE data id: " + id); //aqui llega con el id
        Uri uri = ContentUris.withAppendedId(ViajesContract.CategoriasEntry.URI_CONTENIDO, id);
        getActivity().getContentResolver().delete(uri, null, null); //funciona
    }
    /**
     * Envía todos los datos de la actividad hacia el formulario
     * de actualización
     */
    private void beginUpdate() {
        /*
        getActivity().startActivity(new Intent(getActivity(), UpdateActivity.class)
                                .putExtra(TechsContract.Columnas._ID, id)
                                .putExtra(TechsContract.Columnas.PRIORIDAD, prioridad.getText())
                                .putExtra(TechsContract.Columnas.ESTADO, estado.getText())
                );
                */
    }

    private void saveData() { //insert funciona
        // Obtención de valores actuales
        ContentValues values = new ContentValues();
        values.put(ViajesContract.CategoriasEntry.CAT_CGT, categoria.getText().toString());
    //    values.put(TechsContract.Columnas.PRIORIDAD, prioridad.getSelectedItem().toString());
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
          //      ViajesContract.MonedasEntry.URI_CONTENIDO,
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        registerForContextMenu(this.getListView());
    }

    @Override   //on list item click se va con el id a InsertEvento<<<<<<<<<<<<
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(TAG, " onListItemClick ===> id: " + id);
        getActivity().startActivity(new Intent(getActivity(), InsertEvento.class)
                .putExtra(ViajesContract.CategoriasEntry.CAT_ID, id));
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

   //     registerForContextMenu(getListView());
     //   Log.i(TAG, "En onCreateContextMenu trrrrrrrrrrrres");

        /*
        //menu.add("Action Button");  INSERT_ID_G EDITAR_GASTO DELETE_ID_G EXPORTAR_GASTOS IMPORTAR_GASTOS
        menu.add(0, INSERT_CT,0, R.string.afegir_mc); // afegir_mc editar_mc borrar_mc
        menu.add(0, EDIT_CT,0, R.string.editar_mc);
        menu.add(0, DELET_CT,0, R.string.borrar_mc);
*/
        //MenuItem actionItem = menu.add("Action Button");
        //actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
     //   Intent intent;
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
                Intent intent = new Intent(getActivity(), EditCt.class);
                intent.putExtra(ViajesContract.CategoriasEntry.CAT_ID, infoEd.id);
                    Log.i(TAG, "En onContextItemSelected EDDDDDDDIIIIIIIIIIIITTTT: " + infoEd.id);
                startActivityForResult(intent, EDIT_CT);
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
