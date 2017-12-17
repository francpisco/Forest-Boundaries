package almeida.francisco.forestboundaries;


import android.app.Activity;
import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import almeida.francisco.forestboundaries.dbhelper.MyHelper;
import almeida.francisco.forestboundaries.model.Property;

public class PropertyListFragment extends ListFragment {

    public static interface Listener {
        public void onItemClicked(long id);
    }

    private Listener listener;
    private CursorAdapter cAdapter;
    private SQLiteDatabase db;
    private Cursor cursor;

    public PropertyListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = MyHelper.getHelper(getActivity()).getReadableDatabase();
        cursor = db.query(MyHelper.TABLE_PROPERTIES,
                new String[]{MyHelper._ID, MyHelper.P_DESCRIP},
                null, null, null, null, null);
        cAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1, cursor, new String[]{MyHelper.P_DESCRIP},
                new int[]{android.R.id.text1}, 0);

        setListAdapter(cAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (Listener) activity;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        cursor.close();
        db.close();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.onItemClicked(id);
        }
    }

    public void myOnRestart() {
        db = MyHelper.getHelper(getActivity()).getReadableDatabase();
        Cursor newCursor = db.rawQuery("SELECT " + MyHelper._ID + ", "
                        + MyHelper.P_DESCRIP + " FROM " + MyHelper.TABLE_PROPERTIES,
                null);
        cAdapter.changeCursor(newCursor);
        cursor = newCursor;
    }

}
