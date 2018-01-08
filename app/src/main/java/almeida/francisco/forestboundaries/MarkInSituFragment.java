package almeida.francisco.forestboundaries;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.service.PropertyService;
import almeida.francisco.forestboundaries.util.MapUtil;

public class MarkInSituFragment extends Fragment implements OnMapReadyCallback {

    private final static String LOG_TAG = MarkInSituFragment.class.getName();

    private final static int MIN_TIME_INTERVAL_IN_MS = 1000;
    private final static int MIN_DIST_IN_METERS = 1;

    public final static int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    private long propertyId;
    private TextView propertyNameTxt;
    private Property property;
    private String prefLocationProvider;
    private LocationManager locMgr;
    private GoogleMap map;

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

        if (Build.VERSION.SDK_INT >= 23 &&
                getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            getActivity().requestPermissions(
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }

        locMgr = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        for (String prov : locMgr.getAllProviders()) {
            Log.i(LOG_TAG, "Provider: " + prov);
        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        List<String> providers = locMgr.getProviders(criteria, true);
        if (providers == null || providers.size() == 0) {
            Log.e(LOG_TAG, "cannot open GPS service.");
            Toast.makeText(getActivity(), "Could not open GPS service.", Toast.LENGTH_LONG)
                    .show();
        } else {
            prefLocationProvider = providers.get(0);
        }

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

    @Override
    public void onResume() {
        super.onResume();
        if (prefLocationProvider != null && locMgr != null) {
            try {
                locMgr.requestLocationUpdates(prefLocationProvider,
                        MIN_TIME_INTERVAL_IN_MS,
                        MIN_DIST_IN_METERS, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
//                                String lat = Double.toString(location.getLatitude());
//                                String lon = Double.toString(location.getLongitude());
//                                Toast
//                                        .makeText(
//                                        getActivity(), lat + lon, Toast
//                                                        .LENGTH_SHORT)
//                                        .show();
                            }

                            @Override
                            public void onStatusChanged(String s, int i, Bundle bundle) {}

                            @Override
                            public void onProviderEnabled(String s) {}

                            @Override
                            public void onProviderDisabled(String s) {}
                        });
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }

    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        MapUtil.centerMap(property, map);

    }
}
