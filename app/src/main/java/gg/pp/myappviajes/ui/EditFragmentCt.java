package gg.pp.myappviajes.ui;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;

/**
 * Fragment con formulario de inserción de viajes
 */
public class EditFragmentCt extends android.support.v4.app.Fragment
       // implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener
{
    /**
     * Views del formulario
     */
    private EditText mNomText;
    private TextView mlabelV;
    private EditText mValor;

    private long id_item; //id del item que voy a editar

  //  public Long mId;
    private static final String TAG = "En EditFragmentCt: ";

    private OnFragmentInteractionListener mListener;

    public EditFragmentCt() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        ////// para el edit, llega el id del item a editarrrrrrr:
        Log.i(TAG, "ViajecitosssssssInsertFragmentCCCtttt primero onCreate un poquito: "); // aquí llega
        id_item = getActivity().getIntent().getLongExtra(ViajesContract.CategoriasEntry.CAT_ID, -1);
        Log.i(TAG, "ViajecitosssssssInsertFragmentCCCtttt  onCreate un idITEMMM: " + id_item); //llega el id del item
/*
        if (id_item > 0) {
            Log.i(TAG, "ViajecitosssssssInsertFragmentCCCtttt  el id > 0: " + id_item); //llega el id del item
            id_item = getActivity().getIntent().getLongExtra(ViajesContract.CategoriasEntry.CAT_ID, -1);
            updateView();
        }
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_datam, container, false);
         // Obtener views del layout
        mNomText = (EditText) view.findViewById(R.id.nom_item);
////////////////////////////////////////
///// Falta el Campo valor para monedas /////////
////////////////////////////////////////

   //     mlabelV.setVisibility(View.INVISIBLE)   ;  //
   //     mValor.setVisibility(View.INVISIBLE);

        return view;
    }

    /**
     * Actualizar datos de la actividad
     */
    private void updateData() {

        // Unir Uri principal con identificador
        Uri uri = ContentUris.withAppendedId(ViajesContract.CategoriasEntry.URI_CONTENIDO, id_item);
        Log.i(TAG, "ViajecitosssssssInsertFragmentCCCtttt  87 updateDATA uri: " + uri); //  llega el uri bien, con su id
        Log.i(TAG, "ViajecitosssssssInsertFragmentCCCtttt  88 updateDATA nomcateg: " +  mNomText.getText().toString()); //lo coge biennnnn
        ContentValues values = new ContentValues();
        values.put(ViajesContract.CategoriasEntry.CAT_CGT, mNomText.getText().toString());
/*
        values.put(TechsContract.Columnas.PRIORIDAD, prioridad.getSelectedItem().toString());
*/
        // Actualiza datos del Content Provider
        getActivity().getContentResolver().update(
                uri,
                values,
                null,
                null
        );
    }

    private void updateView() {
        Log.i(TAG, "ViajecitosssssssInsertFragmentCCCtttt  104 updateView id: " + id_item); //llega el id del item BIEN

        // Obtener datos del formulario
        Intent i = getActivity().getIntent();

        Log.i(TAG, "ViajecitosssssssInsertFragmentCCCtttt  109 updateView i: " + i); //llega el i del intent BIEN

        String nom_text = i.getStringExtra(ViajesContract.CategoriasEntry.CAT_CGT);
        Log.i(TAG, "ViajecitosssssssInsertFrag   112 updateView El dato: " + ViajesContract.CategoriasEntry.CAT_CGT); // Bien. es el nombre del campo
        Log.i(TAG, "ViajecitosssssssInsertFrag   113 updateView El nombre: " + nom_text); //llega NULLLLLL por eso falla Creo que pierde el id en algun sitio

        //////En techs llega perfectamente. hay que repasarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr

        // Actualizar la vista
        mNomText.setText(nom_text);     ///////////////aquí fallllllllllaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

    }

    @Override
    public void onResume() {
        super.onResume();
        id_item = getActivity().getIntent().getLongExtra(ViajesContract.CategoriasEntry.CAT_ID, -1);
        updateView(); // Cargar datos iniciales
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private String getSelectedField(SimpleCursorAdapter adapter, int selectedPosition, String field, String defaultValue)
    {
        if(adapter.getCount() > 0)
        {
            Cursor cur = (Cursor)adapter.getItem(selectedPosition);
            return cur.getString(cur.getColumnIndex(field));
        }
        else
        {
            return defaultValue;
        }
    }


  //  @Override
    public void onClick(View view) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                if (id_item > 0) {
                    updateData();
                    getActivity().finish();
                } else {
                    saveData(); // Guardar datos
                    getActivity().finish();
                }

               // getActivity().finish();
                return true;
              case R.id.action_discard:
                  getActivity().finish();
                  return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveData() {
        // Obtención de valores actuales
        ContentValues values = new ContentValues();
        values.put(ViajesContract.CategoriasEntry.CAT_CGT, mNomText.getText().toString());
        Log.i(TAG, "En saveDDDDDDDDDDDDDataAAAAAAAAAA");

        getActivity().getContentResolver().insert(
                ViajesContract.CategoriasEntry.URI_CONTENIDO,
                values
        );
    }
    // TODO: Ver que passa con este onAttach:

  //  *quito esto porque protesta:  Caused by: java.lang.RuntimeException: gg.pp.myappviajes.ui.InsertViaje@2673da8 must implement OnFragmentInteractionListener
/*                                                                     at gg.pp.myappviajes.ui.InsertFragmentVi.onAttach(InsertFragmentVi.java:273)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
   @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

 //   @Override
    public void onClick(DialogInterface dialog, int which) {
    }

  //  @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

     public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
