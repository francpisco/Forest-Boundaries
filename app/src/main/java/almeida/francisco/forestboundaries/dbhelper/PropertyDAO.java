package almeida.francisco.forestboundaries.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.model.Property;

/**
 * Created by fmpap on 13/12/2017.
 */

public class PropertyDAO {

    private static final String TAG = PropertyDAO.class.getName();//for logging

    private MyHelper myHelper;

    public PropertyDAO(Context context) {
        myHelper = new MyHelper(context);
    }

    //Crud
    public long createProperty(Property property) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MyHelper.P_OWNER_ID, property.getOwnerId());
        cv.put(MyHelper.P_DESCRIP, property.getLocationAndDescription());
        cv.put(MyHelper.P_APPROX_SIZE, property.getApproxSizeInSquareMeters());
        long id = db.insert(MyHelper.TABLE_PROPERTIES, null, cv);
        db.close();
        return id;
    }

    //cRud
    public Property findById(long id) {
        Property property = null;
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + MyHelper.TABLE_PROPERTIES + " WHERE " +
                MyHelper._ID + " = " + id, null);
        if (c.moveToFirst()) {
            property = createPropFromCursor(c);
        }
        c.close();
        db.close();
        return property;
    }

    //cRud
    public List<Property> findAll() {
        List<Property> properties = new ArrayList<>();
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + MyHelper.TABLE_PROPERTIES, null);
        if (c.moveToFirst()) {
            properties.add(createPropFromCursor(c));
        }
        while (c.moveToNext()) {
            properties.add(createPropFromCursor(c));
        }
        c.close();
        db.close();
        return properties;
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
        Property property = new Property();
        property.setOwnerId(c.getLong(c.getColumnIndex(MyHelper.P_OWNER_ID)));
        property.setLocationAndDescription(c.getString(c.getColumnIndex(MyHelper.P_DESCRIP)));
        Integer approxSize = c.getInt(c.getColumnIndex(MyHelper.P_APPROX_SIZE));
        if (approxSize != null) {
            property.setApproxSizeInSquareMeters(approxSize);
        }
        Integer calcSize = c.getInt(c.getColumnIndex(MyHelper.P_CALC_SIZE));
        if (calcSize != null) {
            property.setCalculatedSize(calcSize);
        }
        return property;
    }


}
