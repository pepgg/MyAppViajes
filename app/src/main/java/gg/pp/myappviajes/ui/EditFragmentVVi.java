package gg.pp.myappviajes.ui;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
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

import static android.view.View.OnClickListener;

/**
 * Fragment con formulario de EDIción de viajes */
public class EditFragmentVVi extends android.support.v4.app.Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, OnClickListener {
/*
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+1;
    *//////////


    /*     * Views del formulario     */
    Context mContext;

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

   // private LocationManager locManager;
  //  private LocationListener locListener;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault()); //.FRENCH);//  . .US);
    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;

    private long id_item; //
    private String esEdit;
    private boolean es_Edit;

    public static final String TAG = "En EditFragmentVVi: ";
    public EditFragmentVVi() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        //setContentView(R.layout.fragment_insert_ev);



        es_Edit = getActivity().getIntent().getBooleanExtra(esEdit, false);
       // id_categ = getActivity().getIntent().getLongExtra(ViajesContract.CategoriasEntry.CAT_ID, -1);
        id_item = getActivity().getIntent().getLongExtra(ViajesContract.ViajesEntry.V_ID, -1);

          //  Log.i(TAG, "EditFragmentMn  onCreate un iditem: " + id_categ); //
        if (es_Edit) {
            Log.d(TAG, "------ES updateeeeeeeeeeeeeee id : " + es_Edit + " " +  id_item ); //llegsa bien
        } else {
                Log.d(TAG, "------ES NUEVOOOOOOO " + es_Edit  ); ///llega bien

          //  id_categ = getActivity().getIntent().getLongExtra(ViajesContract.CategoriasEntry.CAT_ID, -1);
          //  id_viaj =  getActivity().getIntent().getStringExtra("idv");

          //  Log.i(TAG, "ViajecitosssssssInsertFragmentEV  onCreate un jjkkkkkkkkkkkkkkkkkk idCAT: " + id_categ); // lo tienexxxxxxxxbvn
            //idViaje();
          //  Log.i(TAG, "ViajecitosssssssInsertFragmentEV  onCreate un <<<<<<<<<<<<ID-------Viaje: " + id_viaj); // lo tiene bien ?
        }
     //   getLoaderManager().initLoader(LOADER_TIPOV, null, this);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_insert_vi, container, false);
        // Obtener views
        mNomText = (EditText) view.findViewById(R.id.nom_viaje);
        btDataIn = (Button) view.findViewById(R.id.change_data_in);
        btDataFi = (Button) view.findViewById(R.id.change_data_fi);
        mkmini = (EditText) view.findViewById(R.id.kmini);
        mkmfi = (EditText) view.findViewById(R.id.kmfi);
        mDescrip = (EditText) view.findViewById(R.id.descripv);
        mTipoV = (Spinner) view.findViewById(R.id.spinner_tv);
        String midate = (DateFormat.format("yyyy-MM-dd", new Date()).toString());
        Log.d(TAG, "onCreateView(.EV..) -> fechh222222222222222222a: = " + midate);
        btDataIn.setText(midate);

        return view;
    }
    ///////////////////////////la fecha/////////////////////////////////////////////////
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
                        Log.i(TAG, " antes delllll datae change_data__209 ");
                        btDataIn.setText(formatter.format(dateCalendar
                                .getTime()));
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


    /////////////////////////////
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "Viajecitosssssss EditFragmentVVIII  210 onActivityCreated id: " + id_item); //llega si

        // Obtener datos del formulario
        Intent i = getActivity().getIntent();
        Log.i(TAG, "ViajecitosssssssInsertFragmentVVVIII  214 onActivityCreated i: " + i); //llega si
       // String nom_text = i.getStringExtra(ViajesContract.ViajesEntry.V_NOM);
      //  Log.i(TAG, "ViajecitosssssssInsertFrag   216  El nombre: " + nom_text); //llega null, porque no lo he buscado con una consulta




    //    Spinner mTipoV = (Spinner) view.findViewById(R.id.spinner_tv);

        mTipoVAdapter = new SimpleCursorAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                null,
                new String[]{ViajesContract.TipoVEntry.COLUMN_NAME},
                new int[]{android.R.id.text1}, 2);
        mTipoVAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTipoV.setAdapter(mTipoVAdapter);
        mTipoV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected VVVII...)TipoV 216 -> position: " + position + " id: = " + id);

                Cursor tipv = (Cursor) parent.getItemAtPosition(position);
                id_tipov = tipv.getString(tipv.getColumnIndexOrThrow(ViajesContract.ViajesEntry.V_ID));

                Log.d(TAG, "onItemSelected(.EV..) -> id_tipov: = " + id_tipov);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected");
            }
        });


    //    });

        setListeners();

        if (savedInstanceState != null) {
            dateCalendar = Calendar.getInstance();
            if (savedInstanceState.getLong("dateCalendar") != 0)
                dateCalendar.setTime(new Date(savedInstanceState
                        .getLong("dateCalendar")));
        }
        /////////////
        getLoaderManager().initLoader(LOADER_TIPOV, null, this);

        if (es_Edit) {
            Log.i(TAG, "ESSSSSSSSSSS UPDATEEEEE 260");
            //aquí la consulta para comseguir select * where id = id_item
            Uri urimn = ViajesContract.ViajesEntry.URI_CONTENIDO;
            ContentResolver cr = getActivity().getContentResolver();
            final Cursor cur = cr.query(urimn, null,
                    ViajesContract.ViajesEntry.V_ID + " = " + id_item,
                    null, null);
            if (cur.moveToFirst()) { // ha trobat el evento: estic editant un registre fet anteriorment
                Log.i(TAG, "Viajecitosssssss EditFragmentVVI   268  nombre: " + cur.getString(cur.getColumnIndex(ViajesContract.ViajesEntry.V_NOM))); //  llega el nombre

                mNomText.setText(cur.getString(cur.getColumnIndex(ViajesContract.ViajesEntry.V_NOM)));
                btDataIn.setText(cur.getString(cur.getColumnIndex(ViajesContract.ViajesEntry.V_DATAIN)));
                btDataFi.setText(cur.getString(cur.getColumnIndex(ViajesContract.ViajesEntry.V_DATAFI)));
                mkmini.setText(cur.getString(cur.getColumnIndex(ViajesContract.ViajesEntry.V_KMIN)));
                mkmfi.setText(cur.getString(cur.getColumnIndex(ViajesContract.ViajesEntry.V_KMFI)));
                mDescrip.setText(cur.getString(cur.getColumnIndex(ViajesContract.ViajesEntry.V_DESC)));
                //id_tipov.setText(cur.getString(cur.getColumnIndex(ViajesContract.ViajesEntry.V_DESC)));

                final int tv = Integer.valueOf(cur.getString(cur.getColumnIndex(ViajesContract.ViajesEntry.V_TIPO))) - 1;
                Log.i(TAG, "Viajecitosssssss EditFragmentVVVVVVIIIIIII  277 int MMPP: " + tv); //  llega el id
                Uri urimp = ViajesContract.TipoVEntry.URI_CONTENIDO;
                ContentResolver crs = getActivity().getContentResolver();
                Cursor curs = crs.query(urimp, null,
                        ViajesContract.TipoVEntry.TIPO_TIPO + " = " + tv,
                        null, null);


                mTipoV.post(new Runnable() {
                    @Override
                    public void run() {
                        mTipoV.setSelection(tv);
                    }
                });

                Log.i(TAG, "Viajecitosssssss EditFragmentMNNNNN<<<<<<<  294 int mTipoV: " + Integer.valueOf(cur.getString(cur.getColumnIndex(ViajesContract.ViajesEntry.V_TIPO))));

            }
            //////////////////////////////////////////////////
        }
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case LOADER_TIPOV:
                Log.i(TAG, "InsertFragmentVV onCreateLoader AAAAA-tipoVV-AAAAA un poquito");
                return new CursorLoader(
                    getActivity(),                              // Parent activity context
                    ViajesContract.TipoVEntry.URI_CONTENIDO,    // Table to query
                    ViajesContract.TipoVEntry.TAG_COLUMNS,      // Projection to return
                    null,                                       // No selection clause
                    null,                                       // No selection arguments
                    null);                                      // Default sort order
            default:
                return null;
        }
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId())
        {
            case LOADER_TIPOV:
                onLoadFinishedTipoV(data);
                break;

        }
    }
private void onLoadFinishedTipoV(Cursor data) {
    // se puede usar para deshabilitar el spinner
    Log.d(TAG, "----onLoadFinishedMTipoV<> "   );
    mTipoVAdapter.swapCursor(data);
}


    public void onLoaderReset(Loader<Cursor> loader) {

        switch (loader.getId())
        {
            case LOADER_TIPOV:
                mTipoVAdapter.swapCursor(null);
                break;

        }
    }
////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Log.d(TAG, "------Es NU<<<<********** 748 "   );
                if (es_Edit) {
                    Log.d(TAG, "------case updateeeeeeeeeeeeeee 354 id : " +  id_item ); //llegsa bien
                    updateData();
                    getActivity().finish();
                } else {
                    Log.d(TAG, "------Es NUEVOOOOooooooooooooooo 357 "   );
                    saveData();
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
    @Override
    public void onClick(View view) {
        if (view == btDataIn) {
            fecha1 = DATE_DIALOG_IN;
            Log.i(TAG, " onClick andando voy -- 373--");
            datePickerDialog.show();
        }
        if (view == btDataFi) {
            fecha1 = DATE_DIALOG_FI;
            Log.i(TAG, " onClick andando voy -- 378--");
            datePickerDialog.show();
        }


    }
    private void updateData() {

        // Unir Uri principal con identificador
        Uri uri = ContentUris.withAppendedId(ViajesContract.ViajesEntry.URI_CONTENIDO, id_item);
            Log.i(TAG, "ViajecitosssssssInsertFragmentEv  742 updateDATA uri: " + es_Edit + uri); //  llega el uri bien, con su id
            Log.i(TAG, "ViajecitosssssssInsertFragmentEv  743 updateDATA fecha: " + btDataIn.getText().toString()); //lo coge biennnnn
            Log.i(TAG, "ViajecitosssssssInsertFragmentEv  744 updateDATA nombre: " + mNomText.getText().toString());
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

    private void saveData() {
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
                ViajesContract.ViajesEntry.URI_CONTENIDO,
                values
        );
    }
}