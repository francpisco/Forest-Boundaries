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

import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.service.PropertyService;


public class LocatePropertyFragment
        extends Fragment implements OnMapReadyCallback {

    private TextView propertyName;
    private Button locateMarkerBtn;
    private Button locateLaterBtn;
    private Button saveBtn;

    private long propertyId;
    private GoogleMap map;

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
        locateMarkerBtn = view.findViewById(R.id.create_new_marker);
        locateLaterBtn = view.findViewById(R.id.locate_later);
        saveBtn = view.findViewById(R.id.save_locate);

        locateMarkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng centerLatLng = map.getCameraPosition().target;
                Marker marker = map.addMarker(new MarkerOptions()
                        .position(centerLatLng).draggable(true));

            }
        });

        locateLaterBtn.setOnClickListener(new View.OnClickListener() {
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
    public void onStart(){
        super.onStart();
        PropertyService propertyService = new PropertyService(getActivity());
        Property property = propertyService.findById(propertyId);
        propertyName.setText(property.toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }
}
