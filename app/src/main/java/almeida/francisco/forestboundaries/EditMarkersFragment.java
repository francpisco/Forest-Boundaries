package almeida.francisco.forestboundaries;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.model.MyMarker;
import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.service.MarkerService;
import almeida.francisco.forestboundaries.service.PropertyService;
import almeida.francisco.forestboundaries.util.MapUtil;
import almeida.francisco.forestboundaries.util.MarkerIconFactory;


public class EditMarkersFragment extends Fragment implements OnMapReadyCallback {

    private long propertyId;
    private Property property;
    private Marker currentMarker;
    private GoogleMap map;

    private TextView propNameText;
    private Button newMarkerBtn;
    private Button saveMarkerBtn;
    private Button closeLineBtn;
    private Button saveBtn;

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



        return inflater.inflate(R.layout.fragment_edit_markers, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {


        propNameText = (TextView) view.findViewById(R.id.prop_name_edit_markers);
        newMarkerBtn = (Button) view.findViewById(R.id.create_new_marker_edit_markers);
        saveMarkerBtn = (Button) view.findViewById(R.id.save_marker_edit_markers);
        closeLineBtn = (Button) view.findViewById(R.id.close_polyline_edit_markers);
        saveBtn = (Button) view.findViewById(R.id.save_edit_markers);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.edit_recycler_view);
        MarkerService markerService = new MarkerService(getActivity());
        List<MyMarker> markers = markerService.findListByPropertyId(propertyId);
        LabelledMarkersAdapter labelledMarkersAdapter = new LabelledMarkersAdapter(markers,
                getActivity());
        recyclerView.setAdapter(labelledMarkersAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        newMarkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentMarker != null)
                    currentMarker.remove();
                currentMarker = map.addMarker(new MarkerOptions()
                        .position(map.getCameraPosition().target).draggable(true));
            }
        });

        saveMarkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        closeLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        PropertyService propertyService = new PropertyService(getActivity());
        property = propertyService.findById(propertyId);
        propNameText.setText(property.getLocationAndDescription());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        List<LatLng> markersLatLng = property.fromMarkersToLatLng();
        List<LatLng> readingsLatLng = property.fromReadingsToLatLng();
        if (markersLatLng.size() > 0) {
            MapUtil.centerMap(markersLatLng, map);
            MapUtil.drawPolygon(markersLatLng, map, 10f, Color.BLUE, false);
        } else if (readingsLatLng.size() > 0) {
            MapUtil.centerMap(readingsLatLng, map);
        }
        if (readingsLatLng.size() > 0) {
            MapUtil.drawPolygon(readingsLatLng, map, 8f, Color.GREEN, true);
        }
        for (int i = 0; i < markersLatLng.size(); i++) {
            Marker m = map.addMarker(new MarkerOptions().position(markersLatLng.get(i)));
            m.setIcon(BitmapDescriptorFactory.fromResource(MarkerIconFactory.fromInt(i)));
        }
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }
}
