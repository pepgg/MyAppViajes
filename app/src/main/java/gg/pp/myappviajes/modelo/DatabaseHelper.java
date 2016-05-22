package gg.pp.myappviajes.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

/**
 * Created by pepe on 4/03/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "cpviajes.db";
    private static final int VERSION_ACTUAL = 1;
    private final Context contexto;

    public DatabaseHelper(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = contexto;
    }


    /////////////////////////////esta interface no esta en techs
    interface Tablas {
        String EVENTOS = "eventos";
        String VIAJES = "viajes";
        String CATEGORIAS = "categorias";
        String MONEDAS = "monedas";
        String MPAGO = "mpago";
        String TIPOVIAJE = "tipov";
    }

    interface Referencias {

        String ID_EVENTOS = String.format("REFERENCES %s(%s) ON DELETE CASCADE",
                //String ID_EVENTOS = String.format("REFERENCES %s(%s)",
                Tablas.EVENTOS, ViajesContract.EventosEntry.E_ID);

        String ID_CATEGORIAS = String.format("REFERENCES %s(%s)",
                Tablas.CATEGORIAS, ViajesContract.CategoriasEntry.CAT_ID);

        String ID_MONEDAS = String.format("REFERENCES %s(%s)",
                Tablas.MONEDAS, ViajesContract.MonedasEntry.MON_ID);

        String ID_MPAGO = String.format("REFERENCES %s(%s)",
                Tablas.MPAGO, ViajesContract.MPagoEntry.MPAG_ID);

        String ID_TIPOV = String.format("REFERENCES %s(%s)",
                Tablas.TIPOVIAJE, ViajesContract.TipoVEntry.TIPO_ID);
        String ID_VIAJES = String.format("REFERENCES %s(%s)",
                Tablas.VIAJES, ViajesContract.ViajesEntry.V_ID);
    }


    ///////////////////////////////////////////
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON"); //http://stackoverflow.com/questions/2545558/foreign-key-constraints-in-android-using-sqlite-on-delete-cascade
                //Foreign key constraints with on delete cascade are supported, but you need to enable them.
                // I just added the PRAGMA to my SQLOpenHelper, which seems to do the trick.
            }
        }
    }
    //////
    public static final String V_ID = "_id";
    public static final String V_NOM = "nom";
    public static final String V_DATAIN = "datain";
    public static final String V_DATAFI = "datafi";
    public static final String V_KMIN = "kmin";
    public static final String V_KMFI = "kmfi";
    public static final String V_TIPO = "tipo";
    public static final String V_DESC = "descrip";
    public static final String V_TGAST = "tgast";
    public static final String V_TKM = "tkm";

    public static final String CAT_ID = "_id";
    public static final String CAT_CGT = "categoria";

    public static final String MPAG_ID = "_id";
    public static final String MPAG_MP = "mpago";

    public static final String TIPO_ID = "_id";
    public static final String TIPO_TIPO = "tipov";

    public static final String MON_ID = "_id";
    public static final String MON_NOM = "nom";
    public static final String MON_VAL = "valor";

    public static final String E_ID = "_id";
    public static final String E_IDV = "idviaje";
    public static final String E_IDCGT = "idcateg";
    public static final String E_DATAH = "fechah";
    public static final String E_KMP = "kmp";
    public static final String E_NOM = "nom";
    public static final String E_DESC = "descripcio";
    public static final String E_MPAG = "modpag";
    public static final String E_MON = "moneda";
    public static final String E_TOT = "totaleur";
    public static final String E_FOT1 = "foto1";
    public static final String E_FOT2 = "foto2";
    public static final String E_VAL = "valoracion";
    public static final String E_DIR = "callenum";
    public static final String E_CP = "cp";
    public static final String E_CIUD = "ciudad";
    public static final String E_TEL = "telef";
    public static final String E_MAIL = "mail";
    public static final String E_WEB = "web";
    public static final String E_LON = "longitud";
    public static final String E_LAT = "latitud";
    public static final String E_ALT = "altitud";
    public static final String E_COM = "comentari";

    public static DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public static Integer idViajeA;
    public static String nomViajeA;
    public static String nomViaje;
    public static Float sumaTgasto;
    public static Float valorMon;

    //public static final String NOU_TIPO = "@string/nou_modo";  tengo que arreglar esto, para las traducciones

    private static final String TAG = "En DatabaseHelper: ";

    public static final String TABLE_V = "viajes";
    public static final String TABLE_EVENT = "eventos";
    public static final String TABLE_MON = "monedas";
    public static final String TABLE_TIPO = "tipov";
    public static final String TABLE_CAT = "categorias";
    public static final String TABLE_MPAG = "mpago";

    private static final String V_TABLE =
            "create table " + TABLE_V + " (_id integer primary key autoincrement, "
                    + V_NOM + " text not null, " + V_DATAIN + " date not null, " + V_DATAFI + " date, " + V_KMIN + " integer, "
                    + V_KMFI + " integer, " + V_TIPO + " text, " + V_DESC + " text, " + V_TGAST + " integer, " + V_TKM + " integer);";

    private static final String E_TABLE =
            "create table " + TABLE_EVENT + " (_id integer primary key autoincrement, "
                    + E_IDV + " integer, " + E_IDCGT + " integer, " + E_DATAH + " date not null, " + E_KMP + " integer, "
                    + E_NOM + " text, " + E_DESC + " text, " + E_MPAG + " integer, "
                    + E_MON +" integer, " + E_TOT + " float, " + E_FOT1 + " text, " + E_FOT2 + " text, " + E_VAL + " text, "
                    + E_DIR + " text, " + E_CP + " text, " + E_CIUD + " text, " + E_TEL + " text, " + E_MAIL + " text, "
                    + E_WEB + " text, " + E_LON + " text, " + E_LAT + " text, " + E_ALT + " text, " + E_COM + " text);";

    private static final String TIPO_TABLE =
            "create table " + TABLE_TIPO +" (_id integer primary key autoincrement, "
                    + TIPO_TIPO + " text);";

    private static final String CAT_TABLE =
            "create table " + TABLE_CAT + " (_id integer primary key autoincrement, "
                    + CAT_CGT + " text);";

    private static final String MPAG_TABLE =
            "create table " + TABLE_MPAG + " (_id integer primary key autoincrement, "
                    + MPAG_MP + " text);";

    private static final String MON_TABLE =
            "create table " + TABLE_MON + " (_id integer primary key autoincrement, "
                    + MON_NOM + " text, " + MON_VAL + " float);";
    //////
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Ahora onCreate(SQLiteDatabase db) ");
        db.execSQL(V_TABLE);
        Log.i(TAG, "creada la tabla " + TABLE_V);
        db.execSQL(E_TABLE);
        Log.i(TAG, "creada la tabla " + TABLE_EVENT);
        db.execSQL(MON_TABLE);
        Log.i(TAG, "creada la tabla " + TABLE_MON);
        db.execSQL(TIPO_TABLE);
        Log.i(TAG, "creada la tabla " + TABLE_TIPO);
        db.execSQL(CAT_TABLE);
        Log.i(TAG, "creada la tabla " + TABLE_CAT);
        db.execSQL(MPAG_TABLE);
        Log.i(TAG, "creada la tabla " + TABLE_MPAG);

        //  valores iniciales de los spinners:
        db.execSQL("INSERT INTO tipov (tipov) VALUES ('Autocaravana')");
        db.execSQL("INSERT INTO tipov (tipov) VALUES ('coche')");
        db.execSQL("INSERT INTO tipov (tipov) VALUES ('avión')");
        db.execSQL("INSERT INTO tipov (tipov) VALUES ('tren')");
        db.execSQL("INSERT INTO tipov (tipov) VALUES ('barco')");
        Log.i(TAG, "insertado en la tabla " + TABLE_TIPO);

        db.execSQL("INSERT INTO categorias (categoria) VALUES ('desplazamientos')");
        db.execSQL("INSERT INTO categorias (categoria) VALUES ('alojamiento')");
        db.execSQL("INSERT INTO categorias (categoria) VALUES ('restaurantes')");
        db.execSQL("INSERT INTO categorias (categoria) VALUES ('comida')");
        db.execSQL("INSERT INTO categorias (categoria) VALUES ('regalos')");
        Log.i(TAG, "insertado en la tabla " + TABLE_CAT);

        db.execSQL("INSERT INTO mpago (mpago) VALUES ('efectivo')");
        db.execSQL("INSERT INTO mpago (mpago) VALUES ('ing')");
        db.execSQL("INSERT INTO mpago (mpago) VALUES ('lcaixa')");
        db.execSQL("INSERT INTO mpago (mpago) VALUES ('visalc')");
        db.execSQL("INSERT INTO mpago (mpago) VALUES ('visaing')");
        Log.i(TAG, "insertado en la tabla " + TABLE_MPAG);

        db.execSQL("INSERT INTO monedas (nom, valor) VALUES ('GBP', '1,4')");
        db.execSQL("INSERT INTO monedas (nom, valor) VALUES ('NOK', '1,6')");
        db.execSQL("INSERT INTO monedas (nom, valor) VALUES ('SEK', '1,2')");
        db.execSQL("INSERT INTO monedas (nom, valor) VALUES ('DNK', '0,4')");
        Log.i(TAG, "insertado en la tabla " + TABLE_MON);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //esborrar TOTES les taules
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.EVENTOS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.VIAJES);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.CATEGORIAS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.MONEDAS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.MPAGO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TIPOVIAJE);

        onCreate(db);   //crearlas de nuevo
    }

    public DatabaseHelper open() throws SQLException {
        mDbHelper = new DatabaseHelper(contexto);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
///esto que sygue no funcionaba en MyCPViajes::::::::::::::::::::::::::::::::::::::::: para buscar el viaje activo
    public Cursor buscaViaje(long mId) throws SQLException {
        Log.i(TAG, "En <miDbadapter buscaViaje El MiD viajes " + mId); //este recibe el id del que vamos a editar. podria estar acabado
        Cursor mCursor =
                mDb.query(true, TABLE_V, new String[] {V_ID, V_NOM, V_DATAIN, V_DATAFI,
                                V_KMIN, V_KMFI, V_TIPO, V_DESC}, V_ID + "=" + mId, null,
                        null, null, null, null);

        if (mCursor != null) {

            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Integer idViaje() { //busca el viaje activo
        //esto funciona?
        //  Cursor idVactiv = mDb.rawQuery("SELECT " + V_ID + " from "+ TABLE_V + " where "+ V_DATAFI +"=''", null);
        //  Cursor idVactiv = mDb.rawQuery("SELECT " + V_ID + " from " + TABLE_V ", null);

        Cursor idVactiv = mDb.rawQuery("SELECT * from viajes", null);
        int numvidViajes;
        numvidViajes = idVactiv.getCount();
        Log.i(TAG, " en idViaje dice que tengo " + numvidViajes + "viajes activos");
        //Si existe al menos un registro
        if (idVactiv.moveToFirst()) {
            int idViajeA= idVactiv.getInt(0);
            Log.i(TAG, "en MiDbAdapter, el idviaje activo en el if es " + idViajeA); //perfectoooo???
            //String nomViajeA = idVactiv.getString(1); //tengo que arreglarlo para conseguir el nombre
            return idViajeA;
        }
        Log.i(TAG, "en MiDbAdapter, el idviaje activo después del if es " + idViajeA);
        return idViajeA;
    }

    public String nomViaje() {

        Cursor nomVactiv = mDb.rawQuery("SELECT " + V_NOM + " from "+ TABLE_V + " where "+ V_DATAFI + " = '' ", null);
        //Si existe al menos un registro
        if (nomVactiv.moveToFirst()) {
            nomViajeA = nomVactiv.getString(0); //para conseguir el nombre
        }
        Log.i(TAG, "en BaseDatosViajesrrrrr, el nomviaje consulta nomViaje es " + nomViajeA);  //  LO tiene??
               return nomViajeA;
    }
    public Float valMoneda() {
//String idmoneda = InsertFragmentEv.;
        Cursor valorM = mDb.rawQuery("SELECT " + MON_VAL + " from "+ TABLE_MON + " where "+ MON_ID + " = '' ", null);
        //Si existe al menos un registro
        if (valorM.moveToFirst()) {
            valorMon = valorM.getFloat(0); //para conseguir el valor
        }
        Log.i(TAG, "en Databasehelper, el valor de la moneda es es " + valorMon);  //  LO tiene??
        return valorMon;
    }
    //////////tngo que arreglar estooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
    /////////////////////////////////////////////////////////////ooooooooooooooooooooooooooooooooooooooo
    public Cursor buscaTotMpago(){ //para llenar el spinner de gastos
        ///////////////////////// mDb tiene que ser la base de datossssssssssssssssssssss
        Log.i(TAG, " elestoy en buscaTotMpago ");
        Cursor mCursor = mDb.rawQuery("SELECT * FROM mpago ", null);
        int numpago;
        numpago = mCursor.getCount();
        Log.i(TAG, " en buscaTotMpago dice que tengo " + numpago);
        return mDb.query(Tablas.MPAGO, new String[] {MPAG_ID, MPAG_MP}, null, null, null, null, null);
    }
    public Cursor buscaTotMonedas(){ //para llenar el spinner de gastos
        Log.i(TAG, " elestoy en buscaTotMonedas ");
        Cursor mCursor = mDb.rawQuery("SELECT * FROM monedas ", null);
        int nummonedas;
        nummonedas = mCursor.getCount();
        Log.i(TAG, " en buscaTotMonedas dice que tengo " + nummonedas);
        return mDb.query(Tablas.MONEDAS, new String[] {MON_ID, MON_NOM}, null, null, null, null, null);
    }
    public Cursor buscaTotTipov(){ //para llenar el spinner de tipoV
        Log.i(TAG, " elestoy en buscaTotTipov ");
        Cursor mCursor = mDb.rawQuery("SELECT * FROM tipov ", null);
        int numtipov;
        numtipov = mCursor.getCount();
        Log.i(TAG, " en buscaTotTipov dice que tengo " + numtipov);
        return mDb.query(Tablas.TIPOVIAJE, new String[] {TIPO_ID, TIPO_TIPO}, null, null, null, null, null);
    }

    public Float sumaTotGastos() { //saca el total de gastos del viaje activo ++++ o el cambio de monedas
        Log.i(TAG, " elestoy en sumaTotGastos ");

        Cursor mCursor = mDb.rawQuery("select sum("+ ViajesContract.EventosEntry.E_TOT + ") from " +
                ViajesContract.EventosEntry.TABLE_NAME + "where " + ViajesContract.EventosEntry.E_IDV + " = '2'", null);

        //if (mCursor.moveToFirst()) {
        //	Integer sumaTgasto = mCursor.getInt(0);
        //Log.i(TAG, " en sumaTotGastos dentro del if que total gastado: " + sumaTgasto); //aqui ññega biennnnnnnnnnnnnnnnnnn
        //}
        mCursor.moveToFirst();
        Float sumaTgasto = mCursor.getFloat(0);
        Log.i(TAG, " en sumaTotGastos dice que total gastado: " + sumaTgasto);	// lo tiene<<<<<<<<
        return sumaTgasto;


        //int sumaTgasto;
        //Integer sumaTgasto = mCursor.getInt(0);
        //Log.i(TAG, " en sumaTotGastos dice que total gastado: " + sumaTgasto);

        //return sumaTgasto;
        //mDb.query(TABLE_CGAST, new String[] {CGAST_ID, CGAST_CG}, null, null, null, null, null);
    }
/*
    public List<ViajesContract.TipoVEntry> getAll() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selectQuery = "SELECT  *  FROM " + TABLE_TIPO;

        List<ViajesContract.TipoVEntry> tipovList = new ArrayList<ViajesContract.TipoVEntry>() ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ViajesContract.TipoVEntry tipov  = new ViajesContract.TipoVEntry();
               // student.setAge(cursor.getInt(cursor.getColumnIndex(Student.KEY_age)));
               // student.setEmail(cursor.getString(cursor.getColumnIndex(Student.KEY_email)));
               // student.setName(cursor.getString(cursor.getColumnIndex(Student.KEY_name)));
               // student.setStudent_ID(cursor.getInt(cursor.getColumnIndex(Student.KEY_ID)));
                tipovList.add(tipov);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tipovList;

    }
    */
}
