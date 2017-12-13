package almeida.francisco.forestboundaries.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fmpap on 13/12/2017.
 */

public class MyHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "forest_boundaries";
    private static final int DB_VERSION = 1;

    public static final String _ID = "_id";
    public static final String TABLE_OWNERS = "owners";
    public static final String OWNERS_NAME = "name";
    public static final String TABLE_PROPERTIES = "properties";
    public static final String PROPS_OWNER_ID = "owner_id";
    public static final String PROPS_DESCRIP = "description";

    public MyHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_OWNERS + " (" + _ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                OWNERS_NAME + " TEXT UNIQUE NOT NULL);");
        db.execSQL("CREATE TABLE " + TABLE_PROPERTIES + " (" + _ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PROPS_OWNER_ID + " INTEGER NOT NULL, " +
                PROPS_DESCRIP + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        throw new UnsupportedOperationException("" + i + " to " + i1);
    }
}
