package gg.pp.myappviajes.ui;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.util.Calendar;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.DatabaseHelper;
import gg.pp.myappviajes.modelo.ViajesContract;

/**
 * Fragmento con formulario de inserción de VIAJESSSSSSSSSSSSS
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InsertFragmentVi.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InsertFragmentVi#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsertFragmentVi extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";
    /**
     * Views del formulario
     */
    private EditText mNomText;
    private Button btnDataIn;
    private Button btnDataFi;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mDiaSet;
    private String diasem;
    public Calendar datafi;
    public Calendar diaSetmana;
    public Calendar mDataFI;
    public Calendar mDataIN;
    java.util.Date datainici;
    java.util.Date datafinal;
    java.util.Date datactual;
    private String fecha;
    DateFormat fmtdata = DateFormat.getDateInstance();
    private EditText mkmini;
    private EditText mkmfi;
    private EditText mDescrip;
//	private LinearLayout mLDatafi;  // mDataFI mDataIN mkmini mkmfi mDescrip

    static final int DATE_DIALOG_IN = 0;
    static final int DATE_DIALOG_FI = 1;
    private Spinner mModo;
    public Long mId;
    private Long mIdviaje;
    private Long mIdMod;

    protected final static int DIALOG_CREATING_C = 2;
    private ProgressDialog dialog;
    private DatabaseHelper mDbHelper;
    private static final String TAG = "En InsertFragmentVi---aje";


    // TODO: Rename and change types of parameters
   // private String mParam1;
   // private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InsertFragmentVi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @param //param2 Parameter 2.
     * @return A new instance of fragment InsertFragmentVi.
     *
     *
    // TODO: Rename and change types and number of parameters
    public static InsertFragmentVi newInstance(String param1, String param2) {
        InsertFragmentVi fragment = new InsertFragmentVi();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        */
        setHasOptionsMenu(true);
        Log.i("TAG", "ViajecitosssssssInsertFragmentVIIIII primero onCreate un poquito: " ); // lo tiene??
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_insert_vi, container, false);
        View view = inflater.inflate(R.layout.fragment_insert_vi, container, false);
        // Obtener views del layout

        mNomText = (EditText) view.findViewById(R.id.nom_viaje);
        btnDataIn = (Button) view.findViewById(R.id.change_data_in);
        btnDataFi = (Button) view.findViewById(R.id.change_data_fi);
       // mYear = (EditText) view.findViewById(R.id.nom_e);
       // mMonth = (EditText) view.findViewById(R.id.nom_e);
       // mDay = (EditText) view.findViewById(R.id.nom_e);
       // mDiaSet = (EditText) view.findViewById(R.id.nom_e);
       // diasem = (EditText) view.findViewById(R.id.nom_e);
       // datafi = (EditText) view.findViewById(R.id.nom_e);
       // diaSetmana = (EditText) view.findViewById(R.id.nom_e);
       // mDataFI = (EditText) view.findViewById(R.id.nom_e);
       // mDataIN = (EditText) view.findViewById(R.id.nom_e);
       // datainici = (EditText) view.findViewById(R.id.nom_e);
       // datafinal = (EditText) view.findViewById(R.id.nom_e);
       // datactual = (EditText) view.findViewById(R.id.nom_e);
       // fecha = (EditText) view.findViewById(R.id.nom_e);
        //fmtdata = DateFormat.getDateInstance();
        mkmini = (EditText) view.findViewById(R.id.nom_e);
        mkmfi = (EditText) view.findViewById(R.id.nom_e);
        mDescrip = (EditText) view.findViewById(R.id.nom_e);
//	private LinearLayout mLDatafi;  // mDataFI mDataIN mkmini mkmfi mDescrip
        mModo = (Spinner) view.findViewById(R.id.spinner1);
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
    private void saveData() {
        // Obtención de valores actuales
        ContentValues values = new ContentValues();
        values.put(ViajesContract.ViajesEntry.V_NOM, mNomText.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_DATAIN, btnDataIn.toString());
        values.put(ViajesContract.ViajesEntry.V_DATAFI, btnDataFi.toString());
        values.put(ViajesContract.ViajesEntry.V_KMIN, mkmini.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_KMFI, mkmfi.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_DESC, mDescrip.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_TIPO, mModo.toString());


        getActivity().getContentResolver().insert(
                ViajesContract.EventosEntry.URI_CONTENIDO,
                values
        );
    }



/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
