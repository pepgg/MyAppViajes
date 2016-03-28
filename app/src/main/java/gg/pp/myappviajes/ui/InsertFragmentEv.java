package gg.pp.myappviajes.ui;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
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
    @Override
    public void onClick(View v) {

    }
    //   public class InsertFragmentEv extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Views del formulario
     */
    private TextView nomcateg;
  private EditText nombre;
  private EditText descripcio;
  private EditText totaleur;
    ///////////////////////////////
    private Spinner modpag, monedas;
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
    private EditText longi;
    private EditText latit;
    private EditText altit;
    private EditText kmactual;
    private RatingBar valoracio;
    private EditText comentaris;
    static final int DATE_DIALOG = 0;
    Calendar c = Calendar.getInstance();
    private long id_categ;
    long idcat;
    private String id_modopag;

    public static final int LOADER_MODPAG = 1; // Loader identifier for ModPag
    public static final int LOADER_MONED = 2; // Loader identifier for Monedas

    SimpleCursorAdapter mModPagAdapter, mMonedAdapter, sAdapter; // Adapters for both spinners
    public static final String TAG = "En InsertFragmentEv: ";
  //  public static final String[] TAG_COLUMNS = {"_id","mpago"};
  private static final SimpleDateFormat formatter = new SimpleDateFormat(
          "dd-MM-yyyy", Locale.getDefault()); //.FRENCH);//  . .US);
    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;


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

        longi = (EditText) view.findViewById(R.id.longitud);
        latit = (EditText) view.findViewById(R.id.latitud);
        altit = (EditText) view.findViewById(R.id.altitud);
        kmactual = (EditText) view.findViewById(R.id.Km_p);

        valoracio = (RatingBar) view.findViewById(R.id.ratingBar);
        comentaris = (EditText) view.findViewById(R.id.coment);
        idcat = id_categ;

        Log.i(TAG, "InsertFragmentTT T TT onCreateView 139 un poquito: " + id_categ); //Si lo tiene
//////////// los spinnerssssssssssssssssssssssssss modopag, monedas
        //   mTipoV.setEnabled(false);  //TODO:<<<<<<<>>>>>>>>>>>>>tengo que arrglar esto de los enabled
        mModPagAdapter = new SimpleCursorAdapter(
                getContext(), android.R.layout.simple_spinner_item,
                null,
                new String[] { ViajesContract.TipoVEntry.COLUMN_NAME },
                new int[] { android.R.id.text1 }, 2);
        mModPagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modpag.setAdapter(mModPagAdapter);
        modpag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected EV...) -> position: " + position + "id: = " + id);

                Cursor tipv = (Cursor) parent.getItemAtPosition(position);
                id_modopag = tipv.getString(tipv.getColumnIndexOrThrow(ViajesContract.MPagoEntry.MPAG_ID));

                Log.d(TAG, "onItemSelected(.EV..) -> id_modopag: = " + id_modopag);
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
                    Log.i(TAG, " antes delllll datae change_data__ " + view);
                    datae.setText(formatter.format(dateCalendar
                            .getTime()));
                }
            }, newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH));
}
////////
@Override
public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.i(TAG, "InsertFragmentEVV onActivityCreated un poquito"); //lega

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


}

     //   public Loader<? extends Object> onCreateLoader(int id, Bundle args) {
        public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
            Log.i(TAG, "InsertFragmentEVV onCreateLoader AAAAAAAAAAAAAAAAAA un poquito");
        android.support.v4.content.CursorLoader cursorLoader = new CursorLoader(

                getActivity(),                              // Parent activity context
                ViajesContract.MPagoEntry.URI_CONTENIDO,    // Table to query
                ViajesContract.MPagoEntry.TAG_COLUMNS,      // Projection to return
                null,                                       // No selection clause
                null,                                       // No selection arguments
                null);                                      // Default sort order
            Log.i(TAG, "InsertFragmentEVV onCreateLoader cccccccccccc un poquito"); //aqui llega

            return cursorLoader;
    }

    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        mModPagAdapter.swapCursor(arg1);
    }

    public void onLoaderReset(Loader<Cursor> arg0) {
        mModPagAdapter.swapCursor(null);
    }
///////////////////////


    //////////////////////////////////////////////
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

/*
    Calendar c = Calendar.getInstance();
    System.out.println("Current time => " + c.getTime());

    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c.getTime());
*/
    private void saveData() {
        // Obtención de valores actuales
        ContentValues values = new ContentValues();
        //values.put(ViajesContract.EventosEntry.E_IDV, idviaje.getText().toString());
        values.put(ViajesContract.EventosEntry.E_IDCGT, idcat);
        values.put(ViajesContract.EventosEntry.E_DATAH, datae.toString());
        values.put(ViajesContract.EventosEntry.E_KMP, kmactual.getText().toString());
        values.put(ViajesContract.EventosEntry.E_NOM, nombre.getText().toString());
        values.put(ViajesContract.EventosEntry.E_DESC, descripcio.getText().toString());
        values.put(ViajesContract.EventosEntry.E_MPAG, id_modopag);
        values.put(ViajesContract.EventosEntry.E_TOT, totaleur.getText().toString());
     //   values.put(ViajesContract.EventosEntry.E_MON, monedas.getDropDownWidth());
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
/*
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

*/
}
