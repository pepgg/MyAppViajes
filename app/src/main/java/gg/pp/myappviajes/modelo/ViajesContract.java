package gg.pp.myappviajes.modelo;

import android.net.Uri;
import android.util.Log;
import java.util.UUID;

/**
 * Created by pepe on 4/03/16.
 */
public class ViajesContract {

    public static final String TAG = "Provider";

    interface ColumnasEventos {
        String E_ID = DatabaseHelper.E_ID;
        String E_IDV =  DatabaseHelper.E_IDV;
        String E_IDCGT = DatabaseHelper.E_IDCGT;
        String E_DATAH = DatabaseHelper.E_DATAH;
        String E_KMP = DatabaseHelper.E_KMP;
        String E_NOM = DatabaseHelper.E_NOM;
        String E_DESC = DatabaseHelper.E_DESC;
        String E_MPAG = DatabaseHelper.E_MPAG;
        String E_MON = DatabaseHelper.E_MON;
        String E_TOT = DatabaseHelper.E_TOT;
        String E_FOT1 = DatabaseHelper.E_FOT1;
        String E_FOT2 = DatabaseHelper.E_FOT2;
        String E_VAL = DatabaseHelper.E_VAL;
        String E_DIR = DatabaseHelper.E_DIR;
        String E_CP = DatabaseHelper.E_CP;
        String E_CIUD = DatabaseHelper.E_CIUD;
        String E_TEL = DatabaseHelper.E_TEL;
        String E_MAIL = DatabaseHelper.E_MAIL;
        String E_WEB = DatabaseHelper.E_WEB;
        String E_LON = DatabaseHelper.E_LON;
        String E_LAT = DatabaseHelper.E_LAT;
        String E_ALT = DatabaseHelper.E_ALT;
        String E_COM = DatabaseHelper.E_COM;
    }

    interface ColumnasViajes {
        String V_ID = DatabaseHelper.V_ID;
        String V_NOM = DatabaseHelper.V_NOM;
        String V_DATAIN = DatabaseHelper.V_DATAIN;
        String V_DATAFI = DatabaseHelper.V_DATAFI;
        String V_KMIN = DatabaseHelper.V_KMIN;
        String V_KMFI = DatabaseHelper.V_KMFI;
        String V_TIPO = DatabaseHelper.V_TIPO;
        String V_DESC = DatabaseHelper.V_DESC;
        String V_TGAST = DatabaseHelper.V_TGAST;
        String V_TKM = DatabaseHelper.V_TKM;
    }

    interface ColumnasCategorias {
        String CAT_ID = DatabaseHelper.CAT_ID;
        String CAT_CGT = DatabaseHelper.CAT_CGT;
    }

    interface ColumnasMonedas {
        String MON_ID = DatabaseHelper.MON_ID;
        String MON_NOM = DatabaseHelper.MON_NOM;
        String MON_VAL = DatabaseHelper.MON_VAL;
    }

    interface ColumnasMPago {
        String MPAG_ID = DatabaseHelper.MPAG_ID;
        String MPAG_MP = DatabaseHelper.MPAG_MP;
    }

    interface ColumnasTipoV {
        String TIPO_ID = DatabaseHelper.TIPO_ID;
        String TIPO_TIPO = DatabaseHelper.TIPO_TIPO;
    }
////////////////////////////////////
// [URIS]
public static final String AUTORIDAD = "gg.pp.myappviajes";

    public static final Uri URI_BASE = Uri.parse("content://" + AUTORIDAD);

    private static final String RUTA_EVENTOS = "eventos";
    private static final String RUTA_VIAJES = "viajes";
    private static final String RUTA_CATEGORIAS = "categorias";
    private static final String RUTA_MONEDAS = "monedas";
    private static final String RUTA_MPAGO = "mpago";
    private static final String RUTA_TIPOV = "tipov";

    // [URI_MATCHER] estan al provider

    // [TIPOS_MIME]
    // <<<<<<<<<Techs pone un SINGEL_MIME y un MULTIPLE_MIME

    public static final String BASE_CONTENIDOS = "myappviajes.";
    public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd." + BASE_CONTENIDOS;
    public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd." + BASE_CONTENIDOS;

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

    public static class EventosEntry implements ColumnasEventos {
        public static final String TABLE_NAME = RUTA_EVENTOS;
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(TABLE_NAME).build();

        public static final String PARAMETRO_FILTRO = "filtro";
        public static final String FILTRO_VIAJE = "viaje";
        public static final String FILTRO_TOTAL_G = "totalg";
        public static final String FILTRO_FECHA = "fecha";
      //  Log.i(TAG, "ViajecitContract EventosEntry un poquito" + URI_CONTENIDO);
     //   Log.i("TAG", "ViajecitosProvider onCreate un poquit<<<<<o " + URI_CONTENIDO);//este es bueno

        /*
        public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
                + URI_CONTENIDO + "/" + RUTA_EVENTOS;
        public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
                + URI_CONTENIDO + "/" + RUTA_EVENTOS;
      */
        public static Uri crearUriEvento(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }
        public static Uri crearUriParaViajes(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).appendPath("viajes").build();
        }
        public static boolean tieneFiltro(Uri uri) {
            return uri != null && uri.getQueryParameter(PARAMETRO_FILTRO) != null;
        }

        public static String generarIdEvento() {
            return "EV-" + UUID.randomUUID().toString();
        }
        public static String obtenerIdEvento(Uri uri) {
           return uri.getLastPathSegment();         } // lo cambio por el siguiente:
      //  public static String obtenerIdEvento(Uri uri) {
      //      return uri.getLastPathSegment();
      //  }
    }



    public static class ViajesEntry implements ColumnasViajes {
        public static final String TABLE_NAME = RUTA_VIAJES;
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_VIAJES).build();
        /******quito esto que no está en pedidos:
    public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
            + URI_CONTENIDO + "/" + RUTA_VIAJES;
    public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
            + URI_CONTENIDO + "/" + RUTA_VIAJES;
         */
        public static Uri crearUriViajes(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }
        public static String obtenerIdViaje(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

/*
        public static Uri crearUriViajes(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).appendPath("viajes").build();
         }
*/

    public static class CategoriasEntry implements ColumnasCategorias {
        public static final String TABLE_NAME = RUTA_CATEGORIAS;
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_CATEGORIAS).build();
        /*
        public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
                + URI_CONTENIDO + "/" + RUTA_CATEGORIAS;
        public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
                + URI_CONTENIDO + "/" + RUTA_CATEGORIAS;
        */
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

    public static class MonedasEntry implements ColumnasMonedas {
    public static final String TABLE_NAME = RUTA_MONEDAS;
    public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_MONEDAS).build();

        /*
        public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
            + URI_CONTENIDO + "/" + RUTA_MONEDAS;
    public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
            + URI_CONTENIDO + "/" + RUTA_MONEDAS;
        */
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

    public static class MPagoEntry implements ColumnasMPago {
    public static final String TABLE_NAME = RUTA_MPAGO;
    public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_MPAGO).build();
        public static final String[] TAG_COLUMNS = {"_id","mpago"};
/*
    public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
            + URI_CONTENIDO + "/" + RUTA_MPAGO;
    public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
            + URI_CONTENIDO + "/" + RUTA_MPAGO;
        */
        public static Uri crearUriMPago(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static String generarIdMPago() {
            return "MP-" + UUID.randomUUID().toString();
        }

        public static String obtenerIdMPago(Uri uri) {
            return uri.getLastPathSegment();
        }
}

    public static class TipoVEntry implements ColumnasTipoV {
        public static final String TABLE_NAME = RUTA_TIPOV;
        public static final String NAME_TIPO = TIPO_TIPO;
        public static final String COLUMN_NAME = "tipov";
        public static final String[] TAG_COLUMNS = {"_id","tipov"};
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_TIPOV).build();
       /*
        public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
                + URI_CONTENIDO + "/" + RUTA_TIPOV;
        public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
                + URI_CONTENIDO + "/" + RUTA_TIPOV;
        */
       public static Uri crearUriTipoV(String id) {
           return URI_CONTENIDO.buildUpon().appendPath(id).build();
       }

        public static String generarIdTipoV() {
            return "TV-" + UUID.randomUUID().toString();
        }
        public static String obtenerIdTipoV(Uri uri) {
            return uri.getPathSegments().get(1);
        }
        public void setName(String name){
            this.getName();
        }

        public String getName(){
            return this.TIPO_TIPO;
        }
    }

    private ViajesContract() {
    }

}
