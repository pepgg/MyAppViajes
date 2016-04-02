package gg.pp.myappviajes.ui;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;

/**
 * Fragmento con un formulario de actualización
 */
public class UpdateFragment extends Fragment {
    /**
     * Identificador de la actividad
     */
    private long id;
    /**
     * Views del layout
     */
    private EditText descripcion;
    private Spinner entidad;
    private Spinner prioridad;
    private Spinner estado;
    private Spinner categoria;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert_ev, container, false);

        // Obtener views
     //   descripcion = (EditText) view.findViewById(R.id.descripcion_input);
     //   prioridad = (Spinner) view.findViewById(R.id.prioridad_spinner);
     //   entidad = (Spinner) view.findViewById(R.id.tecnico_spinner);
      //  estado = (Spinner) view.findViewById(R.id.estado_spinner);
     //   categoria = (Spinner) view.findViewById(R.id.categoria_spinner);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        id = getActivity().getIntent().getLongExtra(ViajesContract.EventosEntry.E_ID, -1);
        updateView(); // Cargar datos iniciales
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                updateData(); // Actualizar
                getActivity().finish();
                return true;
            case R.id.action_discard:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Actualizar datos de la actividad
     */
    private void updateData() {  ////////////esto esta provisionalllllllllllllllll

        // Unir Uri principal con identificador
        Uri uri = ContentUris.withAppendedId(ViajesContract.URI_BASE, id);

        ContentValues values = new ContentValues();
        values.put(ViajesContract.EventosEntry.E_DIR, descripcion.getText().toString());
        values.put(ViajesContract.EventosEntry.E_DIR, prioridad.getSelectedItem().toString());
        values.put(ViajesContract.EventosEntry.E_DIR, entidad.getSelectedItem().toString());
        values.put(ViajesContract.EventosEntry.E_DIR, estado.getSelectedItem().toString());
        values.put(ViajesContract.EventosEntry.E_DIR, categoria.getSelectedItem().toString());

        // Actualiza datos del Content Provider
        getActivity().getContentResolver().update(
                uri,
                values,
                null,
                null
        );
    }

    /**
     * Carga los datos que provienen desde el detalle
     */
    private void updateView() {
        // Obtener datos del formulario
        Intent i = getActivity().getIntent();
        String descripcion_text = i.getStringExtra(ViajesContract.EventosEntry.E_DIR);
        String prioridad_text = i.getStringExtra(ViajesContract.EventosEntry.E_DIR);
        String entidad_text = i.getStringExtra(ViajesContract.EventosEntry.E_DIR);
        String estado_text = i.getStringExtra(ViajesContract.EventosEntry.E_DIR);
        String categoria_text = i.getStringExtra(ViajesContract.EventosEntry.E_DIR);

        // Actualizar la vista
        descripcion.setText(descripcion_text);
        prioridad.setSelection(getIndex(prioridad, prioridad_text));
        entidad.setSelection(getIndex(entidad, entidad_text));
        estado.setSelection(getIndex(estado, estado_text));
        categoria.setSelection(getIndex(categoria, categoria_text));
    }

    /**
     * Obtiene el indice de un {@link Spinner} según el valor
     * de una cadena
     *
     * @param spinner Instancia del spinner
     * @param value   Cadena a buscar
     * @return Posición donde se encuentra
     */
    private int getIndex(Spinner spinner, String value) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                index = i;
                break;
            }
        }
        return index;
    }


}
