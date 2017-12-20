package almeida.francisco.forestboundaries.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.model.Marker;


/**
 * Created by Francisco Almeida on 19/12/2017.
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
        Cursor c = db.rawQuery("SELECT * FROM " +
                MyHelper.TABLE_MARKERS + " WHERE " +
                MyHelper._ID + " = " +
                Long.toString(id)
                ,null);
        if (c.moveToFirst()) {
            marker = createMarkerFromCursor(c);
        }
        c.close();
        db.close();
        return marker;
    }

    //cRud
    public List<Marker> findAll() {
        List<Marker> markers = new ArrayList<>();
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +
                MyHelper.TABLE_MARKERS
                ,null);
        if (c.moveToFirst())
            markers.add(createMarkerFromCursor(c));
        while (c.moveToNext())
            markers.add(createMarkerFromCursor(c));
        c.close();
        db.close();
        return markers;
    }

    //cRud
    public long getMarkerPropertyId(long id) {
        long propId = -1;
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " +
                MyHelper.M_PROPERTY_ID + " FROM " +
                MyHelper.TABLE_MARKERS + " WHERE " +
                MyHelper._ID + " = " +
                Long.toString(id)
                ,null);
        if (c.moveToFirst())
           propId = c.getLong(0);
        c.close();
        db.close();
        return propId;
    }

    //crUd
    public boolean update(Marker property) {
        return true;
    }

    //cruD
    public boolean delete(Marker property) {
        return true;
    }

    private Marker createMarkerFromCursor(Cursor c) {
        Marker marker = new Marker()
                .setId(c.getLong(0))
                .setAvgLatitude(c.getDouble(2))
                .setAvgLongitude(3);
        return marker;
    }


}
