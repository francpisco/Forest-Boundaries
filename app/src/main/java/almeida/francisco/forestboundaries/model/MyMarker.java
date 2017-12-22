package almeida.francisco.forestboundaries.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francisco Almeida on 19/12/2017.
 */

public class MyMarker {

    private long id;
    private Property property;
    private double avgLatitude;
    private double avgLongitude;
    private List<Reading> readings = new ArrayList<>();

    public long getId() {
        return id;
    }

    public Property getProperty() {
        return property;
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

    public MyMarker setProperty(Property property) {
        this.property = property;
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
}
