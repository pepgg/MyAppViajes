package gg.pp.myappviajes.ui;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;

import static android.view.View.OnClickListener;

/**
 * Fragment con formulario de inserción de eventos */
public class InsertFragmentEv extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor>, OnClickListener {

    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+1;
    /**
     * Views del formulario      */

    Context mContext;

    private TextView nomcateg;
    private EditText nombre;
    private EditText descripcio;
    private EditText precio;
    private TextView totaleur;
    Spinner modpag, monedas;
    private Button eur;
    private Button datae;
    private Button fotoe;
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
    //private long id_categ;
    long id_categ;
    //long idcat;
    private String id_modopag;
    private String id_monedas;
    private String nomFoto;
    private String idviaje;
    private String id_viaj;
    public Float valorMon;
    public int kmparcial;
    public Float totaleuros;
    boolean botEuros;
    public static final int LOADER_MODPAG = 1; // Loader identifier for ModPag
    public static final int LOADER_MONED = 2; // Loader identifier for Monedas
    /////// Adapters for both spinners:
    SimpleCursorAdapter mModPagAdapter, mMonedAdapter, sAdapter;

    private LocationManager locManager;
    private LocationListener locListener;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "dd-MM-yyyy", Locale.getDefault()); //.FRENCH);//  . .US);
    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;

    private final String ruta_fotos = Environment.getExternalStorageDirectory() +"/Bkviajes/";   //.getAbsolutePath() +"/Bkviajes"; // yo usaré la carpeta de Bkkkk!!!!
    private File file = new File(ruta_fotos);
    Uri uri;
    File mi_foto;



    public static final String TAG = "En InsertFragmentEv: ";
    public InsertFragmentEv() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        id_categ = getActivity().getIntent().getLongExtra(ViajesContract.CategoriasEntry.CAT_ID, -1);
      //  id_categ = getActivity().getIntent().getLongExtra(ViajesContract.CategoriasEntry.CAT_ID, -1);
        id_viaj =  getActivity().getIntent().getStringExtra("idv");

            Log.i(TAG, "ViajecitosssssssInsertFragmentEV  onCreate un jjkkkkkkkkkkkkkkkkkk idCAT: " + id_categ); // lo tienexxxxxxxxbvn
        //idViaje();
            Log.i(TAG, "ViajecitosssssssInsertFragmentEV  onCreate un <<<<<<<<<<<<ID-------Viaje: " + id_viaj); // lo tiene bien ?

        nomFoto = "";
        //Si no existe crea la carpeta donde se guardaran las fotos
        file.mkdirs();
        botEuros = false; // por omisión, no se clica el boton
    }

        @SuppressLint("SimpleDateFormat")
        private String getCode() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault().US);
            String date = dateFormat.format(new Date() );
            String photoCode = "vj_" + idviaje + "_" + date;
                Log.i(TAG, "<<<<<<<<<<ID-------FOTOOOOOO: " + photoCode);
           // en edit_evento pondré un botón para ver fotos tipo galeria
            return photoCode;
        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_insert_ev, container, false);
        // Obtener views
        nomcateg = (TextView) view.findViewById(R.id.categor_input);
        nombre = (EditText) view.findViewById(R.id.nom_e);
        descripcio = (EditText) view.findViewById(R.id.descripcio_e);

        precio = (EditText) view.findViewById(R.id.preu_e);

        modpag = (Spinner) view.findViewById(R.id.spinner_mod_pag);
        totaleur = (TextView) view.findViewById(R.id.total_eur);
        monedas = (Spinner) view.findViewById(R.id.spinner_moned);
        eur = (Button) view.findViewById(R.id.tot_eur);
        datae = (Button) view.findViewById(R.id.fecha_e);
        fotoe = (Button) view.findViewById(R.id.foto_e);

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

      //  idcat = id_categ;

        String midate = (DateFormat.format("dd-MM-yyyy", new java.util.Date()).toString());
            Log.d(TAG, "onCreateView(.EV..) -> fechh222222222222222222a: = " + midate);
        datae.setText(midate);

        ///// EL GPSSSSSSSSSSSSSSSSSSS
       gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //clik en el boton del gps
                Log.i(TAG, "EL GPSSSSSSSSSSSSSSSSSSS"); //Si lo tiene
                        comenzarLocalizacion();
            }
        });



///////<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        eur.setOnClickListener(new View.OnClickListener() {
            //clic al boton EUR >> saca el valor float de precio, le aplica el valmonedas(),
            // lo multiplica por precio y lo escribe en totaleur
            @Override
            public void onClick(View v) { //clik en el boton EUR
                        Log.i(TAG, "EL boton EURRRR"); //Si llega
                        Log.d(TAG, " -> id_monedas: = " + id_monedas);
                        Log.d(TAG, " -> precio: = " + precio.getText().toString());
                Float miprecio = Float.valueOf(precio.getText().toString());
                        Log.d(TAG, " -> precio en float: = " + miprecio);

                valMoneda();
                        Log.d(TAG, " -> valor moneda: = " + valorMon + " ID_MONEDA: " + id_monedas);
                Float preu = Float.valueOf(precio.getText().toString());
                Float enEuros = valorMon * preu;
                        Log.d(TAG, " -> emEuros: = " + enEuros);
                totaleur.setText(String.valueOf(enEuros));
                botEuros = true; // Se ha clicado el boton
                        Log.d(TAG, " -> Clicado el botón: = pasa a " + botEuros);
           }
        });

            Log.i(TAG, "InsertFragmentTT T TT onCreateView 139 un poquito: " + id_categ); //Si lo tiene
        fotoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file = ruta_fotos + getCode() + ".png";
                mi_foto = new File(file);
                try {
                    mi_foto.createNewFile();
                } catch (IOException ex) {
                    Log.e("ERROR ", "Error:" + ex);
                }
                //
                uri = Uri.fromFile(mi_foto);
                nomFoto = getCode() + ".png";
                //Abre la camara para tomar la foto
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Guarda imagen
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                //Retorna a la actividad
                startActivityForResult(cameraIntent, 0);
            }
        });
//////////// los spinnerssssssssssssssssssssssssss modopag, monedas

        //aqui Modopago
        mModPagAdapter = new SimpleCursorAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
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
                getActivity(), android.R.layout.simple_spinner_item,
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
                /*
                    Log.d(TAG, "onItemSelected(.EV..) -> id_monedas: = " + id_monedas);

                    Log.d(TAG, "============ -> val moneda entrADAs: = " + precio.getText().toString());
                Float miprecio = Float.valueOf(precio.getText().toString());
                    Log.d(TAG, "============ -> val moneda entrADAs: = " + miprecio);

                if (miprecio != 0) {
                    valMoneda();
                        Log.d(TAG, "onItemSelected(.EV..) -> val monedas: = " + valorMon + "ID_MONEDA: " + id_monedas);
                        Log.d(TAG, "onItemSelected(.EV..) -> val moneda entrADAs: = " + precio.getText().toString());
                    Float preu = Float.valueOf(precio.getText().toString());
                    Float enEuros = valorMon * preu;
                    totaleur.setText(String.valueOf(enEuros));
                }

                */


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

        datePickerDialog = new DatePickerDialog(getActivity(),
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

        //Si el GPS no está habilitado
  //      if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //  mostrarAvisoGpsDeshabilitado();
            Log.i(TAG, "ElGPSSS  eSta activado ?????????????????¡¡¡¡"); //llega aquí
   //     }
        /////////////////////////////////////// modo simple para Maps de http://stackoverflow.com/questions/32083913/android-gps-requires-access-fine-location-error-even-though-my-manifest-file
/*
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
                gps.getUiSettings().setMyLocationButtonEnabled(true); {
*/

            /////////////////////////////////////
            Log.i(TAG, "ElGPSSS  eStan los permisossss activado ?????????????????¡¡¡¡");
            //Obtenemos una referencia al LocationManager
            Log.i(TAG, "Provider Status: ppSSSSSSSSSS");
            locManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            Log.i(TAG, "Provider Status: ppSSSS22222222SSSSSS");
            //Obtenemos la �ltima posici�n conocida
/*********************
            if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Provider Status: dentron del ifff");
                // return;
*/
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
                Log.i(TAG, "Provider Status: fuera del ifff");
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                //    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener)loc);

                Log.i(TAG, "Provider Status: ppSSS33333333SS");

                //Mostramos la �ltima posici�n conocida
        mostrarPosicion(loc);
                    //sgoliver:
                    //Nos registramos para recibir actualizaciones de la posici�n
                locListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        mostrarPosicion(location);
                        Log.i(TAG, "Provider Status: onLocationChanged");
                    }
                    public void onProviderDisabled(String provider) {
                        Log.i(TAG, "Provider Status: Disabled");
                        //lblEstado.setText("Provider OFF");
                    }
                    public void onProviderEnabled(String provider) {
                        Log.i(TAG, "Provider Status: ON");
                        //lblEstado.setText("Provider ON ");
                    }
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        Log.i(TAG, "Provider Status: " + status);
                        ///   lblEstado.setText("Provider Status: " + status);
                    }
                };
                locManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 10000, 0, locListener);
    }

public Float valMoneda() {
            Log.i(TAG, "En valmoneda() el idmoneda: " + id_monedas);
    String[] projection = new String[] {
            ViajesContract.MonedasEntry.MON_VAL
             };
    Uri vaMonedasUri =  ViajesContract.MonedasEntry.URI_CONTENIDO;
    ContentResolver cr = getActivity().getContentResolver();
        // la consulta
    Cursor cur = cr.query(vaMonedasUri,
            projection, //Columnas a devolver
            ViajesContract.MonedasEntry.MON_ID + " = " + id_monedas,       //Condición de la query
            null,       //Argumentos variables de la query
            null);      //Orden de los resultados
    if (cur.moveToFirst())  //se cumple si la consulta ha dado un resultado con datos
    {
        int valmone = cur.getColumnIndex(ViajesContract.MonedasEntry.MON_VAL);
        do
        {
            valorMon = cur.getFloat(valmone);
                Log.i(TAG, "en valmoneda() el valor de la moneda es: " + valorMon);

        } while (cur.moveToNext());
    }
    return valorMon;
}

    public int kmParcial() {
        Log.i(TAG, "En kmParcial()  " );
        String[] projection = new String[] {
                ViajesContract.EventosEntry.E_KMP
        };
        Uri kmparcialUri =  ViajesContract.EventosEntry.URI_CONTENIDO;
        ContentResolver cr = getActivity().getContentResolver();
        // la consulta
        Cursor cur = cr.query(kmparcialUri,
                projection, //Columnas a devolver
                ViajesContract.EventosEntry.E_KMP + " != " + "''"  ,       //Condición de la query
                null,       //Argumentos variables de la query
                null);      //Orden de los resultados
        if (cur.moveToFirst())  //se cumple si la consulta ha dado un resultado con datos
        {
            int numkm = cur.getCount();
            int kmparci = cur.getColumnIndex(ViajesContract.EventosEntry.E_KMP);
           // do
           // {
            cur.moveToLast();
                kmparcial = cur.getInt(kmparci);
                Log.i(TAG, "en kmParcial() hay " + numkm + " el valor de kmparcial es: " + kmparcial);

           // } while (cur.moveToLast());
        }
        return kmparcial;
    }



//public float precioEur(Float totaleuros) {
    /////// precio es el EdiText(donde se escribe) --------- totaleur el resultado
//esta funcion pretenderia escribir el precio si son euros, sin clicar el botonnnnnn
    //Float miprecio = Float.valueOf(totaleur.getText().toString());
    //if (miprecio != null) { /////////falla porque este float nunca puede ser nulooooo/////***
     //   this.totaleuros = miprecio;
     //} else {
     //   this.totaleuros = Float.valueOf(precio.getText().toString());
    //}

    //return this.totaleuros;
//}



    public String idViaje() {
        String[] projection = new String[] {
                ViajesContract.ViajesEntry.V_ID,
        };
        Uri idViajeUri =  ViajesContract.ViajesEntry.URI_CONTENIDO;
        ContentResolver cr = getActivity().getContentResolver();
// la consulta
        Cursor cur = cr.query(idViajeUri,
                projection, //Columnas a devolver
                ViajesContract.ViajesEntry.V_DATAFI + " LIKE '%'",       //Condición de la query
                null,       //Argumentos variables de la query
                null);      //Orden de los resultados

        if (cur.moveToFirst())

        {
            Log.i(TAG, "ennnnnn IDVIAJE: idviaje: 434 moveToFirst");
            int id_viaj = cur.getColumnIndex(ViajesContract.ViajesEntry.V_ID);
            do
            {
                idviaje = cur.getString(id_viaj) ;
               // Log.i(TAG, "ennnnnn IDVIAJE: idviaje: " + idviaje);
            } while (cur.moveToNext());
        }
        return String.valueOf(idviaje);
    }
  //sgoliver
   private void mostrarPosicion(Location loc){
        if(loc!=null)
        {
        latit.setText(String.valueOf(loc.getLatitude()));
        longi.setText(String.valueOf(loc.getLongitude()));
        //lblPrecision.setText("Precision: " + String.valueOf(loc.getAccuracy()));
        altit.setText(String.valueOf(loc.getAltitude()));

        Log.i(TAG,"!GPSSS posicion: "+loc.getLongitude());
        //+ String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        }
        else
        {
        Log.i(TAG,"GPSSSSS sindatossssssssssss");
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
        if (botEuros == false) {
            Log.d(TAG, "-684-----precio botEuros falso es:<> " + botEuros + precio.getText().toString()  );
            values.put(ViajesContract.EventosEntry.E_TOT, precio.getText().toString());
            // values.put(ViajesContract.EventosEntry.E_TOT, precio.getText().toString());
        }
       // kmParcial();
        if (botEuros == true) {
            Log.d(TAG, "-688-----totaleurrrrrrrrr botEur verdadero es:<> " + botEuros  + precio.getText().toString()  );
            values.put(ViajesContract.EventosEntry.E_TOT, totaleur.getText().toString());
        }

        values.put(ViajesContract.EventosEntry.E_IDV, id_viaj);
        values.put(ViajesContract.EventosEntry.E_IDCGT, id_categ);
        values.put(ViajesContract.EventosEntry.E_DATAH, datae.getText().toString());
        //if (kmactual.getText().toString() == null) {

       // }
        if (kmactual.getText().toString().trim().length()  > 0) {
            Log.d(TAG, "-706-----kmParciaLLLLLLLLLL es: lleno " + kmactual.getText().toString());
            values.put(ViajesContract.EventosEntry.E_KMP, kmactual.getText().toString());
        } else  //if (kmactual.getText().toString().trim().length()  == 0)
        {
            Log.d(TAG, "-710-----kmParciaLLLLLLLLLL es: vacio " + kmactual.getText().toString());
            kmParcial();
            Log.d(TAG, "-710-----kmParciaLLLLLLLLLL es: ULTIMOOO: " + kmparcial);
            values.put(ViajesContract.EventosEntry.E_KMP, String.valueOf(kmparcial));
        }


        values.put(ViajesContract.EventosEntry.E_NOM, nombre.getText().toString());
        values.put(ViajesContract.EventosEntry.E_DESC, descripcio.getText().toString());

        values.put(ViajesContract.EventosEntry.E_MPAG, id_modopag.toString());
        values.put(ViajesContract.EventosEntry.E_MON, id_monedas.toString());
        values.put(ViajesContract.EventosEntry.E_VAL, valoracio.getRating()); //funciona

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
        values.put(ViajesContract.EventosEntry.E_FOT1, nomFoto.toString());

        getActivity().getContentResolver().insert(
                ViajesContract.EventosEntry.URI_CONTENIDO,
                values
        );
    }
}