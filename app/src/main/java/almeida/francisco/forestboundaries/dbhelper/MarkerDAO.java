package almeida.francisco.forestboundaries.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import almeida.francisco.forestboundaries.model.Marker;


/**
 * Created by fmpap on 19/12/2017.
 */

public class MarkerDAO {

    private static final String TAG = MarkerDAO.class.getName();

    private MyHelper myHelper;

    public MarkerDAO(Context context) {
        myHelper = MyHelper.getHelper(context);
    }

    //Crud
    public long createMarker(Marker marker) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MyHelper.M_PROPERTY_ID, marker.getProperty().getId());
        cv.put(MyHelper.M_AVG_LAT, marker.getAvgLatitude());
        cv.put(MyHelper.M_AVG_LON, marker.getAvgLongitude());
        long id = db.insert(MyHelper.TABLE_MARKERS, null, cv);
        db.close();
        return id;
    }

    //cRud
    public Marker findById(long id) {
        Marker marker = null;
        SQLiteDatabase db = myHelper.getReadableDatabase();
//        Cursor c = db.rawQuery(
//                "SELECT " +
//                "m." + MyHelper._ID + ", " +
//                MyHelper.M_PROPERTY_ID + ", " +
//                MyHelper.M_AVG_LAT + ", " +
//                MyHelper.M_AVG_LON + ", "
//
//
//
//                ,null)

        return marker;
    }



}
