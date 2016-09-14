package gg.pp.myappviajes.ui;

import android.content.ContentResolver;
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

/**  * Fragment con formulario de inserción de ModosPago  */
public class EditFragmentMp extends android.support.v4.app.Fragment
{
    /**     * Views del formulario     */
    private EditText mNomText;
    private TextView nuevoIt;
    private TextView labelitem;
    private TextView labelvalor;
    private EditText mValor;

    private long id_item; //id del item que voy a editar
    private long id_categ;
    public boolean es_Edit;
    private String esEdit;

    private static final String TAG = "En EditFragmentMp: ";

    private OnFragmentInteractionListener mListener;

    public EditFragmentMp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        es_Edit = getActivity().getIntent().getBooleanExtra(esEdit, false);
        // para el edit, llega el id del item a editar:
            Log.i(TAG, "EditFragmentMp primero onCreate: " + es_Edit); // aquí llega?
        id_item = getActivity().getIntent().getLongExtra(ViajesContract.MPagoEntry.MPAG_ID, -1);
            Log.i(TAG, "EditFragmentMp  onCreate un iditem: " + id_item); //llega el id del item
        if (es_Edit) {
            Log.d(TAG, "------ES updateeeeeeeeeeeeeee id : " + es_Edit + id_item ); //llegsa bien
        }
        else {
            Log.d(TAG, "------ES NUEVOOOOOOO " + es_Edit  );

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_insert_datam, container, false);
         // Obtener views del layout
        labelitem = (TextView) view.findViewById(R.id.label_item);
        mNomText = (EditText) view.findViewById(R.id.nom_item);
        labelvalor = (TextView) view.findViewById(R.id.label_valor);
        mValor = (EditText) view.findViewById(R.id.valor);
        labelvalor.setEnabled(false);
        mValor.setEnabled(false);

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "ESSSSSSSSSSS onActivityCreated 90: " + id_item);

        Intent i = getActivity().getIntent();
        Log.i(TAG, "Viajecitosssssss EditFragmentCt  96 onActivityCreated i: " + i); //llega?
        String nom_text = i.getStringExtra(ViajesContract.MPagoEntry.MPAG_MP);
        Log.i(TAG, "Viajecitosssssss EdFrag   98 onActivityCreated El nombre: " + nom_text); //llega null, porque no lo he buscado con una consulta

        if (es_Edit) {
            Log.i(TAG, "ESSSSSSSSSSS EDIT onActivityCreated 101" + es_Edit);
            //aquí la consulta para comseguir select * where id = id_item
            Uri urimn = ViajesContract.MPagoEntry.URI_CONTENIDO;
            ContentResolver cr = getActivity().getContentResolver();
            final Cursor cur = cr.query(urimn, null,
                    ViajesContract.MPagoEntry.MPAG_ID + " = " + id_item,
                    null, null);
            if (cur.moveToFirst()) { // ha trobat el categ: estic editant un registre fet anteriorment
                Log.i(TAG, "Viajecitosssssss onActivityCreated  103 updateView nombre: " + cur.getString(cur.getColumnIndex(ViajesContract.CategoriasEntry.CAT_CGT))); //  llega el nombre
                mNomText.setText(cur.getString(cur.getColumnIndex(ViajesContract.MPagoEntry.MPAG_MP)));

            }
        }

    }

    private void updateView() {
        /*
            Log.i(TAG, "ViajecitosssssssInsertFragmentCCCtttt  104 updateView id: " + id_item); //llega
        // Obtener datos del formulario
        Intent i = getActivity().getIntent();
            Log.i(TAG, "ViajecitosssssssInsertFragmentCCCtttt  109 updateView i: " + i); //llega
        String nom_text = i.getStringExtra(ViajesContract.MonedasEntry.MON_NOM);
            Log.i(TAG, "ViajecitosssssssInsertFrag   113 updateView El nombre: " + nom_text); //llega

        // Actualizar la vista
        mNomText.setText(nom_text);
        */
    }

    @Override
    public void onResume() {
        super.onResume();
        id_item = getActivity().getIntent().getLongExtra(ViajesContract.MPagoEntry.MPAG_ID, -1);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (es_Edit) {
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
    private void updateData() {

        // Unir Uri principal con identificador
        Uri uri = ContentUris.withAppendedId(ViajesContract.MPagoEntry.URI_CONTENIDO, id_item);
        Log.i(TAG, "ViajecitosssssssInsertFragmentMP  170 updateDATA uri: " + es_Edit + uri); //
        Log.i(TAG, "ViajecitosssssssInsertFragmentMP  171 updateDATA nomcateg: "+es_Edit +  mNomText.getText().toString()); //
        ContentValues values = new ContentValues();
        values.put(ViajesContract.MPagoEntry.MPAG_MP, mNomText.getText().toString());

        // Actualiza datos del Content Provider
        getActivity().getContentResolver().update(
                uri,
                values,
                null,
                null
        );
    }

    private void saveData() {
        // Obtención de valores actuales
        ContentValues values = new ContentValues();
        values.put(ViajesContract.MPagoEntry.MPAG_MP, mNomText.getText().toString());
        Log.i(TAG, "En saveData");

        getActivity().getContentResolver().insert(
                ViajesContract.MPagoEntry.URI_CONTENIDO,
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
