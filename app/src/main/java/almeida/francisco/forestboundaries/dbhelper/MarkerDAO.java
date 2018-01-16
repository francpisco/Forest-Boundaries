package almeida.francisco.forestboundaries.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.model.MyMarker;


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
    public long createMarker(MyMarker marker) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MyHelper.M_PROPERTY_ID, marker.getProperty().getId());
        cv.put(MyHelper.M_INDEX, marker.getIndex());
        cv.put(MyHelper.M_AVG_LAT, marker.getMarkedLatitude());
        cv.put(MyHelper.M_AVG_LON, marker.getMarkedLongitude());
        long id = db.insert(MyHelper.TABLE_MARKERS, null, cv);
        db.close();
        return id;
    }

    //cRud
    public MyMarker findById(long id) {
        MyMarker marker = null;
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +
                MyHelper.TABLE_MARKERS + " WHERE " +
                MyHelper._ID + " = " +
                Long.toString(id),
                null);
        if (c.moveToFirst()) {
            marker = createMarkerFromCursor(c);
        }
        c.close();
        db.close();
        return marker;
    }

    //cRud
    public List<MyMarker> findAll() {
        List<MyMarker> markers = new ArrayList<>();
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +
                MyHelper.TABLE_MARKERS,
                null);
        if (c.moveToFirst())
            markers.add(createMarkerFromCursor(c));
        while (c.moveToNext())
            markers.add(createMarkerFromCursor(c));
        c.close();
        db.close();
        return markers;
    }

    //cRud
    public List<MyMarker> findByPropertyId(long propId) {
        List<MyMarker> markers = new ArrayList<>();
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +
                MyHelper.TABLE_MARKERS + " WHERE " +
                MyHelper.M_PROPERTY_ID + " = " +
                Long.toString(propId),
                null);
        if (c.moveToFirst())
            markers.add(createMarkerFromCursor(c));
        while (c.moveToNext())
            markers.add(createMarkerFromCursor(c));
        c.close();
        db.close();
        return markers;
    }

    //cRud
    public long getPropertyId(long id) {
        long propId = -1;
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " +
                MyHelper.M_PROPERTY_ID + " FROM " +
                MyHelper.TABLE_MARKERS + " WHERE " +
                MyHelper._ID + " = " +
                Long.toString(id),
                null);
        if (c.moveToFirst())
           propId = c.getLong(0);
        c.close();
        db.close();
        return propId;
    }

    //crUd
    public boolean update(MyMarker marker) {
        return true;
    }

    //cruD
    public boolean deleteById(long id) {
        boolean result;
        SQLiteDatabase db = myHelper.getWritableDatabase();
        Cursor c = db.rawQuery("DELETE FROM " +
                        MyHelper.TABLE_MARKERS + " WHERE " +
                        MyHelper._ID + " = " +
                        Long.toString(id),
                        null);
        result = c.moveToFirst();
        c.close();
        db.close();
        return result;
    }

    private MyMarker createMarkerFromCursor(Cursor c) {
        MyMarker marker = new MyMarker()
                .setId(c.getLong(0))
                .setIndex(c.getDouble(1))
                .setMarkedLatitude(c.getDouble(3))
                .setMarkedLongitude(c.getDouble(4));
        return marker;
    }


}
