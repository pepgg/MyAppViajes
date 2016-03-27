package gg.pp.myappviajes.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.DatabaseHelper;
import gg.pp.myappviajes.modelo.ViajesContract;

//import android.app.LoaderManager;
//import android.support.v4.app.Fragment;

/**
 * Fragmento con formulario de inserción de viajes
 *
 *
 *
 */
//public class InsertFragmentVi extends Fragment implements View.OnClickListener, LoaderCallbacks<Cursor>, LoaderManager.LoaderCallbacks<Object> {   //, AdapterView.OnItemSelectedListener {
public class InsertFragmentVi extends android.support.v4.app.Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Views del formulario
     */

    private EditText mNomText;
   // private EditText etDataIn;
   private Button btDataIn;
    private Button btDataFi;
 //   private Button btAddViaje;
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
  ///////////// /////////// DateFormat fmtdata = DateFormat.getDateInstance();  21-3 lo quito por si acaso
    Spinner mTipoV;
    //////////////////////
    private EditText mkmini;
    private EditText mkmfi;
    private EditText mDescrip;
//	private LinearLayout mLDatafi;  // mDataFI mDataIN mkmini mkmfi mDescrip
    public int fecha1;
    static final int DATE_DIALOG_IN = 0;
    static final int DATE_DIALOG_FI = 1;
    public Long mId;
    private Long mIdviaje;
    private Long mIdMod;

    public static final int LOADER_TIPOV = 2; // Loader identifier for Tipov

    protected final static int DIALOG_CREATING_C = 3;
    private ProgressDialog dialog;
    private DatabaseHelper mDbHelper;
    private static final String TAG = "En InsertFragmentVi---aje";

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "dd-MM-yyyy", Locale.FRENCH);//  . .US);
    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;
   // Viaje viaje = null;
    ///////////////////////////////
   SimpleCursorAdapter mTipoVAdapter; // Adapters for spinners
    SimpleCursorAdapter mModPagAdapter, mMonedAdapter, sAdapter; // Adapters for both spinne
    ////////////////////////////////
    private OnFragmentInteractionListener mListener;


    public InsertFragmentVi() {
        // Required empty public constructor
    }






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.i("TAG", "ViajecitosssssssInsertFragmentVIIIII primero onCreate un poquito: "); // aquí llega

       // Loader
getLoaderManager().initLoader(LOADER_TIPOV, null, this);
     //   getSupportLoaderManager().initLoader(LOADER_TIPOV, null, this);

    }

 //   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_vi, container, false);


        findViewsById(view);

        mTipoV.setEnabled(false);
        mTipoVAdapter = new SimpleCursorAdapter(
                getContext(), android.R.layout.simple_spinner_item,
                null,
                new String[] { ViajesContract.TipoVEntry.COLUMN_NAME },
                new int[] { android.R.id.text1 }, 2);
        mTipoVAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTipoV.setAdapter(mTipoVAdapter);
        // Provinces spinner selection event

        mTipoV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "onItemSelected(...) -> position = " + position);


                Cursor tipv = (Cursor) parent.getItemAtPosition(position);
                ///-  String provinceCode = tipv.getString(tipv.getColumnIndexOrThrow(Provinces.COLUMN_CODE));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("TAG", "onNothingSelected");
            }
        });



        setListeners();

        if (savedInstanceState != null) {
            dateCalendar = Calendar.getInstance();
            if (savedInstanceState.getLong("dateCalendar") != 0)
                dateCalendar.setTime(new Date(savedInstanceState
                        .getLong("dateCalendar")));
        }

        return view;
    }

/* quito esto el 26-3 pqaraq probarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
                Log.d("TAG", "onCreateLoader tipov");
                return new CursorLoader(this, ViajesContract.TipoVEntry.URI_CONTENIDO, new String[] { ViajesContract.TipoVEntry.TIPO_ID,  ViajesContract.TipoVEntry.COLUMN_NAME }, null, null, null);
                return null;
    }

    *///////////////////



    //////pruebo con este
    //////////////////////////////////////////////////////////////////////////////////////////////

    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {

        android.support.v4.content.CursorLoader cursorLoader = new CursorLoader(
       // return new cursorLoader(
                getActivity(),
                ViajesContract.MPagoEntry.URI_CONTENIDO,
                ViajesContract.MPagoEntry.TAG_COLUMNS,
                null,
                null,
                null);
        return cursorLoader;

    }



/////////////////////////
public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
    mTipoVAdapter.swapCursor(arg1);
}

    public void onLoaderReset(Loader<Cursor> arg0) {
        mTipoVAdapter.swapCursor(null);
    }
/*
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ViajesContract.TipoVEntry.URI_CONTENIDO,
                new String[] { ViajesContract.TipoVEntry.TIPO_ID,  ViajesContract.TipoVEntry.COLUMN_NAME }, null, null, null);

        return null;
    }
*/


    /*
        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        }

        @Override
        public void onLoaderReset(Loader<Object> loader) {

        }

        //   @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data)
        {
                    Log.d("TAG", "onLoadFinished<Provinces>");
                    onLoadFinishedTipov(data);
        }
        */

/*
    private void onLoadFinishedTipov(Cursor data)
    {
        mTipoVAdapter.swapCursor(data);
        int rowCount = data.getCount();
        Log.d("TAG", "onLoadFinishedProvinces(...) -> Provinces data loaded. " + rowCount + " rows available");
        if (rowCount > 0)
        {
            mTipoV.setEnabled(true);
        }
        else
        {
            // Both Spinners (provinces and cities) must be disabled
            mTipoV.setEnabled(false);
        }
    }

*/



    private void setListeners() {
        btDataIn.setOnClickListener((View.OnClickListener) getContext()); //.setOnClickListener(this);
        btDataFi.setOnClickListener((View.OnClickListener) getActivity()); //.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateCalendar = Calendar.getInstance();
                        dateCalendar.set(year, monthOfYear, dayOfMonth);
                        Log.i("TAG", " antes delllll switch(viewgetId change_data__ " + view);

                        switch(fecha1){
                            case DATE_DIALOG_IN: //R.id.change_data_in:
                                Log.i("TAG", " switch(viewgetId change_data_in " + fecha);
                                btDataIn.setText(formatter.format(dateCalendar
                                        .getTime()));
                                break;
                            case DATE_DIALOG_FI:
                                Log.i("TAG", " switch(view.getId change_data_fi " + fecha);
                                btDataFi.setText(formatter.format(dateCalendar
                                        .getTime()));
                                break;
                        }

                    }

                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

    }
 /*
    protected void resetAllFields() {
        mNomText.setText("");
        mkmini.setText("");
        mkmfi.setText("");
        mDescrip.setText("");
        if (mTipoV.getAdapter().getCount() > 0)
            mTipoV.setSelection(0);
    }
*/
    /*
        private void setViaje() {  //esto es lo que guarda
            viaje = new Viaje();
            viaje.setName(mNomText.getText().toString());
            viaje.setSalary(Double.parseDouble(mDescrip.getText()
                    .toString()));
            if (dateCalendar != null)
                viaje.setDateOfBirth(dateCalendar.getTime());
            MiTipov selectedTipov = (TipoV) miTipoV.getSelectedItem();
            viaje.setTipoV(selectedTipov);
        }
    */
    @Override
    public void onResume() {
       // getActivity().setTitle(R.string.tipo_v);
       // getActivity().getActionBar().setTitle(R.string.tipo_v);
        super.onResume();

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       if (dateCalendar != null)
                outState.putLong("dateCalendar", dateCalendar.getTime().getTime());
    }

    private String getSelectedField(SimpleCursorAdapter adapter, int selectedPosition, String field, String defaultValue)
    {
        if(adapter.getCount() > 0)
        {
            Cursor cur = (Cursor)adapter.getItem(selectedPosition);
            return cur.getString(cur.getColumnIndex(field));
        }
        else
        {
            return defaultValue;
        }
    }

    private void findViewsById(View view) {

        // Obtener views del layout
        mNomText = (EditText) view.findViewById(R.id.nom_viaje);
        btDataIn = (Button) view.findViewById(R.id.change_data_in);
        btDataFi = (Button) view.findViewById(R.id.change_data_fi);
        mkmini = (EditText) view.findViewById(R.id.kmini);
        mkmfi = (EditText) view.findViewById(R.id.kmfi);
        mDescrip = (EditText) view.findViewById(R.id.descripv);

        mTipoV = (Spinner) view.findViewById(R.id.spinner_tv);

    }

 //   @Override
    public void onClick(View view) {
        if (view == btDataIn) {
            fecha1 = DATE_DIALOG_IN;
            Log.i("TAG", " onClick andando voy -- " + view);
            datePickerDialog.show();
        } else if (view == btDataFi) {
            fecha1 = DATE_DIALOG_FI;
            Log.i("TAG", " onClick estandando vengo -- " + view);
            datePickerDialog.show();
       // } else if (view == btAddViaje) {
       //     saveData();

       // } else if (view == resetButton) {
         //   resetAllFields();
        }
    }

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

    private void saveData() {
        // Obtención de valores actuales
        ContentValues values = new ContentValues();
        values.put(ViajesContract.ViajesEntry.V_NOM, mNomText.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_DATAIN, btDataIn.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_DATAFI, btDataFi.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_KMIN, mkmini.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_KMFI, mkmfi.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_DESC, mDescrip.getText().toString());
        values.put(ViajesContract.ViajesEntry.V_TIPO, mTipoV.toString());


        getActivity().getContentResolver().insert(
                ViajesContract.ViajesEntry.URI_CONTENIDO,
                values
        );
    }

  /*
    *quito esto porque protesta:  Caused by: java.lang.RuntimeException: gg.pp.myappviajes.ui.InsertViaje@2673da8 must implement OnFragmentInteractionListener
                                                                     at gg.pp.myappviajes.ui.InsertFragmentVi.onAttach(InsertFragmentVi.java:273)
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
*/
  //  @Override
    public void onDetach() {
  //      super.onDetach();
        mListener = null;
    }

 //   @Override
    public void onClick(DialogInterface dialog, int which) {

    }




  //  @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


     public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





}