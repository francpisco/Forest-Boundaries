package almeida.francisco.forestboundaries.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.model.Owner;

/**
 * Created by fmpap on 13/12/2017.
 */

public class OwnerDAO {

    private static final String TAG = OwnerDAO.class.getName();

    private MyHelper myHelper;

    public OwnerDAO(Context context) {
        myHelper = new MyHelper(context);
    }

    //Crud
    public long createOwner(Owner owner) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MyHelper.O_NAME, owner.getName());
        long id = db.insert(MyHelper.TABLE_OWNERS, null, cv);
        db.close();
        return id;
    }

    //cRud
    public Owner findById(long id) {
        Owner owner = null;
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyHelper.TABLE_OWNERS + " WHERE " +
                MyHelper._ID + " = " + id, null);
        if (cursor.moveToFirst()) {
            owner = createOwnerFromCursor(cursor);
        }
        cursor.close();
        db.close();
        return owner;
    }

    //cRud
    public List<Owner> findAll() {
        List<Owner> owners = new ArrayList<>();
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyHelper.TABLE_OWNERS, null);
        if (cursor.moveToFirst())
            owners.add(createOwnerFromCursor(cursor));
        while (cursor.moveToNext()) {
            owners.add(createOwnerFromCursor(cursor));
        }
        cursor.close();
        db.close();
        return owners;
    }

    //crUd
    public boolean update(Owner owner) {
        return true;
    }

    //cruD
    public boolean delete(Owner owner) {
        return true;
    }

    private Owner createOwnerFromCursor(Cursor c) {
        Owner owner = new Owner()
                .setName(c.getString(c.getColumnIndex(MyHelper.O_NAME)));
        return owner;
    }
}