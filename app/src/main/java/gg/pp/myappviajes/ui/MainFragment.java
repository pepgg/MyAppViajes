package gg.pp.myappviajes.ui;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import gg.pp.myappviajes.ActivitiesAdapterCt;
import gg.pp.myappviajes.R;
import gg.pp.myappviajes.exportimport.Export;
import gg.pp.myappviajes.exportimport.ExportCSV;
import gg.pp.myappviajes.exportimport.Import;
import gg.pp.myappviajes.exportimport.ImportCSV;
import gg.pp.myappviajes.modelo.ViajesContract;

/**
 * Created by pepe on 5/03/16.  ListFragment FragmentActivity
 */
public class MainFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "En MainFragment: ";

    private ActivitiesAdapterCt adaptador;
    SimpleCursorAdapter viajeActAdapter ;
    private static final int V_CREA = 0;
    private Spinner nomViaje;
    private TextView totalKm;
    private TextView totalGast;
    private Button btnViajes;
    private String nombreViaje;
    private int idviaje;
    private String id_viaje;
    private String po_viaje;
    private String id_categ;
    private String po_categ;
    private long idcate;
    private Float totaGasto;
    private int kmini;
    private int kmactu;
    private int kmparcial;
    private String strKmp;
    private String strTotalGast;
    public static final String nomTabla = null;
    public static final String nomTabl = null;
    public static final int LOADER_MCATEG = 1; // Loader identifier for Categorias
    public static final int LOADER_MVIAJE = 2; // Loader identifier for Viajes

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Habilitar al fragmento para contribuir en la action bar
        setHasOptionsMenu(true);         /////añado esto por la barra de titulo y actionbar
     //   nombrViaje();
       //     Log.i(TAG, "MainFragmentito OnCrete 61 NOMBRE VIAJE " + nombreViaje); //si este está bien
      //  viajesAct();
        //    Log.i(TAG, "MainFragmentito OnCrete 63 IDDDD VIAJE " + idviaje); //sale 1 en lugar de 2
        totaGast();
            Log.i(TAG, "MainFragmentito OnCrete 65 TOTAL GASTO " + totaGasto);// este está bien
        totaKM();
    }

    private void totaKM() {
        //primero busco el km inici en kminici() L 201
        kminici();
                Log.i(TAG, "ennnnnn TOTAKM 77 INICI: " + kmini); // aquí llega bien
        //luego busco el km actual del último evento en kmactual()
        kmactual();
                Log.i(TAG, "ennnnnn TOTAKM 80 ACTUAL: " + kmactu);
        kmparcial = (kmactu - kmini);
                Log.i(TAG, "ennnnnn TOTAKM 82 PARCIAL: " + kmparcial);
        strKmp = Integer.toString(kmparcial);
                    Log.i(TAG, "ennnnnn TOTAKM 85 de momentoooooooooooo: " + strKmp);
    }
  //  private Float totaGast() {
    private String totaGast() {
        String[] projection = new String[] {"sum(" + ViajesContract.EventosEntry.E_TOT + ")"
        };
        // la consulta
        Cursor cursar = getActivity().getContentResolver().query(
                ViajesContract.EventosEntry.URI_CONTENIDO,
                projection, //Columnas a devolver
                ViajesContract.EventosEntry.E_IDV + " = " + id_viaje,   //Condición de la query
                null,       //Argumentos variables de la query
                null);      //Orden de los resultados

        cursar.moveToFirst();

           // int to_gast = cursar.getInt(0);

        totaGasto = cursar.getFloat(0);
        //  totaGasto = Float.valueOf(to_gast);

       strTotalGast = Float.toString(totaGasto);
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.FRANCE)     ;
        strTotalGast = formatter.format(totaGasto);
        /////////////
        Log.i(TAG, "ennnnnn 134 TOTAGAST: "+ strTotalGast + "totagssto: " + totaGasto);

        if (!cursar.isClosed()){
            cursar.close();
        }
           // return Float.valueOf(totaGasto);
        return strTotalGast;
    }
    public String nombrViaje() {
        Log.i(TAG, "ennnnnn 123 nombrViaje: ");
        String[] projection = new String[] {
                ViajesContract.ViajesEntry.V_NOM,
        };
        Uri nomViajeUri =  ViajesContract.ViajesEntry.URI_CONTENIDO;
        ContentResolver cr = getActivity().getContentResolver();
// la consulta
        Cursor cu = cr.query(nomViajeUri,
                projection, //Columnas a devolver
                ViajesContract.ViajesEntry.V_DATAFI + " LIKE '%'",       //Condición de la query
                null,       //Argumentos variables de la query
                null);      //Orden de los resultados

        if (cu.moveToFirst())
        {
            Log.i(TAG, "ennnnnn IDVIAJE: idviaje: 138 moveToFirst");
            int id_viaj = cu.getColumnIndex(ViajesContract.ViajesEntry.V_NOM);
            nombreViaje = cu.getString(id_viaj) ; //
            Log.i(TAG, "ennnnnn 155 nombreviaje: "+ nombreViaje);
        }
        if (!cu.isClosed()){
            cu.close();
        }
        return nombreViaje;
    }
    public String viajesAct() {
        Log.i(TAG, "ennnnnn 147 viajesAct: ");
        String[] projection = new String[] {
                ViajesContract.ViajesEntry.V_ID
        };
        Uri idViajeUri =  ViajesContract.ViajesEntry.URI_CONTENIDO;
        ContentResolver cr = getActivity().getContentResolver();
// la consulta
        Cursor c = cr.query(idViajeUri,
                projection, //Columnas a devolver
                ViajesContract.ViajesEntry.V_DATAFI + " LIKE '%'",       //Condición de la query
               // ViajesContract,
                null,       //Argumentos variables de la query
                null);      //Orden de los resultados

        if (c.moveToFirst())
        {
            int id_viaj = c.getColumnIndex(ViajesContract.ViajesEntry.V_ID);
            idviaje = c.getInt(id_viaj) ;
        }
        if (!c.isClosed()){
            c.close();
        }
        return String.valueOf(idviaje);
    }

    public int kminici(){
        String[] projection = new String[] {
                ViajesContract.ViajesEntry.V_KMIN,
        };
        Uri idViajeUri =  ViajesContract.ViajesEntry.URI_CONTENIDO;
        ContentResolver cr = getActivity().getContentResolver();
// la consulta
        Cursor c = cr.query(idViajeUri,
                projection, //Columnas a devolver
               // ViajesContract.ViajesEntry.V_DATAFI + " LIKE '%'",       //Condición de la query
                ViajesContract.ViajesEntry.V_ID + " = " + id_viaje,
                null,       //Argumentos variables de la query
                null);      //Orden de los resultados
        if (c.moveToFirst())
        {
            int kminici = c.getColumnIndex(ViajesContract.ViajesEntry.V_KMIN);
            kmini = c.getInt(kminici) ;
        }
        if (!c.isClosed()){
            c.close();
        }
        return kmini;
    }

    public int kmactual(){

        String[] projection = new String[] {
                ViajesContract.EventosEntry.E_KMP };
        Uri uri =  ViajesContract.EventosEntry.URI_CONTENIDO;
        ContentResolver cr = getActivity().getContentResolver();
// la consulta

        Cursor c = cr.query(uri,
                projection, //Columnas a devolver
                ViajesContract.EventosEntry.E_IDV + " = " + id_viaje + " and " +
                    ViajesContract.EventosEntry.E_KMP + " != '' ",
                        //+ ViajesContract.EventosEntry.E_KMP + " <> " + "",      //Condición de la query
            //    select _id, idviaje, kmp, fechah from eventos where kmp not null and kmp != "" and idviaje = "4" order by kmp asc ;
               // null,       //Argumentos variables de la query
                null,
            ////////    kmp not null and kmp != ""
                null,
                null);      //Orden de los resultados
              //  ViajesContract.EventosEntry.E_KMP+ " ASC");      //Orden de los resultados

        ////con la consulta anterior me saca el campo kmp de todos los eventos de este idviaje
        if (c.moveToLast())
        {
            // si entra aquí es porque tiene resultados. Entonces se va al último registro
            int totalreg = c.getCount();
            Log.i(TAG, "ennnnnn TOTALREGISTROS 218: " + totalreg); //esto lo hace bien
            c.moveToLast();
            String kmactual = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_KMP));
          ///=>>> //  kmactual.setText(cur.getString(cur.getColumnIndex(ViajesContract.EventosEntry.E_KMP)));
          //  kmactual = getText(getString(c.getColumnIndex(ViajesContract.EventosEntry.E_KMP)));
            //  int kmactual = c.getColumnIndex(ViajesContract.EventosEntry.E_KMP);
//            String nomevento = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_NOM));
            Log.i(TAG, "ennnnnn 231 --------------" + kmactual + " el nombre del evento");

//kmactu = kmactual.;int kmactu = 0;

            try {
                kmactu = Integer.parseInt(kmactual);
            } catch(NumberFormatException nfe) {
                // Handle parse error.
            }
//          kmactu = c.getInt(Integer.parseInt(kmactual)) ; // esto está bien si en el último evento está el kmp
         //   kmactu = Integer.parseInt(kmactual);
  //                  Log.i(TAG, "ennnnnn KMACTU 218: " + kmactu);
        }
                   //Log.i(TAG, "ennnnnn KMACTUAL 224: " + kmactual());
        if (!c.isClosed()){
            c.close();
        }
        return kmactu;
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case LOADER_MVIAJE:
               // getLoaderManager().restartLoader(0, null, this);
                Log.i(TAG, "InsertFragmentEVV onCreateLoader LOADER_MVIAJE 224 -modopag-AAAAA un poquito");
                String[] projection = new String[] {
                        ViajesContract.ViajesEntry.V_NOM,
                        ViajesContract.ViajesEntry.V_DATAIN + " as data",
                        ViajesContract.ViajesEntry.V_ID
                };
                return new CursorLoader(
                        getActivity(),                              // Parent activity context
                        ViajesContract.ViajesEntry.URI_CONTENIDO,    // Table to query
                        projection,      // Projection to return
                        null,                                       // No selection clause
                        null,                                       // No selection arguments
                        "data DESC");                                      // Default sort order
            case LOADER_MCATEG:
             //   getLoaderManager().restartLoader(0, null, this);
                Log.i(TAG, "MainFragmentito onCreateLoader CINCO");
                return new CursorLoader(
                        getActivity(),
                        ViajesContract.CategoriasEntry.URI_CONTENIDO,
                        null, null, null, null);
            default:
                return null;
        }
        }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId())
        {
            case LOADER_MVIAJE:
                viajeActAdapter.swapCursor(data);
              //  onLoadFinishedViajes(data);
                break;
            case LOADER_MCATEG:
                adaptador.swapCursor(data);
             //   onLoadFinishedCateg(data);
                break;
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId())
        {
            case LOADER_MCATEG:
                adaptador.swapCursor(null);
                break;
            case LOADER_MVIAJE:
                viajeActAdapter.swapCursor(null);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "MainFragmentito OnCreateview TRES");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_provi, container, false);

        //inicializa instancia los elementos del View: nomViaje totalKm totalGast btnViajes
        btnViajes = (Button) view.findViewById(R.id.buttnviaje);
        nomViaje = (Spinner) view.findViewById(R.id.spinner_nom_viaje);
        totalKm = (TextView) view.findViewById(R.id.totalkm);
            totalKm.setGravity(Gravity.RIGHT);
        totalGast = (TextView) view.findViewById(R.id.totalg);
            totalGast.setGravity(Gravity.RIGHT);

                 Log.d(TAG, "onItemSviajeActAdaptersetDropDownViewResource AAAAAAAAAAAAAAAAAAAAAA ");
        viajeActAdapter = new SimpleCursorAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                null,
                new String[]{ViajesContract.ViajesEntry.COLUMN_NAME},
                new int[]{android.R.id.text1}, 2);
        viajeActAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Log.d(TAG, "onItemSviajeActAdaptersetDropDownViewResource BBBBBBBBBBBBBBBBBBBBBB ");
        nomViaje.setAdapter(viajeActAdapter);

        nomViaje.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
            @Override
            /*
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();
            */
            public void onItemSelected(AdapterView<?> parent, View view, int positi, long id) {
                //onListItemClick(ListView l, View v, int posit, long idc)
                        Log.d(TAG, "onItemSelected 357 id_viaje -> position: " + positi + " id: = " + id);
                Cursor nomv = (Cursor) parent.getItemAtPosition(positi);
                id_viaje = nomv.getString(nomv.getColumnIndexOrThrow(ViajesContract.ViajesEntry.V_ID));
                po_viaje = String.valueOf(positi);
                String label = parent.getItemAtPosition(positi).toString();
         Log.d(TAG, "onItemSelected 360 (.nombViaje: " + label );
                        Log.d(TAG, "onItemSelected 362 (.position: " + po_viaje + " .) -> id_viaje: = " + id_viaje);
/////////////////////////////este ^ es el bueno
                totaGast();
                totaKM();
                totalKm.setText(strKmp);
                //totalGast.setText(totaGasto.toString());
                totalGast.setText(strTotalGast);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                        Log.d(TAG, "onNothingSelected");
            }
        });
        // aquí el adapter de la listaCateg

        Log.d(TAG, "aquí el adapter de la listaCateg 376 OnCreateView");
        adaptador = new ActivitiesAdapterCt(getActivity());
        // Relacionar adaptador a la lista
        setListAdapter(adaptador);

        // Iniciar Loader

        getLoaderManager().initLoader(LOADER_MCATEG, null, this);
     //   getLoaderManager().restartLoader(LOADER_MCATEG, null, this);
        getLoaderManager().initLoader(LOADER_MVIAJE, null, this);
      //  getLoaderManager().restartLoader(LOADER_MVIAJE, null, this);
        Button button = (Button) view.findViewById(R.id.buttnviaje);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity()
                                .startActivity(
                                       new Intent(getActivity(), EditVi.class)
                                );
                    }
                });
        return view;
    }
// las opciones del menú principal:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_edit:
                beginUpdate(); // Actualizar
                return true;
            case R.id.action_delete:
                deleteData(); // Eliminar
                getActivity().finish();
                return true;
            case R.id.m_catgor:
                String nomTabla = ViajesContract.CategoriasEntry.TABLE_NAME.toString();
                Intent intent = new Intent(getActivity(), ListCt.class);
                intent.putExtra("NombreTabla", nomTabla)
                      .putExtra("idv", String.valueOf(id_viaje));
                startActivity(intent);
                return true;
            case R.id.m_modopag:
                nomTabla = ViajesContract.MPagoEntry.TABLE_NAME.toString();
                Intent inten = new Intent(getActivity(), ListMp.class);
                inten.putExtra("NombreTabla", nomTabla);
                startActivity(inten);
                return true;
            case R.id.m_moneda:
                nomTabla = ViajesContract.MonedasEntry.TABLE_NAME.toString();
                Intent inte = new Intent(getActivity(), ListMn.class);
                inte.putExtra("NombreTabla", nomTabla);
                startActivity(inte);
                return true;
            case R.id.m_tipov:
                nomTabla = ViajesContract.TipoVEntry.TABLE_NAME.toString();
                Intent intn = new Intent(getActivity(), ListTv.class);
                intn.putExtra("NombreTabla", nomTabla);
                startActivity(intn);
                return true;
            case R.id.m_viajes:
                nomTabla = ViajesContract.ViajesEntry.TABLE_NAME.toString();
                Intent intnt = new Intent(getActivity(), ListVi.class);
                intnt.putExtra("NombreTabla", nomTabla);
                startActivity(intnt);
                return true;
            case R.id.m_eventos:
                nomTabla = ViajesContract.EventosEntry.TABLE_NAME.toString();
                Intent intnte = new Intent(getActivity(), ListEv.class);
                intnte.putExtra("NombreTabla", nomTabla)
                      .putExtra("idv", String.valueOf(id_viaje));
                startActivity(intnte);
                return true;
            case R.id.m_exportcsv:
                Intent incsv = new Intent(getActivity(), ExportCSV.class);
                startActivity(incsv);
                return true;
            case R.id.m_export:
                Intent in = new Intent(getActivity(), Export.class);
                startActivity(in);
                return true;
            case R.id.m_import:
                 Intent i = new Intent(getActivity(), Import.class);
                 startActivity(i);
                return true;
            case R.id.m_importcsv:
                Intent imcsv = new Intent(getActivity(), ImportCSV.class);
                startActivity(imcsv);
                return true;
            case R.id.action_vermapa:
                Intent it = new Intent(getActivity(), PolylineActivity.class);
                it.putExtra("idv", String.valueOf(id_viaje));
                startActivity(it);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * Elimina la actividad actual     */
    private void deleteData() {
       // Uri uri = ContentUris.withAppendedId(TechsContract.CONTENT_URI, id);
      //  getActivity().getContentResolver().delete(uri, null, null);
    }

    /** Envía los datos de la actividad hacia el formulario de actualización     */
    private void beginUpdate() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(this.getListView());
        Log.i(TAG, "MainFragmentito onActivityCreated CUATRO ");

       /// nomViaje.setText(nombreViaje);
        totalKm.setText(strKmp);
        totalGast.setText(strTotalGast);
        Log.i(TAG, "MainFragmentito onActivityCreated 422 " + strTotalGast);

        // Iniciar adaptador
        Log.d(TAG, "aquí el adapter de la listaCateg 503 OnActivityCreated");
        adaptador = new ActivitiesAdapterCt(getActivity());
        // Relacionar adaptador a la lista
        setListAdapter(adaptador);
    //    viajeActAdapter = new SimpleCursorAdapter(getActivity())   ;
        // Iniciar Loader
        getLoaderManager().initLoader(LOADER_MCATEG, null, this);
      //  getLoaderManager().restartLoader(LOADER_MCATEG, null, this);
        getLoaderManager().initLoader(LOADER_MVIAJE, null, this);
      //  getLoaderManager().restartLoader(LOADER_MVIAJE, null, this);
    }
    @Override
    public void onResume() {
            super.onResume();
        totaGast();
        totaKM();
        totalKm.setText(strKmp);
        //totalGast.setText(totaGasto.toString());
        totalGast.setText(strTotalGast);
        Log.i(TAG, "MainFragmentito onResume 464  totalgasto: " + strTotalGast);
        Log.i(TAG, "MainFragmentito onResume 465  totalkm: " + strKmp  );
        // Iniciar adaptador
        Log.d(TAG, "aquí el adapter de la listaCateg 525 OnResume");
        adaptador = new ActivitiesAdapterCt(getActivity());
        // Relacionar adaptador a la lista
        setListAdapter(adaptador);
        // Iniciar Loader
        getLoaderManager().initLoader(LOADER_MCATEG, null, this);
    }

    @Override   //on list item click se va con el id a Insertar Evento<<<<<<<<<<<<
    public void onListItemClick(ListView l, View v, int posit, long idc) {
        Log.i(TAG, "MainFragmentito onListItemClick SEIS");
        Log.d(TAG, "onListItemClick(.---530--..:-)))));-) -> id_viajeee: = " + id_viaje);
        Log.d(TAG, "onListItemClick(.---531--..:-)))));-) -> id_categgg: = " + idc);
        id_categ = String.valueOf(idc);
        po_categ = String.valueOf(posit);
        //po_viaje = String.valueOf(positi);

        Log.d(TAG, "onListItemClick(.--- 547 --..:-)))));-) -> pos_categgg: = " + po_categ);

        Bundle bundle = new Bundle();
       getActivity().startActivity(new Intent(getActivity(), EditEv.class)
            //   .putExtra(ViajesContract.CategoriasEntry.CAT_ID, idc)
               .putExtra("idc", id_categ)// este pasa bien el idcateg
               .putExtra("idv", id_viaje)
               .putExtra("poct", po_categ)
               .putExtra("povj", po_viaje));
        Log.d(TAG, "SALE del main id_categ: "+ id_categ + " po_categ: "+ po_categ +" id_viaje: "+ id_viaje +" po_viaje: "+ po_viaje );
           }

    public void iraNouViaje(View view) {
        Log.i(TAG, "Ahora en iraNouViaje de MainViajes) ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
                try {
                    getLoaderManager().destroyLoader(1);
                    if (adaptador != null) {
                        adaptador.changeCursor(null);
                        adaptador = null;
                    }
                    getLoaderManager().destroyLoader(2);
                    if (viajeActAdapter != null) {
                        viajeActAdapter.changeCursor(null);
                        viajeActAdapter = null;
                    }
                } catch (Throwable localThrowable) {
                    // Proyectar la excepción
                }
        }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
    // el menu contextual, con clic prolongado
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
            Log.i(TAG, "Ahora en onCreateContextMenu  441 e MainViajes) ");
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_main, menu);

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {

            case R.id.ctx_m_listevent:
                // menu contextual, con clic prolongado lleva a la lista de eventos de la categ seleccionada
                AdapterView.AdapterContextMenuInfo infoDel = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                        Log.i(TAG, "En onContextItemSelected idCATEGORIAAAAAAAAA id: " + infoDel.id); //esta si que lo tiene
                String idcat = String.valueOf(infoDel.id);
                Intent intnte = new Intent(getActivity(), ListEv.class);
                intnte.putExtra( "idCategoria" , idcat )
                      .putExtra("idv", id_viaje);
                startActivity(intnte);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}