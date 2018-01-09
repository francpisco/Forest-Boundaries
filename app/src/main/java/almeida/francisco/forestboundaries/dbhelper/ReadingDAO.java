package almeida.francisco.forestboundaries.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.model.Reading;


/**
 * Created by Francisco Almeida on 19/12/2017.
 */
public class ReadingDAO {

    private static final String LOG_TAG = ReadingDAO.class.getName();

    private MyHelper myHelper;

    public ReadingDAO(Context context) {
        myHelper = MyHelper.getHelper(context);
    }

    //Crud
    public long createReading(Reading reading) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MyHelper.R_PROPERTY_ID, reading.getProperty().getId());
        cv.put(MyHelper.R_MARKER_ID, reading.getMarker().getId());
        cv.put(MyHelper.R_LAT, reading.getLatitude());
        cv.put(MyHelper.R_LON, reading.getLongitude());
        long id = db.insert(MyHelper.TABLE_READINGS, null, cv);
        db.close();
        return id;
    }

    //cRud
    public Reading findById(long id) {
        Reading reading = null;
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +
                        MyHelper.TABLE_READINGS + " WHERE " +
                        MyHelper._ID + " = " +
                        Long.toString(id),
                        null);
        if (c.moveToFirst()) {
            reading = createReadingFromCursor(c);
        }
        c.close();
        db.close();
        return reading;
    }

    //cRud
    public List<Reading> findAll() {
        List<Reading> markers = new ArrayList<>();
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +
                        MyHelper.TABLE_READINGS,
                        null);
        if (c.moveToFirst())
            markers.add(createReadingFromCursor(c));
        while (c.moveToNext())
            markers.add(createReadingFromCursor(c));
        c.close();
        db.close();
        return markers;
    }

    //cRud
    public List<Reading> findByPropertyId(long propId) {
        List<Reading> readings = new ArrayList<>();
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +
                        MyHelper.TABLE_READINGS + " WHERE " +
                        MyHelper.R_PROPERTY_ID + " = " +
                        Long.toString(propId),
                        null);
        if (c.moveToFirst())
            readings.add(createReadingFromCursor(c));
        while (c.moveToNext())
            readings.add(createReadingFromCursor(c));
        c.close();
        db.close();
        return readings;
    }

    public List<Reading> findByMarkerId(long markerId) {
        List<Reading> readings = new ArrayList<>();
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +
                        MyHelper.TABLE_READINGS + " WHERE " +
                        MyHelper.R_MARKER_ID + " = " +
                        Long.toString(markerId),
                        null);
        if (c.moveToFirst())
            readings.add(createReadingFromCursor(c));
        while (c.moveToNext())
            readings.add(createReadingFromCursor(c));
        c.close();
        db.close();
        return readings;
    }

    //cRud
    public long getPropertyId(long id) {
        long propId = -1;
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " +
                        MyHelper.R_PROPERTY_ID + " FROM " +
                        MyHelper.TABLE_READINGS + " WHERE " +
                        MyHelper._ID + " = " +
                        Long.toString(id),
                        null);
        if (c.moveToFirst())
            propId = c.getLong(0);
        c.close();
        db.close();
        return propId;
    }

    //cRud
    public long getMarkerId(long id) {
        long propId = -1;
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " +
                        MyHelper.R_MARKER_ID + " FROM " +
                        MyHelper.TABLE_READINGS + " WHERE " +
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
    public boolean update(Reading property) {
        return true;
    }

    //cruD
    public boolean delete(Reading property) {
        return true;
    }

    private Reading createReadingFromCursor(Cursor c) {
        Reading reading = new Reading()
                .setId(c.getLong(0))
                .setLatitude(c.getDouble(3))
                .setLongitude(c.getDouble(4));
        return reading;
    }
}
