package gg.pp.myappviajes.ui;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;


/**
 * Fragmento con formulario de inserción de eventos
 */
public class InsertFragmentEv extends Fragment {

    /**
     * Views del formulario
     */
  private EditText nombre;
  private EditText descripcio;
  private EditText totaleur;
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

    public InsertFragmentEv() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id_categ = getActivity().getIntent().getLongExtra(ViajesContract.CategoriasEntry.CAT_ID, -1);
        setHasOptionsMenu(true);
        Log.i("TAG", "ViajecitosssssssInsertFragment primero onCreate un poquitokkkkkkkkkkkkkkkkkkkkkkkkkkkkkk: " + id_categ); // lo tienexxxxxxxxbvn



    }
    /*
    Calendar c = Calendar.getInstance();
    System.out.println("Current time => " + c.getTime());

    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c.getTime());
    */
/////////////////////
/*
private String getDateTime() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date(now); return dateFormat.format(date); }

    Calendar cal = new GregorianCalendar();
    Date date = (Date) cal.getTime();
*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert, container, false);

        // Obtener views
        nombre = (EditText) view.findViewById(R.id.nom_e);
        descripcio = (EditText) view.findViewById(R.id.descripcio_e);
        totaleur = (EditText) view.findViewById(R.id.total_eur);
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
        Log.i("TAG", "InsertFragmentTTTTTTTTTTTTTTTTTTTTT onCreateView 93 un poquito: " + id_categ); //Si lo tiene

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                saveData(); // Guardar datos
                getActivity().finish();
                return true;
          //  case R.id.action_discard:
          //      getActivity().finish();
          //      return true;
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
}
