package almeida.francisco.forestboundaries;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.service.PropertyService;
import almeida.francisco.forestboundaries.util.MapUtil;

public class MarkInSituFragment extends Fragment implements OnMapReadyCallback {

    private long propertyId;
    private TextView propertyNameTxt;
    private Property property;

    public MarkInSituFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Fragment mapFragment = SupportMapFragment.newInstance(new GoogleMapOptions()
                .mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false)
                .camera(new CameraPosition(
                        new LatLng(39.979, -8.7508), 18.0f, 0.0f, 0.0f)));
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.map_container_mark_in_S, mapFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
        ((SupportMapFragment) mapFragment).getMapAsync(this);

        return inflater.inflate(R.layout.fragment_mark_in_situ, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        propertyNameTxt = view.findViewById(R.id.prop_name_mark_in_s);
    }

    @Override
    public void onStart() {
        super.onStart();
        PropertyService propertyService = new PropertyService(getActivity());
        property = propertyService.findById(propertyId);
        propertyNameTxt.setText(property.getLocationAndDescription());
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapUtil.centerMap(property, googleMap);

    }
}
