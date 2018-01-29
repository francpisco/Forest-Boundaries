package almeida.francisco.forestboundaries.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.model.Property;

/**
 * Created by Francisco Almeida on 13/12/2017.
 */

public class PropertyDAO {

    private static final String TAG = PropertyDAO.class.getName();//for logging

    private MyHelper myHelper;

    public PropertyDAO(Context context) {
        myHelper = MyHelper.getHelper(context);
    }

    //Crud
    public long createProperty(Property property) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MyHelper.P_OWNER_ID, property.getOwner().getId());
        cv.put(MyHelper.P_DESCRIP, property.getLocationAndDescription());
        cv.put(MyHelper.P_APPROX_SIZE, property.getApproxSizeInSquareMeters());
        //can these values be null?
        cv.put(MyHelper.P_NOTE, property.getNote());
        cv.put(MyHelper.P_LAND_USE, property.getLandUse());
        cv.put(MyHelper.P_YEAR_OF_PLANTATION, property.getYearOfPlantation());
        cv.put(MyHelper.P_YEAR_OF_LAST_CUT, property.getYearOfLastCut());
        cv.put(MyHelper.P_YEAR_AND_MONTH_OF_LAST_CLEANING, property.getYearAndMonthOfLastCleaning());

        long id = db.insert(MyHelper.TABLE_PROPERTIES, null, cv);
        db.close();
        return id;
    }

    //cRud
    public Property findById(long id) {
        Property property = null;
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM " + MyHelper.TABLE_PROPERTIES +
                " WHERE " + MyHelper._ID + " = " + Long.toString(id),
                null);
        if (c.moveToFirst()) {
            property = createPropFromCursor(c);
        }
        c.close();
        db.close();
        return property;
    }

    public List<Property> findByOwnerId(long ownerId) {
        List<Property> properties = new ArrayList<>();
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +
                MyHelper.TABLE_PROPERTIES + " WHERE " +
                MyHelper.P_OWNER_ID + " = " + Long.toString(ownerId)
                , null);
        if (c.moveToFirst())
            properties.add(createPropFromCursor(c));
        while (c.moveToNext()) {
            properties.add(createPropFromCursor(c));
        }
        c.close();
        db.close();
        return properties;
    }

    //cRud
    public List<Property> findAll() {
        List<Property> properties = new ArrayList<>();
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + MyHelper.TABLE_PROPERTIES,
                null);
        if (c.moveToFirst())
            properties.add(createPropFromCursor(c));
        while (c.moveToNext()) {
            properties.add(createPropFromCursor(c));
        }
        c.close();
        db.close();
        return properties;
    }

    //cRud
    public long getOwnerId(long id) {
        long ownerId = -1;
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + MyHelper.P_OWNER_ID + " FROM " +
                MyHelper.TABLE_PROPERTIES + " WHERE " + MyHelper._ID + " = " +
                Long.toString(id),
                null);
        if (c.moveToFirst())
            ownerId = c.getLong(0);
        c.close();
        db.close();
        return ownerId;
    }

    //cRud
    public int getCount() {
        int count = 0;
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + MyHelper._ID
                            + " FROM " + MyHelper.TABLE_PROPERTIES,
                            null);
        count = c.getCount();
        c.close();
        db.close();
        return count;
    }

    //crUd
    public boolean update(Property property) {
        return true;
    }

    //cruD
    public boolean delete(Property property) {
        return true;
    }

    private Property createPropFromCursor(Cursor c) {
        Property property = new Property()
                .setId(c.getInt(0))
                .setLocationAndDescription(c.getString(2))
                .setApproxSizeInSquareMeters(c.getInt(3))
                .setNote(c.getString(4))
                .setLandUse(c.getString(5))
                .setYearOfPlantation(c.getInt(6))
                .setYearOfLastCut(7)
                .setYearAndMonthOfLastCleaning(8);
        return property;
    }


}
