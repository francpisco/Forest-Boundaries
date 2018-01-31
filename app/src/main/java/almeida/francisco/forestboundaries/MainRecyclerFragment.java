package almeida.francisco.forestboundaries;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.service.PropertyService;


public class MainRecyclerFragment extends Fragment
        implements MainRecyclerAdapter.MainRecyclerListener{

    public interface Listener {
        void onItemClicked(long propertyId);
    }
    private Listener listener;

    private long ownerId = -1;
    private List<Property> properties;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private PropertyService propertyService;
    private RecyclerView recyclerView;

    public MainRecyclerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        propertyService = new PropertyService(getActivity());
        listener = (Listener) getActivity();
        return inflater.inflate(R.layout.fragment_main_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        recyclerView = view.findViewById(R.id.main_recycler_view);
        mainRecyclerAdapter = new MainRecyclerAdapter(new ArrayList<Property>(),
                getActivity(), MainRecyclerFragment.this);
        recyclerView.setAdapter(mainRecyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();
        new PopulateView().execute();
    }

    @Override
    public void onItemClicked(View view, int position) {
        listener.onItemClicked(properties.get(position).getId());
    }

    private class PopulateView extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (ownerId == -1) {
                properties = propertyService.findAll();
            } else {
                properties = propertyService.findByOwnerId(ownerId);
            }
            mainRecyclerAdapter = new MainRecyclerAdapter(properties,
                    getActivity(), MainRecyclerFragment.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            recyclerView.swapAdapter(mainRecyclerAdapter, false);
        }
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
