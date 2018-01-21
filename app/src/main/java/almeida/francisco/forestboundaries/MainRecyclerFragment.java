package almeida.francisco.forestboundaries;


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


public class MainRecyclerFragment extends Fragment {

    private List<Property> properties;

    public MainRecyclerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        properties = (new PropertyService(getActivity())).findAll();
        return inflater.inflate(R.layout.fragment_main_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {

        RecyclerView recyclerView = view.findViewById(R.id.main_recycler_view);
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(properties, getActivity());
        recyclerView.setAdapter(mainRecyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

}
