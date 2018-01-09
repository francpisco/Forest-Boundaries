package almeida.francisco.forestboundaries.model;

/**
 * Created by Francisco Almeida on 19/12/2017.
 */

public class Reading {

    private long id;
    private MyMarker marker;
    private Property property;
    private double latitude;
    private double longitude;

    public long getId() {
        return id;
    }

    public Reading setId(long id) {
        this.id = id;
        return this;
    }

    public MyMarker getMarker() {
        return marker;
    }

    public Reading setMarker(MyMarker marker) {
        this.marker = marker;
        return this;
    }

    public Property getProperty() {
        return property;
    }

    public Reading setProperty(Property property) {
        this.property = property;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Reading setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Reading setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }
}
