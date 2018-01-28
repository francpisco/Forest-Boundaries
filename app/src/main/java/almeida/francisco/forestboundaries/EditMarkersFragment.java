package almeida.francisco.forestboundaries;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Collections;
import java.util.List;

import almeida.francisco.forestboundaries.model.MyMarker;
import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.service.MarkerService;
import almeida.francisco.forestboundaries.service.PropertyService;
import almeida.francisco.forestboundaries.util.MapUtil;
import almeida.francisco.forestboundaries.util.MarkerIconFactory;


public class EditMarkersFragment extends Fragment
        implements OnMapReadyCallback, LabelledMarkersAdapter.RecyclerViewClickListener {

    public interface Listener {
        void onUpButtonClick();
    }
    private Listener upListener;

    private long propertyId;
    private Property property;
    private Marker currentMarker;
    private GoogleMap map;
    private int selectedItemFromList = -1;
    private int fromPosition;
    private int toPosition;
    private MarkerService markerService;
    private PropertyService propertyService;
    private List<MyMarker> markers;
    private RecyclerView recyclerView;
    private LabelledMarkersAdapter labelledMarkersAdapter;

    private CardView cardView;
    private FloatingActionButton addMarkerFab;
    private FloatingActionButton deleteMarkerFab;
    private FloatingActionButton saveFab;

    public EditMarkersFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LatLng latLng = new LatLng(39.979, -8.7508);
        Fragment mapFragment = SupportMapFragment.newInstance(new GoogleMapOptions()
                .mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false)
                .camera(new CameraPosition(latLng, 18.0f, 0.0f, 0.0f)));
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.map_container_edit_markers, mapFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
        ((SupportMapFragment) mapFragment).getMapAsync(this);
        propertyService = new PropertyService(getActivity());
        markerService = new MarkerService(getActivity());

        return inflater.inflate(R.layout.fragment_edit_markers, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        addMarkerFab = (FloatingActionButton) view.findViewById(R.id.add_marker_floating_btn);
        addMarkerFab.setOnClickListener(new NewMarkerBtnListener());

        deleteMarkerFab = (FloatingActionButton) view.findViewById(R.id.delete_marker_floating_btn);
        deleteMarkerFab.setOnClickListener(new DeleteMarkerBtnListener());

        saveFab = (FloatingActionButton) view.findViewById(R.id.save_marker_floating_btn);
        saveFab.setOnClickListener(new SaveMarkerBtnListener());

        recyclerView = (RecyclerView) view.findViewById(R.id.edit_recycler_view);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        upListener = (Listener) getActivity();
        setHasOptionsMenu(true);
        Toolbar mToolBar = (Toolbar) getView().findViewById(R.id.edit_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                upListener.onUpButtonClick();
                return true;
            case R.id.save_action:
                upListener.onUpButtonClick();
                return true;
            case R.id.settings_action:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class NewMarkerBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (currentMarker != null)
                currentMarker.remove();
            currentMarker = map.addMarker(new MarkerOptions()
                    .position(map.getCameraPosition().target).draggable(true));
        }
    }

    private class DeleteMarkerBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (selectedItemFromList >= 0) {
                if (cardView != null)
                    cardView.setCardBackgroundColor(Color.WHITE);
                markerService.deleteById(markers.get(selectedItemFromList).getId());
                markers.remove(selectedItemFromList);
                map.clear();
                drawShapesAndCenterMap(false);
                labelledMarkersAdapter.notifyDataSetChanged();
                selectedItemFromList = -1;
            }
        }
    }

    private class SaveMarkerBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (currentMarker != null) {
                if (cardView != null)
                    cardView.setCardBackgroundColor(Color.WHITE);
                LatLng position = currentMarker.getPosition();
                currentMarker.remove();
                MyMarker marker = new MyMarker()
                        .setIndex(markers.size())
                        .setTempId(markers.size())
                        .setMarkedLatitude(position.latitude)
                        .setMarkedLongitude(position.longitude)
                        .setProperty(property);
                long mId = markerService.createMarker(marker);
                marker.setId(mId);
                markers.add(marker);
                labelledMarkersAdapter.notifyDataSetChanged();
                map.clear();
                drawShapesAndCenterMap(false);
                selectedItemFromList = -1;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        property = propertyService.findById(propertyId);
        getActivity().setTitle(property.getLocationAndDescription());
        markers = property.getMarkers();
        labelledMarkersAdapter = new LabelledMarkersAdapter(markers,
                getActivity(), this);
        recyclerView.setAdapter(labelledMarkersAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private class MyCallback extends ItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                    ItemTouchHelper.LEFT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView,
                              RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            fromPosition = viewHolder.getAdapterPosition();
            toPosition = target.getAdapterPosition();
            reorderList();
            updateIndexes();
            labelledMarkersAdapter.notifyItemMoved(fromPosition, toPosition);
            map.clear();
            drawShapesAndCenterMap(false);
            new Handler().post(new UpdateIndexesInDb());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        }
    }

    private void reorderList() {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(markers, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(markers, i, i - 1);
            }
        }
    }

    private void updateIndexes() {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i <= toPosition; i++) {
                markers.get(i).setIndex(i);
            }
        } else {
            for (int i = fromPosition; i >= toPosition; i--) {
                markers.get(i).setIndex(i);
            }
        }
    }

    private class UpdateIndexesInDb implements Runnable {
        @Override
        public void run() {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i <= toPosition; i++) {
                    markerService.updateIndex(markers.get(i));
                }
            } else {
                for (int i = fromPosition; i >= toPosition; i--) {
                    markerService.updateIndex(markers.get(i));
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        drawShapesAndCenterMap(true);

        //For some reason this is needed to change marker LatLng when marker is dragged
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
            }
        });
    }

    private void drawShapesAndCenterMap(boolean isToCenterMap) {
        List<LatLng> markersLatLng = property.fromMarkersToLatLng();
        List<LatLng> readingsLatLng = property.fromReadingsToLatLng();
        if (markersLatLng.size() > 0) {
            if (isToCenterMap)
                MapUtil.centerMap(markersLatLng, map);
            MapUtil.drawPolygon(markersLatLng, map, 10f, Color.BLUE, false);
        } else if (readingsLatLng.size() > 0 && isToCenterMap) {
            MapUtil.centerMap(readingsLatLng, map);
        }
        if (readingsLatLng.size() > 0) {
            MapUtil.drawPolygon(readingsLatLng, map, 8f, Color.GREEN, true);
        }
        for (int i = 0; i < markersLatLng.size(); i++) {
            Marker m = map.addMarker(new MarkerOptions().position(markersLatLng.get(i)));
            m.setIcon(BitmapDescriptorFactory.fromResource(MarkerIconFactory
                    .fromInt(markers.get(i).getTempId())));
        }
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public void onItemClicked(View view, int position) {
        if (cardView != null)
            cardView.setCardBackgroundColor(Color.WHITE);
        cardView = (CardView) view.findViewById(R.id.card_view);
        cardView.setCardBackgroundColor(Color.GREEN);
        selectedItemFromList = position;
    }
}