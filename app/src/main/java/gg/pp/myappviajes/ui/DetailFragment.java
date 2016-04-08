package gg.pp.myappviajes.ui;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gg.pp.myappviajes.R;
import gg.pp.myappviajes.modelo.ViajesContract;

// no lo uso ==================================================
/**
 * Fragmento para el detalle de la actividad
 */
public class DetailFragment extends Fragment {

    /**
     * Textos del layout
     */
    private TextView nom, descripcio, valoracio, callenum, ciudad;

    /**
     * Identificador de la actividad
     */
    private long id;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Obtención de views
        descripcio = (TextView) view.findViewById(R.id.descripcio_text);
        nom = (TextView) view.findViewById(R.id.nom_text);
        ciudad = (TextView) view.findViewById(R.id.ciudad_text);
        valoracio = (TextView) view.findViewById(R.id.valoracio_text);
        callenum = (TextView) view.findViewById(R.id.callenum_text);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        id = getActivity().getIntent().getLongExtra(ViajesContract.EventosEntry.E_ID, -1);
        updateView(id);  // Actualzar la vista con los datos de la actividad
    }

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
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Elimina la actividad actual
     */
    private void deleteData() {
        Uri uri = ContentUris.withAppendedId(ViajesContract.EventosEntry.URI_CONTENIDO, id);
        getActivity().getContentResolver().delete(
                uri,
                null,
                null
        );
    }

    /**
     * Envía todos los datos de la actividad hacia el formulario
     * de actualización
     */
    private void beginUpdate() {
        getActivity()
                .startActivity(
                        new Intent(getActivity(), UpdateActivity.class)
                                .putExtra(ViajesContract.EventosEntry.E_ID, id)
                                .putExtra(ViajesContract.EventosEntry.E_NOM, nom.getText())
                                .putExtra(ViajesContract.EventosEntry.E_DESC, descripcio.getText())
                                .putExtra(ViajesContract.EventosEntry.E_VAL, valoracio.getText())
                                .putExtra(ViajesContract.EventosEntry.E_DIR, callenum.getText())
                                .putExtra(ViajesContract.EventosEntry.E_CIUD, ciudad.getText())
                );
    }


    /**
     * Actualiza los textos del layout
     *
     * @param id Identificador de la actividad
     */
    private void updateView(long id) {
        if (id == -1) {
            descripcio.setText("");
            nom.setText("");
            ciudad.setText("");
            valoracio.setText("");
            callenum.setText("");

            return;
        }

        Uri uri = ContentUris.withAppendedId(ViajesContract.URI_BASE, id);
        Cursor c = getActivity().getContentResolver().query(
                uri,
                null, null, null, null);

        if (!c.moveToFirst())
            return;

        String descripcio_text = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_DESC));
        String nom_text = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_NOM));
        String ciudad_text = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_CIUD));
        String valoracio_text = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_VAL));
        String callenum_text = c.getString(c.getColumnIndex(ViajesContract.EventosEntry.E_DIR));

        descripcio.setText(descripcio_text);
        nom.setText(nom_text);
        ciudad.setText(ciudad_text);
        valoracio.setText(valoracio_text);
        callenum.setText(callenum_text);

        c.close(); // Liberar memoria del cursor
    }
}
