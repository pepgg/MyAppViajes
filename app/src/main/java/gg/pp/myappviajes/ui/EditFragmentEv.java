package gg.pp.myappviajes.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
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
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;

import static android.view.View.OnClickListener;

/** * Fragment con formulario de EDIción de eventos */
public class EditFragmentEv extends android.support.v4.app.Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, OnClickListener {

    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+1;
    /*     * Views del formulario     */
    Context mContext;
    private TextView nomcateg;
    private EditText nombre;
    private EditText descripcio;
    private EditText precio;
    private TextView totaleur;
    Spinner modpag, monedas, nomviaj, nomcatg;
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
    private Button maps;
    private EditText longi;
    private EditText latit;
    private EditText altit;
    private EditText kmactual;
    private RatingBar valoracio;
    private EditText comentaris;
    public int fecha1;
    static final int DATE_DIALOG = 0;
    Calendar c = Calendar.getInstance();
    private String id_catego;
    private String id_viaje;
    private String id_modopag;
    private String id_monedas;
    private String nomFoto;
    private int idviaje;
    private String id_viaj;
    private String id_cate;
    private String id_viaj_ed;
    private String id_cate_ed;
    public Float valorMon;
    public int kmparcial;
    public Float totaleuros;
    boolean botEuros;
    public static final int LOADER_MODPAG = 1; // Loader identifier for ModPag
    public static final int LOADER_MONED = 2; // Loader identifier for Monedas
    public static final int LOADER_NOMVIAJ = 3;
    public static final int LOADER_NOMCATG = 4;
    SimpleCursorAdapter mModPagAdapter, mMonedAdapter, nomviajAdapter, nomcatgAdapter, sAdapter;
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
    private long id_item; //
    private String esEdit;
    private boolean es_Edit;

    public static final String TAG = "En EditFragmentEv: ";
    public EditFragmentEv() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d(TAG, "--- 131 --onCreate-ES updateeeeeeeeeeeeeee ????? id : " +  id_item );
        es_Edit = getActivity().getIntent().getBooleanExtra(esEdit, false);
            Log.i(TAG, "EditFragmentEvvvv  132 onCreate un es_Edit: " + es_Edit); //bien
        if (es_Edit) {
            id_item = getActivity().getIntent().getLongExtra(ViajesContract.EventosEntry.E_ID, -1);
            Log.d(TAG, "--- 135 --onCreate-ES updateeeeeeeeeeeeeee id : " +  id_item ); //llegsa bien

        } else { //es nuevo
                Log.d(TAG, "---onCreate--136-ES NUEVOOOOOOO "   );
            id_viaj =  getActivity().getIntent().getStringExtra("idv");
            id_cate =  getActivity().getIntent().getStringExtra("idc");
                Log.i(TAG, "NUEVO  onCreate un 144 kkkk idCAT: " + id_cate + " idviaje: " + id_viaj); // lo SI
            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< aquí
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_insert_ev, container, false);
        // Obtener views
        nomviaj = (Spinner) view.findViewById(R.id.spinner_nom_vi);
        nomcatg = (Spinner) view.findViewById(R.id.spinner_catg);
        nombre = (EditText) view.findViewById(R.id.nom_e);
        descripcio = (EditText) view.findViewById(R.id.descripcio_e);
        precio = (EditText)view.findViewById(R.id.preu_e);
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
        maps = (Button) view.findViewById(R.id.map);
        longi = (EditText) view.findViewById(R.id.longitud);
        latit = (EditText) view.findViewById(R.id.latitud);
        altit = (EditText) view.findViewById(R.id.altitud);
        kmactual = (EditText) view.findViewById(R.id.Km_p);
        valoracio = (RatingBar) view.findViewById(R.id.ratingBar);
        comentaris = (EditText) view.findViewById(R.id.coment);

        // <<<<<<<<<<<<aquí

        //
        String midate = (DateFormat.format("dd-MM-yyyy", new Date()).toString());
                Log.d(TAG, "onCreateView(.EV..) -> fechh222222222222222222a: = " + midate);
        datae.setText(midate);
        ///// EL GPSSSSSSSSSSSSSSSSSSS
       gps.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { //clik en el boton del gps
                Log.i(TAG, "EL GPSSSSSSSSSSSSSSSSSSS"); //Si lo tiene
                        comenzarLocalizacion();
            }
        });
        maps.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMapa();
            }
        });
        eur.setOnClickListener(new View.OnClickListener() {
            //clic al boton EUR >> saca el valor float de precio, le aplica el valmonedas(),
            // lo multiplica por precio y lo escribe en totaleur
            @Override
            public void onClick(View v) { //clik en el boton EUR
                        Log.i(TAG, "EL boton EURRRR"); //Si llega
                        Log.d(TAG, " -> id_monedas: = " + id_monedas);
                String m_precio = precio.getText().toString();
                if (m_precio == "") {
                    Toast.makeText(getActivity(), R.string.sin_precio,
                            Toast.LENGTH_LONG).show();
                } else {
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
            }
        });
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
        return view;
    } //onCreateView
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
            //  mostrarAvisoGpsDeshabilitado();
            Log.i(TAG, "ElGPSSS  eSta activado ?????????????????¡¡¡¡"); //llega aquí
// modo simple para Maps de http://stackoverflow.com/questions/32083913/android-gps-requires-access-fine-location-error-even-though-my-manifest-file
            Log.i(TAG, "ElGPSSS  eStan los permisossss activado ?????????????????¡¡¡¡");
            //Obtenemos una referencia al LocationManager
            Log.i(TAG, "Provider Status: ppSSSSSSSSSS");
            locManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            Log.i(TAG, "Provider Status: ppSSSS22222222SSSSSS");
            //Obtenemos la �ltima posici�n conocida
                Log.i(TAG, "Provider Status: fuera del ifff");
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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

private void mostrarMapa(){
    String longitu= longi.getText().toString();
    String latitu= latit.getText().toString();
    String datafot= datae.getText().toString();
    String nombr= nombre.getText().toString();

    Log.i(TAG, "mostrarMapa en 391 longitu: " + longitu);
    Log.i(TAG, "mostrarMapa latitu: " + latitu);
    Log.i(TAG, "mostrarMapa datafot: " + datafot);
    Log.i(TAG, "mostrarMapa nombre: " + nombr);

    Bundle bundle = new Bundle();
    getActivity().startActivity(new Intent(getActivity(), FirstMapActivity.class)
            .putExtra("lg", longitu) // este pasa bien el idcateg
            .putExtra("lt", latitu)
            .putExtra("nb", nombr)
            .putExtra("dt", datafot));
}
public Float valMoneda() {
   // valorMon = Float.valueOf(1);
    Log.i(TAG, "EEEEEEEEEEEENNNNNNNNNNNNN valmoneda el idmoneda: " + id_monedas);
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
    if (cur.moveToFirst())
    {
        int valmone = cur.getColumnIndex(ViajesContract.MonedasEntry.MON_VAL);
        do
        {
            valorMon = cur.getFloat(valmone);
            Log.i(TAG, "ennnnnn Status: valmoneda" + valorMon);

        } while (cur.moveToNext());
    }
    if (!cur.isClosed()){
        cur.close();
    }
    return valorMon;
}

    //sgoliver
   private void mostrarPosicion(Location loc){
        if(loc!=null)
        {
        latit.setText(String.valueOf(loc.getLatitude()));
        longi.setText(String.valueOf(loc.getLongitude()));
        altit.setText(String.valueOf(loc.getAltitude()));
            Log.i(TAG,"!GPSSS posicion: "+loc.getLongitude());

        }        else        {
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
        Log.i(TAG, "EditFragmentEvvvv  380 onActivityCreated un es_Edit: " + es_Edit);
        Log.i(TAG, "Viajecitosssssss EditFragmentEv  379 onActivityCreated id: " + id_item); //llega si
        Log.i(TAG, "Viajecitosssssss EditFragmentEv  380 onActivityCreated id_viaj: " + id_viaj); // null si EDIT
        Log.i(TAG, "Viajecitosssssss EditFragmentEv  381 onActivityCreated id_ cate: " + id_cate); //null si edit
        // Obtener datos del formulario
        Intent i = getActivity().getIntent();
        //aqui NomViaje
        Log.d(TAG, "onActivityCreated(.EV..) 386 id_viaj_ed es: = " + id_viaj_ed);
        Log.d(TAG, "onActivityCreated(.EV..) 387 id_viaj es: = " + id_viaj);
//////////>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        getLoaderManager().initLoader(LOADER_NOMCATG, null, this);
    //    getLoaderManager().restartLoader(LOADER_NOMCATG, null, this);
        getLoaderManager().initLoader(LOADER_NOMVIAJ, null, this);
     // getLoaderManager().restartLoader(LOADER_NOMVIAJ, null, this);

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<aquí


        getLoaderManager().initLoader(LOADER_MODPAG, null, this);
     //   getLoaderManager().restartLoader(LOADER_MODPAG, null, this);
        getLoaderManager().initLoader(LOADER_MONED, null, this);
    //    getLoaderManager().restartLoader(LOADER_MONED, null, this);

        //aqui Viajes
        nomviajAdapter = new SimpleCursorAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                null,
                new String[]{ViajesContract.ViajesEntry.COLUMN_NAME},
                new int[]{android.R.id.text1}, 2);
            nomviajAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            nomviaj.setAdapter(nomviajAdapter);
            Log.d(TAG, "onActivityCreated(.EV..) 395 id_viaj_ed es: = " + id_viaj_ed);
            Log.d(TAG, "onActivityCreated(.EV..) 396 id_viaj es: = " + id_viaj);

            nomviaj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected EV...) 397 id_viaj -> position: " + position + " id: = " + id);
                Cursor nvj = (Cursor) parent.getItemAtPosition(position);
                id_viaje = nvj.getString(nvj.getColumnIndexOrThrow(ViajesContract.ViajesEntry.V_ID));
                Log.d(TAG, "onItemSelected(.EV..) 400 id_viaj es: = " + id_viaj);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected");
            }
        });

        //aqui Categorias
        nomcatgAdapter = new SimpleCursorAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                null,
                new String[]{ViajesContract.CategoriasEntry.COLUMN_NAME},
                new int[]{android.R.id.text1}, 2);
            nomcatgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            nomcatg.setAdapter(nomcatgAdapter);
            Log.d(TAG, "onActivityCreated(.EV..) 415 id_cate_ed es: = " + id_cate_ed);
            Log.d(TAG, "onActivityCreated(.EV..) 416 id_cate es: = " + id_cate); //si
            nomcatg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(TAG, "onItemSelected EV...) 429 id_cate: " + id_cate + " -> position: " + position + " id: = " + id);
                Cursor nctg = (Cursor) parent.getItemAtPosition(position);
              //  Cursor nctg = (Cursor) parent.getItemAtPosition((int) id);
                Log.d(TAG, "onItemSelected(.EV..) 427 id_cate es: = " + nctg);
                id_catego = nctg.getString(nctg.getColumnIndexOrThrow(ViajesContract.CategoriasEntry.CAT_ID)) ;
                    Log.d(TAG, "onItemSelected(.EV..) 423 id_cate es: = " + id_cate); //aquí SALE NULLLLLLLLLLLLLLLL
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected");
            }
        });
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
                    Log.d(TAG, "onItemSelected EV...) 568 modopag -> position: " + position + " id: = " + id);
                Cursor mdp = (Cursor) parent.getItemAtPosition(position);
                id_modopag = mdp.getString(mdp.getColumnIndexOrThrow(ViajesContract.MPagoEntry.MPAG_ID));
                    Log.d(TAG, "onItemSelected(.EV..) 573 -> id_modopag: = " + id_modopag);
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
                    Log.d(TAG, "onItemSelected EV...) 592 monedas -> position: " + position + " id: = " + id);
                Cursor mon = (Cursor) parent.getItemAtPosition(position);
                id_monedas = mon.getString(mon.getColumnIndexOrThrow(ViajesContract.MonedasEntry.MON_ID));
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
//>>>>>>>>>>>>>>>>>>>>los loaders estaban aquí

//>>>>>>>>>>>>>>>>>>>>>><
        if(!es_Edit) { //es nuevooooooooooooooooooooooooooooooooooooo
            Log.i(TAG, "Viajecitosssssss  498    NUEVITTTTO idcate: "+ id_cate); //si
            Log.i(TAG, "Viajecitosssssss  499    NUEVITTTTO idvije: "+ id_viaj); //si
/////////////// CATEG nuevo
            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<aquí

            final int n_cgt = Integer.valueOf(id_cate) -1; /////-1 ; le quitp el -1
            // final int e_cgt = Integer.valueOf(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_IDCGT))) -2  ;
            Log.i(TAG, "onActivityCreated NUEVITTTTO 520 antes del setSelection   int ncgt: " + n_cgt); //  llega el id SIIII

            nomcatg.post(new Runnable() {
                @Override
                public void run() {
                    nomcatg.setSelection(n_cgt);
                    Log.i(TAG, "onActivityCreated NUEVITTTTO 525 despues del setSelection   int ncgt: " + n_cgt);//si
                }
              });

            /*
            Log.i(TAG, "onActivityCreated NUEVO 527  antes del setSelection int n_cgt: " + n_cgt);
            nomcatg.setSelection(n_cgt);
            Log.i(TAG, "onActivityCreated NUEVO 529  despues del setSelection int n_cgt: " + n_cgt);

            */
/////////// VIAJES nuevo
            final int n_v = Integer.valueOf(id_viaj) -1; ////////   -1;
            Log.i(TAG, "onActivityCreated NUEVITTTTO 530  antes del setSelection int n_v: " + n_v); //  llega SI
            nomviaj.post(new Runnable() {
                @Override
                public void run() {
                    nomviaj.setSelection(n_v);
                    Log.i(TAG, "onActivityCreated NUEVITTTTO 535  despues del setSelection int n_v: " + n_v);
                }
            });
            ///

/*
            Log.i(TAG, "onActivityCreated NUEVO 527  antes del setSelection int n_cgt: " + n_cgt);
            nomcatg.setSelection(n_cgt);
            Log.i(TAG, "onActivityCreated NUEVO 529  despues del setSelection int n_cgt: " + n_cgt);
            Log.i(TAG, "onActivityCreated NUEVO 540  antes del setSelection int n_v: " + n_v);
            nomviaj.setSelection(n_v);
            Log.i(TAG, "onActivityCreated NUEVO 542  despues del setSelection int n_v: " + n_v);
            */

        } else  { /////////// ES EDIT ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt

                 //aquí la consulta para comseguir select * where id = id_item
            Uri urimn = ViajesContract.EventosEntry.URI_CONTENIDO;
            ContentResolver cr = getActivity().getContentResolver();
            final Cursor cur = cr.query(urimn, null,
                    ViajesContract.EventosEntry.E_ID + " = " + id_item,
                    null, null);
            if (cur.moveToFirst()) { // ha trobat el evento: estic editant un registre fet anteriorment
                Log.i(TAG, "Viajecitosssssss EditFragmentEV  653 nombre: "
                        + cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_NOM))); //  llega el nombre

                nombre.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_NOM)));
                datae.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_DATAH)));
                kmactual.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_KMP)));
                descripcio.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_DESC)));
//////////////// CATEG edit
                final int e_cgt = Integer.valueOf(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_IDCGT))) -1 ;
                // final int n_v = Integer.valueOf(id_viaj) -1; Esto funciona para NUEVOOOO
                Log.i(TAG, "Viajecitosssssss EditFragmentEVVVVVVVVVV  579 ESTE   int ecgt: " + e_cgt); //  llega el id SIIII
                nomcatg.post(new Runnable() {
                    @Override
                    public void run() {
                        nomcatg.setSelection(e_cgt);
                        Log.i(TAG, " EditFragmentEVVVVVVVVVV  585 ESTE   int ecgt: " + e_cgt);
                    }
                });
/////////// VIAJES edit
                final int e_vj = Integer.valueOf(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_IDV))) -1;
                Log.i(TAG, "Viajecitosssssss EditFragmentEVVVVVVVVVV  534  ESTE int evvj: " + e_vj); //  llega? SI
                nomviaj.post(new Runnable() {
                    @Override
                    public void run() {
                        nomviaj.setSelection(e_vj);
                    }
                });
////////// MODOPAGO edit
                final int mp = Integer.valueOf(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_MPAG))) - 1;
                Log.i(TAG, "Viajecitosssssss EditFragment-EV  703 int MMPP: " + mp); //  llega el id
                Uri urimp = ViajesContract.MPagoEntry.URI_CONTENIDO;
                ContentResolver crsm = getActivity().getContentResolver();
                Cursor cursmp = crsm.query(urimp, null,
                        ViajesContract.MPagoEntry.MPAG_MP + " = " + mp,
                        null, null);
                modpag.post(new Runnable() {
                    @Override
                    public void run() {
                        modpag.setSelection(mp);
                    }
                });
                Log.i(TAG, "Viajecitosssssss EditFragment-EV<<<<<<<  716 int modPag: " + Integer.valueOf(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_MPAG))));

////////////// MONEDAS edit
                final int mond = Integer.valueOf(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_MON))) - 1;
                    Log.i(TAG, "Viajecitosssssss EditFragmentEVVVVVVVV 615 int mond: " + mond); //  llega el id
                monedas.post(new Runnable() {
                    @Override
                    public void run() {
                        monedas.setSelection(mond);
                    }
                });

                float valo = Float.valueOf(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_VAL)));
                valoracio.setRating(valo);
                totaleur.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_TOT)));
                direccio.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_DIR)));
                cp.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_CP)));
                ciudad.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_CIUD)));
                telef.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_TEL)));
                mail.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_MAIL)));
                web.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_WEB)));
                longi.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_LON)));
                latit.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_LAT)));
                altit.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_ALT)));
                comentaris.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_COM)));
                precio.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_PREU)));
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (es_Edit) {
            id_item = getActivity().getIntent().getLongExtra(ViajesContract.EventosEntry.E_ID, -1);
                Log.d(TAG, "--- 621 --onResuma-ES updateeeeeeeeeeeeeee id : " +  id_item ); //llegsa bien


          //  updateViewEd(id_item);  // Actualzar la vista con los datos de la actividad
        } else { //es nuevo
            Log.d(TAG, "---onCreate--136-ES NUEVOOOOOOO "   );
            id_viaj =  getActivity().getIntent().getStringExtra("idv");
            id_cate =  getActivity().getIntent().getStringExtra("idc");
            Log.i(TAG, "NUEVO  onCreate un 144 kkkk idCAT: " + id_cate + " idviaje: " + id_viaj); // lo SI

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< aquí
  /*
            Log.i(TAG, "onResume NUEVO 666  antes del setSelection int n_cgt: " + id_cate);
            nomcatg.post(new Runnable() {
                @Override
                public void run() {
                    nomcatg.setSelection(Integer.parseInt(id_cate));
                }
            });
            Log.i(TAG, "onResume NUEVO 676  despues del setSelection int n_cgt: " + id_cate);
            Log.i(TAG, "onResume NUEVO 660  antes del setSelection int n_v: " + id_viaj);
            nomviaj.post(new Runnable() {
                @Override
                public void run() {
                    nomviaj.setSelection(Integer.parseInt(id_viaj));
                }
            });
            Log.i(TAG, "onResume NUEVO 686  despues del setSelection int n_v: " + id_viaj);
*/

        }
    }
    /*
    private void updateViewEd(long id_item) {
        Log.i("TAG", "DetailFragmentTTTTTTTTupdateview Ed query uri un poquito: " + id_item);//
        /// esto estaba en el On Activity createddddddddddddddddddddddddd
      //  Uri urimn = ViajesContract.EventosEntry.URI_CONTENIDO;
      //  ContentResolver cr = getActivity().getContentResolver();
      //  final Cursor cur = cr.query(urimn, null,
      //          ViajesContract.EventosEntry.E_ID + " = " + id_item,
      //          null, null);
      //  if (cur.moveToFirst()) { // ha trobat el evento: estic editant un registre fet anteriorment
      //      Log.i(TAG, "Viajecitosssssss EditFragmentEV  653 nombre: "
      //              + cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_NOM))); //  llega el nombre
//
      //      nombre.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_NOM)));
        ///
        Uri uri = ContentUris.withAppendedId(ViajesContract.EventosEntry.URI_CONTENIDO, id_item);
        Cursor c = getActivity().getContentResolver().query(
                uri,
                null, null, null, null);

        if (!c.moveToFirst())
            return;
       // String S_id_viaje = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_IDV));
       // String S_id_catego = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_IDCGT));
        final long e_cgt = Long.valueOf(c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_IDCGT))) -1 ;
        final long e_vj = Long.valueOf(c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_IDV))) -1 ;
        final long e_mond = Long.valueOf(c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_MON))) -1 ;
        final long e_mp = Long.valueOf(c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_MPAG))) -1 ;
        String S_datae = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_DATAH));
        String S_kmactual = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_KMP));
        String S_nombre = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_NOM));
        String S_descripcio = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_DESC));
    //    String S_id_modopag = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_MPAG));
     //   String S_id_monedas = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_NOM));
        String S_valoracio = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_VAL)); //funciona??
        String S_direccio = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_DIR));
        String S_cp = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_CP));
        String S_ciudad = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_CIUD));
        String S_telef = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_TEL));
        String S_mail = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_MAIL));
        String S_web = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_WEB));
        String S_longi = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_LON));
        String S_latit = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_LAT));
        String S_altit = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_ALT));
        String S_comentaris = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_COM));
        String S_nomFoto1 = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_FOT1));
        String S_totaleur = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_TOT));
        String S_precio = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_PREU));
        String S_kmp = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_KMP));

        nombre.setText(S_nombre);
        datae.setText(S_datae);
        kmactual.setText(S_kmactual);
        descripcio.setText(S_descripcio);
        valoracio.setRating(Float.parseFloat(S_valoracio));
        totaleur.setText(S_totaleur);
        direccio.setText(S_direccio);
        cp.setText(S_cp);
        ciudad.setText(S_ciudad);
        telef.setText(S_telef);
        mail.setText(S_mail);
        web.setText(S_web);
        longi.setText(S_longi);
        latit.setText(S_latit);
        altit.setText(S_altit);
        comentaris.setText(S_comentaris);
        precio.setText(S_precio);
       // nomcatg.setText(n_cgt); e_vj mond mp

        nomcatg.post(new Runnable() {
            @Override
            public void run() {
                nomcatg.setSelection((int) e_cgt);
                Log.i(TAG, " EditFragmentEVVVVVVVVVV  528 ESTE   int ecgt: " + e_cgt);
            }
        });
             //   nomviaj.setText(S_id_viaje);
        nomcatg.post(new Runnable() {
            @Override
            public void run() {
                nomcatg.setSelection((int) e_vj);
                Log.i(TAG, " EditFragmentEVVVVVVVVVV  528 ESTE   int e_vij: " + e_vj);
            }
        });

        modpag.post(new Runnable() {
            @Override
            public void run() {
                modpag.setSelection((int) e_mp);
                Log.i(TAG, " EditFragmentEVVVVVVVVVV  528 ESTE   int modpag: " + e_mp);
            }
        });
        monedas.post(new Runnable() {
            @Override
            public void run() {
                monedas.setSelection((int) e_mond);
                Log.i(TAG, " EditFragmentEVVVVVVVVVV  528 ESTE   int moned: " + e_mond);
            }
        });

        fotoe.setText(S_nomFoto1);
     //   modpag.setText(S_id_modopag);
     //   monedas.setText(S_id_monedas);
        kmactual.setText(S_kmp);
        //nomviaj modpag nomcatg monedas

        c.close(); // Liberar memoria del cursor
    }
    */
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case LOADER_MODPAG:
                //getLoaderManager().restartLoader(0, null, this);
                Log.i(TAG, "InsertFragmentEVV onCreateLoader AAAAA-modopag-AAAAA un poquito");
                return new CursorLoader(
                    getActivity(),                              // Parent activity context
                    ViajesContract.MPagoEntry.URI_CONTENIDO,    // Table to query
                    ViajesContract.MPagoEntry.TAG_COLUMNS,      // Projection to return
                    null,                                       // No selection clause
                    null,                                       // No selection arguments
                    null);                                      // Default sort order
            case LOADER_MONED:
                //getLoaderManager().restartLoader(0, null, this);
                Log.i(TAG, "InsertFragmentEVV onCreateLoader AAAA-MONEDAS-AAA un poquito");
                return new CursorLoader(
                    getActivity(),                              // Parent activity context
                    ViajesContract.MonedasEntry.URI_CONTENIDO,    // Table to query
                    ViajesContract.MonedasEntry.TAG_COLUMNS,      // Projection to return
                    null,                                       // No selection clause
                    null,                                       // No selection arguments
                    null);                                      // No selection arguments
            case LOADER_NOMVIAJ:
                //getLoaderManager().restartLoader(0, null, this);
                Log.i(TAG, "InsertFragmentEVV onCreateLoader AAAAA-nomviaje un poquito");
                return new CursorLoader(
                        getActivity(),                              // Parent activity context
                        ViajesContract.ViajesEntry.URI_CONTENIDO,    // Table to query
                        ViajesContract.ViajesEntry.TAG_COLUMNS,      // Projection to return
                        null,                                       // No selection clause
                        null,                                       // No selection arguments
                        null);                                      // Default sort order
            case LOADER_NOMCATG:
                //getLoaderManager().restartLoader(0, null, this);
                Log.i(TAG, " onCreateLoader AAAAA-nomcateg un 629");
                return new CursorLoader(
                        getActivity(),                              // Parent activity context
                        ViajesContract.CategoriasEntry.URI_CONTENIDO,    // Table to query
                      //  ViajesContract.CategoriasEntry.TAG_COLUMNS,      // Projection to return
                        null,
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
            case LOADER_MODPAG:
                onLoadFinishedModopag(data);
                break;
            case LOADER_MONED:
                onLoadFinishedMonedas(data);
                break;
            case LOADER_NOMCATG:
                onLoadFinishedNomCateg(data);
                nomcatgAdapter.swapCursor(data);
                break;
            case LOADER_NOMVIAJ:
                onLoadFinishedNomViaje(data);
                break;
        }
    }
private void onLoadFinishedModopag(Cursor data) {
        Log.d(TAG, "----onLoadFinishedModopag<> "   );
    mModPagAdapter.swapCursor(data);
}
    private void onLoadFinishedMonedas(Cursor data)  {
        Log.d(TAG, "------onLoadFinishedMonedas<> "   );
        mMonedAdapter.swapCursor(data);
    }
    private void onLoadFinishedNomViaje(Cursor data)  {
        Log.d(TAG, "----652--onLoadFinishednomviaje<> "  + id_viaj );
        nomviajAdapter.swapCursor(data);
    }
    private void onLoadFinishedNomCateg(Cursor data)  {
        Log.d(TAG, "---656---onLoadFinishednomCateg<> "  + id_cate );
        nomcatgAdapter.swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId())
        {
            case LOADER_MODPAG:
                mModPagAdapter.swapCursor(null);
                break;
            case LOADER_MONED:
                mMonedAdapter.swapCursor(null);
                break;
            case LOADER_NOMVIAJ:
                nomviajAdapter.swapCursor(null);
                break;
            case LOADER_NOMCATG:
                nomcatgAdapter.swapCursor(null);
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Log.d(TAG, "------Es NU<<<<*****case android.R.id.home***** 748 "   );
                if (es_Edit) {
                    //if (id_item > 0) {
                    Log.d(TAG, "------case updateeeeeeeeeeeeeee 750 id : " +  id_item ); //llegsa bien
                    updateData();
                    getActivity().finish();
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));

                } else {
                    Log.d(TAG, "------Es NUEVOOOOooooooooooooooo 754 "   );
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
        if (view == datae) {
            fecha1 = DATE_DIALOG;
            Log.i(TAG, " onClick andando voy -- 704--");
            datePickerDialog.show();
        }
    }
    private void updateData() {
        Uri uri = ContentUris.withAppendedId(ViajesContract.EventosEntry.URI_CONTENIDO, id_item);
            Log.i(TAG, "ViajecitosssssssInsertFragmentEv  742 updateDATA uri: " + uri); //  llega el uri bien, con su id
            Log.i(TAG, "ViajecitosssssssInsertFragmentEv  743 updateDATA fecha: " + datae.getText().toString()); //lo coge biennnnn
            Log.i(TAG, "ViajecitosssssssInsertFragmentEv  744 updateDATA nombre: " + nombre.getText().toString());
        ContentValues values = new ContentValues();
            Log.d(TAG, "-688-----totaleurrrrrrrrr botEur verdadero es:<> " + botEuros  + " " + precio.getText().toString()  );


       // values.put(ViajesContract.EventosEntry.E_TOT, totaleur.getText().toString());
        values.put(ViajesContract.EventosEntry.E_PREU, precio.getText().toString());

        if (botEuros == false){      //no ha clicado el boton
            values.put(ViajesContract.EventosEntry.E_TOT, precio.getText().toString());
        } else {                //si ha clicado el boton
            values.put(ViajesContract.EventosEntry.E_TOT, totaleur.getText().toString());
        }


        if  (latit.getText().toString() == "Latitud: (sin_datos)"){
            latit.setText("");
            longi.setText("");
            altit.setText("");        }
        values.put(ViajesContract.EventosEntry.E_IDV, id_viaje.toString());
        values.put(ViajesContract.EventosEntry.E_IDCGT, id_catego.toString());
        values.put(ViajesContract.EventosEntry.E_DATAH, datae.getText().toString());
        values.put(ViajesContract.EventosEntry.E_KMP, kmactual.getText().toString());
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

        // Actualiza datos del Content Provider
        getActivity().getContentResolver().update(
                uri,
                values,
                null,
                null
        );
    }

    private void saveData() {
        // guardar los valores actuales
        Log.i(TAG, " saveData 972 idviaje: " + id_viaje + " idcateg: " + id_catego + " idmodpag: " + id_modopag + " moned: " + id_monedas);
        ContentValues values = new ContentValues();
        values.put(ViajesContract.EventosEntry.E_PREU, precio.getText().toString());
        if (botEuros == false){      //no ha clicado el boton
            values.put(ViajesContract.EventosEntry.E_TOT, precio.getText().toString());
            } else {                //si ha clicado el boton
            values.put(ViajesContract.EventosEntry.E_TOT, totaleur.getText().toString());
        }

        //values.put(ViajesContract.EventosEntry.E_PREU, precio.getText().toString());
        if  (latit.getText().toString() == "Latitud: (sin_datos)"){
            latit.setText("");
            longi.setText("");
            altit.setText("");
        }
        values.put(ViajesContract.EventosEntry.E_IDV, id_viaje.toString());
        values.put(ViajesContract.EventosEntry.E_IDCGT, id_catego.toString());
        values.put(ViajesContract.EventosEntry.E_DATAH, datae.getText().toString());
        values.put(ViajesContract.EventosEntry.E_KMP, kmactual.getText().toString());
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