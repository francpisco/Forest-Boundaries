package almeida.francisco.forestboundaries.util;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

import almeida.francisco.forestboundaries.model.MyMarker;
import almeida.francisco.forestboundaries.model.Property;

/**
 * Created by Francisco Almeida on 07/01/2018.
 */

public class MapUtil {

    public static GoogleMap centerMap(Property property, GoogleMap map) {
        List<MyMarker> markers = property.getMarkers();
        List<LatLng> points = new ArrayList<>();
        double centerLat = 0.0;
        double centerLon = 0.0;
        for (MyMarker m : markers) {
            double lat = m.getMarkedLatitude();
            double lon = m.getMarkedLongitude();
            points.add(new LatLng(lat, lon));
            centerLat += lat;
            centerLon += lon;
        }
        int numOfPoints = points.size();
        if (numOfPoints > 0) {
            centerLat = centerLat/numOfPoints;
            centerLon = centerLon/numOfPoints;
            if ((centerLat + centerLon) > 0.1 || (centerLat + centerLon) < -0.1) {
                LatLng center = new LatLng(centerLat, centerLon);
                map.moveCamera(CameraUpdateFactory.newLatLng(center));
            }
            PolygonOptions polygonOptions = new PolygonOptions().addAll(points);
            map.addPolygon(polygonOptions);
        }
        return map;
    }
}
