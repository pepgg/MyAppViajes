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
import static gg.pp.myappviajes.modelo.ViajesContract.CategoriasEntry;
import static gg.pp.myappviajes.modelo.ViajesContract.EventosEntry;
import static gg.pp.myappviajes.modelo.ViajesContract.MPagoEntry;
import static gg.pp.myappviajes.modelo.ViajesContract.MonedasEntry;
import static gg.pp.myappviajes.modelo.ViajesContract.ViajesEntry;

/** * Fragment con formulario de EDIción de eventos */
public class EditFragmentEv extends android.support.v4.app.Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, OnClickListener {

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS    };
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION    };
    private static final int INITIAL_REQUEST = 1337;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 1;
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
    private int id_viaje;
    private int id_categ;
    private String id_modopag;
    private String id_monedas;
    private String nomFoto;
    private int id_viajee;
    private int id_catee;
    private int id_mpage;
    private int id_monde;
    private String id_viaj;
    private String id_cate;
    private String po_cate;
    private String po_viaj;
    private String id_viaj_ed;
    private String id_cate_ed; //e_cgt e_vj e_mp e_mond
    private String id_cate_e;
    private int e_cgt;
    private int e_vj;
    private int e_mp;
    private int e_mond;
    private String nom_catg;
    private String nom_viaj;
    private String nom_modp;
    private String nom_mone;
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
    private final String ruta_fotos = Environment.getExternalStorageDirectory() + "/Bkviajes/";   //.getAbsolutePath() +"/Bkviajes"; // yo usaré la carpeta de Bkkkk!!!!
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
        es_Edit = getActivity().getIntent().getBooleanExtra(esEdit, false);
        Log.i(TAG, "EditFragmentEvvvv  151 onCreate un es_Edit: " + es_Edit); //bien
        if (es_Edit) {
            id_item = getActivity().getIntent().getLongExtra(ViajesContract.EventosEntry.E_ID, -1);
            Log.d(TAG, "--- 154 --onCreate-ES updateeeeeeeeeeeeeee id : " + id_item); //llegsa bien

        } else { //es nuevo
            Log.d(TAG, "---onCreate--157-ES NUEVOOOOOOO ");
            id_viaj = getActivity().getIntent().getStringExtra("idv");
            id_cate = getActivity().getIntent().getStringExtra("idc");
            po_cate = getActivity().getIntent().getStringExtra("poct");
            po_viaj = getActivity().getIntent().getStringExtra("povj");
            Log.d(TAG, "LLEGA al edit id_categ: " + id_cate + " po_categ: " + po_cate + " id_viaje: " + id_viaj + " po_viaje: " + po_viaj);
        }
        nomFoto = "";
        //Si no existe crea la carpeta donde se guardaran las fotos
        file.mkdirs();
        botEuros = false; // por omisión, no se clica el boton
    }

    @SuppressLint("SimpleDateFormat")
    private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault().US);
        String date = dateFormat.format(new Date());
        String photoCode = "vj_" + id_viaj + "_" + date;
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
        maps = (Button) view.findViewById(R.id.map);
        longi = (EditText) view.findViewById(R.id.longitud);
        latit = (EditText) view.findViewById(R.id.latitud);
        altit = (EditText) view.findViewById(R.id.altitud);
        kmactual = (EditText) view.findViewById(R.id.Km_p);
        valoracio = (RatingBar) view.findViewById(R.id.ratingBar);
        comentaris = (EditText) view.findViewById(R.id.coment);

        String midate = (DateFormat.format("dd-MM-yyyy", new Date()).toString());
        Log.d(TAG, "onCreateView(.EV..) -> fechh222222222222222222a: = " + midate);
        datae.setText(midate);
        ///// EL GPS
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

    /////////la fecha
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
        //Si el GPS no está habilitado,  mostrarAvisoGpsDeshabilitado();
        Log.i(TAG, "ElGPSSS  eSta activado ?????????????????¡¡¡¡"); //llega aquí
// modo simple para Maps de http://stackoverflow.com/questions/32083913/android-gps-requires-access-fine-location-error-even-though-my-manifest-file
        Log.i(TAG, "ElGPSSS  eStan los permisossss activado ?????????????????¡¡¡¡");
        //Obtenemos una referencia al LocationManager
        Log.i(TAG, "Provider Status: ppSSSSSSSSSS");
        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Log.i(TAG, "Provider Status: ppSSSS22222222SSSSSS");
        //Obtenemos la �ltima posici�n conocida
        Log.i(TAG, "Provider Status: fuera del ifff");
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.i(TAG, "Provider Status: ppSSS33333333SS");
        //Mostramos la �ltima posici�n conocida
        mostrarPosicion(loc);
//sgoliver:         //Nos registramos para recibir actualizaciones de la posici�n
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

    private void mostrarMapa() {
        String longitu = longi.getText().toString();
        String latitu = latit.getText().toString();
        String datafot = datae.getText().toString();
        String nombr = nombre.getText().toString();

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
        String[] projection = new String[]{
                MonedasEntry.MON_VAL
        };
        Uri vaMonedasUri = MonedasEntry.URI_CONTENIDO;
        ContentResolver cr = getActivity().getContentResolver();
// la consulta
        Cursor cur = cr.query(vaMonedasUri,
                projection, //Columnas a devolver
                MonedasEntry.MON_ID + " = " + id_monedas,       //Condición de la query
                null,       //Argumentos variables de la query
                null);      //Orden de los resultados
        if (cur.moveToFirst()) {
            int valmone = cur.getColumnIndex(MonedasEntry.MON_VAL);
            do {
                valorMon = cur.getFloat(valmone);
                Log.i(TAG, "ennnnnn Status: valmoneda" + valorMon);

            } while (cur.moveToNext());
        }
        if (!cur.isClosed()) {
            cur.close();
        }
        return valorMon;
    }

    //sgoliver
    private void mostrarPosicion(Location loc) {
        if (loc != null) {
            latit.setText(String.valueOf(loc.getLatitude()));
            longi.setText(String.valueOf(loc.getLongitude()));
            altit.setText(String.valueOf(loc.getAltitude()));
            Log.i(TAG, "!GPSSS posicion: " + loc.getLongitude());

        } else {
            Log.i(TAG, "GPSSSSS sindatossssssssssss");
            latit.setText("Latitud: (sin_datos)");
            longi.setText("Longitud: (sin_datos)");
            altit.setText("Altitud: (sin_datos)");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "EditFragmentEvvvv  388 onActivityCreated un es_Edit: " + es_Edit);
        Log.i(TAG, "Viajecitosssssss EditFragmentEv  389 onActivityCreated id: " + id_item); //llega si edittttt
        Log.i(TAG, "Viajecitosssssss EditFragmentEv  389 onActivityCreated id_viaj: " + id_viaj); // null si EDIT SI NUEVO llega bien el _id
        Log.i(TAG, "Viajecitosssssss EditFragmentEv  391 onActivityCreated id_ cate: " + id_cate); //null si edit SI NUEVO llega bien el _id
        // Obtener datos del formulario
        Intent i = getActivity().getIntent(); //creo que esto no lo uso

        getLoaderManager().initLoader(LOADER_NOMCATG, null, this);
        getLoaderManager().initLoader(LOADER_NOMVIAJ, null, this);
        getLoaderManager().initLoader(LOADER_MODPAG, null, this);
        getLoaderManager().initLoader(LOADER_MONED, null, this);

//aqui Viajes
        nomviajAdapter = new SimpleCursorAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                null,
                new String[]{ViajesEntry.COLUMN_NAME},
                new int[]{android.R.id.text1});
            nomviajAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            nomviaj.setAdapter(nomviajAdapter);
            Log.d(TAG, "onActivityCreated(.EV..) 414 id_viaj_ed es: = " + id_viaj_ed); // null si EDIT SI
            Log.d(TAG, "onActivityCreated(.EV..) 415 id_viaj es: = " + id_viaj); // null si EDIT SI
        nomviaj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                Cursor nvj = (Cursor) parent.getItemAtPosition(position);
                id_viaj = nvj.getString(nvj.getColumnIndexOrThrow(ViajesEntry.V_ID));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//aqui Categorias
        nomcatgAdapter = new SimpleCursorAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                null,
                new String[]{CategoriasEntry.COLUMN_NAME},
                new int[]{android.R.id.text1});
            nomcatgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            nomcatg.setAdapter(nomcatgAdapter);
            Log.d(TAG, "onActivityCreated(.EV..) 449 id_cate_ed es: = " + id_cate_ed); // null si EDIT SI
            Log.d(TAG, "onActivityCreated(.EV..) 450 id_cate es: = " + id_cate); //s // null si EDIT SI
        nomcatg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                Cursor nctg = (Cursor) parent.getItemAtPosition(position);
                id_catego = nctg.getString(nctg.getColumnIndexOrThrow(CategoriasEntry.CAT_ID));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//aqui Modopago
        mModPagAdapter = new SimpleCursorAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                null,
                new String[]{MPagoEntry.COLUMN_NAME},
                new int[]{android.R.id.text1});
        mModPagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modpag.setAdapter(mModPagAdapter);
        modpag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                Cursor mdp = (Cursor) parent.getItemAtPosition(position);
                id_modopag = mdp.getString(mdp.getColumnIndexOrThrow(MPagoEntry.MPAG_ID));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//aqui MONEDAS
        mMonedAdapter = new SimpleCursorAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                null,
                new String[]{MonedasEntry.COLUMN_NAME},
                new int[]{android.R.id.text1});
        mMonedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monedas.setAdapter(mMonedAdapter);
        monedas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                Cursor mon = (Cursor) parent.getItemAtPosition(position);
                id_monedas = mon.getString(mon.getColumnIndexOrThrow(MonedasEntry.MON_ID));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        setListeners();

        if (savedInstanceState != null) {
            dateCalendar = Calendar.getInstance();
            if (savedInstanceState.getLong("dateCalendar") != 0)
                dateCalendar.setTime(new Date(savedInstanceState
                        .getLong("dateCalendar")));
        }

        if(!es_Edit) { //es nuevooooooooooooooooooooooooooooooooooooo
            Log.i(TAG, "Viajecitosssssss  498    NUEVITTTTO idcate: "+ id_cate); //si
            Log.i(TAG, "Viajecitosssssss  499    NUEVITTTTO idvije: "+ id_viaj); //si

// CATEG nuevo
            final int n_cgt = Integer.valueOf(id_cate);
            Log.i(TAG, "onActivityCreated NUEVITTTTO 511 antes del setSelection   int ncgt: " + n_cgt); //  llega el id SIIII

// VIAJES nuevo
            final int n_v = Integer.valueOf(id_viaj); ////////   -1;
            Log.i(TAG, "onActivityCreated NUEVITTTTO 515  antes del setSelection int n_v: " + n_v); //  llega SI

        } else  { /////////// ES EDIT ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt
// consulta para comseguir select * where id = id_item
            Uri uried = EventosEntry.URI_CONTENIDO;
            ContentResolver cr = getActivity().getContentResolver();
            final Cursor cur = cr.query(uried, null,
                    EventosEntry.E_ID + " = " + id_item,
                    null, null);
            if (cur.moveToFirst()) { // ha trobat el evento: estic editant un registre fet anteriorment
                Log.i(TAG, " EditFragmentEV  525 nombreEvento: "
                        + cur.getString(cur.getColumnIndex(EventosEntry.E_NOM))); //  llega el nombre del evento

/////hasta aquí la consultita de marrAAAAAAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS**************************
// CATEG edit
                final int e_cgt = Integer.valueOf(cur.getString(cur.getColumnIndex(EventosEntry.E_IDCGT)));
                Log.i(TAG, "Viajecitosssssss EditFragmentEVVVVVVVVVV  578 ESTE   int ecgt: " + e_cgt); //  llega el id SIIII
                Uri uricat = CategoriasEntry.URI_CONTENIDO; //<<< aqui BUSCO el position en categorias
                ContentResolver crCatg = getActivity().getContentResolver();
                final Cursor curct = crCatg.query(uricat, null,
                        CategoriasEntry.CAT_ID + " = " + e_cgt, null, null); // esto me da el id de categorias
                if (curct.moveToFirst()) {
                    nom_catg = curct.getString(curct.getColumnIndex(CategoriasEntry.CAT_CGT));
                    id_cate_e = curct.getString(curct.getColumnIndex(CategoriasEntry.CAT_ID));
                    id_catee = Integer.parseInt(id_cate_e);
                    Log.i(TAG, "778   nom_catg " + nom_catg +"  id_catee: " + id_catee);
                    if (!curct.isClosed()) {
                        curct.close();
                    }
                    Log.i(TAG, "EditFragmentEVVVVVVVVVV  538 id_cgt " + e_cgt + "  nom_catg: " + nom_catg);
// VIAJES edit
                final int e_vj = Integer.valueOf(cur.getString(cur.getColumnIndex(EventosEntry.E_IDV)));
                Log.i(TAG, "Viajecitosssssss EditFragmentEVVVVVVVVVV  534  ESTE int evvj: " + e_vj); //  llega SI
                Uri uriviaj = ViajesEntry.URI_CONTENIDO;
                ContentResolver crViaj = getActivity().getContentResolver();
                final Cursor curvj = crViaj.query(uriviaj, null,
                        ViajesEntry.V_ID + " = " + e_vj, null, null);
                if (curvj.moveToFirst()) {
                    nom_viaj = curvj.getString(curvj.getColumnIndex(ViajesEntry.V_NOM));
                    id_viajee = Integer.parseInt(curvj.getString(curvj.getColumnIndex(ViajesEntry.V_ID)));
                }
                if (!curvj.isClosed()) {
                    curvj.close();
                }
// MODOPAGO edit
                final int e_mp = Integer.valueOf(cur.getString(cur.getColumnIndex(EventosEntry.E_MPAG)));
                 Log.i(TAG, "Viajecitosssssss EditFragment-EV  703 int MMPP: " + e_mp); //  llega el id
                Uri urimdp = MPagoEntry.URI_CONTENIDO;
                ContentResolver crMpag = getActivity().getContentResolver();
                final Cursor curmp = crMpag.query(urimdp, null,
                        MPagoEntry.MPAG_ID + " = " + e_mp, null, null);
                if (curmp.moveToFirst()) {
                    nom_modp = curmp.getString(curmp.getColumnIndex(MPagoEntry.MPAG_MP));
                    id_mpage = Integer.parseInt(curmp.getString(curmp.getColumnIndex(MPagoEntry.MPAG_ID)));
                }
                if (!curmp.isClosed()) {
                    curmp.close();
                }
// MONEDAS edit
                final int e_mond = Integer.valueOf(cur.getString(cur.getColumnIndex(EventosEntry.E_MON)));
                   Log.i(TAG, "Viajecitosssssss EditFragmentEVVVVVVVV 615 int e_mond: " + e_mond); //  llega el id
                Uri urimon = MonedasEntry.URI_CONTENIDO;
                ContentResolver crMon = getActivity().getContentResolver();
                final Cursor curmn = crMon.query(urimon, null,
                        MonedasEntry.MON_ID + " = " + e_mond, null, null);
                if (curmn.moveToFirst()) {
                    nom_mone = curmn.getString(curmn.getColumnIndex(MonedasEntry.MON_NOM));
                    id_monde = Integer.parseInt(curmn.getString(curmn.getColumnIndex(MonedasEntry.MON_ID)));
                }
                if (!curmn.isClosed()) {
                    curmn.close();
                }
                Log.i(TAG, "EditFragmentEVVVVVVVVVV  585 id_moned: " + e_mond + " nom_mone: " + nom_mone);

                nombre.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_NOM)));
                datae.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_DATAH)));
                kmactual.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_KMP)));
                descripcio.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_DESC)));
                float valo = Float.valueOf(cur.getString(cur.getColumnIndex(EventosEntry.E_VAL)));
                valoracio.setRating(valo);
                totaleur.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_TOT)));
                direccio.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_DIR)));
                cp.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_CP)));
                ciudad.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_CIUD)));
                telef.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_TEL)));
                mail.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_MAIL)));
                web.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_WEB)));
                longi.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_LON)));
                latit.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_LAT)));
                altit.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_ALT)));
                comentaris.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_COM)));
                precio.setText(cur.getString(cur.getColumnIndex(EventosEntry.E_PREU)));
            }
            if (!cur.isClosed()){
                cur.close();
            }

        }
    }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (es_Edit) {
            id_item = getActivity().getIntent().getLongExtra(EventosEntry.E_ID, -1);
                Log.d(TAG, "--- 621 --onResuma-ES updateeeeeeeeeeeeeee id : " +  id_item ); //llegsa bien

        } else { //es nuevo
            Log.d(TAG, "---onResuma--619-ES NUEVOOOOOOO "   );

            Log.i(TAG, "NUEVO  onResuma un 623 idCAT: " + id_cate + " idviaje: " + id_viaj + " po_cate: " + po_cate); // lo SI BIenNNN
            final int n_v = Integer.valueOf(id_viaj) -1;
            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< aquí rellenar spiners categ y viaje

            Log.i(TAG, "onResume NUEVO 666  antes del setSelection int n_cgt: " + id_cate);
            /*
            nomcatg.post(new Runnable() {
                @Override
                public void run() {
                    nomcatg.setSelection(Integer.parseInt(po_cate), true);
                }
            });
            */
            Log.i(TAG, "onResume NUEVO 676  despues del setSelection int n_cgt: " + (Integer.parseInt(id_cate)-1));
            Log.i(TAG, "onResume NUEVO 660  antes del setSelection int n_v: " + id_viaj);
            Log.d(TAG, "setSelection 648 viaje nuevo es: = " + po_viaj);
            /*
            nomviaj.post(new Runnable() {
                @Override
                public void run() {
                    nomviaj.setSelection(Integer.parseInt(po_viaj), true);
                    Log.d(TAG, "setSelection 655 viaje nuevo es: = " + Integer.parseInt(po_viaj));
                }
            });
            */
        }
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case LOADER_MODPAG:
                //getLoaderManager().restartLoader(0, null, this);
                Log.i(TAG, "InsertFragmentEVV onCreateLoader AAAAA-modopag-AAAAA un poquito");
                return new CursorLoader(
                    getActivity(),                              // Parent activity context
                    MPagoEntry.URI_CONTENIDO,    // Table to query
                    MPagoEntry.TAG_COLUMNS,      // Projection to return
                    null,                                       // No selection clause
                    null,                                       // No selection arguments
                    null);                                      // Default sort order
            case LOADER_MONED:
                //getLoaderManager().restartLoader(0, null, this);
                Log.i(TAG, "InsertFragmentEVV onCreateLoader AAAA-MONEDAS-AAA un poquito");
                return new CursorLoader(
                    getActivity(),                              // Parent activity context
                    MonedasEntry.URI_CONTENIDO,    // Table to query
                    MonedasEntry.TAG_COLUMNS,      // Projection to return
                    null,                                       // No selection clause
                    null,                                       // No selection arguments
                    null);                                      // No selection arguments
            case LOADER_NOMVIAJ:
                //getLoaderManager().restartLoader(0, null, this);
                Log.i(TAG, "InsertFragmentEVV onCreateLoader AAAAA-nomviaje un poquito");
                String[] projecti = new String[] {
                        ViajesContract.ViajesEntry.V_NOM,
                        ViajesContract.ViajesEntry.V_DATAIN + " as data",
                        ViajesContract.ViajesEntry.V_ID
                };
                return new CursorLoader(
                        getActivity(),                              // Parent activity context
                        ViajesEntry.URI_CONTENIDO,    // Table to query
                        projecti,      // Projection to return
                        null,                                       // No selection clause
                        null,                                       // No selection arguments
                        "data DESC");                                      // Default sort order
            case LOADER_NOMCATG:
                //getLoaderManager().restartLoader(0, null, this);
                Log.i(TAG, " onCreateLoader AAAAA-nomcateg un 629");
                return new CursorLoader(
                        getActivity(),                              // Parent activity context
                        CategoriasEntry.URI_CONTENIDO,    // Table to query
                        CategoriasEntry.TAG_COLUMNS,      // Projection to return
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
                break;
            case LOADER_NOMVIAJ:
                onLoadFinishedNomViaje(data);
                break;
        }
    }
private void onLoadFinishedModopag(Cursor data) {
        Log.d(TAG, "----onLoadFinishedModopag<> "   );
        mModPagAdapter.swapCursor(data);
    if (es_Edit == true) {
        Cursor micursor = mModPagAdapter.getCursor();
        micursor.moveToFirst();
        int row_count = 0;
        int spinner_row = 0;
        while (spinner_row < 0 || row_count < micursor.getCount()){ // loop until end of cursor or the ID is found
            int cursorItemID = micursor.getInt(micursor.getColumnIndexOrThrow(MPagoEntry.MPAG_ID));
            if (id_mpage==cursorItemID){
                spinner_row  = row_count;  //set the spinner row value to the same value as the cursor row
            }
            micursor.moveToNext();
            row_count++;
        }
        modpag.setSelection(spinner_row); //set the selected item in the spinner
    }
}
    private void onLoadFinishedMonedas(Cursor data)  {
        Log.d(TAG, "------onLoadFinishedMonedas<> "   );
        mMonedAdapter.swapCursor(data);
        if (es_Edit == true) {
            Cursor micursor = mMonedAdapter.getCursor();
            micursor.moveToFirst();
            int row_count = 0;
            int spinner_row = 0;
            while (spinner_row < 0 || row_count < micursor.getCount()){ // loop until end of cursor or the ID is found
                int cursorItemID = micursor.getInt(micursor.getColumnIndexOrThrow(MonedasEntry.MON_ID));
                if (id_monde==cursorItemID){
                    spinner_row  = row_count;  //set the spinner row value to the same value as the cursor row
                }
                micursor.moveToNext();
                row_count++;
            }
            monedas.setSelection(spinner_row); //set the selected item in the spinner
        }
    }
    private void onLoadFinishedNomViaje(Cursor data)  {
        Log.d(TAG, "----652--onLoadFinishednomviaje<> "  + id_viaj ); // null si edit
        nomviajAdapter.swapCursor(data);
        // si nuevo es id_viaj, si edit es id_viajee
        if (es_Edit == true) {
            id_viaje = id_viajee;
        } else {
            id_viaje = Integer.parseInt(id_viaj);
        }
            Cursor micursor = nomviajAdapter.getCursor();
            micursor.moveToFirst();
            int row_count = 0;
            int spinner_row = 0;
            while (spinner_row < 0 || row_count < micursor.getCount()){ // loop until end of cursor or the ID is found
                int cursorItemID = micursor.getInt(micursor.getColumnIndexOrThrow(ViajesEntry.V_ID));
             //   if (id_viajee==cursorItemID){
                if (id_viaje==cursorItemID){
                    spinner_row  = row_count;  //set the spinner row value to the same value as the cursor row
                }
                micursor.moveToNext();
                row_count++;
            }
            nomviaj.setSelection(spinner_row); //set the selected item in the spinner

    }
    private void onLoadFinishedNomCateg(Cursor data)  {
        Log.d(TAG, "---656---onLoadFinishednomCateg<> "  + id_cate ); // null si EDIT SI
        nomcatgAdapter.swapCursor(data);
        // si nuevo es id_cate, si edit es id_catee

        if (es_Edit == true) {
            id_categ = id_catee;
        } else {
            id_categ = Integer.parseInt(id_cate);
        }
            Cursor micursor = nomcatgAdapter.getCursor();
            micursor.moveToFirst();
            Log.d(TAG, "783 ---onLoadFinishednomCateg<> idcatee "  + id_categ );
            Log.d(TAG, "784 ---onLoadFinishednomCateg<> micursor.getCount() "  + micursor.getCount());
            int row_count = 0;
            int spinner_row = 0;
            while (spinner_row < 0 || row_count < micursor.getCount()){ // loop until end of cursor or the ID is found
                Log.d(TAG, "1148 ---onLoadFinishednomCateg<> spin row "  + spinner_row );
                int cursorItemID = micursor.getInt(micursor.getColumnIndexOrThrow(CategoriasEntry.CAT_ID));
                Log.d(TAG, "1150 ---onLoadFinishednomCateg<> cursorItemID "  + cursorItemID );
                // if (id_catee==cursorItemID){
                if (id_categ==cursorItemID){
                    spinner_row  = row_count;  //set the spinner row value to the same value as the cursor row
                }
                micursor.moveToNext();
                row_count++;
            }
            nomcatg.setSelection(spinner_row); //set the selected item in the spinner

    }

    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId())
        {
            case LOADER_MODPAG:
                Log.d(TAG, "--804--onLoaderReset Modopag<> "   );
                mModPagAdapter.swapCursor(null);
                break;
            case LOADER_MONED:
                Log.d(TAG, "---808---onLoaderReset Monedas<> "   );
                mMonedAdapter.swapCursor(null);
                break;
            case LOADER_NOMVIAJ:
                Log.d(TAG, "----812--onLoaderReset nomviaje<> "  + id_viaj );
                nomviajAdapter.swapCursor(null);
                break;
            case LOADER_NOMCATG:
                Log.d(TAG, "---816---onLoaderReset nomCateg<> "  + id_cate );
                nomcatgAdapter.swapCursor(null);
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Log.d(TAG, "---onOptionsItemSelected---Es NUEVO 884 "   );

                if (es_Edit) {
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
        // guardar si es edit de un registro anterior
        Uri uri = ContentUris.withAppendedId(EventosEntry.URI_CONTENIDO, id_item);
            Log.i(TAG, "ViajecitosssssssInsertFragmentEv  1053 updateDATA uri: " + uri); //  llega el uri bien, con su id
            Log.i(TAG, "ViajecitosssssssInsertFragmentEv  1054 updateDATA fecha: " + datae.getText().toString()); //lo coge biennnnn
            Log.i(TAG, "ViajecitosssssssInsertFragmentEv  1055 updateDATA nombre: " + nombre.getText().toString());
        Log.d(TAG, "-1056-----updateDATA idviaj es:<> " + id_viaj  + " idcateg " + id_cate  );

        ContentValues values = new ContentValues();
            Log.d(TAG, "-688-----totaleurrrrrrrrr botEur verdadero es:<> " + botEuros  + " " + precio.getText().toString()  );

        values.put(EventosEntry.E_PREU, precio.getText().toString());
        if (botEuros == false){      //no ha clicado el boton
            values.put(EventosEntry.E_TOT, precio.getText().toString());
        } else {                //si ha clicado el boton
            values.put(EventosEntry.E_TOT, totaleur.getText().toString());
        }
        if  (latit.getText().toString() == "Latitud: (sin_datos)"){
            latit.setText("");
            longi.setText("");
            altit.setText("");        }
        values.put(EventosEntry.E_IDV, id_viaj);
        values.put(EventosEntry.E_IDCGT, id_cate_e);
        values.put(EventosEntry.E_DATAH, datae.getText().toString());
        values.put(EventosEntry.E_KMP, kmactual.getText().toString());
        values.put(EventosEntry.E_NOM, nombre.getText().toString());
        values.put(EventosEntry.E_DESC, descripcio.getText().toString());
        values.put(EventosEntry.E_MPAG, id_modopag);
        values.put(EventosEntry.E_MON, id_monedas);
        values.put(EventosEntry.E_VAL, valoracio.getRating());
        values.put(EventosEntry.E_DIR, direccio.getText().toString());
        values.put(EventosEntry.E_CP, cp.getText().toString());
        values.put(EventosEntry.E_CIUD, ciudad.getText().toString());
        values.put(EventosEntry.E_TEL, telef.getText().toString());
        values.put(EventosEntry.E_MAIL, mail.getText().toString());
        values.put(EventosEntry.E_WEB, web.getText().toString());
        values.put(EventosEntry.E_LON, longi.getText().toString());
        values.put(EventosEntry.E_LAT, latit.getText().toString());
        values.put(EventosEntry.E_ALT, altit.getText().toString());
        values.put(EventosEntry.E_COM, comentaris.getText().toString());
        values.put(EventosEntry.E_FOT1, nomFoto);

        // Actualiza datos del Content Provider
        getActivity().getContentResolver().update(
                uri,
                values,
                null,
                null
        );
    }

    private void saveData() {
        // guardar si es nuevo
        Log.i(TAG, " saveData 1106 idviaje: " + id_viaj + " idcateg: " + id_catego + " idmodpag: " + id_modopag + " moned: " + id_monedas);
        ContentValues values = new ContentValues();
        values.put(EventosEntry.E_PREU, precio.getText().toString());
        if (botEuros == false){      //no ha clicado el boton
            values.put(EventosEntry.E_TOT, precio.getText().toString());
            } else {                //si ha clicado el boton
            values.put(EventosEntry.E_TOT, totaleur.getText().toString());
        }

        if  (latit.getText().toString() == "Latitud: (sin_datos)"){
            latit.setText("");
            longi.setText("");
            altit.setText("");
        }
        values.put(EventosEntry.E_IDV, id_viaj);
        values.put(EventosEntry.E_IDCGT, id_catego);
        values.put(EventosEntry.E_DATAH, datae.getText().toString());
        values.put(EventosEntry.E_KMP, kmactual.getText().toString());
        values.put(EventosEntry.E_NOM, nombre.getText().toString());
        values.put(EventosEntry.E_DESC, descripcio.getText().toString());
        values.put(EventosEntry.E_MPAG, id_modopag);
        values.put(EventosEntry.E_MON, id_monedas);
        values.put(EventosEntry.E_VAL, valoracio.getRating());
        values.put(EventosEntry.E_DIR, direccio.getText().toString());
        values.put(EventosEntry.E_CP, cp.getText().toString());
        values.put(EventosEntry.E_CIUD, ciudad.getText().toString());
        values.put(EventosEntry.E_TEL, telef.getText().toString());
        values.put(EventosEntry.E_MAIL, mail.getText().toString());
        values.put(EventosEntry.E_WEB, web.getText().toString());
        values.put(EventosEntry.E_LON, longi.getText().toString());
        values.put(EventosEntry.E_LAT, latit.getText().toString());
        values.put(EventosEntry.E_ALT, altit.getText().toString());
        values.put(EventosEntry.E_COM, comentaris.getText().toString());
        values.put(EventosEntry.E_FOT1, nomFoto);

        getActivity().getContentResolver().insert(
                EventosEntry.URI_CONTENIDO,
                values
        );
    }
}