package almeida.francisco.forestboundaries;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.service.PropertyService;


public class LocatePropertyFragment
        extends Fragment implements OnMapReadyCallback {

    private TextView propertyName;
    private Button createNewMarkerBtn;
    private Button leaveForLaterBtn;
    private Button saveMarkerBtn;
    private Button closeLineBtn;
    private Button saveBtn;

    private long propertyId;
    private GoogleMap map;

    private Marker currentMarker;
    private List<Marker> savedMarkers = new ArrayList<>();
    private List<LatLng> points = new ArrayList<>();
    private Polyline polyline;
    private Polygon polygon;
    private Boolean isPolylineOnMap = false;

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
                .replace(R.id.map_container_locate, mapFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
        ((SupportMapFragment) mapFragment).getMapAsync(this);

        return inflater.inflate(R.layout.fragment_locate_property, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        propertyName = view.findViewById(R.id.prop_name_loc);
        createNewMarkerBtn = view.findViewById(R.id.create_new_marker);
        leaveForLaterBtn = view.findViewById(R.id.locate_later);
        saveMarkerBtn = view.findViewById(R.id.save_marker_locate);
        closeLineBtn = view.findViewById(R.id.close_polyline);
        saveBtn = view.findViewById(R.id.save_locate);

        createNewMarkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng centerLatLng = map.getCameraPosition().target;
                if (currentMarker != null)
                    currentMarker.remove();
                currentMarker = map.addMarker(new MarkerOptions()
                        .position(centerLatLng).draggable(true));
            }
        });

        leaveForLaterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        saveMarkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                savedMarkers.add(map.addMarker(new MarkerOptions()
                        .position(currentMarker.getPosition())));
                currentMarker.remove();
                points.add(currentMarker.getPosition());

                if (points.size() > 1) {
                    if (!isPolylineOnMap) {
                        polyline = map.addPolyline(new PolylineOptions());
                        isPolylineOnMap = true;
                    }
                    polyline.setPoints(points);
                }
            }
        });

        closeLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (polygon != null)
                    polygon.remove();
                if (points.size() > 2) {
                    PolygonOptions polygonOptions = new PolygonOptions().addAll(points);
                    polygon = map.addPolygon(polygonOptions);
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        PropertyService propertyService = new PropertyService(getActivity());
        Property property = propertyService.findById(propertyId);
        propertyName.setText(property.toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        //This is needed to change marker LatLng when marker is dragged
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {}
            @Override
            public void onMarkerDrag(Marker marker) {}
            @Override
            public void onMarkerDragEnd(Marker marker) {}
        });
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }
}
