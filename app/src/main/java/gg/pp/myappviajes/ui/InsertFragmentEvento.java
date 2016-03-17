package gg.pp.myappviajes.ui;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;

/**
 * Fragmento con formulario de insertEvento
 * Activities that contain this fragment must implement the
 * {@link InsertFragmentEvento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InsertFragmentEvento#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsertFragmentEvento extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   /// private static final String ARG_PARAM1 = "param1";
    ///private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
  ///  private String mParam1;
  ///  private String mParam2;
    private EditText nombre;
    private EditText descripcion;
    private Spinner moneda;
    private Spinner modopag;
    private EditText dirccio;
    private EditText phone;
    private EditText mail;
    private DatePicker fecha;
    private TextView longitud;

    private long id;

    private OnFragmentInteractionListener mListener;

    public InsertFragmentEvento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @param //param2 Parameter 2.
     * @return A new instance of fragment InsertFragmentEvento.

    // TODO: Rename and change types and number of parameters
    public static InsertFragmentEvento newInstance(String param1, String param2) {
        InsertFragmentEvento fragment = new InsertFragmentEvento();
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        id = getActivity().getIntent().getLongExtra(ViajesContract.CategoriasEntry.CAT_ID,-1);
        Log.i("TAG", "ViajecitosssssssInsertEventoooooo primero uri un poquitokkkkkkkkkkkkkkkkkkkkkkkkkkkkkk el id: " + id);
      ///  if (getArguments() != null) {
      ///      nombre = getArguments().getString(ARG_NOMBRE);
      ///      mParam2 = getArguments().getString(ARG_PARAM2);
      ///  }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("TAG", "ViajecitosssssssInsertEventoooooo sdegundu uri un poquitokkkkkkkkkkkkkkkkkkkkkkkkkkkkkk el id: " + id);
        // Inflate the layout for this fragment
        /// return inflater.inflate(R.layout.fragment_insert_evento, container, false);
        View view = inflater.inflate(R.layout.fragment_insert_evento, container, false);
        // Obtener views:
        nombre = (EditText) view.findViewById(R.id.etnombre);
        descripcion = (EditText) view.findViewById(R.id.eTDescrip);
        moneda = (Spinner) view.findViewById(R.id.spinnerM);
        modopag = (Spinner) view.findViewById(R.id.spinnerP);
        dirccio = (EditText) view.findViewById(R.id.etdireccio);
        phone = (EditText) view.findViewById(R.id.eTphone);
        mail = (EditText) view.findViewById(R.id.eTmail);
        fecha = (DatePicker) view.findViewById(R.id.dataEv);
        longitud = (TextView) view.findViewById(R.id.longi);
     //   categoria = (EditText) view.findViewById(R.id.categoria_spinner);

        return view;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Log.i("TAG", "ViajecitosssssssInsertEventoooooo antes de saveData  uri un poquito: " + ViajesContract.EventosEntry.URI_CONTENIDO);

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
    private void saveData() {
        Log.i("TAG", "ViajecitosssssssInsertEventoooooo saveData  uri un poquito: " + ViajesContract.EventosEntry.URI_CONTENIDO);

        // Obtención de valores actuales
        ContentValues values = new ContentValues();
        values.put(ViajesContract.EventosEntry.E_NOM, nombre.getText().toString());
        values.put(ViajesContract.EventosEntry.E_DESC, descripcion.getText().toString());
        values.put(ViajesContract.EventosEntry.E_MON, moneda.getSelectedItem().toString());
        values.put(ViajesContract.EventosEntry.E_MPAG, modopag.getSelectedItem().toString());
        values.put(ViajesContract.EventosEntry.E_DIR, dirccio.getText().toString());
        values.put(ViajesContract.EventosEntry.E_TEL, phone.getText().toString());
        values.put(ViajesContract.EventosEntry.E_MAIL, mail.getText().toString());
        values.put(ViajesContract.EventosEntry.E_DATAH, fecha.getCalendarView().toString());
        values.put(ViajesContract.EventosEntry.E_LON, longitud.getText().toString());


        getActivity().getContentResolver().insert(
                ViajesContract.EventosEntry.URI_CONTENIDO,
                values
        );
    }






        // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
