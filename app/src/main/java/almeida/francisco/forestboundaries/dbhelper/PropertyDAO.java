package almeida.francisco.forestboundaries.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import almeida.francisco.forestboundaries.model.Property;

/**
 * Created by fmpap on 13/12/2017.
 */

public class PropertyDAO {

    private static final String TAG = PropertyDAO.class.getName();

    private MyHelper myHelper;

    public PropertyDAO(Context context) {
        myHelper = new MyHelper(context);
    }

    public long createProperty(Property property) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MyHelper.PROPS_OWNER_ID, property.getOwner().getId());
        cv.put(MyHelper.PROPS_DESCRIP, property.getLocationAndDescription());


        return 0;
    }


}
