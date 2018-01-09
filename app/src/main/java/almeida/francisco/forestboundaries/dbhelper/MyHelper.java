package almeida.francisco.forestboundaries.dbhelper;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Francisco Almeida on 13/12/2017.
 */

public class MyHelper extends SQLiteOpenHelper {

    private Context context;

    private static MyHelper instance;

    private static final String DB_NAME = "forest_boundaries";
    private static final int DB_VERSION = 12;

    public static final String _ID = "_id";
    public static final String TABLE_OWNERS = "owners";
    public static final String O_NAME = "name";
    public static final String TABLE_PROPERTIES = "properties";
    public static final String P_OWNER_ID = "owner_id";
    public static final String P_DESCRIP = "description";
    public static final String P_APPROX_SIZE = "approx_size"; //in sq meters
    public static final String P_CALC_SIZE = "calc_size";
    public static final String TABLE_MARKERS = "markers";
    public static final String M_PROPERTY_ID = "property_id";
    public static final String M_AVG_LAT = "avg_lat";
    public static final String M_AVG_LON = "avg_lon";
    public static final String TABLE_READINGS = "readings";
    public static final String R_PROPERTY_ID = "property_id";
    public static final String R_MARKER_ID = "marker_id";
    public static final String R_LAT = "lat";
    public static final String R_LON = "lon";

    private MyHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static synchronized MyHelper getHelper(Context context) {
        if (instance == null) {
            instance = new MyHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_OWNERS + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                O_NAME + " TEXT UNIQUE NOT NULL);");

        db.execSQL("CREATE TABLE " + TABLE_PROPERTIES + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                P_OWNER_ID + " INTEGER NOT NULL, " +
                P_DESCRIP + " TEXT NOT NULL, " +
                P_APPROX_SIZE + " INTEGER, " +
                P_CALC_SIZE + " INTEGER);");

        db.execSQL("CREATE TABLE " + TABLE_MARKERS + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                M_PROPERTY_ID + " INTEGER NOT NULL, " +
                M_AVG_LAT + " REAL, " +
                M_AVG_LON + " REAL);");

        db.execSQL("CREATE TABLE " + TABLE_READINGS + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                R_PROPERTY_ID + " INTEGER, " +
                R_MARKER_ID + " INTEGER, " +
                R_LAT + " REAL, " +
                R_LON + " REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OWNERS + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTIES + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARKERS + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_READINGS + ";");

            onCreate(db);
        }
    }
}
