package gg.pp.myappviajes.modelo;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by pepe on 4/03/16.
 */
public class ViajesProvider extends ContentProvider {
    public static final String NOMBRE_BASE_DATOS = "cpviajes.db";
    private static final int VERSION_ACTUAL = 1;
    private DatabaseHelper databaseHelper;
    private ContentResolver resolver;
    public static final String TAG = "Provider";
    long id;

     @Override
    public boolean onCreate() {
         Log.i(TAG, "ViajecitosProvider onCreate un poquito");
         databaseHelper = new DatabaseHelper(getContext(), NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Abrir base de datos
        SQLiteDatabase bd = databaseHelper.getReadableDatabase();
        // Comparar Uri
        int match = ViajesContract.uriMatcher.match(uri);
        // string auxiliar para los ids

        Cursor c;

        switch (match) {
            case ViajesContract.EVENTOS:
                // Consultando todos los eventos
                c = bd.query(ViajesContract.EventosEntry.TABLE_NAME, projection,
                    selection, selectionArgs, null, null, sortOrder);
                c.setNotificationUri(getContext().getContentResolver(), ViajesContract.URI_BASE);
                break;
            case ViajesContract.EVENTOS_ID:
                // Consultando un evento
                id = ContentUris.parseId(uri);
                c = bd.query(ViajesContract.EventosEntry.TABLE_NAME, projection,
                    ViajesContract.EventosEntry.E_ID + " = ?",
                    new String[]{String.valueOf(id)}, null, null, sortOrder);
                break;
            case ViajesContract.VIAJES:
                c = bd.query(ViajesContract.ViajesEntry.TABLE_NAME, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                c.setNotificationUri(getContext().getContentResolver(), ViajesContract.URI_BASE);
            case ViajesContract.VIAJES_ID:
                // Consultando un viaje
                id = ContentUris.parseId(uri);
                c = bd.query(ViajesContract.ViajesEntry.TABLE_NAME, projection,
                        ViajesContract.ViajesEntry.V_ID + " = ?",
                        new String[]{String.valueOf(id)}, null, null, sortOrder);
                break;

            case ViajesContract.CATEGORIAS:
                c = bd.query(ViajesContract.CategoriasEntry.TABLE_NAME, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ViajesContract.CATEGORIAS_ID:
                id = ContentUris.parseId(uri);
                c = bd.query(ViajesContract.EventosEntry.TABLE_NAME, projection,
                        ViajesContract.CategoriasEntry.CAT_ID + " = ?",
                        new String[]{String.valueOf(id)}, null, null, sortOrder);
                break;

            case ViajesContract.MONEDAS:
                c = bd.query(ViajesContract.MonedasEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case ViajesContract.MONEDAS_ID:
                id = ContentUris.parseId(uri);
                c = bd.query(ViajesContract.MonedasEntry.TABLE_NAME, projection,
                        ViajesContract.MonedasEntry.MON_ID + " = ?",
                        new String[]{String.valueOf(id)}, null, null, sortOrder);
                break;

            case ViajesContract.M_PAGO:
                c = bd.query(ViajesContract.MPagoEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case ViajesContract.M_PAGO_ID:
                id = ContentUris.parseId(uri);
                c = bd.query(ViajesContract.MPagoEntry.TABLE_NAME, projection,
                        ViajesContract.MPagoEntry.MPAG_ID + " = ?",
                        new String[]{String.valueOf(id)}, null, null, sortOrder);
                break;

            case ViajesContract.TIPO_V:
                c = bd.query(ViajesContract.TipoVEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case ViajesContract.TIPO_V_ID:
                id = ContentUris.parseId(uri);
                c = bd.query(ViajesContract.TipoVEntry.TABLE_NAME, projection,
                        ViajesContract.TipoVEntry.TIPO_ID + " = ?",
                        new String[]{String.valueOf(id)}, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("URI NO SOPORTADA = " + uri);
        }
        c.setNotificationUri(resolver, uri);

        return c;

    }

    @Override
    public String getType(Uri uri) {
        switch (ViajesContract.uriMatcher.match(uri)) {
            case ViajesContract.EVENTOS:
                return ViajesContract.EventosEntry.TIPO_CONTENIDO;
            case ViajesContract.EVENTOS_ID:
                return ViajesContract.EventosEntry.TIPO_CONTENIDO_ITEM;

            case ViajesContract.VIAJES:
                return ViajesContract.ViajesEntry.TIPO_CONTENIDO;
            case ViajesContract.VIAJES_ID:
                return ViajesContract.ViajesEntry.TIPO_CONTENIDO_ITEM;

            case ViajesContract.CATEGORIAS:
                return ViajesContract.CategoriasEntry.TIPO_CONTENIDO;
            case ViajesContract.CATEGORIAS_ID:
                return ViajesContract.ViajesEntry.TIPO_CONTENIDO_ITEM;

            case ViajesContract.MONEDAS:
                return ViajesContract.MonedasEntry.TIPO_CONTENIDO;
            case ViajesContract.MONEDAS_ID:
                return ViajesContract.MonedasEntry.TIPO_CONTENIDO_ITEM;

            case ViajesContract.M_PAGO:
                return ViajesContract.MPagoEntry.TIPO_CONTENIDO;
            case ViajesContract.M_PAGO_ID:
                return ViajesContract.MPagoEntry.TIPO_CONTENIDO_ITEM;

            case ViajesContract.TIPO_V:
                return ViajesContract.TipoVEntry.TIPO_CONTENIDO;
            case ViajesContract.TIPO_V_ID:
                return ViajesContract.TipoVEntry.TIPO_CONTENIDO_ITEM;

            default:
                throw new UnsupportedOperationException("Uri desconocida =>" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "Inserción en " + uri + "( " + values.toString() + " )\n");
        SQLiteDatabase bd = databaseHelper.getWritableDatabase();
        //String id = null;
        Uri returnUri;
        switch (ViajesContract.uriMatcher.match(uri)) {

            case ViajesContract.VIAJES:
                id = bd.insertOrThrow(ViajesContract.ViajesEntry.TABLE_NAME, null, values);
                notificarCambio(uri);
               // return ViajesContract.ViajesEntry.crearUriViajes(values.getAsString(DatabaseHelper.Tablas.VIAJES));

            case ViajesContract.EVENTOS:
                id = bd.insertOrThrow(ViajesContract.EventosEntry.TABLE_NAME, null, values);
                notificarCambio(uri);

            case ViajesContract.CATEGORIAS:
                id = bd.insertOrThrow(ViajesContract.CategoriasEntry.TABLE_NAME, null, values);
                notificarCambio(uri);

            case ViajesContract.MONEDAS:
                id = bd.insertOrThrow(ViajesContract.MonedasEntry.TABLE_NAME, null, values);
                notificarCambio(uri);

            case ViajesContract.M_PAGO:
                id = bd.insertOrThrow(ViajesContract.MPagoEntry.TABLE_NAME, null, values);
                notificarCambio(uri);

            case ViajesContract.TIPO_V:
                id = bd.insertOrThrow(ViajesContract.TipoVEntry.TABLE_NAME, null, values);
                notificarCambio(uri);

            default:
                throw new UnsupportedOperationException("URI_NO_SOPORTADA" + uri);
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete: " + uri);

        SQLiteDatabase bd = databaseHelper.getWritableDatabase();

        int afectados;

        switch (ViajesContract.uriMatcher.match(uri)) {
            case ViajesContract.EVENTOS:
                afectados = bd.delete(ViajesContract.EventosEntry.TABLE_NAME, selection, selectionArgs);
                notificarCambio(uri);
                break;

            case ViajesContract.VIAJES:
                afectados = bd.delete(ViajesContract.ViajesEntry.TABLE_NAME, selection, selectionArgs);
                notificarCambio(uri);
                break;

            case ViajesContract.CATEGORIAS:
                afectados = bd.delete(ViajesContract.CategoriasEntry.TABLE_NAME, selection, selectionArgs);
                notificarCambio(uri);
                break;

            case ViajesContract.MONEDAS:
                afectados = bd.delete(ViajesContract.MonedasEntry.TABLE_NAME, selection, selectionArgs);
                notificarCambio(uri);
                break;

            case ViajesContract.M_PAGO:
                afectados = bd.delete(ViajesContract.MPagoEntry.TABLE_NAME, selection, selectionArgs);
                notificarCambio(uri);
                break;

            case ViajesContract.TIPO_V:
                afectados = bd.delete(ViajesContract.TipoVEntry.TABLE_NAME, selection, selectionArgs);
                notificarCambio(uri);
                break;

            default:
                throw new UnsupportedOperationException("URI_NO_SOPORTADA" + uri);
        }
        return afectados;
        //return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase bd = databaseHelper.getWritableDatabase();
        int afectados;

        switch (ViajesContract.uriMatcher.match(uri)) {
            case ViajesContract.EVENTOS:
                afectados = bd.update(ViajesContract.EventosEntry.TABLE_NAME, values,
                        selection, selectionArgs);
                notificarCambio(uri);
                break;

            case ViajesContract.VIAJES:
                afectados = bd.update(ViajesContract.ViajesEntry.TABLE_NAME, values,
                        selection, selectionArgs);
                notificarCambio(uri);
                break;

            case ViajesContract.CATEGORIAS:
                afectados = bd.update(ViajesContract.CategoriasEntry.TABLE_NAME, values,
                        selection, selectionArgs);
                notificarCambio(uri);
                break;

            case ViajesContract.MONEDAS:
                afectados = bd.update(ViajesContract.MonedasEntry.TABLE_NAME, values,
                        selection, selectionArgs);
                notificarCambio(uri);
                break;

            case ViajesContract.M_PAGO:
                afectados = bd.update(ViajesContract.MPagoEntry.TABLE_NAME, values,
                        selection, selectionArgs);
                notificarCambio(uri);
                break;

            case ViajesContract.TIPO_V:
                afectados = bd.update(ViajesContract.TipoVEntry.TABLE_NAME, values,
                        selection, selectionArgs);
                notificarCambio(uri);
                break;

            default:
                throw new UnsupportedOperationException("URI_NO_SOPORTADA" + uri);
        }

        return afectados;
    }


    private void notificarCambio(Uri uri) {
        resolver.notifyChange(uri, null);
    }
}
