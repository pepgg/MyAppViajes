package gg.pp.myappviajes.modelo;

import android.content.UriMatcher;
import android.net.Uri;

/**
 * Created by pepe on 4/03/16.
 */
public class ViajesContract {
    public static final String TAG = "Provider";

    // [URIS]

    public static final String AUTORIDAD = "gg.pp.myappviajes.modelo.ViajesProvider";

    public static final Uri URI_BASE = Uri.parse("content://" + AUTORIDAD);

    private static final String RUTA_EVENTOS = "eventos";
    private static final String RUTA_VIAJES = "viajes";
    private static final String RUTA_CATEGORIAS = "categorias";
    private static final String RUTA_MONEDAS = "monedas";
    private static final String RUTA_MPAGO = "mpago";
    private static final String RUTA_TIPOV = "tipov";

    // [URI_MATCHER]
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

        uriMatcher.addURI(AUTORIDAD, "viajes", VIAJES);
        uriMatcher.addURI(AUTORIDAD, "viajes/#", VIAJES_ID);
 //       uriMatcher.addURI(AUTORIDAD, "viajes/*/detalles", VIAJES_DET);

        uriMatcher.addURI(AUTORIDAD, "eventos", EVENTOS);
        uriMatcher.addURI(AUTORIDAD, "eventos/#", EVENTOS_ID);
//        uriMatcher.addURI(AUTORIDAD, "eventos/*/detalles", EVENTOS_DET);

        uriMatcher.addURI(AUTORIDAD, "categorias", CATEGORIAS);
        uriMatcher.addURI(AUTORIDAD, "categorias/#", CATEGORIAS_ID);

        uriMatcher.addURI(AUTORIDAD, "monedas", MONEDAS);
        uriMatcher.addURI(AUTORIDAD, "monedas/#", MONEDAS_ID);

        uriMatcher.addURI(AUTORIDAD, "m_pago", M_PAGO);
        uriMatcher.addURI(AUTORIDAD, "m_pago/#", M_PAGO_ID);

        uriMatcher.addURI(AUTORIDAD, "tipo_v", TIPO_V);
        uriMatcher.addURI(AUTORIDAD, "tipo_v/#", TIPO_V_ID);
    }
    // [/URI_MATCHER]

    // [/URIS]

/*
    // [TIPOS_MIME]
    // <<<<<<<<<Techs pone un SINGEL_MIME y un MULTIPLE_MIME


    public static final String BASE_CONTENIDOS = "myappviajes."; //<<<<si aquí va el nom tabla, necesito los 6


    public static String generarMime(String id) {
        Log.i(TAG, "ViajesContract generarMimecita view un poquito");
        if (id != null) {
            return TIPO_CONTENIDO + id;
        } else {
            return null;
        }
    }

    public static String generarMimeItem(String id) {
        if (id != null) {
            return TIPO_CONTENIDO_ITEM + id;
        } else {
            return null;
        }
    }
    // [/TIPOS_MIME]
    */
    interface ColumnasEventos {
        String E_ID = "id";
        String E_IDV = "idviaje";
        String E_IDCGT = "idcateg";
        String E_DATAH = "fechah";
        String E_KMP = "kmp";
        String E_NOM = "nom";
        String E_DESC = "descripcio";
        String E_MPAG = "modpag";
        String E_MON = "moneda";
        String E_TOT = "totaleur";
        String E_FOT1 = "foto1";
        String E_FOT2 = "foto2";
        String E_VAL = "valoracion";
        String E_DIR = "callenum";
        String E_CP = "cp";
        String E_CIUD = "ciudad";
        String E_TEL = "telef";
        String E_MAIL = "mail";
        String E_WEB = "web";
        String E_LON = "longitud";
        String E_LAT = "latitud";
        String E_ALT = "altitud";
        String E_COM = "comentari";
    }

    interface ColumnasViajes {
        String V_ID = "id";
        String V_NOM = "nom";
        String V_DATAIN = "datain";
        String V_DATAFI = "datafi";
        String V_KMIN = "kmin";
        String V_KMFI = "kmfi";
        String V_TIPO = "tipo";
        String V_DESC = "descrip";
        String V_TGAST = "tgast";
        String V_TKM = "tkm";
    }

    interface ColumnasCategorias {
        String CAT_ID = "id";
        String CAT_CGT = "categoria";
    }

    interface ColumnasMonedas {
        String MON_ID = "id";
        String MON_NOM = "nom";
        String MON_VAL = "valor";
    }

    interface ColumnasMPago {
        String MPAG_ID = "id";
        String MPAG_MP = "mpago";
    }

    interface ColumnasTipoV {
        String TIPO_ID = "id";
        String TIPO_TIPO = "tipov";
    }



    public static class EventosEntry implements ColumnasEventos {
        public static final String TABLE_NAME = RUTA_EVENTOS;

        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_EVENTOS).build();
        public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
                + URI_CONTENIDO + "/" + RUTA_EVENTOS;
        public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
                + URI_CONTENIDO + "/" + RUTA_EVENTOS;


        public static final String PARAMETRO_FILTRO = "filtro";
        public static final String FILTRO_VIAJE = "viaje";
        public static final String FILTRO_TOTAL_G = "totalg";
        public static final String FILTRO_FECHA = "fecha";
    }
/*
        public static String obtenerIdEvento(Uri uri) {
        //    return uri.getPathSegments().get(1);         } // lo cambio por el siguiente:
        //public static String obtenerIdEvento(Uri uri) {
        //    return uri.getLastPathSegment();
        }

        public static Uri crearUriEvento(String id) {
        //    return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static Uri crearUriParaViajes(String id) {
        //    return URI_CONTENIDO.buildUpon().appendPath(id).appendPath("viajes").build();
        }

        public static boolean tieneFiltro(Uri uri) {
         //   return uri != null && uri.getQueryParameter(PARAMETRO_FILTRO) != null;
        }

        public static String generarIdEvento() {
            return "EV-" + UUID.randomUUID().toString();
        }
    }

*/
    public static class ViajesEntry implements ColumnasViajes {
    public static final String TABLE_NAME = RUTA_VIAJES;

    public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_VIAJES).build();
    public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
            + URI_CONTENIDO + "/" + RUTA_VIAJES;
    public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
            + URI_CONTENIDO + "/" + RUTA_VIAJES;
}

/*
        public static Uri crearUriViajes(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static Uri crearUriViajes(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).appendPath("viajes").build();
         }


        //public static String[] obtenerIdViaje(Uri uri) {
        public static String obtenerIdViaje(Uri uri) {
            return uri.getLastPathSegment();
        }
        }
    */

    public static class CategoriasEntry implements ColumnasCategorias {
        public static final String TABLE_NAME = RUTA_CATEGORIAS;
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_CATEGORIAS).build();
        public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
                + URI_CONTENIDO + "/" + RUTA_CATEGORIAS;
        public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
                + URI_CONTENIDO + "/" + RUTA_CATEGORIAS;
    }
/*
        public static Uri crearUriCategorias(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static String generarIdCategoria() {
            return "CAT-" + UUID.randomUUID().toString();
        }

        public static String obtenerIdCategoria(Uri uri) {
            return uri.getLastPathSegment();
        }
    }
*/
    public static class MonedasEntry implements ColumnasMonedas {
    public static final String TABLE_NAME = RUTA_MONEDAS;
    public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_MONEDAS).build();
    public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
            + URI_CONTENIDO + "/" + RUTA_MONEDAS;
    public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
            + URI_CONTENIDO + "/" + RUTA_MONEDAS;
}
/*
        public static Uri crearUriMonedas(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static String generarIdMoneda() {
            return "MO-" + UUID.randomUUID().toString();
        }

        public static String obtenerIdMoneda(Uri uri) {
            return uri.getLastPathSegment();
        }
    }
*/
    public static class MPagoEntry implements ColumnasMPago {
    public static final String TABLE_NAME = RUTA_MPAGO;
    public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_MPAGO).build();
    public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
            + URI_CONTENIDO + "/" + RUTA_MPAGO;
    public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
            + URI_CONTENIDO + "/" + RUTA_MPAGO;
}
/*
        public static Uri crearUriMPago(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static String generarIdMPago() {
            return "MP-" + UUID.randomUUID().toString();
        }

        public static String obtenerIdMPago(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
*/

    public static class TipoVEntry implements ColumnasTipoV {
        public static final String TABLE_NAME = RUTA_TIPOV;
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_TIPOV).build();
        public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
                + URI_CONTENIDO + "/" + RUTA_TIPOV;
        public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
                + URI_CONTENIDO + "/" + RUTA_TIPOV;
    }


/*

        public static Uri crearUriTipoV(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static String generarIdTipoV() {
            return "TV-" + UUID.randomUUID().toString();
        }

        public static String obtenerIdTipoV(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    private ViajesContract() {
    }
*/
}
