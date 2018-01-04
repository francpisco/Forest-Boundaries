package almeida.francisco.forestboundaries;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;


import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.dbhelper.OwnerDAO;
import almeida.francisco.forestboundaries.dbhelper.PropertyDAO;
import almeida.francisco.forestboundaries.model.MyMarker;
import almeida.francisco.forestboundaries.model.Owner;
import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.model.PropertyMarker;
import almeida.francisco.forestboundaries.service.OwnerService;
import almeida.francisco.forestboundaries.service.PropertyService;

public class PropertyDetailFragment
        extends Fragment implements OnMapReadyCallback{

    private long propertyId = 0;
    private Property property;
    private Fragment mapFragment;

    public PropertyDetailFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            propertyId = savedInstanceState.getLong("property_id");
        }

        LatLng latLng = new LatLng(39.979, -8.7508);
        mapFragment = SupportMapFragment.newInstance(new GoogleMapOptions()
                .mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false)
                .camera(new CameraPosition(latLng, 18.0f, 0.0f, 0.0f)));
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.map_container, mapFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

        return inflater.inflate(R.layout.fragment_property_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        PropertyService propertyService = new PropertyService(getActivity());
        OwnerService ownerService = new OwnerService(getActivity());
        property = propertyService.findById(propertyId);

        ((SupportMapFragment) mapFragment).getMapAsync(this);

        long o_id = property
                .getOwner()
                .getId();
        Owner o = ownerService.findById(o_id);
        if (view != null) {
            TextView ownerView = (TextView) view.findViewById(R.id.owner_value);
            ownerView.setText(
                    o.toString());

            TextView descriptionView = (TextView) view.findViewById(R.id.description_value);
            descriptionView.setText(property.getLocationAndDescription());

            TextView firstMarkerView = (TextView) view.findViewById(R.id.marker_value);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle onSavedInstanceState) {
        onSavedInstanceState.putLong("property_id", propertyId);
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        List<MyMarker> markers = property.getMarkers();
        List<LatLng> points = new ArrayList<>();
        for (MyMarker m : markers) {
            points.add(new LatLng(m.getMarkedLatitude(), m.getMarkedLongitude()));
        }
        if (points.size() > 0) {
            PolygonOptions polygonOptions = new PolygonOptions().addAll(points);
            map.addPolygon(polygonOptions);
        }
        //debugging
        PolygonOptions pO = new PolygonOptions()
                .add(new LatLng(39.977, -8.7506))
                .add(new LatLng(39.978, -8.7509))
                .add(new LatLng(39.975, -8.7507));
        map.addPolygon(pO);
    }
}
