package almeida.francisco.forestboundaries;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import almeida.francisco.forestboundaries.model.Property;

public class PropertyListFragment extends ListFragment {

    public static interface Listener {
        public void onItemClicked(long id);
    }

    private Listener listener;

    public PropertyListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayAdapter<Property> adapter = new ArrayAdapter<Property>(inflater.getContext(),
                android.R.layout.simple_list_item_1, Property.properties);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (Listener) activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.onItemClicked(id);
        }
    }

}
