package almeida.francisco.forestboundaries.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.model.Owner;
import almeida.francisco.forestboundaries.model.Property;

/**
 * Created by fmpap on 13/12/2017.
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
        long id = db.insert(MyHelper.TABLE_PROPERTIES, null, cv);
        db.close();
        return id;
    }

    //cRud
    public Property findById(long id) {
        Property property = null;
        SQLiteDatabase db = myHelper.getReadableDatabase();
        String query = "SELECT " + MyHelper._ID + ", ";

        Cursor c = db.rawQuery(
                        "SELECT " +
                        "p." + MyHelper._ID + ", " +
                        MyHelper.P_OWNER_ID + ", " +
                        MyHelper.P_DESCRIP + ", " +
                        MyHelper.P_APPROX_SIZE + ", " +
                        MyHelper.P_CALC_SIZE + ", " +
                        "o." + MyHelper.O_NAME +
                        " FROM " +
                        MyHelper.TABLE_PROPERTIES + " AS p " +
                        "INNER JOIN " +
                        MyHelper.TABLE_OWNERS + " AS o" +
                        " ON " +
                        "p." + MyHelper.P_OWNER_ID + " = " +
                        "o." + MyHelper._ID +
                        " WHERE p." + MyHelper._ID + " = " +
                        Long.toString(id)
                        , null);
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
        Cursor c = db.rawQuery("SELECT " +
                        "p." + MyHelper._ID + ", " +
                        MyHelper.P_OWNER_ID + ", " +
                        MyHelper.P_DESCRIP + ", " +
                        MyHelper.P_APPROX_SIZE + ", " +
                        MyHelper.P_CALC_SIZE + ", " +
                        "o." + MyHelper.O_NAME +
                        " FROM " +
                        MyHelper.TABLE_PROPERTIES + " AS p, " +
                        MyHelper.TABLE_OWNERS + " AS o" +
                        " WHERE " +
                        MyHelper.P_OWNER_ID + " = " +
                        "o." + MyHelper._ID
                        , null);
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
        Owner owner = new Owner()
                .setId(c.getInt(1))
                .setName(c.getString(5));
        Property property = new Property()
                .setId(c.getInt(0))
                .setOwner(owner)
                .setLocationAndDescription(c.getString(2));
        Integer approxSize = c.getInt(3);
        if (approxSize != null) {
            property.setApproxSizeInSquareMeters(approxSize);
        }
        Integer calcSize = c.getInt(4);
        if (calcSize != null) {
            property.setCalculatedSize(calcSize);
        }
        return property;
    }

    public void loadProperties(OwnerDAO oDAO) {

        List<Owner> owners = oDAO.findAll();
        Property property1 = new Property()
                .setOwner(owners.get(0))
                .setLocationAndDescription("Arneiro")
                .setApproxSizeInSquareMeters(650);
        createProperty(property1);
        Property property2 = new Property()
                .setOwner(owners.get(0))
                .setLocationAndDescription("Fonte")
                .setApproxSizeInSquareMeters(10000);
        createProperty(property2);
        Property property3 = new Property()
                .setOwner(owners.get(1))
                .setLocationAndDescription("Castelhanas")
                .setApproxSizeInSquareMeters(2000);
        createProperty(property3);
    }
}
