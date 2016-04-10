package gg.pp.myappviajes.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import gg.pp.myappviajes.ActivitiesAdapter;
import gg.pp.myappviajes.R;
import gg.pp.myappviajes.exportimport.ExportDb;
import gg.pp.myappviajes.exportimport.ImportDb;
import gg.pp.myappviajes.modelo.ViajesContract;

/**
 * Created by pepe on 5/03/16.  ListFragment FragmentActivity
 */
public class MainFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "En MainFragment: ";
    /**
     * Adaptador
     */
    private ActivitiesAdapter adaptador;
    private static final int V_CREA = 0;
    private TextView nomViaje;
    private TextView totalKm;
    private TextView totalGast;
    private Button btnViajes;
    public static final String nomTabla = null;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "MainFragmentito OnCreate UNO ");
        // Habilitar al fragmento para contribuir en la action bar
        setHasOptionsMenu(true);
        /////añado esto por la barra de titulo y actionbar
        Log.i(TAG, "MainFragmentito OnCrete DOS");

    }
    //////////////////////////////////////////////las cards se intercambian
    //final CardView card1 = new CardView(this);
    //card1.setVisibility(View.INVISIBLE);
    //Log.i(TAG, "Inserción en ");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "MainFragmentito OnCrete view TRES");
      //  View view = inflater.inflate(R.layout.fragment_main_old, container, false);
        View view = inflater.inflate(R.layout.layout_provi, container, false);

        //inicialica instancia los elementos del View: nomViaje totalKm totalGast btnViajes
        btnViajes = (Button) view.findViewById(R.id.buttnviaje);
        nomViaje = (TextView) view.findViewById(R.id.nom_viaje);
        totalKm = (TextView) view.findViewById(R.id.totalkm);
        totalGast = (TextView) view.findViewById(R.id.totalg);

        ImageButton fab = (ImageButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity()
                                .startActivity(
                                        new Intent(getActivity(), ListCt.class)
                                );
                    }

                });

        Button button = (Button) view.findViewById(R.id.buttnviaje);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity()
                                .startActivity(
                                        //////////////////////////////////////////////////
                                        new Intent(getActivity(), InsertViaje.class)
                                        //////////////////////////////////////////////////
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
                  Intent intent = new Intent(getContext(), ListCt.class);
                intent.putExtra("NombreTabla", nomTabla);
                startActivity(intent);
                return true;
            case R.id.m_modopag:
                nomTabla = ViajesContract.MPagoEntry.TABLE_NAME.toString();
                Intent inten = new Intent(getContext(), ListMp.class);
                inten.putExtra("NombreTabla", nomTabla);
                startActivity(inten);
                return true;
            case R.id.m_moneda:
                nomTabla = ViajesContract.MonedasEntry.TABLE_NAME.toString();
                Intent inte = new Intent(getContext(), ListMn.class);
                inte.putExtra("NombreTabla", nomTabla);
                startActivity(inte);
                return true;
            case R.id.m_tipov:
                nomTabla = ViajesContract.TipoVEntry.TABLE_NAME.toString();
                Intent intn = new Intent(getContext(), ListTv.class);
                intn.putExtra("NombreTabla", nomTabla);
                startActivity(intn);
                return true;
            case R.id.m_export:
               // nomTabla = ViajesContract.TipoVEntry.TABLE_NAME.toString();
                Intent in = new Intent(getContext(), ExportDb.class);
               // in.putExtra("NombreTabla", nomTabla);
                startActivity(in);
                return true;
            case R.id.m_import:
               // nomTabla = ViajesContract.TipoVEntry.TABLE_NAME.toString();
                Intent i = new Intent(getContext(), ImportDb.class);
              //  i.putExtra("NombreTabla", nomTabla);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * Elimina la actividad actual
     */
    private void deleteData() {
       // Uri uri = ContentUris.withAppendedId(TechsContract.CONTENT_URI, id);
      //  getActivity().getContentResolver().delete(uri, null, null);
    }

    /**
     * Envía todos los datos de la actividad hacia el formulario
     * de actualización
     */
    private void beginUpdate() {
        /*
        getActivity().startActivity(
                        new Intent(getActivity(), UpdateActivity.class)
                                .putExtra(TechsContract.Columnas._ID, id)
                                .putExtra(TechsContract.Columnas.DESCRIPCION, descripcion.getText())
                                .putExtra(TechsContract.Columnas.ESTADO, estado.getText())  );
        */
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "MainFragmentito onActivityCreated CUATRO ");
            // Iniciar adaptador
        adaptador = new ActivitiesAdapter(getActivity());
            // Relacionar adaptador a la lista
        setListAdapter(adaptador);
            // Iniciar Loader
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "MainFragmentito onCreateLoader CINCO");
        // Consultar todos los registros
        return new CursorLoader(
                getActivity(),
                ViajesContract.CategoriasEntry.URI_CONTENIDO,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adaptador.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adaptador.swapCursor(null);
    }

    @Override   //on list item click se va con el id a InsertEvento<<<<<<<<<<<<
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(TAG, "MainFragmentito onListItemClick SEIS");
        getActivity().startActivity(new Intent(getActivity(), InsertEvento.class)
                .putExtra(ViajesContract.CategoriasEntry.CAT_ID, id));
    }

    public void iraNouViaje(View view) {
        Log.i(TAG, "Ahora en iraNouViaje de MainViajes) ");
       // getActivity().startActivity(new Intent(getActivity(), InsertViaje.class));

     ////->   Intent i = new Intent(getContext(), InsertViaje.class);
       /////>- startActivityForResult(i, V_CREA);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getLoaderManager().destroyLoader(0);
            if (adaptador != null) {
                adaptador.changeCursor(null);
                adaptador = null;
            }
        } catch (Throwable localThrowable) {
            // Proyectar la excepción
        }
    }
}
