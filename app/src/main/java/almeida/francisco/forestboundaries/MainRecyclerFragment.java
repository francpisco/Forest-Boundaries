package almeida.francisco.forestboundaries;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.service.PropertyService;


public class MainRecyclerFragment extends Fragment
        implements MainRecyclerAdapter.MainRecyclerListener{

    public static interface Listener {
        public void onItemClicked(long propertyId);
    }
    private Listener listener;

    private List<Property> properties;
    private MainRecyclerAdapter mainRecyclerAdapter;

    public MainRecyclerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        properties = (new PropertyService(getActivity())).findAll();
        listener = (Listener) getActivity();
        return inflater.inflate(R.layout.fragment_main_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {

        RecyclerView recyclerView = view.findViewById(R.id.main_recycler_view);
        mainRecyclerAdapter = new MainRecyclerAdapter(properties, getActivity(), this);
        recyclerView.setAdapter(mainRecyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    public void myOnRestart() {
//        mainRecyclerAdapter.notifyDataSetChanged();
        // TODO: 21/01/2018 descomentar isto
//        new PopulateView().execute();
    }

    @Override
    public void onItemClicked(View view, int position) {
        listener.onItemClicked(properties.get(position).getId());
    }

    // TODO: 21/01/2018 estava aqui
//    private class PopulateView extends AsyncTask<Void, Void, Boolean> {
//
//        Cursor newCursor;
//
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//
//
//
//            db = MyHelper.getHelper(getActivity()).getReadableDatabase();
//            newCursor = db.rawQuery("SELECT " + MyHelper._ID + ", "
//                            + MyHelper.P_DESCRIP + " FROM " + MyHelper.TABLE_PROPERTIES,
//                    null);
//            if (newCursor.getCount() == cursor.getCount())
//                return false;
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean changeCursor) {
//            if (!changeCursor)
//                return;
//            cAdapter.changeCursor(newCursor);
//            cursor = newCursor;
//        }
//    }

}
