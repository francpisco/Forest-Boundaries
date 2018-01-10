package almeida.francisco.forestboundaries.util;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import almeida.francisco.forestboundaries.model.MyMarker;
import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.model.Reading;

/**
 * Created by Francisco Almeida on 07/01/2018.
 */

public class MapUtil {

    public static void centerMap(Property property, GoogleMap map) {
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
            PolygonOptions polygonOptions = new PolygonOptions()
                    .addAll(points).strokeColor(Color.GREEN);
            map.addPolygon(polygonOptions);
        }
    }

    public  static void drawPolygonFromReadings(Property property, GoogleMap map, boolean isDashed) {
        List<Reading> readings = property.getReadings();
        List<LatLng> readingPoints = new ArrayList<>();
        for (Reading r : readings) {
            double lat = r.getLatitude();
            double lon = r.getLongitude();
            readingPoints.add(new LatLng(lat, lon));
        }
        int numOfPoints = readingPoints.size();
        if (numOfPoints > 0) {
            PolygonOptions polygonOptions = new PolygonOptions()
                    .addAll(readingPoints).strokeWidth(8f).strokeColor(Color.BLUE);
            Polygon polygon = map.addPolygon(polygonOptions);
            if (isDashed) {
                List<PatternItem> pattern = Arrays.<PatternItem>asList(
                        new Gap(10f), new Dash(10f), new Gap(10f));
                polygon.setStrokePattern(pattern);
            }
        }
    }
}
