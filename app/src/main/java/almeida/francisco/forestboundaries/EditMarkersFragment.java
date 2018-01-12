package almeida.francisco.forestboundaries;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
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

import java.util.List;

import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.service.PropertyService;
import almeida.francisco.forestboundaries.util.MapUtil;
import almeida.francisco.forestboundaries.util.MarkerIconFactory;


public class EditMarkersFragment extends Fragment implements OnMapReadyCallback {

    private long propertyId;
    private Property property;

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
    public void onStart() {
        super.onStart();
        View view = getView();
        PropertyService propertyService = new PropertyService(getActivity());
        property = propertyService.findById(propertyId);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        List<LatLng> markersLatLng = property.fromMarkersToLatLng();
        List<LatLng> readingsLatLng = property.fromReadingsToLatLng();
        if (markersLatLng.size() > 0) {
            MapUtil.centerMap(markersLatLng, googleMap);
            MapUtil.drawPolygon(markersLatLng, googleMap, 10f, Color.BLUE, false);
        } else if (readingsLatLng.size() > 0) {
            MapUtil.centerMap(readingsLatLng, googleMap);
        }
        if (readingsLatLng.size() > 0) {
            MapUtil.drawPolygon(readingsLatLng, googleMap, 8f, Color.GREEN, true);
        }
        for (int i = 0; i < markersLatLng.size(); i++) {
            Marker m = googleMap.addMarker(new MarkerOptions().position(markersLatLng.get(i)));
            m.setIcon(BitmapDescriptorFactory.fromResource(MarkerIconFactory.fromInt(i)));
        }
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }
}
