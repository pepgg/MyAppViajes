package gg.pp.myappviajes.modelo;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import gg.pp.myappviajes.modelo.DatabaseHelper.Tablas;
import gg.pp.myappviajes.modelo.ViajesContract.CategoriasEntry;
import gg.pp.myappviajes.modelo.ViajesContract.EventosEntry;
import gg.pp.myappviajes.modelo.ViajesContract.MPagoEntry;
import gg.pp.myappviajes.modelo.ViajesContract.MonedasEntry;
import gg.pp.myappviajes.modelo.ViajesContract.TipoVEntry;
import gg.pp.myappviajes.modelo.ViajesContract.ViajesEntry;

/**
 * Created by pepe on 4/03/16.
 */
public class ViajesProvider extends ContentProvider {
   // public static final String NOMBRE_BASE_DATOS = "cpviajes.db";
   // private static final int VERSION_ACTUAL = 1;
    private DatabaseHelper databaseHelper;
    private ContentResolver resolver;
    public ViajesProvider(){
    }
    //////////////////los que estaban el contract:
    public static final UriMatcher uriMatcher;

    // Casos
    public static final int VIAJES = 100;
    public static final int VIAJES_ID = 101;
    //    public static final int VIAJES_DET = 102;
    public static final int VIAJES_NOM = 103;

    public static final int EVENTOS = 200;
    public static final int EVENTOS_ID = 201;
//    public static final int EVENTOS_DET= 202;

    public static final int CATEGORIAS = 300;
    public static final int CATEGORIAS_ID = 301;

    public static final int MONEDAS = 400;
    public static final int MONEDAS_ID = 401;

    public static final int M_PAGO = 500;
    public static final int M_PAGO_ID = 501;

    public static final int TIPO_V = 600;
    public static final int TIPO_V_ID = 601;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(ViajesContract.AUTORIDAD, "viajes", VIAJES);
        uriMatcher.addURI(ViajesContract.AUTORIDAD, "viajes/*", VIAJES_ID);
        //       uriMatcher.addURI(AUTORIDAD, "viajes/*/detalles", VIAJES_DET);

        uriMatcher.addURI(ViajesContract.AUTORIDAD, "eventos", EVENTOS);
        uriMatcher.addURI(ViajesContract.AUTORIDAD, "eventos/*", EVENTOS_ID);
//        uriMatcher.addURI(AUTORIDAD, "eventos/*/detalles", EVENTOS_DET);

        uriMatcher.addURI(ViajesContract.AUTORIDAD, "categorias", CATEGORIAS);
        uriMatcher.addURI(ViajesContract.AUTORIDAD, "categorias/*", CATEGORIAS_ID);

        uriMatcher.addURI(ViajesContract.AUTORIDAD, "monedas", MONEDAS);
        uriMatcher.addURI(ViajesContract.AUTORIDAD, "monedas/*", MONEDAS_ID);

        uriMatcher.addURI(ViajesContract.AUTORIDAD, "mpago", M_PAGO);
        uriMatcher.addURI(ViajesContract.AUTORIDAD, "mpago/*", M_PAGO_ID);

        uriMatcher.addURI(ViajesContract.AUTORIDAD, "tipov", TIPO_V);
        uriMatcher.addURI(ViajesContract.AUTORIDAD, "tipov/*", TIPO_V_ID);
    }
    // [/URI_MATCHER]

    // [/URIS]

    ///////////////////




    // del moviedatabase
  //  private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static final String TAG = "En ViajesProvider: ";
   // long id;

     @Override
    public boolean onCreate() {
         Log.i(TAG, "ViajecitosProvider onCreate un poquit<<<<<o PRUEBA DE URI " + ViajesContract.EventosEntry.URI_CONTENIDO);//este es bueno

         databaseHelper = new DatabaseHelper(getContext());
         resolver = getContext().getContentResolver();
        return true;
    }

    ///////////////////////////////////////////////////

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        /////////////////////////////////////////aqui cambiaba la uri y pone categorias  por qué¿¿¿¿¿¿¿¿¿¿¿¿
        Log.i(TAG, "ViajecitosProvider primero QUERY 109 uri un poquito: " + uri);
        // Abrir base de datos
        SQLiteDatabase bd = databaseHelper.getReadableDatabase();
    //   final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // Comparar Uri
        int match = uriMatcher.match(uri);
        Log.i(TAG, "ViajecitosProvider el mathc de la URRRRRRIIIIII query uri un poquito match: " + match);
////////////////////////////////////////////////el match que llega es el 300, el de categorias
        // string auxiliar para los ids
        String id;
        Cursor c;
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder(); //esto no lo usa

        Log.i(TAG, "ViajecitosProvider query uri un poquito uri y match: " + uri + " " + match); //el uri llega bien
        /////////////////////EN este sswitch fallaaaaaaaaaaaaaaaaaaaaaaa
        switch (match) {
      //  switch(sUriMatcher.match(uri)){

            case EVENTOS:
                // Consultando todos los eventos
                Log.i(TAG, "ViajecitosProvider query uri un EVENTOS uri y match: " + uri + " " + match);
                c = bd.query(Tablas.EVENTOS, projection,
                        selection, selectionArgs, null, null, sortOrder);
               // c.setNotificationUri(getContext().getContentResolver(), ViajesContract.EventosEntry.URI_CONTENIDO);
                break;
            case EVENTOS_ID:
                // Consultando un evento
                id = EventosEntry.obtenerIdEvento(uri);
                c = bd.query(Tablas.EVENTOS, projection,
                    EventosEntry.E_ID + " = ?",
                    new String[]{id}, null, null, null);
                break;
            case VIAJES:
                c = bd.query(Tablas.VIAJES, projection,
                        selection, selectionArgs, null, null, sortOrder);
                //c.setNotificationUri(getContext().getContentResolver(), ViajesContract.ViajesEntry.URI_CONTENIDO);
                break;
            case VIAJES_ID:
                id = ViajesEntry.obtenerIdViaje(uri);
                c = bd.query(Tablas.VIAJES, projection,
                        ViajesEntry.V_ID + " = ?",
                        new String[]{id}, null, null, null);
                break;

            case CATEGORIAS:
                c = bd.query(Tablas.CATEGORIAS, projection,
                        selection, selectionArgs, null, null, sortOrder);
                Log.i(TAG, "ViajecitosProvider query 156 uri un CATEGORIAS uri y match: " + uri + " " + match);
                //c.setNotificationUri(getContext().getContentResolver(), ViajesContract.CategoriasEntry.URI_CONTENIDO);
                break;
            case CATEGORIAS_ID:
                id = CategoriasEntry.obtenerIdCategoria(uri);
                c = bd.query(Tablas.CATEGORIAS, projection,
                        CategoriasEntry.CAT_ID + " = ?",
                        new String[]{id}, null, null, null);
                break;

            case MONEDAS:
                c = bd.query(Tablas.MONEDAS, projection,
                        selection, selectionArgs, null, null, sortOrder);
                //c.setNotificationUri(getContext().getContentResolver(), ViajesContract.MonedasEntry.URI_CONTENIDO);
                break;
            case MONEDAS_ID:
                id = MonedasEntry.obtenerIdMoneda(uri);
                c = bd.query(Tablas.MONEDAS, projection,
                        MonedasEntry.MON_ID + " = ?",
                        new String[]{id}, null, null, null);
                break;

            case M_PAGO:
                Log.i(TAG, "VIIIIajes Provider case MMPPPagoOOOOO");
                c = bd.query(Tablas.MPAGO, projection,
                        selection, selectionArgs, null, null, sortOrder);
                //c.setNotificationUri(getContext().getContentResolver(), ViajesContract.MPagoEntry.URI_CONTENIDO);
                break;
            case M_PAGO_ID:
                id = MPagoEntry.obtenerIdMPago(uri);
                c = bd.query(Tablas.MPAGO, projection,
                        MPagoEntry.MPAG_ID + " = ?",
                        new String[]{id}, null, null, null);
                break;

            case TIPO_V:
                c = bd.query(Tablas.TIPOVIAJE, projection,
                        selection, selectionArgs, null, null, sortOrder);
                //c.setNotificationUri(getContext().getContentResolver(), ViajesContract.TipoVEntry.URI_CONTENIDO);
                break;
            case TIPO_V_ID:
                id = TipoVEntry.obtenerIdTipoV(uri);
                c = bd.query(Tablas.TIPOVIAJE, projection,
                        TipoVEntry.TIPO_ID + " = ?",
                        new String[]{id}, null, null, null);
                break;

            default:
                throw new UnsupportedOperationException("URI NO SOPORTADA = " + uri);
        }
        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(resolver, uri);

        return c;

    }

    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "Esty en getType con " + uri );
        switch (uriMatcher.match(uri)) {
            case EVENTOS:
                return ViajesContract.generarMime("Eventos");
            case EVENTOS_ID:
                return ViajesContract.generarMimeItem("Eventos");

            case VIAJES:
                return ViajesContract.generarMime("Viajes");
            case VIAJES_ID:
                return ViajesContract.generarMimeItem("Viajes");

            case CATEGORIAS:
                return ViajesContract.generarMime("Categorias");
            case CATEGORIAS_ID:
                return ViajesContract.generarMimeItem("Categorias");

            case MONEDAS:
                return ViajesContract.generarMime("Monedas");
            case MONEDAS_ID:
                return ViajesContract.generarMimeItem("Monedas");

            case M_PAGO:
                return ViajesContract.generarMime("MPago");
            case M_PAGO_ID:
                return ViajesContract.generarMimeItem("MPago");

            case TIPO_V:
                return ViajesContract.generarMime("TipoV");
            case TIPO_V_ID:
                return ViajesContract.generarMimeItem("TipoV");

            default:
                throw new UnsupportedOperationException("Uri desconocida =>" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        ///////////////////////////////////////////////////aquí siiiii llegaaaaaaaaaaaaaaaaaaaaaaaaaa
        Log.d(TAG, "Inserción en " + uri );
        SQLiteDatabase bd = databaseHelper.getWritableDatabase();
        String id = null;

        switch (uriMatcher.match(uri)) {

            case VIAJES:
                bd.insertOrThrow(Tablas.VIAJES, null, values);
                notificarCambio(uri);
                return ViajesEntry.crearUriViajes(values.getAsString(Tablas.VIAJES));

            case EVENTOS:
                bd.insertOrThrow(Tablas.EVENTOS, null, values);
                notificarCambio(uri);
                return EventosEntry.crearUriEvento(values.getAsString(Tablas.EVENTOS));

            case CATEGORIAS:
                bd.insertOrThrow(Tablas.CATEGORIAS, null, values);
                notificarCambio(uri);
                return CategoriasEntry.crearUriCategorias(values.getAsString(Tablas.CATEGORIAS));

            case MONEDAS:
                bd.insertOrThrow(Tablas.MONEDAS, null, values);
                notificarCambio(uri);
                return MonedasEntry.crearUriMonedas(values.getAsString(Tablas.MONEDAS));

            case M_PAGO:
                bd.insertOrThrow(Tablas.MPAGO, null, values);
                notificarCambio(uri);
                return MPagoEntry.crearUriMPago(values.getAsString(Tablas.MPAGO));

            case TIPO_V:
                bd.insertOrThrow(Tablas.TIPOVIAJE, null, values);
                notificarCambio(uri);
                return TipoVEntry.crearUriTipoV(values.getAsString(Tablas.TIPOVIAJE));

            default:
                throw new UnsupportedOperationException("URI_NO_SOPORTADA" + uri);
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete: " + uri); //hasta aquí llega siii

        SQLiteDatabase bd = databaseHelper.getWritableDatabase();
        String id = null;
        int afectados;

        switch (uriMatcher.match(uri)) {
            case EVENTOS_ID:
                Log.d(TAG, "delete EVENTOS_ID : " + uri);
                id = EventosEntry.obtenerIdEvento(uri);
                afectados = bd.delete(Tablas.EVENTOS, EventosEntry.E_ID + " = ?", new String[]{id});
                notificarCambio(uri);
                break;

            case VIAJES_ID:
                Log.d(TAG, "delete VIAJES_ID : " + uri);
                id = ViajesEntry.obtenerIdViaje(uri);
                afectados = bd.delete(Tablas.VIAJES, ViajesEntry.V_ID + " = ?", new String[]{id});
                notificarCambio(uri);
                break;

            case CATEGORIAS_ID:
                Log.d(TAG, "delete CATEGORIAS_ID : " + uri);
                id = CategoriasEntry.obtenerIdCategoria(uri);
                afectados = bd.delete(Tablas.CATEGORIAS, CategoriasEntry.CAT_ID + " = ?", new String[]{id});
                Log.d(TAG, "delete CATEGORIAS_ID : ");
                notificarCambio(uri);
                break;

            case TIPO_V_ID:
                Log.d(TAG, "delete TIPO_V_ID : " + uri);
                id = TipoVEntry.obtenerIdTipoV(uri);
                afectados = bd.delete(Tablas.TIPOVIAJE, TipoVEntry.TIPO_ID + " = ?", new String[]{id});
                notificarCambio(uri);
                break;

            case MONEDAS_ID:
                Log.d(TAG, "delete MONEDAS_ID : " + uri);
                id = MonedasEntry.obtenerIdMoneda(uri);
                afectados = bd.delete(Tablas.MONEDAS, MonedasEntry.MON_ID + " = ?", new String[]{id});
                notificarCambio(uri);
                break;

            case M_PAGO_ID:
                Log.d(TAG, "delete M_PAGO_ID : " + uri);
                id = MPagoEntry.obtenerIdMPago(uri);
                afectados = bd.delete(Tablas.MPAGO, MPagoEntry.MPAG_ID + " = ?", new String[]{id});
                notificarCambio(uri);
                break;

            default:
                throw new UnsupportedOperationException("URI_NO_SOPORTADA" + uri);
        }
        return afectados;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase bd = databaseHelper.getWritableDatabase();
        int afectados;
        String id = null;
        Log.d(TAG, "Esty en 360 UPDATE con " + uri );
        switch (uriMatcher.match(uri)) {

            case EVENTOS_ID:
                id = EventosEntry.obtenerIdEvento(uri);
                Log.d(TAG, "Esty en 364 UPDATE con case EVENTOS_ID uri:" + uri );
                afectados = bd.update(Tablas.EVENTOS, values,
                        EventosEntry.E_ID + " = ?", new String[]{id});
                notificarCambio(uri);
                ///////////

                ///////
                break;

            case VIAJES_ID:
                Log.d(TAG, "Esty en 362 UPDATE con case VIAJES_ID uri:" + uri );
                afectados = bd.update(Tablas.VIAJES, values,
                        ViajesEntry.V_ID + " = ?", new String[]{id});
                notificarCambio(uri);
                break;

            case CATEGORIAS_ID:
                id = CategoriasEntry.obtenerIdCategoria(uri);
                Log.d(TAG, "Esty en 369 UPDATE con case CATEGORIAS_ID uri: " + uri );
                afectados = bd.update(Tablas.CATEGORIAS, values,
                        CategoriasEntry.CAT_ID + " = ?", new String[]{id});
                Log.d(TAG, "Esty en 372 UPDATE con case CATEGORIAS_ID values: " + values ); //llega biennnnn
                notificarCambio(uri);
                break;

            case MONEDAS_ID:
                id = MonedasEntry.obtenerIdMoneda(uri);
                Log.d(TAG, "Esty en 376 UPDATE con case MONEDAS_ID uri: " + uri );
                afectados = bd.update(Tablas.MONEDAS, values,
                        MonedasEntry.MON_ID + " = ?", new String[]{id});
                notificarCambio(uri);
                break;

            case M_PAGO_ID:
                Log.d(TAG, "Esty en 383 UPDATE con case MPAGO_ID uri:" + uri );
                afectados = bd.update(Tablas.MPAGO, values,
                        MPagoEntry.MPAG_ID + " = ?", new String[]{id});
                notificarCambio(uri);
                break;

            case TIPO_V_ID:
                Log.d(TAG, "Esty en 390 UPDATE con case TIPOV_ID uri:" + uri );
                afectados = bd.update(Tablas.TIPOVIAJE, values,
                        TipoVEntry.TIPO_ID + " = ?", new String[]{id});
                notificarCambio(uri);
                break;

            default:
                throw new UnsupportedOperationException("URI NO SOPORTADA" + uri);
        }

        return afectados;
    }
      private void notificarCambio(Uri uri) {
        resolver.notifyChange(uri, null);
    }
}
