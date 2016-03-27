package gg.pp.myappviajes.ui;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;

/**
 * Fragmento con formulario de inserción de eventos
 */
public class InsertFragmentEv extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Views del formulario
     */
  private EditText nombre;
  private EditText descripcio;
  private EditText totaleur;
    ///////////////////////////////
    private Spinner modpag, monedas;
    ////////////////////////////////
  private EditText direccio;
  private EditText cp;
    private EditText ciudad;
    private EditText telef;
    private EditText mail;
    private EditText web;
    private EditText longi;
    private EditText latit;
    private EditText altit;
    //private EditText
    // Calendar c = Calendar.getInstance();
    private long id_categ;
    long idcat;

    public static final int LOADER_MODPAG = 1; // Loader identifier for ModPag
    public static final int LOADER_MONED = 2; // Loader identifier for Monedas

    SimpleCursorAdapter mModPagAdapter, mMonedAdapter, sAdapter; // Adapters for both spinners
    public static final String TAG = "Provider";
  //  public static final String[] TAG_COLUMNS = {"_id","mpago"};

    public InsertFragmentEv() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id_categ = getActivity().getIntent().getLongExtra(ViajesContract.CategoriasEntry.CAT_ID, -1);
        setHasOptionsMenu(true);
        Log.i("TAG", "ViajecitosssssssInsertFragmentEV  onCreate un poquitokkkkkkkkkkkkkkkkkkkkkkkkkkkkkk: " + id_categ); // lo tienexxxxxxxxbvn



    }
    ////////////////////////////////////////////////////////////////el spinner



    ////////////////////////////
    /*
    /////////////////////este falla
// Columns from DB to map into the view file
    String[] fromColumns = {
            ViajesContract.MPagoEntry.MPAG_MP    };
    // View IDs to map the columns (fetched above) into
    int[] toViews = {
            android.R.id.text1
    };

    Cursor cursor = getContentResolver().query(
            ViajesContract.MPagoEntry.URI_CONTENIDO,
            null,
            null,
            null,
            ViajesContract.MPagoEntry.MPAG_MP
    );

    SimpleCursorAdapter mModPAdapter = new SimpleCursorAdapter(
            this, // context
            android.R.layout.simple_spinner_item, // layout file
            cursor, // DB cursor
            fromColumns, // data to bind to the UI
            toViews, // views that'll represent the data from `fromColumns`
            0
    );
    mModPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

// Create the list view and bind the adapter
    modpag.setAdapter(mModPAdapter)
*/

    //////////////////////////////////////////////////////////
////////////////////////////////////////////////////////El datepicker lo tengo bien en insertVi
    ///////////////////////////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert, container, false);

        // Obtener views
        nombre = (EditText) view.findViewById(R.id.nom_e);
        descripcio = (EditText) view.findViewById(R.id.descripcio_e);
        totaleur = (EditText) view.findViewById(R.id.total_eur);

        modpag = (Spinner) view.findViewById(R.id.spinner_mod_pag);
        monedas = (Spinner) view.findViewById(R.id.spinner_moned);

        direccio = (EditText) view.findViewById(R.id.direccio);
        cp = (EditText) view.findViewById(R.id.cp);
        ciudad = (EditText) view.findViewById(R.id.ciudad);
        telef = (EditText) view.findViewById(R.id.telef);
        mail = (EditText) view.findViewById(R.id.mail);
        web = (EditText) view.findViewById(R.id.web);
        longi = (EditText) view.findViewById(R.id.longitud);
        latit = (EditText) view.findViewById(R.id.lati);
        altit = (EditText) view.findViewById(R.id.altitud);
        idcat = id_categ;

        ////////////faltan los spinnerssssssssssssssssssssssssss modopag, monedas

        Log.i("TAG", "InsertFragmentTTTTTTTTTTTTTTTTTTTTT onCreateView 140 un poquito: " + id_categ); //Si lo tiene


        return view;

    }
//////////////////////////////////////////////////////////////////////////// desde aquí es nuevo
@Override
public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.i(TAG, "InsertFragmentEVV onActivityCreated un poquito");

   /////////////////////////////////////////////

//////pruebo con este
            //////////////////////////////////////////////////////////////////////////////////////////////
    getLoaderManager().initLoader(1, null, this);
    sAdapter = new SimpleCursorAdapter(
            getContext(),
            android.R.layout.simple_spinner_item,
            null,
            new String[] {ViajesContract.MPagoEntry.MPAG_MP},
            new int[] {android.R.id.text1},
            1);
    sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
 //   Spinner v = (Spinner) findViewById(R.id.modpag);
    modpag.setAdapter(sAdapter);
}

     //   public Loader<? extends Object> onCreateLoader(int id, Bundle args) {
        public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
            Log.i(TAG, "InsertFragmentEVV onCreateLoader AAAAAAAAAAAAAAAAAA un poquito");
        android.support.v4.content.CursorLoader cursorLoader = new CursorLoader(
       // return new cursorLoader(
                getActivity(),
                ViajesContract.MPagoEntry.URI_CONTENIDO,
                ViajesContract.MPagoEntry.TAG_COLUMNS,
                null,
                null,
                null);
            Log.i(TAG, "InsertFragmentEVV onCreateLoader cccccccccccc un poquito");

            return cursorLoader;
    }
  /*
   return new CursorLoader(
                        getActivity(),   // Parent activity context
                        mDataUrl,        // Table to query
                        mProjection,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
        );
        default:
            // An invalid id was passed in
            return null;





public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
    CursorLoader cursorLoader = new CursorLoader(
            this,
            ShopperProvider.CONTENT_URI_TAGS,
            ShopperProvider.TAG_COLUMNS,
            null,
            null,
            null);
    return cursorLoader;
}
public static final String TAG_COLUMN_ID = "_id";
public static final String TAG_COLUMN_TAG = "tagName";
public static final String[] TAG_COLUMNS = {"_id","tagName"};

*/

    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
                sAdapter.swapCursor(arg1);
    }

    public void onLoaderReset(Loader<Cursor> arg0) {
        sAdapter.swapCursor(null);
    }












// hasta aquí<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
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
        values.put(ViajesContract.EventosEntry.E_NOM, nombre.getText().toString());
        values.put(ViajesContract.EventosEntry.E_DATAH, "17/03/2016".toString());
        values.put(ViajesContract.EventosEntry.E_IDCGT, idcat);
        values.put(ViajesContract.EventosEntry.E_DESC, descripcio.getText().toString());
        values.put(ViajesContract.EventosEntry.E_TOT, totaleur.getText().toString());
        values.put(ViajesContract.EventosEntry.E_DIR, direccio.getText().toString());
        values.put(ViajesContract.EventosEntry.E_CP, cp.getText().toString());
        values.put(ViajesContract.EventosEntry.E_CIUD, ciudad.getText().toString());
        values.put(ViajesContract.EventosEntry.E_TEL, telef.getText().toString());
        values.put(ViajesContract.EventosEntry.E_MAIL, mail.getText().toString());
        values.put(ViajesContract.EventosEntry.E_WEB, web.getText().toString());
        values.put(ViajesContract.EventosEntry.E_LON, longi.getText().toString());
        values.put(ViajesContract.EventosEntry.E_LAT, latit.getText().toString());
        values.put(ViajesContract.EventosEntry.E_ALT, altit.getText().toString());


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
