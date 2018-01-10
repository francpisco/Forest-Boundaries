package almeida.francisco.forestboundaries;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.model.Reading;
import almeida.francisco.forestboundaries.service.PropertyService;
import almeida.francisco.forestboundaries.service.ReadingService;
import almeida.francisco.forestboundaries.util.MapUtil;

public class MarkInSituFragment extends Fragment implements OnMapReadyCallback {

    public interface Listener {
        void saveOnClick();
    }

    private Listener listener;

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
    private Button newMarkerBtn;
    private Button saveMarkerBtn;
    private Button closeLineBtn;
    private Button saveBtn;
    private LatLng currentPosition;
    private Circle centerCircle;
    private float radiusAccuracy;
    private Marker currentMarker;
    private List<LatLng> points = new ArrayList<>();
    private boolean isPolylineOnMap = false;
    private Polyline polyline;
    private Polygon polygon;
    private ReadingService readingService;

    public MarkInSituFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Listener) context;
    }

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

        readingService = new ReadingService(getActivity());

        return inflater.inflate(R.layout.fragment_mark_in_situ, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        propertyNameTxt = view.findViewById(R.id.prop_name_mark_in_s);
        newMarkerBtn = view.findViewById(R.id.create_new_marker_in_situ);
        saveMarkerBtn = view.findViewById(R.id.save_marker_in_situ);
        closeLineBtn = view.findViewById(R.id.close_polyline_in_situ);
        saveBtn = view.findViewById(R.id.save_in_situ);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        newMarkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentMarker !=  null)
                    currentMarker.remove();
                currentMarker = map.addMarker(new MarkerOptions().position(currentPosition));
            }
        });

        saveMarkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentMarker != null) {
                    LatLng latLng = currentMarker.getPosition();
                    currentMarker.remove();
                    map.addMarker(new MarkerOptions().position(latLng));
                    points.add(latLng);
                }
                if (points.size() > 1) {
                    if (!isPolylineOnMap) {
                        List<PatternItem> pattern = Arrays.<PatternItem>asList(
                                new Gap(10f), new Dash(10f), new Gap(10f));
                        polyline = map.addPolyline(new PolylineOptions().width(6f));
                        polyline.setPattern(pattern);
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
                    List<PatternItem> pattern = Arrays.<PatternItem>asList(
                            new Gap(10f), new Dash(10f), new Gap(10f));
                    PolygonOptions polygonOptions = new PolygonOptions()
                            .addAll(points).strokeWidth(6f);
                    polygon = map.addPolygon(polygonOptions);
                    polygon.setStrokePattern(pattern);
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (LatLng l : points) {
                    readingService.createReading(new Reading()
                            .setLatitude(l.latitude)
                            .setLongitude(l.longitude)
                            .setProperty(property));
                }
                listener.saveOnClick();
            }
        });
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
                                radiusAccuracy = location.getAccuracy();
                                currentPosition = new LatLng(location.getLatitude(),
                                        location.getLongitude());
                                if (centerCircle != null)
                                    centerCircle.remove();
                                centerCircle = map.addCircle(new CircleOptions()
                                        .center(currentPosition)
                                        .radius(radiusAccuracy)
                                        .strokeColor(Color.argb(255, 0, 0, 255))
                                        .strokeWidth(3f)
                                        .fillColor(Color.argb(128, 0, 0, 255)));
                                map.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
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
