package gg.pp.myappviajes.ui;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
public class EditFragmentMp extends android.support.v4.app.Fragment
{
    /**
     * Views del formulario
     */
    private EditText mNomText;
    private TextView mlabelV;
    private EditText mValor;

    private long id_item; //id del item que voy a editar

  //  public Long mId;
    private static final String TAG = "En EditFragmentMn: ";

    private OnFragmentInteractionListener mListener;

    public EditFragmentMp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

            // para el edit, llega el id del item a editar:
            Log.i(TAG, "EditFragmentMn primero onCreate: "); // aquí llega
        id_item = getActivity().getIntent().getLongExtra(ViajesContract.MonedasEntry.MON_ID, -1);
            Log.i(TAG, "EditFragmentMn  onCreate un iditem: " + id_item); //llega el id del item
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_datam, container, false);
         // Obtener views del layout
        mNomText = (EditText) view.findViewById(R.id.nom_item);
        mValor = (EditText) view.findViewById(R.id.valor);

        mlabelV.setVisibility(View.VISIBLE);
        mValor.setVisibility(View.VISIBLE);

        return view;
    }

    /**
     * Actualizar datos de la actividad
     */
    private void updateData() {

        // Unir Uri principal con identificador
        Uri uri = ContentUris.withAppendedId(ViajesContract.MonedasEntry.URI_CONTENIDO, id_item);
            Log.i(TAG, "ViajecitosssssssInsertFragmentCCCtttt  87 updateDATA uri: " + uri); //  llega el uri bien, con su id
            Log.i(TAG, "ViajecitosssssssInsertFragmentCCCtttt  88 updateDATA nomcateg: " + mNomText.getText().toString()); //lo coge biennnnn
        ContentValues values = new ContentValues();
        values.put(ViajesContract.MonedasEntry.MON_NOM, mNomText.getText().toString());
        // Actualiza datos del Content Provider
        getActivity().getContentResolver().update(
                uri,
                values,
                null,
                null
        );
    }

    private void updateView() {
            Log.i(TAG, "ViajecitosssssssInsertFragmentCCCtttt  104 updateView id: " + id_item); //llega
        // Obtener datos del formulario
        Intent i = getActivity().getIntent();
            Log.i(TAG, "ViajecitosssssssInsertFragmentCCCtttt  109 updateView i: " + i); //llega
        String nom_text = i.getStringExtra(ViajesContract.MonedasEntry.MON_NOM);
            Log.i(TAG, "ViajecitosssssssInsertFrag   113 updateView El nombre: " + nom_text); //llega

        // Actualizar la vista
        mNomText.setText(nom_text);
    }

    @Override
    public void onResume() {
        super.onResume();
        id_item = getActivity().getIntent().getLongExtra(ViajesContract.MonedasEntry.MON_ID, -1);
        updateView(); // Cargar datos iniciales
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
        values.put(ViajesContract.MonedasEntry.MON_NOM, mNomText.getText().toString());
        Log.i(TAG, "En saveData");

        getActivity().getContentResolver().insert(
                ViajesContract.MonedasEntry.URI_CONTENIDO,
                values
        );
    }

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
