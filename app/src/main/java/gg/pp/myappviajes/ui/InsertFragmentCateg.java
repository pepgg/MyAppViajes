package gg.pp.myappviajes.ui;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;


/**
 * Fragmento con formulario de inserción
 */
public class InsertFragmentCateg extends Fragment {

    /**
     * Views del formulario
     */
    private TextView categoria;
  //  private Spinner prioridad;
  //  private Spinner entidad;
  //  private Spinner estado;
  //  private Spinner categoria;


    public InsertFragmentCateg() {
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
        View view = inflater.inflate(R.layout.fragment_insert, container, false);

        // Obtener views
        categoria = (TextView) view.findViewById(R.id.categor_input);
    //    prioridad = (Spinner) view.findViewById(R.id.prioridad_spinner);
    //    entidad = (Spinner) view.findViewById(R.id.tecnico_spinner);
    //    estado = (Spinner) view.findViewById(R.id.estado_spinner);
    //    categoria = (Spinner) view.findViewById(R.id.categoria_spinner);

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
        values.put(ViajesContract.CategoriasEntry.CAT_CGT, categoria.getText().toString());
    //    values.put(TechsContract.Columnas.PRIORIDAD, prioridad.getSelectedItem().toString());
    //    values.put(TechsContract.Columnas.CATEGORIA, categoria.getSelectedItem().toString());
    //    values.put(TechsContract.Columnas.TECNICO, entidad.getSelectedItem().toString());
    //    values.put(TechsContract.Columnas.DESCRIPCION, descripcion.getText().toString());

        getActivity().getContentResolver().insert(
                ViajesContract.URI_BASE,
                values
        );
    }
}
