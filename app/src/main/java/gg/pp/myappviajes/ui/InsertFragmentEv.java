package gg.pp.myappviajes.ui;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;

/**
 * Fragment con formulario de inserción de eventos
 */
public class InsertFragmentEv extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    /**
     * Views del formulario
     */
    //////////
    Context mContext;

  //  public InsertFragmentEv(Context mContext) {
   //     this.mContext = mContext;
  //  }

    ///////////////////
    private TextView nomcateg;
    private EditText nombre;
    private EditText descripcio;
    private EditText totaleur;
    ///////////////////////////////
    //  private
    Spinner modpag, monedas;
    ////////////////////////////////
    private TextView eur;
    private Button datae;
    private EditText direccio;
    private EditText cp;
    private EditText ciudad;
    private EditText telef;
    private EditText mail;
    private EditText web;

    private Button gps;
    private TextView longi;
    private TextView latit;
    private TextView altit;

    private EditText kmactual;
    private RatingBar valoracio;
    private EditText comentaris;
    public int fecha1;
    static final int DATE_DIALOG = 0;
    Calendar c = Calendar.getInstance();
    private long id_categ;
    long idcat;
    private String id_modopag;
    private String id_monedas;
    private String idviaje;
    public static final int LOADER_MODPAG = 1; // Loader identifier for ModPag
    public static final int LOADER_MONED = 2; // Loader identifier for Monedas
    SimpleCursorAdapter mModPagAdapter, mMonedAdapter, sAdapter; // Adapters for both spinners

    private LocationManager locManager;
    private LocationListener locListener;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "dd-MM-yyyy", Locale.getDefault()); //.FRENCH);//  . .US);
    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;


    public static final String TAG = "En InsertFragmentEv: ";

    public InsertFragmentEv() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id_categ = getActivity().getIntent().getLongExtra(ViajesContract.CategoriasEntry.CAT_ID, -1);
        setHasOptionsMenu(true);
        Log.i(TAG, "ViajecitosssssssInsertFragmentEV  onCreate un poquitokkkkkkkkkkkkkkkkkkkkkkkkkkkkkk: " + id_categ); // lo tienexxxxxxxxbvn

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert, container, false);

        // Obtener views
        nomcateg = (TextView) view.findViewById(R.id.categor_input);
        nombre = (EditText) view.findViewById(R.id.nom_e);
        descripcio = (EditText) view.findViewById(R.id.descripcio_e);

        modpag = (Spinner) view.findViewById(R.id.spinner_mod_pag);
        totaleur = (EditText) view.findViewById(R.id.preu_e);
        monedas = (Spinner) view.findViewById(R.id.spinner_moned);
        eur = (TextView) view.findViewById(R.id.tot_eur);
        datae = (Button) view.findViewById(R.id.fecha_e);

        direccio = (EditText) view.findViewById(R.id.direccio);
        cp = (EditText) view.findViewById(R.id.cp);
        ciudad = (EditText) view.findViewById(R.id.ciudad);
        telef = (EditText) view.findViewById(R.id.telef);
        mail = (EditText) view.findViewById(R.id.mail);
        web = (EditText) view.findViewById(R.id.web);

        gps = (Button) view.findViewById(R.id.gps);
        longi = (TextView) view.findViewById(R.id.longitud);
        latit = (TextView) view.findViewById(R.id.latitud);
        altit = (TextView) view.findViewById(R.id.altitud);
        kmactual = (EditText) view.findViewById(R.id.Km_p);


        valoracio = (RatingBar) view.findViewById(R.id.ratingBar);
        comentaris = (EditText) view.findViewById(R.id.coment);

        idcat = id_categ;

        ///// EL GPSSSSSSSSSSSSSSSSSSS
        gps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG, "EL GPSSSSSSSSSSSSSSSSSSS"); //Si lo tiene
                        comenzarLocalizacion();
            }
        });

        Log.i(TAG, "InsertFragmentTT T TT onCreateView 139 un poquito: " + id_categ); //Si lo tiene

//////////// los spinnerssssssssssssssssssssssssss modopag, monedas
        //   mTipoV.setEnabled(false);  //TODO:<<<<<<<>>>>>>>>>>>>>tengo que arrglar esto de los enabled

        //aqui Modopago

        mModPagAdapter = new SimpleCursorAdapter(
                getContext(), android.R.layout.simple_spinner_item,
                null,
                new String[]{ViajesContract.MPagoEntry.COLUMN_NAME},
                new int[]{android.R.id.text1}, 2);
        mModPagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modpag.setAdapter(mModPagAdapter);
        modpag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected EV...)modopag -> position: " + position + " id: = " + id);

                Cursor tipv = (Cursor) parent.getItemAtPosition(position);
                id_modopag = tipv.getString(tipv.getColumnIndexOrThrow(ViajesContract.MPagoEntry.MPAG_ID));

                Log.d(TAG, "onItemSelected(.EV..) -> id_modopag: = " + id_modopag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected");
            }
        });
        //aqui MONEDAS

        mMonedAdapter = new SimpleCursorAdapter(
                getContext(), android.R.layout.simple_spinner_item,
                null,
                new String[]{ViajesContract.MonedasEntry.COLUMN_NAME},
                new int[]{android.R.id.text1}, 2);
        mMonedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monedas.setAdapter(mMonedAdapter);
        monedas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected EV...)monedas -> position: " + position + " id: = " + id);

                Cursor mop = (Cursor) parent.getItemAtPosition(position);
                id_monedas = mop.getString(mop.getColumnIndexOrThrow(ViajesContract.MonedasEntry.MON_ID));
//id_monedas = id;
                Log.d(TAG, "onItemSelected(.EV..) -> id_monedas: = " + id_monedas);
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
        /////////////
        getLoaderManager().initLoader(LOADER_MODPAG, null, this);
        getLoaderManager().initLoader(LOADER_MONED, null, this);
        return view;

    }


    ///////////////////////////la fecha/////////////////////////////////////////////////
    private void setListeners() {
        datae.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateCalendar = Calendar.getInstance();
                        dateCalendar.set(year, monthOfYear, dayOfMonth);
                        Log.i(TAG, " antes delllll datae change_data__209 ");
                        datae.setText(formatter.format(dateCalendar
                                .getTime()));
                    }
                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    /////////////// el GPSSSSSSSSSSSSSSSSSS



    private void comenzarLocalizacion() {
/*
        //Si el GPS no está habilitado
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //  mostrarAvisoGpsDeshabilitado();
            Log.i(TAG, "ElGPSSS NNNO eSta activado¡¡¡¡");
        }
        */
        //Obtenemos una referencia al LocationManager
        Log.i(TAG, "Provider Status: ppSSSSSSSSSS" );

        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Log.i(TAG, "Provider Status: ppSSSS22222222SSSSSS" );
        //Obtenemos la �ltima posici�n conocida

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.i(TAG, "Provider Status: dentron del ifff" );
           // return;
        }

/*
        if (!canAccessLocation() ) {
            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        }
        private boolean canAccessLocation() {
            return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
        }
        private boolean hasPermission(String perm) {
            return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
        }
*/
        Log.i(TAG, "Provider Status: fuera del ifff" );
        Location loc =
                locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    //    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener)loc);

        Log.i(TAG, "Provider Status: ppSSS33333333SS" );

        //Mostramos la �ltima posici�n conocida
        mostrarPosicion(loc);

        //Nos registramos para recibir actualizaciones de la posici�n
        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);

                Log.i(TAG, "Provider Status: onLocationChanged");
            }

            public void onProviderDisabled(String provider){
                Log.i(TAG, "Provider Status: Disabled" );
                //lblEstado.setText("Provider OFF");
            }
            public void onProviderEnabled(String provider){
                Log.i(TAG, "Provider Status: ON" );
                //lblEstado.setText("Provider ON ");
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
                Log.i(TAG, "Provider Status: " + status);
             ///   lblEstado.setText("Provider Status: " + status);
            }
        };

        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 10000, 0, locListener);
    }

    private void mostrarPosicion(Location loc) {
        if(loc != null)
        {
            latit.setText(String.valueOf(loc.getLatitude()));
            longi.setText(String.valueOf(loc.getLongitude()));
            //lblPrecision.setText("Precision: " + String.valueOf(loc.getAccuracy()));
            altit.setText(String.valueOf(loc.getAltitude()));

            Log.i(TAG, "!GPSSS posicion: " + loc.getLongitude());
                    //+ String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        }
        else
        {
            Log.i(TAG, "GPSSSSS sindatossssssssssss");
            latit.setText("Latitud: (sin_datos)");
            longi.setText("Longitud: (sin_datos)");
            altit.setText("Altitud: (sin_datos)");
        }
    }



    /////////////////////////////
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "InsertFragmentEVV onActivityCreated un poquito"); //lega
/*
        getLoaderManager().initLoader(0, null, this);
        //modpag.setEnabled(false);
        mModPagAdapter = new SimpleCursorAdapter(
            getContext(),
            android.R.layout.simple_spinner_item,
            null,
            new String[] {ViajesContract.MPagoEntry.MPAG_MP},
            new int[] {android.R.id.text1},
            0);
        mModPagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        modpag.setAdapter(mModPagAdapter);
        */
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case LOADER_MODPAG:
                Log.i(TAG, "InsertFragmentEVV onCreateLoader AAAAA-modopag-AAAAA un poquito");
            //    android.support.v4.content.CursorLoader cursorLoader = new CursorLoader(
                return new CursorLoader(
                    getActivity(),                              // Parent activity context
                    ViajesContract.MPagoEntry.URI_CONTENIDO,    // Table to query
                    ViajesContract.MPagoEntry.TAG_COLUMNS,      // Projection to return
                    null,                                       // No selection clause
                    null,                                       // No selection arguments
                    null);                                      // Default sort order
           // Log.i(TAG, "InsertFragmentEVV onCreateLoader cccccccccccc un poquito"); //aqui llega
       // return cursorLoader;
            case LOADER_MONED:
                Log.i(TAG, "InsertFragmentEVV onCreateLoader AAAA-MONEDAS-AAA un poquito");
          //      cursorLoader = new CursorLoader(
                return new CursorLoader(
                    getActivity(),                              // Parent activity context
                    ViajesContract.MonedasEntry.URI_CONTENIDO,    // Table to query
                    ViajesContract.MonedasEntry.TAG_COLUMNS,      // Projection to return
                    null,                                       // No selection clause
                    null,                                       // No selection arguments
                    null);                                      // Default sort order
        //    Log.i(TAG, "InsertFragmentEVV onCreateLoader cccccccccccc un poquito"); //aqui llega
            default:
                return null;
        }
    }


    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId())
        {
            case LOADER_MODPAG:
                onLoadFinishedModopag(data);
                break;
            case LOADER_MONED:
                onLoadFinishedMonedas(data);
                break;
        }
      //  mModPagAdapter.swapCursor(arg1);
    }
private void onLoadFinishedModopag(Cursor data) {
    // se puede usar para deshabilitar el spinner
    Log.d(TAG, "----onLoadFinishedModopag<> "   );
    mModPagAdapter.swapCursor(data);
    /*
    mProvincesAdapter.swapCursor(data);
		int rowCount = data.getCount();
		Log.d(TAG, "onLoadFinishedProvinces(...) -> Provinces data loaded. " + rowCount + " rows available");
		if (rowCount > 0)
		{
			provinces.setEnabled(true);
		}
		else
		{
			// Both Spinners (provinces and cities) must be disabled
			provinces.setEnabled(false);
			cities.setEnabled(false);
		}
    */
}
    private void onLoadFinishedMonedas(Cursor data)
    {
        Log.d(TAG, "------onLoadFinishedMonedas<> "   );
        mMonedAdapter.swapCursor(data);
        /*
        mCitiesAdapter.swapCursor(data);
        int rowCount = data.getCount();
        Log.d(TAG, "onLoadFinishedCities(...) -> Cities data loaded. " + rowCount + " rows available");
        if (rowCount > 0)
        {
            if (mSavedCityPosition != AdapterView.INVALID_POSITION)
            {
                cities.setSelection(mSavedCityPosition);
                mSavedCityPosition = AdapterView.INVALID_POSITION;
            }
            else
            {
                cities.setSelection(0);
            }
            cities.setEnabled(true);
        }
        else
        {
            cities.setEnabled(false);
        }
        */
    }

    public void onLoaderReset(Loader<Cursor> loader) {
       // mModPagAdapter.swapCursor(null);
        switch (loader.getId())
        {
            case LOADER_MODPAG:
                mMonedAdapter.swapCursor(null);
                break;
            case LOADER_MONED:
                mMonedAdapter.swapCursor(null);
                break;
        }
    }
////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                saveData(); // Guardar datos
                getActivity().finish();
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
        if (view == datae) {
            fecha1 = DATE_DIALOG;
            Log.i(TAG, " onClick andando voy -- 357--");
            datePickerDialog.show();
        }
    }

    private void saveData() {
        // Obtención de valores actuales
        ContentValues values = new ContentValues();
        //values.put(ViajesContract.EventosEntry.E_IDV, idviaje.getText().toString());
        values.put(ViajesContract.EventosEntry.E_IDCGT, idcat);
        values.put(ViajesContract.EventosEntry.E_DATAH, datae.getText().toString());
        values.put(ViajesContract.EventosEntry.E_KMP, kmactual.getText().toString());
        values.put(ViajesContract.EventosEntry.E_NOM, nombre.getText().toString());
        values.put(ViajesContract.EventosEntry.E_DESC, descripcio.getText().toString());
        values.put(ViajesContract.EventosEntry.E_MPAG, id_modopag.toString());
        values.put(ViajesContract.EventosEntry.E_TOT, totaleur.getText().toString());
        values.put(ViajesContract.EventosEntry.E_MON, id_monedas.toString());
        values.put(ViajesContract.EventosEntry.E_VAL, valoracio.getNumStars());
        values.put(ViajesContract.EventosEntry.E_DIR, direccio.getText().toString());
        values.put(ViajesContract.EventosEntry.E_CP, cp.getText().toString());
        values.put(ViajesContract.EventosEntry.E_CIUD, ciudad.getText().toString());
        values.put(ViajesContract.EventosEntry.E_TEL, telef.getText().toString());
        values.put(ViajesContract.EventosEntry.E_MAIL, mail.getText().toString());
        values.put(ViajesContract.EventosEntry.E_WEB, web.getText().toString());
        values.put(ViajesContract.EventosEntry.E_LON, longi.getText().toString());
        values.put(ViajesContract.EventosEntry.E_LAT, latit.getText().toString());
        values.put(ViajesContract.EventosEntry.E_ALT, altit.getText().toString());
        values.put(ViajesContract.EventosEntry.E_COM, comentaris.getText().toString());

        getActivity().getContentResolver().insert(
                ViajesContract.EventosEntry.URI_CONTENIDO,
                values
        );
    }

}
