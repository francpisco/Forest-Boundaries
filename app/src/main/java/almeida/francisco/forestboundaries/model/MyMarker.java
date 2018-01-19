package almeida.francisco.forestboundaries.model;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francisco Almeida on 19/12/2017.
 */

public class MyMarker implements Comparable<MyMarker> {

    private long id;
    private int index;
    private int tempId;
    private Property property;
    private double markedLatitude;
    private double markedLongitude;
    private double avgLatitude;
    private double avgLongitude;
    private List<Reading> readings = new ArrayList<>();

    public long getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public int getTempId() {
        return tempId;
    }

    public Property getProperty() {
        return property;
    }

    public double getMarkedLatitude() {
        return markedLatitude;
    }

    public double getMarkedLongitude() {
        return markedLongitude;
    }

    public double getAvgLatitude() {
        return avgLatitude;
    }

    public double getAvgLongitude() {
        return avgLongitude;
    }

    public MyMarker setId(long id) {
        this.id = id;
        return this;
    }

    public MyMarker setIndex(int index) {
        this.index = index;
        return this;
    }

    public MyMarker setTempId(int tempId) {
        this.tempId = tempId;
        return this;
    }

    public MyMarker setProperty(Property property) {
        this.property = property;
        return this;
    }

    public MyMarker setMarkedLatitude(double markedLatitude) {
        this.markedLatitude = markedLatitude;
        return this;
    }

    public MyMarker setMarkedLongitude(double markedLongitude) {
        this.markedLongitude = markedLongitude;
        return this;
    }

    public MyMarker setAvgLatitude(double avgLatitude) {
        this.avgLatitude = avgLatitude;
        return this;
    }

    public MyMarker setAvgLongitude(double avgLongitude) {
        this.avgLongitude = avgLongitude;
        return this;
    }

    @Override
    public int compareTo(@NonNull MyMarker marker) {
        if (index == marker.getIndex())
            return 0;
        return (index < marker.getIndex() ? -1 : 1);
    }
}
