package gg.pp.myappviajes.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.DatabaseHelper;
import gg.pp.myappviajes.modelo.ViajesContract;

/** * Fragment con formulario de inserción de viajes */
public class EditFragmentVi extends android.support.v4.app.Fragment implements View.OnClickListener
        // implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener
{
    /**     * Views del formulario     */
    private EditText mNomText;
    private Button btDataIn;
    private Button btDataFi;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mDiaSet;
    private String diasem;
    public Calendar datafi;
    public Calendar diaSetmana;
    public Calendar mDataFI;
    public Calendar mDataIN;
    java.util.Date datainici;
    java.util.Date datafinal;
    java.util.Date datactual;
    private String fecha;

    Spinner mTipoV;

    private EditText mkmini;
    private EditText mkmfi;
    private EditText mDescrip;
    public int fecha1;
    static final int DATE_DIALOG_IN = 0;
    static final int DATE_DIALOG_FI = 1;
    public Long mId;
    private Long mIdviaje;
    private Long mIdMod;
    private String id_tipov;

    public static final int LOADER_TIPOV = 2; // Loader identifier for Tipov
    protected final static int DIALOG_CREATING_C = 3;
    private ProgressDialog dialog;
    private DatabaseHelper mDbHelper;

    SimpleCursorAdapter mTipoVAdapter; // Adapter for spinner

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "dd-MM-yyyy", Locale.getDefault()); //.FRENCH);//  . .US);
    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;

    private long id_item; //id del item que voy a editar
    public boolean es_Edit;
    private String esEdit;

    private static final String TAG = "En EditFragmentVi: ";

    private OnFragmentInteractionListener mListener;

    public EditFragmentVi() {
        // Required empty public constructor
    }
    //TODO: El calendario en español. primer dia de la semana el lunes
    //TODO: activar desactivar el spinner

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        es_Edit = getActivity().getIntent().getBooleanExtra(esEdit, false);
            ////// para el edit, llega el id del item a editarrrrrrr:
            Log.i(TAG, "Viajecitosssssss 105 primero onCreate un poquito: " + es_Edit); //
        id_item = getActivity().getIntent().getLongExtra(ViajesContract.ViajesEntry.V_ID, -1);
        Log.i(TAG, "Viajecitosssssss  onCreate un idITEMMM: " + id_item); //llega ?
        if (es_Edit) {
            Log.d(TAG, "------ES updateeeeeeeeeeeeeee id : " + es_Edit + id_item ); //llegsa bien ?
        }
        else {
            Log.d(TAG, "------ES NUEVOOOOOOO " + es_Edit  );
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_vi, container, false);
        findViewsById(view);

        String midate = (DateFormat.format("dd-MM-yyyy", new java.util.Date()).toString());
        Log.d(TAG, "onCreateView(.EV..) -> fechh222222222222222222a: = " + midate);
        btDataIn.setText(midate);

        mTipoVAdapter = new SimpleCursorAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                null,
                new String[] { ViajesContract.TipoVEntry.COLUMN_NAME },
                new int[] { android.R.id.text1 }, 2);
        mTipoVAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTipoV.setAdapter(mTipoVAdapter);
        mTipoV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected(...) -> position: " + position + "id: = " + id);

                Cursor tipv = (Cursor) parent.getItemAtPosition(position);
                id_tipov = tipv.getString(tipv.getColumnIndexOrThrow(ViajesContract.TipoVEntry.TIPO_ID));

                Log.d(TAG, "onItemSelected(...) -> MIidtipov: " + mIdMod + "id_tipov: = " + id_tipov);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected");
            }
        });

        setListeners();

        if (savedInstanceState != null) {
            dateCalendar = Calendar.getInstance();
            if (savedInstanceState.getLong("dateCalendar") != 0)
                dateCalendar.setTime(new Date(savedInstanceState
                        .getLong("dateCalendar")));
        }


        return view;
    }








    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        Log.d(TAG, "onCreateLoader TIPOV");
        android.support.v4.content.CursorLoader cursorLoader = new CursorLoader(
                // return new cursorLoader(
                getActivity(),
                ViajesContract.TipoVEntry.URI_CONTENIDO,
                ViajesContract.TipoVEntry.TAG_COLUMNS,
                null,
                null,
                null);
        return cursorLoader;

    }

    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        Log.d(TAG, "onLoadFinished<> " + arg0 + " " + arg1  );
        mTipoVAdapter.swapCursor(arg1);
        //el valor del idtipov tiene que ir a mTipoV
    }
    public void onLoaderReset(Loader<Cursor> arg0) {
        mTipoVAdapter.swapCursor(null);
    }

    private void setListeners() {
        btDataIn.setOnClickListener(this);
        btDataFi.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateCalendar = Calendar.getInstance();
                        dateCalendar.set(year, monthOfYear, dayOfMonth);
                        Log.i(TAG, " antes delllll switch(viewgetId change_data__ " + view);

                        switch(fecha1){
                            case DATE_DIALOG_IN: //R.id.change_data_in:
                                Log.i(TAG, " switch(viewgetId change_data_in " + fecha);
                                btDataIn.setText(formatter.format(dateCalendar
                                        .getTime()));
                                break;
                            case DATE_DIALOG_FI:
                                Log.i(TAG, " switch(view.getId change_data_fi " + fecha);
                                btDataFi.setText(formatter.format(dateCalendar
                                        .getTime()));
                                break;
                        }
                    }

                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }




    /**     * Actualizar datos de la actividad     */
    private void updateData() {

        // Unir Uri principal con identificador
        Uri uri = ContentUris.withAppendedId(ViajesContract.ViajesEntry.URI_CONTENIDO, id_item);
        Log.i(TAG, "ViajecitosssssssInsertFragmentVIIIII  228 updateDATA uri: " + es_Edit + uri); //
        Log.i(TAG, "ViajecitosssssssInsertFragmentVIIIII  229 updateDATA nomcateg: "+es_Edit +  mNomText.getText().toString()); //
        ContentValues values = new ContentValues();

        values.put(ViajesContract.ViajesEntry.V_NOM, mNomText.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_DATAIN, btDataIn.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_DATAFI, btDataFi.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_KMIN, mkmini.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_KMFI, mkmfi.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_DESC, mDescrip.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_TIPO, id_tipov.toString());

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

        String nom_text = i.getStringExtra(ViajesContract.ViajesEntry.V_NOM);
        Log.i(TAG, "ViajecitosssssssInsertFrag   112 updateView El dato: " + ViajesContract.ViajesEntry.V_NOM); // Bien. es el nombre del campo
        Log.i(TAG, "ViajecitosssssssInsertFrag   113 updateView El nombre: " + nom_text); //llega NULLLLLL por eso falla Creo que pierde el id en algun sitio

        // Actualizar la vista
        mNomText.setText(nom_text);





    }

    @Override
    public void onResume() {
        super.onResume();
        id_item = getActivity().getIntent().getLongExtra(ViajesContract.ViajesEntry.V_ID, -1);
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
    private void findViewsById(View view) {

        // Obtener views del layout
        mNomText = (EditText) view.findViewById(R.id.nom_viaje);
        btDataIn = (Button) view.findViewById(R.id.change_data_in);
        btDataFi = (Button) view.findViewById(R.id.change_data_fi);
        mkmini = (EditText) view.findViewById(R.id.kmini);
        mkmfi = (EditText) view.findViewById(R.id.kmfi);
        mDescrip = (EditText) view.findViewById(R.id.descripv);

        mTipoV = (Spinner) view.findViewById(R.id.spinner_tv);

    }

  //  @Override
    public void onClick(View view) {
        if (view == btDataIn) {
            fecha1 = DATE_DIALOG_IN;
            Log.i(TAG, " onClick andando voy -- " + view);
            datePickerDialog.show();
        } else if (view == btDataFi) {
            fecha1 = DATE_DIALOG_FI;
            Log.i(TAG, " onClick estandando vengo -- " + view);
            datePickerDialog.show();
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

    private void saveData() {
        Log.i(TAG, "En saveDDDDDDDDDDDDDataAAAAAAAAAA " + es_Edit);
        // Obtención de valores actuales
        ContentValues values = new ContentValues();

        values.put(ViajesContract.ViajesEntry.V_NOM, mNomText.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_DATAIN, btDataIn.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_DATAFI, btDataFi.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_KMIN, mkmini.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_KMFI, mkmfi.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_DESC, mDescrip.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_TIPO, id_tipov.toString());

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
